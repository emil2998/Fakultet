<?php 

include('head.php'); 

if($_SESSION["tip"] != 1 && $_SESSION["tip"] != 2 && $_SESSION["tip"] != 3) {
  header("Location:index.php");
}

?>

<?php 

if(isset($_GET['id_racun'])) {
  $racun = $_GET['id_racun'];

  $sql = "UPDATE racun SET status_racuna=1 WHERE id_racun='$racun'";
  $rs = $bp->updateDB($sql);

  header("Location: popis-racuna.php");
}

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
    $korisnik=$_SESSION['id'];
    $racun = $bp->selectDB("SELECT id_racun, status_racuna, id_cjenik FROM racun r, zahtjevi z WHERE r.id_zahtjev=z.id_zahtjev AND z.id_polaznik='$korisnik'");

    while(list($id_racun,$status_racuna,$id_cjenik)=mysqli_fetch_array($racun)) {
      echo '<tr>';

      echo '<td>'.$id_racun.'</td>';
      
      $sati = $bp->selectDB("SELECT sati FROM cjenik WHERE id_cjenik='$id_cjenik'");
      list($sati)=mysqli_fetch_array($sati);
       echo '<td>'.$sati.'</td>';

      $cijena = $bp->selectDB("SELECT cijena FROM cjenik WHERE id_cjenik='$id_cjenik'");
      list($cijena)=mysqli_fetch_array($cijena);

      echo '<td>'.$cijena.'kn</td>';

      echo '<td>'.$cijena*0.25.'kn</td>';

      echo '<td>'.$cijena*1.25.'kn</td>';

      if($status_racuna == 0) {
        echo '<td><a href="popis-racuna.php?id_racun='.$id_racun.'">Plati</a></td>';
      } else if($status_racuna == 1) {
        echo '<td>Ceka se potvrda moderatora</td>';
      } else if($status_racuna == 2) {
        echo '<td>Placen</td>';
      }
      echo '</tr>';
    }
  ?>

</table>

<?php include('foot.php'); ?>