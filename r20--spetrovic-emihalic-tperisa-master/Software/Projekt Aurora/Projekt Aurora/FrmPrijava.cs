using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Projekt_Aurora;
using System.IO;

namespace Projekt_Aurora
{
    public partial class FrmPrijava : Form
    {
        static string path = Path.Combine(Directory.GetParent(System.IO.Directory.GetCurrentDirectory()).Parent.Parent.FullName);
        string path2 = path + "\\Aurora.chm";
        
        public FrmPrijava()
        {
            InitializeComponent();
            helpProvider.HelpNamespace = path2;
        }

        private void Prijava_Load(object sender, EventArgs e)
        {
            txtKorisnickoIme.Select();
        }

        private void btnIzlaz_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }

        private void lnklabel_kreiraj_račun_LinkClicked(object sender, LinkLabelLinkClickedEventArgs e)
        {
            FrmRegistracija registracija = new FrmRegistracija();
            registracija.ShowDialog();
        }

        private void prikaziLozinku_CheckedChanged(object sender, EventArgs e)
        {
            if (prikaziLozinku.Checked == true)
            {
                txtLozinka.UseSystemPasswordChar = false;
            }
            else
            {
                txtLozinka.UseSystemPasswordChar = true;
            }
        }

        private void btnNastaviKaoGost_Click(object sender, EventArgs e)
        {
            FrmGlavnaForma frmAdministrator = new FrmGlavnaForma();
            frmAdministrator.ShowDialog();
            this.Close();
        }

        private void btnPrijaviSe_Click(object sender, EventArgs e)
        {
            List<TextBox> lista = new List<TextBox>();
            lista.Add(txtKorisnickoIme);
            lista.Add(txtLozinka);
            string korisnickoIme = this.txtKorisnickoIme.Text;
            string lozinka = this.txtLozinka.Text;
            if (ProvjeraKorisnickogUnosa.ProvjeriPrijavu(lista)=="")
            {
                FrmGlavnaForma frmAdministrator = new FrmGlavnaForma();
                frmAdministrator.ShowDialog();
                this.Close();
            }
            else
            {
                FrmUpozorenje frmUpozorenje = new FrmUpozorenje(ProvjeraKorisnickogUnosa.ProvjeriPrijavu(lista));
                frmUpozorenje.ShowDialog();
            }
        }

        private void btnRegistrirajSe_Click(object sender, EventArgs e)
        {
            FrmRegistracija frmRegistracija = new FrmRegistracija();
            frmRegistracija.ShowDialog();
        }
    }
}
