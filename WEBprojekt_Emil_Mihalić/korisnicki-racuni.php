<?php 
include('head.php'); 

if($_SESSION["tip"] != 1) {
  header("Location:index.php");
}

?>

<table>
  <tr>
    <th>Korisnicko ime</th>
    <th>Status racuna</th>
  </tr>

  <?php
  
    $sql = $bp->selectDB("SELECT korisnicko_ime, status_racuna FROM korisnik");

    while(list($korisnicko_ime, $status)=mysqli_fetch_array($sql)) {
      if($korisnicko_ime !== $_SESSION['korisnik']) {
        echo '<tr>';
        echo '<td>'.$korisnicko_ime.'</td>';
        if ($status == 3) {
          echo '<td><a href="promjena-status-racuna.php?otkljucaj='.$korisnicko_ime.'">Otkljucaj</a><br><br><a href="promjena-status-racuna.php?blokiraj='.$korisnicko_ime.'">Blokiraj</a></td>';
        } else if ($status == 7) {
          echo '<td><a href="promjena-status-racuna.php?odblokiraj='.$korisnicko_ime.'">Odblokiraj</a><br><br><a href="promjena-status-racuna.php?zakljucaj='.$korisnicko_ime.'">Zakljucaj</a></td>';
        } else {

          echo '<td><a href="promjena-status-racuna.php?zakljucaj='.$korisnicko_ime.'">Zakljucaj</a><br><br><a href="promjena-status-racuna.php?blokiraj='.$korisnicko_ime.'">Blokiraj</a></td>';
        }
        echo '</tr>';
      }
    }
  
  ?>

</table>

<?php include('foot.php'); ?>