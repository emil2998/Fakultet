using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Projekt_Aurora
{
    public class UkupnaStatistika
    {
        public decimal Iznos { get; set; }
        public int broj_sjedala { get; set; }
        public decimal Ukupna { get; set; }
    }

    public class Statistika1
    {
        public Kino Kino { get; set; }

        public decimal ProfitZaFilm { get; set; }

        public decimal OcekivaniProfit { get; set; }

        public decimal Profitdrugi { get; set; }
        public decimal Ocekivanidrugi { get; set; }
    }
}
