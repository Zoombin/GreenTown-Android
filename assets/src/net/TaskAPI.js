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
        
        // 每周任务
        weekTasks: function(user_id, callback) {
            API.get("task/task_list", {
                "user_id": user_id,
                "type": "1"
            }, callback);
        },
        
        // 随机任务
        randomTasks: function(user_id, callback) {
            API.get("task/task_list", {
                "user_id": user_id,
                "type": "2"
            }, callback);
        },
        
        // 领取任务
        pickTask: function(task_id, user_id, callback) {
            API.get("task/pick_task", {
                "user_id": user_id,
                "task_id": task_id
            }, callback);
        },
        
        // 完成任务
        finishTask: function(task_id, user_id, callback) {
            API.get("task/finish_task", {
                "user_id": user_id,
                "task_id": task_id
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
