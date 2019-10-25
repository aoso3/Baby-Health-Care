<?php

  class Users_lib
  {
            protected $ci;
            function __construct(){
                $this->ci = &get_instance();
                $this->ci->load->model('users_model');
                // if($this->ci->helper_lib->get_current_lang() == "en")
                //  $this->ci->lang->load('profile_lang', 'english');
                // else if($this->ci->helper_lib->get_current_lang() == "ar")
                //  $this->ci->lang->load('profile_lang', 'arabic');

            }

            public function user_info($user_id){
                return $this->ci->users_model->user_info($user_id);
            }

            public function user_role($user_id){
              return $this->ci->users_model->user_role($user_id);
            }

            public function child_info($child_id){
                return $this->ci->users_model->child_info($child_id);
            }

          



   }
