namespace Projekt_Aurora
{
    partial class FrmRezerviranje
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

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.btnRezervacije = new System.Windows.Forms.Button();
            this.panel = new System.Windows.Forms.Panel();
            this.btnPlatno = new System.Windows.Forms.Button();
            this.radioBtnRezerviraj = new System.Windows.Forms.RadioButton();
            this.radioBtnKupi = new System.Windows.Forms.RadioButton();
            this.btnOdustani = new System.Windows.Forms.Button();
            this.helpProvider1 = new System.Windows.Forms.HelpProvider();
            this.SuspendLayout();
            // 
            // btnRezervacije
            // 
            this.btnRezervacije.BackColor = System.Drawing.Color.Red;
            this.btnRezervacije.Location = new System.Drawing.Point(1283, 484);
            this.btnRezervacije.Name = "btnRezervacije";
            this.btnRezervacije.Size = new System.Drawing.Size(131, 62);
            this.btnRezervacije.TabIndex = 16;
            this.btnRezervacije.Text = "Nastavi";
            this.btnRezervacije.UseVisualStyleBackColor = false;
            this.btnRezervacije.Click += new System.EventHandler(this.button1_Click);
            // 
            // panel
            // 
            this.panel.Location = new System.Drawing.Point(12, 117);
            this.panel.Name = "panel";
            this.panel.Size = new System.Drawing.Size(1222, 889);
            this.panel.TabIndex = 9;
            this.panel.Paint += new System.Windows.Forms.PaintEventHandler(this.panel_Paint);
            // 
            // btnPlatno
            // 
            this.btnPlatno.BackColor = System.Drawing.Color.White;
            this.btnPlatno.Font = new System.Drawing.Font("Microsoft Sans Serif", 15.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnPlatno.ForeColor = System.Drawing.Color.Black;
            this.btnPlatno.Location = new System.Drawing.Point(97, 12);
            this.btnPlatno.Name = "btnPlatno";
            this.btnPlatno.Size = new System.Drawing.Size(773, 90);
            this.btnPlatno.TabIndex = 18;
            this.btnPlatno.Text = "Platno";
            this.btnPlatno.UseVisualStyleBackColor = false;
            // 
            // radioBtnRezerviraj
            // 
            this.radioBtnRezerviraj.AutoSize = true;
            this.radioBtnRezerviraj.Location = new System.Drawing.Point(1304, 413);
            this.radioBtnRezerviraj.Name = "radioBtnRezerviraj";
            this.radioBtnRezerviraj.Size = new System.Drawing.Size(72, 17);
            this.radioBtnRezerviraj.TabIndex = 19;
            this.radioBtnRezerviraj.TabStop = true;
            this.radioBtnRezerviraj.Text = "Rezerviraj";
            this.radioBtnRezerviraj.UseVisualStyleBackColor = true;
            // 
            // radioBtnKupi
            // 
            this.radioBtnKupi.AutoSize = true;
            this.radioBtnKupi.Location = new System.Drawing.Point(1304, 436);
            this.radioBtnKupi.Name = "radioBtnKupi";
            this.radioBtnKupi.Size = new System.Drawing.Size(46, 17);
            this.radioBtnKupi.TabIndex = 20;
            this.radioBtnKupi.TabStop = true;
            this.radioBtnKupi.Text = "Kupi";
            this.radioBtnKupi.UseVisualStyleBackColor = true;
            // 
            // btnOdustani
            // 
            this.btnOdustani.BackColor = System.Drawing.Color.Red;
            this.btnOdustani.Location = new System.Drawing.Point(1283, 561);
            this.btnOdustani.Name = "btnOdustani";
            this.btnOdustani.Size = new System.Drawing.Size(131, 62);
            this.btnOdustani.TabIndex = 21;
            this.btnOdustani.Text = "Odustani";
            this.btnOdustani.UseVisualStyleBackColor = false;
            this.btnOdustani.Click += new System.EventHandler(this.btnOdustani_Click);
            // 
            // helpProvider1
            // 
            this.helpProvider1.HelpNamespace = "";
            // 
            // FrmRezerviranje
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.Black;
            this.ClientSize = new System.Drawing.Size(1480, 847);
            this.Controls.Add(this.btnOdustani);
            this.Controls.Add(this.radioBtnKupi);
            this.Controls.Add(this.radioBtnRezerviraj);
            this.Controls.Add(this.btnPlatno);
            this.Controls.Add(this.btnRezervacije);
            this.Controls.Add(this.panel);
            this.ForeColor = System.Drawing.Color.WhiteSmoke;
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.None;
            this.Name = "FrmRezerviranje";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "FrmRezerviranje";
            this.WindowState = System.Windows.Forms.FormWindowState.Maximized;
            this.Load += new System.EventHandler(this.FrmRezerviranje_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion
        private System.Windows.Forms.Button btnRezervacije;
        private System.Windows.Forms.Panel panel;
        private System.Windows.Forms.Button btnPlatno;
        private System.Windows.Forms.RadioButton radioBtnRezerviraj;
        private System.Windows.Forms.RadioButton radioBtnKupi;
        private System.Windows.Forms.Button btnOdustani;
        private System.Windows.Forms.HelpProvider helpProvider1;
    }
}