const Config = require("Config");

cc.Class({
    extends: cc.Component,

    properties: {
        nameLabel: cc.Label,
        departmentLabel: cc.Label,
        avatarSprite: cc.Sprite,
        inspireButton: cc.Button,
        spurButton: cc.Button
    },

    // use this for initialization
    onLoad: function () {
        this.inspireButton.node.on("click", this.inspireButtonClicked, this);
        this.spurButton.node.on("click", this.spurButtonClicked, this);
    },
    
    updateUser: function(user) {
        this.user = user;
        this.nameLabel.string = user.fullname;
        this.departmentLabel.string = user.department_name + " - " + user.position_name;
        this.avatarSprite.spriteFrame = Config.findAvatarWithRoleID(user.role_id);
    },

    // 鼓舞
    inspireButtonClicked: function() {
        
    },
    
    // 鞭策
    spurButtonClicked: function() {
        
    }

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
