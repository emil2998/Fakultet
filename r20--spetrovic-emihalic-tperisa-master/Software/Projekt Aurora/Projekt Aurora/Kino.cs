using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Projekt_Aurora
{
    public class Kino
    {
        public int ID { get; set; }
        public string Naziv { get; set; }
        public string Adresa { get; set; }

        public Kino()
        {

        }

        public override string ToString()
        {
            return Naziv;
        }
    }
}
