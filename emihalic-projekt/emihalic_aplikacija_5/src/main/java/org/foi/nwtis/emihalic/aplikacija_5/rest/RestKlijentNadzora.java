package org.foi.nwtis.emihalic.aplikacija_5.rest;

import org.foi.nwtis.Konfiguracija;
import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author Emil Mihalić
 */
public class RestKlijentNadzora {
  private WebTarget webTarget;
  private Client client;

  public RestKlijentNadzora(Konfiguracija konfig) {
    client = ClientBuilder.newClient();
    webTarget = client.target(konfig.dajPostavku("adresa.aplikacija_2")).path("nadzor/");
  }

  public String getStatus() throws ClientErrorException {
    Response odgovor = webTarget.request().header("Accept", "application/json").get();
    return odgovor.readEntity(String.class);
  }

  public String getNaredba(String naredba) throws ClientErrorException {

    webTarget = webTarget.path(naredba);
    Response odgovor = webTarget.request().header("Accept", "application/json").get();
    return odgovor.readEntity(String.class);
  }

  public String getInfo(String vrsta) throws ClientErrorException {
    if (vrsta.equals("DA")) {
      webTarget = webTarget.path("INFO/DA");
    } else if (vrsta.equals("NE")) {
      webTarget = webTarget.path("INFO/NE");
    }
    Response odgovor = webTarget.request().header("Accept", "application/json").get();
    return odgovor.readEntity(String.class);
  }

  /**
   * 
   * Zatvara klijenta koji se koristi za slanje zahtjeva prema REST servisu.
   */
  public void close() {
    client.close();
  }


}
