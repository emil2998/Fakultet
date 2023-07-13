using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.SqlClient;
using System.ComponentModel.DataAnnotations;
namespace Funkcionalnost_Pretraživanja_Kina_i_Rasporeda
{
    public static class ValidacijaUnosa
    {
        public static string ProvjeriKorisnickoIme(string korisnickoIme) 
        {
            string povratnaPoruka = "";
            if (korisnickoIme.All(Char.IsLetterOrDigit) == false)
            {
                povratnaPoruka += "Korisničko ime može sadržavati samo slova i brojeve!\n";
            }
            if(korisnickoIme == "")
            {
                povratnaPoruka += "Korisničko ime mora biti uneseno!\n";
            }
            return povratnaPoruka = "";
        }

        public static string ProvjeriLozinku(string lozinka)
        {
            string povratnaPoruka = "";
            if (lozinka.All(Char.IsLetterOrDigit) == false)
            {
                povratnaPoruka += "Lozinka može sadržavati samo slova i brojeve!\n";
            }
            if (lozinka == "")
            {
                povratnaPoruka += "Lozinka mora biti unesena!\n";
            }
            return povratnaPoruka = "";
        }

        public static string ProvjeriIme(string ime)
        {
            string povratnaPoruka = "";
            if (ime.All(Char.IsLetter) == false)
            {
                povratnaPoruka += "Ime može sadržavati samo slova!\n";
            }
            if (ime == "")
            {
                povratnaPoruka += "Ime mora biti uneseno!\n";
            }
            return povratnaPoruka = "";
        }

        public static string ProvjeriPrezime(string prezime)
        {
            string povratnaPoruka = "";
            
            if (prezime.All(Char.IsLetter) == false)
            {
                povratnaPoruka += "Prezime može sadržavati samo slova!\n";
            }
            if (prezime == "")
            {
                povratnaPoruka += "Prezime mora biti uneseno!\n";
            }
            return povratnaPoruka = "";
        }

        public static string ProvjeriEmail(string email)
        {
            string povratnaPoruka = "";

            if (new EmailAddressAttribute().IsValid(email) == false)
            {
                povratnaPoruka += "Email nije ispravnog formata!\n";
            }
            if (email == "")
            {
                povratnaPoruka += "Email mora biti unesen!\n";
            }
            return povratnaPoruka = "";
        }

        public static string ProvjeriKontakt(string kontakt)
        {
            string povratnaPoruka = "";

            if (kontakt.All(Char.IsDigit) == false)
            {
                povratnaPoruka += "Kontakt može sadržavati samo brojeve!\n";
            }
            if (kontakt == "")
            {
                povratnaPoruka += "Kontakt mora biti unesen!\n";
            }
            return povratnaPoruka = "";
        }

        public static string ProvjeriAdresu(string adresa)
        {
            string povratnaPoruka = "";

            if (adresa.All(c => Char.IsLetterOrDigit(c) || c == ' ') == false)
            {
                povratnaPoruka += "Adresa može sadržavati samo slova i brojeve!\n";
            }
            if (adresa == "")
            {
                povratnaPoruka += "Adresa mora biti unesena!\n";
            }
            return povratnaPoruka = "";
        }

        public static string ProvjeriGrad(string grad)
        {
            string povratnaPoruka = "";

            if (grad.All(c => Char.IsLetter(c) || c == ' ') == false)
            {
                povratnaPoruka += "Grad može sadržavati samo slova!\n";
            }
            if (grad == "")
            {
                povratnaPoruka += "Grad mora biti unesen!\n";
            }
            return povratnaPoruka = "";
        }

        public static string ProvjeriPotvrduLozinke(string lozinka,string potvrdaLozinke)
        {
            string povratnaPoruka = "";

            if (potvrdaLozinke.All(Char.IsLetterOrDigit) == false)
            {
                povratnaPoruka += "Potvrda lozinke može sadržavati samo slova i brojeve!\n";
            }
            if (lozinka == "")
            {
                povratnaPoruka += "Potvrda lozinke mora biti unesena!\n";
            }
            if (potvrdaLozinke != lozinka)
            {
                povratnaPoruka += "Lozinke se moraju podudarati!\n";
            }
            return povratnaPoruka = "";
        }

        public static string ProvjeriNazivDuze(string naziv)
        {
            string povratnaPoruka = "";

            if (naziv.All(c => Char.IsLetterOrDigit(c) || c == ' ' || c == ':' || c == '.' || c == '!' || c == '?' || c == ',') == false)
            {
                povratnaPoruka += "Naziv može sadržavati samo slova,brojeve i neke specijalne znakove (?!:.,)!\n";
            }
            if (naziv == "")
            {
                povratnaPoruka += "Naziv mora biti unesen!\n";
            }
            return povratnaPoruka;
        }

        public static string ProvjeriNazivKrace(string naziv)
        {
            string povratnaPoruka = "";

            if (naziv.All(Char.IsLetter) == false)
            {
                povratnaPoruka += "Naziv može sadržavati samo slova!\n";
            }
            if (naziv == "")
            {
                povratnaPoruka += "Naziv mora biti unesen!\n";
            }
            return povratnaPoruka;
        }

        public static string ProvjeriGodinu(string godina)
        {
            string povratnaPoruka = "";

            //if (godina != "" && godina.All(Char.IsDigit) == true)
            //{
            //    godina = int.Parse(godina);
            //}
            if (godina.All(Char.IsDigit) == false)
            {
                povratnaPoruka += "Godina može sadržavati samo brojeve!\n";
            }
            if (godina == "")
            {
                povratnaPoruka += "Godina mora biti unesena!\n";
            }
            return povratnaPoruka;
        }

        public static string ProvjeriRedatelja(string redatelj)
        {
            string povratnaPoruka = "";

            if (redatelj.All(c => Char.IsLetter(c) || c == ' ') == false)
            {
                povratnaPoruka += "Redatelj može sadržavati samo slova!\n";
            }
            if (redatelj == "")
            {
                povratnaPoruka += "Redatelj mora biti unesen!\n";
            }
            return povratnaPoruka;
        }

        public static string ProvjeriOpis(string opis)
        {
            string povratnaPoruka = "";

            if (opis == "")
            {
                povratnaPoruka += "Opis mora biti unesen!\n";
            }
            return povratnaPoruka;
        }

        public static string ProvjeriSate(string sati)
        {
            string povratnaPoruka = "";

            if (sati.All(Char.IsDigit) == false)
            {
                povratnaPoruka += "Sati moraju biti izraženi brojevima!\n";
            }
            if (sati == "")
            {
                povratnaPoruka += "Sati moraju biti uneseni!\n";
            }
            return povratnaPoruka;
        }

        public static string ProvjeriMinute(string minute)
        {
            string povratnaPoruka = "";

            if (minute.All(Char.IsDigit) == false)
            {
                povratnaPoruka += "Minute moraju biti izražene brojevima!\n";
            }
            if (minute == "")
            {
                povratnaPoruka += "Minute moraju biti unesene!\n";
            }
            return povratnaPoruka;
        }
        
    }
}
