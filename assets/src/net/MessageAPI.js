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
        
        // 给镇长留言
        messageToMayor: function(user_id, msg_content, callback) {
            API.post("chat/msg_to_mayor", {
                "user_id": user_id,
                "msg_content": msg_content
            }, callback);
        },
        
        // 发送工会消息
        sendDepartmentMessage: function(user_id, msg_content, callback) {
            API.post("chat/send_department_msg", {
                "user_id": user_id,
                "msg_content": msg_content
            }, callback);
        },
        
        // 工会消息
        departmentMessages: function(user_id, callback) {
            API.get("chat/department_msg", null, callback);
        },
        
        // 事件消息
        eventMessages: function(user_id, callback) {
            API.get("chat/event_msg", {
                "user_id": user_id
            }, callback);
        },
        
        // 公告消息
        announcementMessages: function(callback) {
            API.get("chat/announcement_msg", null, callback);
        },
        
    },


    // use this for initialization
    onLoad: function () {

    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
