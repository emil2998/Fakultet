package hr.foi.air.air2116.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.foi.air.air2116.R
import hr.foi.air.core.Prijevozi

class TrenutniPrijevozAdapter(val listaPrijevoza: List<Prijevozi>, val listener: OnItemClickListener): RecyclerView.Adapter<TrenutniPrijevozAdapter.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val utovar: TextView = itemView.findViewById<TextView>(R.id.textUtovar)
        val istovar: TextView = itemView.findViewById<TextView>(R.id.textIstovar)
        val kamionU: TextView = itemView.findViewById<TextView>(R.id.textKamionUtovar)
        val kamionI: TextView = itemView.findViewById<TextView>(R.id.textKamionIstovar)
        val prikolicaU: TextView = itemView.findViewById<TextView>(R.id.textPrikolicaUtovar)
        val prikolicaI: TextView = itemView.findViewById<TextView>(R.id.textPrikolicaIstovar)
        val rednibroj: TextView = itemView.findViewById<TextView>(R.id.redniBroj)
        val btnU: Button = itemView.findViewById<Button>(R.id.btnUtovar)
        val btnI: Button = itemView.findViewById<Button>(R.id.btnIstovar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.red_trenutni_prijevoz,parent,false)
        return ViewHolder((itemView))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var current = listaPrijevoza[position]
        holder.utovar.text = current.adresaUtovar + "-Ut."
        holder.istovar.text = current.adresaIstovar + "-Ist."
        holder.kamionU.text = current.regKamion
        holder.kamionI.text = current.regKamion
        holder.prikolicaU.text = current.regPrikolica
        holder.prikolicaI.text = current.regPrikolica
        holder.rednibroj.text = current.redniBroj

        holder.btnI.setVisibility(View.GONE);
        holder.btnU.setVisibility(View.GONE);

        if(current.utovaren.equals("Ne") && current.redniBroj.equals("1")) {
            holder.btnU.setVisibility(View.VISIBLE);
            holder.btnI.setVisibility(View.GONE);
        }
        if(current.utovaren.equals("Da")  && current.redniBroj.equals("1")){
            holder.btnU.setVisibility(View.GONE);
            holder.btnI.setVisibility(View.VISIBLE);
        }

        holder.btnU.setOnClickListener{
            listener.utovar(current)
        }
        holder.btnI.setOnClickListener{
            listener.istovar(current)
        }




    }

    override fun getItemCount(): Int {
        return listaPrijevoza.size
    }

    interface OnItemClickListener{
        fun istovar(prijevoz: Prijevozi)
        fun utovar(prijevoz: Prijevozi)
    }

}