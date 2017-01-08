cc.Class({
    extends: cc.Component,

    properties: {
        boyButton: cc.Button,
        girlButton: cc.Button,
        avatar1Button: cc.Button,
        avatar2Button: cc.Button,
        avatar3Button: cc.Button,
        bodySprite: cc.Sprite,
        fullnameEditBox: cc.EditBox,
        nicknameEditBox: cc.EditBox,
        confirmButton: cc.Button,
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
        
    },
    
});
