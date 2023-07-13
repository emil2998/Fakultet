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
    public partial class UCBazaDodjeliZanr : UserControl
    {
        private Film selektiraniFilm = new Film();
        public UCBazaDodjeliZanr(Film film)
        {
            selektiraniFilm = film;
            InitializeComponent();
            OsvjeziDodjeljeneZanrove();
            OsvjeziSveZanrove();
        }

        public void OsvjeziDodjeljeneZanrove()
        {
            dgvDodjeljeniZanrovi.DataSource = ZanrRepozitorij.DohvatiZanroveFilma(selektiraniFilm);
            dgvDodjeljeniZanrovi.Columns[0].Visible = false;
        }

        public void OsvjeziSveZanrove()
        {
            dgvSviZanrovi.DataSource = ZanrRepozitorij.DohvatiSveNedodjeljeneZanrove(selektiraniFilm);
            dgvSviZanrovi.Columns[0].Visible = false;
        }

        private void btnOdustani_Click(object sender, EventArgs e)
        {
            this.ParentForm.Close();
        }

        private void btnDodaj_Click(object sender, EventArgs e)
        {
            if (dgvSviZanrovi.SelectedRows.Count >= 1)
            {
                foreach (DataGridViewRow r in dgvSviZanrovi.SelectedRows)
                {
                    ZanrRepozitorij.DodjeliZanrFilmu(selektiraniFilm,r.DataBoundItem as Zanr);
                }
                OsvjeziDodjeljeneZanrove();
                OsvjeziSveZanrove();
            }
        }

        private void btnIzbaci_Click(object sender, EventArgs e)
        {
            if (dgvDodjeljeniZanrovi.SelectedRows.Count >= 1)
            {
                foreach (DataGridViewRow r in dgvDodjeljeniZanrovi.SelectedRows)
                {
                    ZanrRepozitorij.IzbaciZanrFilma(selektiraniFilm,r.DataBoundItem as Zanr);
                }
                OsvjeziDodjeljeneZanrove();
                OsvjeziSveZanrove();
            }
        }
    }
}
