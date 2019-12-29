package com.home.ymvrxandroiddemo.main.discount.view.fragment

import android.view.View
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.Success
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.ToastUtils
import com.home.ymvrxandroiddemo.R
import com.home.ymvrxandroiddemo.main.common.view.LoadingFragment
import com.home.ymvrxandroiddemo.main.common.viewmodel.MvRxEpoxyController
import com.home.ymvrxandroiddemo.main.discount.view.item.contentItem
import com.home.ymvrxandroiddemo.main.discount.view.item.dividerItem
import com.home.ymvrxandroiddemo.main.discount.view.item.errorEmptyItem
import com.home.ymvrxandroiddemo.main.discount.viewmodel.DiscountState
import com.home.ymvrxandroiddemo.main.discount.viewmodel.DiscountViewModel
import kotlinx.android.synthetic.main.fragment_discount.*

class DiscountFragment : LoadingFragment() {

    companion object {
        fun newInstance(): DiscountFragment {
            return DiscountFragment()
        }
    }

    override val contentLayout: Int = R.layout.fragment_discount
    // 數據層
    private val viewModel: DiscountViewModel by lazy { DiscountViewModel() }

    override fun initView(root: View?) {
        dynEpoxyRecycler.setController(epoxyController)
    }

    override fun initData() {
        // 請求狀態和結果監聽
        viewModel.subscribe { state ->
            if (state.request is Loading) { // 请求开始
                //如果没有显示下拉刷新则显示loading
                // if (state.getDiscountPojoList.isNullOrEmpty() && isNeed) {
                if (state.androidList.isNullOrEmpty()) {
                    // 显示loading
                    showLoadingView()
                    //为了防止loading结束后还存在失败的view所以需刷新一下
                    if (state.androidList.isNullOrEmpty()) epoxyController.data = state
                }
            } else if (state.request.complete) { // 請求結束
                // smDynRefresh.finishRefresh(state.request is Success)
                dismissLoadingView()
                epoxyController.data = state
                if (state.request is Fail) { // 請求失敗
                    ToastUtils.showLong("請求失敗: ${state.request.error.message ?: ""}")
                    // Log.e("yzy", "失败原因:${state.request.error.message ?: ""}")
                }
            }
        }
        //请求数据
        viewModel.refreshData()
    }

    private val epoxyController =
        MvRxEpoxyController<DiscountState> { state ->
            if (!state.androidList.isNullOrEmpty()) {
                // 有數據
                state.androidList.forEachIndexed { _, pojo ->
                    contentItem {
                        id("discount")
                        dataBean(pojo)
                        onItemClick { pojo ->
                            ToastUtils.showLong(pojo.name)
                        }
                    }
                    dividerItem {
                        id("discount")
                        heightPx(5)
                    }
                }
            } else {
                // 無數據
                when (state.request) {
                    is Success -> errorEmptyItem {
                        id("discount")
                        imageResource(R.drawable.svg_no_data)
                        tipsText(mContext.getString(R.string.no_data))
                    }
                    // 無網絡或者請求失敗
                    is Fail -> errorEmptyItem {
                        id("discount")
                        if (NetworkUtils.isConnected()) {
                            imageResource(R.drawable.svg_fail)
                            tipsText(mContext.getString(R.string.net_fail_retry))
                        } else {
                            imageResource(R.drawable.svg_no_network)
                            tipsText(mContext.getString(R.string.net_error_retry))
                        }
                        onRetryClick { viewModel.refreshData() }
                    }
                    else -> LogUtils.i("初始化无数据空白")
                }
            }
        }
}