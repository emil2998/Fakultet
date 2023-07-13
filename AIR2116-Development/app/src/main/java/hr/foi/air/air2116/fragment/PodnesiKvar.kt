package hr.foi.air.air2116.fragment

import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import hr.foi.air.air2116.R
import hr.foi.air.air2116.activity.MainActivity
import hr.foi.air.air2116.dataClass.AdreseIstovarUtovar
import hr.foi.air.air2116.dataClass.DodVozaci
import hr.foi.air.air2116.dataClass.Kvar
import hr.foi.air.air2116.repository.*
import hr.foi.air.core.Komunikator

class PodnesiKvar : Fragment() {
    var listaAdresa = mutableListOf<AdreseIstovarUtovar>()
    private lateinit var komunikator: Komunikator
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_podnesi_kvar, container, false)
        val txtNaslov = view.findViewById(R.id.txtNaslov) as TextView
        txtNaslov.text = "Podnesi kvar"

        view?.findViewById<ImageButton>(R.id.btnIzbornik)?.setOnClickListener{
            komunikator = activity as Komunikator
            komunikator.vratiGlavniMeni()
        }

        val argumenti = arguments
        val idPrikolica = argumenti!!.get("idPrikolica")
        val idKamion = argumenti!!.get("idKamion")


        var selectedVozilo = "nista"
        view.findViewById<RadioButton>(R.id.radioKamion).setOnClickListener(){
            selectedVozilo = idKamion.toString()

        }
        view.findViewById<RadioButton>(R.id.radioPrikolica).setOnClickListener(){
            selectedVozilo = idPrikolica.toString()

        }
        var selectedHitnost = "nista"
        view.findViewById<RadioButton>(R.id.hitnostNijeHitno).setOnClickListener(){
            selectedHitnost = "nijeHitno"
        }
        view.findViewById<RadioButton>(R.id.hitnostSrednje).setOnClickListener(){
            selectedHitnost = "srednje"
        }
        view.findViewById<RadioButton>(R.id.hitnostHitno).setOnClickListener(){
            selectedHitnost = "hitno"
        }
        view.findViewById<Button>(R.id.btnZavrsiKvar).setOnClickListener {
            listaAdresa.clear()
            if (view.findViewById<EditText>(R.id.editOpisKvara).text.isNotEmpty()
                && view.findViewById<EditText>(R.id.editDatum).text.isNotEmpty()
                && !(selectedHitnost.equals("nista")) && !(selectedVozilo.equals("nista"))){


                var kvar = Kvar(selectedVozilo, view.findViewById<EditText>(R.id.editOpisKvara).text.toString(),view.findViewById<EditText>(R.id.editDatum).text.toString(),selectedHitnost)


                FirestoreUnesiKvar(kvar)
                println("id vozila koje se briše: "+selectedVozilo)

                FirestoreDohvatiAdreseIstovarUtovar(object: FirestoreCallBackAdreseIstovarUtovar {
                    override fun onCallback(lista: MutableList<AdreseIstovarUtovar>) {
                        listaAdresa = lista
                        for (item in listaAdresa) {
                            FirestoreUrediDodVozace(item.idDogovoreniPosao,selectedVozilo)
                        }

                    }
                })

                FirestoreObrisiInfo(selectedVozilo)
                FirestoreNadiInfoVozaca(selectedVozilo)

                Thread.sleep(500)

                getFragmentManager()?.popBackStack()
            }
        }
        return view
    }
}