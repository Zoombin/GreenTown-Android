package com.zoombin.greentown.model

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
    var is_picked = 0
    // 0:未完成 1：已完成
    var is_finished = 0
    
}