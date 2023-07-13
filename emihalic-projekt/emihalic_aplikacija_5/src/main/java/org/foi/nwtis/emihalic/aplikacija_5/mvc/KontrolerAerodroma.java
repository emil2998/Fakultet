/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package org.foi.nwtis.emihalic.aplikacija_5.mvc;


import org.foi.nwtis.KonfiguracijaBP;
import org.foi.nwtis.emihalic.aplikacija_5.rest.RestKlijentAerodroma;
import org.foi.nwtis.emihalic.aplikacija_5.slusaci.SlusacAplikacije;
import org.foi.nwtis.emihalic.aplikacija_5.ws.WsMeteo.endpoint.Meteo;
import org.foi.nwtis.podaci.Udaljenost;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.mvc.View;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.xml.ws.WebServiceRef;

@Controller
@Path("aerodromi")
@RequestScoped
public class KontrolerAerodroma {

  @WebServiceRef(wsdlLocation = "http://localhost:8080/emihalic_aplikacija_4/meteo?wsdl")
  private Meteo meteoServis;

  @Inject
  private Models model;

  /**
   * Prikazuje početnu stranicu.
   */
  @GET
  @Path("aerodromiMenu")
  @View("aerodromiMenu.jsp")
  public void pocetak() {}

  /**
   * Prikazuje stranicu sa svim aerodromima.
   *
   * @param brojStranice broj stranice
   */
  @GET
  @Path("aerodromi")
  @View("aerodromi.jsp")
  public void getAerodromi(@DefaultValue("") @QueryParam("traziNaziv") String traziNaziv,
      @DefaultValue("") @QueryParam("traziDrzavu") String traziDrzavu,
      @DefaultValue("1") @QueryParam("brojStranice") int brojStranice) {
    KonfiguracijaBP konfig = (KonfiguracijaBP) SlusacAplikacije.context.getAttribute("konfig");
    int broj = Integer.parseInt(konfig.dajPostavku("stranica.brojRedova"));
    try {
      RestKlijentAerodroma rca = new RestKlijentAerodroma(konfig);
      var aerodromi = rca.getAerodromi(brojStranice, broj, traziNaziv, traziDrzavu);
      model.put("aerodromi", aerodromi);
      model.put("brojStranice", brojStranice);
      model.put("traziNaziv", traziNaziv);
      model.put("traziDrzavu", traziDrzavu);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Prikazuje stranicu sa detaljima o odabranom aerodromu.
   *
   * @param icao ICAO kod aerodroma
   */
  @GET
  @Path("{icao}")
  @View("aerodrom.jsp")
  public void getAerodrom(@PathParam("icao") String icao) {
    try {
      KonfiguracijaBP konfig = (KonfiguracijaBP) SlusacAplikacije.context.getAttribute("konfig");
      RestKlijentAerodroma rca = new RestKlijentAerodroma(konfig);
      var aerodrom = rca.getAerodrom(icao);
      if (aerodrom != null) {
        var port = meteoServis.getWsMeteoPort();
        var meteo = port.dajMeteo(icao);
        model.put("aerodrom", aerodrom);
        model.put("meteo", meteo);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  @GET
  @Path("obrazacDvaIcao")
  @View("obrazacDvaIcao.jsp")
  public void getUdaljenosti() {}

  @GET
  @Path("udaljenosti")
  @View("udaljenostiDvaIcao.jsp")
  public void getUdaljenosti(@QueryParam("icaoOd") String icaoOd,
      @QueryParam("icaoDo") String icaoDo) {
    try {
      KonfiguracijaBP konfig = (KonfiguracijaBP) SlusacAplikacije.context.getAttribute("konfig");
      RestKlijentAerodroma rca = new RestKlijentAerodroma(konfig);
      var udaljenosti = rca.getUdaljenosti(icaoOd, icaoDo);
      float ukupno = 0;
      for (Udaljenost u : udaljenosti) {
        ukupno += u.km();
      }
      model.put("udaljenosti", udaljenosti);
      model.put("ukupno", ukupno);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @GET
  @Path("obrazacIzracunaj")
  @View("obrazacIzracunaj.jsp")
  public void getUdaljenost() {}

  @GET
  @Path("izracunaj")
  @View("izracunajDvaIcao.jsp")
  public void getUdaljenost(@QueryParam("icaoOd") String icaoOd,
      @QueryParam("icaoDo") String icaoDo) {
    try {
      KonfiguracijaBP konfig = (KonfiguracijaBP) SlusacAplikacije.context.getAttribute("konfig");
      RestKlijentAerodroma rca = new RestKlijentAerodroma(konfig);
      var udaljenost = rca.getUdaljenost(icaoOd, icaoDo);
      model.put("udaljenost", udaljenost);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }



}
