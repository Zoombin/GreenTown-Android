package com.zoombin.greentown.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.zoombin.greentown.R
import com.zoombin.greentown.ui.fragment.MainFragment
import me.yokeyword.fragmentation.SupportActivity
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

class MainActivity : SupportActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            loadRootFragment(R.id.layout, MainFragment())
        }

    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

}
