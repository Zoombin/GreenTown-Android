package com.zoombin.greentown.model

import com.alibaba.fastjson.JSON
import com.zoombin.greentown.GTApplication
import com.zoombin.greentown.net.Net
import org.json.JSONArray
import org.json.JSONObject

/**
 * Created by gejw on 2017/6/7.
 */

class User: Any() {

    var user_id = 0

    var user_name = ""

    var fullname = ""

    var title_name = ""

    var logo = ""

    var coins = 0

    var golds = 0

    var points = 0

    var department_id = 0

    var department_name = ""

    var position_id = 0

    var position_name = ""

    var gender = ""

    var nickname = ""

    var role_id = 0

    private fun save() {
        val json = JSON.toJSONString(this)
        val edit = GTApplication.context?.getSharedPreferences("user", 0)?.edit()
        edit?.putString("user", json)
        edit?.commit()
    }

    companion object {

        // 退出登录
        fun logout() {
            val edit = GTApplication.context?.getSharedPreferences("user", 0)?.edit()
            edit?.remove("user")
            edit?.commit()
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
                success(user)
            }, failure)
        }

        // 成就排行
        fun pointsRank(page: Int,
                       success: (List<User>) -> Unit,
                       failure: (String?) -> Unit) {
            val map = HashMap<String, Any>()
            map.put("START", page)
            map.put("PAGESIZE", 20)
            Net.get("user/points_ranking", map, { json ->
                val users = JSON.parseArray(JSONObject(json).getString("data").toString(), User::class.java)
                success(users)
            }, failure)
        }

        // 绿币排行
        fun coinsRank(page: Int,
                      success: (List<User>) -> Unit,
                      failure: (String?) -> Unit) {
            val map = HashMap<String, Any>()
            map.put("START", page)
            map.put("PAGESIZE", 20)
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
                        departmentId: String? = null,
                        positionId: String? = null,
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
        Net.get("user/complete_profile", map, { json ->
            success()
        }, failure)
    }

    // 鞭策
    fun spur(reasonId: Int,
             success: () -> Unit,
             failure: (String?) -> Unit) {
        val map = HashMap<String, Any>()
        map.put("reason_id", reasonId)
        map.put("user_id", user_id)
        Net.get("user/spur", map, { json ->
            success()
        }, failure)
    }

    // 鼓舞
    fun inspire(reasonId: Int,
                success: () -> Unit,
                failure: (String?) -> Unit) {
        val map = HashMap<String, Any>()
        map.put("reason_id", reasonId)
        map.put("user_id", user_id)
        Net.get("user/inspire", map, { json ->
            success()
        }, failure)
    }

    // 获取绿币
    fun queryUserPoint(success: () -> Unit,
                       failure: (String?) -> Unit) {
        val map = HashMap<String, Any>()
        Net.get("queryUserPoint", map, { json ->
            success()
        }, failure)
    }

}
