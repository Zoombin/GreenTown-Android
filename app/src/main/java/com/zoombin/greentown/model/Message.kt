package com.zoombin.greentown.model

import com.alibaba.fastjson.JSON
import com.zoombin.greentown.net.Net
import org.json.JSONObject

/**
 * Created by gejw on 2017/6/10.
 */

class Message : Any() {

    var logo: String? = null

    var department_name: String? = null

    var msg_id = 0

    var msg_content = ""
    //1：公告    2：事件   3：工会    4：系统
    var msg_type = 1

    var haveResponse = false

    companion object {

        // 留言
        fun levelMessage(message: String,
                         success: () -> Unit,
                         failure: (String?) -> Unit) {
            val map = HashMap<String, Any>()
            if (User.current()?.user_id != null)
                map.put("user_id", User.current()!!.user_id)
            map.put("msg_content", message)
            Net.post("chat/msg_to_mayor", map, { json ->
                success()
            }, failure)
        }

        fun allMessages(success: (List<Message>) -> Unit,
                          failure: (String?) -> Unit) {
            val map = HashMap<String, Any>()
            if (User.current()?.user_id != null)
                map.put("userId", User.current()!!.user_id)
            Net.get("chat/allMessages", map, { json ->
                val messages = JSON.parseArray(JSONObject(json).getString("data").toString(), Message::class.java)
                success(messages)
            }, failure)
        }

        // 工会消息
        fun departmentMessages(success: (List<Message>) -> Unit,
                               failure: (String?) -> Unit) {
            val map = HashMap<String, Any>()
            Net.get("chat/department_msg", map, { json ->
                val messages = JSON.parseArray(JSONObject(json).getString("data").toString(), Message::class.java)
                success(messages)
            }, failure)
        }

        // 事件消息
        fun eventMessages(success: (List<Message>) -> Unit,
                          failure: (String?) -> Unit) {
            val map = HashMap<String, Any>()
            if (User.current()?.user_id != null)
                map.put("user_id", User.current()!!.user_id)
            Net.get("chat/event_msg", map, { json ->
                val messages = JSON.parseArray(JSONObject(json).getString("data").toString(), Message::class.java)
                success(messages)
            }, failure)
        }

        // 公告消息
        fun announcementMessages(success: (List<Message>) -> Unit,
                                 failure: (String?) -> Unit) {
            val map = HashMap<String, Any>()
            Net.get("chat/announcement_msg", map, { json ->
                val messages = JSON.parseArray(JSONObject(json).getString("data").toString(), Message::class.java)
                success(messages)
            }, failure)
        }

    }
    
}