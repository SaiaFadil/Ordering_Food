package com.tif22.orderingfood.ui.activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.HorizontalScrollView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.android.material.card.MaterialCardView
import com.tif22.orderingfood.R
import com.tif22.orderingfood.api.google.GoogleUsers
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
    private lateinit var tv_register: TextView
    private lateinit var btn_lupasandi: TextView
    private lateinit var tabelregistrasi: TableLayout
    private lateinit var logingoogle: Button
    private lateinit var showAnimin: Animation
    private lateinit var showAnimout: Animation
    private lateinit var progressDialog: ProgressDialog
    private lateinit var googleUsers: GoogleUsers


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
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

        //Mulai Animasi
        MulaiAnimasi()

        //Fungsi Tambahan
        ShowCenterImage()
        LihatPassword()

        //buton login
        btn_login.setOnClickListener({ GetLogin() })

        //Pindah ke Register
        tv_register.setOnClickListener {
            val intent = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.layout_in, R.anim.layout_out)
            MulaiAnimasiKeluar()
        }

        //Pindah ke Lupa Katasandi
        btn_lupasandi.setOnClickListener{
            val intent = Intent(applicationContext, EmailLupaSandi::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(R.anim.layout_in, R.anim.layout_out)
        }

        // Instantiate GoogleUsers
        val googleUsers = GoogleUsers(this) // 'this' refers to the current activity or context

// Set OnClickListener for logingoogle button
        logingoogle.setOnClickListener {
            // Execute the login function from the GoogleUsers class
            val signInIntent = googleUsers.getIntent()
            if (signInIntent != null) {
                startActivityForResult(signInIntent, GoogleUsers.REQUEST_CODE)
            } else {
                Toast.makeText(this@LoginActivity, "Gagal", Toast.LENGTH_SHORT).show()
            }
        }


    }//Akhir onCreate


    //Login Google

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
        } else if (!email.endsWith(".com") && !email.contains("@")) {
            et_email.setError("Email Tidak Valid!")
            et_email.requestFocus()
        } else {
            progressDialog.show()
            val retrofitEndPoint: RetrofitEndPoint = RetrofitClient.connection.create()
            var call: Call<UsersResponse> = retrofitEndPoint.login(email, password)
            call.enqueue(object : Callback<UsersResponse> {
                override fun onResponse(
                    call: Call<UsersResponse>, response: Response<UsersResponse>
                ) {
                    if (response.body() != null && response.body()?.status.equals("admin")) {
                        progressDialog.dismiss()
                        Log.v("retrofit", "call admin success")
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.layout_in, R.anim.layout_out)
                    } else if (response.body() != null && response.body()?.status.equals("user")) {
                        progressDialog.dismiss()
                        Log.v("retrofit", "call user success")
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.layout_in, R.anim.layout_out)
                    } else if (response.body() != null && response.body()?.status.equals("failed")){
                        et_password.error = "Password Salah!"
                        et_password.requestFocus()
                        et_password.setText("")
                        progressDialog.dismiss()
                        response.body().toString()
                    }
                }

                override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                    progressDialog.dismiss()
                    t.printStackTrace()
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

    private fun LihatPassword() {
        // Inisialisasi variabel boolean untuk melacak status password terlihat atau tidak
        var isPasswordVisible = false

        // Mendapatkan referensi ke ImageButton
        val imgLihatPassword = findViewById<ImageView>(R.id.img_lihatpassword)

        // Mendefinisikan fungsi onClick untuk ImageButton
        imgLihatPassword.setOnClickListener {
            // Mengubah status password terlihat atau tidak
            isPasswordVisible = !isPasswordVisible

            // Memperlihatkan atau menyembunyikan password berdasarkan status terlihat atau tidak
            if (isPasswordVisible) {
                // Jika password hendak ditampilkan
                et_password.transformationMethod =
                    null // Menghapus transformasi untuk menampilkan password
                imgLihatPassword.setImageResource(R.drawable.eye) // Mengubah ikon menjadi mata terlihat
            } else {
                // Jika password hendak disembunyikan
                et_password.transformationMethod =
                    PasswordTransformationMethod.getInstance() // Menggunakan transformasi untuk menyembunyikan password
                imgLihatPassword.setImageResource(R.drawable.eye_close) // Mengubah ikon menjadi mata tidak terlihat
            }

            // Mengatur kursor ke posisi terakhir di EditText
            et_password.setSelection(et_password.text.length)
        }

    }

    private fun MulaiAnimasi() {
        btn_login.startAnimation(showAnimin)
        btn_lupasandi.startAnimation(showAnimin)
        logingoogle.startAnimation(showAnimin)
        layoutPager.startAnimation(showAnimin)
        tulisantambahan.startAnimation(showAnimin)
        teksemail.startAnimation(showAnimin)
        tekspassword.startAnimation(showAnimin)
        tabelregistrasi.startAnimation(showAnimin)
        card_et_email.startAnimation(showAnimin)
        card_et_password.startAnimation(showAnimin)
    }

    private fun MulaiAnimasiKeluar() {
        btn_login.startAnimation(showAnimout)
        btn_lupasandi.startAnimation(showAnimout)
        logingoogle.startAnimation(showAnimout)
        layoutPager.startAnimation(showAnimout)
        tulisantambahan.startAnimation(showAnimout)
        teksemail.startAnimation(showAnimout)
        tekspassword.startAnimation(showAnimout)
        tabelregistrasi.startAnimation(showAnimout)
        card_et_email.startAnimation(showAnimout)
        card_et_password.startAnimation(showAnimout)
    }


    private fun initComponent() {
        //deklarasi id komponen
        scrollHorizontal = findViewById(R.id.ScrollHorizontal)
        et_email = findViewById(R.id.et_email)
        et_password = findViewById(R.id.et_password)
        card_et_email = findViewById(R.id.card_et_email)
        card_et_password = findViewById(R.id.card_et_password)
        tv_register = findViewById(R.id.tv_Register)
        btn_login = findViewById(R.id.btn_login)
        layoutPager = findViewById(R.id.layoutPager)
        tulisantambahan = findViewById(R.id.tulisantambahan)
        teksemail = findViewById(R.id.teksemail)
        tekspassword = findViewById(R.id.tekspassword)
        btn_lupasandi = findViewById(R.id.btn_lupasandi)
        tabelregistrasi = findViewById(R.id.tabelregistrasi)
        logingoogle = findViewById(R.id.logingoogle)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.layout_in, R.anim.layout_out)

    }
}