using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Projekt_Aurora;

namespace Projekt_Aurora
{
    public static class StatistikaRepozitorij
    {
        public static List<UkupnaStatistika> DohvatiStatistiku(int film_id)
        {
            List<UkupnaStatistika> lista = new List<UkupnaStatistika>();
            string sqlUpit = $"SELECT (SELECT COUNT(*) FROM kino_ulaznica k JOIN projekcija p2 ON k.id_projekcija = p2.id_projekcija WHERE p2.id_film = 3 AND k.status_uplate = 1)*(SELECT DISTINCT p2.iznos FROM kino_ulaznica k JOIN projekcija p2 ON k.id_projekcija = p2.id_projekcija WHERE p2.id_film = {film_id} AND k.status_uplate = 1)";

            SqlDataReader dr = DB.Instance.DohvatiDataReader(sqlUpit);
            while (dr.Read())
            {
                UkupnaStatistika statistika = StvoriStatistiku(dr);
                lista.Add(statistika);
            }
            dr.Close();
            return lista;
        }

        public static UkupnaStatistika StvoriStatistiku(SqlDataReader dr)
        {
            UkupnaStatistika statistika = null;
            if (dr != null)
            {
                statistika = new UkupnaStatistika();
                statistika.Iznos = decimal.Parse(dr[0].ToString());
                statistika.broj_sjedala = int.Parse(dr[2].ToString());
                statistika.Ukupna = statistika.Iznos * statistika.broj_sjedala;
            }
            return statistika;
                
        }



        public static List<Kino> DohvatiNaziveKina()
        {
            List<Kino> lista = new List<Kino>();
            string sqlUpit = "SELECT id_kino, naziv FROM kino";
            SqlDataReader dr = DB.Instance.DohvatiDataReader(sqlUpit);
            while (dr.Read())
            {
                Kino kino = new Kino();
                kino.ID = int.Parse(dr["id_kino"].ToString());
                kino.Naziv = dr["naziv"].ToString();
                lista.Add(kino);
            }
            dr.Close();
            return lista;
        }
        public static List<int> DohvatiDvoraneKina(int idKino)
        {
            List<int> lista = new List<int>();
            string sqlUpit = $"SELECT d.id_dvorana FROM kino k JOIN dvorana d ON d.id_kino = k.id_kino WHERE k.id_kino = {idKino}";
            SqlDataReader dr = DB.Instance.DohvatiDataReader(sqlUpit);
            while (dr.Read())
            {
                lista.Add(int.Parse(dr["id_dvorana"].ToString()));
            }
            dr.Close();
            return lista;
        }

        public static List<int> DohvatiPrihodeKina(List<Kino> lista)
        {
            List<int> listaPrihoda = new List<int>();
            foreach(Kino kino in lista)
            {
                int iznos = 0;
                string sqlUpit = $"SELECT projekcija.iznos FROM kino JOIN dvorana ON kino.id_kino=dvorana.id_kino JOIN projekcija ON dvorana.id_dvorana=projekcija.id_dvorana JOIN kino_ulaznica ON projekcija.id_projekcija=kino_ulaznica.id_projekcija WHERE kino.id_kino = {kino.ID} AND status_uplate=1";
                SqlDataReader dr = DB.Instance.DohvatiDataReader(sqlUpit);
                while (dr.Read())
                {
                    iznos += int.Parse(dr[0].ToString());
                }
                listaPrihoda.Add(iznos);
                dr.Close();
            }
            return listaPrihoda;
        } 

        public static List<DateTime> DohvatiDatume()
        {
            List<DateTime> lista = new List<DateTime>();
            string sqlUpit = "SELECT datum FROM projekcija";
            SqlDataReader dr = DB.Instance.DohvatiDataReader(sqlUpit);
            while (dr.Read())
            {
                string datumS = dr["datum"].ToString();
                string[] polje = datumS.Split('.');
                int dan = int.Parse(polje[0]);
                int mjesec = int.Parse(polje[1]);
                int godina = int.Parse(polje[2]);
                DateTime datum = new DateTime(godina,mjesec,dan);
                lista.Add(datum);
            }
            dr.Close();
            return lista;
        }

        public static List<int> DohvatiPrihodeDatuma(List<DateTime> lista)
        {
            List<int> listaPrihoda = new List<int>();
            foreach (DateTime datum in lista)
            {
                int iznos = 0;
                string datumS = "";
                datumS += datum.Year + "-" + datum.Month + "-" + datum.Day;
                string sqlUpit = $"SELECT projekcija.iznos FROM kino JOIN dvorana ON kino.id_kino=dvorana.id_kino JOIN projekcija ON dvorana.id_dvorana=projekcija.id_dvorana JOIN kino_ulaznica ON projekcija.id_projekcija=kino_ulaznica.id_projekcija WHERE projekcija.datum = '{datumS}' AND status_uplate=1";
                SqlDataReader dr = DB.Instance.DohvatiDataReader(sqlUpit);
                while (dr.Read())
                {
                    iznos += int.Parse(dr[0].ToString());
                }
                listaPrihoda.Add(iznos);
                dr.Close();
            }
            return listaPrihoda;
        }

        public static List<int> DohvatiOčekivanePrihodeDatuma(List<DateTime> lista)
        {
            List<int> listaPrihoda = new List<int>();
            foreach (DateTime datum in lista)
            {
                int iznos = 0;
                string datumS = "";
                datumS += datum.Year + "-" + datum.Month + "-" + datum.Day;
                string sqlUpit = $"SELECT projekcija.iznos FROM kino JOIN dvorana ON kino.id_kino=dvorana.id_kino JOIN projekcija ON dvorana.id_dvorana=projekcija.id_dvorana JOIN kino_ulaznica ON projekcija.id_projekcija=kino_ulaznica.id_projekcija WHERE projekcija.datum = '{datumS}' AND status_uplate=0";
                SqlDataReader dr = DB.Instance.DohvatiDataReader(sqlUpit);
                while (dr.Read())
                {
                    iznos += int.Parse(dr[0].ToString());
                }
                listaPrihoda.Add(iznos);
                dr.Close();
            }
            return listaPrihoda;
        }

        public static List<Statistika1> DohvatiPrihodePoKinimaZaFilm(Film f)
        {
            if (f == null) throw new ArgumentNullException("Film nije zadan");

            List<Statistika1> lista = new List<Statistika1>();

            List<Kino> kina = DohvatiNaziveKina();

            
            foreach (Kino kino in kina)
            {

                Statistika1 zapis = new Statistika1 { Kino = kino, ProfitZaFilm = 0, OcekivaniProfit = 0 };



                foreach (int dvoranaId in DohvatiDvoraneKina(kino.ID) )
                {
                  
                    
                    string sqlUpit = $"SELECT COUNT(*) FROM kino_ulaznica k JOIN projekcija p2 ON k.id_projekcija = p2.id_projekcija WHERE p2.id_film = '{f.ID}' AND k.status_uplate = 1";
                    SqlDataReader dr = DB.Instance.DohvatiDataReader(sqlUpit);                                                                                                                     
                    
                    while (dr.Read())
                    {
                        try
                        {                          
                            zapis.ProfitZaFilm = decimal.Parse(dr[0].ToString());
                        }
                        catch (Exception)
                        {
                        }
                    }
                    dr.Close();
                    string sqlUpit3 = $"SELECT DISTINCT p2.iznos FROM kino_ulaznica k JOIN projekcija p2 ON k.id_projekcija = p2.id_projekcija WHERE p2.id_film = '{f.ID}' AND k.status_uplate = 1";
                    SqlDataReader dr3 = DB.Instance.DohvatiDataReader(sqlUpit3);

                    while (dr3.Read())
                    {
                        try
                        {
                            zapis.Profitdrugi = decimal.Parse(dr3[0].ToString());                      
                        }
                        catch (Exception)
                        {
                        }
                    }
                    dr3.Close();

                    
                    string sqlUpit2 = $"SELECT COUNT(*) FROM kino_ulaznica k JOIN projekcija p2 ON k.id_projekcija = p2.id_projekcija WHERE p2.id_film = '{f.ID}' AND k.rezervacija = 1"; 
                    
                    
                    SqlDataReader dr2 = DB.Instance.DohvatiDataReader(sqlUpit2);
                    while (dr2.Read())
                    {
                        try
                        {
                            zapis.OcekivaniProfit = decimal.Parse(dr2[0].ToString());
                        }
                        catch (Exception)
                        {
                        }
                    }
                    dr2.Close();

                    string sqlUpit4 = $"SELECT DISTINCT p2.iznos FROM kino_ulaznica k JOIN projekcija p2 ON k.id_projekcija = p2.id_projekcija WHERE p2.id_film = '{f.ID}' AND k.rezervacija = 1";
                    

                    SqlDataReader dr4 = DB.Instance.DohvatiDataReader(sqlUpit4);
                    while (dr4.Read())
                    {
                        try
                        {
                            zapis.Ocekivanidrugi = decimal.Parse(dr4[0].ToString());
                        }
                        catch (Exception)
                        {
                        }
                    }
                    dr4.Close();
                }

                    

                lista.Add(zapis);
            }

            return lista;

            
        }
    }
}
