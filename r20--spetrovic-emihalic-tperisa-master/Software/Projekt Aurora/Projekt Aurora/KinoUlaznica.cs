using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Projekt_Aurora
{
    public class KinoUlaznica
    {
        public int ID { get; set; }

        public int IDProjekcije { get; set; }

        public int IDKorisnik { get; set; }

        public int StatusUplate { get; set; }

        public int BrojSjedala { get; set; }

        public string VrijemeUplate { get; set; }

        public int StatusRezervacija { get; set; }

        public string VrijemeIzdavanja { get; set; }


        public KinoUlaznica()
        {
        }

    }
}
