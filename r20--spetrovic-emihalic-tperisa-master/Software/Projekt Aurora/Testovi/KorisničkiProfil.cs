using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Projekt_Aurora;
using System.Collections.Generic;
namespace Testovi
{
    [TestClass]
    public class KorisničkiProfil
    {
        [TestMethod]
        public void DohvaćanjeKorisnika_AžuriranjePodatakaUBazi_AžuriranjeUspješno()
        {
            //Arange
            List<Korisnik> lista = KorisnikRepozitorij.DohvatiKorisnike();
            Korisnik korisnik = lista[2];
            string pocetniEmail = korisnik.Email;
            korisnik.Email = "mojEmail@email.email";

            //Act
            KorisnikRepozitorij.AžurirajKorisnika(korisnik);
            lista = KorisnikRepozitorij.DohvatiKorisnike();
            korisnik = lista[2];
            //Assert
            Assert.IsFalse(pocetniEmail == korisnik.Email);
        }

    }
}
