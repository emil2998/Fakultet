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
    public partial class UCBazaDodajProjekciju : UserControl
    {
        public UCBazaDodajProjekciju()
        {
            InitializeComponent();
        }

        private void btnSpremi_Click(object sender, EventArgs e)
        {  string sati;
            string datum;
            List<TextBox> lista = new List<TextBox>();          
            lista.Add(txtIznos);
            sati = dateTimePicker2.Value.ToString("HH:mm:ss");
            datum=dateTimePicker1.Value.Date.ToString("MM / dd / yyyy");
            Dvorana dvorana2 = comboBoxDvorana.SelectedItem as Dvorana;

            if (ProvjeraKorisnickogUnosa.ProvjeriDodavanjeIzmjenuProjekcije(lista,sati,datum,dvorana2) == "")
            {
                Film film = comboBoxFilm.SelectedItem as Film;
                Dvorana dvorana = comboBoxDvorana.SelectedItem as Dvorana;
                Projekcija projekcija = new Projekcija();
                projekcija.Vrijeme = dateTimePicker2.Value.ToString("HH:mm:ss");
                projekcija.Iznos = int.Parse(txtIznos.Text);
                projekcija.Id_film = film.ID;
                projekcija.Id_dvorana = dvorana.ID;
                projekcija.Datum = dateTimePicker1.Value.Date.ToString("MM / dd / yyyy");
                ProjekcijaRepozitorij.Spremi(projekcija);
                this.ParentForm.Close();
            }
            else
            {
                 FrmUpozorenje frmUpozorenje = new FrmUpozorenje(ProvjeraKorisnickogUnosa.ProvjeriDodavanjeIzmjenuProjekcije(lista,sati,datum,dvorana2));
                 frmUpozorenje.ShowDialog();
            }
            
        }

        private void btnOdustani_Click(object sender, EventArgs e)
        {
            this.ParentForm.Close();
        }

        private void UCBazaDodajProjekciju_Load(object sender, EventArgs e)
        {
            
            dateTimePicker2.Format = DateTimePickerFormat.Time;
            dateTimePicker2.ShowUpDown = true;
            comboBoxDvorana.DataSource = DvoranaRepozitorij.DohvatiDvorane();
            comboBoxFilm.DataSource = FilmRepozitorij.DohvatiFilmove();
        }

        private void dateTimePicker1_ValueChanged(object sender, EventArgs e)
        {

        }

        private void dateTimePicker2_ValueChanged(object sender, EventArgs e)
        {

        }
    }
}
