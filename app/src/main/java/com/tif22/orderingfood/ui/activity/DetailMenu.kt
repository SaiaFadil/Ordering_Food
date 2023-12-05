package com.tif22.orderingfood.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.button.MaterialButton
import com.tif22.orderingfood.R
import com.tif22.orderingfood.api.retrofit.RetrofitClient
import com.tif22.orderingfood.api.retrofit.RetrofitEndPoint
import com.tif22.orderingfood.data.model.ModelDetailMenu
import com.tif22.orderingfood.data.model.ModelMenuHome
import com.tif22.orderingfood.data.response.ResponseDetailMenu
import com.tif22.orderingfood.ui.fragment.PromoFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class DetailMenu : AppCompatActivity() {

    private lateinit var nama_menu_detail: TextView
    private lateinit var nama_restoran_detail: TextView
    private lateinit var harga_menu_detail: TextView
    private lateinit var btn_pesan_detail: MaterialButton
    private lateinit var gambar_detail: ImageView
    private lateinit var alamat_menu_detail: TextView
    private lateinit var kembali_detail: ImageButton
    private var id_menu: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_menu)
        InitComponent()
        Kembali()
        id_menu = intent.getStringExtra("id_menu").toString()
        TampilDetail()

    }


    private fun InitComponent() {
        nama_menu_detail = findViewById(R.id.nama_menu_detail)
        nama_restoran_detail = findViewById(R.id.nama_restoran_detail)
        harga_menu_detail = findViewById(R.id.harga_detail)
        alamat_menu_detail = findViewById(R.id.alamat_menu_detail)
        gambar_detail = findViewById(R.id.gambar_detail_menu)
        btn_pesan_detail = findViewById(R.id.btn_pesan_detail)
        kembali_detail = findViewById(R.id.kembali_detail)
    }


    private fun TampilDetail() {
        val retrofitEndPoint: RetrofitEndPoint = RetrofitClient.connection.create()
        var call: Call<ResponseDetailMenu> = retrofitEndPoint.TampilDetailMenu(id_menu)
        call.enqueue(object : Callback<ResponseDetailMenu> {
            override fun onResponse(
                call: Call<ResponseDetailMenu>,
                response: Response<ResponseDetailMenu>
            ) {
                if (response.body()?.status.equals("success")) {
                    val data: ModelDetailMenu = response.body()?.data!!
                    val serverUrl: String = RetrofitClient.BASE_URL

                    nama_menu_detail.text = data.nama_menu
                    nama_restoran_detail.text = data.nama_restoran
                    harga_menu_detail.text = data.harga
                    alamat_menu_detail.text = data.alamat
                    Glide.with(this@DetailMenu)
                        .load(serverUrl + data.gambar_menu)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(gambar_detail)


                }

        }

                override fun onFailure(call: Call<ResponseDetailMenu>, t: Throwable) {
            t.printStackTrace()
        }

    })
}

    private fun Kembali(){
        kembali_detail.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.layout_in, R.anim.layout_out)
        }
    }

    override fun onBackPressed(){
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.layout_in, R.anim.layout_out)
    }
}