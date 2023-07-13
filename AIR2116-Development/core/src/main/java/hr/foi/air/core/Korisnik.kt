package hr.foi.air.core

import android.graphics.Bitmap
import java.util.*


data class Korisnik(
    var korisnickoIme: String,
    var lozinka: String,
    var ime: String,
    var prezime: String,
    var uloga: String,
    var pin: String,
    var id: String,
    var slikaURL: String,
    var slika: Bitmap? = null,
    var mobitel: String,
    var email: String,
    var datumRodenja: Date
)