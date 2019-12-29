package com.home.ymvrxandroiddemo.common.utils

import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.ToastUtils

/**
 * Description:
 * @author: caiyoufei
 * @date: 2019/10/1 16:52
 */
class NetUtils private constructor() {
    private object SingletonHolder {
        val holder = NetUtils()
    }

    companion object {
        val instance = SingletonHolder.holder
    }

    //无网络则吐司
    fun checkToast(): Boolean {
        if (!NetworkUtils.isConnected()) {
            // Utils.getApp().toast(R.string.no_network)
            ToastUtils.showLong("no_network")
            return false
        }
        return true
    }
}