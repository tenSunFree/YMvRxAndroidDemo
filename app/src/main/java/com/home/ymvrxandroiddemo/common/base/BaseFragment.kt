package com.home.ymvrxandroiddemo.common.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.airbnb.mvrx.BaseMvRxFragment
import com.home.ymvrxandroiddemo.common.extention.removeParent

abstract class BaseFragment : BaseMvRxFragment() {

    // 頁面基礎信息
    lateinit var mContext: Activity
    private var isFragmentVisible = true
    private var isPrepared = false
    private var isFirst = true
    private var isInViewPager = false
    protected var rootView: FrameLayout? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context as Activity
    }

    /**
     * 內容佈局的ResId
     */
    protected abstract val contentLayout: Int

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initBeforeCreateView(savedInstanceState)
        // 第一次的時候加載xml
        if (contentLayout > 0 && rootView == null) {
            val contentView = inflater.inflate(contentLayout, null)
            if (contentView is FrameLayout) {
                contentView.layoutParams = ViewGroup.LayoutParams(-1, -1)
                rootView = contentView
            } else {
                rootView = FrameLayout(mContext)
                rootView?.layoutParams = ViewGroup.LayoutParams(-1, -1)
                rootView?.addView(contentView, ViewGroup.LayoutParams(-1, -1))
            }
        } else {
            // 防止重新create時還存在
            rootView?.removeParent()
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        isPrepared = true
        lazyLoad()
    }

    /**
     * 視圖真正可見的時候才調用
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        isFragmentVisible = isVisibleToUser
        isInViewPager = true
        lazyLoad()
    }

    override fun invalidate() {}

    //懒加载
    private fun lazyLoad() {
        if (!isInViewPager) {
            isFirst = false
            initData()
            return
        }
        if (!isPrepared || !isFragmentVisible || !isFirst) {
            return
        }
        isFirst = false
        initData()
    }

    // 初始化前的處理
    protected open fun initBeforeCreateView(savedInstanceState: Bundle?) {}

    /**
     * 初始化View
     */
    protected abstract fun initView(root: View?)

    /**
     * 初始化數據
     */
    protected abstract fun initData()
}