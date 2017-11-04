package com.zoombin.greentown.ui.fragment.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.robinge.quickkit.widget.tabbar.QBottomBar
import com.robinge.quickkit.widget.tabbar.QBottomBarTab
import com.zoombin.greentown.R
import com.zoombin.greentown.model.User
import com.zoombin.greentown.ui.LoginActivity
import com.zoombin.greentown.ui.fragment.common.BaseFragment
import com.zoombin.greentown.ui.fragment.main.rank.RankFragment
import me.yokeyword.fragmentation.SupportFragment

/**
 * Created by gejw on 2017/10/2.
 */

open abstract class MainBaseFragment: BaseFragment() {

    open fun exec() {

    }

}

class MainFragment: SupportFragment() {

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

    private val mFragments = arrayOfNulls<MainBaseFragment>(5)

    private var mBottomBar: QBottomBar? = null

    private var isInitBroadcastReceiver = false

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

        checkLoginState()
    }

    private fun initView(view: View) {
        mBottomBar = view.findViewById(R.id.bottomBar) as QBottomBar

        val normalColor = R.color.tab_unselect
        var selectedColor = R.color.tab_select
        mBottomBar
                ?.addItem(QBottomBarTab(
                        _mActivity,
                        R.drawable.tab_member,
                        R.drawable.tab_member_selected,
                        normalColor,
                        selectedColor,
                        "成员"))
                ?.addItem(QBottomBarTab(
                        _mActivity,
                        R.drawable.tab_rank,
                        R.drawable.tab_rank_selected,
                        normalColor,
                        selectedColor,
                        "排行"))
                ?.addItem(QBottomBarTab(
                        _mActivity,
                        R.drawable.tab_notice,
                        R.drawable.tab_notice_selected,
                        normalColor,
                        selectedColor,
                        "公告"))
                ?.addItem(QBottomBarTab(
                        _mActivity,
                        R.drawable.tab_activity,
                        R.drawable.tab_activity_selected,
                        normalColor,
                        selectedColor,
                        "活动"))
                ?.addItem(QBottomBarTab(
                        _mActivity,
                        R.drawable.tab_me,
                        R.drawable.tab_me_selected,
                        normalColor,
                        selectedColor,
                        "我的"))


        mBottomBar?.setOnTabSelectedListener(object : QBottomBar.OnTabSelectedListener {

            override  fun onTabSelected(position: Int, prePosition: Int) {
                showHideFragment(mFragments[position], mFragments[prePosition])

                if (position == FIVE) {
                    MeFragment.newInstance().query()
                }
                mFragments[position]?.exec()
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

    fun checkLoginState() {
        if (User.current() == null) {
            activity.startActivity(Intent(activity, LoginActivity::class.java))
            activity.finish()
        } else {
            User.current()?.statistics()
        }
    }

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context : Context?, intent : Intent?) {
            when(intent!!.action){
                "logout" -> { didLogout() }
                "login" -> { didLogin() }
            }
        }
    }

    override fun onDestroy(){
        super.onDestroy()
        if (isInitBroadcastReceiver) {
            context.unregisterReceiver(broadcastReceiver)
            isInitBroadcastReceiver = false
        }
    }

    override fun onResume(){
        super.onResume()
        if (!isInitBroadcastReceiver) {
            isInitBroadcastReceiver = true
            context.registerReceiver(broadcastReceiver, IntentFilter("logout"))
            context.registerReceiver(broadcastReceiver, IntentFilter("login"))
        }
    }

    fun didLogin() {
        checkLoginState()
    }

    fun didLogout() {
        checkLoginState()
    }

}