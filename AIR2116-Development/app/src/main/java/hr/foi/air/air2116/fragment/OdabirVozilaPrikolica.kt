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
import de.hdodenhof.circleimageview.CircleImageView
import hr.foi.air.air2116.R
import hr.foi.air.air2116.activity.MainActivity
import hr.foi.air.air2116.adapter.VozilaPrikoliceAdapter
import hr.foi.air.air2116.dataClass.VoziloPrikolica
import hr.foi.air.air2116.repository.*
import hr.foi.air.core.Komunikator
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.ArrayList

class OdabirVozilaPrikolica : Fragment(), VozilaPrikoliceAdapter.OnItemClickListener {

    var listaVozilaPrikolica = arrayListOf<VoziloPrikolica>()
    private lateinit var komunikator: Komunikator


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
        FirestoreDohvatiVozilaPrikolice( object : FirestoreCallBackVozilaPrikolice {
            override fun onCallback(lista1: ArrayList<VoziloPrikolica>) {
                listaVozilaPrikolica = lista1
                val adapter =
                    VozilaPrikoliceAdapter(listaVozilaPrikolica, this@OdabirVozilaPrikolica)
                val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(context)
                loadingDialogInstance.dismiss()
            }
        })


        return view

    }

    override fun onItemClick(position: Int) {
        val kliknutoVozilo: VoziloPrikolica = listaVozilaPrikolica[position]
        //UPDATE
        //Otvaranje skočnog prozora za pregled i izmjenu podataka o korisniku
        val dodijeliView =
            LayoutInflater.from(activity)
                .inflate(R.layout.dialog_vozilo, null)
        val dodijeliBuilder = AlertDialog.Builder(activity).setView(dodijeliView)
        val dodijeliInstance = dodijeliBuilder.show()
        dodijeliView.findViewById<TextView>(R.id.naziv_modela).text = kliknutoVozilo.nazivModela
        dodijeliView.findViewById<TextView>(R.id.vrsta_vozila)
            .text = kliknutoVozilo.vrstaVozila
        dodijeliView.findViewById<TextView>(R.id.godina_proizvodnje)
            .text = kliknutoVozilo.godinaProizvodnje
        dodijeliView.findViewById<TextView>(R.id.registracija)
            .text = kliknutoVozilo.registracija
        dodijeliView.findViewById<TextView>(R.id.snaga)
            .text = kliknutoVozilo.snaga
        dodijeliView.findViewById<TextView>(R.id.nosivost)
            .text = kliknutoVozilo.nosivost
        dodijeliView.findViewById<ImageView>(R.id.slikaProfilaVozila).setImageBitmap(kliknutoVozilo.slika)
        dodijeliView.findViewById<TextView>(R.id.registriran_do)
            .text = kliknutoVozilo.registriranDo


        dodijeliView.findViewById<Button>(R.id.otvoriPovijestKvarova).setOnClickListener {

            komunikator = activity as Komunikator
            komunikator.otvoriPovijestKvarova(kliknutoVozilo.idVozila)

            dodijeliInstance.dismiss()

        }




}}
