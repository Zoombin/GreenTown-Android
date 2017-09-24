package com.zoombin.greentown.ui.fragment.main

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.zoombin.greentown.R
import com.zoombin.greentown.model.User
import com.zoombin.greentown.ui.fragment.BaseFragment
import com.zoombin.greentown.ui.fragment.LevelMessageFragment
import com.zoombin.greentown.ui.fragment.MessageContentFragment
import kotlinx.android.synthetic.main.fragment_main_me.*
import kotlinx.android.synthetic.main.layout_guild_cell.view.*
import kotlinx.android.synthetic.main.layout_me_cell.view.*
import kotlinx.android.synthetic.main.layout_titlebar.*
import me.yokeyword.fragmentation.SupportFragment
import org.jetbrains.anko.textColor

/**
 * Created by gejw on 2017/9/23.
 */

class MeFragment : BaseFragment() {

    class CellItem(var title: String,
                   var value: String = "",
                   var valueColor: Int = Color.BLACK,
                   var isShowArrow: Boolean = false)

    var items = arrayListOf<CellItem>()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_main_me, null)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleLabel.visibility = View.VISIBLE
        titleLabel.text = "我的"


        val layoutManager = GridLayoutManager(context, 1)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = ListAdapter(items) {

        }
    }

    fun reloadItems() {
        var user = User.current()
        if (user == null) user = User()

        Glide.with(context).load(user.logo).into(avatarImageView)
        nameLabel.text = user.fullname

        items.clear()
        items.add(CellItem(
                "歌林科技部"))
        items.add(CellItem(
                "绿币",
                "${user.coins}",
                resources.getColor(R.color.themeColor)))
        items.add(CellItem(
                "成就点",
                "${user.points}"))
        items.add(CellItem(
                "星座",
                if (user.constellation == null) "请选择" else user.constellation!!,
                Color.BLACK,
                true))
        items.add(CellItem(
                "爱好",
                if (user.hobby == null) "请输入" else user.hobby!!,
                Color.BLACK,
                true))
        items.add(CellItem(
                "留言",
                "", Color.BLACK,
                true))
    }

    override fun onResume() {
        super.onResume()
        reloadItems()
    }

    override fun didLogin() {
        super.didLogin()
        reloadItems()
    }

    class ListAdapter(val items: ArrayList<CellItem>, val listener: (CellItem) -> Unit) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
            return ViewHolder(View.inflate(parent.context, R.layout.layout_me_cell, null))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(items[position], position, listener)
        }

        override fun getItemCount() = items.size

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bind(item: CellItem, position: Int, listener: (CellItem) -> Unit) = with(itemView) {
                setOnClickListener { listener(item) }

                titleLabel.text = item.title
                valueLabel.text = item.value
                valueLabel.textColor = item.valueColor
                arrowImageView.visibility = if (item.isShowArrow) View.VISIBLE else View.GONE
            }

        }
    }

}