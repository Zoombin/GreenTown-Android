package com.zoombin.greentown.ui.fragment.common

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter

/**
 * Created by gejw on 2017/10/3.
 */

abstract class BaseFragment: QBaseFragment() {

    private var isInitBroadcastReceiver = false

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context : Context?, intent : Intent?) {
            when(intent!!.action){
                "logout" -> { didLogout() }
                "login" -> { didLogin() }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isInitBroadcastReceiver) {
            context.unregisterReceiver(broadcastReceiver)
            isInitBroadcastReceiver = false
        }
    }

    override fun onResume() {
        super.onResume()
        if (!isInitBroadcastReceiver) {
            isInitBroadcastReceiver = true
            context.registerReceiver(broadcastReceiver, IntentFilter("logout"))
            context.registerReceiver(broadcastReceiver, IntentFilter("login"))
        }
    }

    open fun didLogout() {
        // 退出登录
    }

    open fun didLogin() {
        // 登录成功
    }

}