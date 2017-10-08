package com.zoombin.greentown.ui.fragment.main.rank

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.zoombin.greentown.R
import com.zoombin.greentown.model.User
import com.robinge.quickkit.fragment.BarButtonItem
import com.zoombin.greentown.ui.fragment.common.BaseFragment
import com.robinge.quickkit.fragment.setRightBarButtonItem
import kotlinx.android.synthetic.main.fragment_rank.*
import kotlinx.android.synthetic.main.layout_rank_cell.view.*
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.image
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.support.v4.toast
import java.util.*

/**
 * Created by gejw on 2017/6/9.
 */

class RankFragment : BaseFragment() {

    companion object {

        fun newInstance(): RankFragment {
            val args = Bundle()
            val fragment = RankFragment()
            fragment.arguments = args
            return fragment
        }

    }

    private var users = ArrayList<User>()
    private var currentMonth = 0
        set(month) {
            query(month)
        }

    override fun layoutId(): Int {
        return R.layout.fragment_recyclerview
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val c = Calendar.getInstance()
        currentMonth = c.get(Calendar.MONTH) + 1
    }

    override fun initView() {
        title = "排行"
        setRightBarButtonItem(BarButtonItem("选择日期", {
            showSelectDateDialog()
        }))
        val layoutManager = GridLayoutManager(context, 1)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = ListAdapter(users)
    }

    private fun showSelectDateDialog() {
        val items = ArrayList<String>()
        items.add("${month(currentMonth - 2)}月")
        items.add("${month(currentMonth - 1)}月")
        items.add("${month(currentMonth)}月")
        AlertDialog.Builder(context)
                .setTitle("选择月份")
                .setItems(items.toTypedArray(), { dialog, which ->
                    currentMonth = 2 - which
                })
                .show()
    }

    private fun month(month: Int): Int {
        if (month <= 0) {
            return 12 + month
        }
        return month
    }

    private fun query(month: Int) {
        User.coinsRank(0, month, { users ->
            reloadData(users)
        }) {
            reloadData()
        }
    }

    private fun reloadData(users: List<User> = emptyList()) {
        this.users.clear()
        this.users.addAll(users)

        recyclerView.adapter.notifyDataSetChanged()
        emptyView.visibility = if (users.isEmpty()) View.VISIBLE else View.INVISIBLE
    }

    class ListAdapter(val users: ArrayList<User>) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
            return ViewHolder(View.inflate(parent.context, R.layout.layout_rank_cell, null))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(users[position], position)
        }

        override fun getItemCount() = users.size

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bind(user: User, position: Int) = with(itemView) {

                Glide.with(context).load(user.logo).into(avatarImageView)
                coinsTextView.text = "绿币：${user.coins}"
                nameTextView.text = user.fullname
                titleTextView.text = user.title_name
                rankTextView.text = "${position + 1}"
                departmentTextView.text = user.department_name
            }

        }
    }

}