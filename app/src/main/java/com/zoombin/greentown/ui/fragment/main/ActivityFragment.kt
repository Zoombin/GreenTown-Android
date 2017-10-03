package com.zoombin.greentown.ui.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zoombin.greentown.R
import com.zoombin.greentown.ui.fragment.common.BaseFragment
import kotlinx.android.synthetic.main.layout_titlebar.*

/**
 * Created by gejw on 2017/9/23.
 */

class ActivityFragment : BaseFragment() {

    companion object {

        fun newInstance(): ActivityFragment {
            val args = Bundle()
            val fragment = ActivityFragment()
            fragment.arguments = args
            return fragment
        }

    }

    override fun layoutId(): Int {
        return R.layout.fragment_main_activity
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        title = "活动"
    }

}