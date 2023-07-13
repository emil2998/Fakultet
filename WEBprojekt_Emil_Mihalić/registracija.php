<?php 

include('head.php'); 

if(Sesija::dajKorisnika() != NULL) {
  header("Location:index.php");
}

?>

<?php


if(isset($_POST['submit'])) {
  $ime = $_POST['ime'];
  $prezime = $_POST['prezime'];
  $korisnicko_ime = $_POST['korisnicko_ime'];
  $email = $_POST['email'];
  $lozinka = $_POST['lozinka'];
  $ponovljena = $_POST['ponovljena'];
  $response = $_POST["g-recaptcha-response"];
  $url = 'https://www.google.com/recaptcha/api/siteverify';
  

  if(strlen($ime) < 3) {
    echo 'Unesite ime duze od 2 slova';
    return false;
    
  }

  if(strlen($prezime) < 3) {
    echo 'Unesite prezime duze od 2 slova';
    return false;
  }

  if(strlen($korisnicko_ime) < 5) {
    echo 'Unesite korisnicko ime duze od 4 slova';
    return false;
  }

  if($lozinka != $ponovljena) {
    echo 'Lozinke nisu iste, ponovite unos';
    return false;
  }

  if(!preg_match("/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}$/", $lozinka)) {
    echo 'Minimalna duzina lozinke je 5 te mora sadrzavati jedan broj, malo i veliko slovo';
    return false;
  }

  if(!preg_match("/^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i", $email)) {
    echo 'Unesite validan email!';
    return false;
  }

  $data = array(
		'secret' => '6Lek9aUZAAAAAJslVGvVI2Doo2eAskdT0DFbzjUe',
		'response' => $_POST["g-recaptcha-response"]
	);

	$options = array(
		'http' => array (
			'method' => 'POST',
			'content' => http_build_query($data)
		)
  );
  
	$context  = stream_context_create($options);
	$verify = file_get_contents($url, false, $context);
	$captcha_success=json_decode($verify);

	if ($captcha_success->success==false) {
		echo "<p>Captcha nije uredu!</p>";
	} else if ($captcha_success->success==true) {
		echo "<p>Captcha uredu!</p>";
	}

  $sol = sha1(time());
  $kriptirana = sha1($sol . "--" . $lozinka);

  $vrijeme = sha1(date("Y-m-d h:is"));
  $korisnicko = sha1($korisnicko_ime);
  $emai_kod = sha1($vrijeme . "--" . $korisnicko);

  $sql = "INSERT INTO korisnik(korisnicko_ime, ime, prezime, email, lozinka, kriptirana_lozinka, tip_korisnika, status_racuna, email_kod) 
          VALUES ('$korisnicko_ime', '$ime', '$prezime', '$email', '$lozinka', '$kriptirana', 3, 0, '$emai_kod')";
  $rs = $bp->updateDB($sql);

  $vrijeme = date("Y-m-d h:i:s");
  $opis = "Uspješna registracija";
  $bp->updateDB("INSERT INTO dnevnik (id_korisnik, opis, datum_i_vrijeme) VALUES (0,'$opis','$vrijeme')");

  $mail_to = $email;
  $mail_from = "From: WebDiP_2020@foi.hr";
  $mail_subject = "Aktivacija";
  $link = "https://barka.foi.hr/WebDiP/2019_projekti/WebDiP2019x084/aktivacija.php" . "?korisnicko=" . $korisnicko_ime . "&kod=" . $emai_kod . "";
  $mail_body = "Za aktivaciju racuna: " . $link . " ";

  if (mail($mail_to, $mail_subject, $mail_body, $mail_from)) {
    echo 'Provjerite email kako bi aktivirali racun!';
  } else {
    echo 'Registracija nije uspjesna!';
  }
}

?>

<form action="registracija.php" method="post" id="registracija" class="registracija">
  <div class="container">
    <h1>Registracija</h1>
    <p style="text-align: center;">Molimo Vas da popunite sve podatke</p>
    <hr>

    <span id="js-ime" class="error"></span>
    <label for="ime"><b>Ime</b></label>
    <input type="text" placeholder="Unesite Ime" name="ime" id="ime" required>

    <span id="js-prezime" class="error"></span>
    <label for="prezime"><b>Prezime</b></label>
    <input type="text" placeholder="Unesite Prezime" name="prezime" id="prezime" required>

    <span id="js-korisnicko" class="error"></span>
    <span id="dostupnost"></span>
    <label for="korisnicko_ime"><b>Korisnicko ime</b></label>
    <input type="text" placeholder="Unesite Korisnicko Ime" name="korisnicko_ime" id="korisnicko_ime" required>

    <span id="js-email" class="error"></span>
    <label for="email"><b>Email</b></label>
    <input type="text" placeholder="Unesite Email" name="email" id="email" required>

    <span id="js-lozinka" class="error"></span>
    <label for="lozinka"><b>Lozinka</b></label>
    <input type="password" placeholder="Unesite Lozinku" name="lozinka" id="lozinka" required>

    <span id="js-ponovljena" class="error"></span>
    <label for="ponovljena"><b>Ponovljena Lozinka</b></label>
    <input type="password" placeholder="Ponovljena Lozinka" name="ponovljena" id="ponovljena" required>
    <hr>

    <div class="g-recaptcha" data-sitekey="6Lek9aUZAAAAAB_brwrIzbiEcacCNN-MwhwatlRY"></div>

    <button type="submit" name="submit" class="registerbtn button button2">Registracija</button>
  </div>
  
  <div class="container signin">
    <p>Već imate račun? <a href="prijava.php">Prijavite se</a>.</p>
  </div>
</form>

<?php include('foot.php'); ?>