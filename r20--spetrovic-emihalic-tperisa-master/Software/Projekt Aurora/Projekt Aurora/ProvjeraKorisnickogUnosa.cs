using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.SqlClient;
using System.Windows.Forms;
using System.Text.RegularExpressions;
using System.ComponentModel.DataAnnotations;
namespace Projekt_Aurora
{
    public static class ProvjeraKorisnickogUnosa
    {
        public static string ProvjeriPrijavu(List<TextBox> lista)
        {
            string korIme = "";
            string lozinka = "";
            string povratnaPoruka = "";
            foreach (TextBox unos in lista)
            {
                if (unos.Name == "txtKorisnickoIme")
                {
                    korIme = unos.Text;
                    povratnaPoruka += VanjskeBiblioteke.ValidacijaUnosa.ProvjeriKorisnickoIme(unos.Text);
                }
                if (unos.Name == "txtLozinka")
                {
                    lozinka = unos.Text;
                    povratnaPoruka += VanjskeBiblioteke.ValidacijaUnosa.ProvjeriLozinku(unos.Text);
                }
            }
            if (povratnaPoruka == "")
            {
                if (BazaProvjeriPrijavu(korIme, lozinka) == false)
                {
                    povratnaPoruka += "Pogrešno korisničko ime ili lozinka!\n";
                }
            }
            return povratnaPoruka;
        }

        public static bool BazaProvjeriPrijavu(string korIme, string lozinka)
        {
            bool postojiKorisnik = false;
            List<Korisnik> lista = new List<Korisnik>();
            string sqlUpit = $"SELECT * FROM korisnik ";
            SqlDataReader dr = DB.Instance.DohvatiDataReader(sqlUpit);
            while (dr.Read())
            {
                Korisnik korisnik = KorisnikRepozitorij.DohvatiKorisnika(dr);
                lista.Add(korisnik);
            }
            dr.Close();
            foreach (Korisnik korisnik in lista)
            {
                if (korisnik.Korisnicko_ime == korIme && korisnik.Lozinka == lozinka)
                {
                    postojiKorisnik = true;
                    UlogiraniKorisnik.IdUloga = korisnik.Id_uloga;
                    UlogiraniKorisnik.Korisnicko_ime = korisnik.Korisnicko_ime;
                    UlogiraniKorisnik.Lozinka = korisnik.Lozinka;
                    UlogiraniKorisnik.Id_korisnik = korisnik.Id_korisnik;
                    UlogiraniKorisnik.Email = korisnik.Email;
                    UlogiraniKorisnik.Ime = korisnik.Ime;
                    UlogiraniKorisnik.Prezime = korisnik.Prezime;
                    UlogiraniKorisnik.Kontakt = korisnik.Kontakt;
                    UlogiraniKorisnik.Datum_rođenja = korisnik.Datum_rođenja;
                    UlogiraniKorisnik.Adresa = korisnik.Adresa;
                    UlogiraniKorisnik.Stanje_racuna = korisnik.Stanje_racuna;
                    UlogiraniKorisnik.Grad = korisnik.Grad;
                }
            }
            return postojiKorisnik;
        }

        public static string ProvjeriRegistraciju(List<TextBox> lista)
        {
            string lozinka = "";
            string potvrdaLozinke = "";
            string korIme = "";
            string povratnaPoruka = "";
            foreach (TextBox unos in lista)
            {
                if (unos.Name == "txtIme")
                {
                    povratnaPoruka += VanjskeBiblioteke.ValidacijaUnosa.ProvjeriIme(unos.Text);
                }
                if (unos.Name == "txtPrezime")
                {
                    povratnaPoruka += VanjskeBiblioteke.ValidacijaUnosa.ProvjeriPrezime(unos.Text);
                }
                if (unos.Name == "txtEmail")
                {
                    povratnaPoruka += VanjskeBiblioteke.ValidacijaUnosa.ProvjeriEmail(unos.Text);
                }
                if (unos.Name == "txtKontakt")
                {
                    povratnaPoruka += VanjskeBiblioteke.ValidacijaUnosa.ProvjeriKontakt(unos.Text);
                }
                if (unos.Name == "txtAdresa")
                {
                    povratnaPoruka += VanjskeBiblioteke.ValidacijaUnosa.ProvjeriAdresu(unos.Text);
                }
                if (unos.Name == "txtGrad")
                {
                    povratnaPoruka += VanjskeBiblioteke.ValidacijaUnosa.ProvjeriGrad(unos.Text);
                }
                if (unos.Name == "txtKorisnickoIme")
                {
                    korIme = unos.Text;
                    povratnaPoruka += VanjskeBiblioteke.ValidacijaUnosa.ProvjeriKorisnickoIme(unos.Text);
                }
                if (unos.Name == "txtLozinka")
                {
                    lozinka = unos.Text;
                    povratnaPoruka += VanjskeBiblioteke.ValidacijaUnosa.ProvjeriLozinku(unos.Text);
                }
                if (unos.Name == "txtPotvrdaLozinke")
                {
                    potvrdaLozinke = unos.Text;
                    povratnaPoruka += VanjskeBiblioteke.ValidacijaUnosa.ProvjeriPotvrduLozinke(lozinka, unos.Text);
                }
                
            }
            if (povratnaPoruka == "")
            {
                if (BazaProvjeriRegistraciju(korIme) == true)
                {
                    povratnaPoruka += "Korisnik s istim korisničkim imenom već postoji!\n";
                }
                if (lozinka != potvrdaLozinke)
                {
                    povratnaPoruka += "Potvrda lozinke nije identična lozinci!\n";
                }
            
            }
            return povratnaPoruka;

        }

        public static bool BazaProvjeriRegistraciju(string korIme)
        {
            bool postojiKorisnik = false;
            List<Korisnik> lista = new List<Korisnik>();
            string sqlUpit = $"SELECT * FROM korisnik";
            SqlDataReader dr = DB.Instance.DohvatiDataReader(sqlUpit);
            while (dr.Read())
            {
                Korisnik korisnik = KorisnikRepozitorij.DohvatiKorisnika(dr);
                lista.Add(korisnik);
            }
            dr.Close();
            foreach (Korisnik korisnik in lista)
            {
                if (korisnik.Korisnicko_ime == korIme)
                {
                    postojiKorisnik = true;
                }
            }
            return postojiKorisnik;
        }

        public static string ProvjeriDodavanjeIzmjenuFilma(List<TextBox> lista)
        {
            string naziv = "";
            int godina = 0;
            string redatelj = "";
            string povratnaPoruka = "";

            foreach (TextBox unos in lista)
            {
                if (unos.Name == "txtNaziv")
                {
                    naziv = unos.Text;
                    povratnaPoruka += VanjskeBiblioteke.ValidacijaUnosa.ProvjeriNazivDuze(unos.Text);
                }
                if (unos.Name == "txtGodina")
                {
                    godina = int.Parse(unos.Text);
                    povratnaPoruka += VanjskeBiblioteke.ValidacijaUnosa.ProvjeriGodinu(unos.Text);
                }
                if (unos.Name == "txtRedatelj")
                {
                    redatelj = unos.Text;
                    povratnaPoruka += VanjskeBiblioteke.ValidacijaUnosa.ProvjeriRedatelja(unos.Text);
                }
                if (unos.Name == "txtOpis2")
                {
                    povratnaPoruka += VanjskeBiblioteke.ValidacijaUnosa.ProvjeriOpis(unos.Text);
                }
                if (unos.Name == "txtSati")
                {
                    povratnaPoruka += VanjskeBiblioteke.ValidacijaUnosa.ProvjeriSate(unos.Text);
                }
                if (unos.Name == "txtMinute")
                {
                    povratnaPoruka += VanjskeBiblioteke.ValidacijaUnosa.ProvjeriMinute(unos.Text);
                }
            }
            if (povratnaPoruka == "")
            {
                if (BazaProvjeriDodavanjeIzmjenuFilma(naziv, godina, redatelj) == true)
                {
                    povratnaPoruka += "Postoji zapis sa istim podacima!\n";
                }
            }
            return povratnaPoruka;
        }

        public static bool BazaProvjeriDodavanjeIzmjenuFilma(string naziv, int godina, string redatelj)
        {
            bool postojiFilm = false;
            List<Film> lista = new List<Film>();
            string sqlUpit = $"SELECT * FROM film";
            SqlDataReader dr = DB.Instance.DohvatiDataReader(sqlUpit);
            while (dr.Read())
            {
                Film film = FilmRepozitorij.DohvatiFilm(dr);
                lista.Add(film);
            }
            dr.Close();
            foreach (Film film in lista)
            {
                if (film.Naziv == naziv && film.Godina == godina && film.Redatelj == redatelj)
                {
                    postojiFilm = true;
                }
            }
            return postojiFilm;
        }

        public static string ProvjeriDodavanjeIzmjenuGlumca(List<TextBox> lista)
        {
            string ime = "";
            string prezime = "";
            string povratnaPoruka = "";
            foreach (TextBox unos in lista)
            {
                if (unos.Name == "txtIme")
                {
                    ime = unos.Text;
                    povratnaPoruka += VanjskeBiblioteke.ValidacijaUnosa.ProvjeriIme(unos.Text);
                }
                if (unos.Name == "txtPrezime")
                {
                    prezime = unos.Text;
                    povratnaPoruka += VanjskeBiblioteke.ValidacijaUnosa.ProvjeriPrezime(unos.Text);
                }
            }
            if (povratnaPoruka == "")
            {
                if (BazaProvjeriDodavanjeIzmjenuGlumca(ime, prezime) == true)
                {
                    povratnaPoruka += "Glumac s istim imenom i prezimenom već postoji!\n";
                }
            }
            return povratnaPoruka;
        }

        public static bool BazaProvjeriDodavanjeIzmjenuGlumca(string ime, string prezime)
        {
            bool postojiGlumac = false;
            List<Glumac> lista = new List<Glumac>();
            string sqlUpit = $"SELECT * FROM glumci";
            SqlDataReader dr = DB.Instance.DohvatiDataReader(sqlUpit);
            while (dr.Read())
            {
                Glumac glumac = GlumacRepozitorij.DohvatiGlumca(dr);
                lista.Add(glumac);
            }
            dr.Close();
            foreach (Glumac glumac in lista)
            {
                if (glumac.Ime == ime && glumac.Prezime == prezime)
                {
                    postojiGlumac = true;
                }
            }
            return postojiGlumac;
        }

        public static string ProvjeriDodavanjeIzmjenuZanra(TextBox textBox)
        {
            string povratnaPoruka = "";
            povratnaPoruka += VanjskeBiblioteke.ValidacijaUnosa.ProvjeriNazivKrace(textBox.Text);
            if (povratnaPoruka == "")
            {
                if (BazaProvjeriDodavanjeIzmjenuZanra(textBox.Text) == true)
                {
                    povratnaPoruka += "Žanr s istim nazivom već postoji!\n";
                }
            }
            return povratnaPoruka;
        }

        public static bool BazaProvjeriDodavanjeIzmjenuZanra(string naziv)
        {
            bool postojiZanr = false;
            List<Zanr> lista = new List<Zanr>();
            string sqlUpit = $"SELECT * FROM zanr";
            SqlDataReader dr = DB.Instance.DohvatiDataReader(sqlUpit);
            while (dr.Read())
            {
                Zanr zanr = ZanrRepozitorij.DohvatiZanr(dr);
                lista.Add(zanr);
            }
            dr.Close();
            foreach (Zanr zanr in lista)
            {
                if (zanr.Naziv == naziv)
                {
                    postojiZanr = true;
                }
            }
            return postojiZanr;
        }

        public static string ProvjeriDodavanjeIzmjenuKina(List<TextBox> lista)
        {
            string naziv = "";
            string adresa = "";
            string povratnaPoruka = "";
            foreach (TextBox unos in lista)
            {
                if (unos.Name == "txtNaziv")
                {
                    naziv = unos.Text;
                    povratnaPoruka += VanjskeBiblioteke.ValidacijaUnosa.ProvjeriNazivDuze(unos.Text);
                }
                if (unos.Name == "txtAdresa")
                {
                    adresa = unos.Text;
                    povratnaPoruka += VanjskeBiblioteke.ValidacijaUnosa.ProvjeriAdresu(unos.Text);
                }
            }
            if (povratnaPoruka == "")
            {
                if (BazaProvjeriDodavanjeIzmjenuKina(naziv, adresa) == true)
                {
                    povratnaPoruka += "Kino s istim nazivom i adresom već postoji!\n";
                }
            }
            return povratnaPoruka;
        }

        public static bool BazaProvjeriDodavanjeIzmjenuKina(string naziv, string adresa)
        {
            bool postojiKino = false;
            List<Kino> lista = new List<Kino>();
            string sqlUpit = $"SELECT * FROM kino";
            SqlDataReader dr = DB.Instance.DohvatiDataReader(sqlUpit);
            while (dr.Read())
            {
                Kino kino = KinoRepozitorij.DohvatiKino(dr);
                lista.Add(kino);
            }
            dr.Close();
            foreach (Kino kino in lista)
            {
                if (kino.Naziv == naziv && kino.Adresa == adresa)
                {
                    postojiKino = true;
                }
            }
            return postojiKino;

        }

        public static string ProvjeriDodavanjeIzmjenuDvorane(List<TextBox> lista, int kino)
        {
            string naziv = "";
            string broj_redova = "";
            string broj_stupaca = "";
            string povratnaPoruka = "";
            int idkina = kino;


            foreach (TextBox unos in lista)
            {
                if (unos.Name == "txtNaziv")
                {
                    naziv = unos.Text;
                    povratnaPoruka += VanjskeBiblioteke.ValidacijaUnosa.ProvjeriNazivDuze(unos.Text);
                }
                if (unos.Name == "txtBrojRedova")
                {
                    broj_redova = unos.Text;
                    povratnaPoruka += VanjskeBiblioteke.ValidacijaUnosa.ProvjeriBrojRedova(unos.Text);
                }
                if (unos.Name == "txtBrojStupaca")
                {
                    broj_stupaca = unos.Text;
                    povratnaPoruka += VanjskeBiblioteke.ValidacijaUnosa.ProvjeriBrojStupaca(unos.Text);
                }

            }
            if (povratnaPoruka == "")
            {

                if (BazaProvjeriDodavanjeIzmjenuDvorane(naziv, idkina) == true)
                {
                    povratnaPoruka += "Postoji zapis sa istim podacima!\n";
                }


            }
            return povratnaPoruka;
        }

        public static bool BazaProvjeriDodavanjeIzmjenuDvorane(string naziv, int idkina)
        {
            bool postojiDvorana = false;
            List<Dvorana> lista = new List<Dvorana>();
            string sqlUpit = $"SELECT * FROM dvorana";
            SqlDataReader dr = DB.Instance.DohvatiDataReader(sqlUpit);
            while (dr.Read())
            {
                Dvorana dvorana = DvoranaRepozitorij.DohvatiDvoranu(dr);
                lista.Add(dvorana);
            }
            dr.Close();
            foreach (Dvorana dvorana in lista)
            {
                if (dvorana.Naziv == naziv && dvorana.Id_kina == idkina)
                {
                    postojiDvorana = true;
                }
            }
            return postojiDvorana;
        }

        public static string ProvjeriDodavanjeIzmjenuProjekcije(List<TextBox> lista, string sati, string datum, Dvorana dvorana)
        {

            string iznos = "";
            string povratnaPoruka = "";

            foreach (TextBox unos in lista)
            {
                if (unos.Name == "txtIznos")
                {
                    iznos = unos.Text;
                    povratnaPoruka += VanjskeBiblioteke.ValidacijaUnosa.ProvjeriIznos(unos.Text);
                }



            }
            if (povratnaPoruka == "")
            {
                if (BazaProvjeriDodavanjeIzmjenuProjekcije(sati, datum, dvorana) == true)
                {
                    povratnaPoruka += "Postoji zapis sa istim podacima!\n";
                }

            }
            return povratnaPoruka;
        }

        public static bool BazaProvjeriDodavanjeIzmjenuProjekcije(string sati, string datum, Dvorana dvorana)
        {
            bool postojiProjekcija = false;
            List<Projekcija> lista = new List<Projekcija>();
            string sqlUpit = $"SELECT * FROM projekcija";
            SqlDataReader dr = DB.Instance.DohvatiDataReader(sqlUpit);
            while (dr.Read())
            {
                Projekcija projekcija = ProjekcijaRepozitorij.DohvatiProjekciju(dr);
                lista.Add(projekcija);
            }
            dr.Close();
            foreach (Projekcija projekcija in lista)
            {
                if (projekcija.Vrijeme == sati && projekcija.Datum == datum && projekcija.Id_dvorana == dvorana.ID)
                {
                    postojiProjekcija = true;
                }
            }
            return postojiProjekcija;
        }

        public static string ProvjeriPlacanje(List<TextBox> lista)
        {

            string iznos = "";
            string povratnaPoruka = "";

            foreach (TextBox unos in lista)
            {
                if (unos.Name == "txtNadoplataRacuna")
                {
                    iznos = unos.Text;
                    povratnaPoruka += VanjskeBiblioteke.ValidacijaUnosa.ProvjeriIznos(unos.Text);
                }



            }
            if (povratnaPoruka == "")
            {

            }
            return povratnaPoruka;
        }
    }
}
