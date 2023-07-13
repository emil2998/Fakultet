package hr.foi.air.air2116.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import hr.foi.air.core.Komunikator
import hr.foi.air.air2116.R
import hr.foi.air.core.Popravak
import hr.foi.air.air2116.repository.FirestoreNadiRegistraciju
import hr.foi.air.air2116.repository.FirestorePopraviKvar


class OpisZavrsenogPosla : Fragment() {
    private lateinit var komunikator: Komunikator
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_opis_zavrsenog_posla, container, false)
        val txtNaslov = view.findViewById(R.id.txtNaslov) as TextView
        txtNaslov.text = "Završen posao"
        view?.findViewById<ImageButton>(R.id.btnIzbornik)?.setOnClickListener{
            komunikator = activity as Komunikator
            komunikator.vratiGlavniMeni()
        }


        //TODO primit argumente(id?) i stavit ih na TextView-ove
        val argumenti = arguments
        val datumKvara = argumenti!!.get("datumKvara").toString()
        var datumPopravka:String
        val hitnost = argumenti!!.get("hitnost").toString()
        val idKvar = argumenti!!.get("idKvar").toString()
        val idVozilo = argumenti!!.get("idVozilo").toString()
        val opisKvara = argumenti!!.get("opisKvara").toString()
        var opisPopravka:String
        val popravljeno = argumenti!!.get("popravljeno").toString()
        val txtNaziv = view.findViewById(R.id.txtNazivVozila2) as TextView
        val txtOpis = view.findViewById(R.id.txtOpisKvara2) as TextView
        var registracija:String
        FirestoreNadiRegistraciju(idVozilo.toString(),object: FirestoreNadiRegistraciju {
            override fun onCallback(registracijaBaza: String) {
                registracija = registracijaBaza
                txtNaziv.text = registracija
            }
        })
        txtOpis.text=opisKvara.toString()

        view.findViewById<Button>(R.id.btnZavrsi).setOnClickListener{
            if(view.findViewById<EditText>(R.id.editDatum).text.isNotEmpty() && view.findViewById<EditText>(R.id.editOpisPopravka).text.isNotEmpty()){
                //TODO dodat popravak u bazu itd.
                datumPopravka = view.findViewById<EditText>(R.id.editDatum).text.toString()
                opisPopravka = view.findViewById<EditText>(R.id.editOpisPopravka).text.toString()
                var popravljeno = Popravak(datumKvara,datumPopravka,hitnost,idKvar,idVozilo,opisKvara,opisPopravka, popravljeno)
                FirestorePopraviKvar(popravljeno)
                Thread.sleep(500)
                getFragmentManager()?.popBackStack()
            }
        }

        return view

    }
}