package com.example.mealdeal.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mealdeal.R


fun ImageView.loadImageWithGlide(imageUrl: String?){
    Glide
        .with(this)
        .load(imageUrl)
        .apply(
            RequestOptions()
                .placeholder(R.drawable.food_menu)
                .fitCenter())
        .into(this)
}

