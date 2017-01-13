cc.Class({
    extends: cc.Component,

    properties: {
        outsideButton: cc.Button,
        scrollview: cc.ScrollView,
        expandLayout: cc.Sprite
    },

    // use this for initialization
    onLoad: function () {
        this.outsideButton.node.on("click", this.outsideButtonClicked, this);
    },
    
    outsideButtonClicked: function() {
        this.node.removeFromParent(true);
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
