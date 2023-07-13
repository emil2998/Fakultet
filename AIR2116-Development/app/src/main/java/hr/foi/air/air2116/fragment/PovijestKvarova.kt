package hr.foi.air.air2116.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.foi.air.air2116.R
import hr.foi.air.air2116.activity.MainActivity
import hr.foi.air.air2116.adapter.PovijestKvarovaAdapter
import hr.foi.air.air2116.dataClass.Kvar
import hr.foi.air.air2116.dataClass.KvarPovijest
import hr.foi.air.air2116.dataClass.Poduzeca
import hr.foi.air.air2116.repository.FirestoreCallBackPoduzece
import hr.foi.air.air2116.repository.FirestoreCallBackPovijestKvarova
import hr.foi.air.air2116.repository.FirestoreDohvatiDogovoreniPosaoKorisnik
import hr.foi.air.air2116.repository.FirestoreDohvatiPovijestKvarova


class PovijestKvarova : Fragment() {

    var listaKvarPovijest = arrayListOf<KvarPovijest>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val argumenti = arguments
        var idVozila = argumenti!!.get("id").toString()
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_povijest_kvarova, container, false)

        val loadingDialogView =
            LayoutInflater.from(activity).inflate(R.layout.dialog_ucitavanje, null)
        loadingDialogView.findViewById<TextView>(R.id.txtLoadingDialog)
            .setText("Dohvaćanje podataka...")
        val loadingDialogBuilder = AlertDialog.Builder(activity).setView(loadingDialogView)
        loadingDialogBuilder.setCancelable(false)
        val loadingDialogInstance = loadingDialogBuilder.show()




        //refresh reclyclerview
        FirestoreDohvatiPovijestKvarova( object : FirestoreCallBackPovijestKvarova {
            override fun onCallback(lista1: ArrayList<KvarPovijest>) {
                listaKvarPovijest = lista1
                val adapter =
                    PovijestKvarovaAdapter(listaKvarPovijest)
                val recyclerView = view?.findViewById<RecyclerView>(R.id.recycler_view)
                recyclerView?.adapter = adapter
                recyclerView?.layoutManager = LinearLayoutManager(context)
                loadingDialogInstance.dismiss()
            }
        }, idVozila)


        return view

    }
    }
