<?php 

include('head.php'); 

if($_SESSION["tip"] != 1) {
  header("Location:index.php");
}

?>

<?php
  
    if(isset($_GET['otkljucaj'])) {
      $korisnik = $_GET['otkljucaj'];
    } else if(isset($_GET['zakljucaj'])) {
      $korisnik = $_GET['zakljucaj'];
    } else if(isset($_GET['odblokiraj'])) {
      $korisnik = $_GET['odblokiraj'];
    } else if(isset($_GET['blokiraj'])) {
      $korisnik = $_GET['blokiraj'];
    }

    $sql = $bp->selectDB("SELECT id_korisnik FROM korisnik WHERE korisnicko_ime='$korisnik'");
    list($id_korisnik)=mysqli_fetch_array($sql);


    if(isset($_GET['otkljucaj']) || isset($_GET['odblokiraj'])) {
      $update = "UPDATE korisnik SET status_racuna=1 WHERE id_korisnik='$id_korisnik'";
    } else if(isset($_GET['zakljucaj'])) {
      $update = "UPDATE korisnik SET status_racuna=3 WHERE id_korisnik='$id_korisnik'";
    } else if(isset($_GET['blokiraj'])) {
      $update = "UPDATE korisnik SET status_racuna=7 WHERE id_korisnik='$id_korisnik'";
    }

    $rs = $bp->updateDB($update);

    if($rs) {
      header("Location: korisnicki-racuni.php");
    }
  ?>

<?php include('foot.php'); ?>