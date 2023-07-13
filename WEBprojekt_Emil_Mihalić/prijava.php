<?php
  include("head.php");
  
if( !isset($_SERVER['HTTPS'] ) ) {
    header("Location: https:///barka.foi.hr/WebDiP/2019_projekti/WebDiP2019x084/prijava.php");
}

  if( isset($_GET['odjava'] ) ) {
    $vrijeme = date("Y-m-d h:i:s");
    $opis = "Uspješna odjava";
    $korisnik = $_SESSION['id'];
    $bp->updateDB("INSERT INTO dnevnik (id_korisnik, opis, datum_i_vrijeme) VALUES ('$korisnik','$opis','$vrijeme')");

    Sesija::obrisiSesiju();

		header("Location:index.php");
  } else if(Sesija::dajKorisnika() != NULL) {
    Sesija::obrisiSesiju();
  }
?>


<?php
  if(isset($_POST['submit'])) {
    $sql = $bp->selectDB("SELECT id_korisnik,tip_korisnika,status_racuna FROM korisnik WHERE korisnicko_ime  = '".$_POST['korisnicko_ime_prijava']."'");

    if(mysqli_num_rows($sql) > 0) {
      list($id,$tip,$status)=mysqli_fetch_array($sql);

      $lozinka = $bp->selectDB("SELECT id_korisnik,tip_korisnika,status_racuna FROM korisnik WHERE korisnicko_ime  = '".$_POST['korisnicko_ime_prijava']."' AND lozinka='".$_POST['lozinka_prijava']."'");

      if(mysqli_num_rows($lozinka) > 0) {
        if($status != 0 && $status != 3) {
          $update = "UPDATE korisnik SET status_racuna=1 WHERE korisnicko_ime='".$_POST['korisnicko_ime_prijava']."'";
          $bp->updateDB($update);

          $vrijeme = date("Y-m-d h:i:s");
          $opis = "Uspješna prijava";
          $bp->updateDB("INSERT INTO dnevnik (id_korisnik, opis, datum_i_vrijeme) VALUES ('$id','$opis','$vrijeme')");
          Sesija::kreirajKorisnika($_POST['korisnicko_ime_prijava'], $tip, $id);

          header("Location: index.php");
        } else if($status == 0) {
          echo '<span class="error">Korisnicki racun nije aktiviran</span>';
        } else if($status == 3){
          echo '<span class="error">Korisnicki racun je zakljucan</span>';
        }
      } else {
        echo '<span class="error">Lozinka nije dobra! Ponovite unos</span>';

        $vrijeme = date("Y-m-d h:i:s");
        $opis = "Unos pogrešne lozinke";
        $bp->updateDB("INSERT INTO dnevnik (id_korisnik, opis, datum_i_vrijeme) VALUES ('$id','$opis','$vrijeme')");

        if($status == 1) {
          $update = "UPDATE korisnik SET status_racuna=4 WHERE korisnicko_ime='".$_POST['korisnicko_ime_prijava']."'";
          $bp->updateDB($update);
          $vrijeme = date("Y-m-d h:i:s");
          $opis = "Uspješna prijava";
          Sesija::kreirajKorisnika($_POST['korisnicko_ime_prijava'], $tip, $id);
          $bp->updateDB("INSERT INTO dnevnik (id_korisnik, opis, datum_i_vrijeme) VALUES ('$id','$opis','$vrijeme')");
        } else if($status == 4) {
          $update = "UPDATE korisnik SET status_racuna=5 WHERE korisnicko_ime='".$_POST['korisnicko_ime_prijava']."'";
          $bp->updateDB($update);
        } else if($status == 5) {
          $update = "UPDATE korisnik SET status_racuna=3 WHERE korisnicko_ime='".$_POST['korisnicko_ime_prijava']."'";
          $bp->updateDB($update);

          $vrijeme = date("Y-m-d h:i:s");
          $opis = "Korisnički račun je zaključan";
          $bp->updateDB("INSERT INTO dnevnik (id_korisnik, opis, datum_i_vrijeme) VALUES ('$id','$opis','$vrijeme')");
        } else if($status == 3) {
          echo '<span class="error">Korisnicki racun je zakljucan</span>';
        }
      }
    } else {
      echo '<span class="error">Korisnicko ime nije dobro! Ponovite unos</span>';
    }
  }
?>

<form action="prijava.php" method="post" id="prijava" class="registracija">
  <div class="container">
    <h1>Prijava</h1>
    <p style="text-align: center;">Molimo Vas da popunite sve podatke</p>
    <hr>

    <span id="js-korisnicko-prijava" class="error"></span>
    <span id="dostupnost"></span>
    <label for="korisnicko_ime_prijava"><b>Korisnicko ime</b></label>
    <input type="text" placeholder="Unesite Korisnicko Ime" name="korisnicko_ime_prijava" id="korisnicko_ime_prijava" required>


    <span id="js-lozinka-prijava" class="error"></span>
    <label for="lozinka_prijava"><b>Lozinka</b></label>
    <input type="password" placeholder="Unesite Lozinku" name="lozinka_prijava" id="lozinka_prijava" required>
    <hr>

    <div class="container signin">
      <p>Zaboravili ste lozinku? Kliknite <a href="lozinka.php">ovdje</a>.</p>
    </div>
    <button type="submit" name="submit" class="registerbtn button button2">Prijava</button>
  </div>
</form>


<?php
  include("foot.php");
?>