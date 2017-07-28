package com.zoombin.greentown.ui.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.qiniu.android.http.ResponseInfo
import com.qiniu.android.storage.UpCompletionHandler
import com.qiniu.android.storage.UploadManager
import com.zoombin.greentown.R
import com.zoombin.greentown.model.User
import com.zoombin.greentown.utils.GlideCircleTransform
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.android.synthetic.main.layout_sport_cell.view.*
import kotlinx.android.synthetic.main.layout_titlebar.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultVerticalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import org.jetbrains.anko.image
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.support.v4.toast
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File

/**
 * Created by gejw on 2017/6/7.
 */

class UserFragment : SupportFragment() {

    val SELECT_PICTURE = 0
    val SELECT_CAMER = 1

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_user, null)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigationRightButton.visibility = View.VISIBLE
        navigationRightButton.imageResource = R.drawable.navigation_close
        navigationRightButton.setOnClickListener { pop() }
        uploadButton.setOnClickListener { selectImage() }

        loadUI()
        load()
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultVerticalAnimator()
    }

    fun load() {
        User.current()?.queryUserInfo({
            loadUI()
        }) {

        }
        submitButton.setOnClickListener {
            User.current()?.updateInfo(constellationEditText.text.toString(), hobbyEditText.text.toString(), {
                toast("更新成功")
            }) { message ->
                if (message != null) toast(message)
            }
        }
    }

    fun loadUI() {
        val user = User.current()
        if (user != null) {
            if (avatarImageView.image == null) {
                avatarImageView.imageResource = user.avatar()
            }

            Glide.with(context).load(user.logo).into(avatarImageView)
            nameTextView.text = user.fullname
            goalTextView.text = "绿币：${user.coins}"
            integralTextView.text = "成就点：${user.points}"
            positionTextView.text = "${user.department_name} · ${user.position_name}"
            constellationEditText.setText(user.constellation)
            hobbyEditText.setText(user.hobby)
        }
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
                            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            startActivityForResult(intent, SELECT_CAMER)
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
                    load()
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
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val bytes = baos.toByteArray()
            bitmap.recycle()
            uploadImage(bytes)
        }
    }

}
