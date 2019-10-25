
    <!--main content start-->
    <section id="main-content">
      <section class="wrapper">
        <div class="row mt">
          <!--Patients-->
            <div class="col-md-2 col-md-offset-1 mb">
              <!-- WHITE PANEL - Desk Cleaness -->
              <div class="white-panel pn">
                <div class="white-header">
                  <h5>Number Of Patients</h5>
                </div>
                <div class="row row-rating">
                  <div class="col-md-10 col-centered">
                    <i class="fa fa-users fa-2x"></i>
                  </div>
                  <div class="col-md-6 col-centered">
                    <p><strong><?php if(isset($children_count)) {echo $children_count;} else{ echo "0";} ?></strong></p>
                  </div>
                </div>
              </div>
            </div>
          <!--Clinic-->
						<div class="col-md-3 mb">
							<!-- WHITE PANEL - Desk Cleaness -->
							<div class="white-panel pn">
								<div class="white-header">
									<h5>Clinic Cleaness</h5>
								</div>
								<div class="row row-rating">
                  <div class="col-md-10 col-centered">
                  <?php $count_s=0; $stars = floor($doctor_review[0]->office_cleanliness); for($i=0;$i<$stars;$i++):$count_s++;?>
                    <i class="fa fa-star fa-2x"></i>
                    <?php endfor; ?>
                    <?php if($doctor_review[0]->office_cleanliness -  $stars >= 0.5):$count_s++; ?>
                        <i class="fa fa-star-half-o fa-2x"></i>
                   <?php endif; ?>
                   <?php while($count_s<5):$count_s++; ?>
                     <i class="fa fa-star-o fa-2x"></i>
                  <?php endwhile; ?>
                  </div>

									<div class="col-md-6 col-centered">
										<p><strong><?php if(isset($doctor_review)) {echo round($doctor_review[0]->office_cleanliness,2);} else{ echo "0";} ?></strong></p>
									</div>
								</div>
							</div>
						</div>
          <!--Time-->
            <div class="col-md-2 mb">
              <!-- WHITE PANEL - Clarity Explanation -->
              <div class="white-panel pn">
                <div class="white-header">
                  <h5>AVG Waiting Time</h5>
                </div>
                <div class="row row-rating">
                  <div class="col-md-10 col-centered">
                    <i class="fa fa-clock-o fa-2x"></i>

                  </div>
                  <div class="col-md-6 col-centered">
                    <p><strong><?php if(isset($doctor_review)) {echo round($doctor_review[0]->waiting_time)." Min";} else{ echo "0";} ?></strong></p>
                  </div>
                </div>
              </div>
            </div>
          <!--Explanation-->
            <div class="col-md-3 mb">
              <!-- WHITE PANEL - Clarity Explanation -->
              <div class="white-panel pn">
                <div class="white-header">
                  <h5>Clarity Explanation</h5>
                </div>
                <div class="row row-rating">
                  <div class="col-md-10 col-centered">
                  <?php $count_s=0; $stars = floor($doctor_review[0]->explination_clarity); for($i=0;$i<$stars;$i++):$count_s++;?>
                    <i class="fa fa-star fa-2x"></i>
                    <?php endfor; ?>
                    <?php if($doctor_review[0]->explination_clarity -  $stars >= 0.5):$count_s++; ?>
                        <i class="fa fa-star-half-o fa-2x"></i>
                   <?php endif; ?>
                   <?php while($count_s<5):$count_s++; ?>
                     <i class="fa fa-star-o fa-2x"></i>
                  <?php endwhile; ?>
                  </div>
                  <div class="col-md-6 col-centered">
                    <p><strong><?php if(isset($doctor_review)) {echo round($doctor_review[0]->explination_clarity,1);} else{ echo "0";} ?></strong></p>
                  </div>
                </div>
              </div>
            </div>
          <!--Reviews & Rate-->
            <div class="col-md-8 col-md-offset-3 ">
              <div class="row ">
                <div class="col-md-4 col-sm-2 box0">
                  <div class="box1">
                    <h2>Full Rate</h2>
                    <h3><?php if(isset($user_info->rate)) {echo $user_info->rate;} else{ echo "0";} ?></h3>
                  </div>
                </div>

            <div class="col-md-4 col-sm-2 box0">
              <div class="box1">
                <h2>Review Count</h2>
                <h3><?php if(isset($doctor_review)) {echo round($doctor_review[0]->rev_count);} else{ echo "0";} ?></h3>
              </div>
          </div>

            </div>


          </div>

          </div><!-- /row -->



                </section>

          <!--Chart-->
            <div class ="col-xs-12 col-centered">
              <div id="chart_div"></div>
            </div>
          </div>

      </section>
    </section>

  <script type="text/javascript">
  google.charts.load('current', {packages: ['corechart', 'line']});
google.charts.setOnLoadCallback(drawBackgroundColor);

function drawBackgroundColor() {
    var data = new google.visualization.DataTable();
    data.addColumn('number', 'X');
    data.addColumn('number', 'Patients');

    var chart_info = <?php echo json_encode($patients_chart, JSON_PRETTY_PRINT) ?>;

    chart_info.forEach(function(entry) {
              data.addRows([
                [parseFloat(entry.weeks),parseFloat(entry.count)],
              ]);
       });

    // data.addRows([
    //   [0, 0],   [1, 10],  [2, 23],  [3, 17],  [4, 18],  [5, 9],
    //   [6, 11],  [7, 27],  [8, 33],  [9, 40],  [10, 32], [11, 35],
    //   [12, 30], [13, 40], [14, 42], [15, 47], [16, 44], [17, 48],
    //   [18, 52], [19, 54], [20, 42], [21, 55], [22, 56], [23, 57],
    //   [24, 60], [25, 50], [26, 52], [27, 51], [28, 49], [29, 53],
    //   [30, 55], [31, 60], [32, 61], [33, 59], [34, 62], [35, 65],
    //   [36, 62], [37, 58], [38, 55], [39, 61], [40, 64], [41, 65],
    //   [42, 63], [43, 66], [44, 67], [45, 69], [46, 69], [47, 70],
    //   [48, 72], [49, 68], [50, 66], [51, 65], [52, 67], [53, 70],
    //   [54, 71], [55, 72], [56, 73], [57, 75], [58, 70], [59, 68],
    //   [60, 64], [61, 60], [62, 65], [63, 67], [64, 68], [65, 69],
    //   [66, 70], [67, 72], [68, 75], [69, 80]
    // ]);

    var options = {
      hAxis: {
        title: 'Weeks'
      },
      vAxis: {
        title: 'Patients'
      },
      backgroundColor: '#f1f8e9',
      height: 280,

    };

    var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
    chart.draw(data, options);
  }
  </script>
