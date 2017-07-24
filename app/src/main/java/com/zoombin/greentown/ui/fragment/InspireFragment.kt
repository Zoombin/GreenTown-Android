package com.zoombin.greentown.ui.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.zoombin.greentown.R
import com.zoombin.greentown.model.Reason
import com.zoombin.greentown.model.User
import kotlinx.android.synthetic.main.fragment_inspire.*
import kotlinx.android.synthetic.main.layout_titlebar.*
import kotlinx.android.synthetic.main.widget_remark.view.*
import kotlinx.android.synthetic.main.widget_spinner.view.*
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.support.v4.toast

/**
 * Created by gejw on 2017/6/9.
 */

class InspireFragment(user: User? = null) : BaseBackFragment() {

    var user = user
    var reason: Reason? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_inspire, null)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleLabel.text = "我要鼓舞"

        playerSpinner.nameZhLabel.text = "玩家"
        playerSpinner.nameEnLabel.text = "Player"
        playerSpinner.spinnerLayout.backgroundResource = R.drawable.spinner_background_origin
        playerSpinner.arrowImageView.imageResource = R.drawable.spinner_orange_arrow

        reasonSpinner.nameZhLabel.text = "理由"
        reasonSpinner.nameEnLabel.text = "Reason"
        reasonSpinner.spinnerLayout.backgroundResource = R.drawable.spinner_background_blue
        reasonSpinner.arrowImageView.imageResource = R.drawable.spinner_blue_arrow

        rewardSpinner.nameZhLabel.text = "奖励"
        rewardSpinner.nameEnLabel.text = "Reward"
        rewardSpinner.spinnerLayout.backgroundResource = R.drawable.spinner_background_green
        rewardSpinner.arrowImageView.imageResource = R.drawable.spinner_green_arrow
        rewardSpinner.arrowImageView.visibility = View.GONE

        remarkView.remarkNameZhLabel.text = "备注"
        remarkView.remarkNameEnLabel.text = "Remarks"

        if (user != null)
            playerSpinner.valueTextView.text = user!!.fullname

        playerSpinner.setOnClickListener {
            User.allUsers({ users ->
                if (users.size == 0) {
                    toast("无数据")
                    return@allUsers
                }
                val items = users.map { it.fullname }.toTypedArray()
                AlertDialog.Builder(context)
                        .setTitle("选择玩家")
                        .setItems(items, DialogInterface.OnClickListener { dialog, which ->
                            user = users.get(which)
                            playerSpinner.valueTextView.text = user!!.fullname
                        })
                        .show()
            }) { message ->
                if (message != null) toast(message)
            }
        }
        reasonSpinner.setOnClickListener {
            Reason.inspireReason({ reasons ->
                if (reasons.size == 0) {
                    toast("无数据")
                    return@inspireReason
                }
                val items = reasons.map { it.reason }.toTypedArray()
                AlertDialog.Builder(context)
                        .setTitle("选择理由")
                        .setItems(items, DialogInterface.OnClickListener { dialog, which ->
                            reason = reasons.get(which)
                            reasonSpinner.valueTextView.text = reason!!.reason
                            rewardSpinner.valueTextView.text = reason!!.value
                        })
                        .show()
            }) { message ->
                if (message != null) toast(message)
            }
        }
        submitButton.setOnClickListener {
            if (user == null) {
                toast("请选择用户")
                return@setOnClickListener
            }
            if (reason == null) {
                toast("请选择理由")
                return@setOnClickListener
            }
            user?.inspire(reason!!.reason_id,
                    remarkView.remarkEditText.text.toString(), {
                toast("鼓舞成功")
                pop()
            }) { message ->
                if (message != null) toast(message)
            }
        }

    }

}