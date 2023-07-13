<table>

  <tr>
    <th>Korisnicko ime</th>
    <th>Lozinka</th>
    <th>Vrsta</th>
  </tr>
<?php

include_once('../baza.class.php');

$dbc = new Baza();
$dbc->spojiDB();

$korisnici_upit = "SELECT * FROM korisnik";
$korisnici_rezultat = $dbc->selectDB($korisnici_upit);

while(list($id, $korisnicko, $ime, $prezime, $email, $lozinka, $sha, $tip, $status, $kod) = $korisnici_rezultat->fetch_array()){
  echo '<tr>';
    echo '<td>'.$korisnicko.'</td>';
    echo '<td>'.$lozinka.'</td>';
    echo '<td>'.$tip.'</td>';
    
  echo '</tr>';
}
?>
</table>

<style>
  table {
    margin: 0 auto;
  }

  table tr {
    display: block;
    border: 1px solid black;
  }

  table tr td, table tr th {
    padding: 10px;
  }
</style>