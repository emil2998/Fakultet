package org.foi.nwtis.emihalic.aplikacija_2.rest;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.Konfiguracija;
import org.foi.nwtis.podaci.Poruka;
import com.google.gson.Gson;
import jakarta.enterprise.context.RequestScoped;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


/**
 * Klasa za restful servis za nadzor.
 * 
 * @author Emil Mihalić
 * @version 1.0
 */
@Path("nadzor")
@RequestScoped
public class RestNadzor {

  private Gson gson = new Gson();

  @Context
  ServletContext context;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response osnovna() {
    String odgovor = kontaktirajPosluzitelj("STATUS");
    if (odgovor.contains("ERROR") || odgovor.contains("Ne radi")) {
      Poruka poruka = new Poruka(400, odgovor);
      return Response.ok().entity(gson.toJson(poruka)).build();

    }
    Poruka poruka = new Poruka(200, odgovor);
    return Response.ok().entity(gson.toJson(poruka)).build();

  }

  @GET
  @Path("{komanda}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response komanda(@PathParam("komanda") String komanda) {
    String odgovor = kontaktirajPosluzitelj(komanda);
    if (odgovor.contains("ERROR") || odgovor.contains("Ne radi")) {
      Poruka poruka = new Poruka(400, odgovor);
      return Response.ok().entity(gson.toJson(poruka)).build();
    }
    Poruka poruka = new Poruka(200, odgovor);
    return Response.ok().entity(gson.toJson(poruka)).build();
  }

  @GET
  @Path("INFO/{vrsta}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response info(@PathParam("vrsta") String vrsta) {
    String odgovor = kontaktirajPosluzitelj("INFO " + vrsta);
    if (odgovor.contains("ERROR") || odgovor.contains("Ne radi")) {
      Poruka poruka = new Poruka(400, odgovor);
      return Response.ok().entity(gson.toJson(poruka)).build();
    }
    Poruka poruka = new Poruka(200, odgovor);
    return Response.ok().entity(gson.toJson(poruka)).build();
  }

  private String kontaktirajPosluzitelj(String naredba) {
    Konfiguracija konfig = (Konfiguracija) context.getAttribute("konfig");
    String adresa = konfig.dajPostavku("adresa");
    int mreznaVrata = Integer.parseInt(konfig.dajPostavku("mrezna.vrata"));
    System.out.println(adresa);
    try (var socket = new Socket(adresa, mreznaVrata);
        var reader = new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8"));
        var writer = new OutputStreamWriter(socket.getOutputStream(), Charset.forName("UTF-8"));) {
      socket.setSoTimeout(10000);
      writer.write(naredba);
      writer.flush();
      socket.shutdownOutput();
      var sb = new StringBuilder();
      while (true) {
        int slovo = reader.read();
        if (slovo == -1)
          break;
        sb.append((char) slovo);
      }
      socket.shutdownInput();
      socket.close();
      return sb.toString();
    } catch (Exception e) {
      Logger.getGlobal().log(Level.INFO, "Ne radi poslužitelj.");
    }
    return "Ne radi poslužitelj";
  }
}
