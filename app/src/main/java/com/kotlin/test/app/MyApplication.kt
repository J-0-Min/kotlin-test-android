package com.kotlin.test.app

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.kotlin.test.di.myDiModule
import org.koin.android.ext.android.startKoin


/**
 * KotlinTest
 * Class : MyApplication
 * Created by jang on 2019-05-03
 *
 * Description : Application Class
 */
class MyApplication : Application() {

    companion object {
        // Log 여부
        // SHOW = true, HIDE = false
        var IS_DEBUG: Boolean = false
    }

    override fun onCreate() {
        super.onCreate()
        startKoin(applicationContext, myDiModule)

        IS_DEBUG = isDebuggable(applicationContext)
    }

    private fun isDebuggable(context: Context): Boolean {
        var debuggable = false

        val pm = context.packageManager
        try {
            val appInfo = pm.getApplicationInfo(context.packageName, 0)
            debuggable = 0 != appInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE
        } catch (e: PackageManager.NameNotFoundException) {
            /* debuggable variable will remain false */
        }

        return debuggable
    }
}