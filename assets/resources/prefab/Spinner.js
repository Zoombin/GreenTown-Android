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
        
        // 初始化滚动组件
    	this.content = this.scrollView.content;
    	this.items = [];
    	this.updateTimer = 0;
    	this.updateInterval = 0.2;
    	this.lastContentPosY = 0;
    },  
    
    update: function(dt) {
        this.updateTimer += dt;
        if (this.updateTimer < this.updateInterval) return;
        this.updateTimer = 0;
    },
    
    spinnerClicked: function() {
        this.expandSprite.node.active = !this.expandSprite.node.active;
    }

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
