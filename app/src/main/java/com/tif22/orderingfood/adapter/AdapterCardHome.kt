package com.tif22.orderingfood.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.utils.widget.ImageFilterButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.tif22.orderingfood.R
import com.tif22.orderingfood.api.retrofit.RetrofitClient
import com.tif22.orderingfood.data.model.ModelMenuHome
import com.tif22.orderingfood.ui.activity.DetailMenu

class AdapterCardHome(
    private val context: Context?,
    private val data: List<ModelMenuHome>?
) : RecyclerView.Adapter<AdapterCardHome.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card_dashboard, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: ModelMenuHome = data!![position]
        val serverUrl: String = RetrofitClient.BASE_URL
        val gambarUrl = serverUrl + item.gambar_menu
        holder.nama_card_home.text = item.nama_menu
        holder.restoran_card_home.text = item.nama_restoran
        holder.harga_card_home.text = item.harga

        Glide.with(holder.itemView.context)
            .load(gambarUrl)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(holder.image_card_home)

        holder.itemView.setOnClickListener{
                val intent = Intent(context, DetailMenu::class.java)
                intent.putExtra("id_menu", item.id_menu)
                context?.startActivity(intent)
                (context as Activity).overridePendingTransition(R.anim.layout_in, R.anim.layout_out)
        }





    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nama_card_home: TextView = itemView.findViewById(R.id.nama_card_home)
        val restoran_card_home: TextView = itemView.findViewById(R.id.restoran_card_home)
        val harga_card_home: TextView = itemView.findViewById(R.id.harga_item_card_dashboard)
        val image_card_home: ImageView = itemView.findViewById(R.id.image_card_home)
        val like_item_card_home: ImageFilterButton = itemView.findViewById(R.id.like_item_card_home)
    }
}
