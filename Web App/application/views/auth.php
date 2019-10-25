
<body style="overflow:hidden">
<script>
  // // This method is called right before the ajax form validation request
  // // it is typically used to setup some visuals ("Please wait...");
  // // you may return a false to stop the request
  // function beforeCall(form, options){
  //   if (window.console)
  //   console.log("Right before the AJAX form validation call");
  //   return false;
  // }
  //
  // // Called once the server replies to the ajax form validation request
  // function ajaxValidationCallback(status, form, json, options){
  //   if (window.console)
  //   console.log(status);
  //
  //   if (status === true) {
  //     alert("the form is valid!");
  //     // uncomment these lines to submit the form to form.action
  //     // form.validationEngine('detach');
  //     // form.submit();
  //     // or you may use AJAX again to submit the data
  //   }
  // }
  //
  // jQuery(document).ready(function(){
  //   jQuery(".form").validationEngine({
  //     ajaxFormValidationURL : '<?php echo site_url("auth/login");  ?>',
  //     ajaxFormValidation: true,
  //     ajaxFormValidationMethod: 'post',
  //     onBeforeAjaxFormValidation: beforeCall,
  //     onAjaxFormComplete: ajaxValidationCallback
  //   });
  // });
  $(document).ready(function(){
      $(".form").validationEngine();
     });
</script>

<!-- Modal -->
<form  accept-charset="utf-8" action="javascript:void(0);" method="post" style="direction: ltr; background-color :#79b0d4;" >
<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="myModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4  class="modal-title"><?php get_lang_string('chng_pass');?></h4>
            </div>
            <div class="modal-body">
                <p><?php get_lang_string('enter_email');?></p>
                <input id="email_reset" name="identity" placeholder="<?php get_lang_string('email');?>" data-validation-engine="validate[required,custom[email]]" autocomplete="off" class="form-control placeholder-no-fix" >
            </div>
            <div class="modal-footer">
                <button data-dismiss="modal" class="btn btn-primary" type="button"><?php get_lang_string('cancel');?></button>
                <input data-dismiss="modal" id="pass_reset" class="btn btn-success" type="submit" value="<?php get_lang_string('send');?>"/>
            </div>
        </div>
    </div>
</div>
</form>
<!-- modal -->

<!--div class="container">
  <div class="info">
    <h3>Baby Health Care</h3>
  </div>
</div-->
<div class="form" >
  <div class="info form-header">
    Baby Health Care
  </div>
  <div >
    <img height="150" style="padding:20px 20px" src="<?php echo base_url('assets/images/baby2.svg') ?>"/>
  </div>

    <form class="register-form"dir"ltr" accept-charset="utf-8" action="<?php echo site_url('auth/create_user') ?>" method="post">

      <div class="inner-addon right-addon">
        <input type="email" class="form-control inputs" name="email" placeholder="<?php get_lang_string('email');?>" data-validation-engine="validate[required,custom[email]]"autofocus requierd>
        <i class="glyphicon glyphicon-envelope"></i>
      </div>
      <div class="inner-addon right-addon">
        <input type="password" class="form-control inputs" name="password" placeholder="<?php get_lang_string('password');?>" data-validation-engine="validate[required,minSize[8],maxSize[50]]" />
        <i class="glyphicon glyphicon-lock"></i>
      </div>
      <!-- <div class="inner-addon right-addon">
        <input type="number" class="form-control inputs" name="associational_number" placeholder="Associational Number" data-validation-engine="validate[required,minSize[7],maxSize[7]]" />
        <i class="glyphicon glyphicon-tags"></i>
      </div> -->
      <div class="checkbox3 checkbox-primary checkbox-inline checkbox-check checkbox-round  ">
        <input type="checkbox" id="check1" name="agree" value="option1" data-validation-engine="validate[required]">
          <label for="check1">
            <?php get_lang_string('agree_terms');?>
          </label>
      </div>

      <input type="submit" class="form-control submits btn btn-primary"value="<?php get_lang_string('create');?>"/>
      <p class="message"><?php get_lang_string('have_account');?> <a href="#"><?php get_lang_string('login');?></a></p>
    </form>

    <form class="login-form"dir"ltr" accept-charset="utf-8"  action="<?php echo site_url('auth/login') ?>"  method="post">
      <div class="inner-addon right-addon">
        <input  id="email_login" class="form-control inputs" type="text" name="email" placeholder="<?php get_lang_string('email');?>" data-validation-engine="validate[required,custom[email]]">
        <i class="glyphicon glyphicon-envelope"></i>
      </div>
      <div class="inner-addon right-addon">
        <input  id="password_login" class="form-control inputs" type="password" name="password" placeholder="<?php get_lang_string('password');?>" data-validation-engine="validate[required,minSize[8],maxSize[50]" />
        <i class="glyphicon glyphicon-lock"></i>
      </div>
      <div class="checkbox3 checkbox-primary checkbox-inline checkbox-check checkbox-round ">
        <input  type="checkbox" id="check2" name="remember" value="option2" checked>
        <label for="check2">
          <?php get_lang_string('stay_logged_in');?>
        </label>
      </div>
      <!-- <a style="display: inline-block;" data-toggle="modal" href="login.html#myModal" class="login-a"> <?php get_lang_string('forgot_password');?></a> -->
      <input  id="submit_login" class="form-control submits btn btn-primary" type="submit" value="<?php get_lang_string('login');?>"/>
      <?php if(isset($error)): ?>
        <h6 style="color:#e74c3c"> <?php if (isset($error)) {echo $error;} ?> </h6>
      <?php  endif; ?>
      <p class="message"><?php get_lang_string('not_reg');?> <a href="#"><?php get_lang_string('create');?></a></p>
    </form>
</div>

<script type="text/javascript">
$('#pass_reset').on('click',function(){
             $.ajax({
                 url :'<?php echo site_url("auth/forgot_password"); ?>',
                 type: "POST",
                 data : {
                   identity : $("#email_reset").val(),
                 },
                 success: function(data)
                         {

                              },
                 error: function (xhr, ajaxOptions, thrownError) {
                        // alert(xhr.status);
                        // alert(xhr.responseText);
                        // alert(thrownError);
                      }

             });

         });
</script>

</body>
