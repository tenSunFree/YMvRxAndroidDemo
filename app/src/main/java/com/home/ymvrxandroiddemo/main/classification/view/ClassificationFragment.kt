package com.home.ymvrxandroiddemo.main.classification.view

import android.view.View
import com.home.ymvrxandroiddemo.R
import com.home.ymvrxandroiddemo.common.base.BaseFragment

class ClassificationFragment : BaseFragment() {

    override val contentLayout: Int = R.layout.fragment_classification

    companion object {
        fun newInstance(): ClassificationFragment {
            return ClassificationFragment()
        }
    }

    override fun initView(root: View?) {
    }

    override fun initData() {
    }
}