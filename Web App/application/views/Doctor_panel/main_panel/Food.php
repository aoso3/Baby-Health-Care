
<link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/css/doctor_dashboard/patients_main_panel/food_table.css')?>">

      			<section class="color-8 tabs-row" ng-init  style="padding: 30px 0px">
      				<p class="perspective">
      					<button id="all_btn" class="btn_new btn-primary btn-8 round-form " ng-click="show='all'">All</button>
      				</p>
      				<p class="perspective">
      					<button id="good_btn" class="btn_new btn-success btn-8 round-form" ng-click="show='good'">Good</button>
      				</p>
              <p class="perspective">
      					<button id="pending_btn" class="btn_new btn-warning btn-8  round-form" ng-click="show='pending'">Pending</button>
      				</p>
              <p class="perspective">
      					<button id="blocked_btn" class="btn_new btn-danger btn-8 round-form" ng-click="show='blocked'">Blocked</button>
      				</p>
      			</section>


<?php  if(!empty($food)): ?>

<?php   foreach($food as $food_row) :   ?>
  <?php foreach($food_samples as $food_sample) : if($food_sample->id_food == $food_row->id ):      ?>

    <!--Note's Modal-->
    <div id="<?php echo $food_sample->id ?>" class="modal fade " role="dialog">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal">&times;</button>
          </div>
          <div  class="modal-body">
            <p><?php echo $food_sample->note; ?></p>
          </div>
        </div>
      </div>
    </div>

  <?php endif; endforeach;  ?>
<?php  endforeach;  ?>




<div id="all" class=" col-xs-12"  >
  <table class="rules table table-striped table-hover">
    <thead>
      <tr>
        <th class="text-center">Food</th>
        <th class="text-center">Baby Status</th>
        <th class="text-center">Rating</th>
        <th class="text-center">Samples</th>
      </tr>
    </thead>
  <tbody>
    <?php   foreach($food as $food_row) :   ?>
      <tr class="record">
        <td class="first"><?php echo $food_row->name; ?></td>

        <?php if($food_row->rating == "yum"){ ?>
          <td class="first"><img src="<?php echo base_url('assets/Images/doctor_panel/baby-happy.svg');?>" class="img-circle img-responsive baby-face" ></td>
        <?php }else if($food_row->rating == "normal"){ ?>
          <td class="first"><img src="<?php echo base_url('assets/Images/doctor_panel/baby-normal.svg');?>" class="img-circle img-responsive baby-face" ></td>
        <?php }else if($food_row->rating == "yuck"){ ?>
          <td class="first"><img src="<?php echo base_url('assets/Images/doctor_panel/baby-sad.svg');?>" class="img-circle img-responsive baby-face" ></td>
        <?php }  ?>

        <?php if($food_row->status == "good"){ ?>
          <td id="status<?php echo $food_row->id ?>" class="status<?php echo $food_row->id ?> first success text-center"><?php echo $food_row->status; ?></td>
        <?php }else if($food_row->status == "pending"){ ?>
          <td id="status<?php echo $food_row->id ?>" class="status<?php echo $food_row->id ?> first warning  text-center"><?php echo $food_row->status; ?></td>
        <?php }else if($food_row->status == "blocked"){ ?>
          <td id="status<?php echo $food_row->id ?>" class="status<?php echo $food_row->id ?> first danger text-center"><?php echo $food_row->status; ?></td>
        <?php }  ?>

        <td><div class="btn btn-info btn-sm pull-center samples" data-toggle="tooltip" data-placement="bottom" title="show"><span class="glyphicon glyphicon-book"></span></div></td>
      </tr>
      <tr class="companion">
        <td class="output" colspan="7">
          <table class="table " id="sample-1-table">
            <tbody>
              <?php $is=0; foreach($food_samples as $food_sample) : if($food_sample->id_food == $food_row->id ):  $is++;     ?>
                <?php if($is==1): ?>
                  <thead>
                    <tr>
                      <th class="text-center">Sample Date</th>
                      <th class="text-center">Note</th>
                      <th class="text-center">Note Date</th>
                   </tr>
                  </thead>
                <?php endif; ?>
                <tr>
                    <td><label><?php echo $food_sample->date; ?></label></td>
                    <td><div class="btn btn-info btn-sm pull-center" style="margin-left:-24%" data-toggle="modal" data-target="#<?php echo $food_sample->id ?>"  data-placement="bottom" title="Show"><span class="glyphicon glyphicon-file "></span></div></td>
                    <td><label><?php echo $food_sample->note_date; ?></label></td>
                </tr>


              <?php endif; endforeach;  ?>

              </tbody>
              <tfoot>
                <tr><td colspan="7">
                  <div title="<?php echo $food_row->id; ?>" class="center inline btn-group">
                      <div name="blocked"  class="btn btn-danger btn-sm  status_btn"  data-toggle="tooltip" data-placement="bottom" title="Reject"><span class="glyphicon glyphicon-remove"></span></div>
                      <div name="pending"  class="btn btn-warning btn-sm  status_btn"data-toggle="tooltip" data-placement="bottom" title="Pending"> <span class="glyphicon glyphicon-minus"></span></div>
                      <div name="good"   class="btn btn-success btn-sm active  status_btn" data-toggle="tooltip" data-placement="bottom" title="Accept"><span class="glyphicon glyphicon-ok"></span></div>
                  </div></td></tr>
              </tfoot>
            </table>
        </td>
      </tr>
    <?php $is=0;   endforeach;  ?>

    </tbody>
  </table>

  <div class="clearfix"></div>

</div>


    <div id="good" class=" col-xs-12"  >
      <table class="rules table table-striped table-hover">
        <thead>
          <tr>
            <th class="text-center">Food</th>
            <th class="text-center">Baby Status</th>
            <th class="text-center">Rating</th>
            <th class="text-center">Samples</th>
          </tr>
        </thead>
      <tbody>
        <?php   foreach($food as $food_row) : if($food_row->status=='good'):  ?>
        <tr class="record">
          <td class="first"><?php echo $food_row->name; ?></td>

            <?php if($food_row->rating == "yum"){ ?>
              <td class="first"><img src="<?php echo base_url('assets/Images/doctor_panel/baby-happy.svg');?>" class="img-circle img-responsive baby-face" ></td>
            <?php }else if($food_row->rating == "normal"){ ?>
              <td class="first"><img src="<?php echo base_url('assets/Images/doctor_panel/baby-normal.svg');?>" class="img-circle img-responsive baby-face" ></td>
            <?php }else if($food_row->rating == "yuck"){ ?>
              <td class="first"><img src="<?php echo base_url('assets/Images/doctor_panel/baby-sad.svg');?>" class="img-circle img-responsive baby-face" ></td>
            <?php }  ?>

            <?php if($food_row->status == "good"){ ?>
              <td id="status<?php echo $food_row->id ?>" class="status<?php echo $food_row->id ?> first success text-center"><?php echo $food_row->status; ?></td>
            <?php }else if($food_row->status == "pending"){ ?>
              <td id="status<?php echo $food_row->id ?>" class="status<?php echo $food_row->id ?> first warning  text-center"><?php echo $food_row->status; ?></td>
            <?php }else if($food_row->status == "blocked"){ ?>
              <td id="status<?php echo $food_row->id ?>" class="status<?php echo $food_row->id ?> first danger text-center"><?php echo $food_row->status; ?></td>
            <?php }  ?>

          <td><div class="btn btn-info btn-sm pull-center samples" data-toggle="tooltip" data-placement="bottom" title="إظهار"><span class="glyphicon glyphicon-book"></span></div></td>
          </tr>
          <tr class="companion">
            <td class="output" colspan="7">
              <table class="table " id="sample-1-table">
                <tbody>
                  <?php $is=0; foreach($food_samples as $food_sample) : if($food_sample->id_food == $food_row->id ):  $is++;     ?>
                    <?php if($is==1): ?>
                      <thead>
                        <tr>
                          <th class="text-center">Sample Date</th>
                          <th class="text-center">Note</th>
                          <th class="text-center">Note Date</th>
                       </tr>
                      </thead>
                    <?php endif; ?>
                    <tr>
                      <td><label><?php echo $food_sample->date; ?></label></td>
                      <td><div class="btn btn-info btn-sm pull-center" style="margin-left:-24%" data-toggle="modal" data-target="#<?php echo $food_sample->id ?>"  data-placement="bottom" title="show"><span class="glyphicon glyphicon-file "></span></div></td>
                      <td><label><?php echo $food_sample->note_date; ?></label></td>
                    </tr>


                  <?php endif; endforeach;  ?>

                  </tbody>
                  <tfoot>
                    <tr><td colspan="7">
                      <div title="<?php echo $food_row->id; ?>" class="center inline btn-group">
                          <div name="blocked"  class="btn btn-danger btn-sm  status_btn"  data-toggle="tooltip" data-placement="bottom" title="رفض"><span class="glyphicon glyphicon-remove"></span></div>
                          <div name="pending"  class="btn btn-warning btn-sm  status_btn"data-toggle="tooltip" data-placement="bottom" title="انتظار"> <span class="glyphicon glyphicon-minus"></span></div>
                          <div name="good"   class="btn btn-success btn-sm active  status_btn" data-toggle="tooltip" data-placement="bottom" title="قبول"><span class="glyphicon glyphicon-ok"></span></div>
                      </div></td></tr>
                  </tfoot>
                </table>
            </td>
          </tr>
        <?php $is=0; endif;  endforeach;  ?>

        </tbody>
      </table>

      <div class="clearfix"></div>

    </div>

    <div id="pending" class=" col-xs-12"  >
      <table class="rules table table-striped table-hover">
        <thead>
          <tr>
            <th class="text-center">Food</th>
            <th class="text-center">Baby Status</th>
            <th class="text-center">Rating</th>
            <th class="text-center">Samples</th>
          </tr>
        </thead>
      <tbody >
        <?php   foreach($food as $food_row) : if($food_row->status=='pending'):  ?>
        <tr class="record">
          <td class="first"><?php echo $food_row->name; ?></td>

            <?php if($food_row->rating == "yum"){ ?>
              <td class="first"><img src="<?php echo base_url('assets/Images/doctor_panel/baby-happy.svg');?>" class="img-circle img-responsive baby-face" ></td>
            <?php }else if($food_row->rating == "normal"){ ?>
              <td class="first"><img src="<?php echo base_url('assets/Images/doctor_panel/baby-normal.svg');?>" class="img-circle img-responsive baby-face" ></td>
            <?php }else if($food_row->rating == "yuck"){ ?>
              <td class="first"><img src="<?php echo base_url('assets/Images/doctor_panel/baby-sad.svg');?>" class="img-circle img-responsive baby-face" ></td>
            <?php }  ?>

            <?php if($food_row->status == "good"){ ?>
              <td id="status<?php echo $food_row->id ?>" class="status<?php echo $food_row->id ?> first success text-center"><?php echo $food_row->status; ?></td>
            <?php }else if($food_row->status == "pending"){ ?>
              <td id="status<?php echo $food_row->id ?>" class="status<?php echo $food_row->id ?> first warning  text-center"><?php echo $food_row->status; ?></td>
            <?php }else if($food_row->status == "blocked"){ ?>
              <td id="status<?php echo $food_row->id ?>" class="status<?php echo $food_row->id ?> first danger text-center"><?php echo $food_row->status; ?></td>
            <?php }  ?>

          <td><div class="btn btn-info btn-sm pull-center samples" data-toggle="tooltip" data-placement="bottom" title="إظهار"><span class="glyphicon glyphicon-book"></span></div></td>

          </tr>
          <tr class="companion">
            <td class="output" colspan="7">
              <table class="table " id="sample-1-table">
                <tbody>
                  <?php $is=0; foreach($food_samples as $food_sample) : if($food_sample->id_food == $food_row->id ):  $is++;     ?>
                    <?php if($is==1): ?>
                      <thead>
                        <tr>
                          <th class="text-center">Sample Date</th>
                          <th class="text-center">Note</th>
                          <th class="text-center">Note Date</th>
                       </tr>
                      </thead>
                    <?php endif; ?>
                    <tr>
                      <td><label><?php echo $food_sample->date; ?></label></td>
                      <td><div class="btn btn-info btn-sm pull-center" style="margin-left:-24%" data-toggle="modal" data-target="#<?php echo $food_sample->id ?>"  data-placement="bottom" title="Show"><span class="glyphicon glyphicon-file "></span></div></td>
                      <td><label><?php echo $food_sample->note_date; ?></label></td>

                    </tr>


                  <?php endif; endforeach;  ?>

                  </tbody>
                  <tfoot>
                    <tr><td colspan="7">
                      <div title="<?php echo $food_row->id; ?>" class="center inline btn-group">
                          <div name="blocked"  class="btn btn-danger btn-sm  status_btn"  data-toggle="tooltip" data-placement="bottom" title="رفض"><span class="glyphicon glyphicon-remove"></span></div>
                          <div name="pending"  class="btn btn-warning btn-sm  status_btn"data-toggle="tooltip" data-placement="bottom" title="انتظار"> <span class="glyphicon glyphicon-minus"></span></div>
                          <div name="good"   class="btn btn-success btn-sm active  status_btn" data-toggle="tooltip" data-placement="bottom" title="قبول"><span class="glyphicon glyphicon-ok"></span></div>
                      </div></td></tr>
                  </tfoot>
                </table>
            </td>
          </tr>
        <?php $is=0; endif;  endforeach;  ?>

        </tbody>
      </table>

      <div class="clearfix"></div>

    </div>

    <div id="blocked" class=" col-xs-12"  >
      <table class="rules table table-striped table-hover">
        <thead>
          <tr>
            <th class="text-center">Food</th>
            <th class="text-center">Baby Status</th>
            <th class="text-center">Rating</th>
            <th class="text-center">Samples</th>
          </tr>
        </thead>
      <tbody>
        <?php   foreach($food as $food_row) : if($food_row->status=='blocked'):  ?>
        <tr class="record">
          <td class="first"><?php echo $food_row->name; ?></td>
            <?php if($food_row->rating == "yum"){ ?>
            <td class="first"><img src="<?php echo base_url('assets/Images/doctor_panel/baby-happy.svg');?>" class="img-circle img-responsive baby-face" ></td>
            <?php }else if($food_row->rating == "normal"){ ?>
            <td class="first"><img src="<?php echo base_url('assets/Images/doctor_panel/baby-normal.svg');?>" class="img-circle img-responsive baby-face" ></td>
            <?php }else if($food_row->rating == "yuck"){ ?>
            <td class="first"><img src="<?php echo base_url('assets/Images/doctor_panel/baby-sad.svg');?>" class="img-circle img-responsive baby-face" ></td>
            <?php }  ?>

            <?php if($food_row->status == "good"){ ?>
              <td id="status<?php echo $food_row->id ?>" class="status<?php echo $food_row->id ?> first success text-center"><?php echo $food_row->status; ?></td>
            <?php }else if($food_row->status == "pending"){ ?>
              <td id="status<?php echo $food_row->id ?>" class="status<?php echo $food_row->id ?> first warning  text-center"><?php echo $food_row->status; ?></td>
            <?php }else if($food_row->status == "blocked"){ ?>
              <td id="status<?php echo $food_row->id ?>" class="status<?php echo $food_row->id ?>  first danger text-center"><?php echo $food_row->status; ?></td>
            <?php }  ?>

          <td><div class="btn btn-info btn-sm pull-center samples" data-toggle="tooltip" data-placement="bottom" title="إظهار"><span class="glyphicon glyphicon-book"></span></div></td>

          </tr>
          <tr class="companion">
            <td class="output" colspan="7">
              <table class="table " id="sample-1-table">
                <tbody>
                  <?php $is=0; foreach($food_samples as $food_sample) : if($food_sample->id_food == $food_row->id ):  $is++;     ?>
                    <?php if($is==1): ?>
                      <thead>
                        <tr>
                          <th class="text-center">Sample Date</th>
                          <th class="text-center">Note</th>
                          <th class="text-center">Note Date</th>
                       </tr>
                      </thead>
                    <?php endif; ?>
                    <tr>
                      <td><label><?php echo $food_sample->date; ?></label></td>
                      <td><div class="btn btn-info btn-sm pull-center" style="margin-left:-24%" data-toggle="modal" data-target="#<?php echo $food_sample->id ?>"  data-placement="bottom" title="Show"><span class="glyphicon glyphicon-file "></span></div></td>
                      <td><label><?php echo $food_sample->note_date; ?></label></td>
                    </tr>


                  <?php endif; endforeach;  ?>

                  </tbody>
                  <tfoot>
                    <tr><td colspan="7">
                      <div title="<?php echo $food_row->id; ?>" class="center inline btn-group">
                          <div name="blocked"  class="btn btn-danger btn-sm  status_btn"  data-toggle="tooltip" data-placement="bottom" title="رفض"><span class="glyphicon glyphicon-remove"></span></div>
                          <div name="pending"  class="btn btn-warning btn-sm  status_btn"data-toggle="tooltip" data-placement="bottom" title="انتظار"> <span class="glyphicon glyphicon-minus"></span></div>
                          <div name="good"   class="btn btn-success btn-sm active  status_btn" data-toggle="tooltip" data-placement="bottom" title="قبول"><span class="glyphicon glyphicon-ok"></span></div>
                      </div></td></tr>
                  </tfoot>
                </table>
            </td>
          </tr>
        <?php $is=0; endif;  endforeach;  ?>

        </tbody>
      </table>

      <div class="clearfix"></div>

    </div>



  <?php endif; ?>
