package hr.foi.air.air2116.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import hr.foi.air.air2116.R
import hr.foi.air.air2116.dataClass.DodijeljeniVozaci

class DodjelaVozacaAdapter (
        val dodijeljeniVozaciList: List<DodijeljeniVozaci>,
        private val listener: OnItemClickListener
        ) : RecyclerView.Adapter<DodjelaVozacaAdapter.ListaVozaca>(){

    inner class ListaVozaca(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{

        var txtRedniBroj : TextView = itemView.findViewById(R.id.txtRedniBroj)
        var txtVozac : TextView = itemView.findViewById(R.id.txtVozacDodaj)
        var txtKamion : TextView = itemView.findViewById(R.id.txtKamionDodaj)
        var txtPrikolica : TextView = itemView.findViewById(R.id.txtPrikolicaDodaj)

        var linLayoutDodajVozace : LinearLayout = itemView.findViewById(R.id.linLayoutDodjelaVozaca)

        var btnInfo : ImageButton = itemView.findViewById(R.id.btnInfo)
        var btnUkloni : ImageButton = itemView.findViewById(R.id.btnUkloniVozac)

        init {
            itemView.setOnClickListener(this)
            btnInfo.setOnClickListener(this)
            btnUkloni.setOnClickListener(this)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaVozaca {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.lista_dodijeljenih_vozaca, parent, false)
        return ListaVozaca(view)
    }

    override fun getItemCount(): Int {
        return dodijeljeniVozaciList.size
    }

    //Dodajemo vrijednosti iz dodijeljeniVozaci na layout
    override fun onBindViewHolder(holder: ListaVozaca, position: Int) {
        val dodijeljeniVozaci : DodijeljeniVozaci = dodijeljeniVozaciList[position]

        holder.txtRedniBroj.text = (position+1).toString()
        holder.txtVozac.text = dodijeljeniVozaci.vozac
        holder.txtKamion.text = dodijeljeniVozaci.regKamion
        holder.txtPrikolica.text = dodijeljeniVozaci.regPrikolica
        if(dodijeljeniVozaci.status == "Dovršeno"){
            holder.btnUkloni.visibility = View.INVISIBLE
            holder.linLayoutDodajVozace.setBackgroundColor(Color.GREEN)
        }
        if(dodijeljeniVozaci.status == "Kvar"){
            holder.btnUkloni.visibility = View.INVISIBLE
            holder.linLayoutDodajVozace.setBackgroundColor(Color.RED)
        }
    }
}
//Adapter za DodjelaVozacaFragment kreirao Borna Romić