package hr.foi.air.air2116.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.foi.air.air2116.adapter.AktivniPosloviAdapter
import hr.foi.air.air2116.dataClass.AktivniPoslovi
import hr.foi.air.air2116.R
import hr.foi.air.core.Komunikator
import hr.foi.air.air2116.dataClass.*
import hr.foi.air.air2116.repository.FirestoreNadiKvarove
import hr.foi.air.core.Popravak

class AktivniPoslovi : Fragment(), AktivniPosloviAdapter.OnItemClickListener{
    var lista = mutableListOf<AktivniPoslovi>()
    var listaKvarova = mutableListOf<Popravak>()
    private lateinit var komunikator: Komunikator
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_aktivni_poslovi, container, false)
        val txtNaslov = view.findViewById(R.id.txtNaslov) as TextView
        txtNaslov.text = "Aktivni poslovi"

        view?.findViewById<ImageButton>(R.id.btnIzbornik)?.setOnClickListener{
            komunikator = activity as Komunikator
            komunikator.vratiGlavniMeni()
        }

        initData()

        return view
    }

    override fun onItemClick(popravak: Popravak){
            komunikator = activity as Komunikator
            komunikator.otvoriOpisPosla(popravak)
    }

    private fun initData() {
        listaKvarova.clear()
        FirestoreNadiKvarove(object:FirestoreNadiKvarove{
            override fun onCallback(lista: MutableList<Popravak>) {
                listaKvarova = lista
                for(item in listaKvarova){
                    val adapter = AktivniPosloviAdapter(listaKvarova,this@AktivniPoslovi)
                    val recyclerView = view?.findViewById<RecyclerView>(R.id.recViewAktivniPoslovi)
                    recyclerView?.adapter = adapter
                    recyclerView?.layoutManager = LinearLayoutManager(context)
                }
            }
        })

    }

}