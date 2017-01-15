var Config = cc.Class({
    extends: cc.Component,
    
    properties: {
        
    },
    
    statics: {
        
        boy0Avatar: cc.SpriteFrame,
        boy1Avatar: cc.SpriteFrame,
        boy2Avatar: cc.SpriteFrame,
        girl0Avatar: cc.SpriteFrame,
        girl1Avatar: cc.SpriteFrame,
        girl2Avatar: cc.SpriteFrame,

        roles: {
            "boy": [
                {
                    "avatar": "image/user/boy/boy1_avatar",
                    "body": "image/user/boy/boy1_all",
                    "id": 0,
                },
                
                {
                    "avatar": "image/user/boy/boy3_avatar",
                    "body": "image/user/boy/boy3_all",
                    "id": 1,
                },

                {
                    "avatar": "image/user/boy/boy2_avatar",
                    "body": "image/user/boy/boy2_all",
                    "id": 2,
                }
            ],
            
            "girl": [
                {
                    "avatar": "image/user/girl/girl1_avatar",
                    "body": "image/user/girl/girl1_all",
                    "id": 3,
                },
                
                {
                    "avatar": "image/user/girl/girl3_avatar",
                    "body": "image/user/girl/girl3_all",
                    "id": 4,
                },
                
                {
                    "avatar": "image/user/girl/girl2_avatar",
                    "body": "image/user/girl/girl2_all",
                    "id": 5,
                }
            ]
        },
        
        findAvatarWithRoleID: function(role_id) {
            if (role_id === 0) {
                return Config.boy0Avatar;
            }
            if (role_id === 1) {
                return Config.boy1Avatar;
            }
            if (role_id === 2) {
                return Config.boy2Avatar;
            }
            if (role_id === 3) {
                return Config.girl0Avatar;
            }
            if (role_id === 4) {
                return Config.girl1Avatar;
            }
            if (role_id === 5) {
                return Config.girl2Avatar;
            }
        },
        
        loadAvatar: function() {
            cc.loader.loadRes("image/user/boy/boy1_avatar", cc.SpriteFrame, function(error, spriteFrame) {
                Config.boy0Avatar = spriteFrame;
            });
            cc.loader.loadRes("image/user/boy/boy3_avatar", cc.SpriteFrame, function(error, spriteFrame) {
                Config.boy1Avatar = spriteFrame;
            });
            cc.loader.loadRes("image/user/boy/boy2_avatar", cc.SpriteFrame, function(error, spriteFrame) {
                Config.boy2Avatar = spriteFrame;
            });
            cc.loader.loadRes("image/user/girl/girl1_avatar", cc.SpriteFrame, function(error, spriteFrame) {
                Config.girl0Avatar = spriteFrame;
            });
            cc.loader.loadRes("image/user/girl/girl3_avatar", cc.SpriteFrame, function(error, spriteFrame) {
                Config.girl1Avatar = spriteFrame;
            });
            cc.loader.loadRes("image/user/girl/girl2_avatar", cc.SpriteFrame, function(error, spriteFrame) {
                Config.girl2Avatar = spriteFrame;
            });
        }, 
        
        show: function(url) {
            cc.log("url = ", url);
            cc.loader.loadRes(url, cc.Prefab, function(err, prefab) {
                if (err !== null) {
                    cc.log("加载失败" + err);
                    return;
                }
                var node = cc.instantiate(prefab);
                var size = cc.director.getWinSize();
                node.setPosition(cc.v2(size.width / 2, size.height / 2));
                cc.director.getScene().addChild(node);
            });
        }  
        
    },  
    // use this for initialization
    onLoad: function () {

    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
