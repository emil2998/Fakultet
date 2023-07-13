using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Projekt_Aurora;
using System.Collections.Generic;
namespace Testovi
{
    [TestClass]
    public class Recenziranje
    {
        [TestMethod]
        public void DohvatiRecenzije_NepostojećiFilmUBazi_MetodaVraćaPraznuListu()
        {
            //Arange
            UlogiraniKorisnik.IdUloga = 1;
            Film film = new Film();
            film.Godina = 2019;
            film.Naziv = "Naziv testnog filma";
            film.Opis = "loš film";
            film.Redatelj = "Hans Šmans";
            film.Trajanje = "2h:30min";

            //Act
            List<Recenzija> lista = new List<Recenzija>();
            lista = RecenzijaRepozitorij.DohvatiRecenzije(film);

            //Assert
            Assert.IsTrue(lista.Count == 0);
        }

        [TestMethod]
        public void SpremiRecenziju_ListaRecenzijaNekogFilmaUBaziPrijeSpremanja_NovaListaSJednomRecenzijomViše()
        {
            //Arange
            List<Film> listaFilmova = new List<Film>();
            listaFilmova = FilmRepozitorij.DohvatiFilmove();
            List<Recenzija> pocetnaLista = new List<Recenzija>();
            pocetnaLista = RecenzijaRepozitorij.DohvatiRecenzije(listaFilmova[1]);

            //Act
            Recenzija recenzija = new Recenzija();
            recenzija.IdFilm = listaFilmova[1].ID;
            recenzija.IdKorisnik = 27;
            recenzija.Ime = "Zack";
            recenzija.Prezime = "Rawr";
            recenzija.Ocijena = 5;
            recenzija.Komentar = "Odličan film";
            RecenzijaRepozitorij.SpremiRecenziju(recenzija,listaFilmova[1]);
            List<Recenzija> novaLista = new List<Recenzija>();
            novaLista = RecenzijaRepozitorij.DohvatiRecenzije(listaFilmova[1]);

            //Assert
            Assert.IsTrue(novaLista.Count == pocetnaLista.Count+1);
        }
    }
}
