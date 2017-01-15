cc.Class({
    extends: cc.Component,

    properties: {
        announcementButton: cc.Button,
        eventButton: cc.Button,
        departmentButton: cc.Button,
        scrollview: cc.ScrollView,
        
        closeButton: cc.Button,
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
    },
    
	closeButtonClicked: function() {
	    this.node.removeFromParent();
	},

    announcementButtonClicked: function() {
        
    },
    
    eventButtonClicked: function() {
        
    },
    
    departmentButtonClicked: function() {
        
    }

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
