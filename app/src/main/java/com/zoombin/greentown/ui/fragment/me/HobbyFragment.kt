package com.zoombin.greentown.ui.fragment.me

import android.os.Bundle
import android.view.View
import com.zoombin.greentown.R
import com.zoombin.greentown.model.User
import com.zoombin.greentown.ui.fragment.common.QBaseBackFragment
import kotlinx.android.synthetic.main.fragment_hobby.view.*
import org.jetbrains.anko.support.v4.toast

/**
 * Created by gejw on 2017/10/3.
 */

class HobbyFragment : QBaseBackFragment() {

    override fun layoutId(): Int {
        return R.layout.fragment_hobby
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title = "爱好"

        contentView.editText.setText(User.current()?.hobby)
        contentView.saveButton.setOnClickListener {
            didClickSaveButton()
        }
    }

    fun didClickSaveButton() {
        val content = contentView.editText.text.toString()
        if (content == "") {
            toast("请输入爱好")
            return
        }

        val user = User.current()

        User.current()?.updateInfo(user!!.constellation, content, {
            toast("更新成功")
            pop()
        }) { message ->
            if (message != null) toast(message)
        }
    }

}
