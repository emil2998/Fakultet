<?php

 include('head.php'); 
if(Sesija::dajKorisnika() != NULL) {
  header("Location:index.php");
}

if(isset($_GET['korisnicko']) && isset($_GET['kod'])) {
  $sql = "UPDATE korisnik SET status_racuna=1 WHERE korisnicko_ime='".$_GET['korisnicko']."'";
  $rs = $bp->updateDB($sql);

  if($rs) {
    header("Location: index.php");
  } else {
    header("Location: registracija.php");
  }
}
include('foot.php');
?>