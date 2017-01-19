var Config = require("Config");
var MessageAPI = require("MessageAPI");
var UserAPI = require("UserAPI");


cc.Class({
    extends: cc.Component,

    properties: {
        announcementButton: cc.Button,
        eventButton: cc.Button,
        departmentButton: cc.Button,
        scrollview: cc.ScrollView,
        
        closeButton: cc.Button,
        cellPrefab: cc.Prefab,
        
        cells: [cc.Node],
        selectedIndex: 0,
        announcementMessages: [Object],
        eventMessages: [Object],
        departmentMessages: [Object],
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
        this.announcementButton.node.on("click", this.announcementButtonClicked, this);
        this.eventButton.node.on("click", this.eventButtonClicked, this);
        this.departmentButton.node.on("click", this.departmentButtonClicked, this);
        
	    Config.loadImage("image/message/tab_announcement_selected", this.announcementButton);
	    Config.loadImage("image/message/tab_event_normal", this.eventButton);
	    Config.loadImage("image/message/tab_department_normal", this.departmentButton);
	    
	    // 加载
	    MessageAPI.announcementMessages(function(msg, data) {
	        if (data === null) {
	            return;
	        }
	        this.announcementMessages = data;
	        this._reloadData();
	    }.bind(this));
	    MessageAPI.eventMessages(UserAPI.current().user_id, function(msg, data) {
	        if (data === null) {
	            return;
	        }
	        this.eventMessages = data;
	        this._reloadData();
	    }.bind(this));
	    MessageAPI.departmentMessages(UserAPI.current().user_id, function(msg, data) {
	        if (data === null) {
	            return;
	        }
	        this.departmentMessages = data;
	        this._reloadData();
	    }.bind(this));
    },
    
    _reloadData: function() {
        let data = this.selectedIndex === 0 ? this.announcementMessages : this.selectedIndex === 1 ? this.eventMessages : this.departmentMessages;
        for (var i = this.cells.length - 1; i >= 0; i--) {
            this.cells[i].removeFromParent();
        }
        this.cells = [];
        let node = cc.instantiate(this.cellPrefab);
        // 循环 添加
        var height = 0;
        for (let i = 0; i < data.length; i++) {
            let cellNode = cc.instantiate(this.cellPrefab);
            let position = cc.v2(0, -cellNode.height / 2 - i * cellNode.height);
            cellNode.setPosition(position);
            var msg_type = "";
            if (this.selectedIndex === 0) {
                msg_type = "镇长";
            } else if (this.selectedIndex === 1) {
                msg_type = data[i].msg_content.indexOf("鞭策") > 0 ? "鞭策" : "鼓舞";
            } else if (this.selectedIndex === 2) {
                msg_type = data[i].department_name;
            }
            cellNode.getComponent("MessageCell").updateMessage(msg_type, data[i].msg_content);
            this.scrollview.content.addChild(cellNode);
            this.cells.push(cellNode);
            height += cellNode.getComponent("MessageCell").height;
        }  
        this.scrollview.stopAutoScroll();
        this.scrollview.content.height = height;
    },
    
	closeButtonClicked: function() {
	    this.node.removeFromParent();
	},

    announcementButtonClicked: function() {
	    Config.loadImage("image/message/tab_announcement_selected", this.announcementButton);
	    Config.loadImage("image/message/tab_event_normal", this.eventButton);
	    Config.loadImage("image/message/tab_department_normal", this.departmentButton);
	    if (this.selectedIndex === 0) {
	        return;
	    }
	    this.selectedIndex = 0;
	    this._reloadData();
    },
    
    eventButtonClicked: function() {
	    Config.loadImage("image/message/tab_announcement_normal", this.announcementButton);
	    Config.loadImage("image/message/tab_event_selected", this.eventButton);
	    Config.loadImage("image/message/tab_department_normal", this.departmentButton);
	    if (this.selectedIndex === 1) {
	        return;
	    }
	    this.selectedIndex = 1;
	    this._reloadData();
    },
    
    departmentButtonClicked: function() {
	    Config.loadImage("image/message/tab_announcement_normal", this.announcementButton);
	    Config.loadImage("image/message/tab_event_normal", this.eventButton);
	    Config.loadImage("image/message/tab_department_selected", this.departmentButton);
	    if (this.selectedIndex === 2) {
	        return;
	    }
	    this.selectedIndex = 2;
	    this._reloadData();
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
