package com.zoombin.greentown.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zoombin.greentown.R
import kotlinx.android.synthetic.main.fragment_about.*
import kotlinx.android.synthetic.main.layout_titlebar.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultVerticalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import org.jetbrains.anko.imageResource

/**
 * Created by gejw on 2017/6/13.
 */

class AboutFragment: SupportFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_about, null)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigationRightButton.visibility = View.VISIBLE
        navigationRightButton.imageResource = R.drawable.navigation_close
        navigationRightButton.setOnClickListener { pop() }

        versionTextView.text = "V ${context.packageManager.getPackageInfo(context.packageName, 0).versionName}"
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultVerticalAnimator()
    }

}