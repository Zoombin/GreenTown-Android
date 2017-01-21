var Config = require("Config");
var UserAPI = require("UserAPI");
var SportAPI = require("SportAPI");
var Toast = require("Toast");

cc.Class({
    extends: cc.Component,

    properties: {
        closeButton: cc.Button,
        
        ruleLabel: cc.Label,
        
        sport: Object
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
        
        this.ruleLabel.string = this.sport.rule;
    },
    
	closeButtonClicked: function() {
	    this.node.removeFromParent();
	},

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
