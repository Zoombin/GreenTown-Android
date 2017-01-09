const UserAPI = require("UserAPI");
const Toast = require("Toast");
const StringUtils = require("StringUtils");
const Dialog = require("Dialog");

cc.Class({
    extends: cc.Component,

    properties: {
        // 手机号
        phoneEditBox: cc.EditBox,
        // 验证码
        codeEditBox: cc.EditBox,
        // 请求验证码
        codeButton: cc.Button,
        // 登录
        loginButton: cc.Button,
    },

    // use this for initialization
    onLoad: function () {
        
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
    
    codeButtonClicked: function() {
        var phone = this.phoneEditBox.string;
        if (!StringUtils.isPhone(phone)) {
            Toast.show("手机号格式错误");
            return;
        }
        UserAPI.requestCode(phone, function(msg, data) {
            Toast.show(msg);
        });
    },
    
    loginButtonClicked: function() {
        this.login();
    },
    
    onEditingReturn: function(editBox) {
        if (editBox == this.phoneEditBox) {
            this.codeEditBox.setFocus();
        } else if (editBox == this.codeEditBox) {
            this.login();
        }
    },

    login: function() {
        var phone = this.phoneEditBox.string;
        var code = this.codeEditBox.string;
        if (!StringUtils.isPhone(phone)) {
            Toast.show("手机号格式错误");
            return;
        }
        if (StringUtils.isEmpty(code)) {
            Toast.show("请输入验证码");
            return;
        }
        UserAPI.login(phone, code, function(msg, user) {
            if (user === null) {
                Toast.show(msg);
                return;
            }
            // 登录成功
            if (!UserAPI.checkScene()) {
                return;
            }
            // 跳转到主场景
            cc.director.loadScene("mainScene");
        });
    }

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
