using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Projekt_Aurora
{
    public class Korisnik
    {
        public int Id_korisnik { get; set; }
        public int Id_uloga { get; set; }
        public string Korisnicko_ime { get; set; }
        public string Lozinka { get; set; }
        public string Ime { get; set; }
        public string Prezime { get; set; }
        public string Email { get; set; }
        public string Kontakt { get; set; }
        public string Datum_rođenja { get; set; }
        public string Adresa { get; set; }
        public string Grad { get; set; }
        public int Stanje_racuna { get; set; }

        public Korisnik korisnik { get; set; }

        public Korisnik()
        {
        }

        public Korisnik(string korisnickoIme, string lozinka, string ime, string prezime, string email, string kontakt, string datumRođenja, string adresa, string grad)
        {
            Id_korisnik = 0;
            Id_uloga = 1;
            Korisnicko_ime = korisnickoIme;
            Lozinka = lozinka;
            Ime = ime;
            Prezime = prezime;
            Email = email;
            Kontakt = kontakt;
            Datum_rođenja = datumRođenja;
            Adresa = adresa;
            Grad = grad;
            Id_uloga = 1;
            Stanje_racuna = 0;
        }
    }
}
