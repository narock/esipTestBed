<?php

// Google Plus data for this application
// obtained from https://code.google.com/apis/console?api=plus
$googleClientId = "230656974091.apps.googleusercontent.com";
$googleClientSecret = "";
$googleClientRedirect = "http://ec2-54-225-124-3.compute-1.amazonaws.com/login_gplus.php";
$googleDeveloperKey = "AIzaSyAfWYFXf46YpA21rnuAT3mO7z1V2ntTeME";

// Google Plus Scope - what we want back from G+
$scope1 = 'https://www.googleapis.com/auth/userinfo.email';
$scope2 = 'https://www.googleapis.com/auth/userinfo.profile';

require_once 'google-api-php-client/src/Google_Client.php';
require_once 'google-api-php-client/src/contrib/Google_PlusService.php';
require_once 'google-api-php-client/src/contrib/Google_Oauth2Service.php';

session_start();

$client = new Google_Client();
$client->setApplicationName('Google+ Linked Data Authentication Application');
$client->setClientId("$googleClientId");
$client->setClientSecret("$googleClientSecret");
$client->setRedirectUri("$googleClientRedirect");
$client->setDeveloperKey("$googleDeveloperKey");
$client->setScopes(array($scope1,$scope2));
$plus = new Google_PlusService($client);

// redirect url
//$redirect = 'http://' . $_SERVER['HTTP_HOST'] . $_SERVER['PHP_SELF'];

if (isset($_GET['code'])) {
  $client->authenticate();
  $_SESSION['token'] = $client->getAccessToken();
  header('Location: ' . filter_var($googleClientRedirect, FILTER_SANITIZE_URL));
}

if (isset($_SESSION['token'])) {
  $client->setAccessToken($_SESSION['token']);
}

if (isset($_GET['logout'])) {
  unset($_SESSION['token']);
  $client->revokeToken();
  $lurl = 'http://' . $_SERVER['HTTP_HOST'] . '/logout_gplus.php';
  header('Location: ' . filter_var($lurl, FILTER_SANITIZE_URL));
}

if ($client->getAccessToken()) {
  $google_oauthV2 = new Google_Oauth2Service($client);
  $user = $google_oauthV2->userinfo->get();
  $username = filter_var($user['name'], FILTER_SANITIZE_SPECIAL_CHARS);
  $email = filter_var($user['email'], FILTER_SANITIZE_EMAIL);
  $_SESSION['token'] = $client->getAccessToken();
  $names = explode(" ", $username);
  echo "<script type=\"text/javascript\">window.location=\"http://ec2-54-225-124-3.compute-1.amazonaws.com:8080/abstracts/Abstracts?Login=gplus&FirstName=$names[0]&LastName=$names[1]\"</script>";
  //echo "<a href='$googleClientRedirect?logout=1'>Log Out</a>";
} else {
  $authUrl = $client->createAuthUrl();
  echo "<script type=\"text/javascript\">window.location=\"" . $authUrl . "\"</script>";
}
 
?>
