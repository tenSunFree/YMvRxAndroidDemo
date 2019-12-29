package com.home.ymvrxandroiddemo.common

import com.home.ymvrxandroiddemo.common.base.BaseApplication
import com.home.ymvrxandroiddemo.main.common.model.ApiConstants
import com.home.ymvrxandroiddemo.main.common.model.retrofitConfig

class YMADApplication : BaseApplication() {

    override fun initInChildThread() {

    }

    override fun initInMainThread() {
        retrofitConfig {
            context = this@YMADApplication
            baseUrl = ApiConstants.Address.BASE_URL
        }
    }
}

