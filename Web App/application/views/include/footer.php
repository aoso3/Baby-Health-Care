

<?php if ($main_content == 'auth'): ?>

    <script type="text/javascript">
    $('.message a').click(function(){
       $('form').animate({height: "toggle", opacity: "toggle"}, "slow");
    });
    </script>

<?php endif; ?>

<!-- <?php if (isset($patients_main_panel)): ?>
  <script type="text/javascript" src="<?php echo base_url('assets/js/doctor_dashboard/patients_main_panel/jquery-ui-1.9.2.custom.min.js')?>"></script>
  <script type="text/javascript" src="<?php echo base_url('assets/js/doctor_dashboard/patients_main_panel/jquery.ui.touch-punch.min.js')?>"></script>
  <script type="text/javascript" src="<?php echo base_url('assets/js/doctor_dashboard/patients_main_panel/jquery.dcjqaccordion.2.7.js')?>"></script>
  <script type="text/javascript" src="<?php echo base_url('assets/js/doctor_dashboard/patients_main_panel/jquery.scrollTo.min.js')?>"></script>
  <script type="text/javascript" src="<?php echo base_url('assets/js/doctor_dashboard/patients_main_panel/jquery.nicescroll.js')?>"></script>
  <script type="text/javascript" src="<?php echo base_url('assets/js/doctor_dashboard/patients_main_panel/elastic-svg-elements/js/snap.svg-min.js')?>"></script>
  <script type="text/javascript" src="<?php echo base_url('assets/js/doctor_dashboard/patients_main_panel/elastic-svg-elements/js/classie.js')?>"></script>
 <?php endif; ?>
 -->


<!--
  <?php if(isset($admin_dashboard)|| isset($doctor_dashboard)): ?>
      <script src="<?php echo base_url('assets/js/doctor_dashboard/common_scripts.js') ?>"></script>
      <script src="<?php echo base_url('assets/js/doctor_dashboard/jquery.dcjqaccordion.2.7.js') ?>"></script>
      <script src="<?php echo base_url('assets/js/doctor_dashboard/jquery.scrollTo.min.js') ?>"></script>

    <?php endif; ?> -->


<script type="text/javascript">
  $(document).ready(function(){
      $(".logout").on('click',function(){
        $.ajax({
                  url :'<?php echo site_url("auth/logout"); ?>',
                  type: "POST",
                  success: function(data){
                    window.location.replace('<?php echo base_url() ?>');
                  },
                              error: function (xhr, ajaxOptions, thrownError) {
                                         // alert(xhr.status);
                                         // alert(xhr.responseText);
                                         alert(thrownError);
                                     }

              });
      });

    });
</script>




<script>

var base_url = '<?php echo base_url(); ?>';
var current_user_id  = '<?php echo $current_user_id ?>';
var noti_count = '<?php echo $notification_count ?>';
var socket = io.connect( 'http://'+window.location.hostname+':3000' );

socket.on( 'new_message2', function( data ) {
    if(data.recipient_user_id == current_user_id){
      $("#noti_count").html(++noti_count);
        if(data.type_id==1){
          $( "#socket_notification" ).prepend("<li><a href='"+base_url+'doctor_dashboard/Vaccination_child/'+data.additional_data+"'><span class='photo'><img alt='avatar' src='"+base_url+'assets/images/notification/'+data.img+"'></span><span class='subject'><span class='from'>"+data.title+"</span><span class='time'>"+data.creation_date+"</span></span><span class='message'>"+data.text+"</span></a></li>");
        }
        else if(data.type_id==2){
          $( "#socket_notification" ).prepend("<li><a href='"+base_url+'doctor_dashboard/patients_approval'+"'><span class='photo'><img alt='avatar' src='"+base_url+'assets/images/notification/'+data.img+"'></span><span class='subject'><span class='from'>"+data.title+"</span><span class='time'>"+data.creation_date+"</span></span><span class='message'>"+data.text+"</span></a></li>");
        }
  }
});

$('.seen').on('click',function(){
             $.ajax({
                 url :'<?php echo site_url("doctor_dashboard/noti_seen"); ?>',
                 type: "POST",
                 success: function(data)
                         {
                            $("#noti_count").html(0);
                            noti_count = data;
                         },
                 error: function (xhr, ajaxOptions, thrownError) {
                      }

             });

         });

</script>
