using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.SqlClient;

namespace Projekt_Aurora
{
    public static class ProjekcijaRepozitorij
    {

        public static Projekcija DohvatiProjekciju(SqlDataReader dr)
        {
            Projekcija projekcija = null;
            if (dr != null)
            {
                projekcija = new Projekcija();
                projekcija.Id = int.Parse(dr["id_projekcija"].ToString());
                projekcija.Id_film = int.Parse(dr["id_film"].ToString());
                projekcija.Id_dvorana = int.Parse(dr["id_dvorana"].ToString());
                projekcija.Vrijeme= dr["vrijeme"].ToString();
                projekcija.Iznos = (int)decimal.Parse(dr["iznos"].ToString());
                projekcija.Datum = dr["datum"].ToString();
               
            }
            return projekcija;
        }

        public static List<Projekcija> DohvatiProjekcije()
        {
            List<Projekcija> lista = new List<Projekcija>();
            string sqlUpit = "SELECT * FROM projekcija";
            SqlDataReader dr = DB.Instance.DohvatiDataReader(sqlUpit);
            while (dr.Read())
            {
                Projekcija projekcija = DohvatiProjekciju(dr);
                lista.Add(projekcija);
            }
            dr.Close();
            foreach (Projekcija projekcija in lista)
            {
                string sqlUpit2 = $"SELECT * FROM film WHERE id_film= '{projekcija.Id_film}'";
                SqlDataReader dr2 = DB.Instance.DohvatiDataReader(sqlUpit2);
                while (dr2.Read())
                {
                    Film film = null;
                    film = FilmRepozitorij.DohvatiFilm(dr2);
                    projekcija.NazivFilma = film.Naziv;
                }
                dr2.Close();

                string sqlUpit3 = $"SELECT * FROM dvorana WHERE id_dvorana= '{projekcija.Id_dvorana}'";
                SqlDataReader dr3 = DB.Instance.DohvatiDataReader(sqlUpit3);
                while (dr3.Read())
                {
                    Dvorana dvorana = null;
                    dvorana = DvoranaRepozitorij.DohvatiDvoranu(dr3);
                    projekcija.NazivDvorane = dvorana.Naziv;
                    projekcija.IDKina = dvorana.Id_kina;
                   
                }
                dr3.Close();

                string sqlUpit4 = $"SELECT * FROM kino WHERE id_kino= '{projekcija.IDKina}'";
                SqlDataReader dr4 = DB.Instance.DohvatiDataReader(sqlUpit4);
                while (dr4.Read())
                {
                    Kino kino = null;
                    kino = KinoRepozitorij.DohvatiKino(dr4);                 
                    projekcija.NazivKina = kino.Naziv;

                }
                dr4.Close();

            }
            return lista;
        }

       

        public static int Spremi(Projekcija projekcija)
        {
            string sqlUpit = "";
            bool postojiZapis = false;
            List<Projekcija> projekcije = new List<Projekcija>();
            projekcije = DohvatiProjekcije();
            foreach (Projekcija item in projekcije)
            {
                if (item.Id == projekcija.Id)
                {
                    postojiZapis = true;
                }
            }
            if (postojiZapis == false)
            {
                sqlUpit = $"INSERT INTO projekcija (id_film,id_dvorana,vrijeme,iznos,datum) VALUES ('{projekcija.Id_film}','{projekcija.Id_dvorana}','{projekcija.Vrijeme}','{projekcija.Iznos}','{projekcija.Datum}')";
            }
            return DB.Instance.IzvrsiUpit(sqlUpit);
        }

        public static int IzmijeniProjekciju(Projekcija projekcija)
        {
            string sqlUpit = "";
            bool postojiZapis = false;
            List<Projekcija> projekcije = new List<Projekcija>();
            projekcije = DohvatiProjekcije();
            foreach (Projekcija item in projekcije)
            {
                if (item.Id == projekcija.Id)
                {
                    postojiZapis = true;
                }
            }
            if (postojiZapis == true)
            {
                sqlUpit = $"UPDATE projekcija SET id_film = '{projekcija.Id_film}', id_dvorana = '{projekcija.Id_dvorana}', vrijeme = '{projekcija.Vrijeme}', iznos = '{projekcija.Iznos}', datum = '{projekcija.Datum}' WHERE id_projekcija = {projekcija.Id}";
            }
            return DB.Instance.IzvrsiUpit(sqlUpit);
        }

        public static int Obrisi(Projekcija projekcija)
        {
            string sqlUpit = "";
            bool postojiZapis = false;
            List<Projekcija> projekcije = new List<Projekcija>();
            projekcije = DohvatiProjekcije();
            foreach (Projekcija item in projekcije)
            {
                if (item.Id == projekcija.Id)
                {
                    postojiZapis = true;
                }
            }
            if (postojiZapis == true)
            {
                sqlUpit = $"DELETE FROM projekcija WHERE id_projekcija = {projekcija.Id}";
            }
            return DB.Instance.IzvrsiUpit(sqlUpit);

        }
    }
}
