package com.home.ymvrxandroiddemo.main.welfare.view

import android.view.View
import com.home.ymvrxandroiddemo.R
import com.home.ymvrxandroiddemo.common.base.BaseFragment

class WelfareFragment : BaseFragment() {

    override val contentLayout: Int = R.layout.fragment_welfare

    companion object {
        fun newInstance(): WelfareFragment {
            return WelfareFragment()
        }
    }

    override fun initView(root: View?) {
    }

    override fun initData() {
    }
}