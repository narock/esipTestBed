<?php

  $time = time();
  $outFile = "/tmp/logs/" . $time . ".log";
  if (!$fh = fopen($outFile, 'w') ){
    echo "Cannot open file($outFile)";
    exit;
  }
  foreach($_POST as $key => $val) {
    if (fwrite($fh, $key . ',' . $val . "\n") === FALSE) {
      echo "Cannot write to file $outFile";
      exit;
    }
  }
  fclose($fh);
 
  $host = 'http://ec2-54-225-124-3.compute-1.amazonaws.com/';
  echo "Thanks You. Your submissions have been recorded.<br>";
  if ( $_POST['login'] == 'facebook' ) { echo "<a href='" . $host . "logout_facebook.php'>Logout of Facebook</a>"; }
  if ( $_POST['login'] == 'gplus' )    { echo "<a href='" . $host . "logout_gplus.php'>Logout of Google Plus</a>"; }

?>
