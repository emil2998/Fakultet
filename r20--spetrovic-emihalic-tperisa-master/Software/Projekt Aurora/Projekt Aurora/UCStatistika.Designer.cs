namespace Projekt_Aurora
{
    partial class UCStatistika
    {
        /// <summary> 
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary> 
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Component Designer generated code

        /// <summary> 
        /// Required method for Designer support - do not modify 
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.lblUkupniPrihodRezervacija = new System.Windows.Forms.Label();
            this.lblUkupniPrihodKupljeneKarte = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.cmbOdabirFilma = new System.Windows.Forms.ComboBox();
            this.grafPrikaz1 = new VanjskeBiblioteke.GrafPrikaz();
            this.SuspendLayout();
            // 
            // lblUkupniPrihodRezervacija
            // 
            this.lblUkupniPrihodRezervacija.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.lblUkupniPrihodRezervacija.AutoSize = true;
            this.lblUkupniPrihodRezervacija.BackColor = System.Drawing.Color.Transparent;
            this.lblUkupniPrihodRezervacija.Font = new System.Drawing.Font("Arial Rounded MT Bold", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblUkupniPrihodRezervacija.ForeColor = System.Drawing.Color.WhiteSmoke;
            this.lblUkupniPrihodRezervacija.Location = new System.Drawing.Point(558, 855);
            this.lblUkupniPrihodRezervacija.MaximumSize = new System.Drawing.Size(0, 40);
            this.lblUkupniPrihodRezervacija.Name = "lblUkupniPrihodRezervacija";
            this.lblUkupniPrihodRezervacija.Size = new System.Drawing.Size(209, 18);
            this.lblUkupniPrihodRezervacija.TabIndex = 124;
            this.lblUkupniPrihodRezervacija.Text = "RezervacijaUkupniPrihod";
            // 
            // lblUkupniPrihodKupljeneKarte
            // 
            this.lblUkupniPrihodKupljeneKarte.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.lblUkupniPrihodKupljeneKarte.AutoSize = true;
            this.lblUkupniPrihodKupljeneKarte.BackColor = System.Drawing.Color.Transparent;
            this.lblUkupniPrihodKupljeneKarte.Font = new System.Drawing.Font("Arial Rounded MT Bold", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblUkupniPrihodKupljeneKarte.ForeColor = System.Drawing.Color.WhiteSmoke;
            this.lblUkupniPrihodKupljeneKarte.Location = new System.Drawing.Point(558, 802);
            this.lblUkupniPrihodKupljeneKarte.MaximumSize = new System.Drawing.Size(0, 40);
            this.lblUkupniPrihodKupljeneKarte.Name = "lblUkupniPrihodKupljeneKarte";
            this.lblUkupniPrihodKupljeneKarte.Size = new System.Drawing.Size(210, 18);
            this.lblUkupniPrihodKupljeneKarte.TabIndex = 125;
            this.lblUkupniPrihodKupljeneKarte.Text = "KupnjaKartiUkupniPrihod";
            // 
            // label5
            // 
            this.label5.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.label5.BackColor = System.Drawing.Color.Transparent;
            this.label5.Font = new System.Drawing.Font("Microsoft Sans Serif", 16F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label5.ForeColor = System.Drawing.Color.WhiteSmoke;
            this.label5.Location = new System.Drawing.Point(12, 797);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(486, 33);
            this.label5.TabIndex = 126;
            this.label5.Text = "Ukupni prihod temeljen na kupljenim kartama:";
            // 
            // label1
            // 
            this.label1.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.label1.BackColor = System.Drawing.Color.Transparent;
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 16F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.ForeColor = System.Drawing.Color.WhiteSmoke;
            this.label1.Location = new System.Drawing.Point(14, 850);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(538, 29);
            this.label1.TabIndex = 127;
            this.label1.Text = "Ukupni očekivani prihod temeljen na rezervacijama:";
            // 
            // label2
            // 
            this.label2.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.label2.BackColor = System.Drawing.Color.Transparent;
            this.label2.Font = new System.Drawing.Font("Cooper Black", 50F);
            this.label2.ForeColor = System.Drawing.Color.WhiteSmoke;
            this.label2.Location = new System.Drawing.Point(548, 0);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(733, 64);
            this.label2.TabIndex = 130;
            this.label2.Text = "STATISTIKA";
            // 
            // cmbOdabirFilma
            // 
            this.cmbOdabirFilma.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.cmbOdabirFilma.FormattingEnabled = true;
            this.cmbOdabirFilma.Location = new System.Drawing.Point(130, 136);
            this.cmbOdabirFilma.Name = "cmbOdabirFilma";
            this.cmbOdabirFilma.Size = new System.Drawing.Size(455, 21);
            this.cmbOdabirFilma.TabIndex = 131;
            this.cmbOdabirFilma.SelectedIndexChanged += new System.EventHandler(this.cmbOdabirFilma_SelectedIndexChanged);
            // 
            // grafPrikaz1
            // 
            this.grafPrikaz1.BackColor = System.Drawing.Color.Transparent;
            this.grafPrikaz1.Location = new System.Drawing.Point(64, 228);
            this.grafPrikaz1.Name = "grafPrikaz1";
            this.grafPrikaz1.Naziv = null;
            this.grafPrikaz1.Naziv2 = null;
            this.grafPrikaz1.Size = new System.Drawing.Size(1195, 503);
            this.grafPrikaz1.TabIndex = 132;
            this.grafPrikaz1.XosVrijednosti = null;
            this.grafPrikaz1.YosVrijednosti = null;
            this.grafPrikaz1.YosVrijednosti2 = null;
            // 
            // UCStatistika
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.Transparent;
            this.Controls.Add(this.grafPrikaz1);
            this.Controls.Add(this.cmbOdabirFilma);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.lblUkupniPrihodKupljeneKarte);
            this.Controls.Add(this.lblUkupniPrihodRezervacija);
            this.Name = "UCStatistika";
            this.Size = new System.Drawing.Size(1517, 1071);
            this.Load += new System.EventHandler(this.UCStatistika_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion
        private System.Windows.Forms.Label lblUkupniPrihodRezervacija;
        private System.Windows.Forms.Label lblUkupniPrihodKupljeneKarte;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.ComboBox cmbOdabirFilma;
        private VanjskeBiblioteke.GrafPrikaz grafPrikaz1;
    }
}
