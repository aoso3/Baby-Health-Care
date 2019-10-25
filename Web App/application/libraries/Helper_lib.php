<?php

  class Helper_lib
  {
          protected $ci;
          function __construct() {
              $this->ci = &get_instance();
              $this->ci->load->model('users_model');
          }
          public function get_current_lang(){
              //tofix : get lang from user;
              // return 'ar';
              return 'en';
            }

          public function get_lang_string($message_name){
                return $this->ci->lang->line($message_name);
            }

            public function format_msg($msg,$variabel,$value,$multiple=false) {

                if(!$multiple){
                    if (strpos($msg,$variabel) != false) {
                        $msg=str_replace($variabel, $value, $msg);
                    }
                    return $msg;
                }
                else{
                    if(sizeof($variabel)==sizeof($value)){
                        for ($i=0; $i < sizeof($variabel); $i++) {
                            if(strpos($msg, $variabel[$i]) != false)
                                $msg=str_replace($variabel[$i], $value[$i], $msg);
                        }
                        return $msg;
                    }
                    else
                        return false;
                }
            }



   }
