namespace Projekt_Aurora
{
    partial class UCKorisnickiKalendar
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
            this.dgvKalendar = new System.Windows.Forms.DataGridView();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.rbtnBrojuSjedala = new System.Windows.Forms.RadioButton();
            this.rbtnDatum = new System.Windows.Forms.RadioButton();
            this.rbtnStatusUplate = new System.Windows.Forms.RadioButton();
            this.rbtnFilmu = new System.Windows.Forms.RadioButton();
            this.label1 = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.dgvKalendar)).BeginInit();
            this.groupBox1.SuspendLayout();
            this.SuspendLayout();
            // 
            // dgvKalendar
            // 
            this.dgvKalendar.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.dgvKalendar.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.Fill;
            this.dgvKalendar.BackgroundColor = System.Drawing.Color.FromArgb(((int)(((byte)(33)))), ((int)(((byte)(33)))), ((int)(((byte)(33)))));
            this.dgvKalendar.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.dgvKalendar.CellBorderStyle = System.Windows.Forms.DataGridViewCellBorderStyle.SunkenHorizontal;
            this.dgvKalendar.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            dataGridViewCellStyle1.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
            dataGridViewCellStyle1.BackColor = System.Drawing.Color.White;
            dataGridViewCellStyle1.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            dataGridViewCellStyle1.ForeColor = System.Drawing.SystemColors.ControlText;
            dataGridViewCellStyle1.SelectionBackColor = System.Drawing.Color.FromArgb(((int)(((byte)(215)))), ((int)(((byte)(12)))), ((int)(((byte)(42)))));
            dataGridViewCellStyle1.SelectionForeColor = System.Drawing.SystemColors.HighlightText;
            dataGridViewCellStyle1.WrapMode = System.Windows.Forms.DataGridViewTriState.False;
            this.dgvKalendar.DefaultCellStyle = dataGridViewCellStyle1;
            this.dgvKalendar.GridColor = System.Drawing.Color.FromArgb(((int)(((byte)(33)))), ((int)(((byte)(33)))), ((int)(((byte)(33)))));
            this.dgvKalendar.Location = new System.Drawing.Point(181, 409);
            this.dgvKalendar.Name = "dgvKalendar";
            this.dgvKalendar.ReadOnly = true;
            this.dgvKalendar.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
            this.dgvKalendar.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.dgvKalendar.Size = new System.Drawing.Size(850, 432);
            this.dgvKalendar.TabIndex = 14;
            // 
            // groupBox1
            // 
            this.groupBox1.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.groupBox1.Controls.Add(this.rbtnBrojuSjedala);
            this.groupBox1.Controls.Add(this.rbtnDatum);
            this.groupBox1.Controls.Add(this.rbtnStatusUplate);
            this.groupBox1.Controls.Add(this.rbtnFilmu);
            this.groupBox1.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.groupBox1.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.groupBox1.ForeColor = System.Drawing.Color.White;
            this.groupBox1.Location = new System.Drawing.Point(288, 256);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(671, 118);
            this.groupBox1.TabIndex = 16;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Filtriraj po:";
            // 
            // rbtnBrojuSjedala
            // 
            this.rbtnBrojuSjedala.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.rbtnBrojuSjedala.AutoSize = true;
            this.rbtnBrojuSjedala.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.rbtnBrojuSjedala.Location = new System.Drawing.Point(428, 47);
            this.rbtnBrojuSjedala.Name = "rbtnBrojuSjedala";
            this.rbtnBrojuSjedala.Size = new System.Drawing.Size(211, 24);
            this.rbtnBrojuSjedala.TabIndex = 3;
            this.rbtnBrojuSjedala.TabStop = true;
            this.rbtnBrojuSjedala.Text = "Rezerviranim sjedalima";
            this.rbtnBrojuSjedala.UseVisualStyleBackColor = true;
            this.rbtnBrojuSjedala.CheckedChanged += new System.EventHandler(this.rbtnBrojuSjedala_CheckedChanged);
            // 
            // rbtnDatum
            // 
            this.rbtnDatum.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.rbtnDatum.AutoSize = true;
            this.rbtnDatum.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.rbtnDatum.Location = new System.Drawing.Point(145, 47);
            this.rbtnDatum.Name = "rbtnDatum";
            this.rbtnDatum.Size = new System.Drawing.Size(90, 24);
            this.rbtnDatum.TabIndex = 2;
            this.rbtnDatum.TabStop = true;
            this.rbtnDatum.Text = "Datumu";
            this.rbtnDatum.UseVisualStyleBackColor = true;
            this.rbtnDatum.CheckedChanged += new System.EventHandler(this.rbtnDatum_CheckedChanged);
            // 
            // rbtnStatusUplate
            // 
            this.rbtnStatusUplate.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.rbtnStatusUplate.AutoSize = true;
            this.rbtnStatusUplate.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.rbtnStatusUplate.Location = new System.Drawing.Point(257, 47);
            this.rbtnStatusUplate.Name = "rbtnStatusUplate";
            this.rbtnStatusUplate.Size = new System.Drawing.Size(145, 24);
            this.rbtnStatusUplate.TabIndex = 1;
            this.rbtnStatusUplate.TabStop = true;
            this.rbtnStatusUplate.Text = "Statusu uplate";
            this.rbtnStatusUplate.UseVisualStyleBackColor = true;
            this.rbtnStatusUplate.CheckedChanged += new System.EventHandler(this.rbtnStatusUplate_CheckedChanged);
            // 
            // rbtnFilmu
            // 
            this.rbtnFilmu.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.rbtnFilmu.AutoSize = true;
            this.rbtnFilmu.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.rbtnFilmu.Location = new System.Drawing.Point(34, 47);
            this.rbtnFilmu.Name = "rbtnFilmu";
            this.rbtnFilmu.Size = new System.Drawing.Size(70, 24);
            this.rbtnFilmu.TabIndex = 0;
            this.rbtnFilmu.TabStop = true;
            this.rbtnFilmu.Text = "Filmu";
            this.rbtnFilmu.UseVisualStyleBackColor = true;
            this.rbtnFilmu.CheckedChanged += new System.EventHandler(this.rbtnFilmu_CheckedChanged);
            // 
            // label1
            // 
            this.label1.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.label1.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(33)))), ((int)(((byte)(33)))), ((int)(((byte)(33)))));
            this.label1.Font = new System.Drawing.Font("Cooper Black", 50F);
            this.label1.ForeColor = System.Drawing.Color.WhiteSmoke;
            this.label1.Location = new System.Drawing.Point(168, 0);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(911, 64);
            this.label1.TabIndex = 103;
            this.label1.Text = "KORISNIČKI KALENDAR";
            // 
            // UCKorisnickiKalendar
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.label1);
            this.Controls.Add(this.groupBox1);
            this.Controls.Add(this.dgvKalendar);
            this.Name = "UCKorisnickiKalendar";
            this.Size = new System.Drawing.Size(1251, 1050);
            this.Load += new System.EventHandler(this.UCKorisnickiKalendar_Load);
            ((System.ComponentModel.ISupportInitialize)(this.dgvKalendar)).EndInit();
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.DataGridView dgvKalendar;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.RadioButton rbtnBrojuSjedala;
        private System.Windows.Forms.RadioButton rbtnDatum;
        private System.Windows.Forms.RadioButton rbtnStatusUplate;
        private System.Windows.Forms.RadioButton rbtnFilmu;
        private System.Windows.Forms.Label label1;
    }
}
