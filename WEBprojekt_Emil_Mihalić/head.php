<?php

include('baza.class.php');
include('sesija.class.php');

$bp = new Baza();
$bp->spojiDB();

if ($bp->pogreskaDB()) {
  echo "Problem kod upita na bazu podataka!";
  exit;
}

?>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel = "stylesheet" type = "text/css" href = "css/main.css" />
  <link rel="stylesheet" type="text/css" href="//wpcc.io/lib/1.0.2/cookieconsent.min.css"/>
  <script src="//wpcc.io/lib/1.0.2/cookieconsent.min.js"></script>
  <script src="https://www.google.com/recaptcha/api.js" async defer></script>
  <script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
  <title>Plesne škole</title>
</head>
<body>
  <header>
    <ul class="topnav">
      <li><a href="index.php">Početna</a></li>
      <li><a href="mentori-popis.php">Popis mentora</a></li>
      <li><a href="popis-skola.php">Popis skola</a></li>
      <li><a href="o_autoru.html">O Autoru</a></li>
      <li><a href="dokumentacija.html">Dokumentacija</a></li>
      <?php
        if(Sesija::dajKorisnika() != NULL) { 
          echo '<li class="right"><a href="prijava.php?odjava=set">Odjava</a></li>';

          if($_SESSION["tip"] == 1  ||  $_SESSION["tip"] == 2 ||  $_SESSION["tip"] == 3) { 
            echo '<li><a href="kreiranje-zahtjeva.php">Kreiranje zahtjeva</a></li>';
            echo '<li><a href="popis-racuna.php">Popis racuna</a></li>';
          }

          if($_SESSION["tip"] == 1  ||  $_SESSION["tip"] == 2) { 
            echo '<li><a href="moji-podaci.php">Moji podaci</a></li>';
            echo '<li><a href="kreiranje-termina.php">Kreiranje termina</a></li>';
            echo '<li><a href="popis-zahtjeva.php">Popis zahtjeva</a></li>';
            echo '<li><a href="pregledavanje-racuna.php">Pregledavanje racuna</a></li>';
          }

          if($_SESSION["tip"] == 1) { 
            echo '<li><a href="kreiranje-skola.php">Nova škola</a></li>';
            echo '<li><a href="kreiranje-cjenika.php">Novi cjenik</a></li>';
            echo '<li><a href="korisnicki-racuni.php">Korisnicki racuni</a></li>';
            echo '<li><a href="pregled-dnevnika.php">Pregled dnevnika</a></li>';
          }
        } else {
          echo '<li class="right"><a href="registracija.php">Registracija</a></li>';
          echo '<li class="right"><a href="prijava.php">Prijava</a></li>';
        }
      ?>
      
    </ul>
  </header>

  <main>