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
    
    onEnable: function() {
        cc.eventManager.addListener({
            event: cc.EventListener.KEYBOARD,
            onKeyReleased: function(keyCode, event) {
                if (keyCode == cc.KEY.back) {
                    cc.director.end();
                }
            }}, this.node);
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
    },
    onMenuSportClicked: function() {
        cc.log("menu", "onMenuSportClicked");
    },
    onMenuStoreClicked: function() {
        cc.log("menu", "onMenuStoreClicked");
    },
    onMenuSettingClicked: function() {
        cc.log("menu", "onMenuSettingClicked");
    },
    
    start: function() {
        cc.log("start");
        this.infoNameLabel.width = 500;
    },

    // use this for initialization
    onLoad: function () {
        // 检测用户是否登录，未登录的话，进入登录页面
        var user = UserAPI.current();
        if (user === null) {
            cc.director.loadScene("loginScene");
            return;
        }
        if (!UserAPI.checkScene()) {
            return;
        }
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
