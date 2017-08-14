package com.zoombin.greentown.model

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.annotation.JSONField
import com.zoombin.greentown.net.Net
import org.json.JSONObject

/**
 * Created by gejw on 2017/6/10.
 */

class Sport : Any() {

    // id
    var sport_id = 0
    // 名称
    var title = ""
    // 项目介绍
    var sub_title = ""
    // 规则
    var rule = ""
    // 总奖金
    var total_reward = 0
    // 报名费
    var fee = 0
    // 已报名人数
    var cur_quantity = 0
    // 总人数
    var quantity = 0
    // 头像
    var logo = ""
    // 0:未参加 1：已参加
    @JSONField(name="is_joined")
    var joined = 0

    companion object {

        // 活动列表
        fun sports(success: (List<Sport>) -> Unit,
                   failure: (String?) -> Unit) {
            val map = HashMap<String, Any>()
            if (User.current()?.user_id != null)
                map.put("user_id", User.current()!!.user_id)
            Net.get("sports/sportsList", map, { json ->
                val sports = JSON.parseArray(JSONObject(json).getString("data").toString(), Sport::class.java)
                success(sports)
            }, failure)
        }

        // 奖池
        fun pool(success: (Int) -> Unit,
                 failure: (String?) -> Unit) {
            val map = HashMap<String, Any>()
            Net.get("sports/pool", map, { json ->
                success(JSONObject(json).getJSONObject("data").getInt("coins"))
            }, failure)
        }

    }

    // 参赛选手
    fun players(success: (List<User>) -> Unit,
                failure: (String?) -> Unit) {
        val map = HashMap<String, Any>()
        map.put("sport_id", sport_id)
        Net.get("sports/playerList", map, { json ->
            val users = JSON.parseArray(JSONObject(json).getString("data").toString(), User::class.java)
            success(users)
        }, failure)
    }

    // 排行
    fun rank(success: (List<String>) -> Unit,
             failure: (String?) -> Unit) {
        val map = HashMap<String, Any>()
        map.put("sport_id", sport_id)
        Net.get("sports/sport_rank", map, { json ->
            val users = ArrayList<String>()
            val data = JSONObject(json).getJSONObject("data")
            users.add(data.getString("fullname_st"))
            users.add(data.getString("fullname_nd"))
            users.add(data.getString("fullname_rd"))
            success(users)
        }, failure)
    }

    // 规则
    fun rule(success: (String) -> Unit,
             failure: (String?) -> Unit) {
        val map = HashMap<String, Any>()
        map.put("sport_id", sport_id)
        Net.get("sports/sport_rule", map, { json ->
            success(JSONObject(json).getJSONObject("data").getString("rule"))
        }, failure)
    }

    // 参赛选手
    fun rewardList(success: (List<User>) -> Unit,
                   failure: (String?) -> Unit) {
        val map = HashMap<String, Any>()
        map.put("sport_id", sport_id)
        Net.get("sports/rewardList", map, { json ->
            val users = JSON.parseArray(JSONObject(json).getString("data").toString(), User::class.java)
            success(users)
        }, failure)
    }

    // 报名
    fun enroll(success: () -> Unit,
               failure: (String?) -> Unit) {
        val map = HashMap<String, Any>()
        if (User.current()?.user_id != null)
            map.put("user_id", User.current()!!.user_id)
        map.put("sport_id", sport_id)
        Net.post("sports/joinSport", map, { json ->
            success()
        }, failure)
    }

}