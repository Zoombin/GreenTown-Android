package com.zoombin.greentown.ui.fragment.me

import android.os.Bundle
import android.view.View
import com.zoombin.greentown.R
import com.zoombin.greentown.ui.fragment.common.BaseBackFragment

/**
 * Created by gejw on 2017/10/3.
 */

class HobbyFragment: BaseBackFragment() {

    override fun layoutId(): Int {
        return R.layout.fragment_hobby
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title = "爱好"
    }
}
