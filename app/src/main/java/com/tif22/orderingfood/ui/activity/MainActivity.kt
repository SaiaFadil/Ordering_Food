package com.tif22.orderingfood.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tif22.orderingfood.R
import com.tif22.orderingfood.ui.fragment.HistoryFragment
import com.tif22.orderingfood.ui.fragment.HomeFragment
import com.tif22.orderingfood.ui.fragment.LikedFragment

class MainActivity : AppCompatActivity() {
    private lateinit var navbarbttm: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navbarbttm = findViewById(R.id.bottomNav)
        navbarbttm.setOnNavigationItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.beranda -> selectedFragment = HomeFragment()
                R.id.disukai -> selectedFragment = LikedFragment()
                R.id.riwayat -> selectedFragment = HistoryFragment()
            }
            selectedFragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_layout, it)
                    .commit()
            }
            true
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, HomeFragment())
            .commit()
        if (intent.getIntExtra(FRAGMENT, 1) == R.layout.fragment_disukai) {
            navbarbttm.selectedItemId = R.id.disukai
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, LikedFragment())
                .commit()
        }
    }
    companion object {
        const val FRAGMENT: String = "fragment"
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
    }
}
