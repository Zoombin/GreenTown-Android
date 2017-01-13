const API = require("API");

cc.Class({
    extends: cc.Component,

    properties: {
        
    },
    
    statics: {
        
        // 工会列表
        departmentList: function(callback) {
            API.get("common/department_list", null, callback);
        },
        
        // 职位列表
        positionList: function(department_id, callback) {
            API.get("common/position_list", {
                "department_id": department_id
            }, callback);
        },
        
        // 工会成员列表
        departmentUsers: function(department_id, callback) {
            API.get("user/group_list", {
                "department_id": department_id
            }, callback);
        },
        
        // 所有成员列表
        allUsers: function(department_id, callback) {
            API.get("user/group_list", null, callback);
        },
        
    },

    // use this for initialization
    onLoad: function () {

    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
