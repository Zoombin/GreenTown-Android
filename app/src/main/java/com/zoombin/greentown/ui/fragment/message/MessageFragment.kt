package com.zoombin.greentown.ui.fragment.message

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.zoombin.greentown.R
import com.robinge.quickkit.fragment.QBaseBackFragment
import kotlinx.android.synthetic.main.fragment_message.view.*
import android.widget.TextView
import android.widget.TabHost



/**
 * Created by gejw on 2017/10/3.
 */

class MessageFragment : QBaseBackFragment() {

    override fun layoutId(): Int {
        return R.layout.fragment_message
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title = "留言"

        Handler().postDelayed({
            upDateTab(contentView.tabhost)
        }, 100)
    }

    override fun initView() {
        val tabhost = contentView.tabhost
        tabhost.setup()

        tabhost.addTab(tabhost.newTabSpec("我要留言").setIndicator("我要留言").setContent(R.id.tab1))
        tabhost.addTab(tabhost.newTabSpec("留言历史").setIndicator("留言历史").setContent(R.id.tab2))
        tabhost.setOnTabChangedListener {
            upDateTab(tabhost)
        }
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