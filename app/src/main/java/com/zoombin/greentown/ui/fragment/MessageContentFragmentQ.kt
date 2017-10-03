package com.zoombin.greentown.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zoombin.greentown.R
import com.zoombin.greentown.model.Message
import com.zoombin.greentown.ui.fragment.common.QBaseBackFragment
import kotlinx.android.synthetic.main.fragment_messagecontent.*
import kotlinx.android.synthetic.main.layout_titlebar.*
import org.jetbrains.anko.support.v4.toast

/**
 * Created by gejw on 2017/6/30.
 */

class MessageContentFragmentQ(message: Message): QBaseBackFragment() {

    var message = message

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_messagecontent, null)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleLabel.text = "留言回复"

        message.replayContent({ content ->
            textLabel.text = "我的留言: ${message.msg_content}"
            contentLabel.text = "回复: $content"
        }) { message ->
            if (message != null) toast(message)
        }

    }

    override fun layoutId(): Int {
        return 0
    }

}