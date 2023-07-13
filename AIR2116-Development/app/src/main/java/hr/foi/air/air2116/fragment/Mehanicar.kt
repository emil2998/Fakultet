package hr.foi.air.air2116.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import hr.foi.air.core.Komunikator
import hr.foi.air.air2116.R

class mehanicar : Fragment() {
    private lateinit var komunikator: Komunikator
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mehanicar, container, false)
        komunikator = activity as Komunikator
        view.findViewById<Button>(R.id.btnAktivniPoslovi).setOnClickListener {
            komunikator.aktivniPoslovi()
        }
        view.findViewById<Button>(R.id.btnVozila).setOnClickListener {
            komunikator.otvoriVozilaPrikolice()
        }
        view.findViewById<Button>(R.id.btnProfil).setOnClickListener {
            komunikator.otvoriProfil()
        }
        view.findViewById<Button>(R.id.btnOdjava).setOnClickListener {
            komunikator.odjaviSe()
        }
        return view
    }
}