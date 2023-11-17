package com.tif22.orderingfood.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.viewpager.widget.ViewPager
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
    private lateinit var et_email: EditText
    private lateinit var et_password: EditText
    private lateinit var btn_login : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //deklarasi id komponen
        et_email = findViewById(R.id.et_email)
        et_password = findViewById(R.id.et_password)
        btn_login = findViewById(R.id.btn_login)
        scrollHorizontal = findViewById(R.id.ScrollHorizontal)


        //buton login
        btn_login.setOnClickListener(View.OnClickListener {
            GetLogin()
        })


        // Menggeser ke posisi tengah item
        scrollHorizontal.post {
            scrollHorizontal.scrollTo(850, 0)
        }

    }




    //fungsi cek login
     fun GetLogin() {
        val email = et_email.text.toString()
        val password = et_password.text.toString()

        if (TextUtils.isEmpty(email)){
            et_email.setError("Email Harus Diisi!")
            et_email.requestFocus()
        }else if(TextUtils.isEmpty(password)){
            et_password.setError("Password Harus Diisi!")
            et_password.requestFocus()
        }else {
            val retrofitEndPoint: RetrofitEndPoint = RetrofitClient.connection.create()
            var call: Call<UsersResponse> = retrofitEndPoint.login(email, password)
            call.enqueue(object : Callback<UsersResponse> {
                override fun onResponse(
                    call: Call<UsersResponse>,
                    response: Response<UsersResponse>
                ) {
                    if (response.body() != null && response.isSuccessful){
                        Log.v("retrofit", "call success")
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                    }else{
                        response.body().toString()
                    }
                }
                override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                    Log.v("retrofit", "call failed")
                }
            })
        }
    }
}