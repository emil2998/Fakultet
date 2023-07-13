package org.foi.nwtis.podaci;

/**
 * Predstavlja podatke o udaljenosti između aerodroma i države.
 * 
 * @param icao ICAO oznaka aerodroma.
 * @param drzava Ime države.
 * @param km Udaljenost između aerodroma i države u kilometrima.
 */
public record UdaljenostAerodromDrzava(String icao, String drzava, float km) {

}
