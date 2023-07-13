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
    public partial class UCPretraziKinaIRasporede : UserControl
    {
        public UCPretraziKinaIRasporede()
        {
            InitializeComponent();
            OsvjeziKina();
        }

        private void OsvjeziKina()
        {
            this.dgvKina.DataSource = KinoRepozitorij.DohvatiKina();
            dgvKina.Columns[0].Visible = false;
        }

        private void OsvjeziRaspored()
        {
            if (this.dgvKina.SelectedRows.Count == 1)
            {
                Kino odabranoKino = dgvKina.SelectedRows[0].DataBoundItem as Kino;
                dgvRaspored.DataSource = RasporedRepozitorij.DohvatiRaspored(odabranoKino);
                this.dgvRaspored.Columns[0].HeaderText = "Film";
                this.dgvRaspored.Columns[1].HeaderText = "Trajanje";
                this.dgvRaspored.Columns[2].HeaderText = "Dvorana";
                this.dgvRaspored.Columns[3].HeaderText = "Vrijeme prikaza";
                this.dgvRaspored.Columns[4].HeaderText = "Cijena";
                dgvRaspored.Columns["IDprojekcije"].Visible = false;
            }
        }

        private void btnPregledajRaspored_Click_1(object sender, EventArgs e)
        {
            OsvjeziRaspored();
        }

             
        private void UCPretraziKinaIRasporede_Load(object sender, EventArgs e)
        {
            if (UlogiraniKorisnik.Id_korisnik == 0)
            {
                btnRezervacije.Hide();

            }

        }

    

        private void btnRezervacije_Click_1(object sender, EventArgs e)
        {
            if (this.dgvRaspored.SelectedRows.Count == 1)
            {
                Raspored odabraniRaspored = dgvRaspored.SelectedRows[0].DataBoundItem as Raspored;

                FrmRezerviranje frmRezerviranje = new FrmRezerviranje(odabraniRaspored);
                frmRezerviranje.ShowDialog();


            }
        }
    }
}
