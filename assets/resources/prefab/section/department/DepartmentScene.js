var GLScene = require("GLScene");
var Spinner = require("Spinner");
var DepartmentAPI = require("DepartmentAPI");

cc.Class({
    extends: GLScene,

    properties: {
        departmentSpinner: Spinner,
        scrollview: cc.ScrollView,
        cells: [cc.Node],
    
        department: Object,
        departments: [Object],
        cellPrefab: {
            default: null,
            type: cc.Prefab
        },
    },

    // use this for initialization
    onLoad: function () {
        this.register();
        
        this.departmentSpinner.numberOfCellCallback = function() {
            return this.departments.length;
        }.bind(this);
        this.departmentSpinner.textOfCellCallback = function(index) {
            return this.departments[index].department_name;
        }.bind(this);
        this.departmentSpinner.cellClickedCallback = function(index) {
            this.selectDepartment(this.departments[index]);
        }.bind(this);
        
        DepartmentAPI.departmentList(function(msg, data) {
            if (data === null) {
                return Toast.show(msg);
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
        DepartmentAPI.departmentUsers(department.department_id, function(msg, data) {
            if (data === null) {
                return Toast.show(msg);
            }
            for (var i = this.cells.length - 1; i >= 0; i--) {
                this.cells[i].removeFromParent();
            }
            this.cells = [];
            let node = cc.instantiate(this.cellPrefab);
            this.scrollview.content.height = data.length * node.height;
            cc.log("this.scrollview.content.height = ", this.scrollview.content.height);
            // 循环 添加
            for (let i = 0; i < data.length; i++) {
                let cellNode = cc.instantiate(this.cellPrefab);
                let position = cc.v2(0, -cellNode.height / 2 - i * cellNode.height);
                cellNode.setPosition(position);
                cellNode.getComponent("DepartmentIUserCell").updateUser(data[i]);
                this.scrollview.content.addChild(cellNode);
                this.cells.push(cellNode);
            }
        }.bind(this));
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
