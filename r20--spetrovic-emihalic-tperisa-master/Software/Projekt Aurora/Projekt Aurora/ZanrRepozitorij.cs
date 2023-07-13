using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.SqlClient;
namespace Projekt_Aurora
{
    public static class ZanrRepozitorij
    {
        public static Zanr DohvatiZanr(SqlDataReader dr)
        {
            Zanr zanr = null;
            if (dr != null)
            {
                zanr = new Zanr();
                zanr.ID = int.Parse(dr["id_zanr"].ToString());
                zanr.Naziv = dr["naziv"].ToString();
            }
            return zanr;
        }

        public static List<Zanr> DohvatiZanrove()
        {
            List<Zanr> lista = new List<Zanr>();
            string sqlUpit = "SELECT * FROM zanr";
            SqlDataReader dr = DB.Instance.DohvatiDataReader(sqlUpit);
            while (dr.Read())
            {
                Zanr zanr = DohvatiZanr(dr);
                lista.Add(zanr);
            }
            dr.Close();
            return lista;
        }

        public static int DodajZanr(Zanr zanr)
        {
            string sqlUpit = "";
            bool postojiZapis = false;
            List<Zanr> zanrovi = new List<Zanr>();
            zanrovi = DohvatiZanrove();
            foreach (Zanr item in zanrovi)
            {
                if (item.ID == zanr.ID)
                {
                    postojiZapis = true;
                }
            }
            if (postojiZapis == false)
            {
                sqlUpit = $"INSERT INTO zanr (naziv) VALUES ('{zanr.Naziv}')";
            }
            return DB.Instance.IzvrsiUpit(sqlUpit);
        }

        public static int DodjeliZanrFilmu(Film selektiraniFilm, Zanr zanr)
        {
            string sqlUpit = "";
            sqlUpit = $"INSERT INTO film_zanr (id_zanr,id_film) VALUES ('{zanr.ID}','{selektiraniFilm.ID}')";
            return DB.Instance.IzvrsiUpit(sqlUpit);
        }

        public static int IzmijeniZanr(Zanr zanr)
        {
            string sqlUpit = "";
            bool postojiZapis = false;
            List<Zanr> zanrovi = new List<Zanr>();
            zanrovi = DohvatiZanrove();
            foreach (Zanr item in zanrovi)
            {
                if (item.ID == zanr.ID)
                {
                    postojiZapis = true;
                }
            }
            if (postojiZapis == true)
            {
                sqlUpit = $"UPDATE zanr SET naziv = '{zanr.Naziv}' WHERE id_zanr = {zanr.ID}";
            }
            return DB.Instance.IzvrsiUpit(sqlUpit);
        }

        public static int IzbaciZanrFilma(Film selektiraniFilm, Zanr zanr)
        {
            string sqlUpit = "";
            sqlUpit = $"DELETE FROM film_zanr WHERE id_zanr = {zanr.ID} AND id_film = {selektiraniFilm.ID}";
            return DB.Instance.IzvrsiUpit(sqlUpit);
        }

        public static int ObrisiZanr(Zanr zanr)
        {
            string sqlUpit = "";
            bool postojiZapis = false;
            List<Zanr> zanrovi = new List<Zanr>();
            zanrovi = DohvatiZanrove();
            foreach (Zanr item in zanrovi)
            {
                if (item.ID == zanr.ID)
                {
                    postojiZapis = true;
                }
            }
            if (postojiZapis == true)
            {
                sqlUpit = $"DELETE FROM zanr WHERE id_zanr = {zanr.ID}";
            }
            return DB.Instance.IzvrsiUpit(sqlUpit);
        }

        public static List<Zanr> DohvatiZanroveFilma(Film film)
        {
            List<Zanr> lista = new List<Zanr>();
            string sqlUpit = $"SELECT * FROM zanr JOIN film_zanr ON zanr.id_zanr=film_zanr.id_zanr WHERE film_zanr.id_film='{film.ID}'";
            SqlDataReader dr = DB.Instance.DohvatiDataReader(sqlUpit);
            while (dr.Read())
            {
                Zanr zanr = DohvatiZanr(dr);
                lista.Add(zanr);
            }
            dr.Close();
            return lista;
        }

        public static List<Zanr> DohvatiSveNedodjeljeneZanrove(Film film)
        {
            List<string> lista_koji_nisu = new List<string>();
            List<Zanr> lista_koji_jesu = new List<Zanr>();
            List<Zanr> lista_svi = new List<Zanr>();
            string sqlUpit = $"SELECT id_zanr FROM film_zanr WHERE id_film = '{film.ID}'";
            SqlDataReader dr = DB.Instance.DohvatiDataReader(sqlUpit);
            while (dr.Read())
            {
                string id_zanr = DohvatiId_zanr(dr);
                lista_koji_nisu.Add(id_zanr);
            }
            dr.Close();
            string sqlUpit2 = $"Select * FROM zanr";
            SqlDataReader dr2 = DB.Instance.DohvatiDataReader(sqlUpit2);
            while (dr2.Read())
            {
                Zanr zanr = DohvatiZanr(dr2);
                lista_svi.Add(zanr);
            }
            dr2.Close();
            foreach(Zanr zanr in lista_svi)
            {
                bool postoji = false;
                foreach(string id in lista_koji_nisu)
                {
                    if (zanr.ID == int.Parse(id))
                    {
                        postoji = true;
                    }
                }
                if(postoji == false)
                {
                    lista_koji_jesu.Add(zanr);
                }
            }
            return lista_koji_jesu;
        }

        public static string DohvatiId_zanr(SqlDataReader dr)
        {
            string id_zanr = null;
            if (dr != null)
            {
                id_zanr = dr["id_zanr"].ToString();
            }
            return id_zanr;
        }
    }
}
