var Config = require("Config");

cc.Class({
    extends: cc.Component,

    properties: {
        avatarSprite: cc.Sprite,
        nameLabel: cc.Label,
        departmentLabel: cc.Label,
        pointLabel: cc.Label,
        titleLabel: cc.Label
    },

    // use this for initialization
    onLoad: function () {

    },
    
    updateUser: function(user) {
        this.nameLabel.string = user.fullname;
        this.departmentLabel.string = user.department_name + " - " + user.position_name;
        this.avatarSprite.spriteFrame = Config.findAvatarWithRoleID(user.role_id);
        this.pointLabel.string = user.points;
        this.titleLabel.string = user.title_name;
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
