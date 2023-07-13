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
    public partial class UCRecenzija : UserControl
    {
        public UCRecenzija(Recenzija recenzija)
        {
            InitializeComponent();
            this.labelImeIPrezime.Text = recenzija.Ime+" "+recenzija.Prezime;
            this.labelOcjena.Text = recenzija.Ocijena.ToString()+"/10";
            this.txtKomentar.Text = recenzija.Komentar;
        }
    }
}
