package com.zoombin.greentown.model

import android.content.Intent
import android.os.Parcelable
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.annotation.JSONField
import com.zoombin.greentown.GTApplication
import com.zoombin.greentown.R
import com.zoombin.greentown.net.Net
import org.json.JSONArray
import org.json.JSONObject
import java.io.Serializable

/**
 * Created by gejw on 2017/6/7.
 */

class User: Any(), Serializable {

    var user_id = 0

    var user_name = ""

    var fullname = ""

    var title_name = ""

    var logo = ""
    //  绿币
    var coins = 0
    // 成就点
    var points = 0

    var department_id = 0

    var department_name = ""

    var position_id = 0

    var position_name = ""

    var gender = ""

    var nickname = ""

    var constellation: String? = ""

    var hobby: String? = ""

    var role_id = 0

    var reward = ""

    fun avatar(): Int {
        return if (role_id >= 3) R.drawable.female else R.drawable.male
    }

    fun save() {
        val json = JSON.toJSONString(this)
        val edit = GTApplication.context?.getSharedPreferences("user", 0)?.edit()
        edit?.putString("user", json)
        edit?.commit()
        GTApplication.context?.sendBroadcast(Intent("user_update"))
    }

    companion object {

        // 退出登录
        fun logout() {
            val edit = GTApplication.context?.getSharedPreferences("user", 0)?.edit()
            edit?.remove("user")
            edit?.commit()
            GTApplication.context?.sendBroadcast(Intent("logout"))
        }

        // 获取当前登录用户
        fun current() : User? {
            val json = GTApplication.context?.getSharedPreferences("user", 0)?.getString("user", "")
            val user = JSON.parseObject(json, User::class.java)
            if (user != null) return user
            return null
        }

        // 请求验证码
        fun requestCode(phone: String,
                        success: () -> Unit,
                        failure: (String?) -> Unit) {
            val map = HashMap<String, Any>()
            map.put("user_name", phone)
            Net.get("user/captcha", map, { json ->
                success()
            }, failure)
        }

        // 登录
        fun login(phone: String,
                  code: String,
                  success: (User) -> Unit,
                  failure: (String?) -> Unit) {
            val map = HashMap<String, Any>()
            map.put("user_name", phone)
            map.put("captcha", code)
            Net.post("user/login", map, { json ->
                val user = JSON.parseObject(JSONObject(json).getString("data").toString(), User::class.java)
                user.save()
                user.statistics()
                success(user)
            }, failure)
        }

        // 成就排行
        fun pointsRank(page: Int,
                       month: Int,
                       success: (List<User>) -> Unit,
                       failure: (String?) -> Unit) {
            val map = HashMap<String, Any>()
            map.put("START", page)
            map.put("monthBack", month)
            Net.get("user/points_ranking", map, { json ->
                val users = JSON.parseArray(JSONObject(json).getString("data").toString(), User::class.java)
                success(users)
            }, failure)
        }

        // 绿币排行
        fun coinsRank(page: Int,
                      month: Int,
                      success: (List<User>) -> Unit,
                      failure: (String?) -> Unit) {
            val map = HashMap<String, Any>()
            map.put("START", page)
            map.put("monthBack", month)
            Net.get("user/coins_ranking", map, { json ->
                val users = JSON.parseArray(JSONObject(json).getString("data").toString(), User::class.java)
                success(users)
            }, failure)
        }

        // 所有成员
        fun allUsers(success: (List<User>) -> Unit,
                     failure: (String?) -> Unit) {
            val map = HashMap<String, Any>()
            Net.get("user/group_list", map, { json ->
                val users = JSON.parseArray(JSONObject(json).getString("data").toString(), User::class.java)
                success(users)
            }, failure)
        }

    }

    // 完善信息
    fun completeProfile(gender: String? = null,
                        roleId: String? = null,
                        fullName: String? = null,
                        nickName: String? = null,
                        departmentId: Int? = null,
                        positionId: Int? = null,
                        success: () -> Unit,
                 failure: (String?) -> Unit) {
        val map = HashMap<String, Any>()
        if (User.current()?.user_id != null)
            map.put("user_id", User.current()!!.user_id)
        if (gender != null)
            map.put("gender", gender)
        if (roleId != null)
            map.put("role_id", roleId)
        if (fullName != null)
            map.put("fullname", fullName)
        if (nickName != null)
            map.put("nickname", nickName)
        if (departmentId != null)
            map.put("department_id", departmentId)
        if (positionId != null)
            map.put("position_id", positionId)
        Net.post("user/complete_profile", map, { json ->
            success()
        }, failure)
    }

    // 鞭策
    fun spur(reasonId: Int,
             reasonDesc: String,
             success: () -> Unit,
             failure: (String?) -> Unit) {
        val map = HashMap<String, Any>()
        map.put("reason_id", reasonId)
        map.put("targetUserId", user_id)
        map.put("user_id", User.current()!!.user_id)
        map.put("reasonDesc", reasonDesc)
        Net.post("user/spur", map, { json ->
            success()
        }, failure)
    }

    // 鼓舞
    fun inspire(reasonId: Int,
                reasonDesc: String,
                success: () -> Unit,
                failure: (String?) -> Unit) {
        val map = HashMap<String, Any>()
        map.put("reason_id", reasonId)
        map.put("targetUserId", user_id)
        map.put("user_id", User.current()!!.user_id)
        map.put("reasonDesc", reasonDesc)
        Net.post("user/inspire", map, { json ->
            success()
        }, failure)
    }

    // 获取绿币
    fun queryUserInfo(success: () -> Unit,
                      failure: (String?) -> Unit) {
        val map = HashMap<String, Any>()
        map.put("userId", user_id)
        Net.get("user/getUserInfo", map, { json ->
            val user = JSON.parseObject(JSONObject(json).getString("data").toString(), User::class.java)
            user.save()
            success()
        }, failure)
    }

    // 七牛上传token
    fun qiniuToken(success: (String, String) -> Unit,
                   failure: (String?) -> Unit) {
        val key = "${System.currentTimeMillis()}_$user_id"
        val map = HashMap<String, Any>()
        map.put("key", key)
        Net.get("qiniu/uptoken", map, { json ->
            val token = JSONObject(json).getJSONObject("data").getString("token")
            success(key, token)
        }, failure)
    }

    // 上传头像
    fun uploadAvatar(avatar: String,
                     success: () -> Unit,
                     failure: (String?) -> Unit) {
        val map = HashMap<String, Any>()
        map.put("userId", user_id)
        map.put("logo", avatar)
        Net.post("user/updateUserLogo", map, { json ->
            queryUserInfo(success, failure)
        }, failure)
    }

    // 完善信息
    fun updateInfo(constellation: String? = null,
                   hobby: String? = null,
                   success: () -> Unit,
                   failure: (String?) -> Unit) {
        val map = HashMap<String, Any>()
        if (User.current()?.user_id != null)
            map.put("userId", User.current()!!.user_id)
        if (constellation != null)
            map.put("constellation", constellation)
        if (hobby != null)
            map.put("hobby", hobby)
        Net.post("user/updateUserInfo", map, { json ->
            val user = JSON.parseObject(JSONObject(json).getString("data").toString(), User::class.java)
            user.save()
            success()
        }, failure)
    }

    // 完善信息
    fun statistics() {
        val map = HashMap<String, Any>()
        if (User.current()?.user_id != null)
            map.put("userId", User.current()!!.user_id)
        Net.post("userOpenApp/addRecord", map, { json ->
        }, {

        })
    }

}
