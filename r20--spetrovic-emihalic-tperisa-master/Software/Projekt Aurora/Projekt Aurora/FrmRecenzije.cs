using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.IO;

namespace Projekt_Aurora
{
    public partial class FrmRecenzije : Form
    {
        static string path = Path.Combine(Directory.GetParent(System.IO.Directory.GetCurrentDirectory()).Parent.Parent.FullName);
        string path2 = path + "\\Aurora.chm";
        
        private List<Recenzija> recenzije = null;

        public FrmRecenzije(List<Recenzija> listaRecenzija, Film film )
        {
            
            InitializeComponent();
            helpProvider.HelpNamespace = path2;
            this.Focus();
            recenzije = new List<Recenzija>();
            recenzije = listaRecenzija;
            foreach (Recenzija recenzija in recenzije)
            {
                UCRecenzija ucRecenzija = new UCRecenzija(recenzija);
                panelRecenzije.Controls.Add(ucRecenzija);
            }
            panelRecenzije.VerticalScroll.Enabled = true;
            labelNazivFilma.Text = film.Naziv;
        }

        private void btnNazad_Click(object sender, EventArgs e)
        {
            this.Close();
        }
    }
}
