cc.Class({
    extends: cc.Component,

    properties: {
        closeButton: cc.Button
    },
    
    register: function() {
        // 注册事件监听
        cc.eventManager.addListener({
            event: cc.EventListener.KEYBOARD,
            onKeyReleased: function(keyCode, event) {
                if (keyCode == cc.KEY.back) {
                    this.node.removeFromParent();
                }
            }}, this.node);
        this.closeButton.node.on("click", this.closeButtonClicked, this);  
    },
    
    closeButtonClicked: function() {
        this.node.removeFromParent();
    }

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
