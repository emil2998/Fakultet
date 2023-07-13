using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Projekt_Aurora
{
    public class Dvorana
    {
        public int ID { get; set; }
        public string Naziv { get; set; }
        public int Broj_redova { get; set; }
        public int Broj_stupaca { get; set; }

        public int Id_kina { get; set; }

        public Kino Kino { get; set; }
        public string NazivKina { get; set; }



        public Dvorana()
        {
        }

        public override string ToString()
        {
            return Naziv + " " + NazivKina;
        }

    }
}
