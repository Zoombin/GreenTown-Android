package com.zoombin.greentown.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.zoombin.greentown.R
import com.zoombin.greentown.model.Sport
import kotlinx.android.synthetic.main.fragment_sportinfo.*
import kotlinx.android.synthetic.main.layout_titlebar.*
import me.yokeyword.fragmentation.SupportFragment
import org.jetbrains.anko.support.v4.toast

/**
 * Created by gejw on 2017/6/9.
 */

class SportInfoFragment(sport: Sport, pool: String) : BaseBackFragment() {

    var sport = sport
    var pool = pool

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_sportinfo, null)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleLabel.text = sport.title
        poolTextView.text = pool

        ruleButton.setOnClickListener {
            val dialog = AlertDialog.Builder(context)
            dialog.setTitle("参赛规则")
            dialog.setMessage(sport.rule)
            dialog.setPositiveButton("确定", null)
            dialog.show() }

        playerButton.setOnClickListener { start(SportPlayerFragment(sport)) }

        Glide.with(context).load(sport.logo).into(avatarImageView)

        sport.rank({ users ->
            firstNameTextView.text = users[0]
            secondNameTextView.text = users[1]
            thirdNameTextView.text = users[2]
        }) { message ->
            if (message != null) toast(message)
        }
    }

}