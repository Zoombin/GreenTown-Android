package com.zoombin.greentown.ui.fragment

import android.app.AlertDialog
import android.content.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zoombin.greentown.R
import com.zoombin.greentown.model.User
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.layout_titlebar.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultNoAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import org.jetbrains.anko.imageResource

class MainFragment : SupportFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_main, null)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigationTitleImageView.visibility = View.VISIBLE
        navigationRightButton.visibility = View.VISIBLE
        navigationRightButton.imageResource = R.drawable.navigation_menu

        navigationRightButton.setOnClickListener {
            val items = ArrayList<String>()
            items.add("关于")
            items.add("退出登录")
            AlertDialog.Builder(context)
                    .setTitle("菜单")
                    .setItems(items.toTypedArray(), DialogInterface.OnClickListener { dialog, which ->
                        when(which) {
                            0 -> { start(AboutFragment()) }
                            1 -> {
                                val dialog = AlertDialog.Builder(context)
                                dialog.setTitle("确认退出？")
                                dialog.setPositiveButton("确定") { dialog, which ->
                                    // 退出登录
                                    User.logout()
                                }
                                dialog.setNegativeButton("取消", null)
                                dialog.show()
                            }
                        }
                    })
                    .show()
        }

//        messageButton.setOnClickListener { start(MessageFragmentQ()) }
//
//        homeButton.setOnClickListener { start(HomeFragmentQ()) }
//
//        guildButton.setOnClickListener { start(GuildFragmentQ()) }
//
//        sportButton.setOnClickListener { start(SportFragmentQ()) }

//        rankButton.setOnClickListener { start(RankFragment()) }

        if (User.current() == null)
            start(LoginFragment())
        else {
            User.current()?.statistics()
        }

        context.registerReceiver(null, IntentFilter("logout"))
    }

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context : Context?, intent : Intent?) {
            when(intent!!.action){
                "logout" -> { start(LoginFragment()) }
            }
        }
    }

    override fun onPause(){
        super.onPause()
        context.unregisterReceiver(broadcastReceiver)
    }
    override fun onResume(){
        super.onResume()
        context.registerReceiver(broadcastReceiver, IntentFilter("logout"))
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultNoAnimator()
    }

}

