<?php  if ( ! defined('BASEPATH')) exit('No direct script access allowed');

  class Users_model extends CI_Model
    {
          protected $ci;
          public function __construct() {
                  parent::__construct();
                  $this->ci = &get_instance();
              }

         public function user_info($user_id){
           return $this->db->select('*')->from('users')->where('id',$user_id)->get()->row();
         }

         public function user_role($user_id){
               $id_role = $this->db->select('id_role')->from('users')->where('id',$user_id)->get()->row()->id_role;
               $user_role = $this->db->select('name')->from('user_role')->where('id',$id_role)->get()->row()->name;

               return $user_role;
         }
         public function child_info($child_id){
           return $this->db->select('*')->from('child')->where('id',$child_id)->get()->row();
         }

         






    }
