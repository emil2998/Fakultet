/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package org.foi.nwtis.emihalic.aplikacija_5.mvc;

import org.foi.nwtis.Konfiguracija;
import org.foi.nwtis.emihalic.aplikacija_5.rest.RestKlijentNadzora;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.mvc.View;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;

@Controller
@Path("nadzor")
@RequestScoped
public class KontrolerNadzor {

  @Context
  ServletContext context;

  @Inject
  private Models model;

  @GET
  @Path("/index")
  @View("index.jsp")
  public void index() {

  }

  @GET
  @Path("/nadzorMenu")
  @View("nadzorMenu.jsp")
  public void nadzorMenu() {

  }

  @GET
  @Path("/nadzor")
  @View("nadzor.jsp")
  public void nadzor(@DefaultValue("") @QueryParam("naredba") String naredba) {
    Konfiguracija konfig = (Konfiguracija) context.getAttribute("konfig");
    System.out.println(naredba);
    String odgovor = "";
    if (!naredba.isEmpty()) {
      RestKlijentNadzora rkn = new RestKlijentNadzora(konfig);
      if (naredba.equals("STATUS")) {
        odgovor = rkn.getStatus();
      } else if (naredba.equals("KRAJ") || naredba.equals("PAUZA") || naredba.equals("INIT")) {
        odgovor = rkn.getNaredba(naredba);
      } else if (naredba.equals("INFODA")) {
        odgovor = rkn.getInfo("DA");
      } else if (naredba.equals("INFONE")) {
        odgovor = rkn.getInfo("NE");
      }
    }
    model.put("odgovor", odgovor);
  }


}
