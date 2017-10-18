package com.zoombin.greentown.ui.fragment.main

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.zoombin.greentown.R
import com.zoombin.greentown.model.Department
import com.zoombin.greentown.model.User
import com.zoombin.greentown.ui.fragment.common.BaseFragment
import com.zoombin.greentown.ui.fragment.member.InspireFragment
import com.zoombin.greentown.ui.fragment.member.SpurFragment
import kotlinx.android.synthetic.main.fragment_main_member.view.*
import kotlinx.android.synthetic.main.layout_cell_member.view.*

/**
 * Created by gejw on 2017/9/23.
 */

class MemberFragment: BaseFragment() {

    companion object {

        fun newInstance(): MemberFragment {
            val args = Bundle()
            val fragment = MemberFragment()
            fragment.arguments = args
            return fragment
        }

    }

    private var departments = listOf<Department>()
    private var users = arrayListOf<User>()

    private var selectedDepartment: Department? = null
        set(department) = if (department != null) {
            contentView.departmentNameTextView.text = department.department_name
            queryUsers(department)
        } else {

        }

    override fun layoutId(): Int {
        return R.layout.fragment_main_member
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        queryDepartments()
    }

    override fun initView() {
        title = "成员"
        // 搜索输入框

        // 部门选择按钮
        contentView.departmentLayout.setOnClickListener {
            val items = this.departments.map { "${it.department_name}" }
            AlertDialog.Builder(context)
                    .setTitle("选择部门")
                    .setItems(items.toTypedArray()) { dialog, which ->
                        selectedDepartment = this.departments[which]
                    }
                    .create().show()
        }
        // 列表
        val layoutManager = GridLayoutManager(context, 1)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        contentView.recyclerView.layoutManager = layoutManager
        contentView.recyclerView.adapter = ListAdapter(users, { user ->
            // 鼓舞
            push(InspireFragment.newInstance(user))
        }) { user ->
            // 鞭策
            push(SpurFragment.newInstance(user))
        }
    }

    private fun queryDepartments() {
        Department.departments({ departments ->
            this.departments = departments
            if (users.isEmpty()) {
                // 加载用户
                selectedDepartment = departments.first()
            }
        }) {

        }
    }

    private fun queryUsers(department: Department?) {
        department?.users({ users ->
            reloadData(users)
        }) {
            reloadData()
        }
    }

    private fun reloadData(users: List<User> = emptyList()) {
        this.users.clear()
        this.users.addAll(users)

        // 更新列表
        contentView.recyclerView.adapter.notifyDataSetChanged()
        // 检测是否为空
        contentView.emptyView.visibility = if (users.isEmpty()) View.VISIBLE else View.INVISIBLE
    }

    private class ListAdapter(val users: ArrayList<User>,
                              val inspireListener: (User) -> Unit,
                              val spurListener: (User) -> Unit) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
            return ViewHolder(View.inflate(parent.context, R.layout.layout_cell_member, null))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(users[position], inspireListener, spurListener)
        }

        override fun getItemCount() = users.size

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bind(user: User,
                     inspireListener: (User) -> Unit,
                     spurListener: (User) -> Unit) = with(itemView) {

                Glide.with(context).load(user.logo).into(avatarImageView)
                departmentTextView.text = user.department_name
                nameTextView.text = user.fullname

                guwuButton.setOnClickListener { inspireListener(user) }
                bianceButton.setOnClickListener { spurListener(user) }
            }

        }
    }

}