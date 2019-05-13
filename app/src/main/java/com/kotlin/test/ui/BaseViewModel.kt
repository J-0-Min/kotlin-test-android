package com.kotlin.test.ui

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kotlin.test.util.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * KotlinTest
 * Class : BaseViewModel
 * Created by jang on 2019-05-07
 *
 * Description : BaseViewModel
 */
open class BaseViewModel : ViewModel() {

    /**
     * rxJava 의 observing 을 위한 부분
     * addDisposable 이용하여 추가
     */
    private val compositeDisposable = CompositeDisposable()

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    /**
     * 해당 ViewModel 에서 공통으로 사용될 ClickEventListener
     * when 을 통해 view resource id 분기
     */
    private val _clickEvent = SingleLiveEvent<Int>()
    val clickEvent: LiveData<Int>
        get() = _clickEvent

    fun onClickEvent(view: View) {
        _clickEvent.call(view.id)
    }
}