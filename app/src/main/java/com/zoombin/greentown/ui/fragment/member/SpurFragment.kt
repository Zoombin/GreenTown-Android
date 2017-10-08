package com.zoombin.greentown.ui.fragment.member

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import com.zoombin.greentown.R
import com.zoombin.greentown.model.Reason
import com.zoombin.greentown.model.User
import com.zoombin.greentown.ui.fragment.UserListFragmentQ
import com.robinge.quickkit.fragment.QBaseBackFragment
import kotlinx.android.synthetic.main.fragment_spur.*
import kotlinx.android.synthetic.main.widget_remark.view.*
import kotlinx.android.synthetic.main.widget_spinner.view.*
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.support.v4.toast

/**
 * Created by gejw on 2017/6/9.
 */

class SpurFragment : QBaseBackFragment() {

    companion object {

        fun newInstance(user: User): SpurFragment {
            val fragment = SpurFragment()
            val args = Bundle()
            args.putSerializable("user", user)
            fragment.arguments = args
            return fragment
        }

    }

    var user: User? = null
    var reason: Reason? = null

    override fun layoutId(): Int {
        return R.layout.fragment_spur
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        if (args != null) {
            user = args.getSerializable("user") as? User
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        title = "我要鞭策"

    }

    override fun initView() {
        playerSpinner.nameZhLabel.text = "玩家"
        playerSpinner.nameEnLabel.text = "Player"
        playerSpinner.spinnerLayout.backgroundResource = R.drawable.spinner_background_origin
        playerSpinner.arrowImageView.imageResource = R.drawable.spinner_orange_arrow
        playerSpinner.arrowImageView.visibility = View.INVISIBLE

        reasonSpinner.nameZhLabel.text = "理由"
        reasonSpinner.nameEnLabel.text = "Reason"
        reasonSpinner.spinnerLayout.backgroundResource = R.drawable.spinner_background_blue
        reasonSpinner.arrowImageView.imageResource = R.drawable.spinner_blue_arrow

        rewardSpinner.nameZhLabel.text = "惩罚"
        rewardSpinner.nameEnLabel.text = "Penalty"
        rewardSpinner.spinnerLayout.backgroundResource = R.drawable.spinner_background_green
        rewardSpinner.arrowImageView.imageResource = R.drawable.spinner_green_arrow
        rewardSpinner.arrowImageView.visibility = View.INVISIBLE

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
                start(UserListFragmentQ(ArrayList(users), { user ->
                    this.user = user
                    playerSpinner.valueTextView.text = user!!.fullname
                }))
            }) { message ->
                if (message != null) toast(message)
            }
        }
        reasonSpinner.setOnClickListener {
            Reason.spurReason({ reasons ->
                if (reasons.size == 0) {
                    toast("无数据")
                    return@spurReason
                }
                val items = reasons.map { it.reason }.toTypedArray()
                AlertDialog.Builder(context)
                        .setTitle("选择理由")
                        .setItems(items, { dialog, which ->
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
            user?.spur(reason!!.reason_id,
                    remarkView.remarkEditText.text.toString(), {
                toast("鞭策成功")
                pop()
            }) { message ->
                if (message != null) toast(message)
            }
        }
    }

}