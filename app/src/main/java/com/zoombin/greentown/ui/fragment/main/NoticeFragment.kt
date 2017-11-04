package com.zoombin.greentown.ui.fragment.main

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.zoombin.greentown.R
import com.zoombin.greentown.model.Message
import com.zoombin.greentown.ui.fragment.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_recyclerview.view.*
import kotlinx.android.synthetic.main.layout_cell_notice.view.*

/**
 * Created by gejw on 2017/9/23.
 */

class NoticeFragment : MainBaseFragment() {

    companion object {

        fun newInstance(): NoticeFragment {
            val args = Bundle()
            val fragment = NoticeFragment()
            fragment.arguments = args
            return fragment
        }

    }

    private var messages = arrayListOf<Message>()

    override fun layoutId(): Int {
        return R.layout.fragment_recyclerview
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        query()
    }

    override fun initView() {

        title = "公告"
        // 列表
        val layoutManager = GridLayoutManager(context, 1)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        contentView.recyclerView.layoutManager = layoutManager
        contentView.recyclerView.adapter = ListAdapter(messages)
    }

    private fun query() {
        Message.allMessages({ messages ->
            reloadData(messages)
        }) {
            reloadData()
        }
    }

    override fun exec() {
        super.exec()
        query()
    }

    private fun reloadData(users: List<Message> = emptyList()) {
        this.messages.clear()
        this.messages.addAll(users)

        // 更新列表
        contentView.recyclerView.adapter.notifyDataSetChanged()
        // 检测是否为空
        contentView.emptyView.visibility = if (users.isEmpty()) View.VISIBLE else View.INVISIBLE
    }

    private class ListAdapter(val messages: ArrayList<Message>) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
            return ViewHolder(View.inflate(parent.context, R.layout.layout_cell_notice, null))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(messages[position], position)
        }

        override fun getItemCount() = messages.size

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bind(message: Message,
                     position: Int) = with(itemView) {

                when(message.msg_type) {
                    1 -> { timeTextView.text = "公告" }
                    2 -> { timeTextView.text = if (message.msg_content.contains("鞭策")) "鞭策" else "鼓舞" }
                    3 -> { timeTextView.text = message.department_name }
                    4 -> { timeTextView.text = "我的留言" }
                }

                timeTextView.text = "${timeTextView.text} ${message.created_date}"
                messageTextView.text = message.msg_content
            }

        }
    }

}