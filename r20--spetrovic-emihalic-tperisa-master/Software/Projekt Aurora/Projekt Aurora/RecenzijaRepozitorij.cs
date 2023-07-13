using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.SqlClient;
namespace Projekt_Aurora
{
    public static class RecenzijaRepozitorij
    {
        public static Recenzija DohvatiRecenziju(SqlDataReader dr)
        {
            Recenzija recenzija = null;
            if (dr != null)
            {
                recenzija = new Recenzija();
                recenzija.Ime = dr["ime"].ToString();
                recenzija.Prezime = dr["prezime"].ToString();
                recenzija.Ocijena = decimal.Parse(dr["ocijena"].ToString());
                recenzija.Komentar = dr["komentar"].ToString();
            }
            return recenzija;
        }

        public static List<Recenzija> DohvatiRecenzije(Film film)
        {
            List<Recenzija> lista = new List<Recenzija>();
            string sqlUpit = $"SELECT korisnik.ime AS ime,korisnik.prezime AS prezime,recenzija.ocijena AS ocijena,recenzija.komentar AS komentar FROM korisnik JOIN recenzija ON korisnik.id_korisnik=recenzija.id_korisnik WHERE recenzija.id_film='{film.ID}'";
            SqlDataReader dr = DB.Instance.DohvatiDataReader(sqlUpit);
            while (dr.Read())
            {
                Recenzija recenzija = DohvatiRecenziju(dr);
                lista.Add(recenzija);
            }
            dr.Close();
            return lista;
        }

        public static int SpremiRecenziju(Recenzija recenzija,Film film)
        {
            string sqlUpit = "";
            bool postojiZapis= false;
            List<Recenzija> recenzije = new List<Recenzija>();
            recenzije = DohvatiRecenzije(film);
            foreach (Recenzija item in recenzije)
            {
                if (item.IdKorisnik == recenzija.IdKorisnik)
                {
                    postojiZapis = true;
                }
            }
            if (postojiZapis==false)
            {
                sqlUpit = $"INSERT INTO recenzija (id_film,id_korisnik,ocijena,komentar) VALUES ( '{recenzija.IdFilm}','{recenzija.IdKorisnik}','{recenzija.Ocijena}','{recenzija.Komentar}')";
            }
            return DB.Instance.IzvrsiUpit(sqlUpit);
        }
    }
}
