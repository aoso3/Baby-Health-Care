
  <section id="main-content">
      <section class="wrapper">
          <div class="row">
              <div class="col-xs-12 main-chart">
                    <div class="dropdown">
                    <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                      <?php if($current_vac != 0){ echo $current_vac[0]->name;} else{echo "Vaccinations";} ?>
                      <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu" aria-labelledby="dropdownMenu4">
                      <?php foreach($vaccination_list as $vac): ?>
                      <li><a href='<?php echo base_url("doctor_Dashboard/Vaccination_child/$vac->id");  ?>'><?php echo $vac->name ?></a></li>
                      <?php endforeach; ?>
                    </ul>
                  </div>
                  <div id="xcrud">
                    <?php if(isset($xcrud)){ echo $xcrud ; }?>
                 </div>
              </div>
          </div>
      </section>
  </section>


  <script type="text/javascript">
  jQuery(document).on("xcrudafterrequest",function(event,container){
    alert("soso");
      if(Xcrud.current_task == 'save')
      {
          Xcrud.show_message(container,'WOW!','success');
      }
  });
  </script>
