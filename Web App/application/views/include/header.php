<!DOCTYPE html>
<html>
<head>

     <meta http-equiv="Content-Type" cntent="charset=utf-8"/>
  	 <meta http-equiv="X-UA-Compatible" content="IE=edge">
     <meta name="viewport" content="width=device-width, initial-scale=1">
     <meta name="author" content="usama albaghdady,amal alnouri,raodh sandouk">
     <meta name="keywords" content="baby,health,care">

     <title>BHC</title>
     <link rel='stylesheet prefetch' href='http://fonts.googleapis.com/css?family=Roboto:400,100,300,500,700,900'>
     <link rel='stylesheet prefetch' href='http://fonts.googleapis.com/css?family=Montserrat:400,700'>
     <link rel="icon" type="image/ico" sizes="32x32" href="<?php echo base_url('assets/images/doctor.ico') ?>">
     <link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/css/bootstrap.min.css')?>">
     <link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/css/font-awesome.css') ?>">
     <link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/css/jquery_ui_theme.css')?>">
     <link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/css/validationEngine.jquery.css')?>">
     <link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/css/style.css')?>">
     <link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/css/color-sheet1.css')?>">


     <?php if ($main_content == 'auth'): ?>
       <link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/css/auth.css')?>">
      <?php endif; ?>

      <?php if (isset($user_profile)): ?>
        <link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/css/users/view-profile.css')?>">
       <?php endif; ?>

      <?php if (isset($patients_main_panel)): ?>
        <link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/css/doctor_dashboard/patients_main_panel/ihover.min.css')?>">
        <link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/css/doctor_dashboard/patients_main_panel/elastic-svg-elements/css/dropdown.css')?>">
        <link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/css/doctor_dashboard/patients_main_panel/elastic-svg-elements/css/normalize.css')?>">
        <link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/css/doctor_dashboard/patients_main_panel/baby-profile.css')?>">
        <link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/css/doctor_dashboard/patients_main_panel/button_component.css')?>">
        <link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/css/doctor_dashboard/patients_main_panel/style-responsive.css')?>">
       <?php endif; ?>


     <?php if(!isset($patients_main_panel) && !isset($auth)): ?>

       <link href="<?php echo base_url('assets/css/doctor_dashboard/style_responsive.css')?>" rel="stylesheet">
       <link href="<?php echo base_url('assets/css/lineicons.css')?>" rel="stylesheet">
       <script src="<?php echo base_url('assets/js/doctor_dashboard/Chart.js') ?>"></script>

       <section id="container" >
           <!-- **********************************************************************************************************************************************************
           TOP BAR CONTENT & NOTIFICATIONS
           *********************************************************************************************************************************************************** -->
           <!--header start-->
           <header class="header black-bg">
                   <div class="sidebar-toggle-box">
                       <div class="fa fa-bars tooltips" data-placement="right" data-original-title="Toggle Navigation"></div>
                   </div>
                 <!--logo start-->
                 <a href="index.html" class="logo" id="oopp"><b>Baby Health Care</b></a>
                 <!--logo end-->
                 <div class="nav notify-row" id="top_menu">
                   <?php if(isset($notification)): ?>
                     <!--  notification start -->
                     <ul class="nav notify-row-list">
                         <!-- inbox dropdown start-->
                         <li id="header_inbox_bar" class="dropdown">
                             <a data-toggle="dropdown" class="dropdown-toggle" href="index.html#">
                                  <i class="seen fa fa-bell-o fa-lg"></i>
                                 <span id="noti_count" class="badge bg-theme"><?php echo $notification_count ?></span>
                             </a>
                             <ul id="socket_notification" class="dropdown-menu extended inbox"  style="overflow:auto;height:400px;">
                                 <!-- <div class="notify-arrow notify-arrow-green"></div> -->
                                 <!-- <li>
                                     <p class="green">You have <?php echo count($notification) ?> new messages</p>
                                 </li> -->

                                 <?php foreach($notification as $noti):?>
                                 <li>
                                     <a href='<?php if($noti->type_id==1){echo base_url("doctor_dashboard/Vaccination_child/$noti->additional_data");}else if($noti->type_id==2){echo base_url("doctor_dashboard/patients_approval");}  ?>'>
                                         <span class="photo"><img alt="avatar" src='<?php echo base_url("assets/images/notification/$noti->img") ?>'></span>
                                         <span >
                                         <strong><span class="from"> <?php echo $noti->title ?></span></strong>
                                         <span class="time"><?php echo $noti->creation_date ?></span>
                                         </span>
                                         <span class="message" >
                                             <?php echo $noti->text ?>
                                         </span>
                                     </a>
                                 </li>
                               <?php endforeach; ?>
                             </ul>
                         </li>
                         <!-- inbox dropdown end -->
                     </ul>
                   <?php endif; ?>

                     <!--  notification end -->
                 </div>
                 <div class="top-menu">
                  <ul class="nav pull-right top-menu">
                         <li><a class="logout" href="#">Logout</a></li>
                 	</ul>
                 </div>
             </header>
           <!--header end-->

           <!-- **********************************************************************************************************************************************************
           MAIN SIDEBAR MENU
           *********************************************************************************************************************************************************** -->
           <!--sidebar start-->
           <aside>
                <div id="sidebar"  class="nav-collapse ">
                    <!-- sidebar menu start-->
                    <ul class="sidebar-menu" id="nav-accordion">

                       <p class="centered"><a ><img src="<?php if($user_info->pic=='user.png') {echo base_url('assets/images/defuelt/user.png');} else{echo base_url('assets/images/users').'/'.$user_info->pic;} ?>" class="img-circle" width="60"></a></p>
                       <h5 class="centered">
                         <?php
                           echo "$user_info->first_name $user_info->last_name";
                          ?>
                       </h5>

                      <?php if(isset($doctor_dashboard)):  ?>
                       <li class="mt">
                           <a class="<?php if (isset($doctor_dashboard_index)){ echo 'active';} ?>" href="<?php echo base_url('doctor_dashboard'); ?>">
                               <i class="fa fa-dashboard"></i>
                               <span>Dashboard</span>
                           </a>
                       </li>
                     <?php endif; ?>

                        <?php if(isset($admin_dashboard)):  ?>
                          <li class="sub-menu">
                              <a class="<?php if (isset($admin_users)){ echo 'active';} ?>" href="<?php echo base_url('admin_dashboard/users'); ?>" >
                                  <i class="fa fa-users"></i>
                                  <span href="">Users</span>
                             </a>
                          </li>
                        <?php endif; ?>
                        <?php if(isset($doctor_dashboard)):  ?>
                          <li class="sub-menu">
                              <a class="<?php if (isset($doctor_patients_approval)){ echo 'active';} ?>" href="<?php echo base_url('doctor_dashboard/patients_approval'); ?>" >
                                  <i class="fa fa-thumbs-o-up"></i>
                                  <span href="">Patients Approval</span>
                             </a>
                          </li>
                        <?php endif; ?>

                        <!-- <?php if(isset($admin_dashboard)):  ?>
                            <li class="sub-menu">
                                <a class="<?php if (isset($admin_users_roles)){ echo 'active';} ?>" href="<?php echo base_url('admin_dashboard/users_roles'); ?>" >
                                    <i class="fa fa-cogs"></i>
                                    <span>Users Roles</span>
                                </a>
                            </li>
                        <?php endif; ?> -->
                        <?php if(isset($admin_dashboard)):  ?>
                            <li class="sub-menu">
                                <a class="<?php if (isset($children)){ echo 'active';} ?>" href="<?php echo base_url('admin_dashboard/children'); ?>" >
                                    <i class="glyphicon glyphicon-eye-open"></i>
                                    <span>Children</span>
                                </a>
                            </li>
                        <?php endif; ?>
                        <?php if(isset($admin_dashboard)):  ?>
                            <li class="sub-menu">
                                <a class="<?php if (isset($vaccination)){ echo 'active';} ?>" href="<?php echo base_url('admin_dashboard/vaccination'); ?>" >
                                    <i class="glyphicon glyphicon-tasks"></i>
                                    <span>Vaccination</span>
                                </a>
                            </li>
                        <?php endif; ?>
                        <?php if(isset($doctor_dashboard)):  ?>
                            <li class="sub-menu">
                                <a class="<?php if (isset($doctor_patients)){ echo 'active';} ?>" href="<?php echo base_url('doctor_dashboard/patients'); ?>" >
                                    <i class="fa fa-child"></i>
                                    <span>Patients</span>
                                </a>
                            </li>
                        <?php endif; ?>

                        <?php if(isset($admin_dashboard)):  ?>
                            <li class="sub-menu">
                              <a class="<?php if (isset($admin_sections_names)){ echo 'active';} ?>" href="<?php echo base_url('admin_dashboard/sections_names'); ?>" >
                                    <i class="glyphicon glyphicon-xbt"></i>
                                    <span>Sections</span>
                                </a>
                            </li>
                       <?php endif; ?>
                       <?php if(isset($doctor_dashboard)):  ?>
                           <li class="sub-menu">
                               <a class="<?php if (isset($Vaccination_adv)){ echo 'active';} ?>" href="<?php echo base_url('doctor_dashboard/Vaccination_adv'); ?>" >
                                   <i class="fa fa-rss"></i>
                                   <span>Vaccination Advertisements</span>
                               </a>
                           </li>
                       <?php endif; ?>


                        <?php if(isset($doctor_dashboard)):  ?>
                            <li class="sub-menu">
                                <a class="<?php if (isset($Vaccination_child)){ echo 'active';} ?>" href="<?php echo base_url('doctor_dashboard/Vaccination_child'); ?>" >
                                    <i class="fa fa-ambulance"></i>
                                    <span>Vaccination Requests</span>
                                </a>
                            </li>
                        <?php endif; ?>

                        <hr>
                        <?php if(isset($doctor_dashboard)||isset($admin_dashboard)):  ?>
                               <li class="sub-menu">
                                   <a class="<?php if (isset($user_profile)){ echo 'active';} ?>" href="<?php echo site_url('auth/edit_user') ?>">
                                       <i class="fa fa-edit"></i>
                                       <span>Edit Profile</span>
                                   </a>
                               </li>
                       <?php endif; ?>



                    </ul>
                    <!-- sidebar menu end-->
                </div>
            </aside>
           <!--sidebar end-->



     <?php endif; ?>





     <script type="text/javascript" src="<?php echo base_url('assets/js/jquery-1.10.2.js')?>"></script>
     <script type="text/javascript" src="<?php echo base_url('assets/js/jquery-ui.js')?>"></script>
     <script src="<?php echo base_url('assets/js/angular.min.js') ?>"></script>
      <script src="<?php echo base_url('assets/js/angular-route.min.js') ?>"></script>

     <script type="text/javascript" src="<?php echo base_url('assets/js/bootstrap.min.js')?>"></script>
     <script type="text/javascript" src="<?php echo base_url('assets/js/jquery.validationEngine.js')?>"></script>
     <script type="text/javascript" src="<?php echo base_url('assets/js/jquery.validationEngine-en.js')?>"></script>

     <?php if (1): ?>
       <script type="text/javascript" src="<?php echo base_url('assets/js/PictureCut/src/jquery.picture.cut.js')?>"></script>
      <?php endif; ?>

      <script src="<?php echo base_url('assets/js/doctor_dashboard/patients_main_panel/google_chart.js') ?>"></script>
      <script src="<?php echo base_url('node_modules/socket.io/node_modules/socket.io-client/socket.io.js');?>"></script>


     <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
     <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
     <!--[if lt IE 9]>
       <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
       <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
     <![endif]-->

</head>
