<?php
  include("head.php");

  if($_SESSION["tip"] != 1 && $_SESSION["tip"] != 2) {
    header("Location:index.php");
  }
?>

<?php
if(isset($_POST['submit'])) {
  $mentor = $_SESSION["id"];
  $polaznik = $_POST['polaznik'];
  $naziv = $_POST['naziv'];
  $opis = $_POST['opis'];
  $video = $_POST['video'];

  $sql = $bp->updateDB("INSERT INTO termin (id_mentor, id_polaznik, naziv, opis, video) VALUES ('$mentor','$polaznik','$naziv', '$opis', '$video')");

  $vrijeme = date("Y-m-d h:i:s");
  $opis = "Kreiran novi termin";
  $korisnik = $_SESSION['id'];
  $bp->updateDB("INSERT INTO dnevnik (id_korisnik, opis, datum_i_vrijeme) VALUES ('$korisnik','$opis','$vrijeme')");

  if($sql) {
    echo '<span class="error">Termin je kreiran!</span>';
  }
}
  
?>


<form action="kreiranje-termina.php" method="post" id="termin" class="registracija">
  <div class="container">
    <h1>Novi termin</h1>
    <p style="text-align: center;">Molimo Vas da popunite sve podatke kako bi kreirali novi termin</p>
    <hr>

    <label for="polaznik"><b>Polaznik</b></label>
    <select name="polaznik" id="polaznik">
    <?php
    
      $sql = $bp->selectDB("SELECT id_polaznik FROM mentor_polaznik WHERE id_mentor='".$_SESSION["id"]."'");

      while(list($id_polaznik)=mysqli_fetch_array($sql)) {
        $polaznik = $bp->selectDB("SELECT korisnicko_ime FROM korisnik WHERE id_korisnik='$id_polaznik'");
        list($korisnicko_ime)=mysqli_fetch_array($polaznik);

        echo '<option value="'.$id_polaznik.'">'.$korisnicko_ime.'</option>';
      }
    
    ?>
    </select>

    <label for="naziv"><b>Naziv</b></label>
    <input type="text" placeholder="Unesite naziv termina" name="naziv" id="naziv" required>

    <label for="opis"><b>Opis</b></label>
    <input type="text" placeholder="Unesite opis termina" name="opis" id="opis" required>

    <label for="video"><b>Video</b></label>
    <input type="text" placeholder="Unesite url videa" name="video" id="video" required>

    <button type="submit" name="submit" class="registerbtn button button2">Kreiraj</button>
  </div>
</form>


<?php
  include("foot.php");
?>