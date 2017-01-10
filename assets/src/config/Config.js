cc.Class({
    extends: cc.Component,
    
    properties: {
        
    },
    
    statics: {
        // 角色配置
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
        
    },

    // use this for initialization
    onLoad: function () {

    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
