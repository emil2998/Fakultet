namespace Projekt_Aurora
{
    partial class UCBazaDodjeliZanr
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
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle1 = new System.Windows.Forms.DataGridViewCellStyle();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle2 = new System.Windows.Forms.DataGridViewCellStyle();
            this.btnGotovo = new System.Windows.Forms.Button();
            this.btnIzbaci = new System.Windows.Forms.Button();
            this.btnDodaj = new System.Windows.Forms.Button();
            this.dgvDodjeljeniZanrovi = new System.Windows.Forms.DataGridView();
            this.dgvSviZanrovi = new System.Windows.Forms.DataGridView();
            ((System.ComponentModel.ISupportInitialize)(this.dgvDodjeljeniZanrovi)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dgvSviZanrovi)).BeginInit();
            this.SuspendLayout();
            // 
            // btnGotovo
            // 
            this.btnGotovo.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(215)))), ((int)(((byte)(12)))), ((int)(((byte)(42)))));
            this.btnGotovo.FlatAppearance.BorderSize = 0;
            this.btnGotovo.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnGotovo.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnGotovo.ForeColor = System.Drawing.Color.WhiteSmoke;
            this.btnGotovo.Location = new System.Drawing.Point(236, 483);
            this.btnGotovo.Name = "btnGotovo";
            this.btnGotovo.Size = new System.Drawing.Size(101, 64);
            this.btnGotovo.TabIndex = 18;
            this.btnGotovo.Text = "Gotovo";
            this.btnGotovo.UseVisualStyleBackColor = false;
            this.btnGotovo.Click += new System.EventHandler(this.btnOdustani_Click);
            // 
            // btnIzbaci
            // 
            this.btnIzbaci.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(215)))), ((int)(((byte)(12)))), ((int)(((byte)(42)))));
            this.btnIzbaci.FlatAppearance.BorderSize = 0;
            this.btnIzbaci.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnIzbaci.Font = new System.Drawing.Font("Microsoft Sans Serif", 14.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnIzbaci.ForeColor = System.Drawing.Color.WhiteSmoke;
            this.btnIzbaci.Location = new System.Drawing.Point(249, 128);
            this.btnIzbaci.Name = "btnIzbaci";
            this.btnIzbaci.Size = new System.Drawing.Size(75, 51);
            this.btnIzbaci.TabIndex = 26;
            this.btnIzbaci.Text = ">>";
            this.btnIzbaci.UseVisualStyleBackColor = false;
            this.btnIzbaci.Click += new System.EventHandler(this.btnIzbaci_Click);
            // 
            // btnDodaj
            // 
            this.btnDodaj.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(215)))), ((int)(((byte)(12)))), ((int)(((byte)(42)))));
            this.btnDodaj.FlatAppearance.BorderSize = 0;
            this.btnDodaj.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnDodaj.Font = new System.Drawing.Font("Microsoft Sans Serif", 14.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnDodaj.ForeColor = System.Drawing.Color.WhiteSmoke;
            this.btnDodaj.Location = new System.Drawing.Point(249, 185);
            this.btnDodaj.Name = "btnDodaj";
            this.btnDodaj.Size = new System.Drawing.Size(75, 51);
            this.btnDodaj.TabIndex = 25;
            this.btnDodaj.Text = "<<";
            this.btnDodaj.UseVisualStyleBackColor = false;
            this.btnDodaj.Click += new System.EventHandler(this.btnDodaj_Click);
            // 
            // dgvDodjeljeniZanrovi
            // 
            this.dgvDodjeljeniZanrovi.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.dgvDodjeljeniZanrovi.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.Fill;
            this.dgvDodjeljeniZanrovi.BackgroundColor = System.Drawing.Color.FromArgb(((int)(((byte)(33)))), ((int)(((byte)(33)))), ((int)(((byte)(33)))));
            this.dgvDodjeljeniZanrovi.BorderStyle = System.Windows.Forms.BorderStyle.None;
            this.dgvDodjeljeniZanrovi.CellBorderStyle = System.Windows.Forms.DataGridViewCellBorderStyle.SunkenHorizontal;
            this.dgvDodjeljeniZanrovi.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            dataGridViewCellStyle1.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
            dataGridViewCellStyle1.BackColor = System.Drawing.Color.White;
            dataGridViewCellStyle1.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            dataGridViewCellStyle1.ForeColor = System.Drawing.SystemColors.ControlText;
            dataGridViewCellStyle1.SelectionBackColor = System.Drawing.Color.FromArgb(((int)(((byte)(215)))), ((int)(((byte)(12)))), ((int)(((byte)(42)))));
            dataGridViewCellStyle1.SelectionForeColor = System.Drawing.SystemColors.HighlightText;
            dataGridViewCellStyle1.WrapMode = System.Windows.Forms.DataGridViewTriState.False;
            this.dgvDodjeljeniZanrovi.DefaultCellStyle = dataGridViewCellStyle1;
            this.dgvDodjeljeniZanrovi.GridColor = System.Drawing.Color.FromArgb(((int)(((byte)(33)))), ((int)(((byte)(33)))), ((int)(((byte)(33)))));
            this.dgvDodjeljeniZanrovi.Location = new System.Drawing.Point(3, 3);
            this.dgvDodjeljeniZanrovi.Name = "dgvDodjeljeniZanrovi";
            this.dgvDodjeljeniZanrovi.ReadOnly = true;
            this.dgvDodjeljeniZanrovi.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
            this.dgvDodjeljeniZanrovi.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.dgvDodjeljeniZanrovi.Size = new System.Drawing.Size(240, 352);
            this.dgvDodjeljeniZanrovi.TabIndex = 27;
            // 
            // dgvSviZanrovi
            // 
            this.dgvSviZanrovi.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.dgvSviZanrovi.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.Fill;
            this.dgvSviZanrovi.BackgroundColor = System.Drawing.Color.FromArgb(((int)(((byte)(33)))), ((int)(((byte)(33)))), ((int)(((byte)(33)))));
            this.dgvSviZanrovi.BorderStyle = System.Windows.Forms.BorderStyle.None;
            this.dgvSviZanrovi.CellBorderStyle = System.Windows.Forms.DataGridViewCellBorderStyle.SunkenHorizontal;
            this.dgvSviZanrovi.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            dataGridViewCellStyle2.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
            dataGridViewCellStyle2.BackColor = System.Drawing.Color.White;
            dataGridViewCellStyle2.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            dataGridViewCellStyle2.ForeColor = System.Drawing.SystemColors.ControlText;
            dataGridViewCellStyle2.SelectionBackColor = System.Drawing.Color.FromArgb(((int)(((byte)(215)))), ((int)(((byte)(12)))), ((int)(((byte)(42)))));
            dataGridViewCellStyle2.SelectionForeColor = System.Drawing.SystemColors.HighlightText;
            dataGridViewCellStyle2.WrapMode = System.Windows.Forms.DataGridViewTriState.False;
            this.dgvSviZanrovi.DefaultCellStyle = dataGridViewCellStyle2;
            this.dgvSviZanrovi.GridColor = System.Drawing.Color.FromArgb(((int)(((byte)(33)))), ((int)(((byte)(33)))), ((int)(((byte)(33)))));
            this.dgvSviZanrovi.Location = new System.Drawing.Point(329, 3);
            this.dgvSviZanrovi.Name = "dgvSviZanrovi";
            this.dgvSviZanrovi.ReadOnly = true;
            this.dgvSviZanrovi.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
            this.dgvSviZanrovi.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.dgvSviZanrovi.Size = new System.Drawing.Size(240, 352);
            this.dgvSviZanrovi.TabIndex = 28;
            // 
            // UCBazaDodjeliZanr
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.Black;
            this.Controls.Add(this.dgvSviZanrovi);
            this.Controls.Add(this.dgvDodjeljeniZanrovi);
            this.Controls.Add(this.btnIzbaci);
            this.Controls.Add(this.btnDodaj);
            this.Controls.Add(this.btnGotovo);
            this.Name = "UCBazaDodjeliZanr";
            this.Size = new System.Drawing.Size(572, 563);
            ((System.ComponentModel.ISupportInitialize)(this.dgvDodjeljeniZanrovi)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dgvSviZanrovi)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Button btnGotovo;
        private System.Windows.Forms.Button btnIzbaci;
        private System.Windows.Forms.Button btnDodaj;
        private System.Windows.Forms.DataGridView dgvDodjeljeniZanrovi;
        private System.Windows.Forms.DataGridView dgvSviZanrovi;
    }
}
