package hr.foi.air.air2116.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView
import hr.foi.air.air2116.activity.MainActivity
import hr.foi.air.air2116.adapter.ListaKorisnikaAdapter
import hr.foi.air.core.Korisnik
import hr.foi.air.air2116.R
import hr.foi.air.air2116.repository.*
import java.util.*

class UpravljanjeKorisnicima : Fragment(), ListaKorisnikaAdapter.OnItemClickListener {

    var listaKorisnika = mutableListOf<Korisnik>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_upravljanje_korisnicima, container, false)
        //DATABASE READ
        val loadingDialogView =
            LayoutInflater.from(activity).inflate(R.layout.dialog_ucitavanje, null)
        loadingDialogView.findViewById<TextView>(R.id.txtLoadingDialog)
            .setText("Dohvaćanje podataka...")
        val loadingDialogBuilder = AlertDialog.Builder(activity).setView(loadingDialogView)
        loadingDialogBuilder.setCancelable(false)
        val loadingDialogInstance = loadingDialogBuilder.show()

        FirestoreDohvatiKorisnike(object : FirestoreCallBack {
            override fun onCallback(lista1: MutableList<Korisnik>) {
                FirestorDohvatiSlikeKorisnika(lista1, object : FirestoreCallBack {
                    override fun onCallback(lista: MutableList<Korisnik>) {
                        listaKorisnika = lista
                        val adapter =
                            ListaKorisnikaAdapter(listaKorisnika, this@UpravljanjeKorisnicima)
                        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
                        recyclerView.adapter = adapter
                        recyclerView.layoutManager = LinearLayoutManager(context)
                        loadingDialogInstance.dismiss()
                    }
                })
            }
        })

        //DODAVANJE NOVOG KORISNIKA
        view.findViewById<Button>(R.id.btnDodajKorisnika).setOnClickListener {
            val dodijeliView =
                LayoutInflater.from(activity).inflate(R.layout.popup_dodaj_korisnika, null)
            val dodijeliBuilder = AlertDialog.Builder(activity).setView(dodijeliView)
            val dodijeliInstance = dodijeliBuilder.show()

            dodijeliView.findViewById<Button>(R.id.btnOdustani).setOnClickListener {
                dodijeliInstance.dismiss()
            }

            //Generiranje nasumične lozinke
            dodijeliView.findViewById<Button>(R.id.btnGenerirajLozinku).setOnClickListener {
                val charset = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz0123456789"
                var randomLozinka = ""
                repeat(10) {
                    val znak = charset.random().toChar()
                    randomLozinka += znak
                }
                dodijeliView.findViewById<EditText>(R.id.txtLozinkaInput).setText(randomLozinka)
            }

            var opcijeUloga = arrayOf("Administrator", "Vozač", "Mehaničar")
            dodijeliView.findViewById<Spinner>(R.id.spinnerUloga).adapter = ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                opcijeUloga
            )
            var uloga: String = ""
            dodijeliView.findViewById<Spinner>(R.id.spinnerUloga).onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(p0: AdapterView<*>?) {
                    }

                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        if (p2 == 0) uloga = "Administrator"
                        if (p2 == 1) uloga = "Vozač"
                        if (p2 == 2) uloga = "Mehaničar"
                    }
                }

            dodijeliView.findViewById<Button>(R.id.btnPotvrdi).setOnClickListener {

                val loadingDialogView =
                    LayoutInflater.from(activity).inflate(R.layout.dialog_ucitavanje, null)
                loadingDialogView.findViewById<TextView>(R.id.txtLoadingDialog)
                    .setText("Spremanje podataka...")
                val loadingDialogBuilder = AlertDialog.Builder(activity).setView(loadingDialogView)
                loadingDialogBuilder.setCancelable(false)
                val loadingDialogInstance = loadingDialogBuilder.show()

                //Unos novog korisnika u bazu
                val ime = dodijeliView.findViewById<EditText>(R.id.txtImeInput).text.toString()
                val prezime =
                    dodijeliView.findViewById<EditText>(R.id.txtPrezimeInput).text.toString()
                val korisnickoIme =
                    dodijeliView.findViewById<EditText>(R.id.txtKorisnickoImeInput).text.toString()
                val lozinka =
                    dodijeliView.findViewById<EditText>(R.id.txtLozinkaInput).text.toString()
                val pin = ""
                val id = ""
                val slikaURL = "default.png"
                val mobitel = ""
                val email = ""
                val datumRodjenja: Date = Date(1971, 1, 1)

                //Provjera korisničkog imena
                var postoji = false
                if (korisnickoIme != "") {
                    for (korisnik in listaKorisnika) if (korisnik.korisnickoIme == korisnickoIme) postoji =
                        true
                }

                //Provjera unosa (polja moraju biti unešena)
                if (postoji == false) {
                    //Validacija unosa
                    if (ime != "" && prezime != "" && lozinka != "" && uloga != "" && korisnickoIme != "") {
                        val korisnik: Korisnik =
                            Korisnik(
                                korisnickoIme,
                                lozinka,
                                ime,
                                prezime,
                                uloga,
                                pin,
                                id,
                                slikaURL,
                                null,
                                mobitel,
                                email,
                                datumRodjenja
                            )
                        FirestoreUnesiKorisnika(korisnik)

                        //Osvježavanje recycler view-a
                        FirestoreDohvatiKorisnike(object : FirestoreCallBack {
                            override fun onCallback(lista1: MutableList<Korisnik>) {
                                FirestorDohvatiSlikeKorisnika(lista1, object : FirestoreCallBack {
                                    override fun onCallback(lista: MutableList<Korisnik>) {
                                        listaKorisnika = lista
                                        val adapter =
                                            ListaKorisnikaAdapter(
                                                listaKorisnika,
                                                this@UpravljanjeKorisnicima
                                            )
                                        val recyclerView =
                                            view.findViewById<RecyclerView>(R.id.recycler_view)
                                        recyclerView.adapter = adapter
                                        recyclerView.layoutManager = LinearLayoutManager(context)
                                        toastPoruka("Korisnik uspješno unesen")
                                    }
                                })
                            }
                        })
                    }
                }
                if (ime == "" || prezime == "" || lozinka == "" || uloga == "" || korisnickoIme == "" || postoji == true) {
                    Log.w("UNOS", "Neuspjeh")
                    toastPoruka("Greška prilikom unosa")
                }
                loadingDialogInstance.dismiss()
                dodijeliInstance.dismiss()
            }

            dodijeliView.setOnClickListener {
                dodijeliInstance.dismiss()
            }

        }

        //FILTRIRANJE
        view.findViewById<Button>(R.id.btnFiltriraj).setOnClickListener {
            val dodijeliView =
                LayoutInflater.from(activity)
                    .inflate(R.layout.popup_filtriraj_listu_korisnika, null)
            val dodijeliBuilder = AlertDialog.Builder(activity).setView(dodijeliView)
            val dodijeliInstance = dodijeliBuilder.show()

            var opcijeUloga = arrayOf("Uloga", "Administrator", "Vozač", "Mehaničar")
            dodijeliView.findViewById<Spinner>(R.id.spinnerFILTERUloga).adapter =
                ArrayAdapter<String>(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    opcijeUloga
                )
            var uloga: String = ""
            dodijeliView.findViewById<Spinner>(R.id.spinnerFILTERUloga).onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(p0: AdapterView<*>?) {
                    }

                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        if (p2 == 0) uloga = "uloga"
                        if (p2 == 1) uloga = "Administrator"
                        if (p2 == 2) uloga = "Vozač"
                        if (p2 == 3) uloga = "Mehaničar"
                    }
                }

            var opcijeSortiranje = arrayOf(" ", "Ime", "Prezime")
            dodijeliView.findViewById<Spinner>(R.id.spinnerFILTERsortiranjeAtribut).adapter =
                ArrayAdapter<String>(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    opcijeSortiranje
                )
            var sortiranje: String = ""
            dodijeliView.findViewById<Spinner>(R.id.spinnerFILTERsortiranjeAtribut).onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(p0: AdapterView<*>?) {
                    }

                    override fun onItemSelected(
                        p0: AdapterView<*>?,
                        p1: View?,
                        p2: Int,
                        p3: Long
                    ) {
                        if (p2 == 0) sortiranje = ""
                        if (p2 == 1) sortiranje = "ime"
                        if (p2 == 2) sortiranje = "prezime"
                    }
                }
            var opcijeSmjer = arrayOf("Rastuće", "Padajuće")
            dodijeliView.findViewById<Spinner>(R.id.spinnerFILTERsortiranjeSmjer).adapter =
                ArrayAdapter<String>(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    opcijeSmjer
                )
            var smjer: String = ""
            dodijeliView.findViewById<Spinner>(R.id.spinnerFILTERsortiranjeSmjer).onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        smjer = "rastuce"
                    }

                    override fun onItemSelected(
                        p0: AdapterView<*>?,
                        p1: View?,
                        p2: Int,
                        p3: Long
                    ) {
                        if (p2 == 0) smjer = "rastuce"
                        if (p2 == 1) smjer = "padajuce"
                    }
                }

            dodijeliView.findViewById<Button>(R.id.btnFILTERfiltriraj).setOnClickListener {
                //Obrada liste
                //Filtriranje po ulozi
                var filtriranaListaKorisnikaUloga = mutableListOf<Korisnik>()
                if (uloga != "uloga") {
                    for (korisnik in listaKorisnika) {
                        if (korisnik.uloga == uloga) filtriranaListaKorisnikaUloga.add(korisnik)
                    }
                }
                if (uloga == "uloga") {
                    for (korisnik in listaKorisnika) filtriranaListaKorisnikaUloga.add(korisnik)
                }
                //Filtriranje po željenom broju ispisa
                var filtriranaListaKorisnikaBroj = mutableListOf<Korisnik>()
                if (dodijeliView.findViewById<EditText>(R.id.txtFILTERinputBrojPrikaza).text.toString() != "") {
                    if (Integer.parseInt(dodijeliView.findViewById<EditText>(R.id.txtFILTERinputBrojPrikaza).text.toString()) > 0) {
                        val brojKorisnika =
                            Integer.parseInt(dodijeliView.findViewById<EditText>(R.id.txtFILTERinputBrojPrikaza).text.toString())
                        var brojac = 0
                        for (korisnik in filtriranaListaKorisnikaUloga) {
                            brojac++
                        }
                        for (i in 1..brojKorisnika) {
                            if (i <= brojac) filtriranaListaKorisnikaBroj.add(
                                filtriranaListaKorisnikaUloga[i - 1]
                            )
                            else break
                        }
                    }
                } else {
                    for (korisnik in filtriranaListaKorisnikaUloga) filtriranaListaKorisnikaBroj.add(
                        korisnik
                    )
                }
                //Filtriranje uz sortiranje
                if (sortiranje != "") {
                    if (sortiranje == "ime") {
                        if (smjer == "rastuce")
                            filtriranaListaKorisnikaBroj.sortBy { it.ime }
                        if (smjer == "padajuce")
                            filtriranaListaKorisnikaBroj.sortByDescending { it.ime }
                    }
                    if (sortiranje == "prezime") {
                        if (smjer == "rastuce")
                            filtriranaListaKorisnikaBroj.sortBy { it.prezime }
                        if (smjer == "padajuce")
                            filtriranaListaKorisnikaBroj.sortByDescending { it.prezime }
                    }
                }

                //Osvježavanje recycler view-a
                val adapter =
                    ListaKorisnikaAdapter(filtriranaListaKorisnikaBroj, this@UpravljanjeKorisnicima)
                val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(context)
                dodijeliInstance.dismiss()
            }

            dodijeliView.findViewById<Button>(R.id.btnFILTERodustani).setOnClickListener {
                dodijeliInstance.dismiss()
            }

            dodijeliView.setOnClickListener {
                dodijeliInstance.dismiss()
            }
        }
        return view
    }

    override fun onItemClick(position: Int) {
        val kliknutiKorisnik: Korisnik = listaKorisnika[position]
        //UPDATE
        //Otvaranje skočnog prozora za pregled i izmjenu podataka o korisniku
        val dodijeliView =
            LayoutInflater.from(activity)
                .inflate(R.layout.popup_pregledaj_i_izmjeni_korisnika, null)
        val dodijeliBuilder = AlertDialog.Builder(activity).setView(dodijeliView)
        val dodijeliInstance = dodijeliBuilder.show()
        dodijeliView.findViewById<EditText>(R.id.txtREADImeInput).setText(kliknutiKorisnik.ime)
        dodijeliView.findViewById<EditText>(R.id.txtREADPrezimeInput)
            .setText(kliknutiKorisnik.prezime)
        dodijeliView.findViewById<EditText>(R.id.txtREADMobitelInput)
            .setText(kliknutiKorisnik.mobitel)
        dodijeliView.findViewById<EditText>(R.id.txtREADEmailInput)
            .setText(kliknutiKorisnik.email)

        val godina = kliknutiKorisnik.datumRodenja?.year.toString()
        val mjesec = kliknutiKorisnik.datumRodenja?.month.toString()
        val dan = kliknutiKorisnik.datumRodenja?.day.toString()
        val datum = godina + "/" + mjesec + "/" + dan
        dodijeliView.findViewById<EditText>(R.id.txtREADDatumInput)
            .setText(datum)
        dodijeliView.findViewById<CircleImageView>(R.id.imgREADProfilna)
            .setImageBitmap(kliknutiKorisnik.slika)

        var opcijeUloga = arrayOf("Administrator", "Vozač", "Mehaničar")
        dodijeliView.findViewById<Spinner>(R.id.spinnerREADUloga).adapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_list_item_1,
            opcijeUloga
        )
        if (kliknutiKorisnik.uloga == "Administrator") dodijeliView.findViewById<Spinner>(R.id.spinnerREADUloga)
            .setSelection(0)
        if (kliknutiKorisnik.uloga == "Vozač") dodijeliView.findViewById<Spinner>(R.id.spinnerREADUloga)
            .setSelection(1)
        if (kliknutiKorisnik.uloga == "Mehaničar") dodijeliView.findViewById<Spinner>(R.id.spinnerREADUloga)
            .setSelection(2)
        var uloga: String = ""
        dodijeliView.findViewById<Spinner>(R.id.spinnerREADUloga).onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    if (p2 == 0) uloga = "Administrator"
                    if (p2 == 1) uloga = "Vozač"
                    if (p2 == 2) uloga = "Mehaničar"
                }
            }

        dodijeliView.findViewById<Button>(R.id.btnREADObrisi).setOnClickListener {

            val loadingDialogView =
                LayoutInflater.from(activity).inflate(R.layout.dialog_ucitavanje, null)
            loadingDialogView.findViewById<TextView>(R.id.txtLoadingDialog)
                .setText("Brisanje u tijeku...")
            val loadingDialogBuilder = AlertDialog.Builder(activity).setView(loadingDialogView)
            loadingDialogBuilder.setCancelable(false)
            val loadingDialogInstance = loadingDialogBuilder.show()

            //Brisanje korisnika u bazi
            FirestoreDeleteKorisnika(kliknutiKorisnik)

            //Refresh recycler view-a
            FirestoreDohvatiKorisnike(object : FirestoreCallBack {
                override fun onCallback(lista1: MutableList<Korisnik>) {
                    FirestorDohvatiSlikeKorisnika(lista1, object : FirestoreCallBack {
                        override fun onCallback(lista: MutableList<Korisnik>) {
                            listaKorisnika = lista
                            val adapter =
                                ListaKorisnikaAdapter(listaKorisnika, this@UpravljanjeKorisnicima)
                            val recyclerView = view?.findViewById<RecyclerView>(R.id.recycler_view)
                            recyclerView?.adapter = adapter
                            recyclerView?.layoutManager = LinearLayoutManager(context)
                            loadingDialogInstance.dismiss()
                        }
                    })
                }
            })

            dodijeliInstance.dismiss()
        }

        dodijeliView.findViewById<Button>(R.id.btnREADIzmjeni).setOnClickListener {

            val loadingDialogView =
                LayoutInflater.from(activity).inflate(R.layout.dialog_ucitavanje, null)
            loadingDialogView.findViewById<TextView>(R.id.txtLoadingDialog)
                .setText("Spremanje podataka...")
            val loadingDialogBuilder = AlertDialog.Builder(activity).setView(loadingDialogView)
            loadingDialogBuilder.setCancelable(false)
            val loadingDialogInstance = loadingDialogBuilder.show()

            kliknutiKorisnik.ime =
                dodijeliView.findViewById<EditText>(R.id.txtREADImeInput).text.toString()
            kliknutiKorisnik.prezime =
                dodijeliView.findViewById<EditText>(R.id.txtREADPrezimeInput).text.toString()
            kliknutiKorisnik.uloga = uloga
            kliknutiKorisnik.mobitel =
                dodijeliView.findViewById<EditText>(R.id.txtREADMobitelInput).text.toString()
            kliknutiKorisnik.email =
                dodijeliView.findViewById<EditText>(R.id.txtREADEmailInput).text.toString()

            val datumInput: String =
                dodijeliView.findViewById<EditText>(R.id.txtREADDatumInput).text.toString()
            val datum = Date(
                datumInput.split("/")[0].toInt(),
                datumInput.split("/")[1].toInt(),
                datumInput.split("/")[2].toInt()
            )

            kliknutiKorisnik.datumRodenja = datum
            FirestoreUpdateKorisnika(kliknutiKorisnik)

            //Refresh recycler view-a
            FirestoreDohvatiKorisnike(object : FirestoreCallBack {
                override fun onCallback(lista1: MutableList<Korisnik>) {
                    FirestorDohvatiSlikeKorisnika(lista1, object : FirestoreCallBack {
                        override fun onCallback(lista: MutableList<Korisnik>) {
                            listaKorisnika = lista
                            val adapter =
                                ListaKorisnikaAdapter(listaKorisnika, this@UpravljanjeKorisnicima)
                            val recyclerView = view?.findViewById<RecyclerView>(R.id.recycler_view)
                            recyclerView?.adapter = adapter
                            recyclerView?.layoutManager = LinearLayoutManager(context)
                            loadingDialogInstance.dismiss()
                        }
                    })
                }
            })

            dodijeliInstance.dismiss()
        }

        dodijeliView.setOnClickListener {
            dodijeliInstance.dismiss()
        }

        dodijeliView.findViewById<Button>(R.id.btnREADOdustani).setOnClickListener {
            dodijeliInstance.dismiss()
        }
    }

    fun toastPoruka(poruka: String) {
        Toast.makeText(context, poruka, Toast.LENGTH_SHORT).show()
    }
}