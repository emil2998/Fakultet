
package hr.foi.air.air2116.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.foi.air.air2116.R
import hr.foi.air.air2116.dataClass.InfoVozaca
class InfoVozacaAdapter (
    val infoVozacaList: List<InfoVozaca>,
    private val listener: OnItemClickListener
        ) : RecyclerView.Adapter<InfoVozacaAdapter.InfoListaVozaca>(){

    inner class InfoListaVozaca(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{

        var txtPoduzeceInfo : TextView = itemView.findViewById(R.id.PoduzeceInfo)
        var txtAdrsUtovarUnosDodjela : TextView = itemView.findViewById(R.id.txtAdrsUtovarUnosDodjela)
        var txtAdrsIstovarUnosDodjela : TextView = itemView.findViewById(R.id.txtAdrsIstovarUnosDodjela)
        var txtRedoslijedInfo : TextView = itemView.findViewById(R.id.txtRedoslijedInfo)

        var txtKamionInfo : TextView = itemView.findViewById(R.id.KamionInfo)
        var txtPrikolicaInfo : TextView = itemView.findViewById(R.id.PrikolicaInfo)


        var linLayoutInfoVozaca : LinearLayout = itemView.findViewById(R.id.linLayoutInfoVozaca)

        var btnStrijelicaGore : ImageButton = itemView.findViewById(R.id.btnStrijelicaGore)
        var btnStrijelicaDolje : ImageButton = itemView.findViewById(R.id.btnStrijelicaDolje)
        var btnUkloniVozacInfo : ImageButton = itemView.findViewById(R.id.btnUkloniVozacInfo)

        init {
            itemView.setOnClickListener(this)
            btnStrijelicaGore.setOnClickListener(this)
            btnStrijelicaDolje.setOnClickListener(this)
            btnUkloniVozacInfo.setOnClickListener(this)

        }

        //klikom miša dobivamo position i id gumba kliknutog
        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            val gumbId: Int = v!!.id
            if (position != RecyclerView.NO_POSITION){
                listener.onItemClick(position, gumbId)
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int, gumb: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoListaVozaca {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.lista_info_vozaca, parent, false)
        return InfoListaVozaca(view)
    }

    override fun getItemCount(): Int {
        return infoVozacaList.size
    }

    //Dodajemo vrijednosti iz infoVozacaList na layout
    override fun onBindViewHolder(holder: InfoListaVozaca, position: Int) {
        val infoVozaca : InfoVozaca = infoVozacaList[position]

        holder.txtPoduzeceInfo.text = infoVozaca.poduzece
        holder.txtAdrsUtovarUnosDodjela.text = infoVozaca.adresaUtovara
        holder.txtAdrsIstovarUnosDodjela.text = infoVozaca.adresaIstovara
        holder.txtRedoslijedInfo.text = infoVozaca.redoslijed.toString()
        holder.txtKamionInfo.text = infoVozaca.regKamion
        holder.txtPrikolicaInfo.text = infoVozaca.regPrikolica

        if(infoVozaca.redoslijed == 1){
            holder.btnStrijelicaGore.visibility = View.INVISIBLE
        }
        if(infoVozaca.redoslijed == infoVozacaList.size){
            holder.btnStrijelicaDolje.visibility = View.INVISIBLE
        }
    }
}

