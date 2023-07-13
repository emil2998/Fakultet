using System;
using System.Collections.Generic;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Projekt_Aurora;

namespace Testovi
{
    [TestClass]
    public class DohvacanjeInfoFilma
    {
        [TestMethod]
        public void TestirajRadiLiNullDetekcija()
        {

            try
            {
                // Act
                List<Statistika1> lista = StatistikaRepozitorij.DohvatiPrihodePoKinimaZaFilm(null);

                // Assert
                Assert.Fail();
            }
            catch (ArgumentNullException)
            {
               
            }
        }

        
    }
}
