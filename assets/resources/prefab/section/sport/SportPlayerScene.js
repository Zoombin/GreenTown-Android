var Config = require("Config");
var UserAPI = require("UserAPI");
var SportAPI = require("SportAPI");
var Toast = require("Toast");

cc.Class({
    extends: cc.Component,

    properties: {
        closeButton: cc.Button,
        
        users: [Object],
        scrollView: cc.ScrollView,
        playerCellPrefab: cc.Prefab,
        cells: [cc.Node],
        sport: Object
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
        
        SportAPI.players(this.sport.sport_id, function(msg, data) {
	        if (data === null) {
	            return Toast.show(msg);
	        }
	        this.users = data;
	        this._reloadData();
        }.bind(this));
    },
    
	closeButtonClicked: function() {
	    this.node.removeFromParent();
	},
	
	_reloadData: function() {
        for (var i = this.cells.length - 1; i >= 0; i--) {
            this.cells[i].removeFromParent();
        }
        
        this.cells = [];
        let node = cc.instantiate(this.playerCellPrefab);
        this.scrollView.stopAutoScroll();
        this.scrollView.content.height = this.users.length * node.height;
        // 循环 添加
        for (let i = 0; i < this.users.length; i++) {
            let cellNode = cc.instantiate(this.playerCellPrefab);
            let position = cc.v2(0, -cellNode.height / 2 - i * cellNode.height);
            cellNode.setPosition(position);
            cellNode.getComponent("DepartmentIUserCell").updateUser(this.users[i]);
            this.scrollView.content.addChild(cellNode);
            this.cells.push(cellNode);
        }
	},

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
