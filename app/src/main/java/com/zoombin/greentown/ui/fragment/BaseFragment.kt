package com.zoombin.greentown.ui.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zoombin.greentown.R
import kotlinx.android.synthetic.main.layout_titlebar.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultNoAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import org.jetbrains.anko.image
import org.jetbrains.anko.imageResource

/**
 * Created by gejw on 2017/6/10.
 */

open class BaseFragment : SupportFragment() {

    private var isInitBroadcastReceiver = false

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return null
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleLabel.visibility = View.VISIBLE
        navigationLeftButton.image = null
        navigationLeftButton.visibility = View.VISIBLE
        navigationLeftButton.setOnClickListener { pop() }
        navigationRightButton.image = null
        navigationRightButton.visibility = View.VISIBLE
    }

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context : Context?, intent : Intent?) {
            when(intent!!.action){
                "logout" -> { didLogout() }
                "login" -> { didLogin() }
            }
        }
    }

    override fun onPause(){
        super.onPause()
        if (isInitBroadcastReceiver) {
            context.unregisterReceiver(broadcastReceiver)
            isInitBroadcastReceiver = false
        }
    }
    override fun onResume(){
        super.onResume()
        isInitBroadcastReceiver = true
        context.registerReceiver(broadcastReceiver, IntentFilter("logout"))
        context.registerReceiver(broadcastReceiver, IntentFilter("login"))
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultNoAnimator()
    }

    open fun didLogout() {

    }

    open fun didLogin() {

    }

}