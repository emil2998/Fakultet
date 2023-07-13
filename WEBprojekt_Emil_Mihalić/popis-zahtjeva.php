<?php 
include('head.php'); 

if($_SESSION["tip"] != 1 && $_SESSION["tip"] != 2) {
  header("Location:index.php");
}
?>


<?php 

if(isset($_GET['prihvati'])) {
  $prihvati = $_GET['prihvati'];
  $polaznik = $_GET['id_polaznik'];
  $mentor = $_SESSION["id"];

  $sql = "UPDATE zahtjevi SET status_zahtjeva=1 WHERE id_zahtjev='$prihvati'";
  $rs = $bp->updateDB($sql);
  
  $sql_racun = "INSERT INTO racun (id_zahtjev, status_racuna) VALUES ('$prihvati', 0)";
  $sql_racun_rs = $bp->updateDB($sql_racun);
  
  $unos = $bp->updateDB("INSERT INTO mentor_polaznik (id_mentor, id_polaznik) VALUES ('$mentor','$polaznik')");

  header("Location: popis-zahtjeva.php");
} else if(isset($_GET['odbij'])) {
  $odbij = $_GET['odbij'];

  $sql = "UPDATE zahtjevi SET status_zahtjeva=2 WHERE id_zahtjev='$odbij'";
  $rs = $bp->updateDB($sql);
}
?>

<table>
  <tr>
    <th>Ime polaznika</th>
    <th>Prezime polaznika</th>
    <th>Skola</th>
    <th>Cjenik</th>
    <th>Zivotopis</th>
  </tr>

  <?php
    $mentor = $_SESSION['id'];

    $zahtjev = $bp->selectDB("SELECT * FROM zahtjevi WHERE id_mentor='$mentor' AND status_zahtjeva=0");

    while(list($id_zahtjev,$id_mentor, $id_polaznik,$id_cjenik, $zivotopis, $status_zahtjeva)=mysqli_fetch_array($zahtjev)) {
      $polaznik = $bp->selectDB("SELECT ime, prezime FROM korisnik WHERE id_korisnik='$id_polaznik'");
      list($ime, $prezime)=mysqli_fetch_array($polaznik);

      $cjenik = $bp->selectDB("SELECT id_skola, sati, cijena FROM cjenik WHERE id_cjenik='$id_cjenik'");
      list($id_skola, $sati, $cijena)=mysqli_fetch_array($cjenik);

      $skola = $bp->selectDB("SELECT naziv FROM plesna_skola WHERE id_skola='$id_skola'");
      list($naziv)=mysqli_fetch_array($skola);

      echo '<tr>';

      echo '<td>'.$ime.'</td>';

      echo '<td>'.$prezime.'</td>';

      echo '<td>'.$naziv.'</td>';

      echo '<td>'.$sati.'h - '.$cijena.'kn</td>';

      echo '<td>'.$zivotopis.'</td>';

      if($status_zahtjeva == 0) {
        echo '<td><a href="popis-zahtjeva.php?prihvati='.$id_zahtjev.'&id_polaznik='.$id_polaznik.'">Prihvati</a><br><br><a href="popis-zahtjeva.php?odbij='.$id_zahtjev.'">Odbij</a></td>';

      }

      echo '</tr>';
    }
  ?>

</table>

<?php include('foot.php'); ?>