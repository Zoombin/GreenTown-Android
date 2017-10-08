package com.zoombin.greentown.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zoombin.greentown.R
import com.zoombin.greentown.model.Department
import com.zoombin.greentown.model.Position
import com.zoombin.greentown.model.User
import com.robinge.quickkit.fragment.QBaseBackFragment
import kotlinx.android.synthetic.main.fragment_job.*
import kotlinx.android.synthetic.main.layout_titlebar.*
import kotlinx.android.synthetic.main.widget_spinner.view.*
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.imageResource

/**
 * Created by gejw on 2017/6/9.
 */

class JobFragmentQ : QBaseBackFragment() {

    var department: Department? = null

    var position: Position? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_job, null)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleLabel.text = "我要就职"

        guildSpinner.nameZhLabel.text = "公会"
        guildSpinner.nameEnLabel.text = "Guild"
        guildSpinner.arrowImageView.visibility = View.GONE
        guildSpinner.spinnerLayout.backgroundResource = R.drawable.spinner_background_origin
        guildSpinner.arrowImageView.imageResource = R.drawable.spinner_orange_arrow

        classSpinner.nameZhLabel.text = "职业"
        classSpinner.nameEnLabel.text = "Class"
        classSpinner.arrowImageView.visibility = View.GONE
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

        submitButton.setOnClickListener {
            pop()
        }
    }

    override fun initView() {

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

    override fun layoutId(): Int {
        return 0
    }

}