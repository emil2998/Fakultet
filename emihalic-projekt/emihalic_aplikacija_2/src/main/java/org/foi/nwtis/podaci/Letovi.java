package org.foi.nwtis.podaci;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Emil Mihalić
 * @version 1.0.0
 */
public class Letovi {
  @Getter
  @Setter
  private int id;
  @Getter
  @Setter
  private String icao24;
  @Getter
  @Setter
  private int firstseen;
  @Getter
  @Setter
  private String estdepartureairport;
  @Getter
  @Setter
  private int lastseen;
  @Getter
  @Setter
  private String estarrivalairport;
  @Getter
  @Setter
  private String callsign;
  @Getter
  @Setter
  private int estdepartureairporthorizdistance;
  @Getter
  @Setter
  private int estdepartureairportvertdistance;
  @Getter
  @Setter
  private int estarrivalairporthorizdistance;
  @Getter
  @Setter
  private int estarrivalairportvertdistance;
  @Getter
  @Setter
  private int departureairportcandidatescount;
  @Getter
  @Setter
  private int arrivalairportcandidatescount;
  @Getter
  @Setter
  private Date stored;

  public Letovi() {}
}
