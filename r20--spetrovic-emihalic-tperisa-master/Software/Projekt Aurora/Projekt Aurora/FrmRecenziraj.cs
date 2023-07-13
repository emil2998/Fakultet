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
    public partial class FrmRecenziraj : Form
    {
        static string path = Path.Combine(Directory.GetParent(System.IO.Directory.GetCurrentDirectory()).Parent.Parent.FullName);
        string path2 = path + "\\Aurora.chm";
        
        private int IdFilm = 0;
        private int IdKorisnik = 0;
        private Film Odabranifilm = null;
        public FrmRecenziraj(Film film,int IdKorisnika)
        {
            
            InitializeComponent();
            helpProvider.HelpNamespace = path2;
            this.Focus();
            for(int i = 10; i > 0; i--)
            {
                dudOcjena.Items.Add(i);
            }
            dudOcjena.Text = "1";
            dudOcjena.ReadOnly = true;
            IdFilm = film.ID;
            IdKorisnik = IdKorisnika;
            Odabranifilm = film;
        }

        private void btnOdustani_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void btnSpremi_Click(object sender, EventArgs e)
        {
            Recenzija recenzija = new Recenzija();
            recenzija.IdFilm = IdFilm;
            recenzija.IdKorisnik = IdKorisnik;
            recenzija.Ocijena = decimal.Parse(dudOcjena.Text);
            recenzija.Komentar = txtKomentar.Text;
            RecenzijaRepozitorij.SpremiRecenziju(recenzija, Odabranifilm);
            this.Close();
        }
    }
}
