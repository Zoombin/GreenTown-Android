package com.zoombin.greentown.ui.fragment.main

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TabHost
import android.widget.TextView
import com.zoombin.greentown.R
import com.zoombin.greentown.ui.fragment.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_main_activity.view.*
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

        val tabhost = contentView.tabhost
        tabhost.setup()

        tabhost.addTab(tabhost.newTabSpec("娱乐").setIndicator("娱乐").setContent(R.id.tabLayout))
        tabhost.addTab(tabhost.newTabSpec("任务").setIndicator("任务").setContent(R.id.tabLayout))
        tabhost.addTab(tabhost.newTabSpec("礼包").setIndicator("礼包").setContent(R.id.tabLayout))
        tabhost.setOnTabChangedListener {
            upDateTab(tabhost)
        }
        Handler().postDelayed({
            upDateTab(tabhost)
        }, 100)
    }


    private fun upDateTab(mTabHost: TabHost) {
        for (i in 0 until mTabHost.tabWidget.childCount) {
            mTabHost.tabWidget.getChildAt(i).layoutParams.height = 120

            val tv = mTabHost.tabWidget.getChildAt(i).findViewById(android.R.id.title) as TextView
            if (mTabHost.currentTab == i) {//选中
                tv.setTextColor(this.resources.getColor(R.color.tab_select))
                mTabHost.tabWidget.getChildAt(i).setBackgroundResource(R.drawable.tab_background)
            } else {//不选中
                tv.setTextColor(this.resources.getColor(R.color.tab_unselect))
                mTabHost.tabWidget.getChildAt(i).setBackgroundColor(Color.WHITE)
            }
        }
    }

}