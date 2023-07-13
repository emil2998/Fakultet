namespace VanjskeBiblioteke
{
    partial class GrafPrikaz
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
            System.Windows.Forms.DataVisualization.Charting.ChartArea chartArea1 = new System.Windows.Forms.DataVisualization.Charting.ChartArea();
            System.Windows.Forms.DataVisualization.Charting.Legend legend1 = new System.Windows.Forms.DataVisualization.Charting.Legend();
            System.Windows.Forms.DataVisualization.Charting.Series series1 = new System.Windows.Forms.DataVisualization.Charting.Series();
            System.Windows.Forms.DataVisualization.Charting.DataPoint dataPoint1 = new System.Windows.Forms.DataVisualization.Charting.DataPoint(0D, 5D);
            System.Windows.Forms.DataVisualization.Charting.DataPoint dataPoint2 = new System.Windows.Forms.DataVisualization.Charting.DataPoint(1D, 10D);
            System.Windows.Forms.DataVisualization.Charting.DataPoint dataPoint3 = new System.Windows.Forms.DataVisualization.Charting.DataPoint(6D, 44D);
            this.chrtGraf = new System.Windows.Forms.DataVisualization.Charting.Chart();
            ((System.ComponentModel.ISupportInitialize)(this.chrtGraf)).BeginInit();
            this.SuspendLayout();
            // 
            // chrtGraf
            // 
            this.chrtGraf.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.chrtGraf.BackColor = System.Drawing.Color.Transparent;
            this.chrtGraf.BackImageAlignment = System.Windows.Forms.DataVisualization.Charting.ChartImageAlignmentStyle.Left;
            this.chrtGraf.BorderlineColor = System.Drawing.Color.Gray;
            chartArea1.Name = "ChartArea1";
            this.chrtGraf.ChartAreas.Add(chartArea1);
            legend1.Name = "Legend1";
            this.chrtGraf.Legends.Add(legend1);
            this.chrtGraf.Location = new System.Drawing.Point(-9, 0);
            this.chrtGraf.Name = "chrtGraf";
            this.chrtGraf.Palette = System.Windows.Forms.DataVisualization.Charting.ChartColorPalette.None;
            this.chrtGraf.PaletteCustomColors = new System.Drawing.Color[] {
        System.Drawing.Color.FromArgb(((int)(((byte)(215)))), ((int)(((byte)(12)))), ((int)(((byte)(42))))),
        System.Drawing.Color.FromArgb(((int)(((byte)(116)))), ((int)(((byte)(116)))), ((int)(((byte)(116)))))};
            series1.BackGradientStyle = System.Windows.Forms.DataVisualization.Charting.GradientStyle.TopBottom;
            series1.ChartArea = "ChartArea1";
            series1.Legend = "Legend1";
            series1.Name = "Series1";
            series1.Points.Add(dataPoint1);
            series1.Points.Add(dataPoint2);
            series1.Points.Add(dataPoint3);
            this.chrtGraf.Series.Add(series1);
            this.chrtGraf.Size = new System.Drawing.Size(428, 439);
            this.chrtGraf.TabIndex = 0;
            this.chrtGraf.Text = "chart1";
            // 
            // GrafPrikaz
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.Transparent;
            this.Controls.Add(this.chrtGraf);
            this.Name = "GrafPrikaz";
            this.Size = new System.Drawing.Size(434, 445);
            ((System.ComponentModel.ISupportInitialize)(this.chrtGraf)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.DataVisualization.Charting.Chart chrtGraf;
    }
}
