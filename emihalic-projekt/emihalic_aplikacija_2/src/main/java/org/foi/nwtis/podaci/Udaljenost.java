package org.foi.nwtis.podaci;

/**
 * 
 * Zapis koji sadrži podatke o udaljenosti u kilometrima i državi.
 * 
 * @param drzava Država koja se odnosi na udaljenost.
 * @param km Udaljenost između aerodroma u kilometrima.
 * @return Zapis o udaljenosti koji sadrži ime države i udaljenost u kilometrima.
 */

public record Udaljenost(String drzava, float km) {

}


