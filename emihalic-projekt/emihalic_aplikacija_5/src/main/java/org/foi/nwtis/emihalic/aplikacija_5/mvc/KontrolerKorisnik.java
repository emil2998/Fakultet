/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package org.foi.nwtis.emihalic.aplikacija_5.mvc;


import org.foi.nwtis.emihalic.aplikacija_5.ws.WsKorisnici.endpoint.Korisnici;
import org.foi.nwtis.emihalic.aplikacija_5.ws.WsKorisnici.endpoint.Korisnik;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.mvc.View;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.xml.ws.WebServiceRef;

@Controller
@Path("korisnici")
@RequestScoped
public class KontrolerKorisnik {
  @WebServiceRef(wsdlLocation = "http://localhost:8080/emihalic_aplikacija_4/korisnici?wsdl")
  private Korisnici korisnikServis;

  @Inject
  ServletContext context;

  @Inject
  private Models model;

  @GET
  @Path("/korisniciMenu")
  @View("korisniciMenu.jsp")
  public void korisniciMenu() {

  }

  @GET
  @Path("/prijava")
  @View("prijava.jsp")
  public void prijava() {
    String korime = (String) context.getAttribute("korime");
    String lozinka = (String) context.getAttribute("lozinka");
    if (korime != null && lozinka != null) {
      model.put("prijava", true);
    } else {
      model.put("prijava", false);
    }
  }

  @POST
  @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
  @Path("/prijavi")
  @View("prijavi.jsp")
  public void prijavi(@FormParam("korime") String korime, @FormParam("lozinka") String lozinka) {
    var port = korisnikServis.getWsKorisniciPort();
    var odgovor = port.dajKorisnika(korime, lozinka, korime);
    if (odgovor != null) {
      context.setAttribute("korime", korime);
      context.setAttribute("lozinka", lozinka);
      model.put("odgovor", true);
    } else {
      model.put("odgovor", false);
    }
  }

  @GET
  @Path("/odjava")
  @View("odjava.jsp")
  public void odjava() {
    context.removeAttribute("korime");
    context.removeAttribute("lozinka");
  }

  @GET
  @Path("/registracija")
  @View("registracija.jsp")
  public void registracija() {

  }

  @POST
  @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
  @Path("/registriraj")
  @View("registriraj.jsp")
  public void registriraj(@FormParam("korime") String korime, @FormParam("lozinka") String lozinka,
      @FormParam("ime") String ime, @FormParam("prezime") String prezime) {
    var port = korisnikServis.getWsKorisniciPort();
    Korisnik noviKorisnik = new Korisnik();
    noviKorisnik.setKorime(korime);
    noviKorisnik.setLozinka(lozinka);
    noviKorisnik.setIme(ime);
    noviKorisnik.setPrezime(prezime);
    var odgovor = port.dodajKorisnika(noviKorisnik);
    if (odgovor) {
      model.put("odgovor", true);
    } else {
      model.put("odgovor", false);
    }
  }

  @POST
  @Path("/pregledajKorisnike")
  @View("pregledajKorisnike.jsp")
  public void pregledajKorisnike(@FormParam("ime") String ime,
      @FormParam("prezime") String prezime) {
    String korime = (String) context.getAttribute("korime");
    String lozinka = (String) context.getAttribute("lozinka");
    if (korime != null && lozinka != null) {
      var port = korisnikServis.getWsKorisniciPort();
      var odgovor = port.dajKorisnike(korime, lozinka, ime, prezime);
      model.put("odgovor", odgovor);
      model.put("prijava", true);
    } else {
      model.put("prijava", false);
    }

  }

  @GET
  @Path("/pregledKorisnika")
  @View("pregledKorisnika.jsp")
  public void pregledKorisnika() {
    String korime = (String) context.getAttribute("korime");
    String lozinka = (String) context.getAttribute("lozinka");
    if (korime != null && lozinka != null) {
      model.put("prijava", true);
    } else {
      model.put("prijava", false);
    }

  }


}
