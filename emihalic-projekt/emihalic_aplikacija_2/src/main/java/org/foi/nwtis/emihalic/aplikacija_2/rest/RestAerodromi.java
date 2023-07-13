package org.foi.nwtis.emihalic.aplikacija_2.rest;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.Konfiguracija;
import org.foi.nwtis.podaci.Aerodrom;
import org.foi.nwtis.podaci.Lokacija;
import org.foi.nwtis.podaci.Udaljenost;
import com.google.gson.Gson;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("aerodromi")
@RequestScoped
public class RestAerodromi {

  private Gson gson = new Gson();

  @Context
  ServletContext context;

  @Resource(lookup = "java:app/jdbc/nwtis_bp")
  javax.sql.DataSource ds;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response osnovna(@QueryParam("traziNaziv") String traziNaziv,
      @QueryParam("traziDrzavu") String traziDrzavu,
      @DefaultValue("1") @QueryParam("odBroja") int odBroja,
      @DefaultValue("20") @QueryParam("broj") int broj) {
    String query = "select * from airports where 1=1";
    if (traziNaziv != null && !traziNaziv.isEmpty()) {
      query += "and name like '%" + traziNaziv + "%'";
    }
    if (traziDrzavu != null && !traziDrzavu.isEmpty()) {
      query += "and iso_country like '%" + traziDrzavu + "%'";
    }
    query += " OFFSET " + (odBroja - 1) * broj + " LIMIT " + broj;
    PreparedStatement ps = null;
    List<Aerodrom> aerodromi = new ArrayList<Aerodrom>();
    try (var con = ds.getConnection()) {
      ps = con.prepareStatement(query);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        String icao = rs.getString("ICAO");
        String name = rs.getString("NAME");
        String isoCountry = rs.getString("ISO_COUNTRY");
        String koordinateString = rs.getString("COORDINATES");
        String[] koordinate = koordinateString.split(",");
        Lokacija lokacija = new Lokacija(koordinate[0], koordinate[1]);
        aerodromi.add(new Aerodrom(icao, name, isoCountry, lokacija));
      }
      rs.close();
      ps.close();
      con.close();
    } catch (Exception e) {
      Logger.getGlobal().log(Level.SEVERE, e.getMessage());
    }
    return Response.ok().entity(gson.toJson(aerodromi)).build();
  }

  @GET
  @Path("{icao}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response nadiIcao(@PathParam("icao") String icao) {
    String query = "select * from airports where icao = '" + icao + "'";
    PreparedStatement ps = null;
    Aerodrom aerodrom = null;
    try (var con = ds.getConnection()) {
      ps = con.prepareStatement(query);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        String name = rs.getString("NAME");
        String isoCountry = rs.getString("ISO_COUNTRY");
        String koordinateString = rs.getString("COORDINATES");
        String[] koordinate = koordinateString.split(",");
        Lokacija lokacija = new Lokacija(koordinate[0], koordinate[1]);
        aerodrom = new Aerodrom(icao, name, isoCountry, lokacija);
      }
      rs.close();
      ps.close();
      con.close();
    } catch (Exception e) {
      Logger.getGlobal().log(Level.SEVERE, e.getMessage());
    }
    return Response.ok().entity(gson.toJson(aerodrom)).build();
  }

  @GET
  @Path("{icaoOd}/{icaoDo}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response udaljenostiIzmeduAerodrom(@PathParam("icaoOd") String icaoOd,
      @PathParam("icaoDo") String icaoDo) {
    String query = "select * from airports_distance_matrix where icao_from = '" + icaoOd
        + "' and icao_to = '" + icaoDo + "'";
    PreparedStatement ps = null;
    ArrayList<Udaljenost> udaljenosti = new ArrayList<Udaljenost>();
    try (var con = ds.getConnection()) {
      ps = con.prepareStatement(query);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        String country = rs.getString("COUNTRY");
        float distCtry = rs.getFloat("DIST_CTRY");
        udaljenosti.add(new Udaljenost(country, distCtry));
      }
      rs.close();
      ps.close();
      con.close();
    } catch (Exception e) {
      Logger.getGlobal().log(Level.SEVERE, e.getMessage());
    }
    return Response.ok().entity(gson.toJson(udaljenosti)).build();
  }

  @GET
  @Path("{icao}/udaljenosti")
  @Produces(MediaType.APPLICATION_JSON)
  public Response udaljenostiIzmeduAerodrom(@PathParam("icao") String icao,
      @DefaultValue("1") @QueryParam("odBroja") int odBroja,
      @DefaultValue("20") @QueryParam("broj") int broj) {
    String query = "select * from airports_distance_matrix where icao_from = '" + icao + "' OFFSET "
        + (odBroja - 1) * broj + " LIMIT " + broj;
    PreparedStatement ps = null;
    ArrayList<Udaljenost> udaljenosti = new ArrayList<Udaljenost>();
    try (var con = ds.getConnection()) {
      ps = con.prepareStatement(query);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        String country = rs.getString("COUNTRY");
        float distTot = rs.getFloat("DIST_TOT");
        udaljenosti.add(new Udaljenost(country, distTot));
      }
      rs.close();
      ps.close();
      con.close();
    } catch (Exception e) {
      Logger.getGlobal().log(Level.SEVERE, e.getMessage());
    }
    return Response.ok().entity(gson.toJson(udaljenosti)).build();
  }

  @GET
  @Path("{icaoOd}/izracunaj/{icaoDo}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response izracunaj(@PathParam("icaoOd") String icaoOd,
      @PathParam("icaoDo") String icaoDo) {
    Aerodrom aerodromOd = dajAerodrom(icaoOd);
    Aerodrom aerodromDo = dajAerodrom(icaoDo);
    String naredba = "UDALJENOST " + aerodromOd.getLokacija().getLongitude().trim() + " "
        + aerodromOd.getLokacija().getLatitude().trim() + " "
        + aerodromDo.getLokacija().getLongitude().trim() + " "
        + aerodromDo.getLokacija().getLatitude().trim();
    var odgovor = kontaktirajPosluzitelj(naredba);
    return Response.ok().entity(gson.toJson(odgovor.substring(3))).build();
  }

  private Aerodrom dajAerodrom(String icao) {
    String query = "select * from airports where icao = '" + icao + "'";
    PreparedStatement ps = null;
    Aerodrom aerodrom = null;
    try (var con = ds.getConnection()) {
      ps = con.prepareStatement(query);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        String name = rs.getString("NAME");
        String isoCountry = rs.getString("ISO_COUNTRY");
        String koordinateString = rs.getString("COORDINATES");
        String[] koordinate = koordinateString.split(",");
        Lokacija lokacija = new Lokacija(koordinate[0], koordinate[1]);
        aerodrom = new Aerodrom(icao, name, isoCountry, lokacija);
      }
      rs.close();
      ps.close();
      con.close();
    } catch (Exception e) {
      Logger.getGlobal().log(Level.SEVERE, e.getMessage());
    }
    return aerodrom;
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
