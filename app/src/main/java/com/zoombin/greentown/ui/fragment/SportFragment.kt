package com.zoombin.greentown.ui.fragment

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
import kotlinx.android.synthetic.main.fragment_sport.*
import kotlinx.android.synthetic.main.layout_sport_cell.view.*
import kotlinx.android.synthetic.main.layout_titlebar.*
import me.yokeyword.fragmentation.SupportFragment
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.support.v4.toast

/**
 * Created by gejw on 2017/6/9.
 */

class SportFragment : BaseBackFragment() {

    var items = ArrayList<Sport>()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_sport, null)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleLabel.text = "歌林娱乐"

        val layoutManager = GridLayoutManager(context, 1)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = ListAdapter(items, {
            // 规则
        }, {
            // 参加
        }) {
            start(SportInfoFragment(it, poolTextView.text.toString()))
        }

        Sport.sports({ sports ->
            items.clear()
            items.addAll(sports)
            recyclerView.adapter.notifyDataSetChanged()
            emptyView.visibility = if (items.size == 0) View.VISIBLE else View.INVISIBLE
        }) { message ->
            if (message != null) toast(message)
            emptyView.visibility = if (items.size == 0) View.VISIBLE else View.INVISIBLE
        }
        Sport.pool({ pool ->
            poolTextView.text = "总奖金：$pool"
        }) { message ->
            if (message != null) toast(message)
        }
    }


    class ListAdapter(val cars: ArrayList<Sport>,
                      val ruleListener: (Sport) -> Unit,
                      val joinListener: (Sport) -> Unit,
                      val listener: (Sport) -> Unit) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
            return ViewHolder(View.inflate(parent.context, R.layout.layout_sport_cell, null))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(cars[position], ruleListener, joinListener, listener)
        }

        override fun getItemCount() = cars.size

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bind(item: Sport,
                     ruleListener: (Sport) -> Unit,
                     joinListener: (Sport) -> Unit,
                     listener: (Sport) -> Unit) = with(itemView) {
                nameTextView.text = item.title
                descTextView.text = item.sub_title

                Glide.with(context).load(item.logo).into(avatarImageView)

                val selectedRes = R.drawable.sport_percent_selected
                val unSelectedRes = R.drawable.sport_percent_unselected
                val percent = item.cur_quantity / item.quantity.toFloat()
                percent1ImageView.imageResource = if (percent >= 0) selectedRes else unSelectedRes
                percent2ImageView.imageResource = if (percent >= 0.2) selectedRes else unSelectedRes
                percent3ImageView.imageResource = if (percent >= 0.4) selectedRes else unSelectedRes
                percent4ImageView.imageResource = if (percent >= 0.6) selectedRes else unSelectedRes
                percent5ImageView.imageResource = if (percent >= 0.8) selectedRes else unSelectedRes

                joinButton.text = if (item.joined == 0) "参加" else "已参加"
                joinButton.isEnabled = item.joined == 0

                setOnClickListener { listener(item) }

                joinButton.setOnClickListener { joinListener(item) }
                ruleButton.setOnClickListener { ruleListener(item) }
            }

        }
    }

}