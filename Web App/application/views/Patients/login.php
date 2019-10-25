<!DOCTYPE html>
<html lang="en" dir="rtl">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="Dashboard">
    <meta name="keyword" content="Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">

    <title>DASHGUM - Bootstrap Admin Template</title>

    <!-- Bootstrap core CSS -->
    <link href="assets/css/bootstrap.css" rel="stylesheet">
    <!--external css-->
    <link href="assets/font-awesome/css/font-awesome.css" rel="stylesheet" />
        
    <!-- Custom styles for this template -->
    <link href="assets/css/style.css" rel="stylesheet">

    <link href="assets/css/doctor.css" rel="stylesheet">
    <link href="assets/css/color-sheet1.css" rel="stylesheet">
    <link href="assets/css/style-responsive.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>

  <body id="login" src="assets/img/login-body-backgruond.jpg">

      <!-- **********************************************************************************************************************************************************
      MAIN CONTENT
      *********************************************************************************************************************************************************** -->

	  <div id="login-background">
	  	<div class="container">
		      <form class="form-login" action="index.html">
		        <h2 class="form-login-heading">تسجيل الدخول</h2>
		        <div >
		        	<img src="assets/img/baby2.svg" id="thumbnail-image"/>
	  				<!--span class="fa-stack fa-4x"  aria-expanded="true" >
                      <i class="fa fa-circle fa-stack-2x text-primary"></i>
                      <i class="fa fa-laptop fa-stack-1x fa-inverse"></i>                    
                    </span-->
                </div>
		        <div class="login-wrap">
		        <div class="inner-addon left-addon">				
		            <input type="text" lang="en" class="form-control" placeholder="البريد الإلكتروني" autofocus requierd>
		            <i class="glyphicon glyphicon-envelope" ></i>
		        </div>
		        <br>
		        <div class="inner-addon left-addon">
		            <input type="password" class="form-control" placeholder="كلمة المرور" requierd>
		            <i class="glyphicon glyphicon-lock"></i>
		        </div>
		            <label class="checkbox">
		                <span class="pull-right">
		                    <a data-toggle="modal" href="login.html#myModal" class="login-a"> هل نسيت كلمة المرور؟</a>
		
		                </span>
		            </label>
		            <button class="btn btn-theme btn-block" href="index.html" type="submit"> تسجيل الدخول <!--i class="fa fa-key fa-fw fa-flip-horizontal " aria-hidden="true"></i--></button>
		            <hr>
		            
		            <!--div class="login-social-link centered">
		            <p>or you can sign in via your social network</p>
		                <button class="btn btn-facebook" type="submit"><i class="fa fa-facebook"></i> Facebook</button>
		                <button class="btn btn-twitter" type="submit"><i class="fa fa-twitter"></i> Twitter</button>
		            </div-->
		            <div class="registration">
		                ليس لديك حساب؟<br/>
		                <a class="login-a" href="#">
		                    أنشئ حساب
		                </a>
		            </div>
		
		        </div>
		</form>
		          <!-- Modal -->
		          <form>
		          <div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="myModal" class="modal fade">
		              <div class="modal-dialog">
		                  <div class="modal-content">
		                      <div class="modal-header">
		                          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		                          <h4 class="modal-title">Forgot Password ?</h4>
		                      </div>
		                      <div class="modal-body">
		                          <p>Enter your e-mail address below to reset your password.</p>
		                          <input type="text" name="email" placeholder="Email" autocomplete="off" class="form-control placeholder-no-fix">
		
		                      </div>
		                      <div class="modal-footer">
		                          <button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>
		                          <button class="btn btn-theme" type="button">Submit</button>
		                      </div>
		                  </div>
		              </div>
		          </div>
		          <!-- modal -->
		
		      </form>	  	
	  	
	  	</div>
	  </div>

    <!-- js placed at the end of the document so the pages load faster -->
    <script src="assets/js/jquery.js"></script>
    <script src="assets/js/bootstrap.min.js"></script>

    <!--BACKSTRETCH-->
    <!-- You can use an image of whatever size. This script will stretch to fit in any screen size.-->
    <script type="text/javascript" src="assets/js/jquery.backstretch.min.js"></script>
    <script>
       $.backstretch("assets/img/login-body-backgruond.jpg", {speed: 500});
    </script>


  </body>
</html>
