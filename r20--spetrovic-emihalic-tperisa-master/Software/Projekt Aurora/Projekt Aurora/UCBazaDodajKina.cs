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
    public partial class UCBazaDodajKina : UserControl
    {
        public UCBazaDodajKina()
        {
            InitializeComponent();
        }

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void UCBazaDodajKina_Load(object sender, EventArgs e)
        {

        }

        private void btnOdustani_Click(object sender, EventArgs e)
        {
            this.ParentForm.Close();
        }

        private void btnSpremi_Click(object sender, EventArgs e)
        {
            List<TextBox> lista = new List<TextBox>();
            lista.Add(txtNaziv);
            lista.Add(txtAdresa);
            if (ProvjeraKorisnickogUnosa.ProvjeriDodavanjeIzmjenuKina(lista) == "")
            {
                Kino kino = new Kino();
            kino.Naziv = txtNaziv.Text;
            kino.Adresa = txtAdresa.Text;
            KinoRepozitorij.Spremi(kino);
            this.ParentForm.Close();
           
            }
            else
            {
                FrmUpozorenje frmUpozorenje = new FrmUpozorenje(ProvjeraKorisnickogUnosa.ProvjeriDodavanjeIzmjenuKina(lista));
                frmUpozorenje.ShowDialog();
            }
        }
    }
}
