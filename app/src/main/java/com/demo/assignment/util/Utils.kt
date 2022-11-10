package com.demo.assignment.util

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.demo.assignment.R

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun ImageView.load(url: String?, placeHolder: Int = R.mipmap.ic_launcher) {
    Glide.with(this.context)
        .load(url).dontTransform()
        .apply(
            RequestOptions()
                .placeholder(placeHolder)
                .error(placeHolder)
        )
        .into(this)
}