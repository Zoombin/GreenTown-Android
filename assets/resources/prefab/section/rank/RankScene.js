var UserAPI = require("UserAPI");
var Config = require("Config");
const Toast = require("Toast");

cc.Class({
    extends: cc.Component,

    properties: {
        closeButton: cc.Button,
        
        // 切换
        coinsButton: cc.Button,
        pointsButton: cc.Button,
        
        // 滚动组件
        scrollView: cc.ScrollView,
        
        // 底部用户名
        userAvatarSprite: cc.Sprite,
        usernameLabel: cc.Label,
        departmentLabel: cc.Label,
        
        coinsLayout: cc.Layout,
        pointsLayout: cc.Layout,
        
        coinsValueLabel: cc.Label,
        coinsGoldSprite: cc.Sprite,
        coinsGoldLabel: cc.Label,
        
        pointsValueLabel: cc.Label,
        pointsTitleLabel: cc.Label,
        
        coinsCellPrefab: cc.Prefab,
        pointsCellPrefab: cc.Prefab,
        userIndex: 0,
        selectedIndex: 0,
        cells: [cc.Node],
    },

    // use this for initialization
    onLoad: function () {
        cc.eventManager.addListener({
            event: cc.EventListener.KEYBOARD,
            onKeyReleased: function(keyCode, event) {
                if (keyCode == cc.KEY.back) {
                    this.node.removeFromParent();
                }
            }}, this.node);
        this.closeButton.node.on("click", this.closeButtonClicked, this); 
        this.coinsButton.node.on("click", this.coinsButtonClicked, this); 
        this.pointsButton.node.on("click", this.pointsButtonClicked, this); 
        
	    Config.loadImage("image/rank/tank_coins_selected", this.coinsButton);
	    Config.loadImage("image/rank/tank_points_normal", this.pointsButton);
        
        // 加载用户信息
        var user = UserAPI.current();
        if (user) {
            cc.loader.loadRes(user.avatarUrl(), cc.SpriteFrame, function(err, spriteFrame) {
                if (err !== null) {
                    cc.log(err);
                    return;
                }
                this.userAvatarSprite.spriteFrame = spriteFrame;
            }.bind(this));
            this.usernameLabel.string = user.fullname;
            this.departmentLabel.string = user.department_name + " - " + user.position_name;
            
            this.coinsValueLabel.string = user.coins;
            this.pointsValueLabel.string = user.points;
            this.pointsTitleLabel.string = user.title_name;
        }
        
        // 加载数据
        UserAPI.coinsRank(0, function(msg, data) {
            if (data === null) {
                return Toast.show(msg);
            }
            this.coinsItems = data;
            for(var i = 0; i < data.length; i++) {
                if (data[i].user_id == UserAPI.current().user_id) {
                    this.coinsGoldLabel.string = i + 1;
                    this.userIndex = i;
                    break;
                }
            }
            this._reloadData();
        }.bind(this));
        UserAPI.pointsRank(0, function(msg, data) {
            if (data === null) {
                return Toast.show(msg);
            }
            this.pointsItem = data;
            this._reloadData();
        }.bind(this));
    },
    
    _reloadData: function() {
        for (var i = this.cells.length - 1; i >= 0; i--) {
            this.cells[i].removeFromParent();
        }
        let prefab = this.selectedIndex === 0 ? this.coinsCellPrefab : this.pointsCellPrefab;
        let data = this.selectedIndex === 0 ? this.coinsItems : this.pointsItem;
        
        this.cells = [];
        let node = cc.instantiate(prefab);
        this.scrollView.stopAutoScroll();
        this.scrollView.content.height = data.length * node.height;
        // 循环 添加
        for (let i = 0; i < data.length; i++) {
            let cellNode = cc.instantiate(prefab);
            let position = cc.v2(0, -cellNode.height / 2 - i * cellNode.height);
            cellNode.setPosition(position);
            if (this.selectedIndex === 0) {
                cellNode.getComponent("CoinsRankCell").updateUser(data[i], i);
            } else {
                cellNode.getComponent("PointsRankCell").updateUser(data[i]);
            }
            this.scrollView.content.addChild(cellNode);
            this.cells.push(cellNode);
        }
    },
    
	closeButtonClicked: function() {
	    this.node.removeFromParent();
	},
    
	coinsButtonClicked: function() {
	    Config.loadImage("image/rank/tank_coins_selected", this.coinsButton);
	    Config.loadImage("image/rank/tank_points_normal", this.pointsButton);
	    this.coinsLayout.node.active = true;
	    this.pointsLayout.node.active = false;
	    if (this.selectedIndex === 0) {
	        return;
	    }
	    this.selectedIndex = 0;
	    this._reloadData();
	},
    
	pointsButtonClicked: function() {
	    Config.loadImage("image/rank/tank_coins_normal", this.coinsButton);
	    Config.loadImage("image/rank/tank_points_selected", this.pointsButton);
	    this.coinsLayout.node.active = false;
	    this.pointsLayout.node.active = true;
	    if (this.selectedIndex === 1) {
	        return;
	    }
	    this.selectedIndex = 1;
	    this._reloadData();
	},

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
