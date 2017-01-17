var UserAPI = require("UserAPI");
var Config = require("Config");


cc.Class({
    extends: cc.Component,

    properties: {
        closeButton: cc.Button,
        
        // 切换
        coinsButton: cc.Button,
        pointsButton: cc.Button,
        
        // 滚动组件
        scrollView: cc.ScrollView,
        
        // 底部用户名
        userAvatarSprite: cc.Sprite,
        usernameLabel: cc.Label,
        departmentLabel: cc.Label,
        
        coinsLayout: cc.Layout,
        pointsLayout: cc.Layout,
        
        coinsValueLabel: cc.Label,
        coinsGoldSprite: cc.Sprite,
        coinsGoldLabel: cc.Label,
        
        pointsValueLabel: cc.Label,
        pointsTitleLabel: cc.Label,
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
        this.coinsButton.node.on("click", this.coinsButtonClicked, this); 
        this.pointsButton.node.on("click", this.pointsButtonClicked, this); 
        
	    this._loadImage("image/rank/tank_coins_selected", this.coinsButton);
	    this._loadImage("image/rank/tank_points_normal", this.pointsButton);
        
        // 加载用户信息
        var user = UserAPI.current();
        if (user) {
            cc.loader.loadRes(user.avatarUrl(), cc.SpriteFrame, function(err, spriteFrame) {
                if (err !== null) {
                    cc.log(err);
                    return;
                }
                this.userAvatarSprite.spriteFrame = spriteFrame;
            }.bind(this));
            this.usernameLabel.string = user.fullname;
            this.departmentLabel.string = user.department_name + " - " + user.position_name;
            
            this.coinsValueLabel.string = user.coins;
            this.pointsValueLabel.string = user.points;
            this.pointsTitleLabel.string = user.title_name;
        }
    },
    
	closeButtonClicked: function() {
	    this.node.removeFromParent();
	},
    
	coinsButtonClicked: function() {
	    this._loadImage("image/rank/tank_coins_selected", this.coinsButton);
	    this._loadImage("image/rank/tank_points_normal", this.pointsButton);
	},
    
	pointsButtonClicked: function() {
	    this._loadImage("image/rank/tank_coins_normal", this.coinsButton);
	    this._loadImage("image/rank/tank_points_selected", this.pointsButton);
	},
    
    _loadImage: function(url, button) {
        cc.loader.loadRes(url, cc.SpriteFrame, function(err, spriteFrame) {
            if (err !== null) {
                cc.log(err);
                return;
            }
            button.transition = cc.Button.Transition.SPRITE;
            button.normalSprite = spriteFrame;
            button.pressedSprite = spriteFrame;
            button.hoverSprite = spriteFrame;
            button.disabledSprite = spriteFrame;
        }.bind(button));
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
