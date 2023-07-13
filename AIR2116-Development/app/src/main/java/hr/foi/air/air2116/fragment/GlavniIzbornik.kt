package hr.foi.air.air2116.fragment

import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import hr.foi.air.air2116.activity.MainActivity
import hr.foi.air.air2116.R
import hr.foi.air.core.Komunikator


class glavniIzbornik : Fragment() {

    private lateinit var komunikator: Komunikator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_glavni_izbornik, container, false)
        view.findViewById<ImageView>(R.id.profilna).setImageBitmap(MainActivity.ulogiraniKorisnik.slika)
        view.findViewById<TextView>(R.id.ime).setText(MainActivity.ulogiraniKorisnik.ime)
        view.findViewById<TextView>(R.id.prezime).setText(MainActivity.ulogiraniKorisnik.prezime)
        view.findViewById<TextView>(R.id.uloga).setText(MainActivity.ulogiraniKorisnik.uloga)
        komunikator = activity as Komunikator
        komunikator.otvoriGlavniMeni()

        return view;
    }


}