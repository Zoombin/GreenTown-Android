package com.zoombin.greentown.model

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.annotation.JSONField
import com.zoombin.greentown.net.Net
import org.json.JSONObject

/**
 * Created by gejw on 2017/6/10.
 */

class Gift : Any() {

    // 礼包id
    var gift_id = 0
    // 礼包名称
    var gift_name = ""
    // 绿币
    var coins = 0
    // 金币
    var golds = 0
    // 成就点
    var points = 0
    // 是否已认领 0：未认领  1：已认领
    @JSONField(name="is_picked")
    var picked = 0
    // 开始时间
    var gift_start = ""
    // 结束时间
    var gift_end = ""

    companion object {

        // 礼包列表
        fun gifts(success: (List<Gift>) -> Unit,
                        failure: (String?) -> Unit) {
            val map = HashMap<String, Any>()
            if (User.current()?.user_id != null)
                map.put("user_id", User.current()!!.user_id)
            Net.get("gift/gift", map, { json ->
                val gifts = JSON.parseArray(JSONObject(json).getString("data").toString(), Gift::class.java)
                success(gifts)
            }, failure)
        }

    }

    // 领取礼包
    fun pickGift(success: () -> Unit,
                 failure: (String?) -> Unit) {
        val map = HashMap<String, Any>()
        if (User.current()?.user_id != null)
            map.put("user_id", User.current()!!.user_id)
        map.put("gift_id", gift_id)
        Net.post("gift/pick_gift", map, { json ->
            success()
        }, failure)
    }
    
}