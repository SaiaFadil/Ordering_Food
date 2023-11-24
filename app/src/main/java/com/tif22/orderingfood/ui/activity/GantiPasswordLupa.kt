package com.tif22.orderingfood.ui.activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.tif22.orderingfood.R
import com.tif22.orderingfood.api.retrofit.RetrofitClient
import com.tif22.orderingfood.api.retrofit.RetrofitEndPoint
import com.tif22.orderingfood.data.response.ResponseWithoutData
import com.tif22.orderingfood.data.response.UsersResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class GantiPasswordLupa : AppCompatActivity() {
    
    private lateinit var et_password: EditText
    private lateinit var et_konfpassword: EditText
    private lateinit var aturulang: Button
    private lateinit var email: String
    private lateinit var passwordstring: String
    private lateinit var konfpas: String
    private lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ganti_password_lupa)
        InitComponent()

        //Fungsi Lihat Kata Sandi
        LihatPassword()
        LihatKonfirmasiPassword()

        //Progress Dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Sedang Memproses Data...")
        progressDialog.setMessage("Mohon Tunggu...")
        progressDialog.setCancelable(false)
        progressDialog.setIcon(R.drawable.logoaplikasi)


        //Mendapat Email Dari class sebelumnya
        email = intent.getStringExtra("email") ?: ""
        Toast.makeText(this@GantiPasswordLupa, "Cek Email :"+email, Toast.LENGTH_SHORT).show()

        //Button Ganti Katasandi
        aturulang.setOnClickListener {
            passwordstring = et_password.text.toString().trim()
            konfpas = et_konfpassword.text.toString().trim()

            if (email.isEmpty() || passwordstring.isEmpty() || konfpas.isEmpty()) {
                Toast.makeText(this@GantiPasswordLupa, "Semua kolom harus diisi", Toast.LENGTH_SHORT).show()
            } else if (passwordstring.length < 8) {
                et_password.setError("Kata Sandi minimal 8 karakter")
                et_password.requestFocus()
            }else if(passwordstring.length >= 20) {
                et_password.setError("Kata Sandi maksimal 20 karakter")
                et_password.requestFocus()
            }else if(!isPasswordValid(passwordstring)) {
                et_password.setError("Kata Sandi harus terdiri dari huruf dan angka")
                et_password.requestFocus()
            }else if(!TextUtils.equals(passwordstring, konfpas)) {
                et_konfpassword.setError("Kata Sandi tidak cocok")
                et_konfpassword.requestFocus()
            }else{
                val builder = AlertDialog.Builder(this@GantiPasswordLupa)
                builder.setMessage("Apakah Anda Yakin Mengubah Kata Sandi Anda?")
                builder.setPositiveButton("Ya") { dialog, which ->
                    progressDialog.show()
                    Handler().postDelayed({
                        progressDialog.show()
                        val retrofitEndPoint: RetrofitEndPoint = RetrofitClient.connection.create()
                        var call: Call<ResponseWithoutData> = retrofitEndPoint.updatePassword(email, passwordstring)
                        call.enqueue(object : Callback<ResponseWithoutData> {
                            override fun onResponse(call: Call<ResponseWithoutData>, response: Response<ResponseWithoutData>) {
                                progressDialog.dismiss()
                                if (response.body() != null && response.body()?.status.equals("success")) {
                                    val pindah = Intent(this@GantiPasswordLupa, LoginActivity::class.java)
                                    Toast.makeText(this@GantiPasswordLupa, "Password Berhasil Diubah", Toast.LENGTH_SHORT)
                                        .show()
                                    startActivity(pindah)
                                    finish()
                                    overridePendingTransition(R.anim.layout_in, R.anim.layout_out)
                                } else {
                                    Toast.makeText(this@GantiPasswordLupa, response.body()?.message, Toast.LENGTH_SHORT).show()
                                }
                            }

                            override fun onFailure(call: Call<ResponseWithoutData>, t: Throwable) {
                                progressDialog.dismiss()
                                Toast.makeText(this@GantiPasswordLupa, t.message, Toast.LENGTH_SHORT).show()
                            }
                        })
                    }, 1000)
                }
                builder.setNegativeButton("Tidak") { dialog, which -> dialog.dismiss() }
                val alertDialog = builder.create()
                alertDialog.show()
            }
        }
    }
    private fun InitComponent(){
        aturulang = findViewById(R.id.aturulangkatasandi)
        et_password = findViewById(R.id.et_password)
        et_konfpassword = findViewById(R.id.et_konfpassword)
    }
    private fun LihatPassword() {
        var isPasswordVisible = false
        val imgLihatPassword = findViewById<ImageView>(R.id.img_lihatpassword)
        imgLihatPassword.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                et_password.transformationMethod = null
                imgLihatPassword.setImageResource(R.drawable.eye)
            } else {
                et_password.transformationMethod = PasswordTransformationMethod.getInstance()
                imgLihatPassword.setImageResource(R.drawable.eye_close)
            }
            et_password.setSelection(et_password.text.length)
        }
    }
    fun isPasswordValid(password: String): Boolean {
        val containsLetter = password.any { it.isLetter() }
        val containsDigit = password.any { it.isDigit() }
        return containsLetter && containsDigit
    }
    private fun LihatKonfirmasiPassword() {
        var isPasswordVisible = false
        val imgLihatPassword = findViewById<ImageView>(R.id.img_lihatkonfpassword)
        imgLihatPassword.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                et_konfpassword.transformationMethod = null
                imgLihatPassword.setImageResource(R.drawable.eye)
            } else {
                et_konfpassword.transformationMethod = PasswordTransformationMethod.getInstance()
                imgLihatPassword.setImageResource(R.drawable.eye_close)
            }
            et_konfpassword.setSelection(et_konfpassword.text.length)
        }

    }
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
        finish()
        overridePendingTransition(R.anim.layout_in, R.anim.layout_out)
    }
}