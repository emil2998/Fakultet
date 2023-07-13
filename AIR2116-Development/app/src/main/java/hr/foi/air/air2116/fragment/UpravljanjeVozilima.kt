package hr.foi.air.air2116.fragment

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.net.toUri
import androidx.core.view.drawToBitmap
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView
import hr.foi.air.air2116.R
import hr.foi.air.air2116.activity.MainActivity
import hr.foi.air.air2116.adapter.ListaKorisnikaAdapter
import hr.foi.air.air2116.adapter.ListaVozilaAdapter
import hr.foi.air.air2116.dataClass.Vozilo
import hr.foi.air.air2116.repository.*
import java.net.URI
import java.util.*

class UpravljanjeVozilima : Fragment(), ListaVozilaAdapter.OnItemClickListener {

    var listaVozila = mutableListOf<Vozilo>()
    var ImageUri : Uri = "asda".toUri()
    var bitmap: Bitmap? = null
    var promijenaSlike=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_upravljanje_vozilima, container, false)

        //DATABASE READ
        val loadingDialogView =
            LayoutInflater.from(activity).inflate(R.layout.dialog_ucitavanje, null)
        loadingDialogView.findViewById<TextView>(R.id.txtLoadingDialog)
            .setText("Dohvaćanje podataka...")
        val loadingDialogBuilder = AlertDialog.Builder(activity).setView(loadingDialogView)
        loadingDialogBuilder.setCancelable(false)
        val loadingDialogInstance = loadingDialogBuilder.show()

        FirestoreDohvatiVozila(object : FirestoreCallBackVozilo {
            override fun onCallback(lista1: MutableList<Vozilo>) {
                FirestorDohvatiSlikeVozila(lista1, object : FirestoreCallBackVozilo {
                    override fun onCallback(lista: MutableList<Vozilo>) {
                        listaVozila = lista
                        val adapter =
                            ListaVozilaAdapter(listaVozila, this@UpravljanjeVozilima)
                        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_viewVozilo)
                        recyclerView.adapter = adapter
                        recyclerView.layoutManager = LinearLayoutManager(context)
                        loadingDialogInstance.dismiss()
                    }
                })
            }
        })

        //DODAVANJE NOVOG VOZILA
        view.findViewById<Button>(R.id.btnDodajVozilo).setOnClickListener {
            val dodijeliView =
                LayoutInflater.from(activity).inflate(R.layout.popup_dodaj_vozilo, null)
            val dodijeliBuilder = AlertDialog.Builder(activity).setView(dodijeliView)
            val dodijeliInstance = dodijeliBuilder.show()

            dodijeliView.findViewById<Button>(R.id.btnOdustaniVozilo).setOnClickListener {
                dodijeliInstance.dismiss()
            }

            var opcijeVrsta = arrayOf("Kamion", "Prikolica")
            dodijeliView.findViewById<Spinner>(R.id.spinnerVrstaVozila).adapter =
                ArrayAdapter<String>(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    opcijeVrsta
                )
            var vrsta: String = ""
            dodijeliView.findViewById<Spinner>(R.id.spinnerVrstaVozila).onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(p0: AdapterView<*>?) {
                    }

                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        if (p2 == 0) vrsta = "Kamion"
                        if (p2 == 1) vrsta = "Prikolica"
                    }
                }

            dodijeliView.findViewById<Button>(R.id.btnPotvrdiVozilo).setOnClickListener {

                val loadingDialogView =
                    LayoutInflater.from(activity).inflate(R.layout.dialog_ucitavanje, null)
                loadingDialogView.findViewById<TextView>(R.id.txtLoadingDialog)
                    .setText("Spremanje podataka...")
                val loadingDialogBuilder = AlertDialog.Builder(activity).setView(loadingDialogView)
                loadingDialogBuilder.setCancelable(false)
                val loadingDialogInstance = loadingDialogBuilder.show()

                //Unos novog korisnika u bazu
                val nazivModela =
                    dodijeliView.findViewById<EditText>(R.id.txtNazivModelaInput).text.toString()
                val registracija =
                    dodijeliView.findViewById<EditText>(R.id.txtRegistracijaInput).text.toString()
                val godinaProizvodnje =
                    dodijeliView.findViewById<EditText>(R.id.txtLabelGodinaProizvodnjeInput).text.toString()
                val maxNosivost =
                    dodijeliView.findViewById<EditText>(R.id.txtNosivostInput).text.toString()
                var slikaURL = "prikolicaDefault.jpg"
                var snaga = ""
                if (vrsta != "Prikolica") {
                    slikaURL = "kamionDefault.jpg"
                    snaga = dodijeliView.findViewById<EditText>(R.id.txtSnagaInput).text.toString()
                }
                val idVozilo = ""
                val registriranDo =
                    dodijeliView.findViewById<EditText>(R.id.txtRegistriranDoInput).text.toString()
                var registriranoDo: Date? = null
                if (registriranDo != "") {
                    var godina = registriranDo.split("/")[0].toInt()
                    var mjesec = registriranDo.split("/")[1].toInt()
                    var dan = registriranDo.split("/")[2].toInt()
                    registriranoDo = Date(godina, mjesec, dan)
                    Log.w("Provjeraaa", registriranoDo.day.toString())
                }
                val ispravan = "true"

                //Provjera unosa (polja moraju biti unešena) i validacija
                if (nazivModela != "" && registracija != "" && godinaProizvodnje != "") {
                    val vozilo: Vozilo =
                        Vozilo(
                            idVozilo,
                            ispravan,
                            godinaProizvodnje,
                            nazivModela,
                            maxNosivost,
                            registracija,
                            registriranoDo,
                            slikaURL,
                            null,
                            snaga,
                            vrsta
                        )
                    FirestoreUnesiVozilo(vozilo)

                    //Osvježavanje recycler view-a
                    FirestoreDohvatiVozila(object : FirestoreCallBackVozilo {
                        override fun onCallback(lista1: MutableList<Vozilo>) {
                            FirestorDohvatiSlikeVozila(lista1, object : FirestoreCallBackVozilo {
                                override fun onCallback(lista: MutableList<Vozilo>) {
                                    listaVozila = lista
                                    val adapter =
                                        ListaVozilaAdapter(
                                            listaVozila,
                                            this@UpravljanjeVozilima
                                        )
                                    val recyclerView =
                                        view.findViewById<RecyclerView>(R.id.recycler_viewVozilo)
                                    recyclerView.adapter = adapter
                                    recyclerView.layoutManager = LinearLayoutManager(context)
                                }
                            })
                        }
                    })

                }
                if (nazivModela == "" || registracija == "" || godinaProizvodnje == "") {
                    Log.w("UNOS", "Neuspjeh")
                    //toastPoruka("Greška prilikom unosa")
                }
                loadingDialogInstance.dismiss()
                dodijeliInstance.dismiss()
            }

            dodijeliView.setOnClickListener {
                dodijeliInstance.dismiss()
            }


        }

        //FILTRIRANJE
        view.findViewById<Button>(R.id.btnFiltrirajVozila).setOnClickListener {
            val dodijeliView =
                LayoutInflater.from(activity)
                    .inflate(R.layout.popup_filtriraj_listu_vozila, null)
            val dodijeliBuilder = AlertDialog.Builder(activity).setView(dodijeliView)
            val dodijeliInstance = dodijeliBuilder.show()

            var opcijeVrsta = arrayOf("Vrsta", "Kamion", "Prikolica")
            dodijeliView.findViewById<Spinner>(R.id.spinnerFILTERVrstaVozila).adapter =
                ArrayAdapter<String>(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    opcijeVrsta
                )
            var vrsta: String = ""
            dodijeliView.findViewById<Spinner>(R.id.spinnerFILTERVrstaVozila).onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(p0: AdapterView<*>?) {
                    }

                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        if (p2 == 0) vrsta = "Vrsta"
                        if (p2 == 1) vrsta = "Kamion"
                        if (p2 == 2) vrsta = "Prikolica"
                    }
                }

            var opcijeSortiranje = arrayOf(" ", "Naziv modela", "Registracija")
            dodijeliView.findViewById<Spinner>(R.id.spinnerFILTERsortiranjeAtributVozilo).adapter =
                ArrayAdapter<String>(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    opcijeSortiranje
                )
            var sortiranje: String = ""
            dodijeliView.findViewById<Spinner>(R.id.spinnerFILTERsortiranjeAtributVozilo).onItemSelectedListener =
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
                        if (p2 == 1) sortiranje = "Naziv"
                        if (p2 == 2) sortiranje = "Registracija"
                    }
                }
            var opcijeSmjer = arrayOf("Rastuće", "Padajuće")
            dodijeliView.findViewById<Spinner>(R.id.spinnerFILTERsortiranjeSmjerVozilo).adapter =
                ArrayAdapter<String>(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    opcijeSmjer
                )
            var smjer: String = ""
            dodijeliView.findViewById<Spinner>(R.id.spinnerFILTERsortiranjeSmjerVozilo).onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        smjer = "Rastuće"
                    }

                    override fun onItemSelected(
                        p0: AdapterView<*>?,
                        p1: View?,
                        p2: Int,
                        p3: Long
                    ) {
                        if (p2 == 0) smjer = "Rastuće"
                        if (p2 == 1) smjer = "Padajuće"
                    }
                }

            dodijeliView.findViewById<Button>(R.id.btnFILTERfiltrirajVozilo).setOnClickListener {
                //Obrada liste
                //Filtriranje po ulozi
                var filtriranaListaVozilaVrsta = mutableListOf<Vozilo>()
                if (vrsta != "Vrsta") {
                    for (vozilo in listaVozila) {
                        if (vozilo.vrstaVozila == vrsta) filtriranaListaVozilaVrsta.add(vozilo)
                    }
                }
                if (vrsta == "Vrsta") {
                    for (vozilo in listaVozila) filtriranaListaVozilaVrsta.add(vozilo)
                }
                //Filtriranje po željenom broju ispisa
                var filtriranaListaVozilaBroj = mutableListOf<Vozilo>()
                if (dodijeliView.findViewById<EditText>(R.id.txtFILTERinputBrojPrikazaVozilo).text.toString() != "") {
                    if (Integer.parseInt(dodijeliView.findViewById<EditText>(R.id.txtFILTERinputBrojPrikazaVozilo).text.toString()) > 0) {
                        val brojVozila =
                            Integer.parseInt(dodijeliView.findViewById<EditText>(R.id.txtFILTERinputBrojPrikazaVozilo).text.toString())
                        var brojac = 0
                        for (vozilo in filtriranaListaVozilaVrsta) {
                            brojac++
                        }
                        for (i in 1..brojVozila) {
                            if (i <= brojac) filtriranaListaVozilaBroj.add(
                                filtriranaListaVozilaVrsta[i - 1]
                            )
                            else break
                        }
                    }
                } else {
                    for (vozilo in filtriranaListaVozilaVrsta) filtriranaListaVozilaBroj.add(
                        vozilo
                    )
                }
                //Filtriranje uz sortiranje
                if (sortiranje != "") {
                    if (sortiranje == "Naziv") {
                        if (smjer == "Rastuće")
                            filtriranaListaVozilaBroj.sortBy { it.nazivModela }
                        if (smjer == "Padajuće")
                            filtriranaListaVozilaBroj.sortByDescending { it.nazivModela }
                    }
                    if (sortiranje == "Registracija") {
                        if (smjer == "Rastuće")
                            filtriranaListaVozilaBroj.sortBy { it.registracija }
                        if (smjer == "Padajuće")
                            filtriranaListaVozilaBroj.sortByDescending { it.registracija }
                    }
                }

                //Osvježavanje recycler view-a
                val adapter =
                    ListaVozilaAdapter(filtriranaListaVozilaBroj, this@UpravljanjeVozilima)
                val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_viewVozilo)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(context)
                dodijeliInstance.dismiss()
            }

            dodijeliView.findViewById<Button>(R.id.btnFILTERodustaniVozilo).setOnClickListener {
                dodijeliInstance.dismiss()
            }

            dodijeliView.setOnClickListener {
                dodijeliInstance.dismiss()
            }
        }

        return view
    }

    override fun onItemClick(position: Int) {
        val kliknutoVozilo: Vozilo = listaVozila[position]
        Log.w("KliknutoVozilo",kliknutoVozilo.nazivModela)
        //UPDATE
        //Otvaranje skočnog prozora za pregled i izmjenu podataka o vozilu
        val dodijeliView =
            LayoutInflater.from(activity)
                .inflate(R.layout.popup_pregledaj_i_izmjeni_vozilo, null)
        val dodijeliBuilder = AlertDialog.Builder(activity).setView(dodijeliView)
        val dodijeliInstance = dodijeliBuilder.show()
        dodijeliView.findViewById<EditText>(R.id.txtREADNazivModelaInput).setText(kliknutoVozilo.nazivModela)
        dodijeliView.findViewById<EditText>(R.id.txtREADRegistracijaInput)
            .setText(kliknutoVozilo.registracija)
        dodijeliView.findViewById<EditText>(R.id.txtREADGodinaProizvodnjeInput)
            .setText(kliknutoVozilo.godinaProizvodnje)
        dodijeliView.findViewById<EditText>(R.id.txtREADNosivostInput)
            .setText(kliknutoVozilo.nosivost)
        if (kliknutoVozilo.vrstaVozila=="Kamion"){
            dodijeliView.findViewById<EditText>(R.id.txtREADSnagaInput).setText(kliknutoVozilo.snaga)
        }
        if(kliknutoVozilo.vrstaVozila=="Prikolica"){
            dodijeliView.findViewById<TextView>(R.id.txtREADLabelSnaga).isEnabled=false
            dodijeliView.findViewById<EditText>(R.id.txtREADSnagaInput).isEnabled=false
        }

        val godina = kliknutoVozilo.registriranDo?.year.toString()
        val mjesec = kliknutoVozilo.registriranDo?.month.toString()
        val dan = kliknutoVozilo.registriranDo?.day.toString()
        val datum = dan+"/"+mjesec+"/"+godina
        dodijeliView.findViewById<EditText>(R.id.txtREADRegistriranDoInput)
            .setText(datum)
        dodijeliView.findViewById<CircleImageView>(R.id.imgREADProfilnaVozilo)
            .setImageBitmap(kliknutoVozilo.slika)



        dodijeliView.findViewById<Button>(R.id.btnREADObrisiVozilo).setOnClickListener {

            val loadingDialogView =
                LayoutInflater.from(activity).inflate(R.layout.dialog_ucitavanje, null)
            loadingDialogView.findViewById<TextView>(R.id.txtLoadingDialog)
                .setText("Brisanje u tijeku...")
            val loadingDialogBuilder = AlertDialog.Builder(activity).setView(loadingDialogView)
            loadingDialogBuilder.setCancelable(false)
            val loadingDialogInstance = loadingDialogBuilder.show()

            //Brisanje korisnika u bazi
            FirestoreDeleteVozilo(kliknutoVozilo)

            //Refresh recycler view-a
            FirestoreDohvatiVozila(object : FirestoreCallBackVozilo {
                override fun onCallback(lista1: MutableList<Vozilo>) {
                    FirestorDohvatiSlikeVozila(lista1, object : FirestoreCallBackVozilo {
                        override fun onCallback(lista: MutableList<Vozilo>) {
                            listaVozila = lista
                            val adapter =
                                ListaVozilaAdapter(
                                    listaVozila,
                                    this@UpravljanjeVozilima
                                )
                            val recyclerView =
                                view?.findViewById<RecyclerView>(R.id.recycler_viewVozilo)
                            recyclerView?.adapter = adapter
                            recyclerView?.layoutManager = LinearLayoutManager(context)
                        }
                    })
                }
            })
            loadingDialogInstance.dismiss()
            dodijeliInstance.dismiss()
        }

        dodijeliView.findViewById<Button>(R.id.btnPromijeniSlikuVozila).setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent,100)

            dodijeliView.findViewById<CircleImageView>(R.id.imgREADProfilnaVozilo).setImageURI(ImageUri)

            kliknutoVozilo.slika=bitmap

        }

        dodijeliView.findViewById<Button>(R.id.btnREADIzmjeniVozilo).setOnClickListener {

            val loadingDialogView =
                LayoutInflater.from(activity).inflate(R.layout.dialog_ucitavanje, null)
            loadingDialogView.findViewById<TextView>(R.id.txtLoadingDialog)
                .setText("Spremanje podataka...")
            val loadingDialogBuilder = AlertDialog.Builder(activity).setView(loadingDialogView)
            loadingDialogBuilder.setCancelable(false)
            val loadingDialogInstance = loadingDialogBuilder.show()

            kliknutoVozilo.nazivModela =
                dodijeliView.findViewById<EditText>(R.id.txtREADNazivModelaInput).text.toString()
            kliknutoVozilo.godinaProizvodnje =
                dodijeliView.findViewById<EditText>(R.id.txtREADGodinaProizvodnjeInput).text.toString()
            kliknutoVozilo.registracija = dodijeliView.findViewById<EditText>(R.id.txtREADRegistracijaInput).text.toString()
            kliknutoVozilo.nosivost =
                dodijeliView.findViewById<EditText>(R.id.txtREADNosivostInput).text.toString()
            if(kliknutoVozilo.vrstaVozila=="Kamion") {
                kliknutoVozilo.snaga =
                    dodijeliView.findViewById<EditText>(R.id.txtREADSnagaInput).text.toString()
            }
            val datumInput: String =
                dodijeliView.findViewById<EditText>(R.id.txtREADRegistriranDoInput).text.toString()
            val datum = Date(
                datumInput.split("/")[0].toInt(),
                datumInput.split("/")[1].toInt(),
                datumInput.split("/")[2].toInt()
            )

            kliknutoVozilo.registriranDo = datum

            if(promijenaSlike==0){
                FirestoreUpdateVozilo(kliknutoVozilo, object : FirestoreCallBackUpdateVozila {
                    override fun onCallBack() {
                        FirestoreDohvatiVozila(object : FirestoreCallBackVozilo {
                            override fun onCallback(lista1: MutableList<Vozilo>) {
                                FirestorDohvatiSlikeVozila(lista1, object : FirestoreCallBackVozilo {
                                    override fun onCallback(lista: MutableList<Vozilo>) {
                                        listaVozila = lista
                                        val adapter =
                                            ListaVozilaAdapter(
                                                listaVozila,
                                                this@UpravljanjeVozilima
                                            )
                                        val recyclerView =
                                            view?.findViewById<RecyclerView>(R.id.recycler_viewVozilo)
                                        recyclerView?.adapter = adapter
                                        recyclerView?.layoutManager = LinearLayoutManager(context)
                                        loadingDialogInstance.dismiss()
                                    }
                                })
                            }
                        })
                    }
                })
            }
            if(promijenaSlike==1){
                FirestorePromjeniSlikuVozila(kliknutoVozilo.slikaURL,ImageUri,kliknutoVozilo,object : FirestoreCallBackSlikaVozila {
                    override fun onCallback() {
                        kliknutoVozilo.slikaURL=kliknutoVozilo.idVozila
                        FirestoreUpdateVozilo(kliknutoVozilo, object : FirestoreCallBackUpdateVozila {
                            override fun onCallBack() {
                                FirestoreDohvatiVozila(object : FirestoreCallBackVozilo {
                                    override fun onCallback(lista1: MutableList<Vozilo>) {
                                        FirestorDohvatiSlikeVozila(lista1, object : FirestoreCallBackVozilo {
                                            override fun onCallback(lista: MutableList<Vozilo>) {
                                                listaVozila = lista
                                                val adapter =
                                                    ListaVozilaAdapter(
                                                        listaVozila,
                                                        this@UpravljanjeVozilima
                                                    )
                                                val recyclerView =
                                                    view?.findViewById<RecyclerView>(R.id.recycler_viewVozilo)
                                                recyclerView?.adapter = adapter
                                                recyclerView?.layoutManager = LinearLayoutManager(context)
                                                loadingDialogInstance.dismiss()
                                            }
                                        })
                                    }
                                })
                            }
                        })
                    }
                })
            }

            dodijeliInstance.dismiss()
        }

        dodijeliView.setOnClickListener {
            dodijeliInstance.dismiss()
        }

        dodijeliView.findViewById<Button>(R.id.btnREADOdustaniVozilo).setOnClickListener {
            dodijeliInstance.dismiss()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==100 && resultCode==RESULT_OK){
            ImageUri=data?.data!!
            bitmap= MediaStore.Images.Media.getBitmap(context?.contentResolver,ImageUri)
            promijenaSlike=1
        }
    }
}