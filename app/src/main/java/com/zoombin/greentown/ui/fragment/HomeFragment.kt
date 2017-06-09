package com.zoombin.greentown.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zoombin.greentown.R
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

        inauguralButton.setOnClickListener {  }

        giftButton.setOnClickListener {  }

        encourageButton.setOnClickListener {  }

        pushButton.setOnClickListener {  }

        taskButton.setOnClickListener {  }

        messageButton.setOnClickListener { start(LevelMessageFragment()) }
    }

}