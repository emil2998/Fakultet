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


namespace Projekt_Aurora
{
    public partial class FrmGlavnaForma : Form
    {
        static string path = Path.Combine(Directory.GetParent(System.IO.Directory.GetCurrentDirectory()).Parent.Parent.FullName);
        string path2 = path + "\\Aurora.chm";
        
        public FrmGlavnaForma()
        {
            
            InitializeComponent();
            helpProvider.HelpNamespace = path2;
            if (UlogiraniKorisnik.IdUloga != 2)
            {
                btnUpravljajBazomPodataka.Visible = false;
            }
            if(UlogiraniKorisnik.IdUloga != 1 && UlogiraniKorisnik.IdUloga != 2)
            {
                btnOdjava.Visible = false;
                btnPrijaviSe.Visible = true;
            }
            if(UlogiraniKorisnik.IdUloga == 1 || UlogiraniKorisnik.IdUloga == 2)
            {
                btnKalendar.Visible = true;
                btnProfil.Visible = true;
                FrmUpozorenje frmUpozorenje = new FrmUpozorenje("Uspješna prijava!");
                frmUpozorenje.ShowDialog();
            }
            if(UlogiraniKorisnik.IdUloga == 2)
            {
                btnStatistika.Visible = true;
                
            }
            
        }

        private void FrmAdministrator_FormClosed(object sender, FormClosedEventArgs e)
        {
            Application.Exit();
        }

        private void btnIzlaz_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }

        private void btnPretraziFilmove_Click(object sender, EventArgs e)
        {
            OcistiGlavniPanel(this.panelGlavniProzor);
            UCPretraziFilmove ucPretraziFilmove = new UCPretraziFilmove();
            ucPretraziFilmove.Dock = DockStyle.Fill;
            panelGlavniProzor.Controls.Add(ucPretraziFilmove);
        }

        private void btnPretraziKinaIRasporede_Click(object sender, EventArgs e)
        {
            OcistiGlavniPanel(this.panelGlavniProzor);
            UCPretraziKinaIRasporede ucPretraziKinaIRasporede = new UCPretraziKinaIRasporede();
            ucPretraziKinaIRasporede.Dock = DockStyle.Fill;
            panelGlavniProzor.Controls.Add(ucPretraziKinaIRasporede);
        }

        private void btnUpravljajBazomPodataka_Click(object sender, EventArgs e)
        {
            OcistiGlavniPanel(this.panelGlavniProzor);
            UCUpravljanjeBazom ucUpravljanjeBazom = new UCUpravljanjeBazom();
            ucUpravljanjeBazom.Dock = DockStyle.Fill;
            panelGlavniProzor.Controls.Add(ucUpravljanjeBazom);
        }

        private void OcistiGlavniPanel(Panel panel)
        {
            if (panel.Controls.Count> 0)
            {
                panel.Controls.Clear();
            }
        }

        private void btnOdjava_Click(object sender, EventArgs e)
        {
            UlogiraniKorisnik.Odjava();
            btnUpravljajBazomPodataka.Visible = false;
            btnPrijaviSe.Visible = true;
            btnOdjava.Visible = false;
            btnKalendar.Visible = false;
            btnProfil.Visible = false;
            btnStatistika.Visible = false;
            OcistiGlavniPanel(this.panelGlavniProzor);
        }

        private void btnPrijaviSe_Click(object sender, EventArgs e)
        {
            FrmPrijava frmPrijava = new FrmPrijava();
            frmPrijava.ShowDialog();
            this.Close();
        }

        private void btnProfil_Click(object sender, EventArgs e)
        {
            OcistiGlavniPanel(this.panelGlavniProzor);
            UCKorisničkiProfil korisničkiProfil = new UCKorisničkiProfil();
            korisničkiProfil.Dock = DockStyle.Fill;
            panelGlavniProzor.Controls.Add(korisničkiProfil);
        }

        private void btnKalendar_Click(object sender, EventArgs e)
        {
            OcistiGlavniPanel(this.panelGlavniProzor);
            UCKorisnickiKalendar korisnickiKalendar = new UCKorisnickiKalendar();
            korisnickiKalendar.Dock = DockStyle.Fill;
            panelGlavniProzor.Controls.Add(korisnickiKalendar);
        }

        private void btnStatistika_Click(object sender, EventArgs e)
        {
            OcistiGlavniPanel(this.panelGlavniProzor);
            UCStatistika statistika = new UCStatistika();
            statistika.Dock = DockStyle.Fill;
            panelGlavniProzor.Controls.Add(statistika);
        }

        

        
    }
}
