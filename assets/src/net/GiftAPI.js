const API = require("API");

cc.Class({
    extends: cc.Component,

    properties: {
        // foo: {
        //    default: null,      // The default value will be used only when the component attaching
        //                           to a node for the first time
        //    url: cc.Texture2D,  // optional, default is typeof default
        //    serializable: true, // optional, default is true
        //    visible: true,      // optional, default is true
        //    displayName: 'Foo', // optional
        //    readonly: false,    // optional, default is false
        // },
        // ...
    },
    
    statics: {
        
        // 节日礼包列表
        festivalGift: function(user_id, callback) {
            API.get("gift/festival_gift", {
                "user_id": user_id
            }, callback);
        },
        
        // 每月礼包列表
        monthGift: function(user_id, callback) {
            API.get("gift/monthly_gift", {
                "user_id": user_id
            }, callback);
        },
        
        // 领取节日礼包
        pickFestivalGift: function(user_id, gift_id, callback) {
            API.get("gift/pick_festival_gift", {
                "user_id": user_id,
                "gift_id": gift_id
            }, callback);
        },
        
        // 领取每月礼包
        pickMonethGift: function(user_id, gift_id, callback) {
            API.get("gift/pick_monthly_gift", {
                "user_id": user_id,
                "gift_id": gift_id
            }, callback);
        },
        
    },

    // use this for initialization
    onLoad: function () {

    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
