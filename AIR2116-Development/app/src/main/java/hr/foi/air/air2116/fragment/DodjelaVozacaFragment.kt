package hr.foi.air.air2116.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import hr.foi.air.air2116.R
import hr.foi.air.air2116.repository.*
import hr.foi.air.air2116.adapter.DodjelaVozacaAdapter
import hr.foi.air.air2116.dataClass.*
import hr.foi.air.core.Komunikator
import kotlinx.android.synthetic.main.dodjela_novog_vozaca.*
import kotlinx.android.synthetic.main.dodjela_vozaca.*
import kotlinx.android.synthetic.main.lista_poduzeca.*

class DodjelaVozacaFragment  : Fragment(R.layout.dodjela_vozaca), DodjelaVozacaAdapter.OnItemClickListener{
    private lateinit var komunikator: Komunikator
    var dodijeljeniVozaciList = mutableListOf<DodijeljeniVozaci>()
    var vozaciLista = mutableListOf<Vozaci>()
    var kamioniLista = mutableListOf<Kamioni>()
    var prikoliceLista = mutableListOf<Prikolice>()
    var velicina: Int = 0
    var vozaciImena = mutableListOf<String>()
    var kamioniRegistracija = mutableListOf<String>()
    var prikoliceRegistracija = mutableListOf<String>()
    var infoVozacaList = mutableListOf<InfoVozaca>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        val argumenti = arguments
        var adresaUtovar = argumenti!!.get("adrsUtovar")
        var adresaIstovar = argumenti!!.get("adrsIstovar")
        var brojTura = argumenti!!.get("brojTura")

        val txtAdrsUtovarUnosDodjela = view.findViewById(R.id.txtAdrsUtovarUnosDodjela) as TextView
        val txtAdresaIstovaraDodjela = view.findViewById(R.id.txtAdresaIstovaraDodjela) as TextView

        val txtBrojTura = view.findViewById(R.id.txtBrojTrazenihTuraDodjela) as TextView
        val txtBrojDodijeljenihTura = view.findViewById(R.id.txtBrojDodijeljenihTuraDodjela) as TextView

        txtAdrsUtovarUnosDodjela.setText(adresaUtovar.toString())
        txtAdresaIstovaraDodjela.setText(adresaIstovar.toString())
        txtBrojTura.setText(brojTura.toString())

        val txtNaslov = view.findViewById(R.id.txtNaslov) as TextView
        txtNaslov.text = "Dodjela vozača"

        //Pokazuje popup za dodjelu vozača, ali ako je već dodijeljen dovoljan broj tura samo izbacuje Toast message
        btnDodijeliVozacaKamionPrikolicuAdd.setOnClickListener {
            if(txtBrojDodijeljenihTura.text.toString().toInt() >= txtBrojTura.text.toString().toInt()){
                Toast.makeText(getActivity(), "Već je dodijeljen dovoljan broj tura!", Toast.LENGTH_SHORT).show()
            }else
                showDodijeliVozacePopup()
        }

    }


    //Popup za dodavanje vozača
    private fun showDodijeliVozacePopup() {
        val dodijeliView = LayoutInflater.from(activity).inflate(R.layout.dodjela_novog_vozaca, null)
        val dodijeliBuilder = AlertDialog.Builder(activity).setView(dodijeliView)
        val dodijeliInstance = dodijeliBuilder.show()

        var txtVozacUnos = dodijeliView.findViewById(R.id.txtDodijeliVozaca) as Spinner
        val txtKamionUnos = dodijeliView.findViewById(R.id.txtDodijeliKamion) as Spinner
        val txtPrikolicaUnos = dodijeliView.findViewById(R.id.txtDodijeliPrikolicu) as Spinner

        val btnDodijeli = dodijeliView.findViewById(R.id.btnDodijeliVozacaKamionPrikolicu) as Button

        var txtVozacNovi : String = ""
        var txtKamionNovi : String = ""
        var txtPrikolicaNovi : String = ""

        var kamioni = mutableListOf("PlaceHolderKamion1", "PlaceHolderKamion2", "PlaceHolderKamion3")
        var prikolice = mutableListOf("PlaceHolderPrikolice1", "PlaceHolderPrikolice2", "PlaceHolderPrikolice3")


        txtVozacUnos.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, vozaciImena)
        txtKamionUnos.adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, kamioniRegistracija)
        txtPrikolicaUnos.adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, prikoliceRegistracija)

        btnDodijeli.setOnClickListener {
            txtVozacNovi = txtVozacUnos.selectedItem.toString()
            txtKamionNovi = txtKamionUnos.selectedItem.toString()
            txtPrikolicaNovi = txtPrikolicaUnos.selectedItem.toString()
            var idVozaca: String = ""
            var idKamiona: String = ""
            var idPrikolice: String = ""

            val argumenti = arguments
            val idDogovorenPosao = argumenti!!.get("idDogovoreniPosao")

            for (i in 0 until vozaciLista.size) {
                if (vozaciLista[i].imePrezimeVozaca == txtVozacNovi) {
                    idVozaca = vozaciLista[i].idVozaca
                }
            }

            for (i in 0 until kamioniLista.size) {
                if (kamioniLista[i].regKamion == txtKamionNovi) {
                    idKamiona = kamioniLista[i].idKamion
                }
            }

            for (i in 0 until prikoliceLista.size) {
                if (prikoliceLista[i].regPrikolica == txtPrikolicaNovi) {
                    idPrikolice = prikoliceLista[i].idPrikolica
                }
            }
            val noviDodijeljeniVozaci: DodijeljeniVozaci = DodijeljeniVozaci(txtVozacNovi, txtKamionNovi, idKamiona, txtPrikolicaNovi, idPrikolice, "Nedovršeno", idDogovorenPosao.toString(), idVozaca)
            //Funkcija koja dodjeljuje odabranog vozača, kamion i prikolicu na FireStore
            FirestoreDodijeliVozaca(noviDodijeljeniVozaci, idDogovorenPosao.toString(), idVozaca)
            initData()
            dodijeliInstance.dismiss()
        }

        dodijeliView.setOnClickListener(){
            dodijeliInstance.dismiss()
        }
    }

    //Proslijeđivanje liste u adapter fragmenta
    private fun setRecyclerView() {
        val poduzecaAdapter = DodjelaVozacaAdapter(dodijeljeniVozaciList, this@DodjelaVozacaFragment)
        view?.findViewById<RecyclerView>(R.id.recycler_view)?.adapter = poduzecaAdapter
        view?.findViewById<RecyclerView>(R.id.recycler_view)?.setHasFixedSize(true)
    }

    //Funkcija koja dohvaća proslijeđene argumente i pokreće funkcije iz DataRepository-a
    private fun initData(){
        val argumenti = arguments
        val idDogovorenPosao = argumenti!!.get("idDogovoreniPosao").toString()
        var brojTura = argumenti!!.get("brojTura")

        //Funkcija koja dohvaća sve vozače dodijeljene odabranom dogovorenom poslu
        FirestoreDohvatiDodijeljeneVozace(idDogovorenPosao, object : FirestoreCallBackDodijeljeniVozaci{
            override fun onCallback(lista: MutableList<DodijeljeniVozaci>) {
                dodijeljeniVozaciList = lista
                dodijeljeniVozaciList.sortBy { it.vozac }
                var dodijeljene_ture : Int = 0
                for (i in 0 until dodijeljeniVozaciList.size){
                    if (dodijeljeniVozaciList[i].status != "Kvar"){
                        dodijeljene_ture++
                    }
                }
                txtBrojDodijeljenihTuraDodjela.text = dodijeljene_ture.toString()
                setRecyclerView()
            }
        })
        //Funkcija koja dohvaća sve vozače iz FireStore'a
        FirestoreDohvatiVozace(object : FirestoreCallBackVozaci {
            override fun onCallback(lista: MutableList<Vozaci>) {
                vozaciLista = lista
                vozaciImena.clear()
                for (i in 0 until vozaciLista.size){
                    vozaciImena.add(vozaciLista[i].imePrezimeVozaca)
                }
            }
        })
        //Funkcija koja dohvaća sve kamione iz FireStore'a
        FirestoreDohvatiKamione(object : FirestoreCallBackKamioni {
            override fun onCallback(lista: MutableList<Kamioni>) {
                kamioniLista = lista
                kamioniRegistracija.clear()
                for (i in 0 until kamioniLista.size){
                    kamioniRegistracija.add(kamioniLista[i].regKamion)
                }
            }
        })
        //Funkcija koja dohvaća sve prikolice iz FireStore'a
        FirestoreDohvatiPrikolice(object : FirestoreCallBackPrikolice {
            override fun onCallback(lista: MutableList<Prikolice>) {
                prikoliceLista = lista
                prikoliceRegistracija.clear()
                for (i in 0 until prikoliceLista.size){
                    prikoliceRegistracija.add(prikoliceLista[i].regPrikolica)
                }
            }
        })
    }

    //Funkcija koja se poziva iz adaptera pri svakim klikom na red u adapteru
    override fun onItemClick(position: Int, gumb: Int) {
        //Toast.makeText(getActivity(), "Kliknuo sam " + position + "id: "+ gumb, Toast.LENGTH_SHORT).show()
        when(gumb){
            -1 ->{
                //Toast.makeText(activity, "Kliknuo Si na Redak" + position, Toast.LENGTH_SHORT).show()
            }
                //Otvara InfoVozacaFragment onog vozača kojeg smo odabrali
            2131230854 ->{
                var idKliknutogVozaca: String = ""
                for (i in 0..vozaciLista.size-1){
                    if (dodijeljeniVozaciList[position].vozac == vozaciLista[i].imePrezimeVozaca){
                        idKliknutogVozaca = vozaciLista[i].idVozaca
                    }
                }
                komunikator = activity as Komunikator
                komunikator.otvoriInfo(position, idKliknutogVozaca)

            }
            //Brisanje ture odabranog vozača
            2131230885 -> {
                val argumenti = arguments
                val idDogovorenPosao = argumenti!!.get("idDogovoreniPosao").toString()
                val dodijeljenVozac = dodijeljeniVozaciList[position].idDodijeljenVozac
                FirestoreDeleteDodijeljenogVozaca(idDogovorenPosao, dodijeljenVozac)
                Thread.sleep(500)
                initData()
            }
        }
    }
}
//DodjelaVozacaFragment kreirao Borna Romić