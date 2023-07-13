package org.foi.nwtis.emihalic.aplikacija_1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.Konfiguracija;
import org.foi.nwtis.KonfiguracijaApstraktna;

public class Posluzitelj {
  private static int mreznaVrata;
  private static int brojCekaca;
  public static ServerSocket ss = null;

  public static boolean aktivanPosluzitelj = false;
  public static boolean ispisKonzola = false;
  public static int brojacZahtjeva = 0;

  public static void main(String[] args) {
    if (args.length == 1) {
      PreuzmiKonfiguraciju(args);
      try {
        if (mreznaVrata > 7999 && mreznaVrata < 10000) {
          ss = new ServerSocket(mreznaVrata, brojCekaca);
          while (true) {
            Socket socket = ss.accept();
            Dretva dretva = new Dretva(socket);
            dretva.start();
          }
        } else {

        }
      } catch (IOException e) {
        Logger.getGlobal().log(Level.INFO, "Pogreška");
      }
    } else {
      Logger.getGlobal().log(Level.INFO, "Neispravni ulazni parametri.");
    }

  }

  private static void PreuzmiKonfiguraciju(String[] args) {
    Konfiguracija konfig = null;
    try {
      konfig = KonfiguracijaApstraktna.preuzmiKonfiguraciju(args[0]);
      mreznaVrata = Integer.parseInt(konfig.dajPostavku("mreznaVrata"));
      brojCekaca = Integer.parseInt(konfig.dajPostavku("brojCekaca"));
    } catch (Exception e) {
      Logger.getGlobal().log(Level.INFO, "Konfiguracija nije učitana.");
    }
  }



}

