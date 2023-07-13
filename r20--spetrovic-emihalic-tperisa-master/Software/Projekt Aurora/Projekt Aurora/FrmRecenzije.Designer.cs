namespace Projekt_Aurora
{
    partial class FrmRecenzije
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(FrmRecenzije));
            this.labelNazivFilma = new System.Windows.Forms.Label();
            this.btnNazad = new System.Windows.Forms.Button();
            this.panelRecenzije = new System.Windows.Forms.FlowLayoutPanel();
            this.helpProvider = new System.Windows.Forms.HelpProvider();
            this.SuspendLayout();
            // 
            // labelNazivFilma
            // 
            this.labelNazivFilma.AutoEllipsis = true;
            this.labelNazivFilma.Font = new System.Drawing.Font("Microsoft Sans Serif", 15.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.labelNazivFilma.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(215)))), ((int)(((byte)(12)))), ((int)(((byte)(42)))));
            this.labelNazivFilma.Location = new System.Drawing.Point(13, 9);
            this.labelNazivFilma.Name = "labelNazivFilma";
            this.labelNazivFilma.Size = new System.Drawing.Size(277, 25);
            this.labelNazivFilma.TabIndex = 0;
            this.labelNazivFilma.Text = "Naziv filma";
            this.labelNazivFilma.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // btnNazad
            // 
            this.btnNazad.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(215)))), ((int)(((byte)(12)))), ((int)(((byte)(42)))));
            this.btnNazad.FlatAppearance.BorderSize = 0;
            this.btnNazad.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnNazad.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnNazad.ForeColor = System.Drawing.Color.WhiteSmoke;
            this.btnNazad.Location = new System.Drawing.Point(88, 449);
            this.btnNazad.Name = "btnNazad";
            this.btnNazad.Size = new System.Drawing.Size(123, 48);
            this.btnNazad.TabIndex = 2;
            this.btnNazad.Text = "Povratak";
            this.btnNazad.UseVisualStyleBackColor = false;
            this.btnNazad.Click += new System.EventHandler(this.btnNazad_Click);
            // 
            // panelRecenzije
            // 
            this.panelRecenzije.AutoScroll = true;
            this.panelRecenzije.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(33)))), ((int)(((byte)(33)))), ((int)(((byte)(33)))));
            this.panelRecenzije.FlowDirection = System.Windows.Forms.FlowDirection.TopDown;
            this.panelRecenzije.Location = new System.Drawing.Point(13, 48);
            this.panelRecenzije.Name = "panelRecenzije";
            this.panelRecenzije.Size = new System.Drawing.Size(276, 395);
            this.panelRecenzije.TabIndex = 3;
            this.panelRecenzije.WrapContents = false;
            // 
            // helpProvider
            // 
            this.helpProvider.HelpNamespace = "";
            this.helpProvider.HelpNamespace = "C:\\Users\\Sven\\Desktop\\Projekt PI\\r20--spetrovic-emihalic-tperisa\\Software\\Projekt" +
    " Aurora\\Aurora.chm";
            // 
            // FrmRecenzije
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.Black;
            this.ClientSize = new System.Drawing.Size(302, 509);
            this.Controls.Add(this.panelRecenzije);
            this.Controls.Add(this.btnNazad);
            this.Controls.Add(this.labelNazivFilma);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.None;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "FrmRecenzije";
            this.helpProvider.SetShowHelp(this, true);
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "Recenzije";
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Label labelNazivFilma;
        private System.Windows.Forms.Button btnNazad;
        private System.Windows.Forms.FlowLayoutPanel panelRecenzije;
        private System.Windows.Forms.HelpProvider helpProvider;
    }
}