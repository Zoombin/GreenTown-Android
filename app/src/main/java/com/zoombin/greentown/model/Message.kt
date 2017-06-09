package com.zoombin.greentown.model

import com.alibaba.fastjson.JSON
import com.zoombin.greentown.net.Net
import org.json.JSONObject

/**
 * Created by gejw on 2017/6/10.
 */

class Message : Any() {

    var department_name = ""

    var msg_content = ""

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