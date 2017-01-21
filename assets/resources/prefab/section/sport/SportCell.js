var Config = require("Config");
var UserAPI = require("UserAPI");
var SportAPI = require("SportAPI");
var Toast = require("Toast");
var Dialog = require("Dialog");

cc.Class({
    extends: cc.Component,

    properties: {
        avatarSprite: cc.Sprite,
        nameLabel: cc.Label,
        descLabel: cc.Label,
        ruleButton: cc.Button,
        joinButton: cc.Button,
        
        percent1Sprite: cc.Sprite,
        percent2Sprite: cc.Sprite,
        percent3Sprite: cc.Sprite,
        percent4Sprite: cc.Sprite,
        percent5Sprite: cc.Sprite,
        
        sport: Object,
    },

    // use this for initialization
    onLoad: function () {
        this.ruleButton.node.on("click", this.ruleButtonClicked, this);
        this.joinButton.node.on("click", this.joinButtonClicked, this);
    },
    
    updateSport: function(sport) {
        this.sport = sport;
        
        this.nameLabel.string = this.sport.title;
        this.descLabel.string = this.sport.sub_title;
        Config.loadImage(this.sport.is_joined === 1 ? "image/sport/sport_join_diable" : "image/sport/sport_join_button", this.joinButton);
        
        let percent = this.sport.cur_quantity / this.sport.quantity;
        cc.log("percent = ", percent);
        
        this._loadSpriteFrame(percent > 0 ? "image/sport/sport_percent_selected" : "image/sport/sport_percent_unselected", this.percent1Sprite);
        this._loadSpriteFrame(percent >= 0.2 ? "image/sport/sport_percent_selected" : "image/sport/sport_percent_unselected", this.percent2Sprite);
        this._loadSpriteFrame(percent >= 0.4 ? "image/sport/sport_percent_selected" : "image/sport/sport_percent_unselected", this.percent3Sprite);
        this._loadSpriteFrame(percent >= 0.6 ? "image/sport/sport_percent_selected" : "image/sport/sport_percent_unselected", this.percent4Sprite);
        this._loadSpriteFrame(percent >= 0.8 ? "image/sport/sport_percent_selected" : "image/sport/sport_percent_unselected", this.percent5Sprite);
    },
    
    _loadSpriteFrame: function(url, sprite) {
        cc.loader.loadRes(url, cc.SpriteFrame, function(err, spriteFrame) {
            if (err !== null) {
                return cc.log(err);
            }
            sprite.spriteFrame = spriteFrame;
        }.bind(sprite));
    },
    
    ruleButtonClicked: function() {
        Config.showWithCallBack("prefab/section/sport/SportInfoScene", function(node) {
            node.getComponent("SportInfoScene").sport = this.sport;
        }.bind(this));
    },
    
    joinButtonClicked: function() {
        if (this.sport.is_joined === 1) {
            return;
        }
        Dialog.show("你确认参加该赛事么？", function(dialog) {
            SportAPI.enroll(UserAPI.current().user_id, this.sport.sport_id, function(msg, data) {
                if (data === null) {
                    return Toast.show(msg);
                }
                
                this.sport.is_joined = 1;
                Config.loadImage(this.sport.is_joined === 1 ? "image/sport/sport_join_diable" : "image/sport/sport_join_button", this.joinButton);
                Toast.show("参赛成功");
                dialog.dismiss();
            }.bind(this, dialog));
        }.bind(this));
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
