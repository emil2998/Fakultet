using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.SqlClient;
namespace Projekt_Aurora
{
    public static class KinoRepozitorij
    {
        public static Kino DohvatiKino(SqlDataReader dr)
        {
            Kino kino = null;
            if (dr != null)
            {
                kino = new Kino();
                kino.ID = int.Parse(dr["id_kino"].ToString());
                kino.Naziv = dr["naziv"].ToString();
                kino.Adresa= dr["adresa"].ToString();
            }
            return kino;
        }

        public static List<Kino> DohvatiKina()
        {
            List<Kino> lista = new List<Kino>();
            string sqlUpit = "SELECT * FROM kino";
            SqlDataReader dr = DB.Instance.DohvatiDataReader(sqlUpit);
            while (dr.Read())
            {
                Kino kino = DohvatiKino(dr);
                lista.Add(kino);
            }
            dr.Close();     
            return lista;
        }

        public static int Spremi(Kino kino)
        {
            string sqlUpit = "";
            bool postojiZapis = false;
            List<Kino> kina = new List<Kino>();
            kina = DohvatiKina();
            foreach (Kino item in kina)
            {
                if (item.ID == kino.ID)
                {
                    postojiZapis = true;
                }
            }
            if (postojiZapis == false)
            {
                sqlUpit = $"INSERT INTO kino (naziv,adresa) VALUES ('{kino.Naziv}','{kino.Adresa}')";
            }
            return DB.Instance.IzvrsiUpit(sqlUpit);
        }

        public static int IzmijeniKino(Kino kino)
        {
            string sqlUpit = "";
            bool postojiZapis = false;
            List<Kino> kina = new List<Kino>();
            kina = DohvatiKina();
            foreach (Kino item in kina)
            {
                if (item.ID == kino.ID)
                {
                    postojiZapis = true;
                }
            }
            if (postojiZapis == true)
            {
                sqlUpit = $"UPDATE kino SET naziv = '{kino.Naziv}', adresa = '{kino.Adresa}' WHERE id_kino = {kino.ID}";
            }
            return DB.Instance.IzvrsiUpit(sqlUpit);
        }

        public static int Obrisi(Kino kino)
        {
            string sqlUpit = "";
            bool postojiZapis = false;
            List<Kino> kina = new List<Kino>();
            kina = DohvatiKina();
            foreach (Kino item in kina)
            {
                if (item.ID == kino.ID)
                {
                    postojiZapis = true;
                }
            }
            if (postojiZapis == true)
            {
                sqlUpit = $"DELETE FROM kino WHERE id_kino = {kino.ID}";
            }
            return DB.Instance.IzvrsiUpit(sqlUpit);

        }


    }
}
