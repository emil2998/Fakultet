namespace Projekt_Aurora
{
    partial class UCRecenzija
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
            this.labelImeIPrezime = new System.Windows.Forms.Label();
            this.labelOcjena = new System.Windows.Forms.Label();
            this.txtKomentar = new System.Windows.Forms.RichTextBox();
            this.SuspendLayout();
            // 
            // labelImeIPrezime
            // 
            this.labelImeIPrezime.AutoSize = true;
            this.labelImeIPrezime.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.labelImeIPrezime.ForeColor = System.Drawing.Color.WhiteSmoke;
            this.labelImeIPrezime.Location = new System.Drawing.Point(3, 17);
            this.labelImeIPrezime.Name = "labelImeIPrezime";
            this.labelImeIPrezime.Size = new System.Drawing.Size(116, 20);
            this.labelImeIPrezime.TabIndex = 0;
            this.labelImeIPrezime.Text = "Ime i prezime";
            // 
            // labelOcjena
            // 
            this.labelOcjena.AutoSize = true;
            this.labelOcjena.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.labelOcjena.ForeColor = System.Drawing.Color.WhiteSmoke;
            this.labelOcjena.Location = new System.Drawing.Point(175, 17);
            this.labelOcjena.Name = "labelOcjena";
            this.labelOcjena.Size = new System.Drawing.Size(65, 20);
            this.labelOcjena.TabIndex = 1;
            this.labelOcjena.Text = "Ocjena";
            // 
            // txtKomentar
            // 
            this.txtKomentar.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(33)))), ((int)(((byte)(33)))), ((int)(((byte)(33)))));
            this.txtKomentar.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.txtKomentar.ForeColor = System.Drawing.Color.WhiteSmoke;
            this.txtKomentar.Location = new System.Drawing.Point(3, 45);
            this.txtKomentar.Name = "txtKomentar";
            this.txtKomentar.ReadOnly = true;
            this.txtKomentar.Size = new System.Drawing.Size(269, 69);
            this.txtKomentar.TabIndex = 2;
            this.txtKomentar.Text = "";
            // 
            // UCRecenzija
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(33)))), ((int)(((byte)(33)))), ((int)(((byte)(33)))));
            this.Controls.Add(this.txtKomentar);
            this.Controls.Add(this.labelOcjena);
            this.Controls.Add(this.labelImeIPrezime);
            this.Name = "UCRecenzija";
            this.Size = new System.Drawing.Size(275, 121);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label labelImeIPrezime;
        private System.Windows.Forms.Label labelOcjena;
        private System.Windows.Forms.RichTextBox txtKomentar;
    }
}
