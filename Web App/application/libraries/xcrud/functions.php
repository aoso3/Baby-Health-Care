<?php

function delete_patient($doctor_id,$patient_id){
   $this->db->delete('child_doctors', array('id_doctor' => $doctor_id, 'id_child'=>$patient_id));
}

function delete(){
   //$this->db->delete('child_doctors', array('id_doctor' => $xcrud->post('doctor_id'), 'id_child'=>$xcrud->post('patient_id')));

      //  $db = Xcrud_db::get_instance();
      //  $query = 'UPDATE base_fields SET `bool` = b\'0\' WHERE id = ' . (int)$xcrud->get('primary');
      //  $query_del = 'DELETE FROM child_doctors WHERE id_doctor = '.$xcrud->get('primary').' AND id_child= 2';
      //  $db->query($query);
      $this->db->delete('child_doctors', array('id_doctor' => 1, 'id_child'=>2));




}
 function hash_password($password, $salt=false, $use_sha1_override=FALSE)
{
  if (empty($password))
  {
    return FALSE;
  }

  // bcrypt
  if ($use_sha1_override === FALSE && $this->hash_method == 'bcrypt')
  {
    return $this->bcrypt->hash($password);
  }


  if ($this->store_salt && $salt)
  {
    return  sha1($password . $salt);
  }
  else
  {
    $salt = $this->salt();
    return  $salt . substr(sha1($salt . $password), 0, -$this->salt_length);
  }
}

function publish_action($xcrud)
{
    if ($xcrud->get('primary'))
    {
        $db = Xcrud_db::get_instance();
        $query = 'UPDATE child_doctors SET `Approved` = b\'1\' WHERE id_doctor = ' . (int)$xcrud->get('primary').
        ' AND id_child = '.(int)$xcrud->get('secondary') ;
        $db->query($query);
    }
}
function unpublish_action($xcrud)
{
    if ($xcrud->get('primary'))
    {
        $db = Xcrud_db::get_instance();
        $query = 'UPDATE child_doctors SET `Approved` = b\'0\' WHERE id_doctor = ' . (int)$xcrud->get('primary').
        ' AND id_child = '.(int)$xcrud->get('secondary') ;
        $db->query($query);
    }
}

function exception_example($postdata, $primary, $xcrud)
{
    $xcrud->set_exception('ban_reason', 'Lol!', 'error');
    $postdata->set('ban_reason', 'lalala');
}

function test_column_callback($value, $fieldname, $primary, $row, $xcrud)
{
    return $value . ' - nice!';
}

function after_upload_example($field, $file_name, $file_path, $params, $xcrud)
{
    $ext = trim(strtolower(strrchr($file_name, '.')), '.');
    if ($ext != 'pdf' && $field == 'uploads.simple_upload')
    {
        unlink($file_path);
        $xcrud->set_exception('simple_upload', 'This is not PDF', 'error');
    }
}

function date_example($postdata, $primary, $xcrud)
{
    $created = $postdata->get('datetime')->as_datetime();
    $postdata->set('datetime', $created);
}

function movetop($xcrud)
{
    if ($xcrud->get('primary') !== false)
    {
        $primary = (int)$xcrud->get('primary');
        $db = Xcrud_db::get_instance();
        $query = 'SELECT `officeCode` FROM `offices` ORDER BY `ordering`,`officeCode`';
        $db->query($query);
        $result = $db->result();
        $count = count($result);

        $sort = array();
        foreach ($result as $key => $item)
        {
            if ($item['officeCode'] == $primary && $key != 0)
            {
                array_splice($result, $key - 1, 0, array($item));
                unset($result[$key + 1]);
                break;
            }
        }

        foreach ($result as $key => $item)
        {
            $query = 'UPDATE `offices` SET `ordering` = ' . $key . ' WHERE officeCode = ' . $item['officeCode'];
            $db->query($query);
        }
    }
}
function movebottom($xcrud)
{
    if ($xcrud->get('primary') !== false)
    {
        $primary = (int)$xcrud->get('primary');
        $db = Xcrud_db::get_instance();
        $query = 'SELECT `officeCode` FROM `offices` ORDER BY `ordering`,`officeCode`';
        $db->query($query);
        $result = $db->result();
        $count = count($result);

        $sort = array();
        foreach ($result as $key => $item)
        {
            if ($item['officeCode'] == $primary && $key != $count - 1)
            {
                unset($result[$key]);
                array_splice($result, $key + 1, 0, array($item));
                break;
            }
        }

        foreach ($result as $key => $item)
        {
            $query = 'UPDATE `offices` SET `ordering` = ' . $key . ' WHERE officeCode = ' . $item['officeCode'];
            $db->query($query);
        }
    }
}


//  My Function
function before_update_work($postdata, $primary, $xcrud){
    $ext = trim(strtolower(strrchr($postdata->get('img_url'), '.')), '.');
    if($postdata->get('img_url') !='work_'.$primary.'.'.$ext && $postdata->get('img_url') !='')
    {
        rename('../../../assets/img/workshops/'.$postdata->get('img_url'),'../../../assets/img/workshops/work_'.$primary.'.'.$ext);
        $postdata->set('img_url','work_'.$primary.'.'.$ext);
    }
}

function before_update_cat($postdata, $primary, $xcrud){
    $ext = trim(strtolower(strrchr($postdata->get('img_url'), '.')), '.');
     if($postdata->get('img_url') !='work_'.$primary.'.'.$ext && $postdata->get('img_url') !='')
    {
        rename('../../../assets/img/categories/'.$postdata->get('img_url'),'../../../assets/img/categories/cat_'.$primary.'.'.$ext);
        $postdata->set('img_url','cat_'.$primary.'.'.$ext);
    }
}

function before_update_sugg($postdata, $primary, $xcrud){
    $ext = trim(strtolower(strrchr($postdata->get('img_url'), '.')), '.');
    if($postdata->get('img_url') !='sugg_'.$primary.'.'.$ext && $postdata->get('img_url') !='')
    {
        rename('../../../assets/img/suggested_work/'.$postdata->get('img_url'),'../../../assets/img/suggested_work/sugg_'.$primary.'.'.$ext);
        $postdata->set('img_url','sugg_'.$primary.'.'.$ext);
    }
}


function before_insert_work($postdata, $primary, $xcrud){
    if($postdata->get('img_url') != "")
    {
        $ext = trim(strtolower(strrchr($postdata->get('img_url'), '.')), '.');
        rename('../../../assets/img/workshops/'.$postdata->get('img_url'),'../../../assets/img/workshops/work_'.$primary.'.'.$ext);

        $db = Xcrud_db::get_instance();
        $new_name = 'work_'.$primary.'.'.$ext;
        $query = "UPDATE workshops SET img_url='".$new_name."' WHERE id='".$primary."';";
        $db->query($query);
    }
}

function before_insert_cat($postdata, $primary, $xcrud){
    if($postdata->get('img_url') != "")
    {
        $ext = trim(strtolower(strrchr($postdata->get('img_url'), '.')), '.');
        rename('../../../assets/img/categories/'.$postdata->get('img_url'),'../../../assets/img/categories/cat_'.$primary.'.'.$ext);

        $db = Xcrud_db::get_instance();
        $new_name = 'cat_'.$primary.'.'.$ext;
        $query = "UPDATE categories SET img_url='".$new_name."' WHERE id='".$primary."';";
        $db->query($query);
    }
}

function after_insert_sugg($postdata, $primary, $xcrud){
    if($postdata->get('img_url') != "")
    {
        $ext = trim(strtolower(strrchr($postdata->get('img_url'), '.')), '.');
        rename('../../../assets/img/suggested_work/'.$postdata->get('img_url'),'../../../assets/img/suggested_work/sugg_'.$primary.'.'.$ext);

        $db = Xcrud_db::get_instance();
        $new_name = 'sugg_'.$primary.'.'.$ext;
        $query = "UPDATE voting_count SET img_url='".$new_name."' WHERE id='".$primary."';";
        $db->query($query);

        $query = 'UPDATE voting_count SET created_at =CURDATE() WHERE id = ' . (int)$primary;
        $db->query($query);

    }
}



// function after_remove_work($postdata,$primary, $xcrud){
//     var_dump($postdata);

// }


function accept_users($xcrud){
    $db = Xcrud_db::get_instance();
    $query_del = 'DELETE FROM wait_work WHERE id = '.$xcrud->get('primary');
    $query_ins = 'INSERT INTO user_work (workshop_id,user_id,created_at) VALUES ('.$xcrud->get('work_id').','.$xcrud->get('user_id').',CURDATE())';
    if($db->query($query_ins))
        $db->query($query_del);
}


function test($primary, $xcrud){

  // $db = Xcrud_db::get_instance();
  // $db->query('INSERT INTO message (name) VALUES ("koko")');
  // $this->load->library('gcm_notification');
  //
  // $arr['name'] = 'test';
  // $this->db->insert('message',$arr);
  //
  // // prepare options array, you can use custom key s
  // $opts_array = array(
  //     'message'   => 'TEST',
  //     'title'     => 'TEST',
  //     'subtitle'  => 'TEST',
  //     'tickerText'    => 'TEST',
  //     'vibrate'   => 1,
  //     'sound'     => 1,
  //     'largeIcon' => 'large_icon',
  //     'smallIcon' => 'small_icon'
  // );
  //
  // // place your recipients here. select gcm_id from db or smth. don't use $key=>$value
  // $reg_ids[] = 'cozgWcM8eVA:APA91bF-baUGntOobdhcoUvjFhu_85UsncXJCL1V91qW3cvziDXK9PapAFtvzv3l4KDNUzFwscMb5uy5tW6MC59hRK3nXs6wegWGpIjUUX8vZlGp-3p893iPEaCsG6TsWW8-fKc1N_F';
  //
  // // seting recipient
  // $this->gcm_notification->setRecipients($reg_ids);
  //
  // // /* set Time To Live - How long (in seconds) the message should be kept on GCM storage if the device is offline.
  // //  Optional (default time-to-live is 4 weeks, and must be set as a JSON number).*/
  // // $this->gcm_notification->setTTL(20);
  // //
  // // // set collapse Key
  // // /*An arbitrary string (such as "Updates Available") that is used to collapse a group of like messages when the device is
  // //  offline, so that only the last message gets sent to the client. This is intended to avoid sending too many messages to
  // //  the phone when it comes back online.*/
  // // $this->gcm_notification->setCollapseKey('GCM_Library');
  // //
  // // // takes boolean
  // // /*If included, indicates that the message should not be sent immediately if the device is idle. The server will wait for
  // // the device to become active, and then only the last message for each collapse_key value will be sent.*/
  // // $this->gcm_notification->setDelay(true);
  //
  // // set predefined options
  // $this->gcm_notification->setOptions($opts_array);
  //
  // // // debug info. http_headers if (400,401,500) and success if 200. takes boolean
  //  $this->gcm_notification->setDebug(true);
  //
  // // finally send it. if DEBUG is TRUE , print , returns array
  // $this->gcm_notification->send();

}
