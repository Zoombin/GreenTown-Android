
var Config = require("Config");

cc.Class({
    extends: cc.Component,

    properties: {
        announcementButton: cc.Button,
        eventButton: cc.Button,
        departmentButton: cc.Button,
        scrollview: cc.ScrollView,
        
        closeButton: cc.Button,
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
    },
    
	closeButtonClicked: function() {
	    this.node.removeFromParent();
	},

    announcementButtonClicked: function() {
	    Config.loadImage("image/message/tab_announcement_selected", this.announcementButton);
	    Config.loadImage("image/message/tab_event_normal", this.eventButton);
	    Config.loadImage("image/message/tab_department_normal", this.departmentButton);
    },
    
    eventButtonClicked: function() {
	    Config.loadImage("image/message/tab_announcement_normal", this.announcementButton);
	    Config.loadImage("image/message/tab_event_selected", this.eventButton);
	    Config.loadImage("image/message/tab_department_normal", this.departmentButton);
    },
    
    departmentButtonClicked: function() {
	    Config.loadImage("image/message/tab_announcement_normal", this.announcementButton);
	    Config.loadImage("image/message/tab_event_normal", this.eventButton);
	    Config.loadImage("image/message/tab_department_selected", this.departmentButton);
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
