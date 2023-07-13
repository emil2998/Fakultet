PROJEKTNA DOKUMENTACIJA
---------------------------
* RAZVOJNI PRISTUP - Ok, vodopadni model
* PROJEKTNI PLAN - OK, detaljno, prati definirane faze.
* PONUDA - Ok

TEHNIČKA DOKUMENTACIJA
---------------------------
* SRS - Prati IEEE SRS, ali ne u potpunosti i dovoljno detaljno.
* USE CASE - Ima li smisla da neregistrirani korisnik može rezervirati ulaznicu? Izvrši transakciju je implementacijski detalj, to nije use case. To bi imalo više smisla za registriranog korisnika. Fali prodaja ulaznice/karte. Na zajedničkom use case dijagramu se ne vide svi use case-ovi. Baza nije actor, što je ovo CC billing?
* DIJAGRAMI AKTIVNOSTI I SLIJEDA - Dijagrami aktivnosti opisuju korisnikovo kretanje po aplikaciji. Smatram da to nije najsretnija uporaba dijagrama aktivnosti. Vjerojatno bi sve to što je navedeno na dijagramima puno lakše opisali u 2-3 rečenice. Dijagrami slijeda isto imaju nelogičnosti. Npr. "Dodavanje kina" bi trebao valjda prikazivati i objekt Kino.
* DIJAGRAM KLASA - Veza Glumac - Film nije kompozicija. Isto tako i Film -Žanr. Treba odvojiti atribute od metoda klase. Fale neke klase, npr. Uloga. Tko vodi računa o konkretnom sjedalu koje je rezervirano? Zašto nam treba klasa registracija? Zar to nisu sve podaci korisnika? I tu fali ulaznica/karta.
* ERA DIJAGRAM - Jedan film se može prikazivati na više projekcija. Npr. u 17:00 sati, 20:00 sati i 23:00 sata. Korisnik može imati nula ili više recenzija, kao i film. Isto tako i za rezervacije. Gdje se zapisuje izdana karta?

IMPLEMENTACIJA
---------------------------
Započeli s implementacijom grafičkog sučelja.

OSTALI ARTEFAKTI
---------------------------

BODOVI
----------------
9 bodova.
