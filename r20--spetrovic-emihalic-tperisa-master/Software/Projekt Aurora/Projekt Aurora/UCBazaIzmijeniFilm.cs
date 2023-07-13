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
    public partial class UCBazaIzmijeniFilm : UserControl
    {
        Film filmNovo = new Film();
        public UCBazaIzmijeniFilm(Film film)
        {
            InitializeComponent();
            filmNovo = film;
            txtNaziv.Text = film.Naziv;
            txtGodina.Text = film.Godina.ToString();
            txtRedatelj.Text = film.Redatelj;
            txtOpis.Text = film.Opis;
            string[] trajanjeSati = film.Trajanje.Split('h');
            txtSati.Text = trajanjeSati[0];
            string[] trajanjeMinute = trajanjeSati[1].Split('m');
            txtMinute.Text = trajanjeMinute[0].Substring(1,2);
        }

        private void btnSpremi_Click_1(object sender, EventArgs e)
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
                filmNovo.Naziv = txtNaziv.Text;
                filmNovo.Godina = int.Parse(txtGodina.Text);
                filmNovo.Redatelj = txtRedatelj.Text;
                filmNovo.Opis = txtOpis.Text;
                filmNovo.Trajanje = txtSati.Text + "h:" + txtMinute.Text + "min";
                FilmRepozitorij.IzmijeniFilm(filmNovo);
                this.ParentForm.Close();
            }
            else
            {
                FrmUpozorenje frmUpozorenje = new FrmUpozorenje(ProvjeraKorisnickogUnosa.ProvjeriDodavanjeIzmjenuFilma(lista));
                frmUpozorenje.ShowDialog();
            }
        }

        private void btnOdustani_Click_1(object sender, EventArgs e)
        {
            this.ParentForm.Close();
        }
    }
}
