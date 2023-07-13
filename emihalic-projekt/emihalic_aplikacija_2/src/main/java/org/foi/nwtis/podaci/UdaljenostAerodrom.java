package org.foi.nwtis.podaci;

/**
 * 
 * Podaci o udaljenosti aerodroma. Sadrži icao oznaku aerodroma i udaljenost u kilometrima.
 * 
 * @param icao Icao oznaka aerodroma.
 * @param km Udaljenost aerodroma u kilometrima.
 * @return Objekt tipa UdaljenostAerodrom.
 */
public record UdaljenostAerodrom(String icao, float km) {

}


