cc.Class({
    extends: cc.Component,

    properties: {
        nameLabel: cc.Label,
        valueLabel: cc.Label,
        expandSprite: cc.Sprite,
        touchButton: cc.Button,
        scrollView: cc.ScrollView
    },

    // use this for initialization
    onLoad: function () {
        this.expandSprite.node.active = false;
        this.touchButton.node.on("click", this.spinnerClicked, this);
    },  
    
    spinnerClicked: function() {
        this.expandSprite.node.active = !this.expandSprite.node.active;
    }

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
