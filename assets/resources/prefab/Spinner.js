var ListView = require("ListView");

cc.Class({
    extends: cc.Component,

    properties: {
        nameLabel: cc.Label,
        valueLabel: cc.Label,
        expandSprite: cc.Sprite,
        touchButton: cc.Button,
        scrollView: cc.ScrollView,
        
        outsideButton: cc.Button
    },

    // use this for initialization
    onLoad: function () {
        this.expandSprite.node.active = false;
        this.touchButton.node.on("click", this.spinnerClicked, this);
    },
    
    spinnerClicked: function() {
        this.expandSprite.node.active = !this.expandSprite.node.active;
        if (this.expandSprite.node.active) {
            
        } else {
            this.hideOutSideButton();
        }
    },
    
    outsideButtonClicked: function() {
        this.hideOutSideButton();
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
