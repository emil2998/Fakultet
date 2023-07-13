<?php 
include('head.php'); 

?>

<div class="filter">
  <label for="skola"><b>Skola</b></label>
  <select name="skola" id="skola">
    <option value="mentori-popis.php">Sve</option>
  <?php

    $sql = $bp->selectDB("SELECT id_skola,naziv FROM plesna_skola");

    while(list($id_skola, $naziv)=mysqli_fetch_array($sql)) {
      if(isset($_GET['id_skola']) && $_GET['id_skola'] == $id_skola) {
        echo '<option selected="selected" value="mentori-popis.php?id_skola='.$id_skola.'">  '.$naziv.'</option>';
      } else {
        echo '<option value="mentori-popis.php?id_skola='.$id_skola.'">  '.$naziv.'</option>';
      }
    }

  ?>
  </select>
</div>


<table>
  <tr>
    <th>Ime</th>
    <th>Prezime</th>
    <th>Plesna skola</th>
    <?php
      if(isset($_GET['broj_polaznika']) && $_GET['broj_polaznika'] == 'a') {
        echo '<th><a href="mentori-popis.php?broj_polaznika=d">Broj polaznika</a></th>';
      } else {
        echo '<th><a href="mentori-popis.php?broj_polaznika=a">Broj polaznika</a></th>';
      }
    ?>
  </tr>

  <?php
  
    if(isset($_GET['id_skola'])) {
      $skola_id = $_GET['id_skola'];
      $mentor = $bp->selectDB("SELECT * FROM mentor WHERE id_skola='$skola_id'");
    } else {
      if(isset($_GET['broj_polaznika'])) {
        $sort = $_GET['broj_polaznika'];
        if($sort == 'a') {
          $mentor = $bp->selectDB("SELECT * FROM mentor ORDER BY broj_polaznika ASC");
        } else {
          $mentor = $bp->selectDB("SELECT * FROM mentor ORDER BY broj_polaznika DESC");
        }
      } else {
        $mentor = $bp->selectDB("SELECT * FROM mentor");
      }
    }

    while(list($id_mentor,$id_skola, $o_meni, $broj_polaznika)=mysqli_fetch_array($mentor)) {
      echo '<tr>';

      $korisnik = $bp->selectDB("SELECT ime, prezime FROM korisnik WHERE id_korisnik='$id_mentor'");
      list($ime, $prezime)=mysqli_fetch_array($korisnik);
      echo '<td>'.$ime.'</td>';
      echo '<td>'.$prezime.'</td>';

      $skola = $bp->selectDB("SELECT naziv FROM plesna_skola WHERE id_skola='$id_skola'");
      list($naziv)=mysqli_fetch_array($skola);

      echo '<td>'.$naziv.'</td>';
      echo '<td>'.$broj_polaznika.'</td>';

      echo '</tr>';
    }
  
  ?>

</table>

<?php include('foot.php'); ?>