package org.foi.nwtis.emihalic.aplikacija_1;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dretva extends Thread {
  private Socket socket = null;

  public Dretva(Socket socket) {
    super();
    this.socket = socket;
  }

  @Override
  public synchronized void start() {
    super.start();
  }

  @Override
  public void run() {
    try (var reader = new InputStreamReader(this.socket.getInputStream(), Charset.forName("UTF-8"));
        var writer =
            new OutputStreamWriter(this.socket.getOutputStream(), Charset.forName("UTF-8"));) {
      var sb = new StringBuilder();
      while (true) {
        int slovo = reader.read();
        if (slovo == -1)
          break;
        sb.append((char) slovo);
      }
      this.socket.shutdownInput();
      String obradenaNaredba = obradiNaredbu(sb.toString());
      writer.write(obradenaNaredba);
      writer.flush();
      this.socket.shutdownOutput();
    } catch (IOException e) {
      Logger.getGlobal().log(Level.INFO, "Greška u server socketu.");
    }
  }

  @Override
  public void interrupt() {
    super.interrupt();
  }

  private String obradiNaredbu(String naredba) {
    String odgovor = "";
    if (Posluzitelj.ispisKonzola) {
      System.out.println(naredba);
    }
    switch (naredba) {
      case "STATUS":
        if (Posluzitelj.aktivanPosluzitelj) {
          odgovor = "OK 1";
        } else {
          odgovor = "OK 0";
        }
        break;
      case "KRAJ":
        try {
          Posluzitelj.ss.close();
          odgovor = "OK";
        } catch (IOException e) {
          odgovor = "ERROR 05 - Greška kod naredbe 'KRAJ'";
        }
        break;
      case "INIT":
        if (Posluzitelj.aktivanPosluzitelj) {
          odgovor = "ERROR 02 - Poslužitelj je aktivan";
        } else {
          Posluzitelj.aktivanPosluzitelj = true;
          Posluzitelj.brojacZahtjeva = 0;
          odgovor = "OK";
        }
        break;
      case "PAUZA":
        if (Posluzitelj.aktivanPosluzitelj) {
          Posluzitelj.aktivanPosluzitelj = false;
          odgovor = "OK " + Posluzitelj.brojacZahtjeva;
        } else {
          odgovor = "ERROR 01 - Poslužitelj nije aktivan";
        }
        break;
      case "INFO DA":
        if (Posluzitelj.aktivanPosluzitelj) {
          if (Posluzitelj.ispisKonzola) {
            odgovor = "ERROR 03 - Zahtjev se ispisuje";
          } else {
            Posluzitelj.ispisKonzola = true;
            odgovor = "OK";
          }
        } else {
          odgovor = "ERROR 01 - Poslužitelj nije aktivan";
        }
        break;
      case "INFO NE":
        if (Posluzitelj.aktivanPosluzitelj) {
          if (Posluzitelj.ispisKonzola) {
            Posluzitelj.ispisKonzola = false;
            odgovor = "OK";
          } else {
            odgovor = "ERROR 04 - Zahtjev se ne ispisuje";
          }
        } else {
          odgovor = "ERROR 01 - Poslužitelj nije aktivan";
        }
        break;
      default:
        Pattern p = Pattern.compile(
            "UDALJENOST\\s+(-?\\d+\\.\\d+)\\s+(-?\\d+\\.\\d+)\\s+(-?\\d+\\.\\d+)\\s+(-?\\d+\\.\\d+)");
        Matcher m = p.matcher(naredba);
        if (m.matches()) {
          String[] argumenti = naredba.split(" ");
          Double lat1 = Double.parseDouble(argumenti[1]);
          Double lon1 = Double.parseDouble(argumenti[2]);
          Double lat2 = Double.parseDouble(argumenti[3]);
          Double lon2 = Double.parseDouble(argumenti[4]);
          Double udaljenost = izracunajUdaljenost(lat1, lon1, lat2, lon2);
          odgovor = "OK " + udaljenost;
          Posluzitelj.brojacZahtjeva++;
        } else {
          odgovor = "ERROR 05 - Kriva naredba";
        }
    }
    return odgovor;
  }

  private Double izracunajUdaljenost(Double lat1, Double lon1, Double lat2, Double lon2) {
    final int R = 6371;
    Double latDistance = toRad(lat2 - lat1);
    Double lonDistance = toRad(lon2 - lon1);
    Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(toRad(lat1))
        * Math.cos(toRad(lat2)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
    Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    Double distance = R * c;
    return distance;
  }

  private static Double toRad(Double value) {
    return value * Math.PI / 180;
  }
}
