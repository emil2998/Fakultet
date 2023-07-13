package hr.foi.air.air2116.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.foi.air.air2116.R
import hr.foi.air.air2116.activity.MainActivity
import hr.foi.air.air2116.adapter.PovijestAdapter
import hr.foi.air.air2116.dataClass.Poduzeca
import hr.foi.air.air2116.repository.*
import kotlin.collections.ArrayList

class PovijestPrijevoza : Fragment() {

    var listaPovijestiPrijevoza = arrayListOf<Poduzeca>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_povijest, container, false)
        //DATABASE READ





        val loadingDialogView =
            LayoutInflater.from(activity).inflate(R.layout.dialog_ucitavanje, null)
        loadingDialogView.findViewById<TextView>(R.id.txtLoadingDialog)
            .setText("Dohvaćanje podataka...")
        val loadingDialogBuilder = AlertDialog.Builder(activity).setView(loadingDialogView)
        loadingDialogBuilder.setCancelable(false)
        val loadingDialogInstance = loadingDialogBuilder.show()




        //refresh reclyclerview
        FirestoreDohvatiDogovoreniPosaoKorisnik( object : FirestoreCallBackPoduzece {
            override fun onCallback(lista1: ArrayList<Poduzeca>) {
                listaPovijestiPrijevoza = lista1
                val adapter =
                    PovijestAdapter(listaPovijestiPrijevoza)
                val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(context)
                loadingDialogInstance.dismiss()
            }
        }, MainActivity.ulogiraniKorisnik.id.toString())


        return view

        }
    }
