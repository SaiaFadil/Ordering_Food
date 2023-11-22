package com.tif22.orderingfood.ui.activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.tif22.orderingfood.Features.setInputFilterNoNumbers
import com.tif22.orderingfood.Features.setInputFilterNumbersOnly
import com.tif22.orderingfood.Features.setInputFilterNoSpaces
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
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {

    private lateinit var card_et_nama: MaterialCardView
    private lateinit var card_et_notelp: MaterialCardView
    private lateinit var card_et_email: MaterialCardView
    private lateinit var card_et_password: MaterialCardView
    private lateinit var card_et_konfpassword: MaterialCardView
    private lateinit var tulisantambahan: TextView
    private lateinit var tv_nama: TextView
    private lateinit var tv_notelp: TextView
    private lateinit var tv_email: TextView
    private lateinit var tv_katasandi: TextView
    private lateinit var tv_konfkatasandi: TextView
    private lateinit var tv_login: TextView
    private lateinit var et_nama: EditText
    private lateinit var et_notelp: EditText
    private lateinit var et_email: EditText
    private lateinit var et_katasandi: EditText
    private lateinit var et_konfkatasandi: EditText
    private lateinit var btn_register: MaterialButton
    private lateinit var tbl_login: TableLayout
    private lateinit var showAnimin: Animation
    private lateinit var showAnimout: Animation
    private lateinit var progressDialog: ProgressDialog



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initComponent()

        //Deklarasi Animasi
        showAnimin = AnimationUtils.loadAnimation(this, R.anim.show_in)
        showAnimout = AnimationUtils.loadAnimation(this, R.anim.show_out)

        //Deklarasi Progress Dialog (Loading)
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Loading...")
        progressDialog.setMessage("Harap Tunggu")
        progressDialog.setCancelable(false)
        progressDialog.setIcon(R.drawable.logoaplikasi)

        //Mulai Animasi Ketika pertama kali membuka menu Registrasi
        MulaiAnimasi()

        //Fungsi Lainnya
        LihatPassword()
        LihatKonfirmasiPassword()

        //Kembali Ke Menu Login
        tv_login.setOnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
//            MulaiAnimasiKeluar()
            startActivity(intent)
            finish()
            overridePendingTransition(R.anim.layout_in, R.anim.layout_out)
        }

        //Tombol Registrasi
        btn_register.setOnClickListener{
            RegistrasiAkun()
        }

        //Implementasi Input Filter
        val maxLengthNoTelp = 16
        val maxLengthKataSandi = 20
        setInputFilterNoNumbers(et_nama)
        setInputFilterNumbersOnly(et_notelp, maxLengthNoTelp)
        setInputFilterNoSpaces(et_katasandi, maxLengthKataSandi)
        setInputFilterNoSpaces(et_konfkatasandi, maxLengthKataSandi)

    }//Akhir OnCreate


    private fun MulaiAnimasi() {
        card_et_nama.startAnimation(showAnimin)
        card_et_notelp.startAnimation(showAnimin)
        card_et_email.startAnimation(showAnimin)
        card_et_password.startAnimation(showAnimin)
        card_et_konfpassword.startAnimation(showAnimin)
        tulisantambahan.startAnimation(showAnimin)
        tulisantambahan.startAnimation(showAnimin)
        tv_nama.startAnimation(showAnimin)
        tv_notelp.startAnimation(showAnimin)
        tv_email.startAnimation(showAnimin)
        tv_katasandi.startAnimation(showAnimin)
        tv_konfkatasandi.startAnimation(showAnimin)
        btn_register.startAnimation(showAnimin)
        tbl_login.startAnimation(showAnimin)
    }

    private fun MulaiAnimasiKeluar() {
        card_et_nama.startAnimation(showAnimout)
        card_et_notelp.startAnimation(showAnimout)
        card_et_email.startAnimation(showAnimout)
        card_et_password.startAnimation(showAnimout)
        card_et_konfpassword.startAnimation(showAnimout)
        tulisantambahan.startAnimation(showAnimout)
        tulisantambahan.startAnimation(showAnimout)
        tv_nama.startAnimation(showAnimout)
        tv_notelp.startAnimation(showAnimout)
        tv_email.startAnimation(showAnimout)
        tv_katasandi.startAnimation(showAnimout)
        tv_konfkatasandi.startAnimation(showAnimout)
        btn_register.startAnimation(showAnimout)
        tbl_login.startAnimation(showAnimout)
    }

    private fun LihatPassword() {
        var isPasswordVisible = false
        val imgLihatPassword = findViewById<ImageView>(R.id.img_lihatpassword)
        imgLihatPassword.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                et_katasandi.transformationMethod = null
                imgLihatPassword.setImageResource(R.drawable.eye)
            } else {
                et_katasandi.transformationMethod = PasswordTransformationMethod.getInstance()
                imgLihatPassword.setImageResource(R.drawable.eye_close)
            }
            et_katasandi.setSelection(et_katasandi.text.length)
        }
    }
    private fun LihatKonfirmasiPassword() {
        var isPasswordVisible = false
        val imgLihatPassword = findViewById<ImageView>(R.id.img_lihatkonfpassword)
        imgLihatPassword.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                et_konfkatasandi.transformationMethod = null
                imgLihatPassword.setImageResource(R.drawable.eye)
            } else {
                et_konfkatasandi.transformationMethod = PasswordTransformationMethod.getInstance()
                imgLihatPassword.setImageResource(R.drawable.eye_close)
            }
            et_konfkatasandi.setSelection(et_konfkatasandi.text.length)
        }

    }
    private fun initComponent() {
        card_et_nama = findViewById(R.id.card_et_nama)
        card_et_notelp = findViewById(R.id.card_et_notelp)
        card_et_email = findViewById(R.id.card_et_email)
        card_et_password = findViewById(R.id.card_et_password)
        card_et_konfpassword = findViewById(R.id.card_et_konfpassword)
        tulisantambahan = findViewById(R.id.tulisantambahan)
        tv_login = findViewById(R.id.tv_login)
        tv_nama = findViewById(R.id.teksnama)
        tv_notelp = findViewById(R.id.teksnotelp)
        tv_email = findViewById(R.id.teksemail)
        tv_katasandi = findViewById(R.id.tekspassword)
        tv_konfkatasandi = findViewById(R.id.tekskonfpassword)
        et_nama = findViewById(R.id.et_nama)
        et_notelp = findViewById(R.id.et_notelp)
        et_email = findViewById(R.id.et_email)
        et_katasandi = findViewById(R.id.et_password)
        et_konfkatasandi = findViewById(R.id.et_konfpassword)
        btn_register = findViewById(R.id.btn_registrasi)
        tbl_login = findViewById(R.id.tabelregistrasi)
    }

    fun isPasswordValid(password: String): Boolean {
        val containsLetter = password.any { it.isLetter() }
        val containsDigit = password.any { it.isDigit() }
        return containsLetter && containsDigit
    }

    private fun RegistrasiAkun() {
        val nama = et_nama.text.toString()
        val notelp = et_notelp.text.toString()
        val email = et_email.text.toString()
        val password = et_katasandi.text.toString()
        val konfpasword = et_konfkatasandi.text.toString()

        if (TextUtils.isEmpty(nama)) {
            et_nama.setError("Nama Harus Diisi!")
            et_nama.requestFocus()
        } else if (TextUtils.isEmpty(notelp)) {
            et_notelp.setError("No Telepon Harus Diisi!")
            et_notelp.requestFocus()
        } else if (TextUtils.isEmpty(email)) {
            et_email.setError("Email Harus Diisi!")
            et_email.requestFocus()
        } else if (TextUtils.isEmpty(password)) {
            et_katasandi.setError("Password Harus Diisi!")
            et_katasandi.requestFocus()
        } else if (!notelp.startsWith("08")) {
            et_notelp.setError("Nomor telepon Tidak Valid!")
            et_notelp.requestFocus()
        } else if (notelp.length >= 15) {
            et_notelp.setError("Nomor Telepon Maksimal 15 angka!")
            et_notelp.requestFocus()
        } else if (notelp.length <= 11) {
            et_notelp.setError("Nomor Telepon Minimal 11 angka!")
            et_notelp.requestFocus()
        } else if (!email.contains("@") || !email.endsWith(".com")) {
            et_email.setError("Email Tidak Valid!")
            et_email.requestFocus()
        } else if (password.length < 8) {
            et_katasandi.setError("Kata Sandi minimal 8 karakter")
            et_katasandi.requestFocus()
        }else if(password.length >= 20) {
            et_katasandi.setError("Kata Sandi maksimal 20 karakter")
            et_katasandi.requestFocus()
        }else if(!isPasswordValid(password)) {
            et_katasandi.setError("Kata Sandi harus terdiri dari huruf dan angka")
            et_katasandi.requestFocus()
        }else if(!TextUtils.equals(password, konfpasword)) {
            et_konfkatasandi.setError("Kata Sandi tidak cocok")
            et_konfkatasandi.requestFocus()
        }else{

            progressDialog.show()
            val retrofitEndPoint: RetrofitEndPoint = RetrofitClient.connection.create()
            val call: Call<ResponseWithoutData> =
                retrofitEndPoint.cekemail(email)
            call.enqueue(object : Callback<ResponseWithoutData> {
                override fun onResponse(
                    call: Call<ResponseWithoutData>, response: Response<ResponseWithoutData>
                ) {
                    if (response.body() != null && response.isSuccessful) {
                        if (response.body()?.status == "ada") {
                            progressDialog.dismiss()
                            et_email.requestFocus()
                            val builder = AlertDialog.Builder(this@RegisterActivity)
                            builder.setMessage("Email Sudah Pernah Digunakan!!")
                                .setPositiveButton("OK") { dialog, _ ->
                                    // Kode yang ingin dijalankan saat tombol OK ditekan
                                    dialog.dismiss()
                                }
                            val dialog1 = builder.create()
                            dialog1.show()
                        }else{
                            sendOtp()
                        }
                    }
                }
                override fun onFailure(call: Call<ResponseWithoutData>, t: Throwable) {
                    t.printStackTrace()
                    Toast.makeText(this@RegisterActivity, "Gagal"+t.message, Toast.LENGTH_SHORT).show()
                    progressDialog.dismiss()
                }
            })
        }
    }
    private fun sendOtp() {
        val retrofitEndPoint: RetrofitEndPoint = RetrofitClient.connection.create(RetrofitEndPoint::class.java)
        val call: Call<VerifyResponse> = retrofitEndPoint.mail(
            et_email.text.toString(),"SignUp","new","1")

        call.enqueue(object : Callback<VerifyResponse> {
            override fun onResponse(call: Call<VerifyResponse>, response: Response<VerifyResponse>) {
                if (response.body() != null && response.body()?.status.equals("success", ignoreCase = true)) {
                    Toast.makeText(this@RegisterActivity, "OTP Terkirim", Toast.LENGTH_SHORT).show()
                    progressDialog.show()
                    Handler().postDelayed({
                        progressDialog.dismiss()
                        val email = et_email.text.toString()
                        var notlp = et_email.text.toString()
                        if (notlp.startsWith("62")) {
                            notlp = "0${notlp.substring(1)}"
                        }

                        val pindah = Intent(this@RegisterActivity, KodeOtpRegister::class.java)
                        pindah.putExtra("email", email)
                        pindah.putExtra("otp", response.body()?.data?.otp)
                        pindah.putExtra("nama_lengkap", et_nama.text.toString())
                        pindah.putExtra("no_telpon", notlp)
                        pindah.putExtra("password", et_katasandi.text.toString())
                        startActivity(pindah)
                        overridePendingTransition(R.anim.layout_in, R.anim.layout_out)
                        finish()
                    }, 2000)
                } else {
                    progressDialog.dismiss()
                    Toast.makeText(this@RegisterActivity, response.body()?.message, Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<VerifyResponse>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(this@RegisterActivity,t.message,Toast.LENGTH_LONG).show()
            }
        })
    }


    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(applicationContext, LoginActivity::class.java)
//        MulaiAnimasiKeluar()
        startActivity(intent)
        finish()
        overridePendingTransition(R.anim.layout_in, R.anim.layout_out)
    }
}