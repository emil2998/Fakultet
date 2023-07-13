using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Projekt_Aurora
{
    public class Film
    {
        public int ID { get; set; }
        public string Naziv { get; set; }
        public int Godina { get; set; }
        public string Redatelj { get; set; }
        public string Opis { get; set; }
        public string Trajanje { get; set; }
        public List<string> Zanrovi { get; set; }
        public Film()
        {
            Zanrovi = new List<string>();
        }

        public override string ToString()
        {
            return Naziv;
        }
    }
}

