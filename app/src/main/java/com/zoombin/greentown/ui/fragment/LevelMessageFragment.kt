package com.zoombin.greentown.ui.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.zoombin.greentown.R
import com.zoombin.greentown.model.Message
import kotlinx.android.synthetic.main.fragment_levelmessage.*
import kotlinx.android.synthetic.main.layout_message_cell.view.*
import kotlinx.android.synthetic.main.layout_titlebar.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultVerticalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.support.v4.toast

/**
 * Created by gejw on 2017/6/9.
 */

class LevelMessageFragment : BaseBackFragment() {

    var items = ArrayList<Message>()
    var selectedIndex = 0

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_levelmessage, null)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleLabel.text = "我要留言"

        val layoutManager = GridLayoutManager(context, 1)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = ListAdapter(items) {
            start(MessageContentFragment(it))
        }

        levelButton.setOnClickListener { selectSegment(0) }
        listButton.setOnClickListener { selectSegment(1) }
        selectSegment(0)

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
        loadData()
    }

    fun loadData() {
        Message.allMessages({ messages ->
            items.clear()
            items.addAll(messages)
            recyclerView.adapter.notifyDataSetChanged()
            reloadData()
        }) { message ->
            if (message != null) toast(message)
        }
    }

    fun reloadData() {
        recyclerView.adapter.notifyDataSetChanged()
        emptyView.visibility = if (items.size == 0) View.VISIBLE else View.INVISIBLE
    }

    fun selectSegment(index: Int) {
        selectedIndex = index
        when(index) {
            0 -> {
                levelLayout.visibility = View.VISIBLE
                listLayout.visibility = View.INVISIBLE
                coinsTriangle.visibility = View.VISIBLE
                pointTriangle.visibility = View.INVISIBLE
            }
            1 -> {
                levelLayout.visibility = View.INVISIBLE
                listLayout.visibility = View.VISIBLE
                coinsTriangle.visibility = View.INVISIBLE
                pointTriangle.visibility = View.VISIBLE
            }
        }
    }



    class ListAdapter(val users: ArrayList<Message>, val listener: (Message) -> Unit) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
            return ViewHolder(View.inflate(parent.context, R.layout.layout_message_cell, null))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(users[position], position, listener)
        }

        override fun getItemCount() = users.size

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bind(item: Message, position: Int, listener: (Message) -> Unit) = with(itemView) {
                when(position % 4) {
                    0 -> { layout.backgroundResource = R.drawable.cell_blue_xml }
                    1 -> { layout.backgroundResource = R.drawable.cell_green_xml }
                    2 -> { layout.backgroundResource = R.drawable.cell_purple_xml }
                    3 -> { layout.backgroundResource = R.drawable.cell_red_xml }
                }
                nameTextView.text = "我的留言${if (item.haveResponse) "【有回复】" else ""}"
                contentTextView.text = item.msg_content
                setOnClickListener { listener(item) }
            }

        }
    }

}