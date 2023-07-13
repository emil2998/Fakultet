<?php 
include('head.php'); 

?>

<table>
  <tr>
    <th>Naziv</th>
    <th>Broj polaznika</th>
  </tr>

  <?php

    $skola = $bp->selectDB("SELECT * FROM plesna_skola");

    while(list($id_skola,$naziv)=mysqli_fetch_array($skola)) {
      echo '<tr>';

      echo '<td>'.$naziv.'</td>';

      $suma = $bp->selectDB("SELECT SUM(broj_polaznika) FROM mentor WHERE id_skola='$id_skola'");
      list($suma_polaznika)=mysqli_fetch_array($suma);

      echo '<td>'.$suma_polaznika.'</td>';

      echo '</tr>';
    }
  ?>

</table>

<?php include('foot.php'); ?>