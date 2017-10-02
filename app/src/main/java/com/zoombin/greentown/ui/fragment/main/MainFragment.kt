package com.zoombin.greentown.ui.fragment.main

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import com.zoombin.greentown.R
import com.zoombin.greentown.ui.fragment.main.rank.RankFragment
import com.zoombin.greentown.ui.widget.BottomBar
import com.zoombin.greentown.ui.widget.BottomBarTab
import me.yokeyword.fragmentation.SupportFragment
import org.greenrobot.eventbus.EventBus
import java.lang.reflect.Member

/**
 * Created by gejw on 2017/10/2.
 */

class MainFragment: SupportFragment() {

    private val mFragments = arrayOfNulls<SupportFragment>(5)

    private var mBottomBar: BottomBar? = null

    companion object {

        val FIRST = 0
        val SECOND = 1
        val THIRD = 2
        val FOUR = 3
        val FIVE = 4

        fun newInstance(): MainFragment {
            val args = Bundle()
            val fragment = MainFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_main, container, false)
        initView(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val firstFragment = findChildFragment(MemberFragment::class.java)

        if (firstFragment == null) {
            mFragments[FIRST]   = MemberFragment.newInstance()
            mFragments[SECOND]  = RankFragment.newInstance()
            mFragments[THIRD]   = NoticeFragment.newInstance()
            mFragments[FOUR]    = ActivityFragment.newInstance()
            mFragments[FIVE]    = MeFragment.newInstance()

            loadMultipleRootFragment(R.id.fl_tab_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOUR],
                    mFragments[FIVE])
        } else {
            mFragments[FIRST] = firstFragment
            mFragments[SECOND] = findChildFragment(RankFragment::class.java)
            mFragments[THIRD] = findChildFragment(NoticeFragment::class.java)
            mFragments[FOUR] = findChildFragment(ActivityFragment::class.java)
            mFragments[FIVE] = findChildFragment(MeFragment::class.java)
        }
    }

    private fun initView(view: View) {
        mBottomBar = view.findViewById(R.id.bottomBar) as BottomBar

        val normalColor = R.color.tab_unselect
        var selectedColor = R.color.tab_select
        mBottomBar
                ?.addItem(BottomBarTab(
                        _mActivity,
                        R.drawable.tab_member,
                        R.drawable.tab_member_selected,
                        normalColor,
                        selectedColor,
                        "成员"))
                ?.addItem(BottomBarTab(
                        _mActivity,
                        R.drawable.tab_rank,
                        R.drawable.tab_rank_selected,
                        normalColor,
                        selectedColor,
                        "排行"))
                ?.addItem(BottomBarTab(
                        _mActivity,
                        R.drawable.tab_notice,
                        R.drawable.tab_notice_selected,
                        normalColor,
                        selectedColor,
                        "公告"))
                ?.addItem(BottomBarTab(
                        _mActivity,
                        R.drawable.tab_activity,
                        R.drawable.tab_activity_selected,
                        normalColor,
                        selectedColor,
                        "活动"))
                ?.addItem(BottomBarTab(
                        _mActivity,
                        R.drawable.tab_me,
                        R.drawable.tab_me_selected,
                        normalColor,
                        selectedColor,
                        "我的"))


        mBottomBar?.setOnTabSelectedListener(object : BottomBar.OnTabSelectedListener {

            override  fun onTabSelected(position: Int, prePosition: Int) {
                showHideFragment(mFragments[position], mFragments[prePosition])
            }

            override fun onTabUnselected(position: Int) {

            }

            override fun onTabReselected(position: Int) {

            }
        })
    }

    /**
     * start other BrotherFragment
     */
    fun startBrotherFragment(targetFragment: SupportFragment) {
        start(targetFragment)
    }

}