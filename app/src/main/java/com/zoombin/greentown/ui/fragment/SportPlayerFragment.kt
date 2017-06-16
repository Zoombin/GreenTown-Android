package com.zoombin.greentown.ui.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zoombin.greentown.R
import com.zoombin.greentown.model.Sport
import com.zoombin.greentown.model.User
import kotlinx.android.synthetic.main.fragment_guild.*
import kotlinx.android.synthetic.main.layout_guild_cell.view.*
import kotlinx.android.synthetic.main.layout_titlebar.*
import me.yokeyword.fragmentation.SupportFragment
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.support.v4.toast

/**
 * Created by gejw on 2017/6/9.
 */

class SportPlayerFragment(sport: Sport) : BaseBackFragment() {

    var sport = sport
    var items = ArrayList<User>()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_sportplayer, null)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleLabel.text = "选手名单"

        val layoutManager = GridLayoutManager(context, 1)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = ListAdapter(items, {
            start(SpurFragment(it))
        }) {
            start(InspireFragment(it))
        }
        sport.players({ users ->
            items.clear()
            items.addAll(users)
            recyclerView.adapter.notifyDataSetChanged()
            emptyView.visibility = if (items.size == 0) View.VISIBLE else View.INVISIBLE
        }) { message ->
            if (message != null) toast(message)
            emptyView.visibility = if (items.size == 0) View.VISIBLE else View.INVISIBLE
        }

    }

    class ListAdapter(val users: ArrayList<User>,
                      val spurLlistener: (User) -> Unit,
                      val inspireLlistener: (User) -> Unit) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
            return ViewHolder(View.inflate(parent.context, R.layout.layout_guild_cell, null))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(users[position], position, spurLlistener, inspireLlistener)
        }

        override fun getItemCount() = users.size

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bind(item: User, position: Int, spurLlistener: (User) -> Unit, inspireLlistener: (User) -> Unit) = with(itemView) {
                when(position % 4) {
                    0 -> { layout.backgroundResource = R.drawable.cell_blue_xml }
                    1 -> { layout.backgroundResource = R.drawable.cell_green_xml }
                    2 -> { layout.backgroundResource = R.drawable.cell_purple_xml }
                    3 -> { layout.backgroundResource = R.drawable.cell_red_xml }
                }
                avatarImageView.imageResource = if (item.role_id >= 3) R.drawable.female else R.drawable.male
                nameTextView.text = item.fullname
                positionTextView.text = "${item.department_name} · ${item.position_name}"
                spurButton.setOnClickListener { spurLlistener(item) }
                inspireButton.setOnClickListener { inspireLlistener(item) }
            }

        }
    }

}