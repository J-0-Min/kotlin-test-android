package com.kotlin.test.network.res

/**
 * KotlinTest
 * Class : CounselingUsableRes
 * Created by jang on 2019-05-03
 *
 * Description : (주)NSMG 카카오톡 상담 API Response Data Class
 */
data class CounselingUsableRes(
    var result: String,
    var code: Int,
    var message: String,
    var data: Data
) {
    data class Data(
        var openchat: OpenChat
    )

    data class OpenChat(
        var activated: Int,
        var url: String,
        var notice: String
    )
}