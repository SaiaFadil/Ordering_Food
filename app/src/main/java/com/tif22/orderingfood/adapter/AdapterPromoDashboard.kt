package com.tif22.orderingfood.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.tif22.orderingfood.ui.fragment.PromoFragment

class AdapterPromoDashboard(fm: FragmentManager, private val imageUrls: List<String>) : FragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            val newPosition = position % count
            return PromoFragment.newInstance(imageUrls[newPosition])
        }

        override fun getCount(): Int {
            return imageUrls.size
        }
    }

