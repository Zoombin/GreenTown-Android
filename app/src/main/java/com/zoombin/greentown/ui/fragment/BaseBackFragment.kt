package com.zoombin.greentown.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zoombin.greentown.R
import kotlinx.android.synthetic.main.layout_titlebar.*
import me.yokeyword.fragmentation.SupportFragment
import org.jetbrains.anko.image
import org.jetbrains.anko.imageResource

/**
 * Created by gejw on 2017/6/10.
 */

open class BaseBackFragment : SupportFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return null
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleLabel.visibility = View.VISIBLE
        navigationLeftButton.visibility = View.VISIBLE
        navigationRightButton.visibility = View.VISIBLE
        navigationLeftButton.imageResource = R.drawable.navigation_back
        navigationRightButton.image = null
        navigationLeftButton.setOnClickListener {
            pop()
        }
    }
}