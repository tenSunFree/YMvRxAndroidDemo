package com.home.ymvrxandroiddemo.common.base

import android.view.View
import com.airbnb.epoxy.EpoxyHolder

/**
 * Holder的基类
 */
open class BaseEpoxyHolder : EpoxyHolder() {
    lateinit var itemView: View
    override fun bindView(itemView: View) {
        this.itemView = itemView
    }
}