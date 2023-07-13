using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Projekt_Aurora
{
    public partial class UCStatistika : UserControl
    {
        public UCStatistika()
        {
            InitializeComponent();
        }


        private void cmbOdabirFilma_SelectedIndexChanged(object sender, EventArgs e)
        {
            Film film = cmbOdabirFilma.SelectedItem as Film;
            if (film != null)
            {
                List<Statistika1> prihodi = StatistikaRepozitorij.DohvatiPrihodePoKinimaZaFilm(film);  // GRAF

                List<double> xOs = new List<double>();
                List<double> yOs = new List<double>();
                List<double> yOs2 = new List<double>();

                
                int y1 = 0;
                int y2 = 0;
                foreach(var zapis in prihodi)
               {
                    xOs.Add(zapis.Kino.ID);              
                    yOs.Add((double)(zapis.ProfitZaFilm*zapis.Profitdrugi));
                    y1 = int.Parse(zapis.ProfitZaFilm.ToString()) * int.Parse(zapis.Profitdrugi.ToString());
                    yOs2.Add((double)(zapis.OcekivaniProfit*zapis.Ocekivanidrugi));
                    y2 = int.Parse(zapis.OcekivaniProfit.ToString()) * int.Parse(zapis.Ocekivanidrugi.ToString());
                }
               
                grafPrikaz1.XosVrijednosti = xOs;
                grafPrikaz1.YosVrijednosti = yOs;
                grafPrikaz1.YosVrijednosti2 = yOs2;

                grafPrikaz1.Naziv = "Stvarni prihodi";
                grafPrikaz1.Naziv2 = "Očekivani prihodi";

                grafPrikaz1.Inicijaliziraj();

                lblUkupniPrihodKupljeneKarte.Text = $"{y1} kn";
                lblUkupniPrihodRezervacija.Text = $"{y2} kn";
            }




        }

        private void UCStatistika_Load(object sender, EventArgs e)
        {
            cmbOdabirFilma.DataSource = FilmRepozitorij.DohvatiFilmove();

        }
    }
}
