package com.zoombin.greentown.model

import com.alibaba.fastjson.JSON
import com.zoombin.greentown.net.Net
import org.json.JSONObject

/**
 * Created by gejw on 2017/6/10.
 */

class Department : Any() {

    // 公会id
    var department_id = 0
    // 公会名称
    var department_name = ""

    companion object {

        // 职位列表
        fun departments(success: (List<Department>) -> Unit,
                        failure: (String?) -> Unit) {
            val map = HashMap<String, Any>()
            Net.get("common/department_list", map, { json ->
                val departments = JSON.parseArray(JSONObject(json).getString("data").toString(), Department::class.java)
                success(departments)
            }, failure)
        }

    }

    // 职位列表
    fun positions(success: (List<Position>) -> Unit,
                  failure: (String?) -> Unit) {
        val map = HashMap<String, Any>()
        map.put("department_id", department_id)
        Net.get("common/position_list", map, { json ->
            val positions = JSON.parseArray(JSONObject(json).getString("data").toString(), Position::class.java)
            success(positions)
        }, failure)
    }

    // 成员
    fun users(success: (List<User>) -> Unit,
              failure: (String?) -> Unit) {
        val map = HashMap<String, Any>()
        map.put("department_id", department_id)
        Net.get("user/group_list", map, { json ->
            val users = JSON.parseArray(JSONObject(json).getString("data").toString(), User::class.java)
            success(users)
        }, failure)
    }

}