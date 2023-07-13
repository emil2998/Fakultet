using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Projekt_Aurora
{
    public static class KalendarTransakcijaRepozitorij
    {
        public static List <Kalendar> DohvatiKalendar(string sortirajPo="projekcija.vrijeme")
        {
            List<Kalendar> lista = new List<Kalendar>();
            string sqlUpit = $"SELECT film.naziv, projekcija.vrijeme, kino_ulaznica.status_uplate, kino_ulaznica.broj_sjedala FROM kino_ulaznica JOIN projekcija ON kino_ulaznica.id_projekcija = projekcija.id_projekcija JOIN film ON projekcija.id_film = film.id_film WHERE kino_ulaznica.id_korisnik = {UlogiraniKorisnik.Id_korisnik} ORDER BY {sortirajPo} ";
            
           
            SqlDataReader dr = DB.Instance.DohvatiDataReader(sqlUpit);
            while (dr.Read())
            {
                Kalendar kalendar = StvoriKalendar(dr); 
                lista.Add(kalendar);
            }
            dr.Close();
            return lista;
        }
        public static Kalendar StvoriKalendar(SqlDataReader dr)
        {
            Kalendar kalendar = null;
            if (dr != null)
            {
                kalendar = new Kalendar();
                kalendar.Film = dr["naziv"].ToString();
                kalendar.Datum = Convert.ToDateTime (dr["vrijeme"].ToString());
                kalendar.StatusUplate = Convert.ToInt32 (dr["status_uplate"].ToString()); //treba int
                kalendar.brojSjedala =Convert.ToInt32 (dr["broj_sjedala"].ToString());               
            }

            return kalendar;
        }
    }
}
