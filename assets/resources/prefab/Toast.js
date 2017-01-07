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
                
                toast.scheduleOnce(function(dt) {
                    node.removeFromParent(true);
                }, 1);
            });
            // var node = new cc.Node();
            // var node = new cc.Node();
            // var sprite = node.addComponent(cc.Sprite);
            // sprite.size
            // cc.loader.loadRes("image/toast/toast_background", cc.SpriteFrame, function(err, spriteFrame) {
            //     if (err !== null) {
            //         cc.log(err);
            //     }
            //     sprite.spriteFrame = spriteFrame;
            // });
            // node.x = cc.director.getWinSize().width / 2;
            // node.y = 300;
            // node.scale = 0.6;
            // let node = cc.instantiate(original);
            // cc.director.getScene().addChild(node, 10000, 10000);
            
            // // 添加label
            
            // // 延迟1.5秒消失
            // sprite.scheduleOnce(function(dt) {
            //     node.removeFromParent(true);
            // }, 1.5);
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
