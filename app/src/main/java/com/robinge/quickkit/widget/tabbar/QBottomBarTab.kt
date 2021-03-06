package com.robinge.quickkit.widget.tabbar

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.zoombin.greentown.R
import java.lang.Exception

/**
 * Created by gejw on 2017/10/2.
 */
public class QBottomBarTab(context: Context,
                           attrs: AttributeSet?,
                           defStyleAttr: Int,
                           icon: Int,
                           selectedIcon: Int,
                           titleColor: Int,
                           selectedColor: Int,
                           title: CharSequence) : FrameLayout(context, attrs, defStyleAttr) {

    private var mIcon: ImageView? = null
    private var mTvTitle: TextView? = null
    private var mContext: Context? = null

    private var icon = icon
    private var selectedIcon = selectedIcon
    private var titleColor = titleColor
    private var selectedColor = selectedColor

    var tabPosition = -1
        set(position) {
            field = position
            if (position == 0) {
                isSelected = true
            }
        }

    private var mTvUnreadCount: TextView? = null

    /**
     * 获取当前未读数量
     */
    /**
     * 设置未读数量
     */
    var unreadCount: Int
        get() {
            var count = 0
            if (TextUtils.isEmpty(mTvUnreadCount?.text)) {
                return count
            }
            if (mTvUnreadCount?.text.toString() == "99+") {
                return 99
            }
            try {
                count = Integer.valueOf(mTvUnreadCount?.text.toString())
            } catch (ignored: Exception) {
            }

            return count
        }
        set(num) = if (num <= 0) {
            mTvUnreadCount?.text = 0.toString()
            mTvUnreadCount?.visibility = View.GONE
        } else {
            mTvUnreadCount?.visibility = View.VISIBLE
            if (num > 99) {
                mTvUnreadCount?.text = "99+"
            } else {
                mTvUnreadCount?.text = num.toString()
            }
        }

    constructor(context: Context,
                @DrawableRes icon: Int,
                @DrawableRes selectedIcon: Int,
                titleColor: Int,
                selectedColor: Int,
                title: CharSequence) : this(context, null, icon, selectedIcon, titleColor, selectedColor, title)

    constructor(context: Context,
                attrs: AttributeSet?,
                icon: Int,
                selectedIcon: Int,
                titleColor: Int,
                selectedColor: Int,
                title: CharSequence) : this(context, attrs, 0, icon, selectedIcon, titleColor, selectedColor, title)

    init {
        init(context, icon, title)
    }

    private fun init(context: Context, icon: Int, title: CharSequence) {
        mContext = context
        val typedArray = context.obtainStyledAttributes(intArrayOf(android.R.attr.selectableItemBackgroundBorderless))
        val drawable = typedArray.getDrawable(0)
        setBackgroundDrawable(drawable)
        typedArray.recycle()

        val lLContainer = LinearLayout(context)
        lLContainer.orientation = LinearLayout.VERTICAL
        lLContainer.gravity = Gravity.CENTER
        val paramsContainer = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        paramsContainer.gravity = Gravity.CENTER
        lLContainer.layoutParams = paramsContainer

        mIcon = ImageView(context)
        val size = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 27f, resources.displayMetrics).toInt()
        val params = LinearLayout.LayoutParams(size, size)
        mIcon?.setImageResource(icon)
        mIcon?.layoutParams = params
        lLContainer.addView(mIcon)

        mTvTitle = TextView(context)
        mTvTitle?.text = title
        val paramsTv = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        paramsTv.topMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2f, resources.displayMetrics).toInt()
        mTvTitle?.textSize = 10f
        mTvTitle?.layoutParams = paramsTv
        lLContainer.addView(mTvTitle)

        addView(lLContainer)

        val min = dip2px(context, 20f)
        val padding = dip2px(context, 5f)
        mTvUnreadCount = TextView(context)
        mTvUnreadCount?.setBackgroundResource(R.drawable.q_tabbar_bg_msg_bubble)
        mTvUnreadCount?.minWidth = min
        mTvUnreadCount?.setTextColor(Color.WHITE)
        mTvUnreadCount?.setPadding(padding, 0, padding, 0)
        mTvUnreadCount?.gravity = Gravity.CENTER
        val tvUnReadParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, min)
        tvUnReadParams.gravity = Gravity.CENTER
        tvUnReadParams.leftMargin = dip2px(context, 17f)
        tvUnReadParams.bottomMargin = dip2px(context, 14f)
        mTvUnreadCount?.layoutParams = tvUnReadParams
        mTvUnreadCount?.visibility = View.GONE

        addView(mTvUnreadCount)
    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        if (selected) {
            mIcon?.setImageResource(selectedIcon)
            mTvTitle?.setTextColor(ContextCompat.getColor(mContext!!, selectedColor))
        } else {
            mIcon?.setImageResource(icon)
            mTvTitle?.setTextColor(ContextCompat.getColor(mContext!!, titleColor))
        }
    }

    private fun dip2px(context: Context, dp: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics).toInt()
    }
}
