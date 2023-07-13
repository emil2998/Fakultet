using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;


namespace Projekt_Aurora
{
    public class Projekcija
    {
        public int Id { get; set; }
        public string Vrijeme { get; set; }
        public int Iznos { get; set; }
        public int Id_dvorana { get; set; }
        public int Id_film { get; set; }


        public string Datum { get; set; }

        public string NazivDvorane { get; set; }
        public string NazivFilma { get; set; }

        public string NazivKina { get; set; }

        public int IDKina { get; set; }




        public Projekcija()
        {
        }
       

    }
}
