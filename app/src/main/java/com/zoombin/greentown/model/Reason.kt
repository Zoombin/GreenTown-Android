package com.zoombin.greentown.model

import com.alibaba.fastjson.JSON
import com.zoombin.greentown.net.Net
import org.json.JSONObject

/**
 * Created by gejw on 2017/6/10.
 */

class Reason : Any() {

    // 理由ID
    var reason_id = 0
    // 鞭策或奖励的理由
    var reason = ""
    // 奖励内容
    var value = ""

    companion object {

        // 鼓舞，鞭策理由下拉列表
        fun inspireReason(success: (List<Reason>) -> Unit,
                          failure: (String?) -> Unit) {
            val map = HashMap<String, Any>()
            map.put("type", 1)
            Net.post("user/inspire_or_spur_content", map, { json ->
                val reasons = JSON.parseArray(JSONObject(json).getString("data").toString(), Reason::class.java)
                success(reasons)
            }, failure)
        }

        // 鼓舞，鞭策理由下拉列表
        fun spurReason(success: (List<Reason>) -> Unit,
                          failure: (String?) -> Unit) {
            val map = HashMap<String, Any>()
            map.put("type", 2)
            Net.post("user/inspire_or_spur_content", map, { json ->
                val reasons = JSON.parseArray(JSONObject(json).getString("data").toString(), Reason::class.java)
                success(reasons)
            }, failure)
        }

    }

}