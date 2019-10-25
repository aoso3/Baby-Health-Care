
  <body class="box" ng-app="app">
  <div class="continer col-xs-12" >
      <!-- **********************************************************************************************************************************************************
      TOP BAR CONTENT & NOTIFICATIONS
      *********************************************************************************************************************************************************** -->
      <!--header start-->
      <header class="header black-bg">
          <!--Toggle-->
              <div class="sidebar-toggle-box">
                <a href="<?php echo base_url('auth') ?>">
                  <div class="fa fa-arrow-left fa-lg tooltips" data-placement="right" data-original-title="Toggle Navigation"></div>
                </a>
              </div>
          <!--logo start-->
            <a href="" class="logo"><b>Baby Health Care</b></a>
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
                    <li><a class="logout bg-theme03" href="login.html">Logout</a></li>
              </ul>
            </div>
      </header>
      <!--header end-->
      <!-- **********************************************************************************************************************************************************
      MAIN CONTENT
      *********************************************************************************************************************************************************** -->
      <!--main content start-->
      <section id="row main-content">
              <section class=" site-min-height">

                <div class="row cover">
                  <div class="col-md-12 text-center">
                    <img   src='<?php echo base_url("assets/images/baby_profile/$child_data->pic"); ?>' class="child-pic avatar img-circle img-thumbnail" width="200"alt="avatar" >

                  </div>
                </div>
                <div class="row text-center bg-theme01 child-info">
                <?php if ($child_data->gender=="female"): ?>

                  <p id="name"><?php echo $child_data->first_name." ". $child_data->last_name ?></p>
                  <p class="subheading" id="birth"><?php echo $child_data->birth_date; ?></p>
                  <i class="fa fa-female fa-2x" aria-hidden="true"></i>

                <?php elseif ($child_data->gender=="male"): ?>
                  <p id="name"><?php echo $child_data->first_name." ". $child_data->last_name ?></p>
                  <p class="subheading" id="birth"><?php echo $child_data->birth_date; ?></p>
                  <i class="fa fa-male fa-2x"></i>
                <?php endif?>
              </div>
              <!--side sections nav-->
                <div class="row ">
                  <div class="col-md-2 sections-sidebar bg-theme " style="height:1200px;">
                    <ul class=" nav  nav-stacked " style="padding:50px 0px;margin-right: 20px;" >
                      <!-- growing tab -->
                      <li>
                        <div class="circle-tabs" dir="ltr">
                          <div class="ih-item circle effect11 left_to_right col-centered">
                            <a href="#growing" data-toggle="tab">
                              <div class="img" ><img src='<?php $icon=$main_sections[0]->web_icon; echo base_url("assets/images/main_sections_icons/$icon") ?>' alt="img"></div>
                              <div class="info">
                                <h3><?php echo $main_sections[0]->name; ?></h3>
                                <p style="font-size:10px"><?php echo $main_sections[0]->web_description; ?></p>
                              </div>
                            </a>
                          </div>
                        </div>
                      </li>
                      <!-- feeding tab -->
                      <li>
                        <div  class="circle-tabs" dir="ltr">
                          <div class="ih-item circle effect11 left_to_right col-centered">
                            <a href="#feeding" data-toggle="tab">
                              <div class="img" ><img src='<?php $icon=$main_sections[1]->web_icon; echo base_url("assets/images/main_sections_icons/$icon") ?>' alt="img"></div>
                              <div class="info">
                                <h3><?php echo $main_sections[1]->name; ?></h3>
                                <p style="font-size:10px"><?php echo $main_sections[1]->web_description; ?></p>
                              </div>
                            </a>
                          </div>
                        </div>
                      </li>
                      <!-- sleeping tab -->
                      <li>
                        <div class="circle-tabs last-tab" dir="ltr">
                          <div class="ih-item circle effect11 left_to_right col-centered">
                            <a href="#sleeping" data-toggle="tab">
                              <div class="img" ><img src='<?php $icon=$main_sections[2]->web_icon; echo base_url("assets/images/main_sections_icons/$icon") ?>' alt="img"></div>
                              <div class="info">
                                <h3><?php echo $main_sections[2]->name; ?></h3>
                                <p style="font-size:10px"><?php echo $main_sections[2]->web_description; ?></p>
                              </div>
                            </a>
                          </div>
                        </div>
                      </li>
                      <!-- Skilles tab -->
                      <li>
                        <div class="circle-tabs last-tab" dir="ltr">
                          <div class="ih-item circle effect11 left_to_right col-centered">
                            <a href="#skills" data-toggle="tab">
                              <div class="img" ><img src='<?php $icon=$main_sections[3]->web_icon; echo base_url("assets/images/main_sections_icons/$icon") ?>' alt="img"></div>
                              <div class="info">
                                <h3><?php echo $main_sections[3]->name; ?></h3>
                                <p style="font-size:10px"><?php echo $main_sections[3]->web_description; ?></p>
                              </div>
                            </a>
                          </div>
                        </div>
                      </li>
                      <!-- Vaccines tab -->
                      <li>
                        <div class="circle-tabs last-tab" dir="ltr">
                          <div class="ih-item circle effect11 left_to_right col-centered">
                            <a href="#Vaccinations" data-toggle="tab">
                              <div class="img" ><img src='<?php $icon=$main_sections[4]->web_icon; echo base_url("assets/images/main_sections_icons/$icon") ?>' alt="img"></div>
                              <div class="info">
                                <h3  style="font-size:15px"><?php echo $main_sections[4]->name; ?></h3>
                                <p style="font-size:10px"><?php echo $main_sections[4]->web_description; ?></p>
                              </div>
                            </a>
                          </div>
                        </div>
                      </li>

                    </ul>
                    <hr>
                    <hr>
                    <hr>
                    <hr>
                    <hr>
                    <hr>
                    <hr>
                    <hr>
                  </div>

                    <!--Sections Content-->
                      <div class="col-md-10" ng-controller="baby_profile_controller">
                        <div class="tab-content text-center" ng-view>

                        </div>
                      </div>

                </div>

    		      </section><!--/wrapper -->
          </section><!-- /MAIN CONTENT -->

      <!--main content end-->
      <!--footer start-->
      <footer class="row site-footer">
          <div class="text-center">
              2016 - Baby Health Care
              <a href="#" class="go-top">
                  <i class="fa fa-angle-up"></i>
              </a>
          </div>
      </footer>
      <!--footer end-->
  </div>



<script type="text/javascript">
$( document ).ready(function() {

    google.charts.load('current', {'packages':['line','bar']});
    $.ajaxPrefilter(function( options, originalOptions, jqXHR ) {
        options.async = true;
    });
  });

  var my_angular = angular.module("app",['ngRoute']);
      my_angular.config(['$routeProvider',function($routeProvider){

            $routeProvider
            .when('/',{templateUrl:'<?php echo base_url("Doctor_Dashboard/patients_grow"); ?>',controller:'safeCtrl'})
            .when('/growing',{templateUrl:'<?php echo base_url("Doctor_Dashboard/patients_grow"); ?>',controller:'safeCtrl'})
            .when('/feeding',{templateUrl:'<?php echo base_url("Doctor_Dashboard/patients_food/$id_child"); ?>',controller:'foodCtrl'})
            .when('/sleeping',{templateUrl:'<?php echo base_url("Doctor_Dashboard/patients_sleep"); ?>',controller:'sleepCtrl'})
            .when('/skills',{templateUrl:'<?php echo base_url("Doctor_Dashboard/patients_skills"); ?>',controller:'skillsCtrl'})
            .when('/Vaccinations',{templateUrl:'<?php echo base_url("Doctor_Dashboard/patients_Vaccinations"); ?>',controller:'VaccinationsCtrl'})
            .otherwise({redirectTo:'/'})

      }])
      my_angular.controller('baby_profile_controller',['$scope','$http',function($scope,$http){

      }])

      my_angular.controller('VaccinationsCtrl',['$scope','$http',function($scope,$http){
        $scope.section = "compleated";
        $scope.vac = <?php echo json_encode($vac_taken, JSON_PRETTY_PRINT) ?>;

        $scope.compleated = function(){
          $scope.section = "compleated";

          $http({
            method: "POST",
            url: '<?php echo site_url("Doctor_Dashboard/vac_compleated"); ?>',
            data : {
              id_child : <?php echo $id_child ?>,
            },
          }).then(function successCallback(response) {
                  $scope.vac = response.data;
            }, function errorCallback(response) {
            });

        }

        $scope.requasted = function(){
          $scope.section = "requasted";

          $http({
            method: "POST",
            url: '<?php echo site_url("Doctor_Dashboard/vac_requests"); ?>',
            data : {
              id_child : <?php echo $id_child ?>,
            },
          }).then(function successCallback(response) {
                  $scope.vac = response.data;
                  console.log(response);
            }, function errorCallback(response) {
            });

        }

        $scope.not_taken = function(){
          $scope.section = "not_taken";

          $http({
            method: "POST",
            url: '<?php echo site_url("Doctor_Dashboard/vac_not_compleated"); ?>',
            data : {
              id_child : <?php echo $id_child ?>,
            },
          }).then(function successCallback(response) {
                  $scope.vac = response.data;
            }, function errorCallback(response) {
            });

        }

        $scope.add_vac = function add_vac(row) {
            var index = $scope.vac.indexOf(row);


            $http({
              method: "POST",
              url: '<?php echo site_url("Doctor_Dashboard/send_vaccination_added_gcm"); ?>',
              data : {
                creator_user_id : <?php echo $id_doctor ?>,
                id_vac : $scope.vac[index].id,
                type_id : 5,
                child_id : <?php echo $id_child ?>,
              },
            }).then(function successCallback(response) {
                    if (index !== -1) {
                        $scope.vac.splice(index, 1);
                    }
                    console.log("suc");
                    console.log(response);



              }, function errorCallback(response) {
                  console.log(response);
              });




        }



      }])

      my_angular.controller('foodCtrl',['$scope','$http',function($scope,$http){

        $("#good").hide();
        $("#pending").hide();
        $("#blocked").hide();

        $("#all_btn").on('click',function(){
          $("#good").hide();
          $("#pending").hide();
          $("#blocked").hide();
          $("#all").show();
        });
        $("#good_btn").on('click',function(){
          $("#all").hide();
          $("#pending").hide();
          $("#blocked").hide();
          $("#good").show();
        });
        $("#pending_btn").on('click',function(){
          $("#all").hide();
          $("#good").hide();
          $("#blocked").hide();
          $("#pending").show();
        });
        $("#blocked_btn").on('click',function(){
          $("#all").hide();
          $("#pending").hide();
          $("#good").hide();
          $("#blocked").show();
        });
        $(function(){
          $('.record td').on('click', function(){
            var chevState =  $(this).parent('.record').next('.companion');
            $(this).parent('.record').siblings('.companion').not(chevState).hide();
            $(this).parent('.record').next('.companion').toggle('medium');
          })
        });
              document.getElementsByClassName("companion").onclick = function() {
              document.getElementById('NoteModal').style.display = "block";
            };

            $(".status_btn").on("click",function(){
                var btn  = $(this);
                var name = btn.attr("name");
                var id   = btn.parent().attr("title");

                $.ajax({
                    url :'<?php echo site_url("Doctor_Dashboard/update_food"); ?>',
                    type: "POST",
                    data : {
                      food_id : id,
                      status : name,
                    },
                    success: function(data)
                            {
                                if(name == "good" )
                                $(".status"+id).removeClass("success warning danger").addClass("success").html(name);
                                else if(name == "pending" )
                                $(".status"+id).removeClass("success warning danger").addClass("warning").html(name);
                                else if(name == "blocked" )
                                $(".status"+id).removeClass("success warning danger").addClass("danger").html(name);
                            },
                              error: function (xhr, ajaxOptions, thrownError) {
                                    //  alert(xhr.status);
                                    //  alert(xhr.responseText);
                                    //  alert(thrownError);
                                   }

                });

            });

      }])
      my_angular.controller('skillsCtrl',['$scope','$http',function($scope,$http){
          $scope.done_skills = <?php echo json_encode($skills_done, JSON_PRETTY_PRINT) ?>;
          $scope.not_done_skills = <?php echo json_encode($skills_not_done, JSON_PRETTY_PRINT) ?>;
          $scope.done = true;



      }])

      my_angular.controller('sleepCtrl', ['$scope', '$http', function ($scope,$http) {


        google.charts.setOnLoadCallback(drawChart);

        function drawChart() {


          var data = new google.visualization.DataTable();
          data.addColumn('number', 'Months');
          data.addColumn('number', 'Defualt');
          data.addColumn('number', 'Baby');



          var chart_info = <?php echo json_encode($sleep_chart_info, JSON_PRETTY_PRINT) ?>;
          var chart_baby_info = <?php echo json_encode($sleep_chart_baby_info, JSON_PRETTY_PRINT) ?>;

          // console.log(chart_baby_info);
          chart_info.forEach(function(entry) {
                    data.addRows([
                      [parseFloat(entry.age_from),parseFloat(entry.avrg_amount),null],
                    ]);
             });

           chart_baby_info.forEach(function(entry) {
                     data.addRows([
                       [parseFloat(entry.age_months),,parseFloat(entry.amount)],
                     ]);
              });
          var options = {
            // chart: {
            //   title: 'Company Performance',
            //   subtitle: 'Sales, Expenses, and Profit: 2014-2017',
            // }
            axes: {
           y: {
             0: {label: 'Hours'} // Left y-axis.
           }
         }
          };

          var chart = new google.charts.Bar(document.getElementById('columnchart_material'));

          chart.draw(data, options);
        }

      }])

      my_angular.controller('safeCtrl', ['$scope', '$http', function ($scope,$http) {

        function compare(a,b) {
          if (a.age_weeks < b.age_weeks)
            return -1;
          else if (a.age_weeks > b.age_weeks)
            return 1;
          else
            return 0;
        }

        $scope.load_count = 1;
        $scope.order = "age_weeks";
        $scope.displayed = [];
        function add_row() {
            return {
                age_weeks:  parseInt($("#week").val()),
                measurement: parseFloat($("#value").val()),
            }
        }

        $scope.quantities_array = <?php echo json_encode($quantities_array, JSON_NUMERIC_CHECK ) ?>;
        var chart_info = <?php echo json_encode($chart_info, JSON_PRETTY_PRINT) ?>;
        google.charts.setOnLoadCallback(drawChart);

        var id_quantitie_sec ;

        $scope.growth = function(type){


          if(type == "Height"){
              id_quantitie_sec = 1;
          }
          else if(type == "Weight"){
              id_quantitie_sec = 2;
          }
          else if(type == "Head"){
              id_quantitie_sec = 3;
          }
          google.charts.setOnLoadCallback(drawChart);
         $http({
           method: "POST",
           url: '<?php echo site_url("Doctor_Dashboard/quantities_array"); ?>',
           data : {
             id_child : <?php echo $id_child ?>,
             id_quantitie  : id_quantitie_sec,
           },
         }).then(function successCallback(response) {
                 $scope.quantities_array = response.data;
           }, function errorCallback(response) {
           });
           $http({
             method: "POST",
             url: '<?php echo site_url("Doctor_Dashboard/chart_info"); ?>',
             data : {
               id_child : <?php echo $id_child ?>,
               id_quantitie  : id_quantitie_sec,
             },
           }).then(function successCallback(response) {
                 chart_info = response.data;
                 drawChart();
             }, function errorCallback(response) {
             });

           };


      function drawChart() {

        var data = new google.visualization.DataTable();
        data.addColumn('number', 'Weeks');
        data.addColumn('number', 'P3');
        data.addColumn('number', 'P50');
        data.addColumn('number', 'P97');
        data.addColumn('number', 'Baby Quantities');

        chart_info.forEach(function(entry) {
                  data.addRows([
                    [parseFloat(entry.age_weeks), parseFloat(entry.p3), parseFloat(entry.p50), parseFloat(entry.p97),null],
                  ]);
           });
        $scope.quantities_array.forEach(function(entry) {
                     data.addRows([
                       [ parseFloat(entry.age_weeks), , , ,parseFloat(entry.measurement)],
                     ]);
              });

        var options = {
          chart: {
            title: '',
            subtitle: ''
          },
          width: 810,
          height: 400,
        };

        var chart = new google.charts.Line(document.getElementById('linechart_material'));

        chart.draw(data, options);

                  $scope.addItem = function addItem() {
                    var key = parseInt($("#week").val());
                    var value = $("#value").val();

                    var bo = true;

                    $scope.quantities_array.forEach(function(element){
                      if(key == element.age_weeks){
                          bo = false;
                      }

                    });

                  if(key && value){
                      if(bo){
                            $scope.quantities_array.push(add_row());

                            var value = $("#value").val();
                            var week  = $("#week").val();
                            data.addRows([
                              [parseFloat(week),  , , ,parseFloat(value)]
                            ]);

                            data.removeColumn(data.getNumberOfColumns() - 1);
                            data.addColumn('number', ' Baby Quantities');
                            $scope.quantities_array.sort(compare);
                            $scope.quantities_array.forEach(function(entry) {
                                         data.addRows([
                                           [ parseFloat(entry.age_weeks), , , ,parseFloat(entry.measurement)],
                                         ]);
                                  });
                            chart.draw(data, options);

                            $http({
                              method: "POST",
                              url: '<?php echo site_url("Doctor_Dashboard/add_quantity"); ?>',
                              data : {
                                val : value,
                                we  : week,
                                qe  :  id_quantitie_sec,
                                id_child : <?php echo $id_child ?>
                              },
                            }).then(function successCallback(response) {

                              }, function errorCallback(response) {

                              });

                      }
                      else{
                            alert("sorry, you've added a measurement for this week !");
                            bo=true;

                      }
                  }
                  else{
                    alert("sorry, you can't add empty values !");
                    bo=true;
                  }


                  };

                  $scope.editItem = function editItem(row) {
                    var index = $scope.quantities_array.indexOf(row);
                    var id = $(row).attr('id');
                    var new_value = $("#"+id).html();

                    if(Number.isInteger(parseFloat(new_value))||(!isNaN(parseFloat(new_value)) && parseFloat(new_value).toString().indexOf('.') != -1)){
                    $scope.quantities_array[index].measurement = new_value;

                    data.removeColumn(data.getNumberOfColumns() - 1);
                    data.addColumn('number', ' Baby Quantities');
                    $scope.quantities_array.forEach(function(entry) {
                                 data.addRows([
                                   [ parseFloat(entry.age_weeks), , , ,parseFloat(entry.measurement)],
                                 ]);
                          });
                    chart.draw(data, options);
                    $http({
                      method: "POST",
                      url: '<?php echo site_url("Doctor_Dashboard/update_quantity"); ?>',
                      data : {
                        val : new_value,
                        id_q  : id,
                      },
                    }).then(function successCallback(response) {

                      }, function errorCallback(response) {

                      });

                    }
                    else{
                      alert("sorry, wrong input !");

                    }

                  }


                  $scope.removeItem = function removeItem(row) {
                      var index = $scope.quantities_array.indexOf(row);

                      $http({
                        method: "POST",
                        url: '<?php echo site_url("Doctor_Dashboard/delete_quantity"); ?>',
                        data : {
                          val : $scope.quantities_array[index].measurement,
                          we  : $scope.quantities_array[index].age_weeks,
                          qe  :  id_quantitie_sec,
                          id_child : <?php echo $id_child ?>

                        },
                      }).then(function successCallback(response) {

                        }, function errorCallback(response) {

                        });


                      if (index !== -1) {
                          $scope.quantities_array.splice(index, 1);
                      }


                      data.removeColumn(data.getNumberOfColumns() - 1);
                      data.addColumn('number', ' Baby Quantities');
                      $scope.quantities_array.sort(compare);
                      $scope.quantities_array.forEach(function(entry) {
                                   data.addRows([
                                     [ parseFloat(entry.age_weeks), , , ,parseFloat(entry.measurement)],
                                   ]);
                            });
                      chart.draw(data, options);

                  }

      }

      }]);



</script>


  </body>
