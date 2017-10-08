package com.zoombin.greentown.ui.fragment.main

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.TabHost
import android.widget.TextView
import com.zoombin.greentown.R
import com.zoombin.greentown.model.Gift
import com.zoombin.greentown.model.Sport
import com.zoombin.greentown.model.Task
import com.zoombin.greentown.ui.fragment.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_main_activity.*
import kotlinx.android.synthetic.main.fragment_main_activity.view.*
import kotlinx.android.synthetic.main.layout_titlebar.*

/**
 * Created by gejw on 2017/9/23.
 */

class ActivityFragment : BaseFragment() {

    companion object {

        fun newInstance(): ActivityFragment {
            val args = Bundle()
            val fragment = ActivityFragment()
            fragment.arguments = args
            return fragment
        }

    }

    var gifts = listOf<Gift>()
    var sports = listOf<Sport>()
    var tasks = listOf<Task>()

    override fun layoutId(): Int {
        return R.layout.fragment_main_activity
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadData()
    }

    override fun initView() {
        title = "活动"

        val tabhost = contentView.tabhost
        tabhost.setup()

        tabhost.addTab(tabhost.newTabSpec("娱乐").setIndicator("娱乐").setContent(R.id.tabLayout))
        tabhost.addTab(tabhost.newTabSpec("任务").setIndicator("任务").setContent(R.id.tabLayout))
        tabhost.addTab(tabhost.newTabSpec("礼包").setIndicator("礼包").setContent(R.id.tabLayout))
        tabhost.setOnTabChangedListener {
            upDateTab(tabhost)
        }
        Handler().postDelayed({
            upDateTab(tabhost)
        }, 100)
    }


    private fun upDateTab(mTabHost: TabHost) {
        for (i in 0 until mTabHost.tabWidget.childCount) {
            mTabHost.tabWidget.getChildAt(i).layoutParams.height = 120

            val tv = mTabHost.tabWidget.getChildAt(i).findViewById(android.R.id.title) as TextView
            if (mTabHost.currentTab == i) {//选中
                tv.setTextColor(this.resources.getColor(R.color.tab_select))
                mTabHost.tabWidget.getChildAt(i).setBackgroundResource(R.drawable.tab_background)
            } else {//不选中
                tv.setTextColor(this.resources.getColor(R.color.tab_unselect))
                mTabHost.tabWidget.getChildAt(i).setBackgroundColor(Color.WHITE)
            }
        }

        reload()
    }

    private fun reload() {

        when (contentView.tabhost.currentTab) {
            0 -> { contentView.recyclerView.adapter = SportListAdapter(this.sports, {}) }
            1 -> { contentView.recyclerView.adapter = TaskListAdapter(this.tasks, {}) }
            2 -> { contentView.recyclerView.adapter = GiftListAdapter(this.gifts, {}) }
        }
    }

    private fun loadData() {
        Sport.sports({ sports ->
            this.sports = sports
            reload()
        }, {

        })
        Task.tasks({ tasks ->
            this.tasks = tasks
            reload()
        }, {

        })
        Gift.gifts({ gifts ->
            this.gifts = gifts
            reload()
        }, {

        })
    }

    class SportListAdapter(val tasks: List<Sport>,
                           val pickListener: (Sport) -> Unit) : RecyclerView.Adapter<SportListAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
            return ViewHolder(View.inflate(parent.context, R.layout.layout_gift_cell, null))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(tasks[position], position, pickListener)
        }

        override fun getItemCount() = tasks.size

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bind(item: Sport, position: Int, pickListener: (Sport) -> Unit) = with(itemView) {
//                nameTextView.text = item.gift_name
//                timeTextView.text = item.gift_end
//                pickButton.text = if (item.pickStart == 0) "未开始" else if (item.picked == 1) "已领取" else "领取"
//                pickButton.setOnClickListener { pickListener(item) }
            }

        }
    }

    class GiftListAdapter(val tasks: List<Gift>,
                          val pickListener: (Gift) -> Unit) : RecyclerView.Adapter<GiftListAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
            return ViewHolder(View.inflate(parent.context, R.layout.layout_gift_cell, null))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(tasks[position], position, pickListener)
        }

        override fun getItemCount() = tasks.size

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bind(item: Gift, position: Int, pickListener: (Gift) -> Unit) = with(itemView) {
//                nameTextView.text = item.gift_name
//                timeTextView.text = item.gift_end
//                pickButton.text = if (item.pickStart == 0) "未开始" else if (item.picked == 1) "已领取" else "领取"
//                pickButton.setOnClickListener { pickListener(item) }
            }

        }
    }

    class TaskListAdapter(val tasks: List<Task>,
                          val pickListener: (Task) -> Unit) : RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
            return ViewHolder(View.inflate(parent.context, R.layout.layout_gift_cell, null))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(tasks[position], position, pickListener)
        }

        override fun getItemCount() = tasks.size

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bind(item: Task, position: Int, pickListener: (Task) -> Unit) = with(itemView) {
//                nameTextView.text = item.gift_name
//                timeTextView.text = item.gift_end
//                pickButton.text = if (item.pickStart == 0) "未开始" else if (item.picked == 1) "已领取" else "领取"
//                pickButton.setOnClickListener { pickListener(item) }
            }

        }
    }

}