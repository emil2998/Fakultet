using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Drawing.Text;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.IO;
using DinamickoGeneriranjeSjedala;

namespace Projekt_Aurora
{
    public partial class FrmRezerviranje : Form
    {
        static string path = Path.Combine(Directory.GetParent(System.IO.Directory.GetCurrentDirectory()).Parent.Parent.FullName);
        string path2 = path + "\\Aurora.chm";

        Raspored raspored1 = new Raspored();

        public FrmRezerviranje(Raspored raspored)
        {
            InitializeComponent();
            raspored1 = raspored;
            helpProvider1.HelpNamespace = path2;
            

        }

      
      

       /*
        private void Generiraj(int redovi, int stupci, List<int> lista)
        {
            int brojac = 0;
            int brojRedova = redovi;
            int brojStupaca = stupci;
            int ukupnoMjesta = brojRedova * brojStupaca;

            Point pocetnaLokacija = new Point(20, 20);


            //pocetne vrijednosti
            int trenutniStupac = 0;
            int trenutniRed = 0;

            //petlja generiranja
            for (int i = 0; i < ukupnoMjesta; i++)
            {
                //postavke
                CheckBox checkBox = new CheckBox();
                checkBox.CheckAlign = ContentAlignment.MiddleCenter;
                checkBox.TextAlign = ContentAlignment.TopCenter;
                checkBox.ForeColor = Color.WhiteSmoke;
                checkBox.Text = (i+1).ToString();
                checkBox.Height = 40;
                checkBox.Width = 40;               
                checkBox.Name = (i + 1).ToString();               
                checkBox.CheckedChanged += Klik;


                foreach (int brojSjedala in lista)
                {
                    if (checkBox.Name.Equals(brojSjedala.ToString()))
                    {
                        checkBox.Checked = true;
                        checkBox.Enabled = false;
                        checkBox.BackColor = Color.Red;

                    }
                }
                

                //


                //novaLokacija je relativna pozicija koja se mjenja ovisno o trenutnoj poziciji koja se generira
                Point novaLokacija = pocetnaLokacija;

                //prelazak u novi red
                if (i % brojStupaca == 0 && i != 0)
                {
                    trenutniRed++;
                    trenutniStupac = 1;

                    //računanje offseta
                    novaLokacija.Y += 50 * trenutniRed;
                    novaLokacija.X += 50 * trenutniStupac;
                    brojac++;
                }


                //nastavak u istom redu
                else
                {
                    trenutniStupac++;

                    //računanje offseta
                    novaLokacija.X += 50 * trenutniStupac;
                    novaLokacija.Y += 50 * trenutniRed;
                    brojac++;
                }
                checkBox.Location = novaLokacija;
                panel.Controls.Add(checkBox);

            }
           

        }
       */
        private void button1_Click(object sender, EventArgs e)
        {
            int brojacSjedala = 0;
            List<KinoUlaznica> lista = new List<KinoUlaznica>();
            if (radioBtnRezerviraj.Checked == true)
            {
                foreach (CheckBox control in panel.Controls)
                {
                    if (control.Checked == true && control.Enabled == true)
                    {
                        KinoUlaznica kinoUlaznica = new KinoUlaznica();
                        kinoUlaznica.IDProjekcije = raspored1.IDprojekcije;
                        kinoUlaznica.IDKorisnik = UlogiraniKorisnik.Id_korisnik;
                        kinoUlaznica.StatusUplate = 0;
                        kinoUlaznica.StatusRezervacija = 1;
                        kinoUlaznica.VrijemeIzdavanja = DateTime.Now.ToString("MM / dd / yyyy HH: mm:ss");
                        kinoUlaznica.BrojSjedala = int.Parse(control.Name);
                        KinoUlazniceRepozitorij.SpremiRezervaciju(kinoUlaznica);
                        brojacSjedala++;
                    }
                }

                if (brojacSjedala==0)
                {
                    FrmUpozorenje frmUpozorenje = new FrmUpozorenje("Niste odabrali sjedala!");
                    frmUpozorenje.Text = "Pogreska";
                    frmUpozorenje.ShowDialog();
                }
                else {
                    FrmUpozorenje frmUpozorenje2 = new FrmUpozorenje("Uspješno ste rezervirali sjedala!");
                    frmUpozorenje2.Text = "Potvrda rezervacije";
                    frmUpozorenje2.ShowDialog();
                    this.Close();
                }
               
            }

            if (radioBtnKupi.Checked == true)
            {            
                int cijena = int.Parse(raspored1.Iznos.ToString());
                int brojac = 0;

                
                
                foreach (CheckBox control in panel.Controls)
                {
                    if (control.Checked == true && control.Enabled == true)
                    {
                        brojac++;
                    }
                }
                if (brojac == 0)
                {
                    FrmUpozorenje frmUpozorenje = new FrmUpozorenje("Niste odabrali sjedala!");
                    frmUpozorenje.Text = "Pogreska";
                    frmUpozorenje.ShowDialog();
                }
                else {
                    foreach (CheckBox control in panel.Controls)
                    {
                        if (control.Checked == true && control.Enabled == true)
                        {
                            KinoUlaznica kinoUlaznica = new KinoUlaznica();
                            kinoUlaznica.IDProjekcije = raspored1.IDprojekcije;
                            kinoUlaznica.IDKorisnik = UlogiraniKorisnik.Id_korisnik;
                            kinoUlaznica.StatusUplate = 1;
                            kinoUlaznica.StatusRezervacija = 0;
                            kinoUlaznica.VrijemeIzdavanja = DateTime.Now.ToString("MM / dd / yyyy HH: mm:ss");
                            kinoUlaznica.BrojSjedala = int.Parse(control.Name);
                            lista.Add(kinoUlaznica);
                        }
                    }

                    FrmPlacanje frmPlacanje = new FrmPlacanje(cijena, brojac, lista);
                    frmPlacanje.Text = "Placanje";
                    frmPlacanje.FormClosed += frmPlacanje_FormClosed;
                    frmPlacanje.Show();
                }     
                
                         
             }
        }

        private void frmPlacanje_FormClosed(object sender, System.Windows.Forms.FormClosedEventArgs e)
        {          
            this.Close();
        }
        /*
        private void Klik(object sender, EventArgs e) 
        {

            CheckBox checkBox = (CheckBox)sender;
            if (checkBox.BackColor == Color.Green)
            {
                checkBox.BackColor = Color.Black;

            }
            else { checkBox.BackColor = Color.Green; }
            
        
        }
        */
        private void panel_Paint(object sender, PaintEventArgs e)
        {

        }

        private void FrmRezerviranje_Load(object sender, EventArgs e)
        {
            panel.Controls.Clear();
            Dvorana dvorana = DvoranaRepozitorij.DohvatiDvoranuRezervacija(raspored1);
            List<int> lista = KinoUlazniceRepozitorij.DohvatiSjedala(raspored1);
            
            List<CheckBox> povratnaLista = DinamickoGeneriranjeSjedala.Generiranje.Generiraj(dvorana.Broj_redova, dvorana.Broj_stupaca,lista);
            foreach(CheckBox checkbox in povratnaLista)
            {
                panel.Controls.Add(checkbox);
            }
            btnPlatno.Location = new Point(80, 12);
            btnPlatno.Width = dvorana.Broj_stupaca * 50;
            btnRezervacije.Location = new Point((dvorana.Broj_stupaca * 50 + 100), (dvorana.Broj_redova *50+100));
            radioBtnKupi.Location = new Point((dvorana.Broj_stupaca * 50 + 100), (dvorana.Broj_redova * 50 + 80));
            radioBtnRezerviraj.Location = new Point((dvorana.Broj_stupaca * 50 + 100), (dvorana.Broj_redova * 50 + 50));
            btnOdustani.Location = new Point((dvorana.Broj_stupaca * 50 + 300), (dvorana.Broj_redova * 50 + 100));


        }

        private void btnOdustani_Click(object sender, EventArgs e)
        {
            this.Close();
        }
    }
}
