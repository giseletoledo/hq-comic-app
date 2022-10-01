package com.ebac.hqcomicapp.viewBinder

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

@BindingAdapter("srcUrl")
fun ImageView.bindScrUrl(
    url: String
) {
    Glide
        .with(this)
        .load(url)
        .centerInside()
        .into(this)
}

@BindingAdapter("imageList")
fun ImageCarousel.imageList(imageList: List<CarouselItem>?){
    imageList?.let {
        this.setData(it)
    }
}