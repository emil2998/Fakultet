$(document).ready(function(){
  let registracija = $("#registracija");
  let submit = $(".registerbtn");
  let ime = registracija.find("#ime");
  let prezime = registracija.find("#prezime");
  let korisnicko = registracija.find("#korisnicko_ime");
  let lozinka = registracija.find("#lozinka");
  let ponovljena = registracija.find("#ponovljena");
  let email = registracija.find("#email");
  let js_ime = registracija.find("#js-ime");
  let js_prezime = registracija.find("#js-prezime");
  let js_korisnicko = registracija.find("#js-korisnicko");
  let js_lozinka = registracija.find("#js-lozinka");
  let js_ponovljena = registracija.find("#js-ponovljena");
  let js_email = registracija.find("#js-email");
  let check = false;
  
  //Provjera ime
  ime.on('change', function() {
    if(ime[0].value.length < 3) {
      js_ime.html("Unesite ime duze od 2 slova");
      check = true;
      submit.prop('disabled', check);
    } else {
      js_ime.html("");
      check = false;
      submit.prop('disabled', check);
    }
  });

  //Provjera prezime
  prezime.on('change', function() {
    if(prezime[0].value.length < 3) {
      js_prezime.html("Unesite prezime duze od 2 slova");
      check = true;
      submit.prop('disabled', check);
    } else {
      js_prezime.html("");
      check = false;
      submit.prop('disabled', check);
    }
  });

  //Provjera korisnicko ime
  korisnicko.on('change', function() {
    if(korisnicko[0].value.length < 5) {
      js_korisnicko.html("Unesite korisnicko ime duze od 4 slova");
      check = true;
      submit.prop('disabled', check);
    } else {
      js_korisnicko.html("");
      check = false;
      submit.prop('disabled', check);
    }
  });

  $korisnickoIme = $('#korisnicko_ime');
  $('#korisnicko_ime').on('change',function(){
    var korisnicko = $(this).val();
    $.ajax ({
        url: "provjera.php",
        method: "POST",
        data:  {korisnicko :korisnicko },
        dataType : "text",
        success:function(html)
        {
          $('#dostupnost').html(html);
        }
    });
  });

  //Provjera lozinka
  lozinka.on('change', function() {
    let regex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}$/;
    if(!regex.test(lozinka[0].value)) {
      js_lozinka.html("Minimalna duzina lozinke je 5 te mora sadrzavati jedan broj, malo i veliko slovo");
      check = true;
      submit.prop('disabled', check);
    } else {
      js_lozinka.html("");
      check = false;
      submit.prop('disabled', check);
    }
  });

  //   /^
  //   (?=.*\d)          // should contain at least one digit
  //   (?=.*[a-z])       // should contain at least one lower case
  //   (?=.*[A-Z])       // should contain at least one upper case
  //   [a-zA-Z0-9]{8,}   // should contain at least 8 from the mentioned characters
  // $/

  //Provjera ponovljena
  ponovljena.on('change', function() {
    if(lozinka[0].value != ponovljena[0].value) {
      js_ponovljena.html("Lozinka i ponovljena lozinka nisu iste, provjete unose!");
      check = true;
      submit.prop('disabled', check);
    } else {
      js_ponovljena.html("");
      check = false;
      submit.prop('disabled', check);
    }
  });

  //Provjera email
  email.on('change', function() {
    let regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
    if(!regex.test(email[0].value)) {
      js_email.html("Unesite validan email!");
      check = true;
      submit.prop('disabled', check);
    } else {
      js_email.html("");
      check = false;
      submit.prop('disabled', check);
    }
  });



  $korisnickoImPrijava = $('#korisnicko_ime_prijava');
  $('#korisnicko_ime_prijava').on('change',function(){
    var korisnicko = $(this).val();
    $('#dostupnost').html("");
    $.ajax ({
        url: "provjera.php",
        method: "POST",
        data:  {korisnickoPrijava :korisnicko },
        dataType : "text",
        success:function(html)
        {
          $('#dostupnost').html(html);
        }
    });
  });

  $('#skola').on('change', function () {
      var url = $(this).val();
      if (url) {
          window.location = url;
      }
      return false;
  });

  $('#mentor').on('change', function () {
    var url = $(this).val();
    if (url) {
        window.location = url;
    }
    return false;
  });

  $('#korisnik').on('change', function () {
    var url = $(this).val();
    if (url) {
        window.location = url;
    }
    return false;
  });
});
