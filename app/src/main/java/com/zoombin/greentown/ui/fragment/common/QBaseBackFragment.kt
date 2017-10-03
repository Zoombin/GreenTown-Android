package com.zoombin.greentown.ui.fragment.common

import android.os.Bundle
import android.view.View
import com.zoombin.greentown.R
import kotlinx.android.synthetic.main.layout_titlebar.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.DefaultNoAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import org.jetbrains.anko.imageResource

/**
 * Created by gejw on 2017/6/10.
 */

open abstract class QBaseBackFragment : QBaseFragment() {

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLeftBarButtonItem(BarButtonItem(R.drawable.navigation_back, { pop() }))
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

}