var GiftAPI = require("GiftAPI");
var UserAPI = require("UserAPI");
var Toast = require("Toast");
var Config = require("Config");

cc.Class({
    extends: cc.Component,

    properties: {
        closeButton: cc.Button,
        
        festivalNameLabel: cc.Label,
        festivalDateLabel: cc.Label,
        festivalPackButton: cc.Button,
        
        monthNameLabel: cc.Label,
        monthDateLabel: cc.Label,
        monthPackButton: cc.Button,
        
        festivalGift: Object,
        monthGift: Object,
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
        this.festivalPackButton.node.on("click", this.festivalPackButtonClicked, this);
        this.monthPackButton.node.on("click", this.monthPackButtonClicked, this);
        
        this.festivalNameLabel.string = "";
        this.festivalDateLabel.string = "";
        this.monthNameLabel.string = "";
        this.monthDateLabel.string = "";
        
        GiftAPI.festivalGift(UserAPI.current().user_id, function(msg, data) {
            if (data === null) {
                return Toast.show(msg);
            }
            this.festivalGift = data;
            
            this.festivalNameLabel.string = data.gift_name;
            this.festivalDateLabel.string = data.gift_start;
	        Config.loadImage(data.is_picked === 1 ? "image/gift/gift_pack_background_disable" : "image/gift/gift_pack_background_normal", this.festivalPackButton);
        }.bind(this));
        GiftAPI.monthGift(UserAPI.current().user_id, function(msg, data) {
            if (data === null) {
                return Toast.show(msg);
            }
            this.monthGift = data;
            
            this.monthNameLabel.string = data.gift_name;
            this.monthDateLabel.string = data.gift_start;
	        Config.loadImage(data.is_picked === 1 ? "image/gift/gift_pack_background_disable" : "image/gift/gift_pack_background_normal", this.monthPackButton);
        }.bind(this));
    },
    
	closeButtonClicked: function() {
	    this.node.removeFromParent();
	},
	
	festivalPackButtonClicked: function() {
	    if (this.festivalGift.is_picked === 1) {
	        return;
	    }
	    GiftAPI.pickFestivalGift(UserAPI.current().user_id, this.festivalGift.gift_id, function(msg, data) {
            if (data === null) {
                return Toast.show(msg);
            }
            // 领取成功
	    }.bind(this));
	},
	
	monthPackButtonClicked: function() {
	    if (this.monthGift.is_picked === 1) {
	        return;
	    }
	    GiftAPI.pickMonethGift(UserAPI.current().user_id, this.monthGift.gift_id, function(msg, data) {
            if (data === null) {
                return Toast.show(msg);
            }
            // 领取成功
	    }.bind(this));
	},

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
