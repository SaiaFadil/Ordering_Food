package com.tif22.orderingfood.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.tif22.orderingfood.R
import com.tif22.orderingfood.api.retrofit.RetrofitClient
import com.tif22.orderingfood.data.model.ModelMenuCari
import com.tif22.orderingfood.data.model.ModelMenuHome
import com.tif22.orderingfood.ui.activity.DetailMenu

class AdapterCardCari(
    private val context: Context?,
    private val data: List<ModelMenuCari>?
) : RecyclerView.Adapter<AdapterCardCari.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_item_menu_cari, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: ModelMenuCari = data!![position]
        holder.nama_card_home.text = item.nama_menu
        holder.restoran_card_home.text = item.nama_restoran
        holder.harga_card_home.text = item.harga



    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nama_card_home: TextView = itemView.findViewById(R.id.nama_menu_cari)
        val restoran_card_home: TextView = itemView.findViewById(R.id.nama_restoran_cari)
        val harga_card_home: TextView = itemView.findViewById(R.id.harga_cari)
    }
}
