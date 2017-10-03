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
import com.zoombin.greentown.ui.fragment.main.MainFragment
import com.zoombin.greentown.ui.fragment.me.HobbyFragment


/**
 * Created by gejw on 2017/6/10.
 */

open abstract class BaseFragment : SupportFragment() {

    private var isInitBroadcastReceiver = false

    var leftBarButtonItem: BarButtonItem? = null

    var rightBarButtonItem: BarButtonItem? = null

    public lateinit var contentView: View

    public var title: String = ""
        set(title) {
            titleLabel.text = title
        }

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

        navigationLeftButton?.visibility = View.GONE
        navigationLeftButton.setOnClickListener{ leftBarButtonItem?.callback?.invoke() }
        navigationLeftTextView?.visibility = View.GONE
        navigationLeftTextView.setOnClickListener{ leftBarButtonItem?.callback?.invoke() }

        navigationRightButton?.visibility = View.GONE
        navigationRightButton.setOnClickListener{ rightBarButtonItem?.callback?.invoke() }
        navigationRightTextView?.visibility = View.GONE
        navigationRightTextView.setOnClickListener{ rightBarButtonItem?.callback?.invoke() }
    }

    // 布局
    public abstract fun layoutId(): Int

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

    public fun push(targetFragment: SupportFragment) {
        (parentFragment as MainFragment).startBrotherFragment(targetFragment)
    }

}

public class BarButtonItem {

    public var title: String = ""
    public var image: Int = 0
    public var callback: () -> Unit

    public constructor(title: String, callback: () -> Unit) {
        this.title = title
        this.callback = callback
    }

    public constructor(image: Int, callback: () -> Unit) {
        this.image = image
        this.callback = callback
    }

}


public fun BaseFragment.setLeftBarButtonItem(item: BarButtonItem) {
    this.leftBarButtonItem = item
    if (item.title != "") {
        navigationLeftTextView.visibility = View.VISIBLE
        navigationLeftTextView.text = item.title
    } else {
        navigationLeftTextView.visibility = View.GONE
    }
    if (item.image != 0) {
        navigationLeftButton.visibility = View.VISIBLE
        navigationLeftButton.imageResource = item.image
    } else {
        navigationLeftButton.visibility = View.GONE
    }

}

public fun BaseFragment.setRightBarButtonItem(item: BarButtonItem) {
    this.rightBarButtonItem = item
    if (item.title != "") {
        navigationRightTextView.visibility = View.VISIBLE
        navigationRightTextView.text = item.title
    } else {
        navigationRightTextView.visibility = View.GONE
    }
    if (item.image != 0) {
        navigationRightButton.visibility = View.VISIBLE
        navigationRightButton.imageResource = item.image
    } else {
        navigationRightButton.visibility = View.GONE
    }
}