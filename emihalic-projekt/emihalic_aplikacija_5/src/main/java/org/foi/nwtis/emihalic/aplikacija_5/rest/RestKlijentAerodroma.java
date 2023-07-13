package org.foi.nwtis.emihalic.aplikacija_5.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.foi.nwtis.Konfiguracija;
import org.foi.nwtis.podaci.Aerodrom;
import org.foi.nwtis.podaci.Udaljenost;
import com.google.gson.Gson;
import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author Emil Mihalić
 */
public class RestKlijentAerodroma {
  private Konfiguracija konfig;

  public RestKlijentAerodroma(Konfiguracija konfig) {
    this.konfig = konfig;
  }

  /**
   * 
   * Dohvaća listu aerodroma određenog broja počevši od određenog mjesta na temelju REST API-ja.
   * 
   * @param odBroja početno mjesto do kojeg se dohvaćaju aerodromi
   * @param broj broj aerodroma koji se dohvaća
   * @return lista aerodroma
   */
  public List<Aerodrom> getAerodromi(int odBroja, int broj, String traziNaziv, String traziDrzavu) {
    RestKKlijent rc = new RestKKlijent(konfig);
    Aerodrom[] json_Aerodromi = rc.getAerodromi(odBroja, broj, traziNaziv, traziDrzavu);
    List<Aerodrom> aerodromi;
    if (json_Aerodromi == null) {
      aerodromi = new ArrayList<>();
    } else {
      aerodromi = Arrays.asList(json_Aerodromi);
    }
    rc.close();
    return aerodromi;
  }

  public List<Udaljenost> getUdaljenosti(String icaoOd, String icaoDo) {
    RestKKlijent rc = new RestKKlijent(konfig);
    Udaljenost[] json_udaljenosti = rc.getUdaljenosti(icaoOd, icaoDo);
    List<Udaljenost> udaljenosti;
    if (json_udaljenosti == null) {
      udaljenosti = new ArrayList<>();
    } else {
      udaljenosti = Arrays.asList(json_udaljenosti);
    }
    rc.close();
    return udaljenosti;
  }

  /**
   * 
   * Dohvaća aerodrom na temelju ICAO koda.
   * 
   * @param icao ICAO kod aerodroma koji se želi dohvatiti
   * @return Aerodrom objekt s informacijama o aerodromu
   */
  public Aerodrom getAerodrom(String icao) {
    RestKKlijent rc = new RestKKlijent(konfig);
    Aerodrom k = rc.getAerodrom(icao);
    rc.close();
    return k;
  }

  public String getUdaljenost(String icaoOd, String icaoDo) {
    RestKKlijent rc = new RestKKlijent(konfig);
    String k = rc.getUdaljenost(icaoOd, icaoDo);
    rc.close();
    return k;
  }


  static class RestKKlijent {

    private final WebTarget webTarget;
    private final Client client;
    private static String BASE_URI;

    /**
     * 
     * Inicijalizira novu instancu klase RestKKlijent. Stvara klijenta i postavlja WebTarget na
     * osnovni URI za aerodrome.
     */
    public RestKKlijent(Konfiguracija konfig) {
      BASE_URI = konfig.dajPostavku("adresa.aplikacija_2");
      client = ClientBuilder.newClient();
      webTarget = client.target(BASE_URI).path("aerodromi");
    }

    /**
     * 
     * Dohvaća niz aerodroma počevši od određenog indeksa te s ograničenjem broja rezultata.
     * 
     * @param odBroja početni indeks aerodroma koji se dohvaćaju
     * @param broj ograničenje broja aerodroma koji se dohvaćaju
     * @return niz aerodroma koji su dohvaćeni ili null ako dohvaćanje nije uspješno
     * @throws ClientErrorException ako je došlo do greške u komunikaciji s API-jem
     */
    public Aerodrom[] getAerodromi(int odBroja, int broj, String traziNaziv, String traziDrzavu)
        throws ClientErrorException {
      WebTarget resource =
          webTarget.path("").queryParam("odBroja", odBroja).queryParam("broj", broj)
              .queryParam("traziDrzavu", traziDrzavu).queryParam("traziNaziv", traziNaziv);
      Invocation.Builder request = resource.request(MediaType.APPLICATION_JSON);
      Response response = request.get();
      if (response.getStatus() != 200) {
        return null;
      }
      String responseBody = response.readEntity(String.class);
      Gson gson = new Gson();
      Aerodrom[] aerodromi = gson.fromJson(responseBody, Aerodrom[].class);
      if (aerodromi.length == 0) {
        return null;
      }
      return aerodromi;
    }

    /**
     * 
     * Dohvaća aerodrom sa zadanim ICAO kodom putem HTTP GET zahtjeva.
     * 
     * @param icao ICAO kod aerodroma koji se želi dohvatiti
     * @return objekt klase Aerodrom koji predstavlja dohvaćeni aerodrom ili null ako se dogodila
     *         greška prilikom dohvaćanja
     * @throws ClientErrorException ako je došlo do greške u komunikaciji sa serverom
     */
    public Aerodrom getAerodrom(String icao) throws ClientErrorException {
      WebTarget resource = webTarget;
      resource = resource.path(java.text.MessageFormat.format("{0}", new Object[] {icao}));
      Invocation.Builder request = resource.request(MediaType.APPLICATION_JSON);
      Response response = request.get();
      if (response.getStatus() != 200) {
        return null;
      }
      Gson gson = new Gson();
      Aerodrom aerodrom = gson.fromJson(request.get(String.class), Aerodrom.class);
      return aerodrom;
    }



    public Udaljenost[] getUdaljenosti(String icaoOd, String icaoDo) throws ClientErrorException {
      WebTarget resource = webTarget;
      resource =
          resource.path(java.text.MessageFormat.format("{0}/{1}", new Object[] {icaoOd, icaoDo}));
      Invocation.Builder request = resource.request(MediaType.APPLICATION_JSON);
      Response response = request.get();
      if (response.getStatus() != 200) {
        return null;
      }
      String responseBody = response.readEntity(String.class);
      Gson gson = new Gson();
      Udaljenost[] udaljenosti = gson.fromJson(responseBody, Udaljenost[].class);
      if (udaljenosti.length == 0) {
        return null;
      }
      return udaljenosti;
    }

    public String getUdaljenost(String icaoOd, String icaoDo) throws ClientErrorException {
      WebTarget resource = webTarget;
      resource = resource
          .path(java.text.MessageFormat.format("{0}/izracunaj/{1}", new Object[] {icaoOd, icaoDo}));
      Invocation.Builder request = resource.request(MediaType.APPLICATION_JSON);
      Response response = request.get();
      if (response.getStatus() != 200) {
        return null;
      }
      String responseBody = response.readEntity(String.class);
      Gson gson = new Gson();
      String udaljenost = gson.fromJson(responseBody, String.class);
      if (udaljenost == null) {
        return null;
      }
      return udaljenost;
    }

    /**
     * 
     * Zatvara klijenta koji se koristi za slanje zahtjeva prema REST servisu.
     */
    public void close() {
      client.close();
    }
  }

}


