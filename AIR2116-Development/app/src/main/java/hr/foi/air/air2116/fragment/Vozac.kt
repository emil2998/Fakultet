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
import kotlinx.android.synthetic.main.fragment_vozac.*

class vozac : Fragment() {
    private lateinit var komunikator: Komunikator
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_vozac, container, false)
        komunikator = activity as Komunikator
        view.findViewById<Button>(R.id.btnTrenutniPrijevoz).setOnClickListener {
            komunikator.trenutniPrijevoz()
        }
        view.findViewById<Button>(R.id.btnPovijestPrijevoza).setOnClickListener {
            komunikator.otvoriPovijest()
        }
        view.findViewById<Button>(R.id.btnProfil).setOnClickListener {
            komunikator.otvoriProfil()
        }
        view.findViewById<Button>(R.id.btnLokacija).setOnClickListener {
            komunikator.otvoriMapu()
        }
        view.findViewById<Button>(R.id.btnOdjava).setOnClickListener {
            komunikator.odjaviSe()
        }
        return view
    }

}