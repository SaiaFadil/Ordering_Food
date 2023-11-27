package com.tif22.orderingfood.ui.fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.tif22.orderingfood.R

class PromoFragment : Fragment() {
    companion object {
        private const val IMAGE_URL_KEY = "image_url"

        fun newInstance(imageUrl: String): PromoFragment {
            val fragment = PromoFragment()
            val args = Bundle()
            args.putString(IMAGE_URL_KEY, imageUrl)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val imageUrl = arguments?.getString(IMAGE_URL_KEY)
        val view = inflater.inflate(R.layout.fragment_promo, container, false)
        val imageView = view.findViewById<ImageView>(R.id.imageView)

        imageUrl?.let {
            // Konfigurasi Glide di sini
            Glide.with(this)
                .load(it)
                .diskCacheStrategy(DiskCacheStrategy.NONE) // Tidak menyimpan cache di disk
                .skipMemoryCache(true) // Tidak menyimpan cache di memory
                .into(imageView)
        }
        return view
    }
}
