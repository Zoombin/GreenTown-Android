package com.zoombin.greentown.model

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.annotation.JSONField
import com.zoombin.greentown.net.Net
import org.json.JSONObject

/**
 * Created by gejw on 2017/6/10.
 */

class Task : Any() {
    // 任务id
    var task_id = 0
    // 任务名称
    var task_name = ""
    // 金币
    var golds = 0
    // 绿币
    var coins = 0
    // 成就点
    var points = 0
    // 0:未领取 1：已领取
    @JSONField(name="is_picked")
    var picked = 0
    // 0:未完成 1：已完成
    @JSONField(name="is_finished")
    var finished = 0

    companion object {

        fun tasks(success: (List<Task>) -> Unit,
                  failure: (String?) -> Unit) {
            val map = HashMap<String, Any>()
            if (User.current()?.user_id != null)
                map.put("user_id", User.current()!!.user_id)
            Net.get("task/task_list", map, { json ->
                val tasks = JSON.parseArray(JSONObject(json).getString("data").toString(), Task::class.java)
                success(tasks)
            }, failure)
        }

    }

    // 领取任务
    fun pickTask(success: () -> Unit,
              failure: (String?) -> Unit) {
        val map = HashMap<String, Any>()
        if (User.current()?.user_id != null)
            map.put("user_id", User.current()!!.user_id)
        map.put("task_id", task_id)
        Net.get("task/pick_task", map, { json ->
            success()
        }, failure)
    }

    // 完成任务
    fun finishTask(success: () -> Unit,
              failure: (String?) -> Unit) {
        val map = HashMap<String, Any>()
        if (User.current()?.user_id != null)
            map.put("user_id", User.current()!!.user_id)
        map.put("task_id", task_id)
        Net.get("task/finish_task", map, { json ->
            success()
        }, failure)
    }
    
}