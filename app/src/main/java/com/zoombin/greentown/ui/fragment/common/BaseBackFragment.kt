package com.zoombin.greentown.ui.fragment.common

import android.os.Bundle
import android.view.View
import com.zoombin.greentown.R
import kotlinx.android.synthetic.main.layout_titlebar.*
import org.jetbrains.anko.imageResource

/**
 * Created by gejw on 2017/6/10.
 */

open abstract class BaseBackFragment : BaseFragment() {

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationLeftButton?.imageResource = R.drawable.navigation_back
        navigationLeftButton.setOnClickListener { pop() }
    }

}