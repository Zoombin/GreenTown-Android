package com.zoombin.greentown.ui.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import com.bumptech.glide.Glide
import com.zoombin.greentown.R
import com.zoombin.greentown.model.User
import com.robinge.quickkit.fragment.QBaseBackFragment
import kotlinx.android.synthetic.main.fragment_users.*
import kotlinx.android.synthetic.main.layout_guild_cell.view.*
import kotlinx.android.synthetic.main.layout_titlebar.*
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.image
import org.jetbrains.anko.imageResource

/**
 * Created by gejw on 2017/7/28.
 */


class UserListFragmentQ(users: ArrayList<User>, selectedLlistener: (User) -> Unit) : QBaseBackFragment() {

    var users = users
    var selectedLlistener = selectedLlistener

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_users, null)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleLabel.text = "选择用户"

        val layoutManager = GridLayoutManager(context, 1)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = ListAdapter(users, { user ->
            selectedLlistener(user)
            pop()
        })

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                var result = ArrayList<User>()
                for (user in users) {
                    if (user.fullname.contains(query!!)) {
                        result.add(user)
                    }
                }

                val adapter = recyclerView.adapter as ListAdapter
                adapter.users.clear()
                adapter.users.addAll(result)
                recyclerView.adapter.notifyDataSetChanged()
                return false
            }

        })
    }

    override fun initView() {

    }


    class ListAdapter(val items: ArrayList<User>,
                      val selectedLlistener: (User) -> Unit) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

        var users = items

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
            return ViewHolder(View.inflate(parent.context, R.layout.layout_guild_cell, null))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(users[position], position, selectedLlistener)
        }

        override fun getItemCount() = users.size

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bind(item: User, position: Int, selectedLlistener: (User) -> Unit) = with(itemView) {
                when(position % 4) {
                    0 -> { layout.backgroundResource = R.drawable.cell_blue_xml }
                    1 -> { layout.backgroundResource = R.drawable.cell_green_xml }
                    2 -> { layout.backgroundResource = R.drawable.cell_purple_xml }
                    3 -> { layout.backgroundResource = R.drawable.cell_red_xml }
                }
                if (avatarImageView.image == null) {
                    avatarImageView.imageResource = item.avatar()
                }
                Glide.with(context).load(item.logo).into(avatarImageView)
                nameTextView.text = item.fullname
                positionTextView.text = "${item.department_name}"
                spurButton.visibility = View.GONE
                inspireButton.visibility = View.GONE
                layout.setOnClickListener { selectedLlistener(item) }
            }

        }
    }

    override fun layoutId(): Int {
        return 0
    }

}