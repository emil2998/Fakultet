using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.SqlClient;
using System.Windows.Forms;

namespace Projekt_Aurora
{
    public static class DvoranaRepozitorij
    {
        public static Dvorana DohvatiDvoranu(SqlDataReader dr)
        {
            Dvorana dvorana = null;
            if (dr != null)
            {
                dvorana = new Dvorana();
                dvorana.ID = int.Parse(dr["id_dvorana"].ToString());
                dvorana.Naziv = dr["naziv"].ToString();
                dvorana.Broj_redova = int.Parse(dr["broj_redova"].ToString());
                dvorana.Broj_stupaca = int.Parse(dr["broj_stupaca"].ToString());
                dvorana.Id_kina = int.Parse(dr["id_kino"].ToString());
            }
            return dvorana;
        }

        public static List<Dvorana> DohvatiDvorane()
        {
            List<Dvorana> lista = new List<Dvorana>();
            string sqlUpit = "SELECT * FROM dvorana";
            SqlDataReader dr = DB.Instance.DohvatiDataReader(sqlUpit);
            while (dr.Read())
            {
                Dvorana dvorana = DohvatiDvoranu(dr);
                lista.Add(dvorana);
            }
            dr.Close();
            foreach (Dvorana dvorana in lista)
            {
                string sqlUpit2 = $"SELECT * FROM kino WHERE id_kino= '{dvorana.Id_kina}'";
                SqlDataReader dr2 = DB.Instance.DohvatiDataReader(sqlUpit2);
                while (dr2.Read())
                {
                    Kino kino = null;
                    kino = KinoRepozitorij.DohvatiKino(dr2);
                    dvorana.NazivKina = kino.Naziv;
                }
                dr2.Close();
            }
            return lista;
        }

        public static int Spremi(Dvorana dvorana)
        {
            string sqlUpit = "";
            bool postojiZapis = false;
            List<Dvorana> dvorane = new List<Dvorana>();
            dvorane = DohvatiDvorane();
            foreach (Dvorana item in dvorane)
            {
                if (item.ID == dvorana.ID)
                {
                    postojiZapis = true;
                }
            }
            if (postojiZapis == false)
            {
                sqlUpit = $"INSERT INTO dvorana (naziv,broj_redova,broj_stupaca,id_kino) VALUES ('{dvorana.Naziv}','{dvorana.Broj_redova}','{dvorana.Broj_stupaca}','{dvorana.Id_kina}')";
            }
            return DB.Instance.IzvrsiUpit(sqlUpit);
        }

        public static int IzmijeniDvoranu(Dvorana dvorana)
        {
            string sqlUpit = "";
            bool postojiZapis = false;
            List<Dvorana> dvorane = new List<Dvorana>();
            dvorane = DohvatiDvorane();
            foreach (Dvorana item in dvorane)
            {
                if (item.ID == dvorana.ID)
                {
                    postojiZapis = true;
                }
            }
            if (postojiZapis == true)
            {
                sqlUpit = $"UPDATE dvorana SET naziv = '{dvorana.Naziv}', broj_redova = '{dvorana.Broj_redova}', broj_stupaca = '{dvorana.Broj_stupaca}',id_kino = '{dvorana.Id_kina}' WHERE id_dvorana = {dvorana.ID}";
            }
            return DB.Instance.IzvrsiUpit(sqlUpit);
        }

        public static int Obrisi(Dvorana dvorana)
        {
            string sqlUpit = "";
            bool postojiZapis = false;
            List<Dvorana> dvorane = new List<Dvorana>();
            dvorane = DohvatiDvorane();
            foreach (Dvorana item in dvorane)
            {
                if (item.ID == dvorana.ID)
                {
                    postojiZapis = true;
                }
            }
            if (postojiZapis == true)
            {
                sqlUpit = $"DELETE FROM dvorana WHERE id_dvorana = {dvorana.ID}";
            }
            return DB.Instance.IzvrsiUpit(sqlUpit);

        }

        public static Dvorana DohvatiDvoranuRezervacija(Raspored raspored)
        {
            
            Dvorana dvorana = new Dvorana();
            
            string sqlUpit = $"SELECT broj_stupaca,broj_redova FROM dvorana JOIN projekcija ON projekcija.id_dvorana=dvorana.id_dvorana WHERE projekcija.id_projekcija = {raspored.IDprojekcije}";
            SqlDataReader dr = DB.Instance.DohvatiDataReader(sqlUpit);
            while (dr.Read())
            {
                
                dvorana.Broj_redova = int.Parse(dr["broj_redova"].ToString());
                dvorana.Broj_stupaca = int.Parse(dr["broj_stupaca"].ToString());
            }
            dr.Close();
            return dvorana;
        }
    }
}
