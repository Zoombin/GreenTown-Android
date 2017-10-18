package com.zoombin.greentown.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.zoombin.greentown.R
import org.jetbrains.anko.toast

/**
 * Created by gejw on 2017/6/27.
 */

class LaunchActivity: Activity() {

    val handler = Handler()
    val runnable = Runnable {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        handler.postDelayed(runnable, 2000)
    }

    override fun finish() {
        super.finish()
        handler.removeCallbacks(runnable)
    }

}