package com.home.ymvrxandroiddemo.main.common.model

import com.home.ymvrxandroiddemo.common.extention.applySchedulers
import com.home.ymvrxandroiddemo.main.common.model.service.MainService
import com.home.ymvrxandroiddemo.main.discount.model.DiscountPojo
import io.reactivex.Observable

class MainRepository {

    private object SingletonHolder {
        val holder = MainRepository()
    }

    companion object {
        val instance =
            SingletonHolder.holder
    }

    private val service: MainService = RetrofitAPi.instance.getApi(MainService::class.java)

    fun getDiscountPojoList(): Observable<MutableList<DiscountPojo>> {
        return service.getDiscountPojoList().compose(applySchedulers())
    }
}