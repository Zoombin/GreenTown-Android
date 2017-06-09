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

        fun parse(json: String) : User {
            val user = User()
            return user
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

        // 保存
        fun save() {

        }

        // 清空
        fun clear() {

        }

    }

}
