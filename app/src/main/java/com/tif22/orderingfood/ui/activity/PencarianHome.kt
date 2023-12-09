package com.tif22.orderingfood.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TableLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.tif22.orderingfood.R
import com.tif22.orderingfood.adapter.AdapterCardCari
import com.tif22.orderingfood.api.retrofit.RetrofitClient
import com.tif22.orderingfood.api.retrofit.RetrofitEndPoint
import com.tif22.orderingfood.data.model.ModelMenuCari
import com.tif22.orderingfood.data.response.ResponseMenuCari
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PencarianHome : AppCompatActivity() {

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
        setContentView(R.layout.activity_pencarian_home)
        et_pencarian = findViewById(R.id.et_pencarian)
        cardcaricari = findViewById(R.id.cardcaricari)
        recyclerView = findViewById(R.id.recyclerView)
        relative = findViewById(R.id.relative)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        relatif = findViewById(R.id.relatif)
        et_pencarian.requestFocus()
        swipe_up = AnimationUtils.loadAnimation(this@PencarianHome, R.anim.swipe_up)
        cardcaricari.startAnimation(swipe_up)
        fadeIn = AnimationUtils.loadAnimation(applicationContext, R.anim.show_data_shimmer)
        show_out = AnimationUtils.loadAnimation(applicationContext, R.anim.show_out)
        showAnimin = AnimationUtils.loadAnimation(applicationContext, R.anim.show_in)

        relatif.visibility = View.GONE
        TampilPencarian()

    }


    private fun TampilPencarian() {
        et_pencarian.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Method ini dipanggil saat teks berubah
            }

            override fun afterTextChanged(s: Editable?) {
                TampilData(s.toString())
            }
        })
    }


    private fun TampilData(s: String) {
        val retrofitEndPoint: RetrofitEndPoint =
            RetrofitClient.connection.create(RetrofitEndPoint::class.java)
        val call: Call<ResponseMenuCari> = retrofitEndPoint.TampilMenuCari(s.toString())
        call.enqueue(object : Callback<ResponseMenuCari> {
            override fun onResponse(
                call: Call<ResponseMenuCari>,
                response: Response<ResponseMenuCari>
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
                        val responseModel: ResponseMenuCari? = response.body()
                        val data: List<ModelMenuCari>? = responseModel?.data
                        val adapterCardCari = AdapterCardCari(applicationContext, data)
                        recyclerView.adapter = adapterCardCari

                    }

                } else {
                    Toast.makeText(
                        this@PencarianHome,
                        "gagal " + response.body()?.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseMenuCari>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@PencarianHome, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@PencarianHome, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.layout_in, R.anim.layout_out)
        relative.startAnimation(show_out)
    }
}