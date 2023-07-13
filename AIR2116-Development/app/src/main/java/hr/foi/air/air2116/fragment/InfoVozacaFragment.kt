
package hr.foi.air.air2116.fragment
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import hr.foi.air.air2116.R
import hr.foi.air.air2116.adapter.InfoVozacaAdapter
import hr.foi.air.air2116.dataClass.InfoVozaca
import hr.foi.air.air2116.dataClass.Poduzeca
import hr.foi.air.core.Komunikator
import hr.foi.air.air2116.repository.*
import kotlinx.android.synthetic.main.dodjela_novog_vozaca.*
import kotlinx.android.synthetic.main.dodjela_vozaca.*
import kotlinx.android.synthetic.main.lista_poduzeca.*

class InfoVozacaFragment  : Fragment(R.layout.info_vozaca), InfoVozacaAdapter.OnItemClickListener{
    private lateinit var komunikator: Komunikator
    var infoVozacaList = mutableListOf<InfoVozaca>()
    var poduzecaList = ArrayList<Poduzeca>()
    var brojList : Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        setRecyclerView()

        val argumenti = arguments
        var idVozaca = argumenti!!.get("idVozaca").toString()
    }

    //Proslijeđivanje liste u adapter fragmenta
    private fun setRecyclerView() {
        val poduzecaAdapter = InfoVozacaAdapter(infoVozacaList, this@InfoVozacaFragment)
        view?.findViewById<RecyclerView>(R.id.recycler_view)?.adapter = poduzecaAdapter
        view?.findViewById<RecyclerView>(R.id.recycler_view)?.setHasFixedSize(true)
    }

    //Funkcija koja dohvaća proslijeđene argumente i pokreće funkcije iz DataRepository-a
    private fun initData(){
        val argumenti = arguments
        var idVozaca = argumenti!!.get("idVozaca").toString()

        //Funkcija koja dohvaća sve InfoVozača za odabranog vozača
        FirestoreDohvatiInfoVozaca(idVozaca, object : FirestoreCallBackInfoVozaca {
            override fun onCallback(lista: MutableList<InfoVozaca>) {
                infoVozacaList = lista

                infoVozacaList.sortBy { it.redoslijed }

                for (i in 0 until infoVozacaList.size){
                    FirestoreDohvatiDogovoreniPosao(object : FirestoreCallBackPoduzece {
                        override fun onCallback(lista: ArrayList<Poduzeca>) {
                            poduzecaList=lista

                            for (j in 0 until poduzecaList.size){
                                if (poduzecaList[j].id == infoVozacaList[i].idPoduzeca){
                                    infoVozacaList[i].poduzece = poduzecaList[j].Poduzece
                                    infoVozacaList[i].adresaUtovara = poduzecaList[j].AdrsUtovar
                                    infoVozacaList[i].adresaIstovara = poduzecaList[j].AdrsIstovar
                                }
                            }
                            setRecyclerView()
                        }
                    })
                }
            }
        })
    }

    //Funkcija koja se poziva iz adaptera pri svakim klikom na red u adapteru
    override fun onItemClick(position: Int, gumb: Int) {
        //Toast.makeText(getActivity(), "Kliknuo sam " + position + "id: "+ gumb, Toast.LENGTH_SHORT).show()
        when(gumb){
            -1 ->{
                //Toast.makeText(getActivity(), "Kliknuo Si na Redak", Toast.LENGTH_SHORT).show()
            }
            //Pomicanje rednog broja gore
            2131230882 ->{
                //Toast.makeText(getActivity(), "Kliknuo Si na Strijelicu gore", Toast.LENGTH_SHORT).show()
                FirestoreUpdateInfoGore(infoVozacaList[position].idVozac, infoVozacaList[position].redoslijed)
                Thread.sleep(200)
                initData()
            }
            //Pomicanje rednog broja dolje
            2131230881 -> {
                //Toast.makeText(getActivity(), "Kliknuo Si na Strijelicu dolje", Toast.LENGTH_SHORT).show()
                FirestoreUpdateInfoDolje(infoVozacaList[position].idVozac, infoVozacaList[position].redoslijed, infoVozacaList.size)
                Thread.sleep(200)
                initData()
            }
            //Brisanje ture odabranog vozača
            2131230886 -> {
                //Toast.makeText(getActivity(), "Kliknuo Si na Ukloni", Toast.LENGTH_SHORT).show()
                FirestoreDeleteDodijeljenogVozaca(infoVozacaList[position].idPoduzeca, infoVozacaList[position].idDodijeljenVozac)
                Thread.sleep(200)
                initData()
            }
        }
    }
}
//InfoVozacaFragment kreirao Borna Romić