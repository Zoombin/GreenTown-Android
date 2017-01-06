const User = require('User');

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
        
        mapZhenzhangButton: cc.Button,
        mapGonghuiButton: cc.Button,
        mapPaihangButton: cc.Button,
        mapJingjiButton: cc.Button,
        mapBaokuButton: cc.Button,
        mapBeiBaoButton: cc.Button,
        
        menuMessageButton: cc.Button,
        menuSportButton: cc.Button,
        menuStoreButton: cc.Button,
        menuSetButton: cc.Button
        
    },
    
    // 地图
    onMapZhengzhangClicked: function() {
        cc.log("map", "onMapZhengzhangClicked");
    },
    onMapGonghuiClicked: function() {
        cc.log("map", "onMapGonghuiClicked");
    },
    onMapPaihangClicked: function() {
        cc.log("map", "onMapPaihangClicked");
    },
    onMapJingjiClicked: function() {
        cc.log("map", "onMapJingjiClicked");
    },
    onMapBaokuClicked: function() {
        cc.log("map", "onMapBaokuClicked");
    },
    onMapBeibaoClicked: function() {
        cc.log("map", "onMapBeibaoClicked");
    },
    
    // 菜单栏
    onMenuMessageClicked: function() {
        cc.log("menu", "onMenuMessageClicked");
        var user = new User();
        user.user_id = "111";
        user.save();
    },
    onMenuSportClicked: function() {
        cc.log("menu", "onMenuSportClicked");
        this.infoNameLabel.string = User.current().user_id;
    },
    onMenuStoreClicked: function() {
        cc.log("menu", "onMenuStoreClicked");
        cc.director.loadScene("loginScene");
    },
    onMenuSettingClicked: function() {
        cc.log("menu", "onMenuSettingClicked");
        cc.director.loadScene("completeInfoScene");
    },

    // use this for initialization
    onLoad: function () {
        // 检测用户是否登录，未登录的话，进入登录页面
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
