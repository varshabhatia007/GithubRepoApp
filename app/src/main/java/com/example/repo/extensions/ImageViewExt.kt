package com.example.repo.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.repo.R

fun ImageView.load(url: String, placeholder: Int = R.drawable.account_placeholder) {


    Glide.with(context)
        .load(url)
        .apply(RequestOptions.circleCropTransform())
        .placeholder(placeholder)
        .error(placeholder)
        .into(this)


}