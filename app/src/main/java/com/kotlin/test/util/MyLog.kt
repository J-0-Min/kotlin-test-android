package com.kotlin.test.util

import com.kotlin.test.BuildConfig
import com.kotlin.test.global.Constant

/**
 * KotlinTest
 * Class : MyLog
 * Created by jang on 2019-05-08
 *
 * Description : Log Class
 */
class MyLog {

    companion object {

        fun e(msg: String) {
            if (!BuildConfig.DEBUG) {
                return
            }

            val stackTraceElement = Throwable().stackTrace[1]
            val buffer = StringBuffer()

            var className = stackTraceElement.className

            val indexOfPoint = className.lastIndexOf(".")
            if (indexOfPoint != -1) {
                className = className.substring(indexOfPoint + 1)
            }
            val indexOfPoint2 = className.indexOf("$")
            if (indexOfPoint2 != -1) {
                className = className.substring(0, indexOfPoint2)
            }
            buffer.append(className)
            buffer.append("::")
            buffer.append(stackTraceElement.lineNumber)
            buffer.append("::")
            android.util.Log.e(Constant.TAG, buffer.toString() + msg)
        }
    }
}