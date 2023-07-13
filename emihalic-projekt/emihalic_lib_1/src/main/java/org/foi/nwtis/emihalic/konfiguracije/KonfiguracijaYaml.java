package org.foi.nwtis.emihalic.konfiguracije;

import org.foi.nwtis.KonfiguracijaApstraktna;
import org.foi.nwtis.NeispravnaKonfiguracija;

/**
 * Klasa za čitanje yaml datoteke sa postavkama.
 * 
 * @author Emil Mihalić
 *
 */
public class KonfiguracijaYaml extends KonfiguracijaApstraktna {
  /**
   * Konstanta TIP
   */
  public static final String TIP = "yaml";

  /**
   * Konstruktor
   * 
   * @param nazivDatoteke - naziv datoteke
   */
  public KonfiguracijaYaml(String nazivDatoteke) {
    super(nazivDatoteke);
    // TODO Auto-generated constructor stub
  }

  @Override
  public void spremiKonfiguraciju(String datoteka) throws NeispravnaKonfiguracija {
    // TODO Auto-generated method stub

  }

  @Override
  public void ucitajKonfiguraciju() throws NeispravnaKonfiguracija {
    // TODO Auto-generated method stub

  }

}
