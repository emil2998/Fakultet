package hr.foi.air.air2116.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.foi.air.core.Korisnik
import hr.foi.air.air2116.R

class ListaKorisnikaAdapter
    (
    private val listaKorisnika: List<Korisnik>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<ListaKorisnikaAdapter.ExampleViewHolder>() {

    //cache klasa za jedan redak koji će se prikazat u listi
    //ova klasa prima cijeli cardview i sve njegove specifične view-ove
    inner class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val profilna: ImageView = itemView.findViewById<ImageView>(R.id.imgProfilna)
        val imeIPrezime: TextView = itemView.findViewById<TextView>(R.id.txtImeIPrezime)
        val uloga: TextView = itemView.findViewById<TextView>(R.id.txtUloga)

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

    //ovu metodu poziva recycler view kada dođe vrijeme za kreiranje novog viewHoldera
    //metoda LayoutInflater je iz Android frameworka a služi za pretvaranje .xml layout-a u view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.lista_korisnika_item, parent, false)
        return ExampleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = listaKorisnika[position]
        //holder.profilna.setImageResource(currentItem.imageResource)
        holder.profilna.setImageBitmap(currentItem.slika)
        holder.imeIPrezime.text = currentItem.ime + " " + currentItem.prezime
        holder.uloga.text = currentItem.uloga
    }

    override fun getItemCount(): Int {
        return listaKorisnika.size
    }
}