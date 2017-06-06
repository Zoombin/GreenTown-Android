package com.zoombin.greentown.model

import com.zoombin.greentown.net.Net
import org.json.JSONArray

/**
 * Created by gejw on 2017/6/7.
 */

class User: Any() {

    var user_id = 0

    var user_name = ""

    var fullname = ""

    var title_name = ""

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

    companion object {

        // 请求验证码
        fun requestCode(phone: String,
                        success: () -> Unit,
                        failure: (String?) -> Unit) {
            val map = HashMap<String, Any>()
            map.put("user_name", phone)
            Net.get("user/captcha", map, { json ->
                // 开始解析json
                success()
            }, failure)
        }

        // 登录
        fun login(phone: String,
                  code: String,
                  success: (User) -> Unit,
                  failure: (String) -> Unit) {

        }

        // 退出登录
        fun logout() {

        }

        // 获取当前登录用户
        fun current() : User? {
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
