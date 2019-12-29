package com.home.ymvrxandroiddemo.common.extention

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.load(url: String?) {
    url?.let {
        Glide.with(this.context)
            .load(url).into(this)
    }
}