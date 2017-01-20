var Config = require("Config");
var TaskAPI = require("TaskAPI");
var UserAPI = require("UserAPI");

cc.Class({
    extends: cc.Component,

    properties: {
        closeButton: cc.Button,
        // 切换
        weekButton: cc.Button,
        randomButton: cc.Button,
        
        // 滚动组件
        scrollView: cc.ScrollView,
        
        weekTasks: [Object],
        randomTasks: [Object],
        
        taskCellPrefab: cc.Prefab,
        cells: [cc.Node],
        selectedIndex: 0,
    },

    // use this for initialization
    onLoad: function () {
        cc.eventManager.addListener({
            event: cc.EventListener.KEYBOARD,
            onKeyReleased: function(keyCode, event) {
                if (keyCode == cc.KEY.back) {
                    this.node.removeFromParent();
                }
            }}, this.node);
        this.closeButton.node.on("click", this.closeButtonClicked, this);
        this.weekButton.node.on("click", this.weekButtonClicked, this); 
        this.randomButton.node.on("click", this.randomButtonClicked, this); 
        
	    Config.loadImage("image/task/task_week_selected", this.weekButton);
	    Config.loadImage("image/task/task_random_normal", this.randomButton);
	    
	    TaskAPI.weekTasks(UserAPI.current().user_id, function(msg, data) {
            if (data === null) {
                return Toast.show(msg);
            }
            this.weekTasks = data;
            this._reloadData();
	    }.bind(this));
	    TaskAPI.randomTasks(UserAPI.current().user_id, function(msg, data) {
            if (data === null) {
                return Toast.show(msg);
            }
            this.randomTasks = data;
            this._reloadData();
	    }.bind(this));
    },
    
	closeButtonClicked: function() {
	    this.node.removeFromParent();
	},
	
	weekButtonClicked: function() {
	    Config.loadImage("image/task/task_week_selected", this.weekButton);
	    Config.loadImage("image/task/task_random_normal", this.randomButton);
	    if (this.selectedIndex === 0) {
	        return;
	    }
	    this.selectedIndex = 0;
	    this._reloadData();
	},
	
	randomButtonClicked: function() {
	    Config.loadImage("image/task/task_week_normal", this.weekButton);
	    Config.loadImage("image/task/task_random_selected", this.randomButton);
	    if (this.selectedIndex === 1) {
	        return;
	    }
	    this.selectedIndex = 1;
	    this._reloadData();
	},
	
	_reloadData: function() {
        for (var i = this.cells.length - 1; i >= 0; i--) {
            this.cells[i].removeFromParent();
        }
        
        let data = this.selectedIndex === 0 ? this.weekTasks : this.randomTasks;
        
        this.cells = [];
        let node = cc.instantiate(this.taskCellPrefab);
        this.scrollView.stopAutoScroll();
        this.scrollView.content.height = data.length * node.height;
        // 循环 添加
        for (let i = 0; i < data.length; i++) {
            let cellNode = cc.instantiate(this.taskCellPrefab);
            let position = cc.v2(0, -cellNode.height / 2 - i * cellNode.height);
            cellNode.setPosition(position);
            cellNode.getComponent("TaskCell").updateTask(data[i]);
            this.scrollView.content.addChild(cellNode);
            this.cells.push(cellNode);
        }
	},

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
