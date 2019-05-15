package com.kotlin.test.di

import com.kotlin.test.BuildConfig
import com.kotlin.test.global.Constant
import com.kotlin.test.network.Api
import com.kotlin.test.ui.adapter.MainAdapter
import com.kotlin.test.ui.vm.MainViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * KotlinTest
 * Class : MyModule
 * Created by jang on 2019-05-03
 *
 * Description : DI Module
 */
var retrofitPart = module {
    single {
        Retrofit.Builder().apply {
            // 통신할 서버의 주소
            baseUrl(Constant.BASE_URL)

            // 받은 응답을 observable 형태로 변환
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())

            // 서버에서 json 형식으로 보내는 Data Parsing
            addConverterFactory(GsonConverterFactory.create())

            client(
                OkHttpClient.Builder().apply {
                    // TIMEOUT
                    connectTimeout(Constant.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    writeTimeout(Constant.WRITE_TIMEOUT, TimeUnit.SECONDS)
                    readTimeout(Constant.READ_TIMEOUT, TimeUnit.SECONDS)

                    // 실패시 재시도
                    retryOnConnectionFailure(true)

                    // 네트워크 요청 로그를 표시
                    addInterceptor(HttpLoggingInterceptor().apply {
                        if (BuildConfig.DEBUG) {
                            level = HttpLoggingInterceptor.Level.BODY
                        }
                    })
                }.build()
            )
        }.build().create(Api::class.java)
    }
}

var viewModelPart = module {
    viewModel {
        MainViewModel(get())
    }
}

var adapterPart = module {
    factory {
        MainAdapter()
    }
}

var myDiModule = listOf(viewModelPart, adapterPart, retrofitPart)