<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Admin_Dashboard extends CI_Controller {

  function __construct()
		{
			parent::__construct();
      $this->load->library('xcrud/xcrud.php');
      $this->load->library('users_lib');
      $this->lang->load('auth');
		}

	 function index()
		{
      $this->users();
		}

	 function users(){
     if($this->ion_auth->logged_in()){
     $current_user_id = $this->ion_auth->get_user_id();
     $user_role = $this->users_lib->user_role($current_user_id);
     if($user_role == "admin"){
             $xcrud = Xcrud::get_instance();
             $xcrud->language('en');
        		 $xcrud->table('users');
             $xcrud->load_view('create','my_custom_create_form.php');
             $xcrud->table_name('Usres');
             $xcrud->unset_remove(true,'id','=','1'); // 'admin' row can't be removable
             //$xcrud->unset_edit(true,'id','=','1'); // 'admin' row can't be editable
             $xcrud->unset_add();
             $xcrud->highlight_row('id_role', '=', 1, '#a9e9a6');
             $xcrud->highlight_row('id_role','=', 2, '#b9dbf4');
             $xcrud->change_type('ip_address','hidden');
             $xcrud->change_type('username', 'text', '', 50);
             $xcrud->change_type('password', 'hidden');
             $xcrud->change_type('salt','hidden');
             $xcrud->change_type('email','text','',50);
             $xcrud->change_type('activation_code','hidden');
             $xcrud->change_type('forgotten_password_code','hidden');
             $xcrud->change_type('forgotten_password_time','hidden');
             $xcrud->change_type('remember_code','hidden');
             $xcrud->change_type('created_on', 'datetime');
             $xcrud->change_type('last_login','hidden');
             $xcrud->change_type('active','checkbox');
             $xcrud->change_type('first_name', 'text', '', 50);
             $xcrud->change_type('last_name', 'text', '', 50);
             $xcrud->change_type('company', 'text', '', 50);
             $xcrud->change_type('phone', 'int', '', 30);
             $xcrud->change_type('birth_date', 'datetime');
             $xcrud->change_type('pic', 'image', false, array(
               'manual_crop'=>true,
               'width' => 450,
               'path'=>  '../../../assets/images/users',
               'thumbs' => array(array(
                       'height' => 55,
                       'width' => 120,
                       'crop' => true,
                       'marker' => '_th'))));

            $xcrud->modal('pic');
            $xcrud->column_class('pic', 'align-center');
            $xcrud->change_type('location','point','39.909736,-6.679687',array('text'=>'Your are here'));

            $xcrud->validation_required(array(
                'username' => 3,
                'email' => 5,
                'first_name' => 3,
                'last_name' => 3,
                'birth_date',
                'location'
              ));
            $xcrud->validation_pattern(array(
                'email' => 'email',
              ));


             $xcrud->relation('id_role','user_role','id','name');

             $xcrud->benchmark();
        		 $data['xcrud'] = $xcrud->render();

             $data['user_info'] = $this->users_lib->user_info($current_user_id);
             $data['admin_dashboard'] = 1;
             $data['admin_users'] = 1;
        		 $data['main_content'] = 'Admin_panel/Table_Template';
        		 $this->load->view('include/main',$data);
          }
          else{
             echo "only Admins can view this page.";
          }
        }
      else{
        $data['main_content'] = 'auth';
    		$this->load->view('include/main',$data);
      }
	 }

   function users_roles(){

     if($this->ion_auth->logged_in()){
     $current_user_id = $this->ion_auth->get_user_id();
     $user_role = $this->users_lib->user_role($current_user_id);
     if($user_role == "admin"){
      		 $xcrud = Xcrud::get_instance();
           $xcrud->language('en');
      		 $xcrud->table('user_role');
           $xcrud->table_name('Usres Rols');
           $xcrud->change_type('name', 'text', '', 20);
           $xcrud->validation_required(array(
              'name' => 3,
            ));
           $xcrud->benchmark();
      		 $data['xcrud'] = $xcrud->render();

           $data['user_info'] = $this->users_lib->user_info($current_user_id);
           $data['admin_dashboard'] = 1;
           $data['admin_users_roles'] = 1;
      		 $data['main_content'] = 'Admin_panel/Table_Template';
      		 $this->load->view('include/main',$data);
        }
      }
    else{
      $data['main_content'] = 'auth';
     $this->load->view('include/main',$data);
    }
	 }

   function sections_names(){
     if($this->ion_auth->logged_in()){
     $current_user_id = $this->ion_auth->get_user_id();
     $user_role = $this->users_lib->user_role($current_user_id);
     if($user_role == "admin"){
    		 $xcrud = Xcrud::get_instance();
         $xcrud->language('en');
    		 $xcrud->table('main_section');
         $xcrud->table_name('Sections Names');
         $xcrud->change_type('name', 'text', '', 50);
         $xcrud->change_type('web_icon', 'image', false, array(
           'manual_crop'=>true,
           'width' => 450,
           'path'=>  '../../../assets/images/main_sections_icons',
           'thumbs' => array(array(
                   'height' => 55,
                   'width' => 120,
                   'crop' => true,
                   'marker' => '_th'))));

         $xcrud->modal('web_icon');
         $xcrud->column_class('web_icon', 'align-center');

         $xcrud->change_type('android_app_icon', 'image', false, array(
           'manual_crop'=>true,
           'width' => 450,
           'path'=>  '../../../assets/images/main_sections_icons',
           'thumbs' => array(array(
                   'height' => 55,
                   'width' => 120,
                   'crop' => true,
                   'marker' => '_th'))));

         $xcrud->modal('android_app_icon');
         $xcrud->column_class('android_app_icon', 'align-center');

         $xcrud->column_class('description', 'align-center');
         $xcrud->modal('description');


         $xcrud->validation_required(array(
            'name' , 'description','android_app_icon','web_icon'
          ));
         $xcrud->unset_add();


         $xcrud->benchmark();
    		 $data['xcrud'] = $xcrud->render();

         $data['user_info'] = $this->users_lib->user_info($current_user_id);
         $data['admin_dashboard'] = 1;
         $data['admin_sections_names'] = 1;
    		 $data['main_content'] = 'Admin_panel/Table_Template';
    		 $this->load->view('include/main',$data);
        }
        else{
           echo "only Admins can view this page.";
        }
      }
    else{
      $data['main_content'] = 'auth';
     $this->load->view('include/main',$data);
    }
	 }

   function section_guids(){
     if($this->ion_auth->logged_in()){
           $current_user_id = $this->ion_auth->get_user_id();
           $user_role = $this->users_lib->user_role($current_user_id);
     if($user_role == "admin"){
            $age = array();
            for($i=1;$i<=24;$i++)
            array_push($age, $i." Months");
            for($i=3;$i<=18;$i++)
            array_push($age, $i." Years");

        		 $xcrud = Xcrud::get_instance();
             $xcrud->language('en');
        		 $xcrud->table('guid');
             $xcrud->table_name('Sections Guid');
             $xcrud->change_type('age', 'select', '',$age);
             $xcrud->modal('description', 'glyphicon glyphicon-eye-open');
             $xcrud->column_class('description', 'align-center');
             $xcrud->relation('id_main_section','main_section','id','name');
             $xcrud->validation_required(array(
               'id_main_section' ,
               'description' ,
               'age' ,
              ));
              $xcrud->column_class('description', 'align-center');
              $xcrud->modal('description');


             $xcrud->benchmark();
        		 $data['xcrud'] = $xcrud->render();

             $data['user_info'] = $this->users_lib->user_info($current_user_id);
             $data['admin_dashboard'] = 1;
             $data['admin_section_guids'] = 1;
        		 $data['main_content'] = 'Admin_panel/Table_Template';
        		 $this->load->view('include/main',$data);
       }
       else{
          echo "only Admins can view this page.";
       }
     }
   else{
     $data['main_content'] = 'auth';
 		$this->load->view('include/main',$data);
   }

	 }


       function children(){
         if($this->ion_auth->logged_in()){
         $current_user_id = $this->ion_auth->get_user_id();
         $user_role = $this->users_lib->user_role($current_user_id);
         if($user_role == "admin"){
                 $xcrud = Xcrud::get_instance();
                 $xcrud->language('en');
                 $xcrud->table('child');
                 $xcrud->table_name('children');
                 $xcrud->change_type('first_name', 'text', '', 50);
                 $xcrud->change_type('last_name', 'text', '', 50);
                 $xcrud->change_type('birth_date', 'datetime');
                 $xcrud->change_type('pic', 'image', false, array(
                   'manual_crop'=>true,
                   'width' => 450,
                   'path'=>  '../../../assets/images/baby_profile',
                   'thumbs' => array(array(
                           'height' => 55,
                           'width' => 120,
                           'crop' => true,
                           'marker' => '_th'))));

                $xcrud->modal('pic');
                $xcrud->column_class('pic', 'align-center');


                $xcrud->validation_required(array(
                    'first_name' => 3,
                    'last_name' => 3,
                    'birth_date',
                  ));
                $xcrud->validation_pattern(array(
                    'email' => 'email',
                  ));

                //  $xcrud->unset_add();
                //  $xcrud->unset_edit();
                 $xcrud->benchmark();
                 $data['xcrud'] = $xcrud->render();

                 $data['user_info'] = $this->users_lib->user_info($current_user_id);
                 $data['admin_dashboard'] = 1;
                 $data['children'] = 1;
                 $data['main_content'] = 'Admin_panel/Table_Template';
                 $this->load->view('include/main',$data);
              }
              else{
                 echo "only Admins can view this page.";
              }
            }
          else{
            $data['main_content'] = 'auth';
            $this->load->view('include/main',$data);
          }
       }


              function vaccination(){
                if($this->ion_auth->logged_in()){
                $current_user_id = $this->ion_auth->get_user_id();
                $user_role = $this->users_lib->user_role($current_user_id);
                if($user_role == "admin"){
                        $xcrud = Xcrud::get_instance();
                        $xcrud->language('en');
                        $xcrud->table('vaccination');
                        $xcrud->table_name('vaccinations');


                       $xcrud->validation_required(array(
                           'name',
                           'age_from',
                           'age_to',
                           'description'
                         ));
                         $xcrud->column_class('description', 'align-center');
                         $xcrud->modal('description');



                       //  $xcrud->unset_add();
                       //  $xcrud->unset_edit();
                        $xcrud->benchmark();
                        $data['xcrud'] = $xcrud->render();

                        $data['user_info'] = $this->users_lib->user_info($current_user_id);
                        $data['admin_dashboard'] = 1;
                        $data['vaccination'] = 1;
                        $data['main_content'] = 'Admin_panel/Table_Template';
                        $this->load->view('include/main',$data);
                     }
                     else{
                        echo "only Admins can view this page.";
                     }
                   }
                 else{
                   $data['main_content'] = 'auth';
                   $this->load->view('include/main',$data);
                 }
              }

              function sections_details(){
                if($this->ion_auth->logged_in()){
                $current_user_id = $this->ion_auth->get_user_id();
                $user_role = $this->users_lib->user_role($current_user_id);
                if($user_role == "admin"){
                        $xcrud = Xcrud::get_instance();
                        $xcrud->language('en');
                        $xcrud->table('main_section_details');
                        $xcrud->table_name('Sections Guide');

                        $xcrud->validation_required(array(
                           'name',
                           'icon',
                           'id_main_section',
                           'description'
                         ));
                         $xcrud->change_type('icon', 'image', false, array(
                           'manual_crop'=>true,
                           'width' => 450,
                           'path'=>  '../../../assets/images/main_sections_details_icons',
                           'thumbs' => array(array(
                                   'height' => 55,
                                   'width' => 120,
                                   'crop' => true,
                                   'marker' => '_th'))));

                         $xcrud->modal('icon');
                         $xcrud->column_class('icon', 'align-center');

                         $xcrud->column_class('description', 'align-center');
                         $xcrud->modal('description');
                         $xcrud->relation('id_main_section','main_section','id','name');

                       //  $xcrud->unset_add();
                       //  $xcrud->unset_edit();
                        $xcrud->benchmark();
                        $data['xcrud'] = $xcrud->render();

                        $data['user_info'] = $this->users_lib->user_info($current_user_id);
                        $data['admin_dashboard'] = 1;
                        $data['details'] = 1;
                        $data['main_content'] = 'Admin_panel/Table_Template';
                        $this->load->view('include/main',$data);
                     }
                     else{
                        echo "only Admins can view this page.";
                     }
                   }
                 else{
                   $data['main_content'] = 'auth';
                   $this->load->view('include/main',$data);
                 }
              }

                function sections_guide(){
                  if($this->ion_auth->logged_in()){
                  $current_user_id = $this->ion_auth->get_user_id();
                  $user_role = $this->users_lib->user_role($current_user_id);
                  if($user_role == "admin"){
                          $xcrud = Xcrud::get_instance();
                          $xcrud->language('en');
                          $xcrud->table('guide');
                          $xcrud->table_name('Sections Guide');

                          $xcrud->validation_required(array(
                             'id_main_section',
                             'age',
                             'description'
                           ));
                           $xcrud->relation('id_main_section','main_section','id','name');
                           $xcrud->modal('description');



                         //  $xcrud->unset_add();
                         //  $xcrud->unset_edit();
                          $xcrud->benchmark();
                          $data['xcrud'] = $xcrud->render();

                          $data['user_info'] = $this->users_lib->user_info($current_user_id);
                          $data['admin_dashboard'] = 1;
                          $data['guide'] = 1;
                          $data['main_content'] = 'Admin_panel/Table_Template';
                          $this->load->view('include/main',$data);
                       }
                       else{
                          echo "only Admins can view this page.";
                       }
                     }
                   else{
                     $data['main_content'] = 'auth';
                     $this->load->view('include/main',$data);
                   }
                }

}
