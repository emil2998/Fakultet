using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.SqlClient;
namespace Projekt_Aurora
{
    public static class GlumacRepozitorij
    {
        public static Glumac DohvatiGlumca(SqlDataReader dr)
        {
            Glumac glumac = null;
            if (dr != null)
            {
                glumac = new Glumac();
                glumac.ID = int.Parse(dr["id_glumac"].ToString());
                glumac.Ime = dr["ime"].ToString();
                glumac.Prezime = dr["prezime"].ToString();
            }
            return glumac;
        }

        public static List<Glumac> DohvatiGlumce()
        {
            List<Glumac> lista = new List<Glumac>();
            string sqlUpit = $"SELECT * FROM glumci";
            SqlDataReader dr = DB.Instance.DohvatiDataReader(sqlUpit);
            while (dr.Read())
            {
                Glumac glumac = DohvatiGlumca(dr);
                lista.Add(glumac);
            }
            dr.Close();
            return lista;
        }

        public static int DodajGlumca(Glumac glumac)
        {
            string sqlUpit = "";
            bool postojiZapis = false;
            List<Glumac> glumci = new List<Glumac>();
            glumci = DohvatiGlumce();
            foreach (Glumac item in glumci)
            {
                if (item.ID == glumac.ID)
                {
                    postojiZapis = true;
                }
            }
            if (postojiZapis == false)
            {
                sqlUpit = $"INSERT INTO glumci (ime,prezime) VALUES ('{glumac.Ime}','{glumac.Prezime}')";
            }
            return DB.Instance.IzvrsiUpit(sqlUpit);
        }

        public static int IzmijeniGlumca(Glumac glumac)
        {
            string sqlUpit = "";
            bool postojiZapis = false;
            List<Glumac> glumci = new List<Glumac>();
            glumci = DohvatiGlumce();
            foreach (Glumac item in glumci)
            {
                if (item.ID == glumac.ID)
                {
                    postojiZapis = true;
                }
            }
            if (postojiZapis == true)
            {
                sqlUpit = $"UPDATE glumci SET ime = '{glumac.Ime}', prezime = '{glumac.Prezime}' WHERE id_glumac = {glumac.ID}";
            }
            return DB.Instance.IzvrsiUpit(sqlUpit);
        }

        public static int ObrisiGlumca(Glumac glumac)
        {
            string sqlUpit = "";
            bool postojiZapis = false;
            List<Glumac> glumci = new List<Glumac>();
            glumci = DohvatiGlumce();
            foreach (Glumac item in glumci)
            {
                if (item.ID == glumac.ID)
                {
                    postojiZapis = true;
                }
            }
            if (postojiZapis == true)
            {
                sqlUpit = $"DELETE FROM glumci WHERE id_glumac = {glumac.ID}";
            }
            return DB.Instance.IzvrsiUpit(sqlUpit);
        }

        public static List<Glumac> DohvatiGlumceFilma(Film film)
        {
            List<Glumac> lista = new List<Glumac>();
            string sqlUpit = $"SELECT * FROM glumci JOIN glumi ON glumci.id_glumac=glumi.id_glumac WHERE glumi.id_film='{film.ID}'";
            SqlDataReader dr = DB.Instance.DohvatiDataReader(sqlUpit);
            while (dr.Read())
            {
                Glumac glumac = DohvatiGlumca(dr);
                lista.Add(glumac);
            }
            dr.Close();
            return lista;
        }

        public static List<Glumac> DohvatiSveNedodjeljeneGlumce(Film film)
        {
            List<string> lista_koji_nisu = new List<string>();
            List<Glumac> lista_koji_jesu = new List<Glumac>();
            List<Glumac> lista_svi = new List<Glumac>();
            string sqlUpit = $"SELECT id_glumac FROM glumi WHERE id_film = '{film.ID}'";
            SqlDataReader dr = DB.Instance.DohvatiDataReader(sqlUpit);
            while (dr.Read())
            {
                string id_glumac = DohvatiId_glumac(dr);
                lista_koji_nisu.Add(id_glumac);
            }
            dr.Close();
            string sqlUpit2 = $"Select * FROM glumci";
            SqlDataReader dr2 = DB.Instance.DohvatiDataReader(sqlUpit2);
            while (dr2.Read())
            {
                Glumac glumac = DohvatiGlumca(dr2);
                lista_svi.Add(glumac);
            }
            dr2.Close();
            foreach (Glumac glumac in lista_svi)
            {
                bool postoji = false;
                foreach (string id in lista_koji_nisu)
                {
                    if (glumac.ID == int.Parse(id))
                    {
                        postoji = true;
                    }
                }
                if (postoji == false)
                {
                    lista_koji_jesu.Add(glumac);
                }
            }
            return lista_koji_jesu;
        }

        public static string DohvatiId_glumac(SqlDataReader dr)
        {
            string id_glumac = null;
            if (dr != null)
            {
                id_glumac = dr["id_glumac"].ToString();
            }
            return id_glumac;
        }

        public static int IzbaciGlumcaIzFilma(Film selektiraniFilm, Glumac glumac)
        {
            string sqlUpit = "";
            sqlUpit = $"DELETE FROM glumi WHERE id_glumac = {glumac.ID} AND id_film = {selektiraniFilm.ID}";
            return DB.Instance.IzvrsiUpit(sqlUpit);
        }

        public static int DodjeliGlumcaFilmu(Film selektiraniFilm, Glumac glumac)
        {
            string sqlUpit = "";
            sqlUpit = $"INSERT INTO glumi (id_film,id_glumac) VALUES ('{selektiraniFilm.ID}','{glumac.ID}')";
            return DB.Instance.IzvrsiUpit(sqlUpit);
        }
    }
}
