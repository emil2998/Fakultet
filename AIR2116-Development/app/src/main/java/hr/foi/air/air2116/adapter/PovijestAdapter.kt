package hr.foi.air.air2116.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.foi.air.air2116.R
import hr.foi.air.air2116.dataClass.Poduzeca
import org.w3c.dom.Text

class PovijestAdapter
    (
    private val listaPovijestiPrijevoza: List<Poduzeca>,

) :
    RecyclerView.Adapter<PovijestAdapter.ExampleViewHolder>() {

    //cache klasa za jedan redak koji će se prikazat u listi
    //ova klasa prima cijeli cardview i sve njegove specifične view-ove
    inner class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
         {
        // val profilna: ImageView = itemView.findViewById<ImageView>(R.id.image_view)
        val utovar: TextView = itemView.findViewById<TextView>(R.id.txtAdrsUtovarPovijest)
        val istovar: TextView = itemView.findViewById<TextView>(R.id.txtAdrsIstovarPovijest)
        val datum: TextView = itemView.findViewById<TextView>(R.id.datumPovijest)
        val roba: TextView = itemView.findViewById<TextView>(R.id.robaPovijest)
        val poduzece: TextView = itemView.findViewById<TextView>(R.id.poduzecePovijest)

    }


    //ovu metodu poziva recycler view kada dođe vrijeme za kreiranje novog viewHoldera
    //metoda LayoutInflater je iz Android frameworka a služi za pretvaranje .xml layout-a u view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.povijest_item, parent, false)
        return ExampleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = listaPovijestiPrijevoza[position]
        // holder.profilna.setImageBitmap(MainActivity.ulogiraniKorisnik.slika)
        holder.utovar.text = currentItem.AdrsUtovar
        holder.istovar.text = currentItem.AdrsIstovar
        holder.datum.text = currentItem.Datum
        holder.roba.text = currentItem.Roba
        holder.poduzece.text = currentItem.Poduzece


    }

    override fun getItemCount(): Int {
        return listaPovijestiPrijevoza.size
    }


    }
