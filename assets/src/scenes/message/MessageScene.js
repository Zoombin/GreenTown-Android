cc.Class({
    extends: cc.Component,

    properties: {
        announcementButton: cc.Button,
        eventButton: cc.Button,
        departmentButton: cc.Button,
        scrollview: cc.ScrollView
    },

    // use this for initialization
    onLoad: function () {
        cc.eventManager.addListener({
            event: cc.EventListener.KEYBOARD,
            onKeyReleased: function(keyCode, event) {
                if (keyCode == cc.KEY.back) {
                    cc.director.loadScene("mainScene");
                }
            }}, this.node);
    },
    
    closeButtonClicked: function() {
        cc.director.loadScene("mainScene");
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
