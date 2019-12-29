package com.home.ymvrxandroiddemo.main.common.model.service

import com.home.ymvrxandroiddemo.main.discount.model.DiscountPojo
import io.reactivex.Observable
import retrofit2.http.GET

interface MainService {

    @GET("comments")
    fun getDiscountPojoList(): Observable<MutableList<DiscountPojo>>
}