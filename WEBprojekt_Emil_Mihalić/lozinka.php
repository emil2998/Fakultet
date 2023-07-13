<?php
  include("head.php");

  if(isset($_SESSION["id"])) {
    header("Location:index.php");
  }
?>

<?php
if(!isset($_GET['email']) && isset($_POST['submit'])) {
  $sql = $bp->selectDB("SELECT * FROM korisnik WHERE email  = '".$_POST['email']."'");

  if(mysqli_num_rows($sql) > 0) {
    $mail_to = $_POST['email'];
    $mail_from = "From: WebDiP_2020@foi.hr";
    $mail_subject = "Promjena lozinke";
    $link = "https://barka.foi.hr/WebDiP/2019_projekti/WebDiP2019x084/lozinka.php" . "?email=" . $_POST['email'] . "";
    $mail_body = "Za promjenu lozinke: " . $link . " ";

    if (mail($mail_to, $mail_subject, $mail_body, $mail_from)) {
      echo 'Provjerite email kako bi resetirali lozinku!';
    } else {
      echo 'Email nije poslan!';
    }
  } else {
    echo '<span class="error">Email ne postoji u bazi! Pokusajte drugi unos</span>';
  }
}
  
?>

<?php

if(isset($_GET['email'])) {
  $email = $_GET['email'];
}

if(isset($_POST['submitNova'])) {
  if($_POST['lozinka'] == $_POST['ponovljena']) {
    $sol = sha1(time());
    $kriptirana = sha1($sol . "--" . $_POST['lozinka']);

    $sql = "UPDATE korisnik SET lozinka='".$_POST['lozinka']."', kriptirana_lozinka='$kriptirana' WHERE id_korisnik='".$_GET['id']."'";
    $bp->updateDB($sql);

    $vrijeme = date("Y-m-d h:i:s");
    $opis = "Lozinka promjenjena";
    $korisnik = $_GET['id'];
    $bp->updateDB("INSERT INTO dnevnik (id_korisnik, opis, datum_i_vrijeme) VALUES ('$korisnik','$opis','$vrijeme')");

    header("Location: prijava.php");
  } else {
    echo '<span class="error">Lozinke nisu iste! Ponovite unos</span>';
  }
}

if(isset($_GET['email'])) { 
  $rs_id = $bp->selectDB("SELECT id_korisnik FROM korisnik WHERE email  = '".$_GET['email']."'");
  list($id_korisnik)=mysqli_fetch_array($rs_id);

  echo '<form action="lozinka.php?id='.$id_korisnik.'" method="post" id="lozinka" class="registracija">';
  echo '<div class="container">';
    echo '<h1>Zaboravljena lozinka</h1>';
    echo '<p>Molimo Vas da popunite sve podatke</p>';
    echo '<hr>';

    echo '<span id="js-lozinka" class="error"></span>';
    echo '<label for="lozinka"><b>Lozinka</b></label>';
    echo '<input type="password" placeholder="Unesite Lozinku" name="lozinka" id="lozinka" required>';

    echo '<span id="js-ponovljena" class="error"></span>';
    echo '<label for="ponovljena"><b>Ponovljena Lozinka</b></label>';
    echo '<input type="password" placeholder="Ponovljena Lozinka" name="ponovljena" id="ponovljena" required>';
    echo '<hr>';

    echo '<button type="submit" name="submitNova" class="registerbtn button button2">Pošalji</button>';
  echo '</div>';
echo '</form>';

  
} else {
  echo '<form action="lozinka.php" method="post" id="lozinka" class="registracija">';
    echo '<div class="container">';
      echo '<h1>Zaboravljena lozinka</h1>';
      echo '<p style="text-align: center;">Molimo Vas da popunite sve podatke kako bi za zahtjev za promjenom lozinke poslao na vaš mail</p>';
      echo '<hr>';

      echo '<span id="js-email" class="error"></span>';
      echo '<label for="email"><b>Email</b></label>';
      echo '<input type="text" placeholder="Unesite Email" name="email" id="email" required>';

      echo '<button type="submit" name="submit" class="registerbtn button button2">Pošalji</button>';
    echo '</div>';
  echo '</form>';
}


?>



<?php
  include("foot.php");
?>