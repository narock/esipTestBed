<?php

require 'facebook-api-php-client/src/facebook.php';

$facebook = new Facebook(array(
	'appId' => '542958259055124',
	'secret' => '72fd17f34a2d262f13495cd890f967cd',
	'cookie' => false,
));

// facebook api creates cookies via the api
// overwrite the fbsr cookie and destroy the session
// if we don't remove the cookie then facebook auth info is kept despite logging out
$fb_key = 'fbsr_542958259055124'; // app id from above
setcookie($fb_key, '', time()-3600);
$facebook->destroySession();

//header('Location: ' . 'http://' . $_SERVER['HTTP_HOST'] . '/login_facebook.php');
echo "logged out";

?>
