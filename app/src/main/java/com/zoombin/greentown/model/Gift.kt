package com.zoombin.greentown.model

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
    var is_picked = 0
    // 开始时间
    var gift_start = ""
    // 结束时间
    var gift_end = ""
    
}