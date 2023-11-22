package com.tif22.orderingfood.ui.activity

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.tif22.orderingfood.R
import com.tif22.orderingfood.api.retrofit.RetrofitClient
import com.tif22.orderingfood.api.retrofit.RetrofitEndPoint
import com.tif22.orderingfood.data.model.VerifyUtil
import com.tif22.orderingfood.data.response.ResponseResetOtp
import com.tif22.orderingfood.data.response.ResponseWithoutData
import com.tif22.orderingfood.data.response.UsersResponse
import com.tif22.orderingfood.data.response.VerifyResponse
import `in`.aabhasjindal.otptextview.OTPListener
import `in`.aabhasjindal.otptextview.OtpTextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class KodeOtpRegister : AppCompatActivity() {
    private lateinit var konfir: Button
    private lateinit var inputotp: OtpTextView
    private lateinit var tulisansalah: TextView
    private lateinit var progressDialog: ProgressDialog

    private lateinit var email: String
    private lateinit var otp: String
    private lateinit var nama_lengkap: String
    private lateinit var no_telpon: String
    private lateinit var password: String

    private var totalSeconds = 10
    private lateinit var util: VerifyUtil
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kode_otp_register)

        MulaiGif()

        konfir = findViewById(R.id.button_konfirmasiotpp)
        inputotp = findViewById(R.id.kode_otp)
        tulisansalah = findViewById(R.id.tulisansalah)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Sedang Mengirim Ulang OTP...")
        progressDialog.setMessage("Mohon Tunggu...")
        progressDialog.setCancelable(false)
        progressDialog.setIcon(R.drawable.logoaplikasi)

        email = intent.getStringExtra("email") ?: ""
        otp = intent.getStringExtra("otp") ?: ""
        nama_lengkap = intent.getStringExtra("nama_lengkap") ?: ""
        no_telpon = intent.getStringExtra("no_telpon") ?: ""
        password = intent.getStringExtra("password") ?: ""

        Log.e("OTP ", otp.toString())

        konfir.isEnabled = false
        util = VerifyUtil(this)
        util.setEmail(email)
        totalSeconds = 10
        Log.e("OTP", "" + totalSeconds)
        updateSecond()
        konfir.setOnClickListener {
            tulisansalah.visibility = View.INVISIBLE
            inputotp.setOTP(null)
            progressDialog.show()
            totalSeconds = 0
            val retrofitEndPoint: RetrofitEndPoint = RetrofitClient.connection.create(RetrofitEndPoint::class.java)
            var call: Call<VerifyResponse> =
                retrofitEndPoint.mail(email, "SignUp", "update", "1")
                    call.enqueue(object : Callback<VerifyResponse> {
                override fun onResponse(
                    call: Call<VerifyResponse>,response: Response<VerifyResponse>
                ) {
                    Handler().postDelayed({
                        progressDialog.dismiss()
                        if (response.body() != null && response.body()?.status.equals("success")) {
                            Toast.makeText(this@KodeOtpRegister, "OTP Terkirim", Toast.LENGTH_SHORT).show()
                            konfir.isEnabled = false
                            totalSeconds += 60
//
                            val retrofitEndPoint: RetrofitEndPoint = RetrofitClient.connection.create(RetrofitEndPoint::class.java)
                            var call: Call<ResponseResetOtp> =
                                retrofitEndPoint.ResetOtp(email)
                            call.enqueue(object :Callback<ResponseResetOtp>{
                                override fun onResponse(
                                    call: Call<ResponseResetOtp>,
                                    response: Response<ResponseResetOtp>
                                ) {
                                    if (response.isSuccessful){
                                        this@KodeOtpRegister.otp = response.body()?.otp.toString()
                                        util.setOtp(response.body()?.otp.toString())
                                    }else{
                                        Toast.makeText(this@KodeOtpRegister, "gagal ", Toast.LENGTH_SHORT).show()
                                    }
                                }

                                override fun onFailure(call: Call<ResponseResetOtp>, t: Throwable) {

                                }
                            })


                        } else {
                            Toast.makeText(this@KodeOtpRegister, response.body()?.message, Toast.LENGTH_SHORT).show()
                        }
                    }, 2000)
                }

                override fun onFailure(call: Call<VerifyResponse>, t: Throwable) {
                    Toast.makeText(this@KodeOtpRegister, t.message, Toast.LENGTH_SHORT).show()
                    t.printStackTrace()
                    progressDialog.dismiss()
                }
            })
        }

        inputotp.setOtpListener(object : OTPListener {
            override fun onInteractionListener() {
                // Implementasi sesuai kebutuhan
            }

            override fun onOTPComplete(otp: String) {
                Log.e("OTP COM ", otp)
                Log.e("OTP THIS ", this@KodeOtpRegister.otp)
                Log.e("OTP UTIL ", util.getOtp())
                if (this@KodeOtpRegister.otp == inputotp.getOTP()) {
                    val retrofitEndPoint: RetrofitEndPoint = RetrofitClient.connection.create()
                    val call: Call<ResponseWithoutData>
                    = retrofitEndPoint.register(nama_lengkap, no_telpon, email, password)
                    call.enqueue(object : Callback<ResponseWithoutData> {
                        override fun onResponse(
                            call: Call<ResponseWithoutData>,
                            response: Response<ResponseWithoutData>
                        ) {
                            if (response.isSuccessful) {
                                val pindah = Intent(this@KodeOtpRegister, LoginActivity::class.java)
                                pindah.putExtra("email", email)
                                startActivity(pindah)
                                overridePendingTransition(R.anim.layout_in, R.anim.layout_out)
                            } else {
                                Toast.makeText(this@KodeOtpRegister, "Registrasi Gagal"+response.body()?.message, Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<ResponseWithoutData>, t: Throwable) {
                            Toast.makeText(this@KodeOtpRegister, t.message, Toast.LENGTH_SHORT).show()
                        }
                    })
                } else {
                    tulisansalah.visibility = View.VISIBLE
                }
            }
        })
    }

    public override fun onResume() {
        super.onResume()
        MulaiGif()
    }
    private fun MulaiGif(){

        val imageView = findViewById<ImageView>(R.id.gambaranimasi)
        val url = "https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExOHVkcXIwM3F4aWVpNzJianpnOGNnc2p1dXRpZmxib2NuNjgxdDkwOCZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9cw/fKAwpFssEBdpbKPBKa/giphy.gif"

        Glide.with(this)
            .asGif()
            .load(url)
            .into(imageView)
    }
    private fun updateButtonName() {
        val minutes = totalSeconds / 60
        konfir.text = if (totalSeconds > 59) {
            "Kirim Ulang ($minutes Menit)"
        } else {
            "Kirim Ulang ($totalSeconds Detik)"
        }
    }

    private fun updateSecond() {
        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                if (this@KodeOtpRegister.totalSeconds > 0) {
                    handler.postDelayed(this, 1000)
                    updateButtonName()
                    this@KodeOtpRegister.totalSeconds--
                } else if (this@KodeOtpRegister.totalSeconds == 0) {
                    konfir.text = "Kirim Ulang"
                    konfir.isEnabled = true
                    handler.postDelayed(this, 1000)
                }
            }
        }
        handler.post(runnable)
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.layout_in, R.anim.layout_out)
    }
}