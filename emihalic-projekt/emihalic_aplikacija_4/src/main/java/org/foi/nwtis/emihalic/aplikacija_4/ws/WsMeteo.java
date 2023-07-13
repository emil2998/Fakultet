package org.foi.nwtis.emihalic.aplikacija_4.ws;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.Konfiguracija;
import org.foi.nwtis.emihalic.aplikacija_4.slusaci.SlusacAplikacije;
import org.foi.nwtis.podaci.Aerodrom;
import org.foi.nwtis.podaci.Lokacija;
import org.foi.nwtis.rest.klijenti.NwtisRestIznimka;
import org.foi.nwtis.rest.klijenti.OWMKlijent;
import org.foi.nwtis.rest.podaci.MeteoPodaci;
import jakarta.annotation.Resource;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;

/***
 * Klasa koja je web servis za meteo podatke.**
 * 
 * @author Emil Mihalić
 */
@WebService(serviceName = "meteo")
public class WsMeteo {

  @Resource(lookup = "java:app/jdbc/nwtis_bp")
  javax.sql.DataSource ds;

  /**
   * Dohvaća trenutne meteorološke podatke za odabrani aerodrom.
   *
   * @param icao ICAO oznaka aerodroma
   * @return MeteoPodaci objekt koji sadrži trenutne meteorološke podatke
   */
  @WebMethod
  public MeteoPodaci dajMeteo(@WebParam String icao) {
    Konfiguracija konfig = (Konfiguracija) SlusacAplikacije.context.getAttribute("konfig");
    String owmApi = konfig.dajPostavku("OpenWeatherMap.apikey");
    String adresaAplikacija2 = konfig.dajPostavku("adresa.aplikacija_2");
    Client client = ClientBuilder.newClient();
    WebTarget webTarget = client.target(adresaAplikacija2).path("aerodromi/").path(icao);
    Response odgovor = webTarget.request().header("Accept", "application/json").get();
    Aerodrom aerodrom = odgovor.readEntity(Aerodrom.class);
    if (aerodrom == null) {
      return null;
    }
    OWMKlijent klijentOWM = new OWMKlijent(owmApi);
    Lokacija lokacija = aerodrom.getLokacija();
    MeteoPodaci meteoPodaci = null;
    try {
      meteoPodaci = klijentOWM.getRealTimeWeather(lokacija.getLongitude(), lokacija.getLatitude());
    } catch (NwtisRestIznimka e) {
      Logger.getGlobal().log(Level.SEVERE, e.getMessage());
    }
    return meteoPodaci;
  }
}
