package com.kotlin.test.ui.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kotlin.test.global.Constant
import com.kotlin.test.network.Api
import com.kotlin.test.network.res.CounselingUsableRes
import com.kotlin.test.ui.BaseViewModel
import com.kotlin.test.util.MyLog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * KotlinTest
 * Class : MainViewModel
 * Created by jang on 2019-05-07
 *
 * Description : MainActivity 에 해당하는 ViewModel
 */
class MainViewModel(private val api: Api) : BaseViewModel() {

    private val _counselingUsableResLiveData = MutableLiveData<CounselingUsableRes>()
    val counselingUsableResLiveData: LiveData<CounselingUsableRes>
        get() = _counselingUsableResLiveData

    // 카카오톡 상담 가능 통신
    fun getUsableCounseling() {
        addDisposable(api.getUsableState(Constant.CLIENT_TOKEN)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.run {
                    if (code == 0) {
                        // success
                        MyLog.e(it.toString())

                        // Response Data 를 LiveData 에 Post
                        _counselingUsableResLiveData.postValue(this)
                    } else {
                        // fail
                        MyLog.e(message)
                    }
                }
            }, {
                // error
                MyLog.e("${it.message}")
            })
        )
    }

    /**
     * SubActivity 로 전환
     *  -> _startSubActivityEvent 에 startActivity() Binding 해두고
     *     startSubActivity() 이용하여 call
     *
     * ps) BaseActivity 로 move2Activity() 뽑아내는 것이 맞을지
     * 이동할 Activity 마다 매번 만들어줘야하는지 이 부분은 고민이 필요
     */
//    private val _startSubActivityEvent = SingleLiveEvent<Any>()
//    val startSubActivityEvent: LiveData<Any>
//        get() = _startSubActivityEvent
//
//
//    fun startSubActivity() {
//        _startSubActivityEvent.call()
//    }
}