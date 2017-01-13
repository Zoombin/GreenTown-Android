var Spinner = require("Spinner");
var DepartmentAPI = require("DepartmentAPI");
var Toast = require("Toast");
var UserAPI = require("UserAPI");

cc.Class({
    extends: cc.Component,

    properties: {
        departmentSpinner: Spinner,
        positionSpinner: Spinner,
    
        department: Object,
        departments: [Object],
        position: Object,
        positions: [Object],
    },
    
    onEnable: function() {
        cc.eventManager.addListener({
            event: cc.EventListener.KEYBOARD,
            onKeyReleased: function(keyCode, event) {
                if (keyCode == cc.KEY.back) {
                    cc.director.end();
                }
            }}, this.node);
    },

    // use this for initialization
    onLoad: function () {
        this.departmentSpinner.numberOfCellCallback = function() {
            return this.departments.length;
        }.bind(this);
        this.departmentSpinner.textOfCellCallback = function(index) {
            return this.departments[index].department_name;
        }.bind(this);
        this.departmentSpinner.cellClickedCallback = function(index) {
            this.selectDepartment(this.departments[index]);
        }.bind(this);
        this.positionSpinner.numberOfCellCallback = function() {
            return this.positions.length;
        }.bind(this);
        this.positionSpinner.textOfCellCallback = function(index) {
            return this.positions[index].position_name;
        }.bind(this);
        this.positionSpinner.cellClickedCallback = function(index) {
            this.position = position;
        }.bind(this);
        
        DepartmentAPI.departmentList(function(msg, data) {
            if (data === null) {
                Toast.show(msg);
                return;
            }
            this.departments = data;
            cc.log("departmentList --", data.length);
            if (data.length > 0) {
                this.selectDepartment(data[0]);
            }
            this.departmentSpinner.reloadData();
        }.bind(this));
    },
    
    selectDepartment: function(department) {
        this.department = department;
        DepartmentAPI.positionList(department.department_id, function(msg, data) {
            if (data === null) {
                Toast.show(msg);
                return;
            }
            this.positions = data;
            cc.log("positionList --", data.length);
            if (data.length > 0) {
                this.position = data[0];
            }
            this.positionSpinner.reloadData();
        }.bind(this));
    },
    
    closeButtonClicked: function() {
        
    },
    
    submitButtonClicked: function() {
        var user_id = UserAPI.current().user_id;
        if (!this.department) {
            return Toast.show("请选择工会");
        }
        if (!this.position) {
            return Toast.show("请选择职位");
        }
        UserAPI.update({
            "department_id": this.department.department_id,
            "position_id": this.position.position_id,
            "user_id": user_id
        }, function (msg, user) {
            if (user === null) {
                Toast.show(msg);
                return;
            }
            // 登录成功
            if (!UserAPI.checkScene(false)) {
                return;
            }
        });
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
