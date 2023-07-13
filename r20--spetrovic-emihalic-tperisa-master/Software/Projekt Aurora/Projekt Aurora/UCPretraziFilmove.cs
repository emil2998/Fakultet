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
    public partial class UCPretraziFilmove : UserControl
    {
        public UCPretraziFilmove()
        {
            InitializeComponent();
            OsvjeziFilmove();
        }

        private void OsvjeziFilmove()
        {
            this.dgvFilmovi.DataSource = FilmRepozitorij.DohvatiFilmove();
            dgvFilmovi.Columns[0].Visible = false;
        }

        private void btnOpsirnije_Click(object sender, EventArgs e)
        {
            if (this.dgvFilmovi.SelectedRows.Count == 1)
            {
                if (btnOpsirnije.Enabled == true)
                {
                    if (this.panelOpsirnije.Visible == false)
                    {
                        panelOpsirnije.Visible = true;
                    }
                    if (UlogiraniKorisnik.IdUloga == 1 || UlogiraniKorisnik.IdUloga == 2)
                    {
                        this.btnRecenziraj.Visible = true;
                    }
                    Film film = dgvFilmovi.SelectedRows[0].DataBoundItem as Film;
                    labelNaziv.Text = film.Naziv;
                    labelGodina.Text = film.Godina.ToString();
                    labelRedatelj.Text = film.Redatelj;
                    labelTrajanje.Text = film.Trajanje;
                    txtOpis.Text = film.Opis;
                    dgvGlumci.DataSource = FilmRepozitorij.DohvatiGlumce(film);
                    dgvGlumci.Columns[0].Visible = false;
                    dgvGlumci.Columns[1].HeaderText = "Ime";
                    dgvGlumci.Columns[2].HeaderText = "Prezime";
                    Film filmZanr = FilmRepozitorij.DohvatiZanrFilma(film);
                    film.Zanrovi = filmZanr.Zanrovi;
                    List<string> zanrovi = film.Zanrovi;
                    labelZanrovi.Text = "";
                    foreach (string zanr in zanrovi)
                    {
                        labelZanrovi.Text += zanr + " ";
                    }
                }
            }
        }

        private void rbtnNaziv_CheckedChanged(object sender, EventArgs e)
        {
            txtFiltriraj.Enabled = true;
        }

        private void rbtnGodina_CheckedChanged(object sender, EventArgs e)
        {
            txtFiltriraj.Enabled = true;
        }

        private void rbtnRedatelj_CheckedChanged(object sender, EventArgs e)
        {
            txtFiltriraj.Enabled = true;
        }

        private void rbtnZanr_CheckedChanged(object sender, EventArgs e)
        {
            txtFiltriraj.Enabled = true;
        }

        private void btnFiltriraj_Click(object sender, EventArgs e)
        {
            panelOpsirnije.Visible = false;
            string uvjet = "";
            string sadrzaj = txtFiltriraj.Text;
            if (this.rbtnNaziv.Checked == true)
            {
                uvjet = "Naziv";
            }
            if (this.rbtnGodina.Checked == true)
            {
                uvjet = "Godina";
            }
            if (this.rbtnRedatelj.Checked == true)
            {
                uvjet = "Redatelj";
            }
            if (this.rbtnZanr.Checked == true)
            {
                uvjet = "Zanr";
            }
            dgvFilmovi.DataSource = FilmRepozitorij.DohvatiFiltriraneFilmove(uvjet, sadrzaj);
        }

        private void btnPregledajRecenzije_Click(object sender, EventArgs e)
        {
            Film film = dgvFilmovi.SelectedRows[0].DataBoundItem as Film;
            List<Recenzija> listaRecenzija = null;
            listaRecenzija = RecenzijaRepozitorij.DohvatiRecenzije(film);
            FrmRecenzije frmRecenzije = new FrmRecenzije(listaRecenzija, film);
            frmRecenzije.ShowDialog();
        }

        private void btnRecenziraj_Click(object sender, EventArgs e)
        {
            Film film = dgvFilmovi.SelectedRows[0].DataBoundItem as Film;
            int IdKorisnik = UlogiraniKorisnik.Id_korisnik;
            FrmRecenziraj frmRecenziraj = new FrmRecenziraj(film, IdKorisnik);
            frmRecenziraj.ShowDialog();
        }

        private void txtFiltriraj_TextChanged(object sender, EventArgs e)
        {
            btnFiltriraj.Enabled = true;
        }
    }
}
