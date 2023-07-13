using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.SqlClient;
namespace Projekt_Aurora
{
    public static class FilmRepozitorij
    {
        public static Film DohvatiFilm(SqlDataReader dr)
        {
            Film film = null;
            if (dr != null)
            {
                film = new Film();
                film.ID = int.Parse(dr["id_film"].ToString());
                film.Naziv = dr["naziv"].ToString();
                film.Godina = int.Parse(dr["godina"].ToString());
                film.Redatelj = dr["redatelj"].ToString();
                film.Opis = dr["opis"].ToString();
                film.Trajanje = dr["trajanje"].ToString();
            }
            return film;
        }

        public static List<Film> DohvatiFilmove()
        {
            List<Film> lista = new List<Film>();
            string sqlUpit = "SELECT * FROM film";
            SqlDataReader dr = DB.Instance.DohvatiDataReader(sqlUpit);
            while (dr.Read())
            {
               Film film = DohvatiFilm(dr);
               lista.Add(film);
            }
            dr.Close();     
            return lista;
        }

        public static Film  DohvatiZanrFilma(Film film)
        {
            string sqlUpit = $"SELECT zanr.naziv AS naziv FROM zanr JOIN film_zanr ON zanr.id_zanr=film_zanr.id_zanr JOIN film ON film_zanr.id_film=film.id_film WHERE film.id_film='{film.ID}'";
            SqlDataReader dr = DB.Instance.DohvatiDataReader(sqlUpit);
            while (dr.Read())
            {
                film.Zanrovi.Add(dr["naziv"].ToString());
            }
            dr.Close();
            return film;
        }

        public static List<Film> DohvatiFiltriraneFilmove(string uvjet,string sadrzaj)
        {
            List<Film> lista = new List<Film>();
            string sqlUpit = "SELECT * FROM film";
            SqlDataReader dr = DB.Instance.DohvatiDataReader(sqlUpit);
            while (dr.Read())
            {
            Film film = DohvatiFilm(dr);
                if(uvjet == "Naziv")
                {
                    if (film.Naziv.Contains(sadrzaj))
                    {
                        lista.Add(film);
                    }
                }
                if (uvjet == "Godina")
                {
                    if (film.Godina.ToString().Contains(sadrzaj))
                    {
                        lista.Add(film);
                    }
                }
                if (uvjet == "Redatelj")
                {
                    if (film.Redatelj.Contains(sadrzaj))
                    {
                        lista.Add(film);
                    }
                }
                if (uvjet == "Zanr")
                {
                    lista.Add(film);
                }
            }
            dr.Close();
            if (uvjet == "Zanr")
            {
                List<Film> listaFilmovaSZanrom = new List<Film>();
                foreach (Film film in lista)
                    {
                        DohvatiZanrFilma(film);
                        if (film.Zanrovi.Contains(sadrzaj))
                        {
                        listaFilmovaSZanrom.Add(film);
                        }
                    }
                return listaFilmovaSZanrom;
            }
            return lista;
        }

        public static Glumac DohvatiGlumca(SqlDataReader dr)
        {
           Glumac glumac = null;
            if (dr != null)
            {
                glumac = new Glumac();
                glumac.Ime =dr["ime"].ToString();
                glumac.Prezime = dr["prezime"].ToString();
            }
            return glumac;
        }

        public static List<Glumac> DohvatiGlumce(Film film)
        {
            List<Glumac> lista = new List<Glumac>();
            string sqlUpit = $"SELECT ime,prezime FROM glumci JOIN glumi ON glumci.id_glumac=glumi.id_glumac WHERE glumi.id_film='{film.ID}'";
            SqlDataReader dr = DB.Instance.DohvatiDataReader(sqlUpit);
            while (dr.Read())
            {
                Glumac glumac = DohvatiGlumca(dr);
                lista.Add(glumac);
            }
            dr.Close();
            return lista;
        }

        public static int DodajFilm(Film film)
        {
            string sqlUpit = "";
            bool postojiZapis = false;
            List<Film> filmovi = new List<Film>();
            filmovi = DohvatiFilmove();
            foreach (Film item in filmovi)
            {
                if (item.ID == film.ID)
                {
                    postojiZapis = true;
                }
            }
            if (postojiZapis == false)
            {
                sqlUpit = $"INSERT INTO film (naziv,godina,redatelj,opis,trajanje) VALUES ( '{film.Naziv}','{film.Godina}','{film.Redatelj}','{film.Opis}','{film.Trajanje}')";
            }
            return DB.Instance.IzvrsiUpit(sqlUpit);
        }

        public static int IzmijeniFilm(Film film)
        {
            string sqlUpit = "";
            bool postojiZapis = false;
            List<Film> filmovi = new List<Film>();
            filmovi = DohvatiFilmove();
            foreach (Film item in filmovi)
            {
                if (item.ID == film.ID)
                {
                    postojiZapis = true;
                }
            }
            if (postojiZapis == true)
            {
                sqlUpit = $"UPDATE film SET naziv = '{film.Naziv}', godina = '{film.Godina}', redatelj = '{film.Redatelj}', opis = '{film.Opis}', trajanje = '{film.Trajanje}' WHERE id_film = {film.ID}";
            }
            return DB.Instance.IzvrsiUpit(sqlUpit);
        }

        public static int ObrisiFilm(Film film)
        {
            string sqlUpit = "";
            bool postojiZapis = false;
            List<Film> filmovi = new List<Film>();
            filmovi = DohvatiFilmove();
            foreach (Film item in filmovi)
            {
                if (item.ID == film.ID)
                {
                    postojiZapis = true;
                }
            }
            if (postojiZapis == true)
            {
                sqlUpit = $"DELETE FROM film WHERE id_film = {film.ID}";
            }
            return DB.Instance.IzvrsiUpit(sqlUpit);
        }
    }
}
