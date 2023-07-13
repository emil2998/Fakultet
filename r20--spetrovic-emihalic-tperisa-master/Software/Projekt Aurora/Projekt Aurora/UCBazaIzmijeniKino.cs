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
    public partial class UCBazaIzmijeniKino : UserControl
    {
        Kino kinoNovo = new Kino();
        public UCBazaIzmijeniKino(Kino kino)
        {
            InitializeComponent();
            kinoNovo = kino;
            txtNaziv.Text = kino.Naziv;
            txtAdresa.Text = kino.Adresa;
            txtID.Text = kino.ID.ToString();
        }

        private void btnSpremi_Click(object sender, EventArgs e)
        {
            List<TextBox> lista = new List<TextBox>();
            lista.Add(txtNaziv);
            lista.Add(txtAdresa);
            if (ProvjeraKorisnickogUnosa.ProvjeriDodavanjeIzmjenuKina(lista) == "")
            {
                kinoNovo.Naziv = txtNaziv.Text;
            kinoNovo.Adresa = txtAdresa.Text;
            KinoRepozitorij.IzmijeniKino(kinoNovo);
            this.ParentForm.Close();
            }
            else
            {
                FrmUpozorenje frmUpozorenje = new FrmUpozorenje(ProvjeraKorisnickogUnosa.ProvjeriDodavanjeIzmjenuKina(lista));
                frmUpozorenje.ShowDialog();
            }
        }

        private void btnOdustani_Click(object sender, EventArgs e)
        {
            this.ParentForm.Close();
        }
    }
}
