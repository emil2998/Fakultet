package hr.foi.air.air2116.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.foi.air.air2116.R
import hr.foi.air.air2116.activity.MainActivity
import hr.foi.air.air2116.dataClass.DogovoreniPosao
import hr.foi.air.air2116.dataClass.Kvar
import hr.foi.air.air2116.dataClass.KvarPovijest
import org.w3c.dom.Text

class PovijestKvarovaAdapter
    (
    private val listaKvarPovijest: List<KvarPovijest>,

    ) :
    RecyclerView.Adapter<PovijestKvarovaAdapter.ExampleViewHolder>() {

    //cache klasa za jedan redak koji će se prikazat u listi
    //ova klasa prima cijeli cardview i sve njegove specifične view-ove
    inner class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        // val profilna: ImageView = itemView.findViewById<ImageView>(R.id.image_view)
        val datumKvara: TextView = itemView.findViewById<TextView>(R.id.datumKvara)
        val datumPopravka: TextView = itemView.findViewById<TextView>(R.id.datumPopravka)
        val hitnost: TextView = itemView.findViewById<TextView>(R.id.hitnostKvara)
        val opis: TextView = itemView.findViewById<TextView>(R.id.opisKvara)

    }


    //ovu metodu poziva recycler view kada dođe vrijeme za kreiranje novog viewHoldera
    //metoda LayoutInflater je iz Android frameworka a služi za pretvaranje .xml layout-a u view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.povijest_kvarova_item, parent, false)
        return ExampleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = listaKvarPovijest[position]
        // holder.profilna.setImageBitmap(MainActivity.ulogiraniKorisnik.slika)
        holder.datumKvara.text = "Datum kvara:" + currentItem.datumKvara
        holder.datumPopravka.text = "Datum Popravka:" + currentItem.datumPopravka
        holder.hitnost.text = "Hitnost:" + currentItem.hitnost
        holder.opis.text = "Opis kvara:" + currentItem.opisKvara


    }

    override fun getItemCount(): Int {
        return listaKvarPovijest.size
    }


}
