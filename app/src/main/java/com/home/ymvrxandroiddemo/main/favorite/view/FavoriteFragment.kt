package com.home.ymvrxandroiddemo.main.favorite.view

import android.view.View
import com.home.ymvrxandroiddemo.R
import com.home.ymvrxandroiddemo.common.base.BaseFragment

class FavoriteFragment : BaseFragment() {

    override val contentLayout: Int = R.layout.fragment_favorite

    companion object {
        fun newInstance(): FavoriteFragment {
            return FavoriteFragment()
        }
    }

    override fun initView(root: View?) {
    }

    override fun initData() {
    }
}