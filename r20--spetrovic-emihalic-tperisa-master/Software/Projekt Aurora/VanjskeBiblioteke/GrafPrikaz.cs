using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Windows.Forms.DataVisualization.Charting;

namespace VanjskeBiblioteke
{
    public partial class GrafPrikaz : UserControl
    {
        public List<double> XosVrijednosti { get; set; }
        public List<double> YosVrijednosti { get; set; }
        public List<double> YosVrijednosti2 { get; set; }

        public string Naziv { get; set; }
        public string Naziv2 { get; set; }

        public GrafPrikaz()
        {
            InitializeComponent();
        }

        public void Inicijaliziraj()
        {

            if (XosVrijednosti.Count != YosVrijednosti.Count)
            {
                throw new DataException("Broj podataka za vrijednosti x i y osi nije jednak!");
            }

            chrtGraf.Series.Clear();

            chrtGraf.Series.Add(Naziv);

            for (int i = 0; i < XosVrijednosti.Count; i++)
            {
                DataPoint dp = new DataPoint();
                dp.SetValueY(YosVrijednosti[i], YosVrijednosti2[i]);
                
                dp.XValue = XosVrijednosti[i];
                chrtGraf.Series[0].Points.Add(dp);
            }

          
                chrtGraf.Series.Add(Naziv2);

                for (int i = 0; i < XosVrijednosti.Count; i++)
                {
                    DataPoint dp = new DataPoint();
                    dp.SetValueY(YosVrijednosti2[i]);

                    dp.XValue = XosVrijednosti[i];
                    chrtGraf.Series[1].Points.Add(dp);
                }
         
        }
    }
}
