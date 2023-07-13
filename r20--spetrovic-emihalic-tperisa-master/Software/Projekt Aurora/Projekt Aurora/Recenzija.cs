using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Projekt_Aurora
{
    public class Recenzija
    {

        public string Ime { get; set; }
        public string Prezime { get; set; }
        public decimal Ocijena { get; set; }
        public string Komentar { get; set; }

        public int IdFilm { get; set; }
        public int IdKorisnik { get; set; }


        public Recenzija()
        {

        }
    }
}
