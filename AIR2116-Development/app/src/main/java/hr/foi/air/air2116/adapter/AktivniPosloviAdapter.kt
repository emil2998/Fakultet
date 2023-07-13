package hr.foi.air.air2116.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.foi.air.air2116.R
import hr.foi.air.core.Popravak
import hr.foi.air.air2116.repository.FirestoreNadiRegistraciju

class AktivniPosloviAdapter(val listaPoslova: List<Popravak>, val listener: OnItemClickListener): RecyclerView.Adapter<AktivniPosloviAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val naziv: TextView = itemView.findViewById<TextView>(R.id.textNaziv)
        val opis: TextView = itemView.findViewById<TextView>(R.id.textOpis)
        val hitnost: TextView = itemView.findViewById<TextView>(R.id.textHitnost)
        val zavrsi: Button = itemView.findViewById<Button>(R.id.zavrsi)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.red_aktivni_poslovi,parent,false)
        return ViewHolder((itemView))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = listaPoslova[position]
        var registracija:String

        FirestoreNadiRegistraciju(currentItem.idVozilo,object: FirestoreNadiRegistraciju {
            override fun onCallback(registracijaBaza: String) {
                registracija = registracijaBaza
                holder.naziv.text = registracija
            }
        })

        holder.opis.text = currentItem.opisKvara
        holder.hitnost.text = currentItem.hitnost
        holder.zavrsi.setOnClickListener{
            listener.onItemClick(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return listaPoslova.size
    }

    interface OnItemClickListener{
        fun onItemClick(popravak: Popravak)
    }


}