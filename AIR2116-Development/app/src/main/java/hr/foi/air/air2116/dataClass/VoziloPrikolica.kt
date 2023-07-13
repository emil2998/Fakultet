package hr.foi.air.air2116.dataClass

import android.graphics.Bitmap

data class VoziloPrikolica(
    var godinaProizvodnje: String,
    var idVozila: String,
    var ispravan: String,
    var nazivModela: String,
    var nosivost: String,
    var registracija: String,
    var registriranDo: String,
    var slikaURL: String,
    var slika: Bitmap? = null,
    var snaga: String,
    var vrstaVozila: String,
)
