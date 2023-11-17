package com.tif22.orderingfood.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.tif22.orderingfood.Component.fragment.ViewPagerLogin;
import com.tif22.orderingfood.R;

public class AdapterPosterLogin extends FragmentStatePagerAdapter {

        public AdapterPosterLogin(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            int itemCount = getCount();
            int newPosition = position % itemCount;

            if (newPosition == 0) {
                return ViewPagerLogin.newInstance(R.drawable.makanan1);
            } else if (newPosition == 1) {
                return ViewPagerLogin.newInstance(R.drawable.makanan2);
            } else if (newPosition == 2) {
                return ViewPagerLogin.newInstance(R.drawable.makanan1);
            }

            return null;
        }



        @Override
        public int getCount() {
            return 3; // Jumlah gambar yang dapat digeser
        }

}
