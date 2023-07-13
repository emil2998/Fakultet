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
    public partial class UCUpravljanjeBazom : UserControl
    {
        private string selektiranaTablica = "";
        private string radnja = "";
        public UCUpravljanjeBazom()
        {
            InitializeComponent();
            foreach (Control control in panelPrikazTablice.Controls)
            {
                control.Visible = false;
            }
        }

        private void OdznačiGumbe()
        {
            foreach(Button gumb in panelGumbiTablica.Controls)
            {
                gumb.BackColor = Color.FromArgb(33,33,33);
            }
        }

        private void btnFilmovi_Click(object sender, EventArgs e)
        {
            OdznačiGumbe();
            btnFilmovi.BackColor = Color.FromArgb(215, 12, 42);
            bool postojeGlumci = false;
            bool postojeZanrovi = false;
            selektiranaTablica = "Film";
            dgvTablica.DataSource = FilmRepozitorij.DohvatiFilmove();
            btnDodaj.Text = "Dodaj film";
            btnIzmijeni.Text = "Izmijeni podatke o filmu";
            btnObrisi.Text = "Obriši film";
            foreach (Control control in panelPrikazTablice.Controls)
            {
                control.Visible = true;
                if (control.Text == "Dodjeli glumce")
                {
                    postojeGlumci = true;
                }
                if (control.Text == "Dodjeli žanrove")
                {
                    postojeZanrovi = true;
                }
            }
            if (postojeGlumci == false)
            {
                Button btnDodjeliGlumce = new Button();
                btnDodjeliGlumce.Anchor = (AnchorStyles.Top | AnchorStyles.Left);
                btnDodjeliGlumce.Height = 40;
                btnDodjeliGlumce.Width = 122;
                btnDodjeliGlumce.Text = "Dodjeli glumce";
                Point lokacija = new Point(387, 3);
                btnDodjeliGlumce.Location = lokacija;
                btnDodjeliGlumce.BackColor = Color.FromArgb(215,12,42);
                btnDodjeliGlumce.ForeColor = Color.WhiteSmoke;
                btnDodjeliGlumce.FlatStyle = FlatStyle.Flat;
                btnDodjeliGlumce.FlatAppearance.BorderSize = 0;
                btnDodjeliGlumce.Font = btnDodaj.Font;
                panelPrikazTablice.Controls.Add(btnDodjeliGlumce);
                btnDodjeliGlumce.Click += BtnDodjeliGlumce_Click;
            }
            if (postojeZanrovi == false)
            {
                Button btnDodjeliZanrove = new Button();
                btnDodjeliZanrove.Anchor = (AnchorStyles.Top | AnchorStyles.Left);
                btnDodjeliZanrove.Height = 40;
                btnDodjeliZanrove.Width = 122;
                btnDodjeliZanrove.Text = "Dodjeli žanrove";
                Point lokacija2 = new Point(515, 3);
                btnDodjeliZanrove.Location = lokacija2;
                btnDodjeliZanrove.BackColor = Color.FromArgb(215, 12, 42);
                btnDodjeliZanrove.ForeColor = Color.WhiteSmoke;
                btnDodjeliZanrove.FlatStyle = FlatStyle.Flat;
                btnDodjeliZanrove.FlatAppearance.BorderSize = 0;
                btnDodjeliZanrove.Font = btnDodaj.Font;
                panelPrikazTablice.Controls.Add(btnDodjeliZanrove);
                btnDodjeliZanrove.Click += BtnDodjeliZanrove_Click;
            }
        }

        private void BtnDodjeliGlumce_Click(object sender, EventArgs e)
        {
            radnja = "DodjeliG";
            if (this.dgvTablica.SelectedRows.Count == 1)
            {
                FrmBaza frmBaza = new FrmBaza(radnja, dgvTablica.SelectedRows[0].DataBoundItem as Film);
                frmBaza.ShowDialog();
            }
        }

        private void BtnDodjeliZanrove_Click(object sender, EventArgs e)
        {
            radnja = "DodjeliZ";
            if (this.dgvTablica.SelectedRows.Count == 1)
            {
                FrmBaza frmBaza = new FrmBaza(radnja, dgvTablica.SelectedRows[0].DataBoundItem as Film);
                frmBaza.ShowDialog();
            }
        }

        private void btnZanrovi_Click(object sender, EventArgs e)
        {
            OdznačiGumbe();
            btnZanrovi.BackColor = Color.FromArgb(215, 12, 42);
            selektiranaTablica = "Zanr";
            dgvTablica.DataSource = ZanrRepozitorij.DohvatiZanrove();
            btnDodaj.Text = "Dodaj žanr";
            btnIzmijeni.Text = "Izmijeni podatke o žanru";
            btnObrisi.Text = "Obriši žanr";
            foreach (Control control in panelPrikazTablice.Controls)
            {
                control.Visible = true;
                if (control.Text == "Dodjeli glumce" || control.Text == "Dodjeli žanrove")
                {
                    control.Visible = false;
                }
            }
        }

        private void btnGlumci_Click(object sender, EventArgs e)
        {
            OdznačiGumbe();
            btnGlumci.BackColor = Color.FromArgb(215, 12, 42);
            selektiranaTablica = "Glumac";
            dgvTablica.DataSource = GlumacRepozitorij.DohvatiGlumce();
            btnDodaj.Text = "Dodaj glumca";
            btnIzmijeni.Text = "Izmijeni podatke o glumcu";
            btnObrisi.Text = "Obriši glumca";
            foreach (Control control in panelPrikazTablice.Controls)
            {
                control.Visible = true;
                if (control.Text == "Dodjeli glumce" || control.Text == "Dodjeli žanrove")
                {
                    control.Visible = false;
                }
            }
        }

        private void btnDodaj_Click(object sender, EventArgs e)
        {
            radnja = "Dodaj";
            FrmBaza frmBaza = new FrmBaza(selektiranaTablica, radnja);
            frmBaza.ShowDialog();
            if (selektiranaTablica == "Film")
            {
                dgvTablica.DataSource = FilmRepozitorij.DohvatiFilmove();
            }
            if (selektiranaTablica == "Zanr")
            {
                dgvTablica.DataSource = ZanrRepozitorij.DohvatiZanrove();
            }
            if (selektiranaTablica == "Glumac")
            {
                dgvTablica.DataSource = GlumacRepozitorij.DohvatiGlumce();
            }
            if (selektiranaTablica == "Kina")
            {
                dgvTablica.DataSource = KinoRepozitorij.DohvatiKina();
            }
            if (selektiranaTablica == "Dvorane")
            {
                dgvTablica.DataSource = DvoranaRepozitorij.DohvatiDvorane();
            }
            if (selektiranaTablica == "Projekcije")
            {
                dgvTablica.DataSource = ProjekcijaRepozitorij.DohvatiProjekcije();
            }
        }

        private void btnIzmijeni_Click(object sender, EventArgs e)
        {
            radnja = "Izmijeni";
            if (this.dgvTablica.SelectedRows.Count == 1)
            {
                if (selektiranaTablica == "Film")
                {
                    Film film = dgvTablica.SelectedRows[0].DataBoundItem as Film;
                    FrmBaza frmBaza = new FrmBaza(film);
                    frmBaza.ShowDialog();
                    dgvTablica.DataSource = FilmRepozitorij.DohvatiFilmove();
                }
                if (selektiranaTablica == "Zanr")
                {
                    Zanr zanr = dgvTablica.SelectedRows[0].DataBoundItem as Zanr;
                    FrmBaza frmBaza = new FrmBaza(zanr);
                    frmBaza.ShowDialog();
                    dgvTablica.DataSource = ZanrRepozitorij.DohvatiZanrove();
                }
                if (selektiranaTablica == "Glumac")
                {
                    Glumac glumac = dgvTablica.SelectedRows[0].DataBoundItem as Glumac;
                    FrmBaza frmBaza = new FrmBaza(glumac);
                    frmBaza.ShowDialog();
                    dgvTablica.DataSource = GlumacRepozitorij.DohvatiGlumce();
                }
                if (selektiranaTablica == "Kina")
                {
                    Kino kino = dgvTablica.SelectedRows[0].DataBoundItem as Kino;
                    FrmBaza frmBaza = new FrmBaza(kino);
                    frmBaza.ShowDialog();
                    dgvTablica.DataSource = KinoRepozitorij.DohvatiKina();
                }
                if (selektiranaTablica == "Dvorane")
                {
                    Dvorana dvorana = dgvTablica.SelectedRows[0].DataBoundItem as Dvorana;
                    FrmBaza frmBaza = new FrmBaza(dvorana);
                    frmBaza.ShowDialog();
                    dgvTablica.DataSource = DvoranaRepozitorij.DohvatiDvorane();
                }
                if (selektiranaTablica == "Projekcije")
                {
                    Projekcija projekcija = dgvTablica.SelectedRows[0].DataBoundItem as Projekcija;
                    FrmBaza frmBaza = new FrmBaza(projekcija);
                    frmBaza.ShowDialog();
                    dgvTablica.DataSource = ProjekcijaRepozitorij.DohvatiProjekcije();
                }
            }
        }

        private void btnObrisi_Click(object sender, EventArgs e)
        {
            if (this.dgvTablica.SelectedRows.Count >= 1)
            {
                if (selektiranaTablica == "Film")
                {
                    foreach (DataGridViewRow r in dgvTablica.SelectedRows)
                    {
                        FilmRepozitorij.ObrisiFilm(r.DataBoundItem as Film);
                    }
                    dgvTablica.DataSource = FilmRepozitorij.DohvatiFilmove();
                }
                if (selektiranaTablica == "Zanr")
                {
                    foreach (DataGridViewRow r in dgvTablica.SelectedRows)
                    {
                        ZanrRepozitorij.ObrisiZanr(r.DataBoundItem as Zanr);
                    }
                    dgvTablica.DataSource = ZanrRepozitorij.DohvatiZanrove();
                }
                if (selektiranaTablica == "Glumac")
                {
                    foreach (DataGridViewRow r in dgvTablica.SelectedRows)
                    {
                        GlumacRepozitorij.ObrisiGlumca(r.DataBoundItem as Glumac);
                    }
                    dgvTablica.DataSource = GlumacRepozitorij.DohvatiGlumce();
                }
                if (selektiranaTablica == "Kina")
                {
                    foreach (DataGridViewRow r in dgvTablica.SelectedRows)
                    {
                        KinoRepozitorij.Obrisi(r.DataBoundItem as Kino);
                    }
                    dgvTablica.DataSource = KinoRepozitorij.DohvatiKina();
                }
                if (selektiranaTablica == "Dvorane")
                {
                    foreach (DataGridViewRow r in dgvTablica.SelectedRows)
                    {
                        DvoranaRepozitorij.Obrisi(r.DataBoundItem as Dvorana);
                    }
                    dgvTablica.DataSource = DvoranaRepozitorij.DohvatiDvorane();
                }
                if (selektiranaTablica == "Projekcije")
                {
                    foreach (DataGridViewRow r in dgvTablica.SelectedRows)
                    {
                        ProjekcijaRepozitorij.Obrisi(r.DataBoundItem as Projekcija);
                    }
                    dgvTablica.DataSource = ProjekcijaRepozitorij.DohvatiProjekcije();
                }
            }
        }

        private void btnKinaDvorane_Click(object sender, EventArgs e)
        {
            OdznačiGumbe();
            btnKina.BackColor = Color.FromArgb(215, 12, 42);
            selektiranaTablica = "Kina";
            dgvTablica.DataSource = KinoRepozitorij.DohvatiKina();
            btnDodaj.Text = "Dodaj kino";
            btnIzmijeni.Text = "Izmijeni podatke o kinu";
            btnObrisi.Text = "Obriši kino";
            foreach (Control control in panelPrikazTablice.Controls)
            {
                control.Visible = true;
                if (control.Text == "Dodjeli glumce" || control.Text == "Dodjeli žanrove")
                {
                    control.Visible = false;
                }
            }
        }

        private void btnDvorane_Click(object sender, EventArgs e)
        {
            OdznačiGumbe();
            btnDvorane.BackColor = Color.FromArgb(215, 12, 42);
            selektiranaTablica = "Dvorane";
            dgvTablica.DataSource = DvoranaRepozitorij.DohvatiDvorane();
            dgvTablica.Columns["Id_kina"].Visible = false;
            dgvTablica.Columns["Kino"].Visible = false;
            btnDodaj.Text = "Dodaj dvoranu";
            btnIzmijeni.Text = "Izmijeni podatke o dvorani";
            btnObrisi.Text = "Obriši dvoranu";
            foreach (Control control in panelPrikazTablice.Controls)
            {
                control.Visible = true;
                if (control.Text == "Dodjeli glumce" || control.Text == "Dodjeli žanrove")
                {
                    control.Visible = false;
                }
            }
        }

        private void dgvTablica_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {

        }

        private void btnProjekcije_Click(object sender, EventArgs e)
        {
            OdznačiGumbe();
            btnProjekcije.BackColor = Color.FromArgb(215, 12, 42);
            selektiranaTablica = "Projekcije";
            dgvTablica.DataSource = ProjekcijaRepozitorij.DohvatiProjekcije();
            dgvTablica.Columns["Id_dvorana"].Visible = false;
            dgvTablica.Columns["Id_film"].Visible = false;
            dgvTablica.Columns["IDKina"].Visible = false;
            btnDodaj.Text = "Dodaj projekciju";
            btnIzmijeni.Text = "Izmijeni podatke o projekciji";
            btnObrisi.Text = "Obriši projekciju";
            foreach (Control control in panelPrikazTablice.Controls)
            {
                control.Visible = true;
                if (control.Text == "Dodjeli glumce" || control.Text == "Dodjeli žanrove")
                {
                    control.Visible = false;
                }
            }
        }
    }
}
