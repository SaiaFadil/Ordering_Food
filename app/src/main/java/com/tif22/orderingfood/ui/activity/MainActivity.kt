package com.tif22.orderingfood.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.tif22.orderingfood.R;

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.layout_in, R.anim.layout_out)
    }
}