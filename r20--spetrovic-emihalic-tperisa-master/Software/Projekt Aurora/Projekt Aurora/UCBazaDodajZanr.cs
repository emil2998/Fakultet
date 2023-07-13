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
    public partial class UCBazaDodajZanr : UserControl
    {
        public UCBazaDodajZanr()
        {
            InitializeComponent();
        }

        private void btnOdustani_Click(object sender, EventArgs e)
        {
            this.ParentForm.Close();
        }

        private void btnSpremi_Click(object sender, EventArgs e)
        {
            if (ProvjeraKorisnickogUnosa.ProvjeriDodavanjeIzmjenuZanra(txtNaziv) == "")
            {
                Zanr zanr = new Zanr();
                zanr.Naziv = txtNaziv.Text;
                ZanrRepozitorij.DodajZanr(zanr);
                this.ParentForm.Close();
            }
            else
            {
                FrmUpozorenje frmUpozorenje = new FrmUpozorenje(ProvjeraKorisnickogUnosa.ProvjeriDodavanjeIzmjenuZanra(txtNaziv));
                frmUpozorenje.ShowDialog();
            }
        }

        private void UCBazaDodajZanr_Load(object sender, EventArgs e)
        {

        }
    }
}
