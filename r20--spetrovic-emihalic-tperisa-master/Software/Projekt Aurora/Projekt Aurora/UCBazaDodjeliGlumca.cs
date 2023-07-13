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
    public partial class UCBazaDodjeliGlumca : UserControl
    {
        private Film selektiraniFilm = new Film();
        public UCBazaDodjeliGlumca(Film film)
        {
            selektiraniFilm = film;
            InitializeComponent();
            OsvjeziGlumceUFilmu();
            OsvjeziSveGlumce();
        }

        public void OsvjeziGlumceUFilmu()
        {
            dgvGlumciUFilmu.DataSource = GlumacRepozitorij.DohvatiGlumceFilma(selektiraniFilm);
            dgvGlumciUFilmu.Columns[0].Visible = false;
        }

        public void OsvjeziSveGlumce()
        {
            dgvSviGlumci.DataSource = GlumacRepozitorij.DohvatiSveNedodjeljeneGlumce(selektiraniFilm);
            dgvSviGlumci.Columns[0].Visible = false;
        }

        private void btnGotovo_Click(object sender, EventArgs e)
        {
            this.ParentForm.Close();
        }

        private void btnIzbaci_Click(object sender, EventArgs e)
        {
            if (dgvGlumciUFilmu.SelectedRows.Count >= 1)
            {
                foreach (DataGridViewRow r in dgvGlumciUFilmu.SelectedRows)
                {
                    GlumacRepozitorij.IzbaciGlumcaIzFilma(selektiraniFilm, r.DataBoundItem as Glumac);
                }
                OsvjeziGlumceUFilmu();
                OsvjeziSveGlumce();
            }
        }

        private void btnDodaj_Click(object sender, EventArgs e)
        {
            if (dgvSviGlumci.SelectedRows.Count >= 1)
            {
                foreach (DataGridViewRow r in dgvSviGlumci.SelectedRows)
                {
                    GlumacRepozitorij.DodjeliGlumcaFilmu(selektiraniFilm, r.DataBoundItem as Glumac);
                }
                OsvjeziGlumceUFilmu();
                OsvjeziSveGlumce();
            }
        }
    }
}
