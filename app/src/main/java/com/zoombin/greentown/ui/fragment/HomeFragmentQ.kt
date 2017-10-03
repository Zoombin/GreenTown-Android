package com.zoombin.greentown.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zoombin.greentown.R
import com.zoombin.greentown.ui.fragment.common.QBaseBackFragment
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_titlebar.*

/**
 * Created by gejw on 2017/6/9.
 */

class HomeFragmentQ : QBaseBackFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_home, null)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleLabel.text = "歌林小屋"

        inauguralButton.setOnClickListener { start(UserFragment()) }

        giftButton.setOnClickListener { start(GiftFragmentQ()) }

        encourageButton.setOnClickListener { start(InspireFragmentQ()) }

        pushButton.setOnClickListener { start(SpurFragmentQ()) }

        taskButton.setOnClickListener { start(TaskFragmentQ()) }

        messageButton.setOnClickListener { start(LevelMessageFragmentQ()) }
    }

    override fun layoutId(): Int {
        return 0
    }

}