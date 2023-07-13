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
    public partial class UCKorisničkiProfil : UserControl
    {
        public UCKorisničkiProfil()
        {
            InitializeComponent();
        }

        private void OsvježiProfil()
        {
            txtKorisnickoIme.Text = UlogiraniKorisnik.Korisnicko_ime;
            txtEmail.Text = UlogiraniKorisnik.Email;
            txtAdresa.Text = UlogiraniKorisnik.Adresa;
            txtGrad.Text = UlogiraniKorisnik.Grad;
            txtDatumRođenja.Text = UlogiraniKorisnik.Datum_rođenja;
            txtKontakt.Text = UlogiraniKorisnik.Kontakt;
            lblIme.Text = UlogiraniKorisnik.Ime;
            lblPrezime.Text = UlogiraniKorisnik.Prezime;           
            int idKorisnika2 = UlogiraniKorisnik.Id_korisnik;
            lblStanjeRacuna.Text = KorisnikRepozitorij.DohvatiStanjeRacuna(idKorisnika2).ToString()+" kn";

        }



        private void btnAzurirajInformacije_Click(object sender, EventArgs e)
        {
            bool sveOk = true;

            if (txtKorisnickoIme.Text.Length < 1) sveOk = false;
            FrmUpozorenje frmUpozorenje = new FrmUpozorenje("Nisu unešeni svi podaci točno!");

            if (!sveOk) {              
                frmUpozorenje.ShowDialog();
                return;
            }

            Korisnik korisnik = new Korisnik();
            korisnik.Korisnicko_ime = txtKorisnickoIme.Text;
            korisnik.Email = txtEmail.Text;
            korisnik.Adresa = txtAdresa.Text;
            korisnik.Grad = txtGrad.Text;
            korisnik.Datum_rođenja = txtDatumRođenja.Text;
            korisnik.Kontakt = txtKontakt.Text;
            

            korisnik.Id_korisnik = UlogiraniKorisnik.Id_korisnik;


            UlogiraniKorisnik.Korisnicko_ime = txtKorisnickoIme.Text;
            UlogiraniKorisnik.Email = txtEmail.Text;
            UlogiraniKorisnik.Adresa = txtAdresa.Text;
            UlogiraniKorisnik.Grad = txtGrad.Text;
            UlogiraniKorisnik.Datum_rođenja = txtDatumRođenja.Text;
            UlogiraniKorisnik.Kontakt = txtKontakt.Text;

            KorisnikRepozitorij.AžurirajKorisnika(korisnik);

             frmUpozorenje = new FrmUpozorenje("Profil uspješno ažuriran!");
             frmUpozorenje.ShowDialog();
        }


        private void UCKorisničkiProfil_Load(object sender, EventArgs e)
        {
            int idKorisnika2 = UlogiraniKorisnik.Id_korisnik;

            lblStanjeRacuna.Text = KorisnikRepozitorij.DohvatiStanjeRacuna(idKorisnika2).ToString();
            OsvježiProfil();
            lblStanjeRacuna.Visible = false;
        }

        private void btnSakrijStanjeRačuna_Click(object sender, EventArgs e)
        {
            lblStanjeRacuna.Visible = false;
        }

        private void btnPrikaziStanje_Click(object sender, EventArgs e)
        {
            lblStanjeRacuna.Visible = true;
        }
    }
}
