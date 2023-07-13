using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Globalization;
namespace Projekt_Aurora
{
    public partial class UCBazaIzmijeniProjekciju : UserControl
    {
        Projekcija projekcijaNovo = new Projekcija();

        public UCBazaIzmijeniProjekciju(Projekcija projekcija)
        {
            InitializeComponent();
            projekcijaNovo = projekcija;         
            dateTimePicker1.Text = projekcija.Datum;        
            dateTimePicker2.Text = projekcija.Vrijeme;           
            txtIznos.Text = projekcija.Iznos.ToString();
          
        }

        private void btnSpremi_Click(object sender, EventArgs e)
        {
            
            List<TextBox> lista = new List<TextBox>();
            lista.Add(txtIznos);
            string sati = dateTimePicker2.Value.ToString("HH:mm:ss");
            string datum = dateTimePicker1.Value.Date.ToString("MM / dd / yyyy");

            Dvorana dvorana2 = comboBoxDvorana.SelectedItem as Dvorana;


            if (ProvjeraKorisnickogUnosa.ProvjeriDodavanjeIzmjenuProjekcije(lista,sati,datum,dvorana2) == "")
            {
                Dvorana dvorana = comboBoxDvorana.SelectedItem as Dvorana;
                Film film = comboBoxFilm.SelectedItem as Film;
                projekcijaNovo.Vrijeme = dateTimePicker2.Value.ToString("HH:mm:ss");
                projekcijaNovo.Iznos = int.Parse(txtIznos.Text);
                projekcijaNovo.Id_dvorana = dvorana.ID;
                projekcijaNovo.Id_film = film.ID;
                projekcijaNovo.Datum = dateTimePicker1.Value.Date.ToString("MM / dd / yyyy");
                ProjekcijaRepozitorij.IzmijeniProjekciju(projekcijaNovo);
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

        private void UCBazaIzmijeniProjekciju_Load(object sender, EventArgs e)
        {
            dateTimePicker2.Format = DateTimePickerFormat.Time;
            dateTimePicker2.ShowUpDown = true;
            comboBoxFilm.DataSource = FilmRepozitorij.DohvatiFilmove();
            comboBoxDvorana.DataSource = DvoranaRepozitorij.DohvatiDvorane();
            
        }
    }
}
