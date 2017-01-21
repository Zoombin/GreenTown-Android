const Toast = require("Toast");
const Config = require("Config");
const SportAPI = require("SportAPI");
const UserAPI = require("UserAPI");

cc.Class({
    extends: cc.Component,

    properties: {
        closeButton: cc.Button,
        
        moneyLabel: cc.Label,
        
        // 切换
        monthButton: cc.Button,
        yearButton: cc.Button,
        
        monthSports: [Object],
        yearSports: [Object],
        // 滚动组件
        scrollView: cc.ScrollView,
        sportsCellPrefab: cc.Prefab,
        cells: [cc.Node],
        selectedIndex: 0,
        
        monthMoney: 0,
        yearMoney: 0,
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
        this.monthButton.node.on("click", this.monthButtonClicked, this); 
        this.yearButton.node.on("click", this.yearButtonClicked, this); 
        
	    Config.loadImage("image/sport/sport_tab_month_selected", this.monthButton);
	    Config.loadImage("image/sport/sport_tab_year_normal", this.yearButton);
	    
	    SportAPI.sportMonthList(UserAPI.current().user_id, function(msg, data) {
	        if (data === null) {
	            return Toast.show(msg);
	        }
	        var money = 0;
	        for (var i = 0; i < data.length; i++) {
	            var sport = data[i];
	            cc.log("sport.total_reward", sport.total_reward);
	            money += sport.total_reward;
	        }
	        this.monthMoney = money;
	        this.monthSports = data;
	        this._reloadData();
	    }.bind(this));
	    SportAPI.sportYearList(UserAPI.current().user_id, function(msg, data) {
	        if (data === null) {
	            return Toast.show(msg);
	        }
	        var money = 0;
	        for (var i = 0; i < data.length; i++) {
	            var sport = data[i];
	            money += sport.total_reward;
	        }
	        this.yearMoney = money;
	        this.yearSports = data;
	        this._reloadData();
	    }.bind(this));
    },
    
	closeButtonClicked: function() {
	    this.node.removeFromParent();
	},
	
	_reloadData: function() {
	    this.moneyLabel.string = this.selectedIndex === 0 ? this.monthMoney : this.yearMoney;
        for (var i = this.cells.length - 1; i >= 0; i--) {
            this.cells[i].removeFromParent();
        }
        
        let data = this.selectedIndex === 0 ? this.monthSports : this.yearSports;
        
        this.cells = [];
        let node = cc.instantiate(this.sportsCellPrefab);
        this.scrollView.stopAutoScroll();
        this.scrollView.content.height = data.length * node.height;
        // 循环 添加
        for (let i = 0; i < data.length; i++) {
            let cellNode = cc.instantiate(this.sportsCellPrefab);
            let position = cc.v2(0, -cellNode.height / 2 - i * cellNode.height);
            cellNode.setPosition(position);
            cellNode.getComponent("SportCell").updateSport(data[i]);
            this.scrollView.content.addChild(cellNode);
            this.cells.push(cellNode);
        }
	},
    
	monthButtonClicked: function() {
	    Config.loadImage("image/sport/sport_tab_month_selected", this.monthButton);
	    Config.loadImage("image/sport/sport_tab_year_normal", this.yearButton);
	    if (this.selectedIndex === 0) {
	        return;
	    }
	    this.selectedIndex = 0;
	    this._reloadData();
	},
    
	yearButtonClicked: function() {
	    Config.loadImage("image/sport/sport_tab_month_normal", this.monthButton);
	    Config.loadImage("image/sport/sport_tab_year_selected", this.yearButton);
	    if (this.selectedIndex === 1) {
	        return;
	    }
	    this.selectedIndex = 1;
	    this._reloadData();
	},

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
