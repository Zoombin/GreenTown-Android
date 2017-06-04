package com.zoombin.greentown

import android.app.Application
import org.xutils.x

/**
 * Created by gejw on 2017/6/4.
 */

class GTApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        x.Ext.init(this)
        x.Ext.setDebug(true)
    }
}