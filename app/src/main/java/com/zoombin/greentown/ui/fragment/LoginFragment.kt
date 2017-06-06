package com.zoombin.greentown.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zoombin.greentown.R
import com.zoombin.greentown.model.User
import kotlinx.android.synthetic.main.fragment_login.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultVerticalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import org.jetbrains.anko.support.v4.toast

/**
 * Created by gejw on 2017/6/7.
 */

class LoginFragment : SupportFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_login, null)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        codeButton.setOnClickListener {
            val phone = usernameEditText.text.toString().trim()

            if (phone.equals("")) {
                toast("请输入手机号")
                return@setOnClickListener
            }
            User.requestCode(phone, {
                toast("验证码发送成功！请查收短信！")
            }) { if (it != null) toast(it) }
        }

        loginButton.setOnClickListener {
            val phone = usernameEditText.text.toString().trim()
            var code = codeEditText.text.toString().trim()

            if (phone.equals("")) {
                toast("请输入手机号")
                return@setOnClickListener
            }

            if (code.equals("")) {
                toast("请输入验证码")
                return@setOnClickListener
            }
            User.login(phone, code, {

            }) { if (it != null) toast(it) }
        }

    }

    override fun onBackPressedSupport(): Boolean {
        System.exit(0)
        return true
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultVerticalAnimator()
    }

}