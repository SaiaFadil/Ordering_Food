package com.tif22.orderingfood.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.button.MaterialButton
import com.tif22.orderingfood.R
import com.tif22.orderingfood.adapter.AdapterCardHome
import com.tif22.orderingfood.adapter.AdapterPromoDashboard
import com.tif22.orderingfood.api.retrofit.RetrofitClient
import com.tif22.orderingfood.api.retrofit.RetrofitEndPoint
import com.tif22.orderingfood.data.model.ModelMenuHome
import com.tif22.orderingfood.data.response.ResponseMenuHome
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment(), View.OnClickListener {

    private lateinit var recyclerView: RecyclerView
    private val kategori: String = "makanan"

    private lateinit var btn_makanan_home: MaterialButton
    private lateinit var btn_minuman_home: MaterialButton
    private lateinit var btn_snack_home: MaterialButton
    private lateinit var btn_lainnya_home: MaterialButton
    private lateinit var viewPager: ViewPager

    private val imageUrls = listOf(
        "https://assets.pikiran-rakyat.com/crop/0x0:0x0/x/photo/2021/08/17/1095053953.jpg",
        "https://4.bp.blogspot.com/-dQG0lBiNBW8/VwNnfOyGM9I/AAAAAAAADsw/aAM_8E4Ad8IH344I7HwVEXy0QSGDvo_4w/s1600/bn-20160307161131.jpg"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View? = inflater.inflate(R.layout.fragment_beranda, container, false)
        viewPager = view?.findViewById(R.id.viewPager)!!

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        btn_makanan_home = view.findViewById(R.id.btn_makanan_home)
        btn_minuman_home = view.findViewById(R.id.btn_minuman_home)
        btn_snack_home = view.findViewById(R.id.btn_snack_home)
        btn_lainnya_home = view.findViewById(R.id.btn_lainnya_home)
        btn_makanan_home.setOnClickListener(this)
        btn_minuman_home.setOnClickListener(this)
        btn_snack_home.setOnClickListener(this)
        btn_lainnya_home.setOnClickListener(this)

        val adapterPromo = AdapterPromoDashboard(childFragmentManager, imageUrls)
        viewPager.adapter = adapterPromo

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
            }
            R.id.btn_minuman_home -> {
                btn_minuman_home.backgroundTintList = resources.getColorStateList(R.color.primary)
                btn_makanan_home.backgroundTintList = resources.getColorStateList(R.color.secondary)
                btn_snack_home.backgroundTintList = resources.getColorStateList(R.color.secondary)
                btn_lainnya_home.backgroundTintList = resources.getColorStateList(R.color.secondary)
            }
            R.id.btn_snack_home -> {
                btn_snack_home.backgroundTintList = resources.getColorStateList(R.color.primary)
                btn_makanan_home.backgroundTintList = resources.getColorStateList(R.color.secondary)
                btn_minuman_home.backgroundTintList = resources.getColorStateList(R.color.secondary)
                btn_lainnya_home.backgroundTintList = resources.getColorStateList(R.color.secondary)
            }
            R.id.btn_lainnya_home -> {
                btn_lainnya_home.backgroundTintList = resources.getColorStateList(R.color.primary)
                btn_makanan_home.backgroundTintList = resources.getColorStateList(R.color.secondary)
                btn_snack_home.backgroundTintList = resources.getColorStateList(R.color.secondary)
                btn_minuman_home.backgroundTintList = resources.getColorStateList(R.color.secondary)
            }
        }
    }

    private fun tampilkanData() {
        val retrofitEndPoint: RetrofitEndPoint = RetrofitClient.connection.create(RetrofitEndPoint::class.java)
        val call: Call<ResponseMenuHome> = retrofitEndPoint.MenuHome(kategori)
        call.enqueue(object : Callback<ResponseMenuHome> {
            override fun onResponse(
                call: Call<ResponseMenuHome>,
                response: Response<ResponseMenuHome>
            ) {
                if (response.body()?.status.equals("success")) {
                    val responseModel: ResponseMenuHome? = response.body()
                    val data: List<ModelMenuHome>? = responseModel?.data

                    val adapterCardHome = AdapterCardHome(data)
                    recyclerView.adapter = adapterCardHome
                } else {
                    Toast.makeText(activity, "gagal " + response.body()?.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseMenuHome>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
