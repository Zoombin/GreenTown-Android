const UserAPI = require('UserAPI');
const Dialog = require("Dialog");
const Config = require("Config");

var HomeScene = require("HomeScene");

cc.Class({
    extends: cc.Component,

    properties: {
        
        mapZhenzhangButton: cc.Button,
        mapGonghuiButton: cc.Button,
        mapPaihangButton: cc.Button,
        mapJingjiButton: cc.Button,
        mapBaokuButton: cc.Button,
        mapBeiBaoButton: cc.Button,
        
        menuMessageButton: cc.Button,
        menuSportButton: cc.Button,
        menuStoreButton: cc.Button,
        menuSetButton: cc.Button,
        
        settingLayout: cc.Node,
    },

    // use this for initialization
    onLoad: function () {
        // cc.game.addPersistRootNode(this.node); 
        Config.loadAvatar();
        // 检测用户是否登录，未登录的话，进入登录页面
        if (!UserAPI.checkScene(true)) {
            return;
        }
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
        // cc.director.loadScene("HomeScene");
        Config.show("prefab/section/home/HomeScene");
    },
    onMapGonghuiClicked: function() {
        Config.show("prefab/section/department/DepartmentScene");
    },
    onMapPaihangClicked: function() {
        Config.show("prefab/section/rank/RankScene");
    },
    onMapJingjiClicked: function() {
        Config.show("prefab/section/sport/SportScene");
    },
    onMapBaokuClicked: function() {
        cc.log("map", "onMapBaokuClicked");
    },
    onMapBeibaoClicked: function() {
        cc.log("map", "onMapBeibaoClicked");
    },
    
    // 菜单栏
    onMenuMessageClicked: function() {
        Config.show("prefab/section/message/MessageScene");
    },
    onMenuSportClicked: function() {
        Config.show("prefab/section/sport/SportScene");
    },
    onMenuStoreClicked: function() {
        cc.log("menu", "onMenuStoreClicked");
    },
    onMenuSettingClicked: function() {
        this.settingLayout.active = !this.settingLayout.active;
    },
    
    logoutButtonClicked: function() {
        Dialog.show("你确认要退出帐号么？", function(dialog) {
            UserAPI.current().logout();
            dialog.dismiss();
            if (!UserAPI.checkScene(true)) {
                return;
            }
        });
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
