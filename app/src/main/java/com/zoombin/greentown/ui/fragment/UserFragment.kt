package com.zoombin.greentown.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zoombin.greentown.R
import com.zoombin.greentown.model.User
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.android.synthetic.main.layout_titlebar.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultVerticalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.support.v4.toast

/**
 * Created by gejw on 2017/6/7.
 */

class UserFragment : SupportFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_user, null)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigationRightButton.visibility = View.VISIBLE
        navigationRightButton.imageResource = R.drawable.navigation_close
        navigationRightButton.setOnClickListener { pop() }

        val user = User.current()
        if (user != null) {
            avatarImageView.imageResource = if (user.role_id >= 3) R.drawable.female else R.drawable.male
            nameTextView.text = user.fullname
            goalTextView.text = "金币：${user.golds}"
            moneyTextView.text = "绿币：${user.coins}"
            integralTextView.text = "成就点：${user.points}"
            positionTextView.text = "${user.department_name} · ${user.position_name}"
        }
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultVerticalAnimator()
    }

}