package com.kotlin.test.ui.activity

import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.test.R
import com.kotlin.test.databinding.ActivityMainBinding
import com.kotlin.test.ui.BaseActivity
import com.kotlin.test.ui.adapter.MainAdapter
import com.kotlin.test.ui.vm.MainViewModel
import com.kotlin.test.util.MyLog
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.singleTop
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject

/**
 * KotlinTest
 * Class : MainActivity
 * Created by jang on 2019-05-03
 *
 * Description : Main 화면
 */
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val layoutResId: Int
        get() = R.layout.activity_main

    // MainViewModel Constructor (Api Class) - MyModule 에서 RetrofitPart 셋팅
    override val viewModel: MainViewModel = MainViewModel(get())

    // RecyclerView 해당하는 Adapter - MyModule 에서 AdapterPart 셋팅
    private val mainAdapter: MainAdapter by inject()

    // xml 에서 정의해준 ViewModel (vm) 에 해당 viewModel 연결
    override fun setVM() {
        binding.vm = viewModel
    }

    override fun initStartView() {
        Log.e("MainActivity", "initStartView()")

        // RecyclerView 셋팅
        recycler_view.run {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
        }

    }

    override fun initDataBinding() {
        MyLog.e("initDataBinding()")

        // Sub 화면으로 전환 버튼 Click
        viewModel.clickEvent.observe(this, Observer { resId ->
            when (resId) {
                btn_move_sub.id -> {
                    // Sub 화면으로 전환
                    startActivity(intentFor<SubActivity>(
                        "KEY1" to "VALUE1"
                    ).singleTop())
                }
            }
        })

        // 카카오톡 상담 가능 Response LiveData Observe Binding
        viewModel.counselingUsableResLiveData.observe(this, Observer {
            MyLog.e(it.data.toString())

            mainAdapter.addItem(MainAdapter.DataItem("1"))
            mainAdapter.addItem(MainAdapter.DataItem("2"))
            mainAdapter.addItem(MainAdapter.DataItem("3"))
            mainAdapter.addItem(MainAdapter.DataItem("4"))
            mainAdapter.addItem(MainAdapter.DataItem("5"))

            mainAdapter.notifyDataSetChanged()
        })

    }

    override fun initAfterBinding() {

        // 카카오톡 상담 가능 통신
        viewModel.getUsableCounseling()

    }
}