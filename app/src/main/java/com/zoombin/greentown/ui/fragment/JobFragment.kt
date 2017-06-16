package com.zoombin.greentown.ui.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.zoombin.greentown.R
import com.zoombin.greentown.model.Department
import com.zoombin.greentown.model.Position
import com.zoombin.greentown.model.User
import com.zoombin.greentown.net.Net
import kotlinx.android.synthetic.main.fragment_job.*
import kotlinx.android.synthetic.main.layout_titlebar.*
import kotlinx.android.synthetic.main.widget_spinner.view.*
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.textColor

/**
 * Created by gejw on 2017/6/9.
 */

class JobFragment : BaseBackFragment() {

    var department: Department? = null

    var position: Position? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_job, null)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleLabel.text = "我要就职"

        guildSpinner.nameZhLabel.text = "工会"
        guildSpinner.nameEnLabel.text = "Guild"
        guildSpinner.spinnerLayout.backgroundResource = R.drawable.spinner_background_origin
        guildSpinner.arrowImageView.imageResource = R.drawable.spinner_orange_arrow

        classSpinner.nameZhLabel.text = "职业"
        classSpinner.nameEnLabel.text = "Class"
        classSpinner.spinnerLayout.backgroundResource = R.drawable.spinner_background_blue
        classSpinner.arrowImageView.imageResource = R.drawable.spinner_blue_arrow

        Department.departments({
            updateDepartment(it.filter { it.department_name == User.current()?.department_name }.first())
            department?.positions({
                updatePosition(it.filter { it.position_name == User.current()?.position_name }.first())
            }) {

            }
        }) {

        }

        guildSpinner.setOnClickListener {
            Department.departments({
                // 弹框
                AlertDialog.Builder(context)
                        .setTitle("选择工会")
                        .setItems(it.map { it.department_name }.toTypedArray(), DialogInterface.OnClickListener { dialog, which ->
                            updateDepartment(it.get(which))
                        })
                        .show()
            }) {

            }
        }

        classSpinner.setOnClickListener {
            department?.positions({
                AlertDialog.Builder(context)
                        .setTitle("选择职位")
                        .setItems(it.map { it.position_name }.toTypedArray(), DialogInterface.OnClickListener { dialog, which ->
                            updatePosition(it.get(which))
                        })
                        .show()
            }) {

            }
        }

        submitButton.setOnClickListener {
            if (department == null) {
                toast("请选择工会")
                 return@setOnClickListener
            }
            if (position == null) {
                toast("请选择职位")
                return@setOnClickListener
            }
            User.current()?.completeProfile(departmentId = department!!.department_id, positionId = position!!.position_id, success = {
                toast("修改成功")
                val user = User.current()
                user?.department_id = department!!.department_id
                user?.department_name = department!!.department_name
                user?.position_id = position!!.position_id
                user?.position_name = position!!.position_name
                user?.save()
                pop()
            }) { message ->
                if (message != null) toast(message)
            }
        }
    }

    fun updateDepartment(department: Department?) {
        this.department = department
        if (department == null) {
            guildSpinner.valueTextView.text = "请选择"
            return
        }
        guildSpinner.valueTextView.text = department.department_name
        updatePosition(null)
    }

    fun updatePosition(position: Position?) {
        this.position = position
        if (position == null) {
            classSpinner.valueTextView.text = "请选择"
            return
        }
        classSpinner.valueTextView.text = position.position_name
    }

}