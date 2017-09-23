package com.zoombin.greentown.ui

import android.os.Bundle
import android.view.View
import com.zoombin.greentown.R
import com.zoombin.greentown.ui.fragment.main.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_main_tab.view.*
import me.yokeyword.fragmentation.SupportActivity
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator


class MainActivity : SupportActivity() {

    private val images = intArrayOf(
            R.drawable.tab_member,
            R.drawable.tab_rank,
            R.drawable.tab_notice,
            R.drawable.tab_activity,
            R.drawable.tab_me)
    private val selected_images = intArrayOf(
            R.drawable.tab_member_selected,
            R.drawable.tab_rank_selected,
            R.drawable.tab_notice_selected,
            R.drawable.tab_activity_selected,
            R.drawable.tab_me_selected)
    private val titles = arrayOf("成员", "排行", "公告", "活动", "我的")
    private val clas = arrayOf<Class<*>>(
            MemberFragment::class.java,
            RankFragment::class.java,
            NoticeFragment::class.java,
            ActivityFragment::class.java,
            MeFragment::class.java)
    private var tabs = arrayListOf<View>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initTab()
    }

    private fun initTab() {
        tabhost.setup(this, supportFragmentManager, R.id.layout_content)

        for (i in images.indices) {
            val image = images[i]
            // 初始化
            val indicatorView = layoutInflater.inflate(R.layout.layout_main_tab, null)
            tabs.add(indicatorView)

            indicatorView.tab_image_indicator.setImageResource(image)
            indicatorView.tab_title_indicator.text = titles[i]

            tabhost.addTab(tabhost.newTabSpec("tab$i").setIndicator(indicatorView), clas[i], null)
            tabhost.setOnTabChangedListener { tagId ->
                updateTab(tagId.replace("tab", "").toInt())
            }
        }
        selectTab(0)
    }

    private fun selectTab(index: Int) {
        tabhost.currentTab = index
        updateTab(index)
    }

    private fun updateTab(index: Int) {

        for (j in tabs.indices) {
            val image = if (j == index) selected_images[j] else images[j]
            tabs[j].tab_image_indicator.setImageResource(image)
        }
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

}
