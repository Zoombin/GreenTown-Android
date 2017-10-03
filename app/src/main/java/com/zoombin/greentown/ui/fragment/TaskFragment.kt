package com.zoombin.greentown.ui.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zoombin.greentown.R
import com.zoombin.greentown.model.Task
import com.zoombin.greentown.ui.fragment.common.BaseBackFragment
import kotlinx.android.synthetic.main.fragment_task.*
import kotlinx.android.synthetic.main.layout_task_cell.view.*
import kotlinx.android.synthetic.main.layout_titlebar.*
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.support.v4.toast

/**
 * Created by gejw on 2017/6/9.
 */

class TaskFragment : BaseBackFragment() {

    var items = ArrayList<Task>()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_task, null)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleLabel.text = "我的任务"

        val layoutManager = GridLayoutManager(context, 1)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = ListAdapter(items, {
            // 领取
            it.pickTask({
                loadData()
            }) { message ->
                if (message != null) toast(message)
            }
        })
        loadData()
    }

    fun loadData() {
        Task.tasks({ tasks ->
            items.clear()
            items.addAll(tasks)
            recyclerView.adapter.notifyDataSetChanged()
            emptyView.visibility = if (items.size == 0) View.VISIBLE else View.INVISIBLE
        }) { message ->
            if (message != null) toast(message)
            emptyView.visibility = if (items.size == 0) View.VISIBLE else View.INVISIBLE
        }
    }

    class ListAdapter(val tasks: ArrayList<Task>,
                      val pickListener: (Task) -> Unit) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
            return ViewHolder(View.inflate(parent.context, R.layout.layout_task_cell, null))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(tasks[position], position, pickListener)
        }

        override fun getItemCount() = tasks.size

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bind(item: Task, position: Int, pickListener: (Task) -> Unit) = with(itemView) {
                when(position % 4) {
                    0 -> { layout.backgroundResource = R.drawable.cell_blue_xml }
                    1 -> { layout.backgroundResource = R.drawable.cell_green_xml }
                    2 -> { layout.backgroundResource = R.drawable.cell_purple_xml }
                    3 -> { layout.backgroundResource = R.drawable.cell_red_xml }
                }

                nameTextView.text = item.task_name
                rewardValueTextView.text = "成就点${item.points}，绿币${item.coins}，金币${item.golds}"
                pickButton.text = if (item.started == 2) "已截止" else if (item.started == 0) "未开始" else if (item.picked == 1) "已领取" else "领取"
                finishButton.text = if (item.finished == 1) "已完成" else "未完成"
                finishButton.isEnabled = false
                pickButton.isEnabled = item.picked == 0 && item.started == 1

                pickButton.setOnClickListener {
                    if (item.picked == 1) { return@setOnClickListener }
                    pickListener(item)
                }
            }

        }
    }

    override fun layoutId(): Int {
        return 0
    }

}