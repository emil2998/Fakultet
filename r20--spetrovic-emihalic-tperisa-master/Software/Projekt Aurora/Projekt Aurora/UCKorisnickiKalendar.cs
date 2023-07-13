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
    public partial class UCKorisnickiKalendar : UserControl
    {
        public UCKorisnickiKalendar()
        {
            InitializeComponent();
        }

        private void UCKorisnickiKalendar_Load(object sender, EventArgs e)
        {
            dgvKalendar.DataSource = KalendarTransakcijaRepozitorij.DohvatiKalendar();
            this.dgvKalendar.Columns[0].HeaderText = "Film";
            this.dgvKalendar.Columns[1].HeaderText = "Datum";
            this.dgvKalendar.Columns[2].HeaderText = "Status uplate";
            this.dgvKalendar.Columns[3].HeaderText = "Broj rezerviranog sjedala";
        }

        
        private void rbtnFilmu_CheckedChanged(object sender, EventArgs e)
        {
            if (rbtnFilmu.Checked)
            {
                dgvKalendar.DataSource = null;
                dgvKalendar.DataSource = KalendarTransakcijaRepozitorij.DohvatiKalendar("film.naziv");
                this.dgvKalendar.Columns[0].HeaderText = "Film";
                this.dgvKalendar.Columns[1].HeaderText = "Datum";
                this.dgvKalendar.Columns[2].HeaderText = "Status uplate";
                this.dgvKalendar.Columns[3].HeaderText = "Broj rezerviranog sjedala";
            }
          
        }

        private void rbtnDatum_CheckedChanged(object sender, EventArgs e)
        {
            if (rbtnDatum.Checked)
            {
                dgvKalendar.DataSource = null;
                dgvKalendar.DataSource = KalendarTransakcijaRepozitorij.DohvatiKalendar("projekcija.vrijeme");
                this.dgvKalendar.Columns[0].HeaderText = "Film";
                this.dgvKalendar.Columns[1].HeaderText = "Datum";
                this.dgvKalendar.Columns[2].HeaderText = "Status uplate";
                this.dgvKalendar.Columns[3].HeaderText = "Broj rezerviranog sjedala";
            }
         
        }

        private void rbtnStatusUplate_CheckedChanged(object sender, EventArgs e)
        {
            if (rbtnStatusUplate.Checked)
            {
                dgvKalendar.DataSource = null;
                dgvKalendar.DataSource = KalendarTransakcijaRepozitorij.DohvatiKalendar("kino_ulaznica.status_uplate");
                this.dgvKalendar.Columns[0].HeaderText = "Film";
                this.dgvKalendar.Columns[1].HeaderText = "Datum";
                this.dgvKalendar.Columns[2].HeaderText = "Status uplate";
                this.dgvKalendar.Columns[3].HeaderText = "Broj rezerviranog sjedala";
            }

          
        }

        private void rbtnBrojuSjedala_CheckedChanged(object sender, EventArgs e)
        {
            if (rbtnBrojuSjedala.Checked)
            {
                dgvKalendar.DataSource = null;
                dgvKalendar.DataSource = KalendarTransakcijaRepozitorij.DohvatiKalendar("kino_ulaznica.broj_sjedala");
                this.dgvKalendar.Columns[0].HeaderText = "Film";
                this.dgvKalendar.Columns[1].HeaderText = "Datum";
                this.dgvKalendar.Columns[2].HeaderText = "Status uplate";
                this.dgvKalendar.Columns[3].HeaderText = "Broj rezerviranog sjedala";
            }
           
        }
 
    }
}
