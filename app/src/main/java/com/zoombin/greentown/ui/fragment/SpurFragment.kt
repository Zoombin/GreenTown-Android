package com.zoombin.greentown.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.zoombin.greentown.R
import kotlinx.android.synthetic.main.layout_titlebar.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultVerticalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

/**
 * Created by gejw on 2017/6/9.
 */

class SpurFragment : BaseBackFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_spur, null)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleLabel.text = "我要鞭策"
    }

}