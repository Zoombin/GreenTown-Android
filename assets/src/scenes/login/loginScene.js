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
        
    },
    
    loginButtonClicked: function() {
        this.login();
    },
    
    onEditingReturn: function(editBox) {
        cc.log("333");
        if (editBox == this.phoneEditBox) {
            this.codeEditBox.setFocus();
            cc.log("1111");
        } else if (editBox == this.codeEditBox) {
            this.login();
            cc.log("2222");
        }
        cc.log("444");
    },

    login: function() {
        
    }

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
