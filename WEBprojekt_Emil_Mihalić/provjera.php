<?php

include('baza.class.php');
$bp = new Baza();
$bp->spojiDB();

if ($bp->pogreskaDB()) {
  echo "Problem kod upita na bazu podataka!";
  exit;
}

?>


<?php

if(isset($_POST['korisnicko']) || isset($_POST['korisnickoPrijava'])){
  if(isset($_POST['korisnickoPrijava'])) {
    $korisnicko  = $_POST["korisnickoPrijava"];
  } else {
    $korisnicko  = $_POST['korisnicko'];
  }
  $sql = $bp->selectDB("SELECT * FROM korisnik WHERE korisnicko_ime  = '$korisnicko'");

  if(mysqli_num_rows($sql) > 0) {
    if(isset($_POST['korisnickoPrijava'])) {
      echo '';
    } else {
      echo '<span class="text-error">Korisnicko ime postoji, pokusajte neko drugo!</span>';
    }
  } else {
    if(isset($_POST['korisnickoPrijava'])) {
      echo '<span class="text-error">Korisnicko ne postoji!</span>';
    } else {
      echo '<span class="text-success"></span>';
    }
  }
}

?>


<?php
 $bp->zatvoriDB();
?>