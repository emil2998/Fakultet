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
    public partial class UCBazaDodajFilm : UserControl
    {
        public UCBazaDodajFilm()
        {
            InitializeComponent();
        }

        private void btnOdustani_Click(object sender, EventArgs e)
        {
            this.ParentForm.Close();
        }

        private void btnSpremi_Click(object sender, EventArgs e)
        {
            TextBox textbox = new TextBox();
            textbox.Name = "txtOpis2";
            textbox.Text = txtOpis.Text;
            List<TextBox> lista = new List<TextBox>();
            lista.Add(txtNaziv);
            lista.Add(txtGodina);
            lista.Add(txtRedatelj);
            lista.Add(textbox);
            lista.Add(txtSati);
            lista.Add(txtMinute);
            if (ProvjeraKorisnickogUnosa.ProvjeriDodavanjeIzmjenuFilma(lista) == "")
            {
                Film film = new Film();
                film.Naziv = txtNaziv.Text;
                film.Godina = int.Parse(txtGodina.Text);
                film.Redatelj = txtRedatelj.Text;
                film.Opis = txtOpis.Text;
                film.Trajanje = txtSati.Text + "h:" + txtMinute.Text + "min";
                FilmRepozitorij.DodajFilm(film);
                this.ParentForm.Close();
            }
            else
            {
                FrmUpozorenje frmUpozorenje = new FrmUpozorenje(ProvjeraKorisnickogUnosa.ProvjeriDodavanjeIzmjenuFilma(lista));
                frmUpozorenje.ShowDialog();
            }
        }

        private void UCBazaDodajFilm_Load(object sender, EventArgs e)
        {

        }
    }
}
