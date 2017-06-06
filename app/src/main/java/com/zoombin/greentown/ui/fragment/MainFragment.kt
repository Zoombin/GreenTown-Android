package com.zoombin.greentown.ui.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zoombin.greentown.R
import com.zoombin.greentown.model.User
import kotlinx.android.synthetic.main.layout_titlebar.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultVerticalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import org.jetbrains.anko.imageResource

class MainFragment : SupportFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_main, null)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigationTitleImageView.visibility = View.VISIBLE
        navigationLeftButton.visibility = View.VISIBLE
        navigationRightButton.visibility = View.VISIBLE
        navigationLeftButton.imageResource = R.drawable.navigation_user
        navigationRightButton.imageResource = R.drawable.navigation_menu

        navigationLeftButton.setOnClickListener {
            // 点击用户信息
        }

        navigationRightButton.setOnClickListener {
            // 点击菜单
        }

        if (User.current() == null)
            start(LoginFragment())
    }

}

