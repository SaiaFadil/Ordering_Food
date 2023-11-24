package com.tif22.orderingfood.ui.activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.tif22.orderingfood.R
import com.tif22.orderingfood.api.retrofit.RetrofitClient
import com.tif22.orderingfood.api.retrofit.RetrofitEndPoint
import com.tif22.orderingfood.data.response.ResponseWithoutData
import com.tif22.orderingfood.data.response.UsersResponse
import com.tif22.orderingfood.data.response.VerifyResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class EmailLupaSandi : AppCompatActivity() {

    private lateinit var motor: ImageView
    private lateinit var tulisanatas: TextView
    private lateinit var tulisanbawah: TextView
    private lateinit var card_et_email: CardView
    private lateinit var button_lupakatasandi: Button
    private lateinit var et_emailLupakatasandi: EditText
    private lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_lupa_sandi)
        InitComponent()

        //Deklarasi animasi
        val fade_in_image: Animation = AnimationUtils.loadAnimation(this, R.anim.fade_in_image)
        val showAnimin: Animation = AnimationUtils.loadAnimation(this, R.anim.show_in)

        //Deklarasi Progress Dialog (Loading)
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Loading...")
        progressDialog.setMessage("Harap Tunggu")
        progressDialog.setCancelable(false)
        progressDialog.setIcon(R.drawable.logoaplikasi)

        //Menjalankan Animasi Pertama
        motor.startAnimation(fade_in_image)
        tulisanatas.startAnimation(showAnimin)
        tulisanbawah.startAnimation(showAnimin)
        card_et_email.startAnimation(showAnimin)
        button_lupakatasandi.startAnimation(showAnimin)

        button_lupakatasandi.setOnClickListener {
            val email = et_emailLupakatasandi.text.toString()

            if (email.isEmpty()) {
                et_emailLupakatasandi.error = "Email Harus Terisi"
            } else if (!email.endsWith("@gmail.com")) {
                et_emailLupakatasandi.error = "Email Tidak Valid!"
            } else {
                progressDialog.show()

                val retrofitEndPoint: RetrofitEndPoint = RetrofitClient.connection.create()
                var call: Call<ResponseWithoutData> = retrofitEndPoint.cekemail(et_emailLupakatasandi.text.toString())
                call.enqueue(object : Callback<ResponseWithoutData> {
                    override fun onResponse(call: Call<ResponseWithoutData>, response: Response<ResponseWithoutData>) {
                        if (response.body()?.status == "ada") {
                            Handler().postDelayed({
                                sendOtp()
                            }, 1000)
                        } else {
                            progressDialog.dismiss()
                            et_emailLupakatasandi.requestFocus()
                            Toast.makeText(this@EmailLupaSandi, "Email Belum Terdaftar", Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onFailure(call: Call<ResponseWithoutData>, t: Throwable) {
                        t.printStackTrace()
                        progressDialog.dismiss()
                    }
                })
            }
        }
    }


    private fun sendOtp() {
        val retrofitEndPoint: RetrofitEndPoint = RetrofitClient.connection.create(RetrofitEndPoint::class.java)
        val call: Call<VerifyResponse> = retrofitEndPoint.mail(
            et_emailLupakatasandi.text.toString(),"SignUp","new","1")

        call.enqueue(object : Callback<VerifyResponse> {
            override fun onResponse(call: Call<VerifyResponse>, response: Response<VerifyResponse>) {
                if (response.body() != null && response.body()?.status.equals("success", ignoreCase = true)) {
                    Toast.makeText(this@EmailLupaSandi, "OTP Terkirim", Toast.LENGTH_SHORT).show()
                    progressDialog.show()
                    Handler().postDelayed({
                        progressDialog.dismiss()
                        val email = et_emailLupakatasandi.text.toString()
                        val pindah = Intent(this@EmailLupaSandi, KodeOtpRegister::class.java)
                        pindah.putExtra("email", email)
                        pindah.putExtra("otp", response.body()?.data?.otp)
                        startActivity(pindah)
                        overridePendingTransition(R.anim.layout_in, R.anim.layout_out)
                        finish()
                    }, 2000)
                } else {
                    progressDialog.dismiss()
                    Toast.makeText(this@EmailLupaSandi, response.body()?.message, Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<VerifyResponse>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(this@EmailLupaSandi,t.message,Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun InitComponent(){
        motor = findViewById(R.id.gambaranimasi)
        tulisanatas = findViewById(R.id.tulisanatas)
        tulisanbawah = findViewById(R.id.tulisanbawah)
        card_et_email = findViewById(R.id.card_et_email)
        button_lupakatasandi = findViewById(R.id.button_lupakatasandi)
        et_emailLupakatasandi = findViewById(R.id.et_emailLupakatasandi)
    }
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
        finish()
        overridePendingTransition(R.anim.layout_in, R.anim.layout_out)
    }
}