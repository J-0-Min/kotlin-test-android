package com.kotlin.test.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import java.lang.Exception

/**
 * KotlinTest
 * Class : BaseActivity
 * Created by jang on 2019-05-03
 *
 * Description : BaseActivity
 */
abstract class BaseActivity<VDB : ViewDataBinding, BVM : BaseViewModel> : AppCompatActivity() {

    abstract val mLayoutResId: Int

    abstract val mViewModel: BVM

    val mBinding: VDB by lazy { DataBindingUtil.setContentView(this, mLayoutResId) as VDB }

    /**
     * xml 에 정의한 <data> ViewModel 과
     * 해당 Activity 에서의 ViewModel 연결.
     */
    abstract fun setVM()

    /**
     * setContentView() 이후에 호출.
     * Activity, View 속성 등을 초기화.
     * ex) RecyclerView, Toolbar, DrawerView
     */
    abstract fun initStartView()

    /**
     * DataBinding 및 rxJava 설정.
     * ex) rxJava observe, dataBinding observe
     */
    abstract fun initDataBinding()

    /**
     * 바인딩 이후에 할 일을 이곳에서 구현.
     * 그 외에 설정할 것이 있다면 이곳에서 설정.
     */
    abstract fun initAfterBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setVM()
        initStartView()
        initDataBinding()
        initAfterBinding()
    }

    /**
     * Activity 전환
     * 기본                 = move2Activity(전환할 Activity Class)
     * 이전 화면 종료하면서 전환 = move2Activity(전환할 Activity Class, true)
     * putExtra 전환        = move2Activity(전환할 Activity Class, false/true, "KEY", "VALUE")
     *  -> String 형태로만 가능하고 Key, VALUE 형태의 한 쌍으로 권장
     */
    fun move2Activity(clazz: Class<*>, isFinish: Boolean = false, vararg args: String) {
        val intent = Intent(this, clazz).apply {
            try {
                for (i in args.indices step 2) {
                    putExtra(args[i], args[i + 1])
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        startActivity(intent)

        if (isFinish) {
            finish()
        }
    }

    /**
     * Activity 전환
     *  -> Activity 로부터 결과 가져오기
     *  기본 = move2Activity(전환할 Activity Class, Request Code)
     *  그 외 동일
     */
    fun move2ActivityForResult(clazz: Class<*>, reqCode: Int, vararg args: String) {
        val intent = Intent(this, clazz).apply {
            try {
                for (i in args.indices step 2) {
                    putExtra(args[i], args[i + 1])
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        startActivityForResult(intent, reqCode)
    }
}