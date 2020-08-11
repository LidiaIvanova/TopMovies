package com.example.topmovies.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.topmovies.R

@BindingAdapter("app:load_image_src")
fun ImageView.loadImageSrc(url: String?) {
    if (!url.isNullOrEmpty())
        this.loadImage(url)
}

fun ImageView.loadImage(url: String) {
    Glide.with(context)
        .load(url)
        .placeholder(R.drawable.logo)
        .into(this)
}