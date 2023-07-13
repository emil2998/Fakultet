using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.SqlClient;

namespace Projekt_Aurora
{
    public static class KinoUlazniceRepozitorij
    {
        public static List<int> DohvatiSjedala(Raspored raspored)
        {

            List<int> lista = new List<int>();

            string sqlUpit = $"SELECT broj_sjedala FROM kino_ulaznica JOIN projekcija ON projekcija.id_projekcija=kino_ulaznica.id_projekcija WHERE kino_ulaznica.id_projekcija = {raspored.IDprojekcije}";
            SqlDataReader dr = DB.Instance.DohvatiDataReader(sqlUpit);
            while (dr.Read())
            {
                int a = int.Parse(dr["broj_sjedala"].ToString());
                lista.Add(a);
            }
            dr.Close();
            return lista;
        }

        public static int SpremiRezervaciju(KinoUlaznica kinoUlaznica)
        {
            string sqlUpit = "";
            sqlUpit = $"INSERT INTO kino_ulaznica (id_projekcija,id_korisnik,status_uplate,broj_sjedala,rezervacija,vrijeme_izdavanja) VALUES ('{kinoUlaznica.IDProjekcije}','{kinoUlaznica.IDKorisnik}','{kinoUlaznica.StatusUplate}','{kinoUlaznica.BrojSjedala}','{kinoUlaznica.StatusRezervacija}','{kinoUlaznica.VrijemeIzdavanja}')";
            
            return DB.Instance.IzvrsiUpit(sqlUpit);
        }
    }
}
