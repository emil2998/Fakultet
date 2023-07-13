using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Windows.Forms.VisualStyles;
using System.IO;

namespace Projekt_Aurora
{

    public partial class FrmPlacanje : Form
    {

        static string path = Path.Combine(Directory.GetParent(System.IO.Directory.GetCurrentDirectory()).Parent.Parent.FullName);
        string path2 = path + "\\Aurora.chm";


        int ukupniBrojSjedala = new int();
        int cijenaKarte = new int();
        int ukupniIznos = new int();
        List<KinoUlaznica> listaUlaznica = new List<KinoUlaznica>();

       
       
        public FrmPlacanje(int cijena, int brojac , List<KinoUlaznica> lista)
        {
            InitializeComponent();
            cijenaKarte = cijena;
            ukupniBrojSjedala = brojac;
            listaUlaznica = lista;
            helpProvider1.HelpNamespace = path2;

            
            
        }
        

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void FrmPlacanje_Load(object sender, EventArgs e)
        {   
           
            int idKorisnika2 = UlogiraniKorisnik.Id_korisnik;
            
            txtStanjeRacuna.Text = KorisnikRepozitorij.DohvatiStanjeRacuna(idKorisnika2).ToString();
            txtUkupniBrojSjedala.Text = ukupniBrojSjedala.ToString();
            txtCijena.Text = cijenaKarte.ToString();
            ukupniIznos = ukupniBrojSjedala * cijenaKarte;
            txtUkupniIznos.Text = ukupniIznos.ToString();
            panel1.Hide();
            panel2.Hide();
            btnIzvrsiTransakciju.Hide();
        }

        private void radioButtonNovcanik_CheckedChanged(object sender, EventArgs e)
        {
            if (radioButtonNovcanik.Checked == true)
            {
                panel1.Hide();
                panel2.Hide();
                btnIzvrsiTransakciju.Show();
            }

        }

        private void radioButtonOnline_CheckedChanged(object sender, EventArgs e)
        {
            if (radioButtonOnline.Checked == true)
            {
                panel1.Hide();
                panel2.Show();
                button4.BackColor = Color.Red;
                button3.BackColor = Color.Red;
                btnIzvrsiTransakciju.Show();
                txtBrojRacunaOnline.Text = "";
                txtImePrezimeOnline.Text = "";
                txtMjesecOnline.Text = "";
                txtGodinaOnline.Text = "";
                txtCCVOnline.Text = "";
                txtIznosZaPlatiti.Text = ukupniIznos.ToString(); 
            }

        }

        private void button4_Click(object sender, EventArgs e)
        {

            button4.BackColor = Color.Green;
            button3.BackColor = Color.Red;

        }

        private void button3_Click(object sender, EventArgs e)
        {
            button4.BackColor = Color.Red;
            button3.BackColor = Color.Green;
        }

        private void button6_Click(object sender, EventArgs e)
        {
            button6.BackColor = Color.Green;
            button7.BackColor = Color.Red;
        }

        private void button7_Click(object sender, EventArgs e)
        {
            button6.BackColor = Color.Red;
            button7.BackColor = Color.Green;
        }

        private void btnNadoplatiRacun_Click(object sender, EventArgs e)
        {
            panel1.Show();
            panel2.Hide();
            button6.BackColor = Color.Red;
            button7.BackColor = Color.Red;
            radioButtonNovcanik.Checked = false;
            radioButtonOnline.Checked = false;
            btnIzvrsiTransakciju.Hide();

            txtBrojRacunaNovcanik.Text = "";
            txtImePrezimeNovcanik.Text = "";
            txtMjesecNovcanik.Text = "";
            txtGodinaNovcanik.Text = "";
            txtCCVNovcanik.Text = "";
            txtNadoplataRacuna.Text = "";
            


        }

        private void button1_Click(object sender, EventArgs e)
        {
            panel1.Hide();
        }
     

        private void button8_Click(object sender, EventArgs e)
        {
            panel2.Hide();
            radioButtonOnline.Checked = false;
            btnIzvrsiTransakciju.Hide();
        }

        private void btnIzvrsiTransakcije_Click(object sender, EventArgs e)
        {
            if (radioButtonNovcanik.Checked == true)
            {
                if ((int.Parse(txtStanjeRacuna.Text)) >= ukupniIznos)
                {
                    int idKorisnik = UlogiraniKorisnik.Id_korisnik;
                    int noviIznos = (int.Parse(txtStanjeRacuna.Text)) - ukupniIznos;
                    KorisnikRepozitorij.SpremiStanjeRacuna(noviIznos, idKorisnik);
                    foreach (KinoUlaznica item in listaUlaznica)
                    {
                        KinoUlazniceRepozitorij.SpremiRezervaciju(item);
                    }
                    FrmUpozorenje frmUpozorenje = new FrmUpozorenje("Kupnja uspjesna!");
                    frmUpozorenje.Text = "Obavijest";
                    frmUpozorenje.ShowDialog();
                    this.Close();
                }
                else
                {
                    int idKorisnika2 = UlogiraniKorisnik.Id_korisnik;
                    txtStanjeRacuna.Text = KorisnikRepozitorij.DohvatiStanjeRacuna(idKorisnika2).ToString();
                    if ((int.Parse(txtStanjeRacuna.Text)) < ukupniIznos)
                    {

                        FrmUpozorenje frmUpozorenje = new FrmUpozorenje("Nedovoljno novaca na racunu!");
                        frmUpozorenje.Text = "Pogreska";
                        frmUpozorenje.ShowDialog();

                    }
                }

            }
            if (radioButtonOnline.Checked == true)
            {
                if (txtIznosZaPlatiti.Text=="" || txtBrojRacunaOnline.Text == "" || txtImePrezimeOnline.Text == "" || txtMjesecOnline.Text == "" || txtGodinaOnline.Text == "" || txtCCVOnline.Text == "")
                {
                    FrmUpozorenje frmUpozorenje = new FrmUpozorenje("Popunite sva polja!");
                    frmUpozorenje.Text = "Pogreska";
                    frmUpozorenje.ShowDialog();
                }

                else if ((int.Parse(txtIznosZaPlatiti.Text)) == ukupniIznos)
                {
                    foreach (KinoUlaznica item in listaUlaznica)
                    {
                        KinoUlazniceRepozitorij.SpremiRezervaciju(item);

                    }
                    FrmUpozorenje frmUpozorenje = new FrmUpozorenje("Kupnja uspjesna!");
                    frmUpozorenje.Text = "Obavijest";
                    frmUpozorenje.ShowDialog();
                    this.Close();
                }
                else {
                    FrmUpozorenje frmUpozorenje = new FrmUpozorenje("Iznos za placanje nije tocan za kupnju karti!");
                    frmUpozorenje.Text = "Pogreska";
                    frmUpozorenje.ShowDialog();
                }
                

            }
        }
    

        private void btnNadoplatiIznosNovcanik_Click(object sender, EventArgs e)
        {
            if (txtNadoplataRacuna.Text == "" || txtBrojRacunaNovcanik.Text=="" || txtImePrezimeNovcanik.Text == "" || txtMjesecNovcanik.Text == "" || txtGodinaNovcanik.Text == "" || txtCCVNovcanik.Text == "")
            {
                FrmUpozorenje frmUpozorenje = new FrmUpozorenje("Popunite sva polja!");
                frmUpozorenje.Text = "Pogreska";
                frmUpozorenje.ShowDialog();
            }

            else
            {
                List<TextBox> lista = new List<TextBox>();
                lista.Add(txtNadoplataRacuna);
                
                if (ProvjeraKorisnickogUnosa.ProvjeriPlacanje(lista) == "") {
                    int idKorisnik = UlogiraniKorisnik.Id_korisnik;
                    int noviIznos = (int.Parse(txtStanjeRacuna.Text)) + (int.Parse(txtNadoplataRacuna.Text));
                    KorisnikRepozitorij.SpremiStanjeRacuna(noviIznos, idKorisnik);
                    FrmUpozorenje frmUpozorenje = new FrmUpozorenje("Nadoplata dodana u novcanik");
                    frmUpozorenje.Text = "Obavijest";
                    frmUpozorenje.ShowDialog();
                    int idKorisnika2 = UlogiraniKorisnik.Id_korisnik;
                    txtStanjeRacuna.Text = KorisnikRepozitorij.DohvatiStanjeRacuna(idKorisnika2).ToString();
                    panel1.Hide();
                }
                else
                {
                    FrmUpozorenje frmUpozorenje = new FrmUpozorenje(ProvjeraKorisnickogUnosa.ProvjeriPlacanje(lista));
                    frmUpozorenje.ShowDialog();
                }

            }

        }

        private void btnPovratakProjekcije_Click(object sender, EventArgs e)
        {
            this.Close();
        }
    }
}
