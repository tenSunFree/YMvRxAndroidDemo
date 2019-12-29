package com.home.ymvrxandroiddemo.common.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.airbnb.mvrx.mock.MvRxMocks
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ProcessUtils
import com.blankj.utilcode.util.Utils
import com.home.ymvrxandroiddemo.BuildConfig
import com.home.ymvrxandroiddemo.common.extention.applySchedulers
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import me.jessyan.autosize.AutoSizeConfig

abstract class BaseApplication : Application() {

    companion object {
        fun getApp(): BaseApplication {
            return Utils.getApp() as BaseApplication
        }
    }

    //子进程中的初始化是否完成,有的必须要子进程中的初始化完成后才能调用
    private var initFinishInChildThread = false
    private var launcherTime = 0L

    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
        if (ProcessUtils.isMainProcess()) {
            initInMainProcess()
            MvRxMocks.install(this)
        }
    }

    //主进程中的初始化
    @SuppressLint("CheckResult")
    private fun initInMainProcess() {
        LogUtils.getConfig()
            .setLogSwitch(BuildConfig.DEBUG) // log开关
            .setGlobalTag("more")
            .stackDeep = 1 // log栈
        // 主線程中的初始化(必要的放在這, 不然APP打開會比較慢)
        initInMainThread()
        // 子線程中的初始化(為了防止APP打開太慢, 將不必要的放在子線程中初始化)
        Observable.create(ObservableOnSubscribe<Boolean> { emitter ->
            initInChildThread()
            emitter.onNext(true)
            emitter.onComplete()
        })
            .compose(applySchedulers())
            .subscribe({
                initFinishInChildThread = true
            }, { LogUtils.e(it) }, {})
        // 字體sp不跟隨系統大小變化
        AutoSizeConfig.getInstance().isExcludeFontScale = true
    }

    // 主線程中的初始化(只在主進程中調用)
    abstract fun initInMainThread()

    // 子線程中的初始化(只在主進程中調用)
    abstract fun initInChildThread()

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
        launcherTime = System.currentTimeMillis()
    }
}
