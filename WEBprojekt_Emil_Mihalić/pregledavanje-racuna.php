<?php 

include('head.php'); 

if($_SESSION["tip"] != 1 && $_SESSION["tip"] != 2) {
  header("Location:index.php");
}

?>

<?php 

if(isset($_GET['id_racun'])) {
  $mentor = $_SESSION['id'];
  $racun = $_GET['id_racun'];
  $skola = $_GET['id_skola'];

  $sql = "UPDATE racun SET status_racuna=2 WHERE id_racun='$racun'";
  $rs = $bp->updateDB($sql);
  


  $mjesta = $bp->selectDB("SELECT broj_polaznika FROM mentor WHERE id_mentor='$mentor' AND id_skola='$skola'");
  list($broj_polaznika)=mysqli_fetch_array($mjesta);

  $novi = $broj_polaznika + 1;
  
  $polaznici = "UPDATE mentor SET broj_polaznika='$novi' WHERE id_mentor='$mentor' AND id_skola='$skola'";
  $bp->updateDB($polaznici);

  header("Location: pregledavanje-racuna.php");}
  


?>

<table>
  <tr>
    <th>Racun ID</th>
    <th>Sati</th>
    <th>Cijena</th>
    <th>PDV</th>
    <th>Ukupan iznos za placanje</th>
    <th>Status racuna</th>
  </tr>

  <?php

    $mentor = $_SESSION['id'];

    $racun = $bp->selectDB("SELECT id_racun, status_racuna, id_cjenik FROM racun r, zahtjevi z WHERE r.id_zahtjev=z.id_zahtjev AND z.id_mentor='$mentor'");

    while(list($id_racun,$status_racuna,$id_cjenik)=mysqli_fetch_array($racun)) {
      echo '<tr>';

      echo '<td>'.$id_racun.'</td>';
      
      $sati = $bp->selectDB("SELECT id_skola, sati FROM cjenik WHERE id_cjenik='$id_cjenik'");
      list($id_skola, $sati)=mysqli_fetch_array($sati);
      echo '<td>'.$sati.'</td>';

      $cijena = $bp->selectDB("SELECT id_skola, cijena FROM cjenik WHERE id_cjenik='$id_cjenik'");
      list($id_skola, $cijena)=mysqli_fetch_array($cijena);

      echo '<td>'.$cijena.'kn</td>';

      echo '<td>'.$cijena*0.25.'kn</td>';

      echo '<td>'.$cijena*1.25.'kn</td>';

      if($status_racuna == 1) {
        echo '<td><a href="pregledavanje-racuna.php?id_racun='.$id_racun.'&id_skola='.$id_skola.'">Potvrdi</a></td>';
      } else if($status_racuna == 2) {
        echo '<td>Placen</td>';
      }
      echo '</tr>';
    }
  ?>

</table>

<?php include('foot.php'); ?>