package com.kotlin.test.network

import com.kotlin.test.network.res.CounselingUsableRes
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header

/**
 * KotlinTest
 * Class : Api
 * Created by jang on 2019-05-03
 *
 * Description : (주)NSMG 통신부 API
 */
interface Api {

    @GET("/api/v1/openchat")
    fun getUsableState(
        @Header("x-tdi-client-secret") token: String
    ): Single<CounselingUsableRes>
}