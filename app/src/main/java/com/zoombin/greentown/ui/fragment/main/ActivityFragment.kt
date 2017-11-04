package com.zoombin.greentown.ui.fragment.main

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.TabHost
import android.widget.TextView
import com.bumptech.glide.Glide
import com.zoombin.greentown.R
import com.zoombin.greentown.model.Gift
import com.zoombin.greentown.model.Sport
import com.zoombin.greentown.model.Task
import com.zoombin.greentown.ui.fragment.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_main_activity.*
import kotlinx.android.synthetic.main.fragment_main_activity.view.*
import kotlinx.android.synthetic.main.layout_cell_activity.view.*
import kotlinx.android.synthetic.main.layout_titlebar.*
import org.jetbrains.anko.sp
import org.jetbrains.anko.support.v4.sp
import org.jetbrains.anko.support.v4.toast

/**
 * Created by gejw on 2017/9/23.
 */

class ActivityFragment : MainBaseFragment() {

    companion object {

        fun newInstance(): ActivityFragment {
            val args = Bundle()
            val fragment = ActivityFragment()
            fragment.arguments = args
            return fragment
        }

    }

    var gifts = arrayListOf<Gift>()
    var sports = arrayListOf<Sport>()
    var tasks = arrayListOf<Task>()

    var sportListAdapter: SportListAdapter? = null
    var taskListAdapter: TaskListAdapter? = null
    var giftListAdapter: GiftListAdapter? = null

    override fun layoutId(): Int {
        return R.layout.fragment_main_activity
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        query()
    }

    override fun initView() {
        title = "活动"

        val tabhost = contentView.tabhost
        tabhost.setup()

        tabhost.addTab(tabhost.newTabSpec("娱乐").setIndicator("娱乐").setContent(R.id.tabLayout))
        tabhost.addTab(tabhost.newTabSpec("任务").setIndicator("任务").setContent(R.id.tabLayout))
        tabhost.addTab(tabhost.newTabSpec("礼包").setIndicator("礼包").setContent(R.id.tabLayout))
        tabhost.setOnTabChangedListener {
            updateTab(tabhost)
        }
        Handler().postDelayed({
            updateTab(tabhost)
        }, 100)

        sportListAdapter = SportListAdapter(sports, { sport ->
            if (sport.joined == 1 || sport.cur_quantity >= sport.quantity) return@SportListAdapter
            // 提醒报名任务
            showDialog("是否参与活动？", "参与") {
                sport.enroll({
                    toast("报名成功")
                    query()
                }) { message ->
                    if (message != null) toast(message)
                }
            }
        }) { sport ->
            // 规则
            showDialog(sport.rule)
        }
        taskListAdapter = TaskListAdapter(tasks, { task ->
            if (task.started == 1) {
                if (task.picked == 0) {
                    // 提醒领取任务
                    showDialog("是否领取任务？", "领取") {
                        task.pickTask({
                            toast("领取成功")
                            query()
                        }) { message ->
                            if (message != null) toast(message)
                        }
                    }
                }
            } else {
                if (task.finished == 0) {
                    // 提醒完成任务
                    showDialog("任务是否完成？", "完成") {
                        task.finishTask({
                            query()
                        }) { message ->
                            if (message != null) toast(message)
                        }
                    }
                }
            }
        })
        giftListAdapter = GiftListAdapter(gifts, { gift ->
            if (!(gift.pickStart == 1 && gift.picked == 0)) return@GiftListAdapter
            // 提醒领取礼包
            showDialog("是否领取礼包？", "领取") {
                gift.pickGift({
                    toast("领取成功")
                    query()
                }) { message ->
                    if (message != null) toast(message)
                }
            }
        })

        val layoutManager = GridLayoutManager(context, 1)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        contentView.recyclerView.layoutManager = layoutManager
        contentView.recyclerView.adapter = sportListAdapter
    }

    private fun updateTab(mTabHost: TabHost) {
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
        reloadData()
    }

    override fun exec() {
        super.exec()
        query()
    }

    private fun reloadSports(sports: List<Sport> = emptyList()) {
        this.sports.clear()
        this.sports.addAll(sports)
        reloadData()
    }

    private fun reloadTasks(tasks: List<Task> = emptyList()) {
        this.tasks.clear()
        this.tasks.addAll(tasks)
        reloadData()
    }

    private fun reloadGifts(gifts: List<Gift> = emptyList()) {
        this.gifts.clear()
        this.gifts.addAll(gifts)
        reloadData()
    }

    private fun reloadData() {
        contentView.tabLayout.visibility = View.VISIBLE
        when (contentView.tabhost.currentTab) {
            0 -> {
                if (contentView.recyclerView.adapter !is SportListAdapter) {
                    contentView.recyclerView.adapter = sportListAdapter
                    contentView.emptyView.visibility = if (sports.isEmpty()) View.VISIBLE else View.INVISIBLE
                }
            }
            1 -> {
                if (contentView.recyclerView.adapter !is TaskListAdapter) {
                    contentView.recyclerView.adapter = taskListAdapter
                    contentView.emptyView.visibility = if (tasks.isEmpty()) View.VISIBLE else View.INVISIBLE
                }
            }
            2 -> {
                if (contentView.recyclerView.adapter !is GiftListAdapter) {
                    contentView.recyclerView.adapter = giftListAdapter
                    contentView.emptyView.visibility = if (gifts.isEmpty()) View.VISIBLE else View.INVISIBLE
                }
            }
        }
        contentView.recyclerView.adapter.notifyDataSetChanged()
    }

    private fun query() {
        Sport.sports({ sports ->
            reloadSports(sports)
        }, {
            reloadSports()
        })
        Task.tasks({ tasks ->
            reloadTasks(tasks)
        }, {
            reloadTasks()
        })
        Gift.gifts({ gifts ->
            reloadGifts(gifts)
        }, {
            reloadGifts()
        })
    }

    private fun showDialog(message: String, button: String = "确定", okListener: (() -> Unit)? = null) {
        AlertDialog.Builder(context).setTitle("提醒").setMessage(message).setPositiveButton(button, { dialog, whitch ->
            okListener?.invoke()
        }).setNegativeButton("取消", null).show()
    }

    class SportListAdapter(val sports: List<Sport>,
                           val pickListener: (Sport) -> Unit,
                           val ruleListener: (Sport) -> Unit) : RecyclerView.Adapter<SportListAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
            return ViewHolder(View.inflate(parent.context, R.layout.layout_cell_activity, null))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(sports[position], position, pickListener, ruleListener)
        }

        override fun getItemCount() = sports.size

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bind(sport: Sport,
                     position: Int,
                     pickListener: (Sport) -> Unit,
                     ruleListener: (Sport) -> Unit) = with(itemView) {
                titleTextView.text = sport.title
                nameTextView.text = sport.title
                descTextView.text = sport.sub_title
                Glide.with(context).load(sport.logo).into(avatarImageView)
                ruleLayout.visibility = View.VISIBLE
                ruleLayout.setOnClickListener { ruleListener.invoke(sport) }
                leftInfoTextView.text = "奖池: ${sport.total_reward}绿币"
                rightInfoTextView.text = "报名费: ${sport.fee}绿币"
                button.setOnClickListener { pickListener.invoke(sport) }

                if (sport.joined == 0) {
                    if (sport.cur_quantity >= sport.quantity) {
                        button.text = "人数已满"
                    } else {
                        button.text = "报名"
                    }
                } else {
                    button.text = "已报名"
                }
            }

        }
    }

    class TaskListAdapter(val tasks: List<Task>,
                          val pickListener: (Task) -> Unit) : RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
            return ViewHolder(View.inflate(parent.context, R.layout.layout_cell_activity, null))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(tasks[position], position, pickListener)
        }

        override fun getItemCount() = tasks.size

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bind(task: Task, position: Int,
                     pickListener: (Task) -> Unit) = with(itemView) {
                titleTextView.text = task.task_name
                nameTextView.text = task.task_name
                descTextView.text = ""
                ruleLayout.visibility = View.GONE
                leftInfoTextView.text = "奖励: ${task.coins}绿币"
                rightInfoTextView.text = ""
                button.setOnClickListener { pickListener.invoke(task) }

                if (task.started == 0) {
                    // 任务未开始
                    button.text = "暂未开始"
                } else {
                    // 任务开始
                    if (task.picked == 0) {
                        // 未领取
                        button.text = "领取"
                    } else {
                        if (task.finished == 0) {
                            // 未完成
                            button.text = "完成"
                        } else {
                            // 已完成
                            button.text = "已完成"
                        }
                    }
                }
            }

        }
    }

    class GiftListAdapter(val gifts: List<Gift>,
                          val pickListener: (Gift) -> Unit) : RecyclerView.Adapter<GiftListAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
            return ViewHolder(View.inflate(parent.context, R.layout.layout_cell_activity, null))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(gifts[position], position, pickListener)
        }

        override fun getItemCount() = gifts.size

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bind(gift: Gift, position: Int, pickListener: (Gift) -> Unit) = with(itemView) {
                titleTextView.text = gift.gift_name
                nameTextView.text = gift.gift_name
                descTextView.text = "绿币: ${gift.coins}"
                ruleLayout.visibility = View.GONE
                leftInfoTextView.text = "结束时间: ${gift.gift_end}"
                rightInfoTextView.text = ""
                button.setOnClickListener { pickListener.invoke(gift) }

                if (gift.pickStart == 0) {
                    button.text = "未开始"
                } else {
                    if (gift.picked == 0) {
                        button.text = "领取"
                    } else {
                        button.text = "已领取"
                    }
                }
            }

        }
    }

}