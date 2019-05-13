package com.kotlin.test.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.test.databinding.ListItemSimpleBinding
import kotlinx.android.synthetic.main.list_item_simple.view.*

/**
 * KotlinTest
 * Class : MainAdapter
 * Created by jang on 2019-05-03
 *
 * Description : Main 화면에서 사용되는 RecyclerView's Adapter
 */
class MainAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // String Data Class
    data class DataItem(var txt: String)

    // String Data View Holder
    class DataHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            ListItemSimpleBinding.inflate(LayoutInflater.from(parent.context), parent, false).root
    ) {
        fun onBind(item: DataItem) {
            itemView.run {
                tv_txt.text = item.txt
            }
        }
    }

    // String Data List
    private val itemList = ArrayList<DataItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = DataHolder(parent)

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? DataHolder)?.onBind(itemList[position])
    }

    fun addItem(data: DataItem) {
        itemList.add(data)
    }
}