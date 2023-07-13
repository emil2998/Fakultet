package hr.foi.air.air2116.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.foi.air.air2116.R
import hr.foi.air.air2116.dataClass.Vozilo

class ListaVozilaAdapter (
    private val listaVozila: List<Vozilo>,
    private val listener: ListaVozilaAdapter.OnItemClickListener
) :
    RecyclerView.Adapter<ListaVozilaAdapter.ExampleViewHolder>()
{

    inner class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val slikaVozila: ImageView = itemView.findViewById<ImageView>(R.id.imgSlikaVozila)
        val nazivModela: TextView = itemView.findViewById<TextView>(R.id.txtModel)
        val registracija: TextView = itemView.findViewById<TextView>(R.id.txtRegistracija)
        val vrstaVozila:TextView=itemView.findViewById<TextView>(R.id.txtVrsta)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
                //notifyDataSetChanged()
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.lista_vozila_item, parent, false)
        return ExampleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = listaVozila[position]
        //holder.profilna.setImageResource(currentItem.imageResource)
        holder.slikaVozila.setImageBitmap(currentItem.slika)
        holder.nazivModela.text = currentItem.nazivModela
        holder.registracija.text=currentItem.registracija
        holder.vrstaVozila.text = currentItem.vrstaVozila
    }

    override fun getItemCount(): Int {
        return listaVozila.size
    }
}