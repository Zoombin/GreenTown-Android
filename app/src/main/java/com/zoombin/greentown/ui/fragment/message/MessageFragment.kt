package com.zoombin.greentown.ui.fragment.message

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.zoombin.greentown.R
import com.robinge.quickkit.fragment.QBaseBackFragment
import kotlinx.android.synthetic.main.fragment_message.view.*
import android.widget.TextView
import android.widget.TabHost
import com.zoombin.greentown.model.Message
import kotlinx.android.synthetic.main.fragment_message.*
import kotlinx.android.synthetic.main.layout_cell_message.view.*
import org.jetbrains.anko.support.v4.toast


/**
 * Created by gejw on 2017/10/3.
 */

class MessageFragment : QBaseBackFragment() {

    private var messages = arrayListOf<Message>()

    override fun layoutId(): Int {
        return R.layout.fragment_message
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        query()
    }

    override fun initView() {
        title = "留言"

        // tab
        val tabhost = contentView.tabhost
        tabhost.setup()

        tabhost.addTab(tabhost.newTabSpec("我要留言").setIndicator("我要留言").setContent(R.id.tab1))
        tabhost.addTab(tabhost.newTabSpec("留言历史").setIndicator("留言历史").setContent(R.id.tab2))
        tabhost.setOnTabChangedListener {
            upDateTab(tabhost)
        }

        Handler().postDelayed({
            upDateTab(contentView.tabhost)
        }, 100)

        // 列表
        val layoutManager = GridLayoutManager(context, 1)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        contentView.recyclerView.layoutManager = layoutManager
        contentView.recyclerView.adapter = ListAdapter(messages) { message ->
            // 点击
            push(MessageContentFragment.newInstance(message))
        }

        // 发送事件
        sendButton.setOnClickListener {
            val message = messageEditText.text.toString().trim()
            if (message == "") {
                toast("请输入内容")
                return@setOnClickListener
            }

            Message.levelMessage(message, {
                AlertDialog.Builder(activity).setTitle("发送成功").setPositiveButton("确定", { dialog, whitch ->
                    pop()
                }).show()
            }) { message ->
                if (message != null) toast(message)
            }
        }
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
    }

    private fun query() {
        Message.allSendMessages({ messages ->
            reloadData(messages)
        }) {
            reloadData()
        }
    }

    private fun reloadData(users: List<Message> = emptyList()) {
        this.messages.clear()
        this.messages.addAll(users)

        // 更新列表
        contentView.recyclerView.adapter.notifyDataSetChanged()
        // 检测是否为空
        contentView.emptyView.visibility = if (users.isEmpty()) View.VISIBLE else View.INVISIBLE
    }

    private class ListAdapter(val messages: ArrayList<Message>,
                              val clickHandler: (Message) -> Unit) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
            return ViewHolder(View.inflate(parent.context, R.layout.layout_cell_message, null))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(messages[position], clickHandler)
        }

        override fun getItemCount() = messages.size

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bind(message: Message,
                     clickHandler: (Message) -> Unit) = with(itemView) {

                layout.setOnClickListener{
                    if (message.haveResponse)
                        clickHandler(message)
                }
                titleTextView.text = "我的留言 ${if (message.haveResponse) "【有回复】" else ""}"
                messageTextView.text = message.msg_content
            }

        }
    }

}