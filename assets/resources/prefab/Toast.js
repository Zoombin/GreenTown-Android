cc.Class({
    extends: cc.Component,

    properties: {
        messageLabel: cc.Label,
        backgroundSprite: cc.Sprite,
    },
    
    statics: {
        
        // 显示
        show: function(msg) {
            cc.loader.loadRes("prefab/Toast", cc.Prefab, function(err, prefab) {
                if (err !== null) {
                    cc.log("加载失败" + err);
                    return;
                }
                var node = cc.instantiate(prefab);
                let toast = node.getComponent('Toast');
                toast.updateMessage(msg);
                var size = cc.director.getWinSize();
                node.setPosition(cc.v2(size.width / 2, size.height / 2));
                cc.director.getScene().addChild(node, 10000, 10000);
                
                node.opacity = 0;
                var action = cc.fadeTo(0.15, 255);
                node.runAction(action);
                
                toast.scheduleOnce(function(dt) {
                    node.removeFromParent(true);
                }, 1);
            });
        }  
    },
    
    updateMessage: function(msg) {
        this.messageLabel.string = msg;
    },

    // use this for initialization
    onLoad: function () {

    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
