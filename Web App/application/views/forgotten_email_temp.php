<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
	</head>
	<body>
		<p>
			We received an account recovery request on Baby Health Care for <?php echo $identity ?>.<br>
			If you initiated this request, click this link to reset your password : <br>
      <?php echo anchor('auth/reset_password/'. $forgotten_password_code,"Rest password","Rest password") ?>.<br>
			As a reminder, you can use any of the following credentials to log in to your account: Email and Password (<?php echo $identity ?>)<br>
			Note: once logged in, you can review existing credentials and add new ones by visiting your profile, clicking Edit Profile & Settings and My Logins.<br>
			If you did not initiate this account recovery request, just ignore this email. We'll keep your account safe.<br>
		</p>
	</body>
</html>
