package com.kotlin.test.di

import com.kotlin.test.app.MyApplication.Companion.IS_DEBUG
import com.kotlin.test.global.Constant
import com.kotlin.test.network.Api
import com.kotlin.test.ui.adapter.MainAdapter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/**
 * KotlinTest
 * Class : MyModule
 * Created by jang on 2019-05-03
 *
 * Description : DI Module
 */
var retrofitPart = module {
    single {
        createRetrofitBuilder()
    }
}

private fun createRetrofitBuilder(): Api {

    val retrofitBuilder = Retrofit.Builder()
        // 통신할 서버의 주소
        .baseUrl(Constant.BASE_URL)

        // 받은 응답을 observable 형태로 변환
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

        // 서버에서 json 형식으로 보내는 Data Parsing
        .addConverterFactory(GsonConverterFactory.create())

    if (IS_DEBUG) {
        // 네트워크 요청 로그를 표시
        retrofitBuilder.client(provideOkHttpClient(provideLoggingInterceptor()))
    }

    return retrofitBuilder.build().create(Api::class.java)
}

// 네트워크 통신에 사용할 Client 객체 생성
private fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
    val builder = OkHttpClient.Builder()

    // 이 Client 통해 오고 가는 네트워크 Req, Res Log
    builder.addInterceptor(interceptor)

    return builder.build()
}

private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    return interceptor
}

var adapterPart = module {
    factory {
        MainAdapter()
    }
}

var myDiModule = listOf(adapterPart, retrofitPart)