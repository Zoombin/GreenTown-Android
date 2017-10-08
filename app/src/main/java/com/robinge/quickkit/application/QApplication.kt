package com.robinge.quickkit.application

import android.app.Application
import android.content.Context

/**
 * Created by gejw on 2017/10/8.
 */

open class QApplication: Application() {

    companion object {

        var context: Context? = null

    }

    override fun onCreate() {
        super.onCreate()

        QApplication.context = this
    }

}