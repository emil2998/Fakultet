package org.foi.nwtis.emihalic.aplikacija_4.ws;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.podaci.Korisnik;
import jakarta.annotation.Resource;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

/**
 * Klasa koja je web servis za meteo podatke.
 * 
 * @author Emil Mihalić
 */
@WebService(serviceName = "korisnici")
public class WsKorisnici {

  @Resource(lookup = "java:app/jdbc/nwtis_bp")
  javax.sql.DataSource ds;

  @WebMethod
  public Korisnik dajKorisnika(@WebParam String korisnik, @WebParam String lozinka,
      @WebParam String traziKorisnika) {
    PreparedStatement ps = null;
    Korisnik trazeniKorisnik = null;
    String query =
        "select * from korisnik where korime = '" + korisnik + "' and lozinka = '" + lozinka + "'";
    try (var con = ds.getConnection()) {
      ps = con.prepareStatement(query);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        query = "select * from korisnik where korime = '" + traziKorisnika + "'";
        ps = con.prepareStatement(query);
        rs = ps.executeQuery();
        if (rs.next()) {
          String lozinkaBaza = rs.getString("LOZINKA");
          String imeBaza = rs.getString("IME");
          String prezimeBaza = rs.getString("PREZIME");
          trazeniKorisnik = new Korisnik(traziKorisnika, lozinkaBaza, imeBaza, prezimeBaza);
        }
      }
      rs.close();
      ps.close();
      con.close();
    } catch (Exception e) {
      Logger.getGlobal().log(Level.SEVERE, e.getMessage());
    }
    return trazeniKorisnik;
  }

  @WebMethod
  public List<Korisnik> dajKorisnike(@WebParam String korisnik, @WebParam String lozinka,
      @WebParam String traziImeKorisnika, @WebParam String traziPrezimeKorisnika) {
    PreparedStatement ps = null;
    List<Korisnik> trazeniKorisnici = new ArrayList<Korisnik>();
    String query =
        "select * from korisnik where korime = '" + korisnik + "' and lozinka = '" + lozinka + "'";
    try (var con = ds.getConnection()) {
      ps = con.prepareStatement(query);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        query = "select * from korisnik where 1=1";
        if (traziImeKorisnika != null && !traziImeKorisnika.isEmpty()) {
          query += "and ime like'%" + traziImeKorisnika + "%'";
        }
        if (traziPrezimeKorisnika != null && !traziPrezimeKorisnika.isEmpty()) {
          query += "and prezime like '%" + traziPrezimeKorisnika + "%'";
        }
        ps = con.prepareStatement(query);
        rs = ps.executeQuery();
        while (rs.next()) {
          String korimeBaza = rs.getString("KORIME");
          String lozinkaBaza = rs.getString("LOZINKA");
          String imeBaza = rs.getString("IME");
          String prezimeBaza = rs.getString("PREZIME");
          trazeniKorisnici.add(new Korisnik(korimeBaza, lozinkaBaza, imeBaza, prezimeBaza));
        }
      }
      rs.close();
      ps.close();
      con.close();
    } catch (Exception e) {
      Logger.getGlobal().log(Level.SEVERE, e.getMessage());
    }
    return trazeniKorisnici;
  }

  @WebMethod
  public boolean dodajKorisnika(@WebParam Korisnik korisnik) {
    PreparedStatement ps = null;
    int dodano = 0;
    String query = "insert into korisnik values ('" + korisnik.getKorime() + "','"
        + korisnik.getLozinka() + "','" + korisnik.getIme() + "','" + korisnik.getPrezime() + "')";
    try (var con = ds.getConnection()) {
      ps = con.prepareStatement(query);
      dodano = ps.executeUpdate();
      ps.close();
      con.close();
    } catch (Exception e) {
      Logger.getGlobal().log(Level.SEVERE, e.getMessage());
    }
    if (dodano > 0) {
      return true;
    } else {
      return false;
    }
  }

}
