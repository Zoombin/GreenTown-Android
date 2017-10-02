package com.zoombin.greentown.ui

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import com.zoombin.greentown.BuildConfig
import com.zoombin.greentown.R
import com.zoombin.greentown.model.User
import kotlinx.android.synthetic.main.activity_login.*
import me.yokeyword.fragmentation.SupportActivity
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.DefaultVerticalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import org.jetbrains.anko.toast

/**
 * Created by gejw on 2017/9/25.
 */

class LoginActivity: SupportActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (BuildConfig.DEBUG) {
            usernameEditText.setText("13625286286")
        }

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
                toast("登录成功！")
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }) { if (it != null) toast(it) }
        }
    }

    public override fun onCreateFragmentAnimator(): FragmentAnimator {
        // 设置横向(和安卓4.x动画相同)
        return DefaultVerticalAnimator()
    }

}