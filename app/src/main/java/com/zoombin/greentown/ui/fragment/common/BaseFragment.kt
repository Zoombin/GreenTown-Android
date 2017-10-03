package com.zoombin.greentown.ui.fragment.common

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.zoombin.greentown.R
import kotlinx.android.synthetic.main.fragment_base.*
import kotlinx.android.synthetic.main.layout_titlebar.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultNoAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.image
import org.jetbrains.anko.imageResource
import android.view.Gravity
import com.blankj.utilcode.util.ToastUtils.setGravity
import android.widget.LinearLayout



/**
 * Created by gejw on 2017/6/10.
 */

open abstract class BaseFragment : SupportFragment() {

    private var isInitBroadcastReceiver = false

    lateinit var contentView: View

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_base, null)
        val contentView = inflater?.inflate(layoutId(), null)
        if (contentView != null) {
            val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT)
            contentView?.layoutParams = params
            (view?.findViewById(R.id.contentLayout) as? LinearLayout)?.addView(contentView)
            this.contentView = contentView
        }
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleLabel?.visibility = View.VISIBLE
        navigationLeftButton?.image = null
        navigationLeftButton?.visibility = View.VISIBLE
        navigationRightButton?.image = null
        navigationRightButton?.visibility = View.VISIBLE
    }

    // 布局
    abstract fun layoutId(): Int

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

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultNoAnimator()
    }

    open fun didLogout() {
        // 退出登录
    }

    open fun didLogin() {
        // 登录成功
    }

}