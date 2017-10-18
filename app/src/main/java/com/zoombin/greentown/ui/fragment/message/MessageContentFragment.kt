package com.zoombin.greentown.ui.fragment.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zoombin.greentown.R
import com.zoombin.greentown.model.Message
import com.robinge.quickkit.fragment.QBaseBackFragment
import kotlinx.android.synthetic.main.fragment_messagecontent.*
import org.jetbrains.anko.support.v4.toast

/**
 * Created by gejw on 2017/6/30.
 */

class MessageContentFragment: QBaseBackFragment() {

    companion object {

        fun newInstance(message: Message): MessageContentFragment {
            val fragment = MessageContentFragment()
            val args = Bundle()
            args.putSerializable("message", message)
            fragment.arguments = args
            return fragment
        }

    }

    var message: Message? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        if (args != null) {
            message = args.getSerializable("message") as? Message
        }
    }

    override fun initView() {

        title = "留言回复"

        message?.replayContent({ content ->
            textLabel.text = "我的留言: ${message!!.msg_content}"
            contentLabel.text = "回复: $content"
        }) { message ->
            if (message != null) toast(message)
        }
    }

    override fun layoutId(): Int {
        return R.layout.fragment_messagecontent
    }

}