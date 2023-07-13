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
    public partial class FrmBaza : Form
    {
        static string path = Path.Combine(Directory.GetParent(System.IO.Directory.GetCurrentDirectory()).Parent.Parent.FullName);
        string path2 = path + "\\Aurora.chm";
        
        public FrmBaza(string selektiranaTablica,string radnja)
        {
            
            InitializeComponent();
            helpProvider.HelpNamespace = path2;
            if (selektiranaTablica == "Film" && radnja == "Dodaj")
            {
                UCBazaDodajFilm ucBazaDodajFilm = new UCBazaDodajFilm();
                panel.Controls.Add(ucBazaDodajFilm);
            }
            if(selektiranaTablica == "Zanr" && radnja == "Dodaj")
            {
                UCBazaDodajZanr ucBazaDodajZanr = new UCBazaDodajZanr();
                panel.Controls.Add(ucBazaDodajZanr);
            }
            if(selektiranaTablica == "Glumac" && radnja == "Dodaj")
            {
                UCBazaDodajGlumca ucBazaDodajGlumca = new UCBazaDodajGlumca();
                panel.Controls.Add(ucBazaDodajGlumca);
            }
            if (selektiranaTablica == "Kina" && radnja == "Dodaj")
            {
                UCBazaDodajKina ucBazaDodajKina = new UCBazaDodajKina();
                panel.Controls.Add(ucBazaDodajKina);
            }
            if (selektiranaTablica == "Dvorane" && radnja == "Dodaj")
            {
                
                UCBazaDodajDvoranu ucBazaDodajDvoranu = new UCBazaDodajDvoranu();
                panel.Controls.Add(ucBazaDodajDvoranu);
                
            }
            if (selektiranaTablica == "Projekcije" && radnja == "Dodaj")
            {

                UCBazaDodajProjekciju ucBazaDodajProjekciju = new UCBazaDodajProjekciju();
                panel.Controls.Add(ucBazaDodajProjekciju);

            }
        }

        public FrmBaza(Film film)
        {
            InitializeComponent();
            UCBazaIzmijeniFilm ucBazaIzmijeniFilm = new UCBazaIzmijeniFilm(film);
            panel.Controls.Add(ucBazaIzmijeniFilm);
        }

        public FrmBaza (Zanr zanr)
        {
            InitializeComponent();
            UCBazaIzmijeniZanr ucBazaIzmijeniZanr = new UCBazaIzmijeniZanr(zanr);
            panel.Controls.Add(ucBazaIzmijeniZanr);
        }

        public FrmBaza(Glumac glumac)
        {
            InitializeComponent();
            UCBazaIzmijeniGlumca ucBazaIzmijeniGlumca = new UCBazaIzmijeniGlumca(glumac);
            panel.Controls.Add(ucBazaIzmijeniGlumca);
        }
        public FrmBaza(Kino kino)
        {
            InitializeComponent();
            UCBazaIzmijeniKino ucBazaIzmijeniKino = new UCBazaIzmijeniKino(kino);
            panel.Controls.Add(ucBazaIzmijeniKino);
        }
        public FrmBaza(Dvorana dvorana)
        {
            InitializeComponent();
            UCBazaIzmijeniDvoranu ucBazaIzmijeniDvoranu = new UCBazaIzmijeniDvoranu(dvorana);
            panel.Controls.Add(ucBazaIzmijeniDvoranu);
        }
        public FrmBaza(Projekcija projekcija)
        {
            InitializeComponent();
            UCBazaIzmijeniProjekciju ucBazaIzmijeniProjekciju = new UCBazaIzmijeniProjekciju(projekcija);
            panel.Controls.Add(ucBazaIzmijeniProjekciju);
        }

        public FrmBaza(String radnja, Film film)
        {
            InitializeComponent();
            if(radnja == "DodjeliZ")
            {
                UCBazaDodjeliZanr ucBazaDodjeliZanr = new UCBazaDodjeliZanr(film);
                panel.Controls.Add(ucBazaDodjeliZanr);
            }
            if(radnja == "DodjeliG") 
            {
                UCBazaDodjeliGlumca ucBazaDodjeliGlumca = new UCBazaDodjeliGlumca(film);
                panel.Controls.Add(ucBazaDodjeliGlumca);
            }
        }

        private void panel_Paint(object sender, PaintEventArgs e)
        {

        }
    }
}
