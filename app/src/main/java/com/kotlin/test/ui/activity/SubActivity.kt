package com.kotlin.test.ui.activity

import androidx.lifecycle.Observer
import com.kotlin.test.R
import com.kotlin.test.databinding.ActivitySubBinding
import com.kotlin.test.ui.BaseActivity
import com.kotlin.test.ui.vm.SubViewModel
import com.kotlin.test.util.MyLog
import kotlinx.android.synthetic.main.activity_sub.*

/**
 * KotlinTest
 * Class : SubActivity
 * Created by jang on 2019-05-08
 *
 * Description :
 */
class SubActivity : BaseActivity<ActivitySubBinding, SubViewModel>() {

    override val layoutResId: Int
        get() = R.layout.activity_sub

    override val viewModel: SubViewModel = SubViewModel()

    private lateinit var sendText: String

    // xml 에서 정의해준 ViewModel (vm) 에 해당 viewModel 연결
    override fun setVM() {
        binding.vm = viewModel
    }

    override fun initStartView() {
        if (intent.hasExtra("KEY1")) {
            MyLog.e(intent.getStringExtra("KEY1"))
            sendText = intent.getStringExtra("KEY1")
        }
        if (intent.hasExtra("KEY2")) {
            MyLog.e(intent.getStringExtra("KEY2"))
        }
    }

    override fun initDataBinding() {
        viewModel.clickEvent.observe(this, Observer { resId ->
            when(resId) {
                btn_val_change.id -> {
                    // 값 바꾸기 버튼 클릭
                    tv_txt.text = "!! 버튼 클릭했다 !!"
                }

                btn_finish.id -> {
                    // 종료 버튼 클릭
                    finish()
                }
            }
        })
    }

    override fun initAfterBinding() {
        tv_txt.text = sendText
    }
}