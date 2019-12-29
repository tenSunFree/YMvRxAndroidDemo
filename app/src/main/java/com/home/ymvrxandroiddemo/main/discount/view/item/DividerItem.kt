package com.home.ymvrxandroiddemo.main.discount.view.item

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.home.ymvrxandroiddemo.common.base.BaseEpoxyHolder
import com.home.ymvrxandroiddemo.common.base.BaseEpoxyModel
import com.home.ymvrxandroiddemo.R

/**
 * Description:
 * @author: caiyoufei
 * @date: 2019/10/1 16:58
 */
@EpoxyModelClass(layout = R.layout.item_divider)
abstract class DividerItem : BaseEpoxyModel<BaseEpoxyHolder>() {

    @EpoxyAttribute
    var heightPx: Int? = null

    init {
        heightPx = 1
    }

    override fun bind(holder: BaseEpoxyHolder) {
        super.bind(holder)
        if (heightDp == null) {
            heightPx?.let {
                val layoutParams = holder.itemView.layoutParams
                layoutParams.height = it
            }
        }
    }
}