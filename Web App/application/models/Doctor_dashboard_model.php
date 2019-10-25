<?php  if ( ! defined('BASEPATH')) exit('No direct script access allowed');

  class Doctor_dashboard_model extends CI_Model
    {
          protected $ci;
          public function __construct() {
                  parent::__construct();
                  $this->ci = &get_instance();
              }

          public function child_info($child_id){
            return $this->db->select('*')->from('child')->where('id',$child_id)->get()->row();
          }

          public function approve_child($doctor_id,$child_id){
            $data ['approved'] = 1;
            $data ['approved_date'] = date("Y-m-d");
            $this->db->update('child_doctors',$data,array('id_doctor' => $doctor_id,'id_child' => $child_id));
          }

        public function get_chart_max_x(){
            $this->db->select_max('age_weeks');
            $q = $this->db->get('growth_chart_data');
            return $q->result()[0]->age_weeks;
        }

        public function get_char_growth_y($sex,$id_quantities){
          return  $this->db->select('age_weeks,p3,p50,p97')->from('growth_chart_data')
                     ->where(array('sex'=>$sex,'id_quantity'=>$id_quantities))->get()->result();
        }

        public function add_quantity($child_id,$quantitie_id,$week,$value){
              $data = array(
                 'id_child' => $child_id ,
                 'id_quantities' => $quantitie_id ,
                 'age_weeks' => $week,
                 'measurement' => $value

              );

              $this->db->insert('child_quantities', $data);
        }

        public function delete_quantity($child_id,$quantitie_id,$week,$value){
                $data = array(
                   'id_child' => $child_id ,
                   'id_quantities' => $quantitie_id ,
                   'age_weeks' => $week,
                   'measurement' => $value

                );
               $this->db->delete('child_quantities', $data);

        }
        public function update_quantity($id_quantitie,$value){
                $data = array(
                   'measurement' => $value
                );
               $this->db->where(array('id' => $id_quantitie));
               $this->db->update('child_quantities', $data);

        }

        public function get_quantity($child_id,$quantitie_id){
          return  $this->db->select('id,age_weeks,measurement')->from('child_quantities')
                     ->where(array('id_child'=>$child_id,'id_quantities'=>$quantitie_id))
                     ->order_by('age_weeks','ASC')->get()->result();
        }


        public function get_food($child_id){
          return  $this->db->select('*')->from('food_type')
                     ->where(array('id_child'=>$child_id))
                     ->get()->result();
        }

        public function get_food_samples($food_id_array){
          return  $this->db->select('*')->from('food_sample')
                     ->where_in('id_food',$food_id_array)
                     ->get()->result();
        }
        public function update_food_status($food_id,$status){
              $data = array(
                 'status' => $status
              );
             $this->db->where(array('id' => $food_id));
             $this->db->update('food_type', $data);
        }

        public function get_sleep_chart(){
            return  $this->db->select('*')->from('sleep_chart_data')->get()->result();
        }
        public function get_sleep_chart_baby($child_id){
            return $this->db->select('age_months,avg(amount)as amount')->from('sleep_sample')
              ->where(array('id_child'=>$child_id))->group_by('age_months')->get()->result();
        }
        public function get_done_skills($child_id){
             $q = $this->db
             ->select("skills.id,skills.age_from,skills.skill,skills.age_to,skills.kind,child_skills.id_skills,child_skills.date,child_skills.age_months,child_skills.id_child, child_skills.rate")
             ->from("child_skills")
             ->join("skills","child_skills.id_skills = skills.id")
             ->where("child_skills.id_child",$child_id)
             ->order_by("skills.age_from","ASC")
             ->get()->result();

             return $q;
            //  echo "<pre>";
            //  print_r($q);
            //  echo "</pre>";
            //  die();
          }
        public function get_not_done_skills($child_id){
           $q = $this->db
           ->select("skills.id,skills.age_from,skills.skill,skills.age_to,skills.kind,child_skills.id_skills,child_skills.date,child_skills.age_months")
           ->from("child_skills")
           ->join("skills","child_skills.id_skills != skills.id")
           ->where("child_skills.id_child",$child_id)
           ->order_by("skills.age_from","ASC")
           ->get()->result();

           if(empty($q)){
             $q2 = $this->db
             ->select("*")
             ->from("skills")
             ->order_by("skills.age_from","ASC")
             ->get()->result();
             return $q2;

           }

           return $q;


        }

        public function get_vaccination_list($doctor_id){
           $q = $this->db
           ->select("vaccination.name,vaccination_adv.id")
           ->from("register_for_vaccination")
           ->join("vaccination_adv","register_for_vaccination.id_adv = vaccination_adv.id")
           ->join("vaccination","vaccination_adv.id_vaccination = vaccination.id")
           ->where("vaccination_adv.id_doctor",$doctor_id)
           ->order_by("vaccination.name","ASC")
           ->distinct()->get()->result();

           return $q;

          //  echo "<pre>";
          //  print_r($q);
          //  echo "</pre>";
          //  die();
        }
        public function get_vaccination($vac_id){
          $q = $this->db
          ->select("vaccination.name,vaccination_adv.id")
          ->from("vaccination_adv")
          ->join("vaccination","vaccination.id = vaccination_adv.id_vaccination")
          ->where("vaccination_adv.id",$vac_id)
          ->get()->result();

          return $q;
        }

        public function get_taken_vaccination($child_id){
            $q = $this->db
            ->select("*")
            ->from("child_vaccination")
            ->join("vaccination","child_vaccination.id_vaccination = vaccination.id")
            ->where("child_vaccination.id_child",$child_id)
            ->where("child_vaccination.state",'taken')
            ->get()->result();

            return $q;
        }

        public function get_not_taken_vaccination($child_id){
          $q = $this->db
          ->select("child_vaccination.id_vaccination")
          ->from("child_vaccination")
          ->join("vaccination","child_vaccination.id_vaccination = vaccination.id")
          ->where("child_vaccination.id_child",$child_id)
          ->where("child_vaccination.state",'taken')
          ->get()->result();

          $taken_id = array();
          foreach ($q as $key) {
              array_push($taken_id,$key->id_vaccination);
          }

          if(sizeof($taken_id) != 0){
              $q2 = $this->db
              ->select("*")
              ->from("vaccination")
              ->where_not_in("vaccination.id",$taken_id)
              ->get()->result();


              return $q2;
            }
            else {
              $q2 = $this->db
              ->select("*")
              ->from("vaccination")
              ->get()->result();


              return $q2;
            }

        }

        public function get_requasted_vaccination($child_id){
            $q = $this->db
            ->select("*")
            ->from("register_for_vaccination")
            ->join("vaccination_adv","register_for_vaccination.id_adv = vaccination_adv.id")
            ->join("vaccination","vaccination_adv.id_vaccination = vaccination.id")
            ->where("register_for_vaccination.id_child",$child_id)
            ->get()->result();

            return $q;
        }
        public function get_vac_ad_token($doctor_id){

          // doctor children
          $q = $this->db
          ->select("child_parents.id_parent")
          ->from("child_doctors")
          ->join("child","child.id = child_doctors.id_child")
          ->join("child_parents","child.id =  child_parents.id_child")
          ->where("child_doctors.id_doctor",$doctor_id)
          ->where("child_doctors.approved",1)
          ->get()->result_array();

          $result = array();
          foreach($q as $arr){
            foreach($arr as $row){
              array_push($result, $row);
            }
          }

          $result = array_unique($result);




            $q2 = $this->db
            ->select("gcm_registeredid.id_user,gcm_registeredid.token")
            ->from("gcm_registeredid")
            ->where_in("gcm_registeredid.id_user",$result)
            ->distinct()->get()->result();



            return $q2;
        }
        public function get_child_token($doctor_id,$child_id){
            $q = $this->db
            ->select("gcm_registeredid.id_user,gcm_registeredid.token")
            ->from("users")
            ->join("child_doctors","child_doctors.id_doctor = users.id")
            ->join("child_parents","child_doctors.id_child = child_parents.id_child")
            ->join("gcm_registeredid","gcm_registeredid.id_user = child_parents.id_parent")
            ->where("users.id",$doctor_id)
            ->where("child_parents.id_child",$child_id)
            ->get()->result();

            return $q;
        }

        public function add_vac_ad_notification($creator_user_id,$additional_info,$type_id,$title,$text,$img,$child_id){

              $data = array(
                 'creator_user_id' => $creator_user_id ,
                 'additional_data' => $additional_info ,
                 'type_id' => $type_id,
                 'creation_date' => date("Y-m-d H:i:s"),
                 'title' => $title,
                 'text' => $text,
                 'img' => $img,
              );
              $this->db->insert('notification', $data);
              $insert_id = $this->db->insert_id();

              $rec_ids = $this->db
              ->select("child_parents.id_parent,child_parents.id_child")
              ->from("users")
              ->join("child_doctors","child_doctors.id_doctor = users.id")
              ->join("child_parents","child_doctors.id_child = child_parents.id_child")
              ->where("users.id",$creator_user_id)
              ->where("child_parents.id_child",$child_id)
              ->get()->result();

            foreach ($rec_ids as $key) {
              $data2 = array(
                 'notification_id' => $insert_id ,
                 'recipient_user_id' => $key->id_parent ,
                 'id_child' => $key->id_child,
              );

              $this->db->insert('notification_recipieant', $data2);
            }

            return $insert_id;
        }

        public function add_child_notification($creator_user_id,$additional_info,$type_id,$title,$text,$img,$child_id){

              $data = array(
                 'creator_user_id' => $creator_user_id ,
                 'additional_data' => $additional_info ,
                 'type_id' => $type_id,
                 'creation_date' => date("Y-m-d H:i:s"),
                 'title' => $title,
                 'text' => $text,
                 'img' => $img,
              );
              $this->db->insert('notification', $data);
              $insert_id = $this->db->insert_id();



              $rec_ids = $this->db
              ->select("child_parents.id_parent,child_parents.id_child")
              ->from("users")
              ->join("child_doctors","child_doctors.id_doctor = users.id")
              ->join("child_parents","child_doctors.id_child = child_parents.id_child")
              ->where("users.id",$creator_user_id)
              ->where("child_parents.id_child",$child_id)
              ->get()->result();

            foreach ($rec_ids as $key) {
              $data2 = array(
                 'notification_id' => $insert_id ,
                 'recipient_user_id' => $key->id_parent ,
                 'id_child' => $key->id_child,
              );

              $this->db->insert('notification_recipieant', $data2);
            }

            return $insert_id;
        }

        public function get_vac_name($vac_adv){
          $q = $this->db
          ->select("vaccination.name")
          ->from("vaccination_adv")
          ->join("vaccination","vaccination_adv.id_vaccination = vaccination.id")
          ->where("vaccination_adv.id",$vac_adv)
          ->get()->result()[0]->name;

         return $q;

        }
        public function get_vac_name_from_id($vac_id){
          $q = $this->db
          ->select("vaccination.name")
          ->from("vaccination")
          ->where("vaccination.id",$vac_id)
          ->get()->result()[0]->name;

         return $q;

        }

        public function get_message_by_type($type_id){
          return $this->db->select("*")->from('notification_type')->where('id',$type_id)->get()->result();
        }

        public function get_notification($noti_id,$type_id){

          $q = $this->db
                ->select('notification.id as id,id_child,notification.title,notification.text,img,additional_data,is_seen,creation_date,creator_user_id,notification_type.name as type,notification_recipieant.recipient_user_id')
                ->from('notification')
                ->join('notification_recipieant','notification_recipieant.notification_id = notification.id ')
                ->join('notification_type','notification_type.id = notification.type_id')
                ->where('notification.id',$noti_id)
                ->where('notification_type.id',$type_id)
                ->get()->result();

                return $q;
        }


        public function add_vac_requast($doc_id,$child_id,$vac_id){
          $data = array(
            'id_doctor' => $doc_id,
            'id_child' => $child_id,
            'id_vaccination' => $vac_id,
            'date' => date("Y-m-d H:i:s"),
            'state' => 'pending',
          );
            $this->db->insert("child_vaccination",$data);
            $insert_id = $this->db->insert_id();
            return $insert_id;

        }

        public function get_all_notifications($user_id){
            $q = $this->db
            ->select("*")
            ->from("notification_recipieant")
            ->join("notification","notification.id = notification_recipieant.notification_id")
            ->where("notification_recipieant.recipient_user_id",$user_id)
            ->get()->result();

            return $q;
        }

        public function notifications_seen($user_id){
            $data['is_seen'] = 1;
            $this->db->update('notification_recipieant',$data,array('recipient_user_id'=>$user_id));
        }


        public function get_all_notifications_count($user_id){

            $q = $this->db
            ->select("notification_recipieant.notification_id, count(*) as count")
            ->from("notification_recipieant")
            ->join("notification","notification.id = notification_recipieant.notification_id")
            ->where("notification_recipieant.recipient_user_id",$user_id)
            ->where("notification_recipieant.is_seen",0)
            ->group_by('notification_recipieant.notification_id')
            ->get()->result();

            if(isset($q[0]->count))
            return $q[0]->count;
            else
              return 0;



          }

        public function get_vac_add_info($id_ad){
          return $this->db->select('*')->from('vaccination_adv')->where('id',$id_ad)->get()->row();
        }

        public function get_doctor_childs($doc_id){
              $q = $this->db
              ->select("*")
              ->from("child_doctors")
              ->join("child","child.id = child_doctors.id_child")
              ->where("child_doctors.id_doctor",$doc_id)
              ->where("child_doctors.approved",1)
              ->get()->result();

              return $q;
          }

          public function get_doctor_parents($doc_id){
                  $q = $this->db
                  ->select("child_parents.id_parent")
                  ->from("child_doctors")
                  ->join("child","child.id = child_doctors.id_child")
                  ->join("child_parents","child.id =  child_parents.id_child")
                  ->where("child_doctors.id_doctor",$doc_id)
                  ->where("child_doctors.approved",1)
                  ->get()->result_array();

                  $result = array();
                  foreach($q as $arr){
                    foreach($arr as $row){
                      array_push($result, $row);
                    }
                  }

                  $result = array_unique($result);

                  return $result;
            }

          public function get_doctor_reviews($doc_id){
                $q = $this->db
                ->select("AVG(waiting_time) as waiting_time ,AVG(office_cleanliness) as office_cleanliness,AVG(explination_clarity) as explination_clarity,Count(*) as rev_count")
                ->from("patient_review")
                ->where("patient_review.id_doctor",$doc_id)
                ->get()->result();


                return $q;
            }

            public function get_doctor_childs_dates($doc_id){
                  $q = $this->db
                  ->select("count(*) as count, child_doctors.approved_date")
                  ->from("child_doctors")
                  ->join("child","child.id = child_doctors.id_child")
                  ->where("child_doctors.id_doctor",$doc_id)
                  ->where("child_doctors.approved",1)
                  ->group_by('approved_date')
                  ->get()->result();


                  return $q;
              }


              public function get_main_sections(){
                    return $this->db->select('*')->from('main_section')->get()->result();

              }
              public function vac_taken($doctor_id,$child_id,$id_vac){

                $data = array(
                   'id_doctor' => $doctor_id ,
                   'id_child' => $child_id ,
                   'id_vaccination' => $id_vac,
                   'state' =>'taken',
                   'date' =>date("Y-m-d")

                );

                $this->db->insert('child_vaccination', $data);
              }

    }
