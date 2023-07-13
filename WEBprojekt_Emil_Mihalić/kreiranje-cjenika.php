<?php
  include("head.php");

  if($_SESSION["tip"] != 1) {
    header("Location:index.php");
  }
?>

<?php
if(isset($_POST['submit'])) {
  $skola = $_POST['skola2'];
  $sati = $_POST['sati'];
  $cijena = $_POST['cijena'];

  $sql = $bp->updateDB("INSERT INTO cjenik (id_skola, sati, cijena) VALUES ('$skola','$sati','$cijena')");

  $vrijeme = date("Y-m-d h:i:s");
  $opis = "Kreiran novi cjenik";
  $korisnik = $_SESSION['id'];
  $bp->updateDB("INSERT INTO dnevnik (id_korisnik, opis, datum_i_vrijeme) VALUES ('$korisnik','$opis','$vrijeme')");

  if($sql) {
    echo '<span class="error">Cjenik je kreiran!</span>';
  }
}
  
?>


<form action="kreiranje-cjenika.php" method="post" id="cjenik" class="registracija">
  <div class="container">
    <h1>Novi cjenik</h1>
    <p style="text-align: center;">Molimo Vas da popunite sve podatke kako bi kreirali novi cjenik</p>
    <hr>

    <label for="skola2"><b>Skola</b></label>
    <select name="skola2" id="skola2">
    <?php
    
      $sql = $bp->selectDB("SELECT id_skola,naziv FROM plesna_skola");

      while(list($id_skola, $naziv)=mysqli_fetch_array($sql)) {
        echo '<option value="'.$id_skola.'">'.$naziv.'</option>';
      }
    
    ?>
    </select>

    <label for="sati"><b>Sati</b></label>
    <input type="number" name="sati" id="sati" required>

    <label for="cijena"><b>Cijena</b></label>
    <input type="number" name="cijena" id="cijena" required>

    <button type="submit" name="submit" class="registerbtn button button2">Kreiraj</button>
  </div>
</form>


<?php
  include("foot.php");
?>