package com.tif22.orderingfood.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tif22.orderingfood.R
import com.tif22.orderingfood.data.model.ModelMenuHome

class AdapterCardHome(private val data: List<ModelMenuHome>?) : RecyclerView.Adapter<AdapterCardHome.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card_dashboard, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: ModelMenuHome = data!![position]
        holder.nama_card_home.text = item.nama_menu
        holder.restoran_card_home.text = item.nama_restoran
        holder.harga_card_home.text = item.harga.toString()
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nama_card_home: TextView = itemView.findViewById(R.id.nama_card_home)
        val restoran_card_home: TextView = itemView.findViewById(R.id.restoran_card_home)
        val harga_card_home: TextView = itemView.findViewById(R.id.harga_item_card_dashboard)
    }
}
