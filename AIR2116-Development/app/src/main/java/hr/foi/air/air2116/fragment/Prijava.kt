package hr.foi.air.air2116.fragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import hr.foi.air.core.Komunikator
import hr.foi.air.core.Korisnik
import hr.foi.air.air2116.R
import hr.foi.air.air2116.repository.FirestorDohvatiSlikeKorisnika
import hr.foi.air.air2116.repository.FirestoreCallBack
import hr.foi.air.air2116.repository.FirestoreDohvatiKorisnike

class Prijava : Fragment() {

    private lateinit var komunikator: Komunikator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_prijava, container, false)

        komunikator = activity as Komunikator
        //var listaKorisnika = FirestoreDohvatiKorisnike()

        val prefLoad = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val pin = prefLoad.getString("pin", "empty")
        val userID = prefLoad.getString("korisnik", "empty")

        val dodijeliView = LayoutInflater.from(activity).inflate(R.layout.dialog_ucitavanje, null)
        dodijeliView.findViewById<TextView>(R.id.txtLoadingDialog).setText("Dohvaćanje podataka...")
        val dodijeliBuilder = AlertDialog.Builder(activity).setView(dodijeliView)
        dodijeliBuilder.setCancelable(false)
        val dodijeliInstance = dodijeliBuilder.show()

        FirestoreDohvatiKorisnike(object : FirestoreCallBack {
            override fun onCallback(lista: MutableList<Korisnik>) {
                dodijeliInstance.dismiss()
                var listaKorisnika = lista
                view.findViewById<Button>(R.id.btnPrijaviSe).setOnClickListener {

                    val dodijeliView =
                        LayoutInflater.from(activity).inflate(R.layout.dialog_ucitavanje, null)
                    dodijeliView.findViewById<TextView>(R.id.txtLoadingDialog)
                        .setText("Prijava u tijeku...")
                    val dodijeliBuilder = AlertDialog.Builder(activity).setView(dodijeliView)
                    dodijeliBuilder.setCancelable(false)
                    val dodijeliInstance = dodijeliBuilder.show()

                    var uspjesnaPrijava = false
                    val korisnickoIme1 =
                        view.findViewById<EditText>(R.id.txtKorisnickoIme).text.toString()
                    val lozinka1 = view.findViewById<EditText>(R.id.txtLozinka).text.toString()

                    for (korisnik in listaKorisnika) {
                        if (korisnik.korisnickoIme == korisnickoIme1 && korisnik.lozinka == lozinka1) {
                            Toast.makeText(
                                context,
                                "Uspješna prijava preko korisničkog imena i lozinke",
                                Toast.LENGTH_SHORT
                            ).show()
                            uspjesnaPrijava = true
                            val listaUlogiraniKorisnik = mutableListOf<Korisnik>()
                            listaUlogiraniKorisnik.add(korisnik)
                            FirestorDohvatiSlikeKorisnika(
                                listaUlogiraniKorisnik,
                                object : FirestoreCallBack {
                                    override fun onCallback(lista: MutableList<Korisnik>) {
                                        for (korisnik0 in lista) {
                                            korisnik.slika = korisnik0.slika
                                        }
                                        dodijeliInstance.dismiss()
                                        komunikator.ulogirajKorisnika(korisnik)
                                    }
                                })
                            view.findViewById<EditText>(R.id.txtKorisnickoIme).setText("")
                            view.findViewById<EditText>(R.id.txtLozinka).setText("")
                            komunikator.ulogirajKorisnika(korisnik)
                        }
                    }
                    if (uspjesnaPrijava == false) {
                        dodijeliInstance.dismiss()
                        Toast.makeText(context, "Neuspješna prijava", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

        return view
    }
}