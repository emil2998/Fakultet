package hr.foi.air.air2116.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.foi.air.air2116.adapter.TrenutniPrijevozAdapter
import hr.foi.air.core.Komunikator
import hr.foi.air.air2116.R
import hr.foi.air.air2116.activity.MainActivity
import hr.foi.air.air2116.dataClass.*
import hr.foi.air.air2116.repository.*
import hr.foi.air.core.Prijevozi
import kotlinx.android.synthetic.main.dodjela_vozaca.*


class TrenutniPrijevoz : Fragment(), TrenutniPrijevozAdapter.OnItemClickListener {


    var listaAdresa = mutableListOf<AdreseIstovarUtovar>()
    var listaDodVozaca = mutableListOf<DodVozaci>()
    var listaPrijevoza = mutableListOf<Prijevozi>()

    private lateinit var komunikator: Komunikator
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_trenutni_prijevoz, container, false)
        val txtNaslov = view.findViewById(R.id.txtNaslov) as TextView
        txtNaslov.text = "Trenutni prijevoz"

        view?.findViewById<ImageButton>(R.id.btnIzbornik)?.setOnClickListener{
            komunikator = activity as Komunikator
            komunikator.vratiGlavniMeni()
        }


        initData()



        /*
        val txtKamion = view.findViewById<TextView>(R.id.textVozilo)
        val txtPrikolica = view.findViewById<TextView>(R.id.textPrikolica)
        if(lista.isNotEmpty()){
            txtKamion.text = lista[0].kamion
            txtPrikolica.text = lista[0].prikolica
        }
        view.findViewById<Button>(R.id.btnPodnesiKvar).setOnClickListener{
            if(lista.isNotEmpty()){
                komunikator = activity as Komunikator
                komunikator.podnesiKvar(lista[0].id_kamiona,lista[0].id_prikolica)
            }
        }
        */

        return view
    }

    private fun initData() {
        listaPrijevoza.clear()
        listaDodVozaca.clear()
        listaAdresa.clear()
        FirestoreDohvatiAdreseIstovarUtovar(object: FirestoreCallBackAdreseIstovarUtovar{
            override fun onCallback(lista: MutableList<AdreseIstovarUtovar>) {
                listaAdresa = lista
                var brojac=0;
                for(item in listaAdresa){
                    FirestoreDohvatiDodVozace(item.idDogovoreniPosao,MainActivity.ulogiraniKorisnik.id.toString(), object: FirestoreCallBackDodVozace{
                        override fun onCallback(lista2: MutableList<DodVozaci>) {
                            listaDodVozaca = lista2
                            for(item2 in listaDodVozaca){

                                var idDogovoreniPosao = item.idDogovoreniPosao
                                var adresaUtovar = item.adresaUtovara
                                var adresaIstovara = item.adresaIstovara
                                var idDodijeljenVozac = item2.idDodijeljeniVozac
                                var idInfo = item2.idInfo
                                var idKamion = item2.idKamion
                                var idPoduzeca = item2.idPoduzeca
                                var idPrikolica = item2.idPrikolica
                                var idVozac = item2.idVozac
                                var regKamion = item2.regKamion
                                var regPrikolica = item2.regPrikolica
                                var status = item2.status
                                var utovaren = item2.utovaren
                                var vozac = item2.vozac
                                var redniBroj = "0"

                                if(status.equals("Nedovršeno")){
                                    listaPrijevoza.add(
                                        Prijevozi(
                                            idDogovoreniPosao,
                                            adresaUtovar,
                                            adresaIstovara,
                                            idDodijeljenVozac,
                                            idInfo,
                                            idKamion,
                                            idPoduzeca,
                                            idPrikolica,
                                            idVozac,
                                            regKamion,
                                            regPrikolica,
                                            status,
                                            utovaren,
                                            vozac,
                                            redniBroj
                                        )
                                    )
                                }

                            }
                            brojac++;
                            if(brojac.equals(listaAdresa.size)) {
                                var brojac2=0;
                                for(item in listaPrijevoza){
                                    FirestoreDohvatiRedniBroj(item.idVozac,item.idInfo,object: FirestoreCallBackRedniBroj {
                                        override fun onCallback(redniBroj: Number) {
                                            item.redniBroj = redniBroj.toString()
                                            brojac2++
                                            if(brojac2.equals(listaPrijevoza.size)){
                                                listaPrijevoza.sortBy { it.redniBroj }

                                                val txtKamion = view?.findViewById<TextView>(R.id.textVozilo)
                                                val txtPrikolica = view?.findViewById<TextView>(R.id.textPrikolica)
                                                if(lista.isNotEmpty()){
                                                    txtKamion?.text = listaPrijevoza[0].regKamion
                                                    txtPrikolica?.text = listaPrijevoza[0].regPrikolica
                                                    view?.findViewById<Button>(R.id.btnPodnesiKvar)?.setOnClickListener{
                                                        if(listaPrijevoza.isNotEmpty()){
                                                            komunikator = activity as Komunikator
                                                            komunikator.podnesiKvar(listaPrijevoza[0])
                                                        }
                                                    }
                                                }




                                                val adapter = TrenutniPrijevozAdapter(listaPrijevoza,this@TrenutniPrijevoz)
                                                val recyclerView = view?.findViewById<RecyclerView>(R.id.recViewTrenutniPrijevoz)
                                                recyclerView?.adapter = adapter
                                                recyclerView?.layoutManager = LinearLayoutManager(context)
                                            }
                                        }
                                    })


                                }

                            }
                }
            })
        }
    }
        } )
        if(listaPrijevoza.isEmpty()){
            val adapter = TrenutniPrijevozAdapter(listaPrijevoza,this@TrenutniPrijevoz)
            val recyclerView = view?.findViewById<RecyclerView>(R.id.recViewTrenutniPrijevoz)
            recyclerView?.adapter = adapter
            recyclerView?.layoutManager = LinearLayoutManager(context)
        }
    }

    override fun utovar(prijevoz: Prijevozi){
        FirestoreUtovariPrijevoz(prijevoz)
        Thread.sleep(500)
        initData()


    }
    override fun istovar(prijevoz: Prijevozi){
        FirestoreIstovariPrijevoz(prijevoz)
        FirestoreUrediRedneBrojeve(prijevoz)
        FirestorePogledajDogPoslove(prijevoz)
        Thread.sleep(500)
        initData()
    }
}