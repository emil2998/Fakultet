package hr.foi.air.air2116.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import hr.foi.air.air2116.R
import hr.foi.air.air2116.dataClass.Poduzeca


class DogovoreniPosloviAdapter(
    val poduzecaList: List<Poduzeca>,
    private val listener: OnItemClickListener
    ) : RecyclerView.Adapter<DogovoreniPosloviAdapter.ListaPoslova>(){

    inner class ListaPoslova(itemView: View) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener{
        var txtPoduzece : TextView = itemView.findViewById(R.id.txtPoduzece)
        var txtDatum : TextView = itemView.findViewById(R.id.txtDatum)
        var txtStatus : TextView = itemView.findViewById(R.id.txtStatus)

        var txtPoduzeceE : TextView = itemView.findViewById(R.id.txtPoduzeceE)
        var txtAdrsUtovarE : TextView = itemView.findViewById(R.id.txtAdrsUtovarE)
        var txtAdrsIstovarE : TextView = itemView.findViewById(R.id.txtAdrsIstovarE)
        var txtDatumE : TextView = itemView.findViewById(R.id.txtDatumE)
        var txtRobaE : TextView = itemView.findViewById(R.id.txtRobaE)
        var txtBrojTuraE : TextView = itemView.findViewById(R.id.txtBrojTuraE)
        var txtStatusE : TextView = itemView.findViewById(R.id.txtStatusE)
        var txtKontaktE : TextView = itemView.findViewById(R.id.txtKontaktE)
        var txtEmailE : TextView = itemView.findViewById(R.id.txtEmailE)

        var linLayoutPoduzece : LinearLayout = itemView.findViewById(R.id.linLayoutPoduzece)
        var expLayoutPoduzece : LinearLayout = itemView.findViewById(R.id.expLayoutPoduzece)

        var btnDodijeliVozace : Button = itemView.findViewById(R.id.btnDodajVozace)
        var btnUredi : Button = itemView.findViewById(R.id.btnUredi)
        var btnObrisi : Button = itemView.findViewById(R.id.btnObrisi)

        init {
            itemView.setOnClickListener(this)
            btnDodijeliVozace.setOnClickListener(this)
            btnUredi.setOnClickListener(this)
            btnObrisi.setOnClickListener(this)
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

    interface OnItemClickListener {
        fun onItemClick(position: Int, gumb: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaPoslova {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.lista_poduzeca, parent, false)
        return ListaPoslova(itemView)
    }

    override fun getItemCount(): Int {
        return poduzecaList.size
    }


    //Dodajemo vrijednosti iz poduzecaList na layout
    override fun onBindViewHolder(holder: ListaPoslova, position: Int) {
        var dogovoreniPoslovi : Poduzeca = poduzecaList[position]

        var checkExpanded = arrayListOf<String>()
        var zadnji = poduzecaList.lastIndex
        for (i in 0..zadnji.toInt()){
            checkExpanded.add(i.toString())
        }

        holder.txtPoduzece.text = dogovoreniPoslovi.Poduzece
        holder.txtDatum.text = dogovoreniPoslovi.Datum
        holder.txtStatus.text = dogovoreniPoslovi.Status

        holder.txtPoduzeceE.text = dogovoreniPoslovi.Poduzece
        holder.txtAdrsUtovarE.text = dogovoreniPoslovi.AdrsUtovar
        holder.txtAdrsIstovarE.text = dogovoreniPoslovi.AdrsIstovar
        holder.txtDatumE.text = dogovoreniPoslovi.Datum
        holder.txtRobaE.text = dogovoreniPoslovi.Roba
        holder.txtBrojTuraE.text = dogovoreniPoslovi.BrojTura
        holder.txtStatusE.text = dogovoreniPoslovi.Status
        holder.txtKontaktE.text = dogovoreniPoslovi.Kontakt
        holder.txtEmailE.text = dogovoreniPoslovi.Email

        if (holder.txtStatus.text == "Nedovršeno"){
            holder.txtStatus.setTextColor(Color.parseColor("#E31010"))
            holder.txtStatusE.setTextColor(Color.parseColor("#E31010"))
        }
        if (holder.txtStatus.text == "Dovršeno"){
            holder.txtStatus.setTextColor(Color.parseColor("#6DAC61"))
            holder.txtStatusE.setTextColor(Color.parseColor("#6DAC61"))
        }
        if (holder.txtStatus.text == "U tijeku"){
            holder.txtStatus.setTextColor(Color.parseColor("#A1A657"))
            holder.txtStatusE.setTextColor(Color.parseColor("#A1A657"))
        }

        val isExpandable : Boolean = poduzecaList[position].expandable
        holder.expLayoutPoduzece.visibility = if (isExpandable) View.VISIBLE else View.GONE

        holder.linLayoutPoduzece.setOnClickListener {
            var dogovoreniPosloviPosition = poduzecaList[position]
            val pozicija : Int = position
            dogovoreniPosloviPosition.expandable = !dogovoreniPosloviPosition.expandable
            notifyItemChanged(position)
            checkExpanded.set(pozicija, "true")
            for (i in 0..zadnji.toInt()){
                Log.e(checkExpanded[i].toString(), toString())
            }
        }
    }
}
//Adapter za DogovoreniPosloviFragment kreirao Borna Romić