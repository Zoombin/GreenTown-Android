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
import kotlinx.android.synthetic.main.fragment_rank.*
import kotlinx.android.synthetic.main.layout_rank_cell.view.*
import kotlinx.android.synthetic.main.layout_titlebar.*
import me.yokeyword.fragmentation.SupportFragment
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.support.v4.toast

/**
 * Created by gejw on 2017/6/9.
 */

class RankFragment : BaseBackFragment() {

    var items = ArrayList<User>()
    var selectedIndex = 0

    private var coinsUsers = ArrayList<User>()
    private var pointsUsers = ArrayList<User>()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_rank, null)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleLabel.text = "歌林排行"

        val layoutManager = GridLayoutManager(activity, 1)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = ListAdapter(items) {

        }

        coinsButton.setOnClickListener { selectSegment(0) }
        pointButton.setOnClickListener { selectSegment(1) }
        selectSegment(0, true)

        User.coinsRank(0, { users ->
            coinsUsers.clear()
            coinsUsers.addAll(users)
            reloadData()
        }) { message ->
            if (message != null) toast(message)
            reloadData()
        }
        User.pointsRank(0, { users ->
            pointsUsers.clear()
            pointsUsers.addAll(users)
            reloadData()
        }) { message ->
            if (message != null) toast(message)
            reloadData()
        }
    }

    fun selectSegment(index: Int, isInit: Boolean = false) {
        selectedIndex = index
        when(index) {
            0 -> {
                coinsTriangle.visibility = View.VISIBLE
                pointTriangle.visibility = View.INVISIBLE
            }
            1 -> {
                coinsTriangle.visibility = View.INVISIBLE
                pointTriangle.visibility = View.VISIBLE
            }
        }
        (recyclerView.adapter as ListAdapter).selectedIndex = index
        if (!isInit)
            reloadData()
    }

    fun reloadData() {
        items.clear()
        when(selectedIndex) {
            0 -> {
                items.addAll(coinsUsers)
            }
            1 -> {
                items.addAll(pointsUsers)
            }
        }
        recyclerView.adapter.notifyDataSetChanged()
        emptyView.visibility = if (items.size == 0) View.VISIBLE else View.INVISIBLE
    }

    class ListAdapter(val users: ArrayList<User>, val listener: (User) -> Unit) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

        var selectedIndex = 0

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
            return ViewHolder(View.inflate(parent.context, R.layout.layout_rank_cell, null))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(users[position], position, selectedIndex, listener)
        }

        override fun getItemCount() = users.size

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bind(item: User, position: Int, selectedIndex: Int, listener: (User) -> Unit) = with(itemView) {
                when(position % 4) {
                    0 -> { layout.backgroundResource = R.drawable.cell_blue }
                    1 -> { layout.backgroundResource = R.drawable.cell_green }
                    2 -> { layout.backgroundResource = R.drawable.cell_purple_xml }
                    3 -> { layout.backgroundResource = R.drawable.cell_red }
                }
                rankTextView.text = "${position + 1}"
                nameTextView.text = item.fullname
                titleTextView.text = if (selectedIndex == 0) "" else "${item.title_name}"
                moneyTextView.text = if (selectedIndex == 0) "绿币：${item.coins}" else "成就点：${item.points}"
                positionTextView.text = "${item.department_name} · ${item.position_name}"
                setOnClickListener { listener(item) }
            }

        }
    }

}