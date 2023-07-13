using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.IO;

namespace Projekt_Aurora
{
    public partial class FrmUpozorenje : Form
    {
        static string path = Path.Combine(Directory.GetParent(System.IO.Directory.GetCurrentDirectory()).Parent.Parent.FullName);
        string path2 = path + "\\Aurora.chm";
        
        public FrmUpozorenje(string text)
        {
            InitializeComponent();
            txtUpozorenje.SelectionAlignment = HorizontalAlignment.Center;
            txtUpozorenje.Text = text;
            helpProvider.HelpNamespace = path2;
        }
    }
}
