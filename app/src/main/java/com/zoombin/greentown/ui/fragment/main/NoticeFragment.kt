package com.zoombin.greentown.ui.fragment.main

import android.os.Bundle
import android.view.View
import com.zoombin.greentown.R
import com.zoombin.greentown.ui.fragment.common.BaseFragment

/**
 * Created by gejw on 2017/9/23.
 */

class NoticeFragment : BaseFragment() {

    companion object {

        fun newInstance(): NoticeFragment {
            val args = Bundle()
            val fragment = NoticeFragment()
            fragment.arguments = args
            return fragment
        }

    }

    override fun layoutId(): Int {
        return R.layout.fragment_recyclerview
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        title = "公告"
    }

    override fun initView() {

    }

}