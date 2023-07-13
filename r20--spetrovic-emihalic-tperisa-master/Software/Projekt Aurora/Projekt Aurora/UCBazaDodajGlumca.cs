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
    public partial class UCBazaDodajGlumca : UserControl
    {
        public UCBazaDodajGlumca()
        {
            InitializeComponent();
        }

        private void btnOdustani_Click(object sender, EventArgs e)
        {
            this.ParentForm.Close();
        }

        private void btnSpremi_Click(object sender, EventArgs e)
        {
            List<TextBox> lista = new List<TextBox>();
            lista.Add(txtIme);
            lista.Add(txtPrezime);
            if (ProvjeraKorisnickogUnosa.ProvjeriDodavanjeIzmjenuGlumca(lista) == "")
            {
                Glumac glumac = new Glumac();
                glumac.Ime = txtIme.Text;
                glumac.Prezime = txtPrezime.Text;
                GlumacRepozitorij.DodajGlumca(glumac);
                this.ParentForm.Close();
            }
            else
            {
                FrmUpozorenje frmUpozorenje = new FrmUpozorenje(ProvjeraKorisnickogUnosa.ProvjeriDodavanjeIzmjenuGlumca(lista));
                frmUpozorenje.ShowDialog();
            }
        }
    }
}
