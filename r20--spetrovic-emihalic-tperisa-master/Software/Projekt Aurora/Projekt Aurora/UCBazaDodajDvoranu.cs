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
    public partial class UCBazaDodajDvoranu : UserControl
    {
        public UCBazaDodajDvoranu()
        {
            InitializeComponent();
            
        }

        private void btnSpremi_Click(object sender, EventArgs e)
        {
            Kino kino2 = comboBoxKino.SelectedItem as Kino;
            List<TextBox> lista = new List<TextBox>();
            lista.Add(txtNaziv);
            lista.Add(txtBrojRedova);
            lista.Add(txtBrojStupaca);
            
            
            
            if (ProvjeraKorisnickogUnosa.ProvjeriDodavanjeIzmjenuDvorane(lista,kino2.ID) == "")
            {
                Kino kino = comboBoxKino.SelectedItem as Kino;
                Dvorana dvorana = new Dvorana();
                dvorana.Naziv = txtNaziv.Text;
                dvorana.Broj_redova = int.Parse(txtBrojRedova.Text);
                dvorana.Broj_stupaca = int.Parse(txtBrojStupaca.Text);
                dvorana.Id_kina = kino.ID;
                DvoranaRepozitorij.Spremi(dvorana);
                this.ParentForm.Close();
            }
            else
            {
                FrmUpozorenje frmUpozorenje = new FrmUpozorenje(ProvjeraKorisnickogUnosa.ProvjeriDodavanjeIzmjenuDvorane(lista,kino2.ID));
                frmUpozorenje.ShowDialog();
            }

        }

        private void btnOdustani_Click(object sender, EventArgs e)
        {
            this.ParentForm.Close();
        }

        private void UCBazaDodajDvoranu_Load(object sender, EventArgs e)
        {
            comboBoxKino.DataSource = KinoRepozitorij.DohvatiKina();

        }

        private void comboBoxKino_SelectedIndexChanged(object sender, EventArgs e)
        {

        }

        private void comboBoxKino_ControlAdded(object sender, ControlEventArgs e)
        {
           
        }
    }
}
