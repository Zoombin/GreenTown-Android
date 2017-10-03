package com.zoombin.greentown.ui.fragment.message

import android.os.Bundle
import android.view.View
import com.zoombin.greentown.R
import com.zoombin.greentown.ui.fragment.common.BaseBackFragment
import com.zoombin.greentown.ui.fragment.common.BaseFragment

/**
 * Created by gejw on 2017/10/3.
 */

class MessageFragment: BaseBackFragment() {

    override fun layoutId(): Int {
        return R.layout.fragment_message
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title = "留言"
    }

}