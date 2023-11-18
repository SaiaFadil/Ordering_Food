package com.tif22.orderingfood.ui.activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.viewpager.widget.ViewPager
import com.google.android.material.card.MaterialCardView
import com.tif22.orderingfood.R
import com.tif22.orderingfood.api.retrofit.RetrofitClient
import com.tif22.orderingfood.api.retrofit.RetrofitEndPoint
import com.tif22.orderingfood.data.response.UsersResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class LoginActivity : AppCompatActivity() {

    private lateinit var scrollHorizontal: HorizontalScrollView
    private lateinit var card_et_email: MaterialCardView
    private lateinit var card_et_password: MaterialCardView
    private lateinit var et_email: EditText
    private lateinit var et_password: EditText
    private lateinit var btn_login: Button
    private lateinit var layoutPager: LinearLayout
    private lateinit var tulisantambahan: TextView
    private lateinit var teksemail: TextView
    private lateinit var tekspassword: TextView
    private lateinit var btn_lupasandi: TextView
    private lateinit var tabelregistrasi: TableLayout
    private lateinit var logingoogle: Button
    private lateinit var showAnim: Animation
    private lateinit var progressDialog: ProgressDialog

    private fun initComponent(){
        //deklarasi id komponen
        scrollHorizontal = findViewById(R.id.ScrollHorizontal)
        et_email = findViewById(R.id.et_email)
        et_password = findViewById(R.id.et_password)
        card_et_email = findViewById(R.id.card_et_email)
        card_et_password = findViewById(R.id.card_et_password)
        btn_login = findViewById(R.id.btn_login)
        layoutPager = findViewById(R.id.layoutPager)
        tulisantambahan = findViewById(R.id.tulisantambahan)
        teksemail = findViewById(R.id.teksemail)
        tekspassword = findViewById(R.id.tekspassword)
        btn_lupasandi = findViewById(R.id.btn_lupasandi)
        tabelregistrasi = findViewById(R.id.tabelregistrasi)
        logingoogle = findViewById(R.id.logingoogle)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initComponent()

        //Deklarasi Animasi
        showAnim = AnimationUtils.loadAnimation(this, R.anim.show)

        //Deklarasi Progress Dialog (Loading)
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Loading...")
        progressDialog.setMessage("Harap Tunggu")
        progressDialog.setCancelable(false)
        progressDialog.setIcon(R.drawable.logoaplikasi)

        //Mulai Animasi
        MulaiAnimasi()

        //Fungsi Tambahan
        ShowCenterImage()

        //buton login
        btn_login.setOnClickListener(View.OnClickListener {  GetLogin() })


    }//Akhir onCreate


    //fungsi cek login
    fun GetLogin() {
        val email = et_email.text.toString()
        val password = et_password.text.toString()
        if (TextUtils.isEmpty(email)) {
            et_email.setError("Email Harus Diisi!")
            et_email.requestFocus()
        } else if (TextUtils.isEmpty(password)) {
            et_password.setError("Password Harus Diisi!")
            et_password.requestFocus()
        } else {
            progressDialog.show()
            val retrofitEndPoint: RetrofitEndPoint = RetrofitClient.connection.create()
            var call: Call<UsersResponse> = retrofitEndPoint.login(email, password)
            call.enqueue(object : Callback<UsersResponse> {
                override fun onResponse(
                    call: Call<UsersResponse>, response: Response<UsersResponse>
                ) {
                    if (response.body() != null && response.isSuccessful) {
                        progressDialog.dismiss()
                        Log.v("retrofit", "call success")
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.layout_in, R.anim.layout_out)
                    } else {
                        progressDialog.dismiss()
                        response.body().toString()
                    }
                }

                override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                    progressDialog.dismiss()
                    Log.v("retrofit", "call failed")
                }
            })
        }
    }

    //memulai animasi
    private fun ShowCenterImage() {
        // Menggeser ke posisi tengah item
        scrollHorizontal.post {
            scrollHorizontal.scrollTo(850, 0)
        }
    }

    private fun MulaiAnimasi() {
        btn_login.startAnimation(showAnim)
        btn_lupasandi.startAnimation(showAnim)
        logingoogle.startAnimation(showAnim)
        layoutPager.startAnimation(showAnim)
        tulisantambahan.startAnimation(showAnim)
        teksemail.startAnimation(showAnim)
        tekspassword.startAnimation(showAnim)
        tabelregistrasi.startAnimation(showAnim)
        card_et_email.startAnimation(showAnim)
        card_et_password.startAnimation(showAnim)
    }


    override fun onBackPressed() {
        super.onBackPressed()

    }
}