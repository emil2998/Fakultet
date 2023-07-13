namespace Projekt_Aurora
{
    partial class FrmUpozorenje
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(FrmUpozorenje));
            this.txtUpozorenje = new System.Windows.Forms.RichTextBox();
            this.helpProvider = new System.Windows.Forms.HelpProvider();
            this.SuspendLayout();
            // 
            // txtUpozorenje
            // 
            this.txtUpozorenje.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.txtUpozorenje.BackColor = System.Drawing.Color.Black;
            this.txtUpozorenje.Font = new System.Drawing.Font("Microsoft Sans Serif", 14F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtUpozorenje.ForeColor = System.Drawing.Color.WhiteSmoke;
            this.txtUpozorenje.Location = new System.Drawing.Point(0, 0);
            this.txtUpozorenje.Name = "txtUpozorenje";
            this.txtUpozorenje.ReadOnly = true;
            this.txtUpozorenje.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.None;
            this.helpProvider.SetShowHelp(this.txtUpozorenje, true);
            this.txtUpozorenje.Size = new System.Drawing.Size(329, 213);
            this.txtUpozorenje.TabIndex = 0;
            this.txtUpozorenje.Text = "";
            // 
            // helpProvider
            // 
            this.helpProvider.HelpNamespace = "";
            this.helpProvider.HelpNamespace = "C:\\Users\\Sven\\Desktop\\Projekt PI\\r20--spetrovic-emihalic-tperisa\\Software\\Projekt" +
    " Aurora\\Aurora.chm";
            // 
            // FrmUpozorenje
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.Black;
            this.ClientSize = new System.Drawing.Size(329, 213);
            this.Controls.Add(this.txtUpozorenje);
            this.Cursor = System.Windows.Forms.Cursors.No;
            this.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.5F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.ForeColor = System.Drawing.Color.WhiteSmoke;
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedToolWindow;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "FrmUpozorenje";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "Upozorenje!";
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.RichTextBox txtUpozorenje;
        private System.Windows.Forms.HelpProvider helpProvider;
    }
}