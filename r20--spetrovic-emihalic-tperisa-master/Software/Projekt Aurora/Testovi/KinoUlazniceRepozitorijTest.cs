using System;
using System.Collections.Generic;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Projekt_Aurora;
namespace Testovi
{
    [TestClass]
    public class KinoUlazniceRepozitorijTest
    {
        [TestMethod]
        public void DohvatiSjedala_ZauzetaSjedala()
        {
            Raspored raspored = new Raspored();
            raspored.IDprojekcije = 1;
            raspored.Iznos = 40;
            raspored.NazivDvorane="Ime dvorane";
            raspored.NazivFilma = "Ime filma";
            raspored.TrajanjeFilma = "1h:45min";
            raspored.Vrijeme = "17:00:00";

            List<int> lista = new List<int>();
            lista =KinoUlazniceRepozitorij.DohvatiSjedala(raspored);

            //Assert
            Assert.IsTrue(lista.Count == 0);



        }

        [TestMethod]

        public void DohvatiKina_PopisKina()
        {
            Kino kina = new Kino();
            kina.ID = 1;
            kina.Naziv = "Kino 1";
            kina.Adresa = "Adresa Kina";
        
            List<Kino> lista = new List<Kino>();
            lista = KinoRepozitorij.DohvatiKina();

            //Assert
            Assert.IsTrue(lista.Count != 0);



        }
    }
}
