package com.zoombin.greentown

import android.app.Application
import android.content.Context
import cn.jpush.android.api.JPushInterface
import com.robinge.quickkit.application.QApplication
import org.xutils.x

/**
 * Created by gejw on 2017/6/4.
 */

class GTApplication : QApplication() {

    companion object {

        var context: Context? = null

    }

    override fun onCreate() {
        super.onCreate()

        GTApplication.context = this

        x.Ext.init(this)
        x.Ext.setDebug(true)
        JPushInterface.setDebugMode(true)
        JPushInterface.init(this)
        JPushInterface.setAlias(this, "green", null)
    }

}