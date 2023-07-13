package hr.foi.air.air2116

import android.R.attr
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import Data.Poduzeca
import android.R.attr.data




class DogovoreniPosloviAdapter(
    val poduzecaList: List<Poduzeca>
    ) : RecyclerView.Adapter<DogovoreniPosloviAdapter.ListaPoslova>(){

    class ListaPoslova(itemView: View) : RecyclerView.ViewHolder(itemView) {
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

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaPoslova {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.lista_poduzeca, parent, false)
        var checkExpanded = arrayListOf(String())
        var zadnji = poduzecaList.lastIndex
        for (i in 0..zadnji.toInt()){
            checkExpanded.add(i.toString())
        }
        Log.e("bok", toString())
        return ListaPoslova(view)
    }

    override fun getItemCount(): Int {
        return poduzecaList.size
    }

    override fun onBindViewHolder(holder: ListaPoslova, position: Int) {
        var dogovoreni_poslovi : Poduzeca = poduzecaList[position]



        holder.txtPoduzece.text = dogovoreni_poslovi.Poduzece
        holder.txtDatum.text = dogovoreni_poslovi.Datum
        holder.txtStatus.text = dogovoreni_poslovi.Status

        holder.txtPoduzeceE.text = dogovoreni_poslovi.Poduzece
        holder.txtAdrsUtovarE.text = dogovoreni