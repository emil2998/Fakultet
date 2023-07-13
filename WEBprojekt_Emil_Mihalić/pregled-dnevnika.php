<?php 

include('head.php'); 

if($_SESSION["tip"] != 1) {
  header("Location:index.php");
}

?>

<div class="filter">
  <label for="korisnik"><b>Korisnik</b></label>
  <select name="korisnik" id="korisnik">
  <?php

    $filter = $bp->selectDB("SELECT id_korisnik, korisnicko_ime FROM korisnik");

    while(list($id, $korisnik)=mysqli_fetch_array($filter)) {
      if(isset($_GET['id_korisnik']) && $_GET['id_korisnik'] == $id) {
        echo '<option selected value="pregled-dnevnika.php?id_korisnik='.$id.'">'.$korisnik.'</option>';
      } else {
        echo '<option value="pregled-dnevnika.php?id_korisnik='.$id.'">'.$korisnik.'</option>';
      }
    }

  ?>
  </select>
</div>

<table>
  <tr>
    <th>Korisnik</th>
    <th>Dogadjaj</th>
    <th>Datum i vrijeme</th>
  </tr>

  <?php

    if(isset($_GET['id_korisnik'])) {
      $id = $_GET['id_korisnik'];
      $dnevnik = $bp->selectDB("SELECT id_korisnik, opis, datum_i_vrijeme FROM dnevnik WHERE id_korisnik='$id'");
    } else {
      $dnevnik = $bp->selectDB("SELECT id_korisnik, opis, datum_i_vrijeme FROM dnevnik");
    }
  
    while(list($id_korisnik, $opis, $datum)=mysqli_fetch_array($dnevnik)) {
      echo '<tr>';

      $korisnik = $bp->selectDB("SELECT korisnicko_ime FROM korisnik WHERE id_korisnik='$id_korisnik'");
      list($korisnicko)=mysqli_fetch_array($korisnik);

      echo '<td>'.$korisnicko.'</td>';

      echo '<td>'.$opis.'</td>';

      echo '<td>'.$datum.'</td>';

      echo '</tr>';
    }
  ?>

</table>

<?php include('foot.php'); ?>