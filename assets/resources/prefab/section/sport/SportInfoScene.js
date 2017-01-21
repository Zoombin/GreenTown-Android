var Config = require("Config");
var UserAPI = require("UserAPI");
var SportAPI = require("SportAPI");
var Toast = require("Toast");

cc.Class({
    extends: cc.Component,

    properties: {
        closeButton: cc.Button,
        ruleButton: cc.Button,
        playerButton: cc.Button,
        
        titleLabel: cc.Label,
        player1: cc.Label,
        player2: cc.Label,
        player3: cc.Label,
        moneyLabel: cc.Label,
        
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
        this.ruleButton.node.on("click", this.ruleButtonClicked, this); 
        this.playerButton.node.on("click", this.playerButtonClicked, this); 
        
        this.titleLabel.string = this.sport.title;
        this.moneyLabel.string = this.sport.total_reward;
        
        // 抓取排行榜
        SportAPI.rank(this.sport.sport_id, function(msg, data) {
            this.player1.string = data.fullname_st;
            this.player2.string = data.fullname_nd;
            this.player3.string = data.fullname_rd;
        }.bind(this));
    },
    
	closeButtonClicked: function() {
	    this.node.removeFromParent();
	},
	
	ruleButtonClicked: function() {
        Config.showWithCallBack("prefab/section/sport/SportRuleScene", function(node) {
            node.getComponent("SportRuleScene").sport = this.sport;
        }.bind(this));
	},
	
	playerButtonClicked: function() {
        Config.showWithCallBack("prefab/section/sport/SportPlayerScene", function(node) {
            node.getComponent("SportPlayerScene").sport = this.sport;
        }.bind(this));
	}

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
