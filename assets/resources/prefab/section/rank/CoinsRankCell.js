var Config = require("Config");

cc.Class({
    extends: cc.Component,

    properties: {
        avatarSprite: cc.Sprite,
        nameLabel: cc.Label,
        departmentLabel: cc.Label,
        coinsLabel: cc.Label,
        goldSprite: cc.Sprite,
        rankLabel: cc.Label
    },

    // use this for initialization
    onLoad: function () {

    },
    
    updateUser: function(user, index) {
        this.nameLabel.string = user.fullname;
        this.departmentLabel.string = user.department_name + " - " + user.position_name;
        this.avatarSprite.spriteFrame = Config.findAvatarWithRoleID(user.role_id);
        this.coinsLabel.string = user.coins;
        
        this.goldSprite.node.active = index < 3;
        this.rankLabel.node.active = index >= 3;
        this.rankLabel.string = index + 1;
        if (index === 0) {
            cc.loader.loadRes("image/rank/rank_goldmedal", cc.SpriteFrame, function(err, spriteFrame) {
                if (err !== null) {
                    return cc.log(err);
                }
                this.goldSprite.spriteFrame = spriteFrame;
            }.bind(this));
        } else if (index === 1) {
            cc.loader.loadRes("image/rank/rank_silvermedal", cc.SpriteFrame, function(err, spriteFrame) {
                if (err !== null) {
                    return cc.log(err);
                }
                this.goldSprite.spriteFrame = spriteFrame;
            }.bind(this));
        } else if (index === 2) {
            cc.loader.loadRes("image/rank/rank_bronzemedal", cc.SpriteFrame, function(err, spriteFrame) {
                if (err !== null) {
                    return cc.log(err);
                }
                this.goldSprite.spriteFrame = spriteFrame;
            }.bind(this));
        }
    },
    
});
