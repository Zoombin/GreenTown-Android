package com.zoombin.greentown.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.zoombin.greentown.R
import com.zoombin.greentown.model.Message
import kotlinx.android.synthetic.main.fragment_levelmessage.*
import kotlinx.android.synthetic.main.layout_titlebar.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultVerticalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import org.jetbrains.anko.support.v4.toast

/**
 * Created by gejw on 2017/6/9.
 */

class LevelMessageFragment : BaseBackFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_levelmessage, null)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleLabel.text = "我要留言"

        sendButton.setOnClickListener {
            val message = messageEditText.text.toString().trim()
            if (message == "") {
                toast("请输入内容")
                return@setOnClickListener
            }

            Message.levelMessage(message, {
                toast("发送成功")
                pop()
            }) { message ->
                if (message != null) toast(message)
            }
        }
    }

}