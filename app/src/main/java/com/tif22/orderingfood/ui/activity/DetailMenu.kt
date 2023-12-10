package com.tif22.orderingfood.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.button.MaterialButton
import com.tif22.orderingfood.R
import com.tif22.orderingfood.adapter.AdapterCardHome
import com.tif22.orderingfood.api.retrofit.RetrofitClient
import com.tif22.orderingfood.api.retrofit.RetrofitEndPoint
import com.tif22.orderingfood.data.model.ModelDetailMenu
import com.tif22.orderingfood.data.model.ModelMenuHome
import com.tif22.orderingfood.data.response.ResponseDetailMenu
import com.tif22.orderingfood.data.response.ResponseMenuHome
import com.tif22.orderingfood.data.response.ResponseWithoutData
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
    private lateinit var btn_like: ImageButton
    private var id_menu: String = ""
    private lateinit var shimmer: ShimmerFrameLayout

    private lateinit var linear: LinearLayout
    private lateinit var relatif: RelativeLayout
    private lateinit var fade_in: Animation
    private var asal: String = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_menu)
        InitComponent()
        fade_in = AnimationUtils.loadAnimation(this@DetailMenu, R.anim.show_data_shimmer)
        asal = intent.getStringExtra("model").toString()
        Kembali()
        id_menu = intent.getStringExtra("id_menu").toString()
        TampilDetail()
        Disukai()
    }


    private fun InitComponent() {
        nama_menu_detail = findViewById(R.id.nama_menu_detail)
        nama_restoran_detail = findViewById(R.id.nama_restoran_detail)
        harga_menu_detail = findViewById(R.id.harga_detail)
        alamat_menu_detail = findViewById(R.id.alamat_menu_detail)
        gambar_detail = findViewById(R.id.gambar_detail_menu)
        btn_pesan_detail = findViewById(R.id.btn_pesan_detail)
        kembali_detail = findViewById(R.id.kembali_detail)
        linear = findViewById(R.id.lineardata)
        relatif = findViewById(R.id.relatif)
        shimmer = findViewById(R.id.shimmerdetail)
        btn_like = findViewById(R.id.btn_like)
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
                    val serverUrl: String = RetrofitClient.BASE_URL
                    val data: ModelDetailMenu = response.body()?.data!!
                    if (response.body()!!.data.id_menu.isEmpty()) {
                        shimmer.startShimmer()
                        linear.visibility = View.GONE
                        relatif.visibility = View.GONE
                    } else {
                        linear.visibility = View.VISIBLE
                        relatif.visibility = View.VISIBLE
                        linear.startAnimation(fade_in)
                        shimmer.visibility = View.GONE
                        shimmer.stopShimmer()
                    }
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
    
    private fun Disukai(){
        val sharedPreferences: SharedPreferences = getSharedPreferences("prefLogin",AppCompatActivity.MODE_PRIVATE)!!
        val id_user = sharedPreferences.getString("id_user", "")
        val retrofitEndPoint: RetrofitEndPoint = RetrofitClient.connection.create()
        var statusLike = false

        val call: Call<ResponseWithoutData> = retrofitEndPoint.CekDisukai(id_user.toString(), id_menu)
        call.enqueue(object : Callback<ResponseWithoutData> {
            override fun onResponse(call: Call<ResponseWithoutData>, response: Response<ResponseWithoutData>) {
                if (response.isSuccessful && response.body()?.status.equals("tersedia")) {
                    btn_like.setImageResource(R.drawable.like_on)
                    statusLike = true
                } else {
                    btn_like.setImageResource(R.drawable.like_off)
                    statusLike = false
                }
            }

            override fun onFailure(call: Call<ResponseWithoutData>, t: Throwable) {
                t.printStackTrace()
            }
        })

        btn_like.setOnClickListener {
            if (statusLike) {
                statusLike = false
                btn_like.setImageResource(R.drawable.like_off)
                val endPoint: RetrofitEndPoint = RetrofitClient.connection.create()
                endPoint.HapusDisukai(id_user.toString(), id_menu)
                    .enqueue(object :
                        Callback<ResponseWithoutData> {
                        override fun onResponse(call: Call<ResponseWithoutData>, response: Response<ResponseWithoutData>) {
                            if (response.isSuccessful && response.body()?.status.equals("success")) {
                                Log.e("Berhasil Hapus","Berhasil Hapus")
                            }
                        }

                        override fun onFailure(call: Call<ResponseWithoutData>, t: Throwable) {
                            t.printStackTrace()
                        }
                    })
            }
            else {
                statusLike = true
                btn_like.startAnimation(AnimationUtils.loadAnimation(this@DetailMenu,R.anim.scale_up))
                btn_like.setImageResource(R.drawable.like_on)
                val endPoint: RetrofitEndPoint = RetrofitClient.connection.create()
                endPoint.TambahDisukai(id_user.toString(), id_menu)
                    .enqueue(object :
                        Callback<ResponseWithoutData> {
                        override fun onResponse(call: Call<ResponseWithoutData>, response: Response<ResponseWithoutData>) {
                            if (response.isSuccessful && response.body()?.message.equals("success")) {
                                Log.e("Berhasil Tambah","Berhasil Tambah")

                            }
                        }

                        override fun onFailure(call: Call<ResponseWithoutData>, t: Throwable) {
                            t.printStackTrace()
                        }
                    })
            }

        }

    }

    private fun Kembali() {
        kembali_detail.setOnClickListener {

            if(asal.equals("home")) {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.layout_in, R.anim.layout_out)
            }else if (asal.equals("liked")){
                finish()
                overridePendingTransition(R.anim.layout_in, R.anim.layout_out)

            }
        }
    }

    
    

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
    }
}