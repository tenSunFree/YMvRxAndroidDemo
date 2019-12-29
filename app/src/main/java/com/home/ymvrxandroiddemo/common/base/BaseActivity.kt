package com.home.ymvrxandroiddemo.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.mvrx.MvRxView
import com.airbnb.mvrx.MvRxViewId
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.ktx.immersionBar

abstract class BaseActivity : AppCompatActivity(), MvRxView{

    //MvRxView
    private val mvRxViewIdProperty = MvRxViewId()
    final override val mvrxViewId: String by mvRxViewIdProperty

    override fun onCreate(savedInstanceState: Bundle?) {
        this.onCreateBefore()
        this.initStatus()
        initBeforeCreateView(savedInstanceState)
        super.onCreate(savedInstanceState)
        setContentView(layoutResId())
        mvRxViewIdProperty.restoreFrom(savedInstanceState)
        initView()
        initData()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mvRxViewIdProperty.saveTo(outState)
    }

    /**
     * 页面内容布局resId
     */
    protected abstract fun layoutResId(): Int

    abstract fun initView()
    abstract fun initData()
    /**
     * 需要在onCreateView中调用的方法
     */
    protected open fun initBeforeCreateView(savedInstanceState: Bundle?) {

    }

    /** 适配状态栏  */
    protected open fun initStatus() {
        immersionBar {
            transparentStatusBar()
            statusBarDarkFont(true)
            fitsSystemWindows(true)
            statusImmersionBar(this)
        }
    }

    protected open fun statusImmersionBar(immersionBar: ImmersionBar) {

    }

    override fun invalidate() {
    }

    /** 这里可以做一些setContentView之前的操作,如全屏、常亮、设置Navigation颜色、状态栏颜色等  */
    protected open fun onCreateBefore() {}
}
