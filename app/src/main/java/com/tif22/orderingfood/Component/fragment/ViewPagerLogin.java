package com.tif22.orderingfood.Component.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tif22.orderingfood.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewPagerLogin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewPagerLogin extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_IMAGE_RES = "imageRes";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ViewPagerLogin() {
    }
    public static ViewPagerLogin newInstance(String param1, String param2) {
        ViewPagerLogin fragment = new ViewPagerLogin();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static ViewPagerLogin newInstance(int imageRes) {
        ViewPagerLogin fragment = new ViewPagerLogin();
        Bundle args = new Bundle();
        args.putInt(ARG_IMAGE_RES, imageRes);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_pager_login, container, false);
        ImageView imageView = view.findViewById(R.id.imageView);
        int imageRes = getArguments().getInt(ARG_IMAGE_RES);
        imageView.setImageResource(imageRes);
        return view;
    }
}
