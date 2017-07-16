package com.zoombin.greentown.ui.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.zoombin.greentown.R
import com.zoombin.greentown.model.Gift
import kotlinx.android.synthetic.main.fragment_gift.*
import kotlinx.android.synthetic.main.layout_gift_cell.view.*
import kotlinx.android.synthetic.main.layout_titlebar.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultVerticalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.support.v4.toast

/**
 * Created by gejw on 2017/6/9.
 */

class GiftFragment : BaseBackFragment() {

    var items = ArrayList<Gift>()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_gift, null)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleLabel.text = "我的礼包"

        val layoutManager = GridLayoutManager(context, 1)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = ListAdapter(items, {
            if (it.picked == 1 || it.pickStart == 0) { return@ListAdapter }
            // 领取
            it.pickGift({
                toast("领取成功")
                loadData()
            }) { message ->
                if (message != null) toast(message)
            }
        })
        loadData()
    }

    fun loadData() {
        Gift.gifts({ gifts ->
            items.clear()
            items.addAll(gifts)
            recyclerView.adapter.notifyDataSetChanged()
            emptyView.visibility = if (items.size == 0) View.VISIBLE else View.INVISIBLE
        }) { message ->
            emptyView.visibility = if (items.size == 0) View.VISIBLE else View.INVISIBLE
            if (message != null) toast(message)
        }
    }

    class ListAdapter(val tasks: ArrayList<Gift>,
                      val pickListener: (Gift) -> Unit) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
            return ViewHolder(View.inflate(parent.context, R.layout.layout_gift_cell, null))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(tasks[position], position, pickListener)
        }

        override fun getItemCount() = tasks.size

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bind(item: Gift, position: Int, pickListener: (Gift) -> Unit) = with(itemView) {
                nameTextView.text = item.gift_name
                timeTextView.text = item.gift_end
                pickButton.text = if (item.pickStart == 0) "未开始" else if (item.picked == 1) "已领取" else "领取"
                pickButton.setOnClickListener { pickListener(item) }
            }

        }
    }

}