using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.IO;
using System.Runtime.CompilerServices;

namespace Projekt_Aurora
{
    public partial class FrmRegistracija : Form
    {
        static string path = Path.Combine(Directory.GetParent(System.IO.Directory.GetCurrentDirectory()).Parent.Parent.FullName);
        string path2 = path + "\\Aurora.chm";
        

      
        public FrmRegistracija()
        {
            InitializeComponent();
            helpProvider.HelpNamespace = path2;  
           
        }

        private void btnPotvrdi_Click(object sender, EventArgs e)
        {
            List<TextBox> lista = new List<TextBox>();
            lista.Add(txtIme);
            lista.Add(txtPrezime);
            lista.Add(txtAdresa);
            lista.Add(txtGrad);
            lista.Add(txtKontakt);
            lista.Add(txtEmail);
            lista.Add(txtKorisnickoIme);
            lista.Add(txtLozinka);
            lista.Add(txtPotvrdaLozinke);
            if (ProvjeraKorisnickogUnosa.ProvjeriRegistraciju(lista)=="")
            {
                Korisnik novikorisnik = new Korisnik(txtKorisnickoIme.Text, txtLozinka.Text, txtIme.Text, txtPrezime.Text, txtEmail.Text, txtKontakt.Text, txtDatumRođenja.Text, txtAdresa.Text, txtGrad.Text);
                KorisnikRepozitorij.Spremi(novikorisnik);
                Close();
            }          
            else
            {
                FrmUpozorenje frmUpozorenje = new FrmUpozorenje(ProvjeraKorisnickogUnosa.ProvjeriRegistraciju(lista));
                frmUpozorenje.ShowDialog();
            }
        }

        private void btnOdustani_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void btnOsvjezi_Click(object sender, EventArgs e)
        {
            Osvjezi();
            txtIme.Select();
        }

        private void Osvjezi()
        {
            foreach (TextBox tb in this.Controls.OfType<TextBox>()){
                tb.Text = string.Empty;
            }
        }

       

        }

        
       
       


    }
    
