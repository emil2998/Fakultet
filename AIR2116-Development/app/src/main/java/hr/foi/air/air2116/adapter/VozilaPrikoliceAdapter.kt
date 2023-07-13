package hr.foi.air.air2116.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.foi.air.air2116.R
import hr.foi.air.air2116.dataClass.VoziloPrikolica
import hr.foi.air.air2116.fragment.OdabirVozilaPrikolica
import hr.foi.air.core.Komunikator

class VozilaPrikoliceAdapter(
    private val listaVozilaPrikolica: List<VoziloPrikolica>,
    private val listener: OdabirVozilaPrikolica

    ) :
    RecyclerView.Adapter<VozilaPrikoliceAdapter.ExampleViewHolder>() {
    private lateinit var komunikator: Komunikator
    //cache klasa za jedan redak koji će se prikazat u listi
    //ova klasa prima cijeli cardview i sve njegove specifične view-ove
    inner class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener
    {
        // val profilna: ImageView = itemView.findViewById<ImageView>(R.id.image_view)
        val nazivModela: TextView = itemView.findViewById<TextView>(R.id.nazivModela)
        val vrstaVozila: TextView = itemView.findViewById<TextView>(R.id.vrstaVozila)
        val godinaProizvodnje: TextView = itemView.findViewById<TextView>(R.id.godinaProizvodnje)



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
            .inflate(R.layout.vozila_prikolice_item, parent, false)
        return ExampleViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = listaVozilaPrikolica[position]
        // holder.profilna.setImageBitmap(currentItem.slika)
        holder.nazivModela.text = currentItem.nazivModela
        holder.vrstaVozila.text = currentItem.vrstaVozila
        holder.godinaProizvodnje.text = currentItem.godinaProizvodnje


    }

    override fun getItemCount(): Int {
        return listaVozilaPrikolica.size
    }


}
