package com.tif22.orderingfood.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.tif22.orderingfood.R
import com.tif22.orderingfood.api.retrofit.RetrofitClient
import com.tif22.orderingfood.api.retrofit.RetrofitEndPoint
import com.tif22.orderingfood.data.model.ModelMenuHome
import com.tif22.orderingfood.data.response.ResponseWithoutData
import com.tif22.orderingfood.ui.activity.DetailMenu
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

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

        val sharedPreferences: SharedPreferences = context?.getSharedPreferences(
            "prefLogin",
            AppCompatActivity.MODE_PRIVATE
        )!!

        val id_user = sharedPreferences.getString("id_user", "")
        val retrofitEndPoint: RetrofitEndPoint = RetrofitClient.connection.create()
        var statusLike = false

        val call: Call<ResponseWithoutData> =
            retrofitEndPoint.CekDisukai(id_user.toString(), item.id_menu)
        call.enqueue(object : Callback<ResponseWithoutData> {
            override fun onResponse(
                call: Call<ResponseWithoutData>,
                response: Response<ResponseWithoutData>
            ) {
                if (response.isSuccessful && response.body()?.status.equals("tersedia")) {
                    holder.like_item_card_home.setImageResource(R.drawable.like_on)
                    statusLike = true
                } else {
                    holder.like_item_card_home.setImageResource(R.drawable.like_off)
                    statusLike = false
                }
            }

            override fun onFailure(call: Call<ResponseWithoutData>, t: Throwable) {
                t.printStackTrace()
            }
        })

        holder.like_item_card_home.setOnClickListener {
            if (statusLike) {
                statusLike = false
                holder.like_item_card_home.startAnimation(AnimationUtils.loadAnimation(context,R.anim.scale_down))
                holder.like_item_card_home.setImageResource(R.drawable.like_off)
                val endPoint: RetrofitEndPoint = RetrofitClient.connection.create()
                endPoint.HapusDisukai(id_user.toString(), item.id_menu)
                    .enqueue(object :
                        Callback<ResponseWithoutData> {
                        override fun onResponse(
                            call: Call<ResponseWithoutData>,
                            response: Response<ResponseWithoutData>
                        ) {
                            if (response.isSuccessful && response.body()?.status.equals("success")) {

                            }
                        }

                        override fun onFailure(call: Call<ResponseWithoutData>, t: Throwable) {
                            t.printStackTrace()
                        }
                    })
            } else {
                statusLike = true
                holder.like_item_card_home.startAnimation(AnimationUtils.loadAnimation(context,R.anim.scale_up))
                holder.like_item_card_home.setImageResource(R.drawable.like_on)
                val endPoint: RetrofitEndPoint = RetrofitClient.connection.create()
                endPoint.TambahDisukai(id_user.toString(), item.id_menu)
                    .enqueue(object :
                        Callback<ResponseWithoutData> {
                        override fun onResponse(
                            call: Call<ResponseWithoutData>,
                            response: Response<ResponseWithoutData>
                        ) {
                            if (response.isSuccessful && response.body()?.message.equals("success")) {

                            }
                        }

                        override fun onFailure(call: Call<ResponseWithoutData>, t: Throwable) {
                            t.printStackTrace()
                        }
                    })
            }

        }

        holder.itemView.setOnClickListener {
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
        val like_item_card_home: ImageView = itemView.findViewById(R.id.like_item_card_home)
    }
}
