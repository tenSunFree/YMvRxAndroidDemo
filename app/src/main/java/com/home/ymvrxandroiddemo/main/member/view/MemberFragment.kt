package com.home.ymvrxandroiddemo.main.member.view

import android.view.View
import com.home.ymvrxandroiddemo.R
import com.home.ymvrxandroiddemo.common.base.BaseFragment

class MemberFragment : BaseFragment() {

    override val contentLayout: Int = R.layout.fragment_member

    companion object {
        fun newInstance(): MemberFragment {
            return MemberFragment()
        }
    }

    override fun initView(root: View?) {
    }

    override fun initData() {
    }
}