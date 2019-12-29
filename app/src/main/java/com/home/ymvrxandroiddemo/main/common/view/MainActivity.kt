package com.home.ymvrxandroiddemo.main.common.view

import androidx.annotation.IntRange
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.FragmentUtils
import com.blankj.utilcode.util.ToastUtils
import com.home.ymvrxandroiddemo.R
import com.home.ymvrxandroiddemo.common.base.BaseActivity
import com.home.ymvrxandroiddemo.common.base.BaseFragment
import com.home.ymvrxandroiddemo.main.classification.view.ClassificationFragment
import com.home.ymvrxandroiddemo.main.discount.view.fragment.DiscountFragment
import com.home.ymvrxandroiddemo.main.favorite.view.FavoriteFragment
import com.home.ymvrxandroiddemo.main.member.view.MemberFragment
import com.home.ymvrxandroiddemo.main.welfare.view.WelfareFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    //页面
    private lateinit var welfareFragment: BaseFragment
    private lateinit var classificationFragment: BaseFragment
    private lateinit var discountFragment: LoadingFragment
    private lateinit var favoriteFragment: BaseFragment
    private lateinit var memberFragment: BaseFragment

    //当前页面
    private var currentFragment: BaseFragment? = null

    //子列表合集，方便外部调用选中那个
    private var fragmentList = mutableListOf<BaseFragment>()

    override fun layoutResId(): Int = R.layout.activity_main

    override fun initView() {
        //初始化
        welfareFragment = WelfareFragment.newInstance()
        classificationFragment = ClassificationFragment.newInstance()
        discountFragment = DiscountFragment.newInstance()
        favoriteFragment = FavoriteFragment.newInstance()
        memberFragment = MemberFragment.newInstance()
        //添加
        fragmentList = mutableListOf(
            welfareFragment, classificationFragment, discountFragment,
            favoriteFragment, memberFragment
        )
        //设置选中
        selectFragment(0)
        bottom_navigation_view?.let { view ->
            if (view.selectedItemId != R.id.menu_item_welfare) {
                view.selectedItemId = R.id.menu_item_welfare
            }
            view.setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.menu_item_welfare -> {
                        selectFragment(0)
                    }
                    R.id.menu_item_classification -> {
                        selectFragment(1)
                    }
                    R.id.menu_item_discount -> {
                        selectFragment(2)
                    }
                    R.id.menu_item_favorite -> {
                        selectFragment(3)
                    }
                    R.id.menu_item_member -> {
                        selectFragment(4)
                    }
                }
                true//返回true让其默认选中点击的选项
            }
        }
    }

    override fun initData() {
        //关闭其他所有页面
        ActivityUtils.finishOtherActivities(javaClass)
    }

    //设置选中的fragment
    // 確保傳入的整數值在0至2之間
    private fun selectFragment(@IntRange(from = 0, to = 4) index: Int) {
        //需要显示的fragment
        val fragment = fragmentList[index]
        //和当前选中的一样，则不再处理
        if (currentFragment == fragment) return
        //先关闭之前显示的
        currentFragment?.let { FragmentUtils.hide(it) }
        //设置现在需要显示的
        currentFragment = fragment
        if (!fragment.isAdded) { //没有添加，则添加并显示
            val tag = fragment::class.java.simpleName
            FragmentUtils.add(
                supportFragmentManager,
                fragment,
                frame_layout_container.id,
                tag,
                false
            )
        } else { //添加了就直接显示
            FragmentUtils.show(fragment)
        }
    }

    private var touchTime = 0L
    private val waitTime = 2000L
    override fun onBackPressed() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - touchTime >= waitTime) {
            //让Toast的显示时间和等待时间相同
            ToastUtils.showLong(R.string.double_exit)
            touchTime = currentTime
        } else {
            super.onBackPressed()
        }
    }
}
