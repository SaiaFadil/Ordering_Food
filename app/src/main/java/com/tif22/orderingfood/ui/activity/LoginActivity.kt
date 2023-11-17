package com.tif22.orderingfood.ui.activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.tif22.orderingfood.R
import com.tif22.orderingfood.adapter.AdapterPosterLogin

class LoginActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var scrollHorizontal: HorizontalScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        scrollHorizontal = findViewById(R.id.ScrollHorizontal)

        // Menggeser ke posisi tengah item
        scrollHorizontal.post {
            scrollHorizontal.scrollTo(850, 0)
        }

    }
    

}