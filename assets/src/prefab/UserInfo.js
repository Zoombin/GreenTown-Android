const UserAPI = require('UserAPI');

cc.Class({
    extends: cc.Component,

    properties: {
        infoAvatarSprite: cc.Sprite,
        infoNameLabel: cc.Label,
        infoDepartmentLabel: cc.Label,
        infoTitleLabel: cc.Label,
        infoPointsLabel: cc.Label,
        infoGoldsLabel: cc.Label,
        infoCoinsLabel: cc.Label,
    },

    // use this for initialization
    onLoad: function () {
        this.loadUser();
        cc.director.getScene().on("user", this.loadUser, this);
    },
    
    loadUser: function() {
        var user = UserAPI.current();
        if (user) {
            this.infoNameLabel.string = user.fullname;
            this.infoDepartmentLabel.string = user.department_name;
            this.infoTitleLabel.string = user.title_name;
            this.infoPointsLabel.string = user.points;
            this.infoGoldsLabel.string = user.golds;
            this.infoCoinsLabel.string = user.coins;
            // 头像加载
            
            cc.loader.loadRes(user.avatarUrl(), cc.SpriteFrame, function(err, spriteFrame) {
                if (err !== null) {
                    cc.log(err);
                    return;
                }
                this.infoAvatarSprite.spriteFrame = spriteFrame;
            }.bind(this));
        }
    }

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
