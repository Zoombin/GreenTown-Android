const StringUtils = require("StringUtils");
const MessageAPI = require("MessageAPI");
const UserAPI = require("UserAPI");
const Config = require("Config");
const Toast = require("Toast");

cc.Class({
    extends: cc.Component,

    properties: {
        closeButton: cc.Button,
        editBox: cc.EditBox,
        sendButton: cc.Button,
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
        this.sendButton.node.on("click", this.sendButtonClicked, this);
    },
    
	closeButtonClicked: function() {
	    this.node.removeFromParent();
	},
    
	sendButtonClicked: function() {
	    let message = this.editBox.string;
        
        if (StringUtils.isEmpty(message)) {
            Toast.show("请输入内容");
            return;
        }
        MessageAPI.messageToMayor(UserAPI.current().user_id, message, function(msg, data) {
            if (data === null) {
                return Toast.show(msg);
            }
            Config.show("prefab/section/message/LeaveSuccessScene");
        }.bind(this));
	},

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
