const API = require("API");
const UserAPI = require("UserAPI");
const Toast = require("Toast");
const StringUtils = require("StringUtils");

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
        var phone = this.phoneEditBox.string;
        var code = this.codeEditBox.string;
        if (!StringUtils.isPhone(phone)) {
            Toast.show("手机号格式错误");
            return;
        }
        UserAPI.login(phone, code, function(msg, user) {
            if (msg !== null) {
                Toast.show(msg);
                return;
            }
            // 登录成功
        });
    },
    
    onEditingReturn: function(editBox) {
        if (editBox == this.phoneEditBox) {
            this.codeEditBox.setFocus();
        } else if (editBox == this.codeEditBox) {
            this.login();
        }
        Toast.show("123");
    },

    login: function() {
        
    }

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
