<?php
  include("head.php");

  if($_SESSION["tip"] != 1) {
    header("Location:index.php");
  }
?>

<?php
if(isset($_POST['submit'])) {
  $naziv = $_POST['naziv_skole'];
  $moderator = $_POST['moderator'];

  $sql = $bp->updateDB("INSERT INTO plesna_skola (naziv) VALUES ('$naziv')");

  $skola = $bp->selectDB("SELECT id_skola FROM plesna_skola WHERE naziv='$naziv'");

  list($id_skola)=mysqli_fetch_array($skola);

  $poveznica = $bp->updateDB("INSERT INTO mentor (id_mentor, id_skola, o_meni, broj_polaznika) VALUES ('$moderator', '$id_skola', ' ', 0)");

  $vrijeme = date("Y-m-d h:i:s");
  $opis = "Kreirana nova škola";
  $korisnik = $_SESSION['id'];
  $bp->updateDB("INSERT INTO dnevnik (id_korisnik, opis, datum_i_vrijeme) VALUES ('$korisnik','$opis','$vrijeme')");

  if($sql) {
    echo '<span class="error">Škola je kreirana!</span>';
  }
}
  
?>


<form action="kreiranje-skola.php" method="post" id="skola" class="registracija">
  <div class="container">
    <h1>Nova plesna škola</h1>
    <p style="text-align: center;">Molimo Vas da popunite sve podatke kako bi kreirali novu plesnu školu</p>
    <hr>

    <label for="naziv_skole"><b>Naziv škole</b></label>
    <input type="text" placeholder="Unesite Naziv Skole" name="naziv_skole" id="naziv_skole" required>

    <label for="moderator"><b>Moderator</b></label>
    <select name="moderator" id="moderator">

    <?php
    
      $sql = $bp->selectDB("SELECT id_korisnik,korisnicko_ime FROM korisnik WHERE tip_korisnika=2");

      while(list($id_korisnik, $korisnicko_ime)=mysqli_fetch_array($sql)) {
        echo '<option value="'.$id_korisnik.'">'.$korisnicko_ime.'</option>';
      }
    
    ?>

    </select>


    <button type="submit" name="submit" class="registerbtn button button2">Kreiraj</button>
  </div>
</form>


<?php
  include("foot.php");
?>