package hr.foi.air.air2116.dataClass

import android.graphics.Bitmap
import java.util.*

data class Vozilo (
    var idVozila: String,
    var ispravan: String,
    var godinaProizvodnje: String,
    var nazivModela: String,
    var nosivost: String,
    var registracija: String,
    var registriranDo: Date?=null,
    var slikaURL: String,
    var slika: Bitmap? = null,
    var snaga: String,
    var vrstaVozila: String
)