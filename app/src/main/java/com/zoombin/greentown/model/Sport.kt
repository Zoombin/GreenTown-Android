package com.zoombin.greentown.model

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
    // 已报名人数
    var cur_quantity = 0
    // 总人数
    var quantity = 0
    // 头像
    var logo = ""
    // 0:未参加 1：已参加
    var is_joined = 0
    
}