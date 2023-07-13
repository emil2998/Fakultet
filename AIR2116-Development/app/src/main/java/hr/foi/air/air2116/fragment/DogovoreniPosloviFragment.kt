package hr.foi.air.air2116.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import hr.foi.air.air2116.dataClass.Poduzeca
import hr.foi.air.air2116.R
import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.dogovoreni_poslovi_toolbar.*
import android.widget.Toast
import android.widget.TextView
import android.widget.EditText
import hr.foi.air.air2116.repository.*
import hr.foi.air.air2116.adapter.DogovoreniPosloviAdapter
import hr.foi.air.core.Komunikator
import kotlinx.android.synthetic.main.lista_poduzeca.*


class DogovoreniPosloviFragment : Fragment(R.layout.dogovoreni_poslovi), DogovoreniPosloviAdapter.OnItemClickListener {
    private lateinit var komunikator: Komunikator
    var poduzecaList = ArrayList<Poduzeca>()
    var brojUnosa : Int = 0
    var sort : String = "Status"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        setRecyclerView()
        val txtNaslov = view.findViewById(R.id.txtNaslov) as TextView
        txtNaslov.text = "Dogovoreni poslovi"

        //Otvara Filter popup za odabir filtriranja liste
        btnFilter.setOnClickListener {
            showFilterPopup()
        }
        //Otvara popup za dodavanje novog dogovorenog posla
        btnDodaj.setOnClickListener {
            showDodajPopup()
        }
    }

    //Otvara filter popup
    fun showFilterPopup(){
        val adapter = DogovoreniPosloviAdapter(poduzecaList, this@DogovoreniPosloviFragment)
        //Inflate the dialog as custom view
        val filterView = LayoutInflater.from(activity).inflate(R.layout.dogovoreni_poslovi_filter, null)

        //AlertDialogBuilder
        val filterBuilder = AlertDialog.Builder(activity).setView(filterView)

        //show dialog
        val  filterInstance = filterBuilder.show()

        val btnDatum = filterView.findViewById(R.id.btnDatumSort) as Button
        val btnPoduzece = filterView.findViewById(R.id.btnPoduzeceSort) as Button
        val btnStatus = filterView.findViewById(R.id.btnStatusSort) as Button
        val btnAdd = filterView.findViewById(R.id.btnAdd) as ImageButton
        val btnSubtract = filterView.findViewById(R.id.btnSubtract) as ImageButton
        val btnFilter = filterView.findViewById(R.id.btnFiltriraj) as Button

        val txtBroj: EditText = filterView.findViewById(R.id.txtBrojPrikaza)
        txtBroj.setText(poduzecaList.size.toString(), TextView.BufferType.EDITABLE)
        var broj: Int = txtBroj.text.toString().trim().toInt()

        if (sort == "Datum"){
            btnDatum.setBackgroundColor(resources.getColor(R.color.rp_blue))
            btnStatus.setBackgroundColor(resources.getColor(R.color.rp_blue_light))
            btnPoduzece.setBackgroundColor(resources.getColor(R.color.rp_blue_light))
        }
        if (sort == "Status"){
            btnStatus.setBackgroundColor(resources.getColor(R.color.rp_blue))
            btnDatum.setBackgroundColor(resources.getColor(R.color.rp_blue_light))
            btnPoduzece.setBackgroundColor(resources.getColor(R.color.rp_blue_light))
        }
        if (sort == "Poduzece"){
            btnPoduzece.setBackgroundColor(resources.getColor(R.color.rp_blue))
            btnStatus.setBackgroundColor(resources.getColor(R.color.rp_blue_light))
            btnDatum.setBackgroundColor(resources.getColor(R.color.rp_blue_light))
        }

        btnDatum.setOnClickListener {
            btnDatum.setBackgroundColor(resources.getColor(R.color.rp_blue))
            btnStatus.setBackgroundColor(resources.getColor(R.color.rp_blue_light))
            btnPoduzece.setBackgroundColor(resources.getColor(R.color.rp_blue_light))
            sort = "Datum"

        }
        btnStatus.setOnClickListener {
            btnStatus.setBackgroundColor(resources.getColor(R.color.rp_blue))
            btnDatum.setBackgroundColor(resources.getColor(R.color.rp_blue_light))
            btnPoduzece.setBackgroundColor(resources.getColor(R.color.rp_blue_light))
            sort = "Status"

        }
        btnPoduzece.setOnClickListener {
            btnPoduzece.setBackgroundColor(resources.getColor(R.color.rp_blue))
            btnStatus.setBackgroundColor(resources.getColor(R.color.rp_blue_light))
            btnDatum.setBackgroundColor(resources.getColor(R.color.rp_blue_light))
            sort = "Poduzece"
        }

        btnAdd.setOnClickListener {
            broj += 1
            if (broj >= poduzecaList.size){
                broj = poduzecaList.size
            }
            txtBroj.setText(broj.toString(), TextView.BufferType.EDITABLE)
        }
        btnSubtract.setOnClickListener {
            broj -= 1
            if (broj <= 1){
                broj = 1
            }
            txtBroj.setText(broj.toString(), TextView.BufferType.EDITABLE)
        }


        btnFilter.setOnClickListener {
            if (sort == "Poduzece"){
                poduzecaList.sortByDescending { it.Poduzece }
            }else if (sort == "Datum"){
                poduzecaList.sortByDescending { it.Datum }
            } else if (sort == "Status"){
                poduzecaList.sortByDescending { it.Status }
            }
            broj = txtBroj.text.toString().toInt()
            //Toast.makeText(activity, broj.toString(), Toast.LENGTH_SHORT).show()


            var brojFilterPoduzeca = ArrayList<Poduzeca>()

            for (i in 1..broj){
                brojFilterPoduzeca.add(poduzecaList[i-1])
            }

            adapter.notifyItemInserted(0)
            val poduzecaAdapter = DogovoreniPosloviAdapter(brojFilterPoduzeca, this@DogovoreniPosloviFragment)
            view?.findViewById<RecyclerView>(R.id.recycler_view)?.adapter = poduzecaAdapter
            view?.findViewById<RecyclerView>(R.id.recycler_view)?.setHasFixedSize(true)
            filterInstance.dismiss()
        }

        filterView.setOnClickListener(){
            filterInstance.dismiss()
        }
    }

    //Funkcija koja provjerava može li se unesena string vrijednost koristiti kao integer
    fun isInteger(str: String?) = str?.toIntOrNull()?.let { true } ?: false

    //Funkcija koja pokazuje popup za dodavanje novog dogovorenog posla
    private fun showDodajPopup() {
        val adapter = DogovoreniPosloviAdapter(poduzecaList,this@DogovoreniPosloviFragment)

        val dodajView = LayoutInflater.from(activity).inflate(R.layout.unos_uredivanje_posla, null)
        val dodajBuilder = AlertDialog.Builder(activity).setView(dodajView)
        val dodajInstance = dodajBuilder.show()

        val txtPoduzeceUnos = dodajView.findViewById(R.id.txtPoduzeceUnos) as EditText
        val txtAdrsUtovarUnos = dodajView.findViewById(R.id.txtAdrsUtovarUnos) as EditText
        val txtAdrsIstovarUnos = dodajView.findViewById(R.id.txtAdrsIstovarUnos) as EditText
        val txtDatumUnos = dodajView.findViewById(R.id.txtDatumUnos) as EditText
        val txtRobaUnos = dodajView.findViewById(R.id.txtRobaUnos) as EditText
        val txtBrojTuraUnos = dodajView.findViewById(R.id.txtBrojTuraUnos) as EditText
        val txtKontaktUnos = dodajView.findViewById(R.id.txtKontaktUnos) as EditText
        val txtEmailUnos = dodajView.findViewById(R.id.txtEmailUnos) as EditText

        val btnDodajNovi = dodajView.findViewById(R.id.btnPotvrdi) as ImageButton

        var txtPoduzeceNovi : String = ""
        var txtAdrsUtovarNovi : String = ""
        var txtAdrsIstovarNovi : String = ""
        var txtDatumNovi : String = ""
        var txtRobaNovi : String = ""
        var txtBrojTuraNovi : String = ""
        var txtKontaktNovi : String = ""
        var txtEmailNovi : String = ""
        var txtStatusNovi : String = "Nedovršeno"

        btnDodajNovi.setOnClickListener {
            txtPoduzeceNovi = txtPoduzeceUnos.text.toString()
            txtAdrsUtovarNovi = txtAdrsUtovarUnos.text.toString()
            txtAdrsIstovarNovi = txtAdrsIstovarUnos.text.toString()
            txtDatumNovi = txtDatumUnos.text.toString()
            txtRobaNovi = txtRobaUnos.text.toString()
            txtBrojTuraNovi = txtBrojTuraUnos.text.toString()
            txtKontaktNovi = txtKontaktUnos.text.toString()
            txtEmailNovi = txtEmailUnos.text.toString()

            var ispravanUnos : Boolean = false
            var ispravanBroj : Boolean = false

            if (txtPoduzeceNovi == "" || txtAdrsUtovarNovi == "" || txtAdrsIstovarNovi == "" || txtDatumNovi == "" ||
                    txtRobaNovi == "" || txtBrojTuraNovi == "" || txtKontaktNovi == "" || txtEmailNovi == ""){
                Toast.makeText(activity, "Nisu svi podaci uneseni!", Toast.LENGTH_SHORT).show()
            }else{
                ispravanUnos = true
            }

            if(isInteger(txtBrojTuraNovi))
                ispravanBroj = true
            else
                Toast.makeText(activity, "Broj potrebnih tura mora biti broj!", Toast.LENGTH_SHORT).show()

            if (ispravanUnos == true && ispravanBroj == true){
                val poduzece: Poduzeca = Poduzeca(txtPoduzeceNovi, txtDatumNovi, txtStatusNovi, txtAdrsUtovarNovi, txtAdrsIstovarNovi, txtRobaNovi, txtBrojTuraNovi, txtKontaktNovi, txtEmailNovi, "")
                FirestoreUnesiDogovoreniPosao(poduzece)
                initData()

                brojUnosa = poduzecaList.size.toInt()
                brojUnosa -= 1
                adapter.notifyItemInserted(0)
                dodajInstance.dismiss()
            }
        }

        dodajView.setOnClickListener(){
            dodajInstance.dismiss()
        }
    }

    //Proslijeđivanje liste u adapter fragmenta
    private fun setRecyclerView() {
        val poduzecaAdapter = DogovoreniPosloviAdapter(poduzecaList, this@DogovoreniPosloviFragment)
        view?.findViewById<RecyclerView>(R.id.recycler_view)?.adapter = poduzecaAdapter
        view?.findViewById<RecyclerView>(R.id.recycler_view)?.setHasFixedSize(true)
    }

    //Pokretanje funkcije iz DataRepository-a
    private fun initData() {

        FirestoreDohvatiDogovoreniPosao(object : FirestoreCallBackPoduzece {
            override fun onCallback(lista: ArrayList<Poduzeca>) {
                poduzecaList=lista
                setRecyclerView()
            }
        })
    }
    //Funkcija koja se poziva iz adaptera pri svakim klikom na red u adapteru
    override fun onItemClick(position: Int, gumb: Int) {
        //Toast.makeText(getActivity(), "Kliknuo sam " + position + "id: "+ gumb, Toast.LENGTH_SHORT).show()
        when(gumb){
            /*
            -1 ->
                Toast.makeText(getActivity(), "Kliknuo Si na Redak", Toast.LENGTH_SHORT).show()
            */
                //Ovara DodjelaVozacaFragment onog dogovorenog posla kojeg smo odabrali
            2131230840 ->{
                komunikator = activity as Komunikator
                komunikator.proslijediOdabranPosao(poduzecaList[position].id, poduzecaList[position].AdrsUtovar, poduzecaList[position].AdrsIstovar, poduzecaList[position].BrojTura)
            }
            //Otvara popup za uređivanje odabranog dogovorenog posla
            2131230887 -> {
                val urediView = LayoutInflater.from(activity).inflate(R.layout.unos_uredivanje_posla, null)
                val urediBuilder = AlertDialog.Builder(activity).setView(urediView)
                val urediInstance = urediBuilder.show()
                var txtPoduzeceUnos = urediView.findViewById(R.id.txtPoduzeceUnos) as EditText
                val txtAdrsUtovarUnos = urediView.findViewById(R.id.txtAdrsUtovarUnos) as EditText
                val txtAdrsIstovarUnos = urediView.findViewById(R.id.txtAdrsIstovarUnos) as EditText
                val txtDatumUnos = urediView.findViewById(R.id.txtDatumUnos) as EditText
                val txtRobaUnos = urediView.findViewById(R.id.txtRobaUnos) as EditText
                val txtBrojTuraUnos = urediView.findViewById(R.id.txtBrojTuraUnos) as EditText
                val txtKontaktUnos = urediView.findViewById(R.id.txtKontaktUnos) as EditText
                val txtEmailUnos = urediView.findViewById(R.id.txtEmailUnos) as EditText

                val btnDodajNovi = urediView.findViewById(R.id.btnPotvrdi) as ImageButton

                txtPoduzeceUnos.setText(activity?.txtPoduzeceE?.text.toString())
                txtAdrsUtovarUnos.setText(activity?.txtAdrsUtovarE?.text.toString())
                txtAdrsIstovarUnos.setText(activity?.txtAdrsIstovarE?.text.toString())
                txtDatumUnos.setText(activity?.txtDatumE?.text.toString())
                txtRobaUnos.setText(activity?.txtRobaE?.text.toString())
                txtBrojTuraUnos.setText(activity?.txtBrojTuraE?.text.toString())
                txtKontaktUnos.setText(activity?.txtKontaktE?.text.toString())
                txtEmailUnos.setText(activity?.txtEmailE?.text.toString())

                var status: String = poduzecaList[position].Status
                var id: String = poduzecaList[position].id

                btnDodajNovi.setOnClickListener {

                    var ispravanUnos : Boolean = false
                    var ispravanBroj : Boolean = false

                    if (txtPoduzeceUnos.text.toString() == "" || txtAdrsUtovarUnos.text.toString()  == "" || txtAdrsIstovarUnos.text.toString()  == "" || txtDatumUnos.text.toString()  == "" ||
                        txtRobaUnos.text.toString()  == "" || txtBrojTuraUnos.text.toString()  == "" || txtKontaktUnos.text.toString()  == "" || txtEmailUnos.text.toString()  == ""){
                        Toast.makeText(activity, "Nisu svi podaci uneseni!", Toast.LENGTH_SHORT).show()
                    }else{
                        ispravanUnos = true
                    }

                    if(isInteger(txtBrojTuraUnos.text.toString()))
                        ispravanBroj = true
                    else
                        Toast.makeText(activity, "Broj potrebnih tura mora biti broj!", Toast.LENGTH_SHORT).show()

                    //Ako su ispravno uneseni podatci u FireStore'u se ažuriraju
                    if (ispravanUnos == true && ispravanBroj == true){
                        val updatePoduzece: Poduzeca = Poduzeca(txtPoduzeceUnos.text.toString(),txtDatumUnos.text.toString(),status,txtAdrsUtovarUnos.text.toString(),txtAdrsIstovarUnos.text.toString(),txtRobaUnos.text.toString(),txtBrojTuraUnos.text.toString(),txtKontaktUnos.text.toString(),txtEmailUnos.text.toString(),id)
                        FirestoreUpdateDogovoreniPosao(updatePoduzece)
                        urediInstance.dismiss()
                        initData()
                    }
                }

                urediView.setOnClickListener(){
                    urediInstance.dismiss()
                }
            }
            //Briše se odabrani dogovoren posao iz FireStore'a
            2131230859 ->{
                FirestoreDeleteDogovoreniPosao(poduzecaList[position])
                initData()
            }
        }
    }
}
//DogovoreniPosloviFragment kreirao Borna Romić