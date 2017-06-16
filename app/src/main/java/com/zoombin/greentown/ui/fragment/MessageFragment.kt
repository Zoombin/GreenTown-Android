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
import com.zoombin.greentown.model.Message
import kotlinx.android.synthetic.main.fragment_message.*
import kotlinx.android.synthetic.main.layout_message_cell.view.*
import kotlinx.android.synthetic.main.layout_titlebar.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultVerticalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.support.v4.toast

/**
 * Created by gejw on 2017/6/9.
 */

class MessageFragment : BaseBackFragment() {

    var items = ArrayList<Message>()
    var selectedIndex = 0

    private var noteMessages = ArrayList<Message>()
    private var eventMessages = ArrayList<Message>()
    private var departmentMessages = ArrayList<Message>()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_message, null)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigationLeftButton.imageResource = R.drawable.navigation_close
        titleLabel.text = "消息"

        val layoutManager = GridLayoutManager(context, 1)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = ListAdapter(items) {

        }

        noteButton.setOnClickListener { selectSegment(0) }
        eventButton.setOnClickListener { selectSegment(1) }
        departmentButton.setOnClickListener { selectSegment(2) }
        selectSegment(0, true)

        Message.announcementMessages({ messages ->
            noteMessages.clear()
            noteMessages.addAll(messages)
            reloadData()
        }) { message ->
            if (message != null) toast(message)
        }

        Message.eventMessages({ messages ->
            eventMessages.clear()
            eventMessages.addAll(messages)
            reloadData()
        }) { message ->
            if (message != null) toast(message)
        }

        Message.departmentMessages({ messages ->
            departmentMessages.clear()
            departmentMessages.addAll(messages)
            reloadData()
        }) { message ->
            if (message != null) toast(message)
        }
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultVerticalAnimator()
    }

    fun selectSegment(index: Int, isInit: Boolean = false) {
        selectedIndex = index
        when(index) {
            0 -> {
                noteTriangle.visibility = View.VISIBLE
                eventTriangle.visibility = View.INVISIBLE
                departmentTriangle.visibility = View.INVISIBLE
            }
            1 -> {
                noteTriangle.visibility = View.INVISIBLE
                eventTriangle.visibility = View.VISIBLE
                departmentTriangle.visibility = View.INVISIBLE
            }
            2 -> {
                noteTriangle.visibility = View.INVISIBLE
                eventTriangle.visibility = View.INVISIBLE
                departmentTriangle.visibility = View.VISIBLE
            }
        }
        (recyclerView.adapter as ListAdapter).selectedIndex = index
        if (!isInit)
            reloadData()
    }

    fun reloadData() {
        items.clear()
        when(selectedIndex) {
            0 -> { items.addAll(noteMessages) }
            1 -> { items.addAll(eventMessages) }
            2 -> { items.addAll(departmentMessages) }
        }
        recyclerView.adapter.notifyDataSetChanged()
        emptyView.visibility = if (items.size == 0) View.VISIBLE else View.INVISIBLE
    }

    class ListAdapter(val users: ArrayList<Message>, val listener: (Message) -> Unit) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

        var selectedIndex = 0

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
            return ViewHolder(View.inflate(parent.context, R.layout.layout_message_cell, null))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(users[position], position, selectedIndex, listener)
        }

        override fun getItemCount() = users.size

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bind(item: Message, position: Int, selectedIndex: Int, listener: (Message) -> Unit) = with(itemView) {
                when(position % 4) {
                    0 -> { layout.backgroundResource = R.drawable.cell_blue_xml }
                    1 -> { layout.backgroundResource = R.drawable.cell_green_xml }
                    2 -> { layout.backgroundResource = R.drawable.cell_purple_xml }
                    3 -> { layout.backgroundResource = R.drawable.cell_red_xml }
                }
                when(selectedIndex) {
                    0 -> { nameTextView.text = "镇长" }
                    1 -> { nameTextView.text = if (item.msg_content.contains("鞭策")) "鞭策" else "鼓舞" }
                    2 -> { nameTextView.text = item.department_name }
                }
                contentTextView.text = item.msg_content
                setOnClickListener { listener(item) }
            }

        }
    }

}