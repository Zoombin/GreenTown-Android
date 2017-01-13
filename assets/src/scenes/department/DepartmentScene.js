var Spinner = require("Spinner");
var DepartmentAPI = require("DepartmentAPI");

cc.Class({
    extends: cc.Component,

    properties: {
        departmentSpinner: Spinner,
    
        department: Object,
        departments: [Object],
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
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
