package org.foi.nwtis.emihalic.aplikacija_4.slusaci;

import java.io.File;
import org.foi.nwtis.Konfiguracija;
import org.foi.nwtis.NeispravnaKonfiguracija;
import org.foi.nwtis.PostavkeBazaPodataka;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * Klasa koja je slusac aplikacije.
 * 
 * @author Emil Mihalić
 */
@WebListener
public class SlusacAplikacije implements ServletContextListener {

  public static ServletContext context;

  /**
   * Metoda koja se poziva prilikom inicijalizacije ServletContext-a.
   *
   * @param sce objekt koji predstavlja događaj inicijalizacije ServletContext-a
   */
  @Override
  public void contextInitialized(ServletContextEvent sce) {
    context = sce.getServletContext();
    String nazivDatoteke = context.getInitParameter("konfiguracija");
    String putanja = context.getRealPath("/WEB-INF") + File.separator;
    nazivDatoteke = putanja + nazivDatoteke;

    Konfiguracija konfig = new PostavkeBazaPodataka(nazivDatoteke);
    try {
      konfig.ucitajKonfiguraciju();
    } catch (NeispravnaKonfiguracija e) {
      e.printStackTrace();
      return;
    }

    context.setAttribute("konfig", konfig);
  }

  /**
   * Metoda koja se poziva prilikom uništavanja ServletContext-a.
   *
   * @param sce objekt koji predstavlja događaj uništavanja ServletContext-a
   */
  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    context = sce.getServletContext();
    context.removeAttribute("postavke");
    ServletContextListener.super.contextDestroyed(sce);
  }

}
