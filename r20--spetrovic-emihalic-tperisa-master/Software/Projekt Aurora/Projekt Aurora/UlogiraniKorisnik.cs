using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Projekt_Aurora
{
    public abstract class UlogiraniKorisnik
    {
        public static int IdUloga { get; set; }
        public static int Id_korisnik { get; set; }
        public static string Korisnicko_ime { get; set; }
        public static string Lozinka { get; set; }
        public static string Ime { get; set; }
        public static string Prezime { get; set; }
        public static string Email { get; set; }
        public static string Kontakt { get; set; }
        public static string Datum_rođenja { get; set; }
        public static string Adresa { get; set; }
        public static string Grad { get; set; }
        public static int Stanje_racuna { get; set; }

        public static void Odjava()
        {
            IdUloga = 0;
            Id_korisnik = 0;
            Korisnicko_ime = null;
            Lozinka = null;
            Ime = null;
            Prezime = null;
            Email = null;
            Kontakt = null;
            Datum_rođenja = null;
            Adresa = null;
            Grad = null;
            Stanje_racuna = 0;
        }
    }
}