using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Projekt_Aurora
{
    public class Raspored
    {
        public string NazivFilma { get; set; }
        public string TrajanjeFilma { get; set; }
        public string NazivDvorane { get; set; }
        public string Vrijeme { get; set; }
        public decimal Iznos { get; set; }

        public int IDprojekcije { get; set; }

        

        public Raspored()
        {

        }
    }
}
