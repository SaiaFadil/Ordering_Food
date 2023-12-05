package com.tif22.orderingfood.ui.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.tif22.orderingfood.R
import com.tif22.orderingfood.adapter.AdapterCardHome
import com.tif22.orderingfood.adapter.AdapterPromoDashboard
import com.tif22.orderingfood.api.retrofit.RetrofitClient
import com.tif22.orderingfood.api.retrofit.RetrofitEndPoint
import com.tif22.orderingfood.data.model.ModelMenuHome
import com.tif22.orderingfood.data.response.ResponseMenuHome
import com.tif22.orderingfood.data.response.ResponsePoster
import com.tif22.orderingfood.ui.activity.DetailMenu
import com.tif22.orderingfood.ui.activity.PencarianHome
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment(), View.OnClickListener {

    private lateinit var shimmercardhome: ShimmerFrameLayout
    private var fadeIn: Animation? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var kategori: String

    private lateinit var cardcarihome: MaterialCardView
    private lateinit var btn_makanan_home: MaterialButton
    private lateinit var btn_minuman_home: MaterialButton
    private lateinit var btn_snack_home: MaterialButton
    private lateinit var btn_lainnya_home: MaterialButton
    private lateinit var cardPager: MaterialCardView
    private lateinit var viewPager: ViewPager
    private lateinit var berandaatas: RelativeLayout

    private lateinit var showAnimin: Animation
    private lateinit var swipe_down: Animation
    private lateinit var show_alpha: Animation


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view: View? = inflater.inflate(R.layout.fragment_beranda, container, false)
        viewPager = view?.findViewById(R.id.viewPager)!!
        fadeIn = AnimationUtils.loadAnimation(context, R.anim.show_data_shimmer)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        shimmercardhome = view.findViewById(R.id.shimmercardhome)
        cardcarihome = view.findViewById(R.id.cardcarihome)
        berandaatas = view.findViewById(R.id.berandaatas)

        btn_makanan_home = view.findViewById(R.id.btn_makanan_home)
        btn_minuman_home = view.findViewById(R.id.btn_minuman_home)
        btn_snack_home = view.findViewById(R.id.btn_snack_home)
        btn_lainnya_home = view.findViewById(R.id.btn_lainnya_home)
        cardPager = view.findViewById(R.id.cardpager)


        showAnimin = AnimationUtils.loadAnimation(context, R.anim.show_in)
        swipe_down = AnimationUtils.loadAnimation(context, R.anim.swipe_down)
        show_alpha = AnimationUtils.loadAnimation(context, R.anim.show_alpha)
        ShowAnimasi()

        btn_makanan_home.setOnClickListener(this)
        btn_minuman_home.setOnClickListener(this)
        btn_snack_home.setOnClickListener(this)
        btn_lainnya_home.setOnClickListener(this)
        cardcarihome.setOnClickListener(this)

        getPoster()



        kategori = "makanan"
        tampilkanData()

        return view
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_makanan_home -> {
                btn_makanan_home.backgroundTintList = resources.getColorStateList(R.color.primary)
                btn_minuman_home.backgroundTintList = resources.getColorStateList(R.color.secondary)
                btn_snack_home.backgroundTintList = resources.getColorStateList(R.color.secondary)
                btn_lainnya_home.backgroundTintList = resources.getColorStateList(R.color.secondary)
                kategori = "makanan"
                tampilkanData()
            }
            R.id.btn_minuman_home -> {
                btn_minuman_home.backgroundTintList = resources.getColorStateList(R.color.primary)
                btn_makanan_home.backgroundTintList = resources.getColorStateList(R.color.secondary)
                btn_snack_home.backgroundTintList = resources.getColorStateList(R.color.secondary)
                btn_lainnya_home.backgroundTintList = resources.getColorStateList(R.color.secondary)
                kategori = "minuman"
                tampilkanData()
            }
            R.id.btn_snack_home -> {
                btn_snack_home.backgroundTintList = resources.getColorStateList(R.color.primary)
                btn_makanan_home.backgroundTintList = resources.getColorStateList(R.color.secondary)
                btn_minuman_home.backgroundTintList = resources.getColorStateList(R.color.secondary)
                btn_lainnya_home.backgroundTintList = resources.getColorStateList(R.color.secondary)
                kategori = "snack"
                tampilkanData()
            }
            R.id.btn_lainnya_home -> {
                btn_lainnya_home.backgroundTintList = resources.getColorStateList(R.color.primary)
                btn_makanan_home.backgroundTintList = resources.getColorStateList(R.color.secondary)
                btn_snack_home.backgroundTintList = resources.getColorStateList(R.color.secondary)
                btn_minuman_home.backgroundTintList = resources.getColorStateList(R.color.secondary)
                kategori = "lainnya"
                tampilkanData()
            }
            R.id.cardcarihome -> {
                val intent = Intent(activity, PencarianHome::class.java)
                activity?.startActivity(intent)
                activity?.overridePendingTransition(R.anim.layout_in, R.anim.layout_out)
            }
        }
    }
    private fun getPoster() {
        val retrofitEndPoint: RetrofitEndPoint =
            RetrofitClient.connection.create(RetrofitEndPoint::class.java)
        val call: Call<ResponsePoster> = retrofitEndPoint.getPoster()
        call.enqueue(object : Callback<ResponsePoster> {
            @SuppressLint("SuspiciousIndentation")
            override fun onResponse(
                call: Call<ResponsePoster>,
                response: Response<ResponsePoster>
            ) {
                if (response.isSuccessful && response.body()?.status.equals("success")) {

                    val serverUrl: String = RetrofitClient.BASE_URL
                    val posterUrls = mutableListOf<String>()
                    response.body()?.data?.forEach { poster ->
                        val posterUrl = serverUrl + poster.poster
                        posterUrls.add(posterUrl)
                        Log.e("urlimage", posterUrl)
                        val adapterPromo = AdapterPromoDashboard(childFragmentManager, posterUrls)
                        viewPager.adapter = adapterPromo
                    }


                } else {
                    Toast.makeText(activity, "Failed to get posters", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponsePoster>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun tampilkanData() {
        val retrofitEndPoint: RetrofitEndPoint =
            RetrofitClient.connection.create(RetrofitEndPoint::class.java)
        val call: Call<ResponseMenuHome> = retrofitEndPoint.MenuHome(kategori)
        call.enqueue(object : Callback<ResponseMenuHome> {
            override fun onResponse(
                call: Call<ResponseMenuHome>,
                response: Response<ResponseMenuHome>
            ) {
                if (response.body()?.status.equals("success")) {


                    if (response.body()?.data?.isEmpty() == true) {
                        shimmercardhome.startShimmer()
                        recyclerView.visibility = View.GONE

                    } else {
                        recyclerView.visibility = View.VISIBLE
                        recyclerView.startAnimation(fadeIn)
                        shimmercardhome.visibility = View.GONE
                        shimmercardhome.stopShimmer()
                        val responseModel: ResponseMenuHome? = response.body()
                        val data: List<ModelMenuHome>? = responseModel?.data
                        val adapterCardHome = AdapterCardHome(context,data)
                        recyclerView.adapter = adapterCardHome

                    }

                } else {
                    Toast.makeText(
                        activity,
                        "gagal " + response.body()?.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseMenuHome>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun ShowAnimasi(){
        cardPager.startAnimation(showAnimin)
        cardcarihome.startAnimation(swipe_down)
        berandaatas.startAnimation(show_alpha)

    }
}
