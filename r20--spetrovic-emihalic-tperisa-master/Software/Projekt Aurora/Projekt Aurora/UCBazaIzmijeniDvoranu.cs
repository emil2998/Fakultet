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
    public partial class UCBazaIzmijeniDvoranu : UserControl
    {
        Dvorana dvoranaNovo = new Dvorana();

       
        public UCBazaIzmijeniDvoranu(Dvorana dvorana)
        {
            
            InitializeComponent();
            dvoranaNovo = dvorana;
            txtNaziv.Text = dvorana.Naziv;
            txtBrojRedova.Text = dvorana.Broj_redova.ToString();
            txtBrojStupaca.Text = dvorana.Broj_stupaca.ToString();
           
            
        }

        private void UCBazaIzmijeniDvoranu_Load(object sender, EventArgs e)
        {
            
            comboBoxKino.DataSource = KinoRepozitorij.DohvatiKina();
           

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
                dvoranaNovo.Naziv = txtNaziv.Text;
                dvoranaNovo.Broj_redova = int.Parse(txtBrojRedova.Text);
                dvoranaNovo.Broj_stupaca = int.Parse(txtBrojStupaca.Text);
                dvoranaNovo.Id_kina = kino.ID;
                DvoranaRepozitorij.IzmijeniDvoranu(dvoranaNovo);
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
    }
}
