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
    public partial class UCBazaIzmijeniGlumca : UserControl
    {
        Glumac noviGlumac = new Glumac();
        public UCBazaIzmijeniGlumca(Glumac glumac)
        {
            InitializeComponent();
            noviGlumac = glumac;
            txtIme.Text = glumac.Ime;
            txtPrezime.Text = glumac.Prezime;
        }

        private void btnSpremi_Click_1(object sender, EventArgs e)
        {
            List<TextBox> lista = new List<TextBox>();
            lista.Add(txtIme);
            lista.Add(txtPrezime);
            if (ProvjeraKorisnickogUnosa.ProvjeriDodavanjeIzmjenuGlumca(lista) == "")
            {
                noviGlumac.Ime = txtIme.Text;
                noviGlumac.Prezime = txtPrezime.Text;
                GlumacRepozitorij.IzmijeniGlumca(noviGlumac);
                this.ParentForm.Close();
            }
            else
            {
                FrmUpozorenje frmUpozorenje = new FrmUpozorenje(ProvjeraKorisnickogUnosa.ProvjeriDodavanjeIzmjenuGlumca(lista));
                frmUpozorenje.ShowDialog();
            }
        }

        private void btnOdustani_Click_1(object sender, EventArgs e)
        {
            this.ParentForm.Close();
        }
    }
}
