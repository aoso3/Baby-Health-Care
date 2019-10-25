<?php

  class Doctor_dashboard_lib
  {
            protected $ci;
            function __construct(){
                $this->ci = &get_instance();
                $this->ci->load->model('doctor_dashboard_model');
                $this->ci->load->model('users_model');
                $this->ci->load->helper('lang_helper');

                // if($this->ci->helper_lib->get_current_lang() == "en")
                //  $this->ci->lang->load('profile_lang', 'english');
                // else if($this->ci->helper_lib->get_current_lang() == "ar")
                //  $this->ci->lang->load('profile_lang', 'arabic');

            }

        public function child_info($child_id){
          return $this->ci->doctor_dashboard_model->child_info($child_id);
        }
        public function approve_child($doctor_id,$child_id){
          return $this->ci->doctor_dashboard_model->approve_child($doctor_id,$child_id);
        }
        public function get_chart_max_x(){
          return $this->ci->doctor_dashboard_model->get_chart_max_x();
        }
        public function get_char_growth_y($sex,$id_quantities){
          if($sex == 'male'){
            $s = 1;
          }
          else{
            $s = 2;
          }
          return $this->ci->doctor_dashboard_model->get_char_growth_y($s,$id_quantities);
        }
        public function add_quantity($child_id,$quantitie_id,$week,$value){
          $this->ci->doctor_dashboard_model->add_quantity($child_id,$quantitie_id,$week,$value);
        }
        public function delete_quantity($child_id,$quantitie_id,$week,$value){
          $this->ci->doctor_dashboard_model->delete_quantity($child_id,$quantitie_id,$week,$value);
        }
        public function update_quantity($id_quantitie,$value){
          $this->ci->doctor_dashboard_model->update_quantity($id_quantitie,$value);
        }
        public function get_quantity($child_id,$quantitie_id){
          return $this->ci->doctor_dashboard_model->get_quantity($child_id,$quantitie_id);
        }
        public function get_food_and_samples($child_id,$what_to_return){

          $food = $this->ci->doctor_dashboard_model->get_food($child_id);
          $food_id_array = array();
            foreach ($food as $key) {
                array_push($food_id_array, $key->id);
            }

          $food_samples =  $this->ci->doctor_dashboard_model->get_food_samples($food_id_array);


          if($what_to_return == 1)
          return $food;
          else
          return $food_samples;

        }
        public function update_food_status($food_id,$status){
          $this->ci->doctor_dashboard_model->update_food_status($food_id,$status);
        }
        public function get_sleep_chart(){
          return $this->ci->doctor_dashboard_model->get_sleep_chart();
        }

        public function get_sleep_chart_baby($child_id){
          return $this->ci->doctor_dashboard_model->get_sleep_chart_baby($child_id);
        }
        public function get_done_skills($child_id){
          $skills = $this->ci->doctor_dashboard_model->get_done_skills($child_id);
          $group_by = array();
          for($i=0;$i<=34;$i++){
            $new_arra = array();
            array_push($group_by,$new_arra);
          }

          foreach ($skills as $key ) {
            array_push($group_by[$key->age_from],$key);
          }

          //  echo "<pre>";
          //  print_r($group_by);
          //  echo "</pre>";
          //  die();

          return $group_by;



        }
        public function get_not_done_skills($child_id){
          $skills = $this->ci->doctor_dashboard_model->get_not_done_skills($child_id);
          $group_by = array();
          for($i=0;$i<=34;$i++){
            $new_arra = array();
            array_push($group_by,$new_arra);
          }

          foreach ($skills as $key ) {
            array_push($group_by[$key->age_from],$key);
          }

          return $group_by;

        }

        public function get_vaccination_list($doctor_id){
          return   $this->ci->doctor_dashboard_model->get_vaccination_list($doctor_id);
        }
        public function get_vaccination($vac_id){
          return  $this->ci->doctor_dashboard_model->get_vaccination($vac_id);
        }
        public function get_taken_vaccination($child_id){
          return  $this->ci->doctor_dashboard_model->get_taken_vaccination($child_id);
        }
        public function get_not_taken_vaccination($child_id){
          return  $this->ci->doctor_dashboard_model->get_not_taken_vaccination($child_id);
        }
        public function get_requasted_vaccination($child_id){
          return  $this->ci->doctor_dashboard_model->get_requasted_vaccination($child_id);
        }
        public function get_vac_ad_token($doctor_id){
          return $this->ci->doctor_dashboard_model->get_vac_ad_token($doctor_id);
        }
        public function get_child_approve_token($doctor_id,$child_id){
          return $this->ci->doctor_dashboard_model->get_child_approve_token($doctor_id,$child_id);
        }

        public function add_vac_ad_notification($creator_user_id,$additional_info,$type_id){
          $info = $this->ci->users_model->user_info($creator_user_id);
          $type = $this->ci->doctor_dashboard_model->get_message_by_type($type_id);
          $vac_name = $this->ci->doctor_dashboard_model->get_vac_name($additional_info);
          $doctor_name = $info->first_name ." ".$info->last_name;
          $title = $type[0]->title;
          $msg = $type[0]->text;
          $img = 'new_vac.jpg';
          $doctor_childs = $this->ci->doctor_dashboard_model->get_doctor_childs($creator_user_id);
          $adv=$this->ci->doctor_dashboard_model->get_vac_add_info($additional_info);

          $doctor_childs_in_age = array();
          $now_date = date("Y-m-d H:i:s");

          foreach ($doctor_childs as $child) {
              $child_date = $child->birth_date;
              $child_months_age = (int)abs((strtotime($child_date) - strtotime($now_date))/(60*60*24*30));

              if($child_months_age > $adv->age_from && $child_months_age < $adv->age_to )
                  array_push($doctor_childs_in_age,$child);

          }

          $notifications_array = array();
          foreach ($doctor_childs_in_age as $child) {

            $c = $this->ci->doctor_dashboard_model->child_info($child->id_child);
            $child_name = $c->first_name . " " . $c->last_name;
            $final_msg = format_msg($msg,array('$doctor_name','$vaccination_name','$child_name'),array($doctor_name,$vac_name,$child_name),true);

            $insert_id = $this->ci->doctor_dashboard_model->add_vac_ad_notification($creator_user_id,$additional_info,$type_id,$title,$final_msg,$img,$child->id_child);

            $notification = $this->ci->doctor_dashboard_model->get_notification($insert_id,$type_id);

            array_push($notifications_array,$notification);
          }


          return $notifications_array;
        }

        public function add_child_approve_notification($creator_user_id,$additional_info,$type_id,$child_id){
          $info = $this->ci->users_model->user_info($creator_user_id);
          $type = $this->ci->doctor_dashboard_model->get_message_by_type($type_id);
          $doctor_name = $info->first_name ." ".$info->last_name;
          $c = $this->ci->doctor_dashboard_model->child_info($child_id);
          $child_name = $c->first_name . " " . $c->last_name;
          $title = $type[0]->title;
          $msg = $type[0]->text;
          $img = 'approve_req.jpg';
          $final_msg = format_msg($msg,array('$doctor_name','$child_name'),array($doctor_name,$child_name),true);

          $insert_id = $this->ci->doctor_dashboard_model->add_child_notification($creator_user_id,$additional_info,$type_id,$title,$final_msg,$img,$child_id);

          return $this->ci->doctor_dashboard_model->get_notification($insert_id,$type_id);
        }

        public function add_vaccination_added_notification($creator_user_id,$additional_info,$type_id,$child_id){
          $info = $this->ci->users_model->user_info($creator_user_id);
          $type = $this->ci->doctor_dashboard_model->get_message_by_type($type_id);
          $vac_name = $this->ci->doctor_dashboard_model->get_vac_name_from_id($additional_info);
          $c = $this->ci->doctor_dashboard_model->child_info($child_id);
          $child_name = $c->first_name . " " . $c->last_name;
          $title = $type[0]->title;
          $msg = $type[0]->text;
          $img = 'vac_add_req.jpg';
          $final_msg = format_msg($msg,array('$vac_name','$child_name'),array($vac_name,$child_name),true);

          $insert_id = $this->ci->doctor_dashboard_model->add_child_notification($creator_user_id,$additional_info,$type_id,$title,$final_msg,$img,$child_id);
          return $this->ci->doctor_dashboard_model->get_notification($insert_id,$type_id);
        }

        public function add_vac_requast($doc_id,$child_id,$vac_id){
           return $this->ci->doctor_dashboard_model->add_vac_requast($doc_id,$child_id,$vac_id);
        }

        public function get_all_notifications($user_id){
           return $this->ci->doctor_dashboard_model->get_all_notifications($user_id);
        }
        public function get_all_notifications_count($user_id){
           return $this->ci->doctor_dashboard_model->get_all_notifications_count($user_id);
        }
        public function notifications_seen($user_id){
           return $this->ci->doctor_dashboard_model->notifications_seen($user_id);
        }
        public function get_child_token($doctor_id,$child_id){
           return $this->ci->doctor_dashboard_model->get_child_token($doctor_id,$child_id);
        }

        public function get_doctor_childs($doctor_id){
           return $this->ci->doctor_dashboard_model->get_doctor_childs($doctor_id);
        }
        public function get_doctor_reviews($doc_id){
           return $this->ci->doctor_dashboard_model->get_doctor_reviews($doc_id);
        }

        public function get_doctor_childs_dates($doctor_id){
           $doctor_childs = $this->ci->doctor_dashboard_model->get_doctor_childs_dates($doctor_id);

           $now_date = date("Y-m-d");
           $doctor_childs_in_age = array();

           foreach ($doctor_childs as $child) {
               $child_date = $child->approved_date;
               $child_weeks_age = (int)abs((strtotime($child_date) - strtotime($now_date))/(60*60*24*30))*4;

               $temp['count'] = $child->count;
               $temp['weeks'] = $child_weeks_age;

               array_push($doctor_childs_in_age,$temp);

           }
          //  echo "<pre>";
          //  print_r($doctor_childs_in_age);
          //  echo "</pre>";
          //  die();
          return $doctor_childs_in_age;
       }
       public function get_main_sections(){
          return $this->ci->doctor_dashboard_model->get_main_sections();
       }

       public function get_doctor_parents($doc_id){
          return $this->ci->doctor_dashboard_model->get_doctor_parents($doc_id);
       }

       public function vac_taken($doctor_id,$child_id,$id_vac){
          return $this->ci->doctor_dashboard_model->vac_taken($doctor_id,$child_id,$id_vac);
       }



   }
