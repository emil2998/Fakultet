using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.SqlClient;
namespace Projekt_Aurora
{
    public static class KorisnikRepozitorij
    {
        public static Korisnik DohvatiKorisnika(SqlDataReader dr)
        {
            Korisnik korisnik = null;
            if (dr != null)
            {
                korisnik = new Korisnik();
                korisnik.Id_korisnik = int.Parse(dr["id_korisnik"].ToString());
                korisnik.Id_uloga = int.Parse(dr["id_uloga"].ToString());
                korisnik.Korisnicko_ime = dr["korisnicko_ime"].ToString();
                korisnik.Lozinka = dr["lozinka"].ToString();
                korisnik.Ime = dr["ime"].ToString();
                korisnik.Prezime = dr["prezime"].ToString();
                korisnik.Email = dr["email"].ToString();
                korisnik.Kontakt = dr["kontakt"].ToString();
                korisnik.Datum_rođenja = dr["datum_rođenja"].ToString();
                korisnik.Adresa = dr["adresa"].ToString();
                korisnik.Grad = dr["grad"].ToString();
                korisnik.Stanje_racuna = int.Parse(dr["stanje_racuna"].ToString());
            }
            return korisnik;
        }

        public static List<Korisnik> DohvatiKorisnike()
        {
            List<Korisnik> lista = new List<Korisnik>();
            string sqlUpit = $"SELECT * FROM korisnik";
            SqlDataReader dr = DB.Instance.DohvatiDataReader(sqlUpit);
            while (dr.Read())
            {
                Korisnik korisnik = DohvatiKorisnika(dr);
                lista.Add(korisnik);
            }
            dr.Close();
            return lista;
        }

        public static int Spremi(Korisnik korisnik)
        {
            string sqlUpit = "";
            if (korisnik.Id_korisnik == 0)
            {
                sqlUpit = $"INSERT INTO korisnik (id_uloga, korisnicko_ime, lozinka, ime, prezime, email, kontakt, datum_rođenja, adresa, grad, stanje_racuna) VALUES ('1', '{korisnik.Korisnicko_ime}', '{korisnik.Lozinka}', '{korisnik.Ime}', '{korisnik.Prezime}', '{korisnik.Email}', '{korisnik.Kontakt}','{korisnik.Datum_rođenja}','{korisnik.Adresa}','{korisnik.Grad}','{korisnik.Stanje_racuna}')";
            }
            else
            {
                sqlUpit = $"UPDATE korisnik SET id_uloga = '{korisnik.Id_uloga}', korisnicko_ime = '{korisnik.Korisnicko_ime}', lozinka = '{korisnik.Lozinka}', ime = '{korisnik.Ime}', prezime = '{korisnik.Prezime}', email = '{korisnik.Email}', kontakt = '{korisnik.Kontakt}' , datum_rođenja = '{korisnik.Datum_rođenja}', adresa = '{korisnik.Adresa}', grad = '{korisnik.Grad}', stanje_racuna = '{korisnik.Stanje_racuna}' WHERE Id = {korisnik.Id_korisnik}";
            }
            return DB.Instance.IzvrsiUpit(sqlUpit);
        }

        public static int Obrisi(Korisnik korisnik)
        {
            string sqlDelete = "DELETE FROM Student WHERE Id = " + korisnik.Id_korisnik;
            return DB.Instance.IzvrsiUpit(sqlDelete);
        }

        public static int SpremiStanjeRacuna(int iznos,int idKorisnik)
        {
            string sqlUpit = "";
            sqlUpit = $"UPDATE korisnik SET stanje_racuna = '{iznos}' WHERE id_korisnik = {idKorisnik}";
            
            return DB.Instance.IzvrsiUpit(sqlUpit);
        }
        public static int DohvatiStanjeRacuna(int idKorisnik)
        {
            int stanjeRacuna = 0;
            string sqlUpit = "";
            sqlUpit = $"SELECT stanje_racuna FROM korisnik WHERE id_korisnik = {idKorisnik}";
            SqlDataReader dr = DB.Instance.DohvatiDataReader(sqlUpit);
            while (dr.Read())
            {
                stanjeRacuna = int.Parse(dr["stanje_racuna"].ToString());
            }
            dr.Close();
           
            return stanjeRacuna;
        }

        public static int AžurirajKorisnika(Korisnik korisnik)
        {
            string sqlUpit = "";
            sqlUpit = $"UPDATE korisnik SET korisnicko_ime = '{korisnik.Korisnicko_ime}', email = '{korisnik.Email}', kontakt = '{korisnik.Kontakt}' , datum_rođenja = '{korisnik.Datum_rođenja}', adresa = '{korisnik.Adresa}', grad = '{korisnik.Grad}'  WHERE id_korisnik = {korisnik.Id_korisnik}";

            return DB.Instance.IzvrsiUpit(sqlUpit);
        }
    }
}
