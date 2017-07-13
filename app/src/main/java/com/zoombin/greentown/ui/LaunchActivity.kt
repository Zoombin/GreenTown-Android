package com.zoombin.greentown.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.zoombin.greentown.R

/**
 * Created by gejw on 2017/6/27.
 */

class LaunchActivity: Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000)
    }

}