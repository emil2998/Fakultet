<?php
  include("head.php");

  if($_SESSION["tip"] != 1 && $_SESSION["tip"] != 2) {
    header("Location:index.php");
  }
?>

<?php
if(isset($_POST['submit'])) {
  $o_meni = $_POST['o_meni'];
  $polaznici = $_POST['broj_polaznika_maks'];
  $korisnik = $_SESSION["id"];

  $sql = $bp->updateDB("UPDATE mentor SET o_meni='$o_meni', broj_polaznika_maks='$polaznici' WHERE id_mentor='$korisnik'");

  if($sql) {
    echo '<span class="error">Moji podaci su ažurirani!</span>';
  } else {
    echo '<span class="error">Mentor nije registriran u tablici mentora!</span>';
  }
}
  
?>


<form action="moji-podaci.php" method="post" id="podaci" class="registracija">
  <div class="container">
    <h1>Moji podaci</h1>
    <p style="text-align: center;">Molimo Vas da popunite sve podatke</p>
    <hr>

    <label for="o_meni"><b>O Meni</b></label>
    <textarea type="text" placeholder="Unesite Neke Podatke O Sebi" name="o_meni" id="o_meni" rows="10" required></textarea>

    <label for="broj_polaznika_maks"><b>Broj polaznika</b></label>
    <input type="number" name="broj_polaznika_maks" id="broj_polaznika_maks" required>

    <button type="submit" name="submit" class="registerbtn button button2">Ažuriraj</button>
  </div>
</form>


<?php
  include("foot.php");
?>