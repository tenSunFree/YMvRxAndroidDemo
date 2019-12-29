package com.home.ymvrxandroiddemo.main.discount.viewmodel

import com.airbnb.mvrx.*
import com.home.ymvrxandroiddemo.main.common.model.MainRepository
import com.home.ymvrxandroiddemo.main.common.viewmodel.MvRxViewModel
import com.home.ymvrxandroiddemo.main.discount.model.DiscountPojo

/**
 * Async為Kotlin中的sealed class, 有4個subclass: Uninitialized  Loading Success Fail (其中包含1個error欄欄位)
 */
data class DiscountState(
    val androidList: MutableList<DiscountPojo> = mutableListOf(),
    val hasMore: Boolean = false,
    val request: Async<Any> = Uninitialized
) : MvRxState

class DiscountViewModel(state: DiscountState = DiscountState()) :
    MvRxViewModel<DiscountState>(state) {

    private val mainRepository: MainRepository by lazy { MainRepository.instance }

    fun refreshData() {
        getAndroidList()
    }

    private fun getAndroidList() = withState { state ->
        if (state.request is Loading) return@withState
        mainRepository.getDiscountPojoList()
            .execute {
                val result: MutableList<DiscountPojo> = it.invoke() ?: mutableListOf()
                copy(
                    androidList = if (it is Success) {
                        result
                    } else {
                        state.androidList
                    },
                    request = it
                )
            }
    }
}