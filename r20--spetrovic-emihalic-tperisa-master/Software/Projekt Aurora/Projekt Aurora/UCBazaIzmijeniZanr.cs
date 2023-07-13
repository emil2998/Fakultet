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
    public partial class UCBazaIzmijeniZanr : UserControl
    {
        Zanr zanrNovo = new Zanr();
        public UCBazaIzmijeniZanr(Zanr zanr)
        {
            InitializeComponent();
            zanrNovo = zanr;
            txtNaziv.Text = zanr.Naziv;
        }

        private void btnSpremi_Click_1(object sender, EventArgs e)
        {
            if (ProvjeraKorisnickogUnosa.ProvjeriDodavanjeIzmjenuZanra(txtNaziv) == "")
            {
                zanrNovo.Naziv = txtNaziv.Text;
                ZanrRepozitorij.IzmijeniZanr(zanrNovo);
                this.ParentForm.Close();
            }
            else
            {
                FrmUpozorenje frmUpozorenje = new FrmUpozorenje(ProvjeraKorisnickogUnosa.ProvjeriDodavanjeIzmjenuZanra(txtNaziv));
                frmUpozorenje.ShowDialog();
            }
        }

        private void btnOdustani_Click_1(object sender, EventArgs e)
        {
            this.ParentForm.Close();
        }
    }
}
