package com.zoombin.greentown.ui.fragment.main

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.qiniu.android.storage.UploadManager
import com.zoombin.greentown.R
import com.zoombin.greentown.model.User
import com.zoombin.greentown.ui.fragment.common.BaseFragment
import kotlinx.android.synthetic.main.layout_me_cell.view.*
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.textColor
import me.weyye.hipermission.PermissionCallback
import me.weyye.hipermission.HiPermission
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.zoombin.greentown.ui.fragment.common.BarButtonItem
import com.zoombin.greentown.ui.fragment.common.setRightBarButtonItem
import com.zoombin.greentown.ui.fragment.me.HobbyFragment
import com.zoombin.greentown.ui.fragment.message.MessageFragment
import kotlinx.android.synthetic.main.fragment_main_me.view.*
import me.weyye.hipermission.PermissionItem
import java.io.ByteArrayOutputStream


/**
 * Created by gejw on 2017/9/23.
 */

class MeFragment : BaseFragment() {

    companion object {

        fun newInstance(): MeFragment {
            val args = Bundle()
            val fragment = MeFragment()
            fragment.arguments = args
            return fragment
        }

    }

    class CellItem(var title: String,
                   var value: String = "",
                   var valueColor: Int = Color.DKGRAY,
                   var isShowArrow: Boolean = false)


    val SELECT_PICTURE = 0
    val SELECT_CAMER = 1

    var items = arrayListOf<CellItem>()

    override fun layoutId(): Int {
        return R.layout.fragment_main_me
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        title = "我的"

        setRightBarButtonItem(BarButtonItem("菜单", {
            showMenu()
        }))

        contentView.avatarImageView.setOnClickListener {
            selectImage()
        }

        val layoutManager = GridLayoutManager(context, 1)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        contentView.recyclerView?.layoutManager = layoutManager
        contentView.recyclerView?.adapter = ListAdapter(items) { t ->
            when (t.title) {
                "星座" -> {
                    selectConstellation()
                }
                "爱好" -> {
                    push(HobbyFragment())
                }
                "留言" -> {
                    push(MessageFragment())
                }
            }
        }
        User.current()?.queryUserInfo({
            reloadItems()
        }) {

        }
    }

    fun showMenu() {
        val items = ArrayList<String>()
        items.add("退出登录")
        AlertDialog.Builder(context)
                .setTitle("菜单")
                .setItems(items.toTypedArray(), DialogInterface.OnClickListener { dialog, which ->
                    when(which) {
//                            0 -> { start(AboutFragment()) }
                        0 -> {
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

    fun reloadItems() {
        var user = User.current()
        if (user == null) user = User()

        Glide.with(context).load(user.logo).into(contentView.avatarImageView)
        contentView.nameLabel.text = user.fullname

        items.clear()
        items.add(CellItem(
                user.department_name))
        items.add(CellItem(
                "绿币",
                "${user.coins}",
                resources.getColor(R.color.themeColor)))
        items.add(CellItem(
                "成就点",
                "${user.points}"))
        items.add(CellItem(
                "星座",
                if (user.constellation == null) "请选择" else user.constellation!!,
                Color.DKGRAY,
                true))
        items.add(CellItem(
                "爱好",
                if (user.hobby == null) "请输入" else user.hobby!!,
                Color.DKGRAY,
                true))
        items.add(CellItem(
                "留言",
                "",
                Color.DKGRAY,
                true))

        contentView.recyclerView.adapter.notifyDataSetChanged()

    }

    override fun onResume() {
        super.onResume()
        reloadItems()
    }

    override fun didLogin() {
        super.didLogin()
        reloadItems()
    }

    fun selectConstellation() {
        val items = arrayOf<String>("白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座", "水瓶座", "双鱼座")
        AlertDialog.Builder(context)
                .setTitle("选择星座")
                .setItems(items) { dialog, which ->
                    var user = User.current()
                    User.current()?.updateInfo(items[which], user!!.hobby, {
                        toast("更新成功")
                        reloadItems()
                    }) { message ->
                        if (message != null) toast(message)
                    }
                }
                .create().show()
    }

    fun selectImage() {
        val items = arrayOf<CharSequence>("相册", "相机")
        AlertDialog.Builder(context)
                .setTitle("选择图片来源")
                .setItems(items) { dialog, which ->
                    when (which) {
                        0 -> {
                            val intent = Intent(Intent.ACTION_GET_CONTENT)
                            intent.addCategory(Intent.CATEGORY_OPENABLE)
                            intent.type = "image/*"
                            startActivityForResult(intent, SELECT_PICTURE)
                        }
                        1 -> {
                            val permissions = ArrayList<PermissionItem>()
                            permissions.add(PermissionItem(android.Manifest.permission.CAMERA, getString(R.string.permission_camera), R.drawable.permission_ic_camera))
                            HiPermission.create(context)
                                    .permissions(permissions)
                                    .checkMutiPermission(object : PermissionCallback {
                                        override fun onClose() {
                                            toast("用户关闭权限申请")
                                        }

                                        override fun onFinish() {
                                            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                            startActivityForResult(intent, SELECT_CAMER)
                                        }

                                        override fun onDeny(permission: String, position: Int) {
                                        }

                                        override fun onGuarantee(permission: String, position: Int) {
                                        }
                                    })
                        }
                    }
                }
                .create().show()
    }

    fun uploadImage(bytes: ByteArray) {
        User.current()?.qiniuToken({ key, token ->
            val uploadManager = UploadManager()
            uploadManager.put(bytes, key, token, { key, info, response ->
                User.current()?.uploadAvatar(key, {
                    toast("上传成功")
                    reloadItems()
                }) { message ->
                    if (message != null) toast(message)
                }
            }, null)
        }) { message ->
            if (message != null) toast(message)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            val uri = data?.data
            var bitmap: Bitmap?
            if (uri == null) {
                val bundle = data?.extras
                bitmap = bundle?.get("data") as? Bitmap
            } else {
                bitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri))
            }
            if (bitmap == null) {
                toast("异常错误！")
                return
            }
            var baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos)
            val bytes = baos.toByteArray()
            bitmap.recycle()
            uploadImage(bytes)
        }
    }

    class ListAdapter(val items: ArrayList<CellItem>, val listener: (CellItem) -> Unit) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
            return ViewHolder(View.inflate(parent.context, R.layout.layout_me_cell, null))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(items[position], position, listener)
        }

        override fun getItemCount() = items.size

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bind(item: CellItem, position: Int, listener: (CellItem) -> Unit) = with(itemView) {
                setOnClickListener { listener(item) }

                titleLabel.text = item.title
                valueLabel.text = item.value
                valueLabel.textColor = item.valueColor
                arrowImageView.visibility = if (item.isShowArrow) View.VISIBLE else View.GONE
            }

        }
    }

}