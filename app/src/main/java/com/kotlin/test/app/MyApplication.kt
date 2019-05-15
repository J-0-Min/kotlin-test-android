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

    override fun onCreate() {
        super.onCreate()
        startKoin(applicationContext, myDiModule)
    }
}