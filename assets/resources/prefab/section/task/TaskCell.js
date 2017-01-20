var Config = require("Config");
var TaskAPI = require("TaskAPI");
var UserAPI = require("UserAPI");
var Toast = require("Toast");

cc.Class({
    extends: cc.Component,

    properties: {
        taskNameLabel: cc.Label,
        taskValueLabel: cc.Label,
        finishButton: cc.Button,
        packButton: cc.Button,
        
        task: Object,
    },

    // use this for initialization
    onLoad: function () {
        this.finishButton.node.on("click", this.finishButtonClicked, this); 
        this.packButton.node.on("click", this.packButtonClicked, this); 
    },
    
    updateTask: function(task) {
        this.task = task;
        this.taskNameLabel.string = task.task_name;
        this.taskValueLabel.string = "成就点" + task.points + ", 绿币" + task.coins + ", 金币" + task.golds;
      
	    Config.loadImage(task.is_finished === 1 ? "image/task/task_finish_disable" : "image/task/task_finish_normal", this.finishButton);
	    Config.loadImage(task.is_picked === 1 ? "image/task/task_pick_disable" : "image/task/task_pick_normal", this.packButton);
    },
    
    finishButtonClicked: function() {
        // 完成
        if (this.task.is_finished === 1) {
            return;
        }
        TaskAPI.finishTask(this.task.task_id, UserAPI.current().user_id, function(msg, data) {
            if (data === null) {
                return Toast.show(msg);
            }
            this.task.is_finished = 1;
            Config.loadImage(this.task.is_finished === 1 ? "image/task/task_finish_disable" : "image/task/task_finish_normal", this.finishButton);
            Toast.show("完成成功");
        }.bind(this));
    },
    
    packButtonClicked: function() {
        // 领取
        if (this.task.is_picked === 1) {
            return;
        }
        TaskAPI.pickTask(this.task.task_id, UserAPI.current().user_id, function(msg, data) {
            if (data === null) {
                return Toast.show(msg);
            }
            this.task.is_picked = 1;
            Config.loadImage(this.task.is_picked === 1 ? "image/task/task_pick_disable" : "image/task/task_pick_normal", this.packButton);
            Toast.show("领取成功");
        }.bind(this));
    },
    
});
