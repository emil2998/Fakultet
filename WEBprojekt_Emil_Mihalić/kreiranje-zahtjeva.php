<?php
  include("head.php");

  if($_SESSION["tip"] != 1 && $_SESSION["tip"] != 2 && $_SESSION["tip"] != 3) {
    header("Location:index.php");
  }
?>

<?php
if(isset($_POST['submit'])) {
  $polaznik = $_SESSION["id"];
  $mentor = $_GET['id_mentor'];
  $cjenik = $_POST['cjenik'];
  $zivotopis = $_POST['zivotopis'];

  $zahtjev = $bp->updateDB("INSERT INTO zahtjevi (id_mentor, id_polaznik, id_cjenik, zivotopis) VALUES ('$mentor','$polaznik','$cjenik', '$zivotopis')");

  $vrijeme = date("Y-m-d h:i:s");
  $opis = "Kreiran novi zahtjev";
  $korisnik = $_SESSION['id'];
  $bp->updateDB("INSERT INTO dnevnik (id_korisnik, opis, datum_i_vrijeme) VALUES ('$korisnik','$opis','$vrijeme')");

  if($zahtjev) {
    echo '<span class="error">Zahtjev je poslan!</span>';
  }
}
  
?>


<form action="kreiranje-zahtjeva.php<?php if(isset($_GET['id_mentor'])) { echo '?id_mentor='.$_GET['id_mentor'].'';} if(isset($_GET['id_skola'])) { echo '&id_skola='.$_GET['id_skola'].'';}?>" method="post" id="zahtjev" class="registracija">
  <div class="container">
    <h1>Novi zahtjev za mentorstvom</h1>
    <p style="text-align: center;">Molimo Vas da popunite sve podatke kako bi kreirali novi zahtjev</p>
    <hr>

    <label for="mentor"><b>Slobodni mentori</b></label>
    <select name="mentor" id="mentor">
    <?php
    
      $sql = $bp->selectDB("SELECT id_mentor, id_skola FROM mentor WHERE broj_polaznika >= 0 AND broj_polaznika<broj_polaznika_maks");

      while(list($id_mentor, $id_skola)=mysqli_fetch_array($sql)) {
        $mentor = $bp->selectDB("SELECT korisnicko_ime FROM korisnik WHERE id_korisnik='$id_mentor'");
        list($korisnicko_ime)=mysqli_fetch_array($mentor);

        $skola = $bp->selectDB("SELECT naziv FROM plesna_skola WHERE id_skola='$id_skola'");
        list($naziv)=mysqli_fetch_array($skola);

        if(isset($_GET['id_skola']) && $_GET['id_skola'] == $id_skola) {
          echo '<option selecter="selected" value="kreiranje-zahtjeva.php?id_mentor='.$id_mentor.'&id_skola='.$id_skola.'">'.$korisnicko_ime.' - '.$naziv.'</option>';
        } else {
          echo '<option value="kreiranje-zahtjeva.php?id_mentor='.$id_mentor.'&id_skola='.$id_skola.'">'.$korisnicko_ime.' - '.$naziv.'</option>';
        }
      }

    ?>
    </select>

    <label for="cjenik"><b>Cjenik</b></label>
    <select name="cjenik" id="cjenik">
    <?php
    
      if(isset($_GET['id_skola'])) {
        $skola_id = $_GET['id_skola'];
      }

      $cjenik = $bp->selectDB("SELECT id_cjenik, sati, cijena FROM cjenik WHERE id_skola='$skola_id'");

      while(list($id_cjenik, $sati, $cijena)=mysqli_fetch_array($cjenik)) {
        echo '<option value="'.$id_cjenik.'">'.$sati.'h - '.$cijena.'</option>';
      }
    ?>
    </select>

    <label for="zivotopis"><b>Zivotopis</b></label>
    <textarea placeholder="Unesite kratki zivotopis" name="zivotopis" id="zivotopis" rows="10" required></textarea>

    <button type="submit" name="submit" class="registerbtn button button2">Kreiraj</button>
  </div>
</form>


<?php
  include("foot.php");
?>