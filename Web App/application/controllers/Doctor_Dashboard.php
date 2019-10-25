<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Doctor_Dashboard extends CI_Controller {

  function __construct()
		{
			parent::__construct();
      $this->load->library('xcrud/xcrud.php');
      $this->load->library('users_lib');
      $this->load->library('doctor_dashboard_lib');
      $this->lang->load('auth');
      $this->load->library('gcm');
		}

  function test(){
    //tofix
    // $id_quantitie = 1;
    // $data['chart_info'] = $this->doctor_dashboard_lib->get_char_growth_y($id_child,$id_quantitie);
    // $data['quantities_array'] = $this->doctor_dashboard_lib->get_quantity($id_child,$id_quantitie);
    // $data['id_child'] = $id_child;
    // $data['main_content'] = 'Doctor_panel/test';
    // $this->load->view('include/main',$data);
  }



   function update_food (){

     $food_id = $this->input->post("food_id");
     $status = $this->input->post("status");

     $this->doctor_dashboard_lib->update_food_status($food_id,$status);

   }

  function add_quantity(){

    $data = json_decode(file_get_contents("php://input"));
    //tofix
    $id_child = $data->id_child;
    $id_quantitie = $data->qe;
    $week = $data->we;
    $value = $data->val;

    $this->doctor_dashboard_lib->add_quantity($id_child,$id_quantitie,$week,$value);


  }
  function delete_quantity(){
    $data = json_decode(file_get_contents("php://input"));
    //tofix
    $id_child = $data->id_child;
    $id_quantitie = $data->qe;
    $week = $data->we;
    $value = $data->val;

    $this->doctor_dashboard_lib->delete_quantity($id_child,$id_quantitie,$week,$value);


  }

  function update_quantity(){

    $data = json_decode(file_get_contents("php://input"));
    $id_quantitie = $data->id_q;
    $value = $data->val;

    $this->doctor_dashboard_lib->update_quantity($id_quantitie,$value);


  }

	 function index()
		{
      if($this->ion_auth->logged_in()){
      $current_user_id = $this->ion_auth->get_user_id();
      $user_role = $this->users_lib->user_role($current_user_id);
      if($user_role == "doctor"){
          $data['user_info'] = $this->users_lib->user_info($current_user_id);
          $data['children_count'] = count($this->doctor_dashboard_lib->get_doctor_childs($current_user_id));
          $data['doctor_review'] = $this->doctor_dashboard_lib->get_doctor_reviews($current_user_id);
          $data['patients_chart'] = $this->doctor_dashboard_lib->get_doctor_childs_dates($current_user_id);
          $data['doctor_dashboard'] = 1;
          $data['doctor_dashboard_index'] = 1;
          $data['notification'] = $this->doctor_dashboard_lib->get_all_notifications($current_user_id);
          $data['notification_count'] = $this->doctor_dashboard_lib->get_all_notifications_count($current_user_id);
          $data['current_user_id'] = $this->ion_auth->get_user_id();
    			$data['main_content'] = 'Doctor_panel/Dashboard';
    			$this->load->view('include/main',$data);
        }
        else{
           echo "only doctors can view this page.";
        }
      }
      else{
        $data['notification'] = $this->doctor_dashboard_lib->get_all_notifications($current_user_id);
        $data['notification_count'] = $this->doctor_dashboard_lib->get_all_notifications_count($current_user_id);
        $data['current_user_id'] = $this->ion_auth->get_user_id();
        $data['main_content'] = 'auth';
        $this->load->view('include/main',$data);
      }
		}

    function patients_approval(){
      if($this->ion_auth->logged_in()){

            $current_user_id = $this->ion_auth->get_user_id();
            $user_role = $this->users_lib->user_role($current_user_id);
            if($user_role == "doctor"){
                   $xcrud = Xcrud::get_instance();
                   $xcrud->table('child_doctors');
                   $xcrud->join('id_child','child','id');
                   $xcrud->columns('child.first_name,child.last_name,child.birth_date,
                   child.gender,child.pic,child_doctors.approved');
                   $xcrud->highlight_row('approved', '=', 0, '#e6d47a');
                   $xcrud->table_name(' Patients Approval ');
                   $xcrud->where('id_doctor =', $current_user_id);
                   $xcrud->where('approved =', 0);
                  //  $xcrud->create_action('publish', 'publish_action');
                  //  $xcrud->create_action('unpublish', 'unpublish_action');
                  //  $xcrud->button('#', 'unpublished', 'icon-close glyphicon glyphicon-remove', 'xcrud-action',
                  //      array(  // set action vars to the button
                  //          'data-task' => 'action',
                  //          'data-action' => 'publish',
                  //          'data-primary' => '{id_doctor}',
                  //          'data-secondary'  =>  '{id_child}' ),
                  //      array(  // set condition ( when button must be shown)
                  //          'approved',
                  //          '!=',
                  //          '1')
                  //  );
                  //  $xcrud->button('#', 'published', 'icon-checkmark glyphicon glyphicon-ok', 'xcrud-action', array(
                  //      'data-task' => 'action',
                  //      'data-action' => 'unpublish',
                  //      'data-primary' => '{id_doctor}',
                  //      'data-secondary'  =>  '{id_child}' ),
                  //     array(
                  //      'approved',
                  //      '=',
                  //      '1'));

                  $xcrud->change_type('child.pic', 'image', false, array(
                    'manual_crop'=>true,
                    'width' => 450,
                    'path'=>  '../../../assets/images/baby_profile',
                    'thumbs' => array(array(
                            'height' => 55,
                            'width' => 120,
                            'crop' => true,
                            'marker' => '_th'))));

                  $xcrud->modal('child.pic');
                  $xcrud->column_class('child.pic', 'align-center');

                   $xcrud->button(base_url("doctor_dashboard/send_child_approve_gcm/$current_user_id/$current_user_id/4/{id_child}"),'View','glyphicon glyphicon-ok','');


                   $xcrud->unset_add();
                   $xcrud->unset_edit();
                   $xcrud->unset_remove();
                   $xcrud->benchmark();
                   $data['xcrud'] = $xcrud->render();

                   $data['user_info'] = $this->users_lib->user_info($current_user_id);
                   $data['doctor_dashboard'] = 1;
                   $data['doctor_patients_approval'] = 1;
                   $data['notification'] = $this->doctor_dashboard_lib->get_all_notifications($current_user_id);
                   $data['notification_count'] = $this->doctor_dashboard_lib->get_all_notifications_count($current_user_id);
                   $data['current_user_id'] = $this->ion_auth->get_user_id();
                   $data['main_content'] = 'Doctor_panel/Table_Template';
                   $this->load->view('include/main',$data);
                 }
                 else{
                    echo "only doctors can view this page.";
                 }
            }
            else{
              $data['notification'] = $this->doctor_dashboard_lib->get_all_notifications($current_user_id);
              $data['notification_count'] = $this->doctor_dashboard_lib->get_all_notifications_count($current_user_id);
              $data['current_user_id'] = $this->ion_auth->get_user_id();
              $data['main_content'] = 'auth';
             $this->load->view('include/main',$data);
            }

    }


    function patients(){
      if($this->ion_auth->logged_in()){
          $current_user_id = $this->ion_auth->get_user_id();
          $user_role = $this->users_lib->user_role($current_user_id);
          if($user_role == "doctor"){
                $xcrud = Xcrud::get_instance();

                $xcrud->table('child_doctors');
                $xcrud->table_name('Patients Approval ');
                $xcrud->columns('child.first_name,child.last_name,child.birth_date,
                child.gender,child.pic');
                $xcrud->join('child_doctors.id_child','child','id');
                $xcrud->where('child_doctors.id_doctor =', $current_user_id);
                $xcrud->where('child_doctors.approved =',1);
                $xcrud->unset_add();
                $xcrud->unset_edit();
                $xcrud->unset_view();
                $xcrud->unset_remove();
                $xcrud->change_type('child.pic', 'image', false, array(
                  'manual_crop'=>true,
                  'width' => 450,
                  'path'=>  '../../../assets/images/baby_profile',
                  'thumbs' => array(array(
                          'height' => 55,
                          'width' => 120,
                          'crop' => true,
                          'marker' => '_th'))));

                $xcrud->modal('child.pic');
                $xcrud->column_class('child.pic', 'align-center');


                $xcrud->button(base_url('doctor_dashboard/patients_main_panel').'/{child.id}','View','glyphicon glyphicon-zoom-in','',array('target'=>'_blank'));
                $xcrud->benchmark();
                $data['xcrud'] = $xcrud->render();

                $data['user_info'] = $this->users_lib->user_info($current_user_id);
                $data['doctor_dashboard'] = 1;
                $data['doctor_patients'] = 1;
                $data['notification'] = $this->doctor_dashboard_lib->get_all_notifications($current_user_id);
                $data['notification_count'] = $this->doctor_dashboard_lib->get_all_notifications_count($current_user_id);
                $data['current_user_id'] = $this->ion_auth->get_user_id();
                $data['main_content'] = 'Doctor_panel/Table_Template';
                $this->load->view('include/main',$data);

              }
              else{
                 echo "only doctors can view this page.";
              }
          }
        else{
          $data['notification'] = $this->doctor_dashboard_lib->get_all_notifications($current_user_id);
          $data['notification_count'] = $this->doctor_dashboard_lib->get_all_notifications_count($current_user_id);
          $data['current_user_id'] = $this->ion_auth->get_user_id();
          $data['main_content'] = 'auth';
          $this->load->view('include/main',$data);
        }
    }

    public function quantities_array(){

      $data = json_decode(file_get_contents("php://input"));
      $id_child = $data->id_child;
      $id_quantitie = $data->id_quantitie;

       $quantities_array = $this->doctor_dashboard_lib->get_quantity($id_child,$id_quantitie);
       echo json_encode($quantities_array, JSON_NUMERIC_CHECK );

    }
    public function chart_info(){

      $data = json_decode(file_get_contents("php://input"));
      $id_child = $data->id_child;
      $id_quantitie = $data->id_quantitie;

       $chart_info = $this->doctor_dashboard_lib->get_char_growth_y($id_child,$id_quantitie);
       echo json_encode($chart_info, JSON_PRETTY_PRINT);

    }
    public function vac_compleated(){

       $data = json_decode(file_get_contents("php://input"));
       $id_child = $data->id_child;

       $vac = $this->doctor_dashboard_lib->get_taken_vaccination($id_child);
       echo json_encode($vac, JSON_PRETTY_PRINT);

    }
    public function vac_not_compleated(){

       $data = json_decode(file_get_contents("php://input"));
       $id_child = $data->id_child;

       $vac = $this->doctor_dashboard_lib->get_not_taken_vaccination($id_child);
       echo json_encode($vac, JSON_PRETTY_PRINT);

    }
    public function vac_requests(){

       $data = json_decode(file_get_contents("php://input"));
       $id_child = $data->id_child;

       $vac = $this->doctor_dashboard_lib->get_requasted_vaccination($id_child);
       echo json_encode($vac, JSON_PRETTY_PRINT);

    }

    public function patients_main_panel($patient_id){

      $id_quantitie = 1;
      $current_user_id = $this->ion_auth->get_user_id();
      $sex = $this->users_lib->child_info($patient_id)->gender;
      $data['main_sections'] = $this->doctor_dashboard_lib->get_main_sections();
      $data['chart_info'] = $this->doctor_dashboard_lib->get_char_growth_y($sex,$id_quantitie);
      $data['quantities_array'] = $this->doctor_dashboard_lib->get_quantity($patient_id,$id_quantitie);
      $data['sleep_chart_info'] = $this->doctor_dashboard_lib->get_sleep_chart();
      $data['sleep_chart_baby_info'] = $this->doctor_dashboard_lib->get_sleep_chart_baby($patient_id);
      $data['skills_done'] = $this->doctor_dashboard_lib->get_done_skills($patient_id);
      $data['skills_not_done'] = $this->doctor_dashboard_lib->get_not_done_skills($patient_id);
      $data['vac_taken'] =  $this->doctor_dashboard_lib->get_taken_vaccination($patient_id);
      $data['vac_not_taken'] =  $this->doctor_dashboard_lib->get_not_taken_vaccination($patient_id);
      $data['patient_id'] = $id_quantitie;
      $data['id_child'] = $patient_id;
      $data['id_doctor'] =  $this->ion_auth->get_user_id();
      $data['child_data']  =$this->doctor_dashboard_lib->child_info($patient_id);
      $data['patients_main_panel'] = 1;
      $data['notification'] = $this->doctor_dashboard_lib->get_all_notifications($current_user_id);
      $data['notification_count'] = $this->doctor_dashboard_lib->get_all_notifications_count($current_user_id);
      $data['current_user_id'] = $this->ion_auth->get_user_id();
      $data['main_content'] = 'Doctor_panel/patients_main_panel';
      $this->load->view('include/main',$data);

    }

    public function patients_sleep(){
      $current_user_id = $this->ion_auth->get_user_id();
      $data['patients_main_panel'] = 1;
      $data['sleep_page'] = 1;
      $data['notification'] = $this->doctor_dashboard_lib->get_all_notifications($current_user_id);
      $data['notification_count'] = $this->doctor_dashboard_lib->get_all_notifications_count($current_user_id);
      $data['current_user_id'] = $this->ion_auth->get_user_id();
      $this->load->view('Doctor_panel/main_panel/sleep',$data);
    }
    public function patients_food($id_child){
      $current_user_id = $this->ion_auth->get_user_id();
      $data['food'] = $this->doctor_dashboard_lib->get_food_and_samples($id_child,1);
      $data['food_samples'] = $this->doctor_dashboard_lib->get_food_and_samples($id_child,2);
      $data['id_child'] = $id_child;
      $data['food_page'] = 1;
      $data['patients_main_panel'] = 1;
      $data['notification'] = $this->doctor_dashboard_lib->get_all_notifications($current_user_id);
      $data['notification_count'] = $this->doctor_dashboard_lib->get_all_notifications_count($current_user_id);
      $data['current_user_id'] = $this->ion_auth->get_user_id();
      $this->load->view('Doctor_panel/main_panel/food',$data);
    }
    public function patients_grow(){
      $current_user_id = $this->ion_auth->get_user_id();
      $data['growth_page'] = 1;
      $data['patients_main_panel'] = 1;
      $data['notification'] = $this->doctor_dashboard_lib->get_all_notifications($current_user_id);
      $data['notification_count'] = $this->doctor_dashboard_lib->get_all_notifications_count($current_user_id);
      $data['current_user_id'] = $this->ion_auth->get_user_id();
      $this->load->view('Doctor_panel/main_panel/growth',$data);
    }
    public function patients_skills(){
      $current_user_id = $this->ion_auth->get_user_id();
      $data['skills_page'] = 1;
      $data['patients_main_panel'] = 1;
      $data['notification'] = $this->doctor_dashboard_lib->get_all_notifications($current_user_id);
      $data['notification_count'] = $this->doctor_dashboard_lib->get_all_notifications_count($current_user_id);
      $data['current_user_id'] = $this->ion_auth->get_user_id();
      $this->load->view('Doctor_panel/main_panel/skills',$data);
    }
    public function patients_Vaccinations(){
      $current_user_id = $this->ion_auth->get_user_id();
      $data['Vaccinations_page'] = 1;
      $data['patients_main_panel'] = 1;
      $data['notification'] = $this->doctor_dashboard_lib->get_all_notifications($current_user_id);
      $data['notification_count'] = $this->doctor_dashboard_lib->get_all_notifications_count($current_user_id);
      $data['current_user_id'] = $this->ion_auth->get_user_id();
      $this->load->view('Doctor_panel/main_panel/Vaccinations',$data);
    }

    public function Vaccination_adv(){

      if($this->ion_auth->logged_in()){
          $current_user_id = $this->ion_auth->get_user_id();
          $user_role = $this->users_lib->user_role($current_user_id);
          if($user_role == "doctor"){

                $xcrud = Xcrud::get_instance();
                $xcrud->language('en');
                $xcrud->table('vaccination_adv');
                $xcrud->table_name('Vaccination Advertisements');
                $xcrud->fields('id_doctor', true);
                $xcrud->columns('id_doctor', true);
                $xcrud->validation_required(array(
                   'id_vaccination',
                   'text' ,
                   'age_form',
                   'age_to' ,
                   'activation_date',
                   'close_date',
                   'status',
                   'price',
                 ));
                $xcrud->where('id_doctor =', $current_user_id);
                $xcrud->relation('id_vaccination','vaccination','id','name');
                $xcrud->pass_var('id_doctor', $current_user_id);
                $xcrud->unset_view();
                $xcrud->button(base_url('doctor_dashboard/Vaccination_child').'/{id}','View','glyphicon glyphicon-zoom-in','',array('target'=>'_blank'));
                $xcrud->button(base_url("doctor_dashboard/send_new_vac_ad_gcm/$current_user_id/{id}/3"),'View','glyphicon glyphicon-ok','');
                $xcrud->after_insert('test');
                $xcrud->benchmark();
                $data['xcrud'] = $xcrud->render();

                $data['user_info'] = $this->users_lib->user_info($current_user_id);
                $data['doctor_dashboard'] = 1;
                $data['Vaccination_adv'] = 1;
                $data['notification'] = $this->doctor_dashboard_lib->get_all_notifications($current_user_id);
                $data['notification_count'] = $this->doctor_dashboard_lib->get_all_notifications_count($current_user_id);
                $data['current_user_id'] = $this->ion_auth->get_user_id();
                $data['main_content'] = 'Doctor_panel/Table_Template';
                $this->load->view('include/main',$data);

              }
              else{
                 echo "only doctors can view this page.";
              }
          }
        else{
          $data['notification'] = $this->doctor_dashboard_lib->get_all_notifications($current_user_id);
            $data['notification_count'] = $this->doctor_dashboard_lib->get_all_notifications_count($current_user_id);
          $data['current_user_id'] = $this->ion_auth->get_user_id();
          $data['main_content'] = 'auth';
          $this->load->view('include/main',$data);
        }

    }

    public function Vaccination_child($vaccination_adv_id = null){
      if($this->ion_auth->logged_in()){
          $current_user_id = $this->ion_auth->get_user_id();
          $user_role = $this->users_lib->user_role($current_user_id);
          if($user_role == "doctor"){

            $data['vaccination_list'] = $this->doctor_dashboard_lib->get_vaccination_list($current_user_id);

            $xcrud = Xcrud::get_instance();

            $xcrud->table('child');
            $xcrud->table_name(' Patients Vaccination Requests ');
            $xcrud->columns('child.first_name,child.last_name,vaccination.name');
            $xcrud->join('child.id','register_for_vaccination','id_child');
            $xcrud->join('register_for_vaccination.id_adv','vaccination_adv','id');
            $xcrud->join('vaccination_adv.id_vaccination','vaccination','id');
            $data['current_vac'] = 0;
            if($vaccination_adv_id != null){
              $xcrud->where('register_for_vaccination.id_adv =', $vaccination_adv_id);
              $data['current_vac'] = $this->doctor_dashboard_lib->get_vaccination($vaccination_adv_id);

            }
            $xcrud->where('vaccination_adv.id_doctor =', $current_user_id);

            $xcrud->unset_add();
            $xcrud->unset_edit();
            $xcrud->unset_view();
            $xcrud->unset_remove();
            $xcrud->button(base_url('doctor_dashboard/patients_main_panel').'/{register_for_vaccination.id_child}','View','glyphicon glyphicon-zoom-in','',array('target'=>'_blank'));


            $xcrud->benchmark();
            $data['xcrud'] = $xcrud->render();

            $data['user_info'] = $this->users_lib->user_info($current_user_id);
            $data['doctor_dashboard'] = 1;
            $data['Vaccination_child'] = 1;
            $data['notification'] = $this->doctor_dashboard_lib->get_all_notifications($current_user_id);
            $data['notification_count'] = $this->doctor_dashboard_lib->get_all_notifications_count($current_user_id);
            $data['current_user_id'] = $this->ion_auth->get_user_id();
            $data['main_content'] = 'Doctor_panel/Table_Template_vac';
            $this->load->view('include/main',$data);


          }
          else{
             echo "only doctors can view this page.";
          }
          }
          else{
            $data['notification'] = $this->doctor_dashboard_lib->get_all_notifications($current_user_id);
            $data['notification_count'] = $this->doctor_dashboard_lib->get_all_notifications_count($current_user_id);
            $data['current_user_id'] = $this->ion_auth->get_user_id();
            $data['main_content'] = 'auth';
            $this->load->view('include/main',$data);
          }


    }

    public function send_new_vac_ad_gcm($creator_user_id,$additional_info,$type_id){

          // $parents =  $this->doctor_dashboard_lib->get_doctor_parents($creator_user_id);


            $notifications_array = $this->doctor_dashboard_lib->add_vac_ad_notification($creator_user_id,$additional_info,$type_id);
            $current_user_id = $this->ion_auth->get_user_id();
            $token_array = $this->doctor_dashboard_lib->get_vac_ad_token($current_user_id);


            if(!empty($notifications_array)){

             foreach ($notifications_array as $notification) {

              //  foreach ($notifications_array as $noti) {


                      // echo "<pre>";
                      // print_r($noti);
                      // echo "</pre>";
                      // echo "soso" ;
                      // die();

                      foreach ($token_array as $key ) {
                        if($key->id_user == $notification[0]->recipient_user_id){
                          // echo $key->id_user;
                          // echo $noti->recipient_user_id;
                            // echo "1";
                            // echo $key->id_user;
                            $this->gcm->addRecepient($key->token);
                          }
                      }
                      $array =  (array) $notification[0];
                      unset($array['recipient_user_id']);
                      // echo "<pre>";
                      // print_r(json_encode($array));
                      // echo "</pre>";
                      // echo "soso" ;
                      // die();

                      $this->gcm->setMessage(json_encode($array));


                      $this->gcm->setData(array(
                          'date' => date('d.m.Y H:s:i')
                      ));


                     $this->gcm->send();



                  //  }

              }
                //  die();
//              var_dump($token_array);
 // die();

 // die();

           redirect("/Doctor_Dashboard/Vaccination_adv",'refresh');
         }
      }


      public function send_child_approve_gcm($creator_user_id,$additional_info,$type_id,$child_id){

              $this->doctor_dashboard_lib->approve_child($creator_user_id,$child_id);

              $notification = $this->doctor_dashboard_lib->add_child_approve_notification($creator_user_id,$additional_info,$type_id,$child_id);

              if(!empty($notification)){



              // echo "<pre>";
              // print_r($notification[0]);
              // echo "</pre>";
              // echo "soso" ;
              // die();

              $current_user_id = $this->ion_auth->get_user_id();
              $token_array = $this->doctor_dashboard_lib->get_child_token($current_user_id,$child_id);

              foreach ($token_array as $key ) {
                $this->gcm->addRecepient($key->token);
              }

              $array =  (array) $notification[0];
              unset($array['recipient_user_id']);

            $this->gcm->setMessage(json_encode($array));

              $this->gcm->setData(array(
                  'date' => date('d.m.Y H:s:i')
              ));
           //
          //     var_dump($notification[0] );die();
          //     if ($this->gcm->send())
          //      echo 'Success for all messages';
          //  else
          //      echo 'Some messages have errors';
           //
          //      die();

               $this->gcm->send();

            // redirect("/Doctor_Dashboard/patients_main_panel/$child_id",'refresh');
            redirect("/Doctor_Dashboard/patients",'refresh');

          }

        }


        public function send_vaccination_added_gcm(){


                $data = json_decode(file_get_contents("php://input"));
                $creator_user_id = $data->creator_user_id;
                $additional_info = $data->id_vac;
                $type_id = $data->type_id;
                $child_id = $data->child_id;



                $this->doctor_dashboard_lib->vac_taken($creator_user_id,$child_id,$additional_info);

            //     $child_vac = $this->doctor_dashboard_lib->add_vac_requast($creator_user_id,$child_id,$additional_info);
             //
            //     $notification = $this->doctor_dashboard_lib->add_vaccination_added_notification($creator_user_id,$child_vac,$type_id,$child_id);
             //
            //     if(!empty($notification)){
             //
             //
             //
            //     $current_user_id = $this->ion_auth->get_user_id();
            //     $token_array = $this->doctor_dashboard_lib->get_child_token($current_user_id,$child_id);
             //
            //     foreach ($token_array as $key ) {
            //       $this->gcm->addRecepient($key->token);
            //     }
             //
            //     $array =  (array) $notification[0];
            //     unset($array['recipient_user_id']);
             //
            //   $this->gcm->setMessage(json_encode($array));
             //
             //
            //     $this->gcm->setData(array(
            //         'date' => date('d.m.Y H:s:i')
            //     ));
             //
            //    $this->gcm->send();
             //
            //  }
             //

          }

          public function noti_seen(){
            $current_user_id = $this->ion_auth->get_user_id();
            $count = $this->doctor_dashboard_lib->notifications_seen($current_user_id);
            json_encode($count);
          }



}
