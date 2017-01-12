cc.Class({
    extends: cc.Component,

    properties: {
        nameLabel: cc.Label,
        valueLabel: cc.Label,
        touchButton: cc.Button,
    },

    // use this for initialization
    onLoad: function () {
        this.touchButton.node.on("click", this.spinnerClicked, this);
    },
    
    spinnerClicked: function() {
        if (cc.director.getScene().getChildByName("SpinnerExpand") === null) {
            cc.loader.loadRes("prefab/SpinnerExpand", cc.Prefab, function(err, prefab) {
                if (err !== null) {
                    cc.log(err);
                    return;
                }
                var winSize = cc.director.getWinSize();
                var node = cc.instantiate(prefab);
                node.name = "SpinnerExpand";
                node.setPosition(cc.v2(winSize.width / 2, winSize.height / 2));
                cc.director.getScene().addChild(node, 10000);
                
                var spinnerExpand = node.getComponent("SpinnerExpand");
                var expandLayout = spinnerExpand.expandLayout;
                var rect = this.touchButton.node.getBoundingBoxToWorld();
                var position = cc.v2(rect.x, rect.y);
                position = cc.v2(position.x - winSize.width / 2 + rect.width / 2, position.y - winSize.height / 2 - rect.height / 2 - rect.height);
                expandLayout.node.setPosition(position);
            }.bind(this));
        } else {
            cc.director.getScene().getChildByName("SpinnerExpand").removeFromParent(true);
        }
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
