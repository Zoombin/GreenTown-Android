cc.Class({
    extends: cc.Component,

    properties: {
        submitButton: cc.Button,
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
        this.submitButton.node.on("click", this.submitButtonClicked, this);
    },
    
    submitButtonClicked: function() {
	    this.node.removeFromParent();
    }
    
});
