package com.zoombin.greentown.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zoombin.greentown.R
import com.zoombin.greentown.model.Task
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_titlebar.*
import me.yokeyword.fragmentation.SupportFragment
import java.util.logging.Level

/**
 * Created by gejw on 2017/6/9.
 */

class HomeFragment : BaseBackFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_home, null)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleLabel.text = "歌林小屋"

        inauguralButton.setOnClickListener { start(JobFragment()) }

        giftButton.setOnClickListener { start(GiftFragment()) }

        encourageButton.setOnClickListener { start(InspireFragment()) }

        pushButton.setOnClickListener { start(SpurFragment()) }

        taskButton.setOnClickListener { start(TaskFragment()) }

        messageButton.setOnClickListener { start(LevelMessageFragment()) }
    }

}