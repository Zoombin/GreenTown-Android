const UserAPI = require("UserAPI");
const Config = require("Config");
const StringUtils = require("StringUtils");
const Toast = require("Toast");

cc.Class({
    extends: cc.Component,

    properties: {
        boyButton: cc.Button,
        girlButton: cc.Button,
        avatar1Button: cc.Button,
        avatar2Button: cc.Button,
        avatar3Button: cc.Button,
        avatar1BorderSprite: cc.Sprite,
        avatar2BorderSprite: cc.Sprite,
        avatar3BorderSprite: cc.Sprite,
        bodySprite: cc.Sprite,
        fullnameEditBox: cc.EditBox,
        nicknameEditBox: cc.EditBox,
        confirmButton: cc.Button,
        
        isBoy: true,
        roles: [Object],
        selectedIndex: 0
    },
    
    onEnable: function() {
        cc.eventManager.addListener({
            event: cc.EventListener.KEYBOARD,
            onKeyReleased: function(keyCode, event) {
                if (keyCode == cc.KEY.back) {
                    cc.director.end();
                }
            }}, this.node);
    },

    // use this for initialization
    onLoad: function () {
        this._updateRoles(Config.roles.boy);
        this._selectAvatar(0);
    },
    
    _updateRoles: function(roles) {
        this.roles = roles;
        this._loadImage(this.roles[0].avatar, this.avatar1Button);
        this._loadImage(this.roles[1].avatar, this.avatar2Button);
        this._loadImage(this.roles[2].avatar, this.avatar3Button);
        this._loadBody(this.roles[this.selectedIndex].body, this.bodySprite);
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
    
    _loadBody: function(url, sprite) {
        cc.loader.loadRes(url, cc.SpriteFrame, function(err, spriteFrame) {
            if (err !== null) {
                cc.log(err);
                return;
            }
            sprite.spriteFrame = spriteFrame;
        }.bind(sprite));
    },
    
    _selectAvatar: function(index) {
        this.selectedIndex = index;
        
        this.avatar1BorderSprite.node.opacity = index === 0 ? 255 : 0;
        this.avatar2BorderSprite.node.opacity = index === 1 ? 255 : 0;
        this.avatar3BorderSprite.node.opacity = index === 2 ? 255 : 0;
        this._loadBody(this.roles[this.selectedIndex].body, this.bodySprite);
    },
    
    boySwitchClicked: function() {
        this._updateRoles(Config.roles.boy);
        this.isBoy = true;
    },
    
    girlSwitchClicked: function() {
        this._updateRoles(Config.roles.girl);
        this.isBoy = false;
    },
    
    avatar1ButtonClicked: function() {
        this._selectAvatar(0);
    },
    
    avatar2ButtonClicked: function() {
        this._selectAvatar(1);
    },
    
    avatar3ButtonClicked: function() {
        this._selectAvatar(2);
    },
    
    submitButtonClicked: function() {
        var role_id = this.roles[this.selectedIndex].id;
        var fullname = this.fullnameEditBox.string;
        var nickname = this.nicknameEditBox.string;
        var user_id = UserAPI.current().user_id;
        
        if (StringUtils.isEmpty(fullname)) {
            Toast.show("请输入姓名");
            return;
        }
        
        if (StringUtils.isEmpty(nickname)) {
            Toast.show("请输入昵称");
            return;
        }
        
        UserAPI.update({
            "user_id": user_id,
            "gender": this.isBoy ? "male" : "felmale",
            "role_id": role_id,
            "fullname": fullname,
            "nickname": nickname,
        }, function (msg, user) {
            if (user === null) {
                Toast.show(msg);
                return;
            }
            // 登录成功
            if (!UserAPI.checkScene(false)) {
                return;
            }
        });
    }
    
    
});
