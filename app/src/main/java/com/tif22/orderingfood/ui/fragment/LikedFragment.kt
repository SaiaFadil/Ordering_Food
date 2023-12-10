package com.tif22.orderingfood.ui.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.tif22.orderingfood.R
import com.tif22.orderingfood.adapter.AdapterCardCari
import com.tif22.orderingfood.adapter.AdapterCardDisukai
import com.tif22.orderingfood.api.retrofit.RetrofitClient
import com.tif22.orderingfood.api.retrofit.RetrofitEndPoint
import com.tif22.orderingfood.data.model.ModelMenuCari
import com.tif22.orderingfood.data.model.ModelMenuDisukai
import com.tif22.orderingfood.data.response.ResponseMenuDisukai
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LikedFragment : Fragment() {

    private lateinit var et_pencarian: EditText
    private lateinit var cardcaricari: MaterialCardView
    private lateinit var swipe_up: Animation
    private lateinit var recyclerView: RecyclerView
    private lateinit var relatif: RelativeLayout
    private lateinit var relative: RelativeLayout
    private lateinit var fadeIn: Animation
    private lateinit var show_out: Animation
    private lateinit var showAnimin: Animation


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_disukai, container, false)

        et_pencarian = view.findViewById(R.id.et_pencarian_disukai)
        cardcaricari = view.findViewById(R.id.cardcaridisukai)
        recyclerView = view.findViewById(R.id.recyclerView)
        relative = view.findViewById(R.id.relative)
        recyclerView.layoutManager = LinearLayoutManager(context)
        relatif = view.findViewById(R.id.relatif)
        swipe_up = AnimationUtils.loadAnimation(context, R.anim.swipe_up)
        cardcaricari.startAnimation(swipe_up)
        fadeIn = AnimationUtils.loadAnimation(context, R.anim.show_data_shimmer)
        show_out = AnimationUtils.loadAnimation(context, R.anim.show_out)
        showAnimin = AnimationUtils.loadAnimation(context, R.anim.show_in)

        relatif.visibility = View.GONE

        TampilDataSemua()
        TampilPencarian()
        return view
    }




    private fun TampilPencarian() {
        et_pencarian.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Method ini dipanggil saat teks berubah
            }

            override fun afterTextChanged(s: Editable?) {

                if (et_pencarian.text.equals(null)){
                    TampilDataSemua()
                }else{
                    TampilData(s.toString())
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()

    }

    private fun TampilData(s :String){
        val sharedPreferences: SharedPreferences = context?.getSharedPreferences("prefLogin",
            AppCompatActivity.MODE_PRIVATE)!!
        val id_user = sharedPreferences.getString("id_user", "")
        val retrofitEndPoint: RetrofitEndPoint =
            RetrofitClient.connection.create(RetrofitEndPoint::class.java)
        val call: Call<ResponseMenuDisukai> = retrofitEndPoint.TampilDisukaiCari(id_user.toString(),s.toString())
        call.enqueue(object : Callback<ResponseMenuDisukai> {
            override fun onResponse(
                call: Call<ResponseMenuDisukai>,
                response: Response<ResponseMenuDisukai>
            ) {
                if (response.body()?.status.equals("success")) {


                    if (response.body()?.data?.isEmpty() == true) {
                        relatif.visibility = View.VISIBLE
                        relatif.startAnimation(showAnimin)
                        recyclerView.visibility = View.GONE

                    } else {
                        recyclerView.visibility = View.VISIBLE
                        recyclerView.startAnimation(fadeIn)
                        relatif.visibility = View.GONE
                        val responseModel: ResponseMenuDisukai? = response.body()
                        val data: List<ModelMenuDisukai>? = responseModel?.data
                        val adapterCardDisukai = AdapterCardDisukai(context,data)
                        recyclerView.adapter = adapterCardDisukai

                    }

                } else {
                    Toast.makeText(
                        context,
                        "gagal " + response.body()?.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseMenuDisukai>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun TampilDataSemua() {
        val sharedPreferences: SharedPreferences = context?.getSharedPreferences("prefLogin",
            AppCompatActivity.MODE_PRIVATE)!!
        val id_user = sharedPreferences.getString("id_user", "")
        val retrofitEndPoint: RetrofitEndPoint =
            RetrofitClient.connection.create(RetrofitEndPoint::class.java)
        val call: Call<ResponseMenuDisukai> = retrofitEndPoint.TampilDisukai(id_user.toString())
        call.enqueue(object : Callback<ResponseMenuDisukai> {
            override fun onResponse(
                call: Call<ResponseMenuDisukai>,
                response: Response<ResponseMenuDisukai>
            ) {
                if (response.body()?.status.equals("success")) {


                    if (response.body()?.data?.isEmpty() == true) {
                        relatif.visibility = View.VISIBLE
                        relatif.startAnimation(showAnimin)
                        recyclerView.visibility = View.GONE

                    } else {
                        recyclerView.visibility = View.VISIBLE
                        recyclerView.startAnimation(fadeIn)
                        relatif.visibility = View.GONE
                        val responseModel: ResponseMenuDisukai? = response.body()
                        val data: List<ModelMenuDisukai>? = responseModel?.data
                        val adapterCardDisukai = AdapterCardDisukai(context, data)
                        recyclerView.adapter = adapterCardDisukai

                    }

                } else {
                    Toast.makeText(
                        context,
                        "gagal " + response.body()?.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseMenuDisukai>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
