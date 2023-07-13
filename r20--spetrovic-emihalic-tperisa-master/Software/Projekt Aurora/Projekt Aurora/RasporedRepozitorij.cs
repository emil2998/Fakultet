using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.SqlClient;
namespace Projekt_Aurora
{
    public static class RasporedRepozitorij
    {
        public static Raspored StvoriRaspored(SqlDataReader dr)
        {
            Raspored raspored = null;
            if (dr != null)
            {
                raspored = new Raspored();
                raspored.NazivDvorane = dr["naziv1"].ToString();
                raspored.NazivFilma = dr["naziv2"].ToString();
                raspored.Vrijeme =dr["vrijeme"].ToString();
                raspored.TrajanjeFilma = dr["trajanje"].ToString();
                raspored.Iznos = decimal.Parse(dr["iznos"].ToString());
                raspored.IDprojekcije= int.Parse(dr["id_projekcija"].ToString());
                
            }
            return raspored;
        }

        public static List<Raspored> DohvatiRaspored(Kino kino)
        {
            List<Raspored> lista = new List<Raspored>();
            string sqlUpit = $"SELECT dvorana.naziv AS naziv1, film.naziv AS naziv2, projekcija.vrijeme AS vrijeme, film.trajanje AS trajanje, projekcija.iznos AS iznos, projekcija.id_projekcija FROM dvorana JOIN projekcija ON dvorana.id_dvorana = projekcija.id_dvorana JOIN film ON projekcija.id_film = film.id_film WHERE dvorana.id_kino = {kino.ID} ";
            SqlDataReader dr = DB.Instance.DohvatiDataReader(sqlUpit);
            while (dr.Read())
            {
                Raspored raspored = StvoriRaspored(dr);
                lista.Add(raspored);
            }
            dr.Close();
            return lista;
        }
    }
}
