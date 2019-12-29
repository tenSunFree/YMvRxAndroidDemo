package com.home.ymvrxandroiddemo.main.discount.view.item

import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.home.ymvrxandroiddemo.R
import com.home.ymvrxandroiddemo.common.base.BaseEpoxyHolder
import com.home.ymvrxandroiddemo.common.base.BaseEpoxyModel
import com.home.ymvrxandroiddemo.common.extention.click
import com.home.ymvrxandroiddemo.common.extention.pressEffectBgColor
import com.home.ymvrxandroiddemo.main.discount.model.DiscountPojo
import kotlinx.android.synthetic.main.item_content.view.*

@EpoxyModelClass(layout = R.layout.item_content)
abstract class ContentItem : BaseEpoxyModel<BaseEpoxyHolder>() {

    // 数据源
    @EpoxyAttribute
    var dataBean: DiscountPojo? = null
    // 点击item
    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var onItemClick: ((bean: DiscountPojo?) -> Unit)? = null

    override fun onBind(itemView: View) {
        dataBean?.let {
            itemView.text_view_name.text = it.name
            itemView.text_view_email.text = it.email
        }
        // item点击
        itemView.click { onItemClick?.invoke(dataBean) }
        // item按压效果
        itemView.pressEffectBgColor()
    }
}
