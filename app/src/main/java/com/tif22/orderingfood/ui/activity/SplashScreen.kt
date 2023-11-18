package com.tif22.orderingfood.ui.activity

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TableLayout
import com.tif22.orderingfood.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        //Deklarasi id komponen
        val tabeljudul: TableLayout = findViewById(R.id.tabeljudul)
        val motor: ImageView = findViewById(R.id.motor)

        //Deklarasi animasi
        val fade_in_image: Animation = AnimationUtils.loadAnimation(this, R.anim.fade_in_image)
        val fade_out_image: Animation = AnimationUtils.loadAnimation(this, R.anim.fade_out_image)
        val fade_in_teks: Animation = AnimationUtils.loadAnimation(this, R.anim.fade_in_teks)
        val fade_out_teks: Animation = AnimationUtils.loadAnimation(this, R.anim.fade_out_teks)

        //Menampilkan Gambar
        motor.visibility = View.VISIBLE


        //Menjalankan Animasi Pertama
        tabeljudul.startAnimation(fade_in_teks)
        motor.startAnimation(fade_in_image)



        //Menjalankan Animasi Kedua
        val handlerKomponen= Handler()
        handlerKomponen.postDelayed({
            motor.startAnimation(fade_out_image)
            tabeljudul.startAnimation(fade_out_teks)
            handlerKomponen.postDelayed({
            }, fade_out_image.duration.toLong())
        }, 2000)

        //Menghilangkan Gambar Motor
        val handlerHilang= Handler()
        handlerHilang.postDelayed({
            motor.visibility = View.INVISIBLE
            handlerHilang.postDelayed({
            }, fade_out_image.duration.toLong())
        }, 3000)

        //Melanjutkan ke Halaman Login Setelah 3 detik
        val handler = Handler()
        handler.postDelayed({
                // Start the LoginActivity using an Intent
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.layout_in, R.anim.layout_out);
                finish()
        }, 3000)
    }//akhir onCreate




}
