package com.zoombin.greentown.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.zoombin.greentown.R
import com.zoombin.greentown.model.Sport
import com.zoombin.greentown.model.User
import com.zoombin.greentown.ui.fragment.common.BaseBackFragment
import kotlinx.android.synthetic.main.fragment_sportinfo.*
import kotlinx.android.synthetic.main.layout_sport_reward_cell.view.*
import kotlinx.android.synthetic.main.layout_titlebar.*
import org.jetbrains.anko.support.v4.toast

/**
 * Created by gejw on 2017/6/9.
 */

class SportInfoFragment(sport: Sport, pool: String) : BaseBackFragment() {

    var sport = sport
    var pool = pool
    var items = ArrayList<User>()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_sportinfo, null)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleLabel.text = sport.title
        poolTextView.text = pool

        val layoutManager = GridLayoutManager(context, 1)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = ListAdapter(items)

        ruleButton.setOnClickListener {
            val dialog = AlertDialog.Builder(context)
            dialog.setTitle("参赛规则")
            dialog.setMessage(sport.rule)
            dialog.setPositiveButton("确定", null)
            dialog.show() }

        playerButton.setOnClickListener { start(SportPlayerFragment(sport)) }

        Glide.with(context).load(sport.logo).into(avatarImageView)

        sport.rewardList({ users ->
            items.clear()
            items.addAll(users)
            recyclerView.adapter.notifyDataSetChanged()
            emptyView.visibility = if (items.size == 0) View.VISIBLE else View.INVISIBLE
        }) { message ->
            if (message != null) toast(message)
            emptyView.visibility = if (items.size == 0) View.VISIBLE else View.INVISIBLE
        }
    }

    class ListAdapter(val users: ArrayList<User>) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
            return ViewHolder(View.inflate(parent.context, R.layout.layout_sport_reward_cell, null))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(users[position], position)
        }

        override fun getItemCount() = users.size

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bind(item: User, position: Int) = with(itemView) {
                nameTextView.text = item.fullname
                rewardTextView.text = "${item.reward}绿币"
            }

        }
    }

    override fun layoutId(): Int {
        return 0
    }

}