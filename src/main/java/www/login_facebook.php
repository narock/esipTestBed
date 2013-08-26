<?php

require 'facebook-api-php-client/src/facebook.php';

$facebook = new Facebook(array(
	'appId' => '',
	'secret' => '',
	'cookie' => true,
));

// retrieve user id - if 0 then user is not logged in
$uid = $facebook->getUser();

// requesting 'me' to API
$me = null;
if ($uid != 0) {
  try {
	$me = $facebook->api('/me');
  } catch (FacebookApiException $e) {
   	error_log($e);
  }
}

// login or logout
if ($me) {
  $logout = 'http://' . $_SERVER['HTTP_HOST'] . '/logout_facebook.php';
  $logoutUrl = $facebook->getLogoutUrl(array('next'=>$logout));
} else {
  $loginUrl = $facebook->getLoginUrl();
}

?>

<!doctype html>
<html xmlns:fb="http://www.facebook.com/2008/fbml">
  <head>
    <title>Facebook Login</title>
  </head>
  <body>
    <?php if ($me): ?>
    <?php
      echo "<script type=\"text/javascript\">window.location=\"http://ec2-54-225-124-3.compute-1.amazonaws.com:8080/abstracts/Abstracts?Login=facebook&FirstName=" . $me['first_name'] . "&" . "LastName=" . $me['last_name'] . "\"</script>";
    ?>
    <!-- <a href="<?php echo $logoutUrl; ?>">Logout</a> -->
    <?php else: ?>
      <?php echo "<script type=\"text/javascript\">window.location=\"" . $loginUrl . "\"</script>"; ?>
    <?php endif ?>
   </body>
</html>
