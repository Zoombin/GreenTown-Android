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
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.layout_titlebar.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultNoAnimator
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

        navigationLeftButton.setOnClickListener { start(UserFragment()) }

        navigationRightButton.setOnClickListener {

        }

        messageButton.setOnClickListener { start(MessageFragment()) }

        homeButton.setOnClickListener { start(HomeFragment()) }

        guildButton.setOnClickListener { start(GuildFragment()) }

        sportButton.setOnClickListener { start(SportFragment()) }

        rankButton.setOnClickListener { start(RankFragment()) }

        if (User.current() == null)
            start(LoginFragment())
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultNoAnimator()
    }

}
