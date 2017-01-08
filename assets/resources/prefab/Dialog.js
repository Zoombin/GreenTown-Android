cc.Class({
    extends: cc.Component,

    properties: {
        maskLayer: cc.Sprite,
        cancelButton: cc.Button,
        submitButton: cc.Button,
        messageLabel: cc.Label,
        submitCallback: Function,
    },
    
    statics: {
        
        show: function(msg, callback) {
            cc.loader.loadRes("prefab/Dialog", cc.Prefab, function(err, prefab) {
                if (err !== null) {
                    cc.log("加载失败" + err);
                    return;
                }
                var node = cc.instantiate(prefab);
                let toast = node.getComponent('Dialog');
                toast.messageLabel.string = msg;
                toast.submitCallback = callback;
                var size = cc.director.getWinSize();
                node.setPosition(cc.v2(size.width / 2, size.height / 2));
                cc.director.getScene().addChild(node, 10000);
                
                node.opacity = 0;
                var action = cc.fadeTo(0.15, 255);
                node.runAction(action);
            });
        }  
    },

    // use this for initialization
    onLoad: function () {
        this.cancelButton.node.on("click", this.cancelButtonOnClicked, this);
        this.submitButton.node.on("click", this.submitButtonOnClicked, this);
    },
    
    onEnable:function(){
        this.maskLayer.node.on('touchstart', this.eatTouch, this);
    },
    onDisable:function(){
        this.maskLayer.node.off('touchstart', this.eatTouch, this);
    },
    
    dismiss: function() {
        this.node.removeFromParent(true);
    },
    
    //触摸吞噬
    eatTouch:function(event){
        event.stopPropagation();
    },
    
    cancelButtonOnClicked: function() {
        this.dismiss();
    },
    
    submitButtonOnClicked: function() {
        this.submitCallback(this);
    }

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
