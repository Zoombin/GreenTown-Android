const API = require("API");

var User = cc.Class({
    extends: cc.Component,
    
    properties: {
        // 用户id
        user_id: "",
        // 用户名(手机号)
        user_name: "",
        // 姓名
        fullname: "",
        // 昵称
        nickname: "",
        // 性别 （男：male）（女：felmale）
        gender: "",
        // 角色id
        role_id: "",
        // 工会id
        department_id: "",
        // 工会名称
        department_name: "",
        // 职位id
        position_id: "",
        // 职位名字
        position_name: "",
        // 金币
        golds: "",
        // 绿币
        coins: "",
        // 成就点数
        points: "",
        // 称号
        title_name: "",
    },
    
    statics: {
        
        // 当前用户
        current: function() {
            var user_id = cc.sys.localStorage.getItem("user_id");
            if (user_id !== null && user_id !== undefined && user_id !== '') {
                var user = new User();
                user.user_id = user_id;
                user.user_name = cc.sys.localStorage.getItem("user_name");
                user.fullname = cc.sys.localStorage.getItem("fullname");
                user.nickname = cc.sys.localStorage.getItem("nickname");
                user.gender = cc.sys.localStorage.getItem("gender");
                user.role_id = cc.sys.localStorage.getItem("role_id");
                user.department_id = cc.sys.localStorage.getItem("department_id");
                user.department_name = cc.sys.localStorage.getItem("department_name");
                user.position_id = cc.sys.localStorage.getItem("position_id");
                user.position_name = cc.sys.localStorage.getItem("position_name");
                user.golds = cc.sys.localStorage.getItem("golds");
                user.coins = cc.sys.localStorage.getItem("coins");
                user.points = cc.sys.localStorage.getItem("points");
                user.title_name = cc.sys.localStorage.getItem("title_name");
                return user;
            }
            return null;
        },
        
        // 登录
        login: function(phone, code, callback) {
            API.post("user/login", {
                "user_name": phone,
                "captcha": code
            }, function(msg, data) {
                if (msg !== null) {
                    callback(msg, null);
                    return;
                }
                var user = new User();
                user.user_id = data.user_id;
                user.user_name = data.user_name;
                user.fullname = data.fullname;
                user.nickname = data.nickname;
                user.gender = data.gender;
                user.role_id = data.role_id;
                user.department_id = data.department_id;
                user.department_name = data.department_name;
                user.position_id = data.position_id;
                user.position_name = data.position_name;
                user.golds = data.golds;
                user.coins = data.coins;
                user.points = data.points;
                user.title_name = data.title_name;
                user.save();
                callback(null, user);
            });
        },
        
        // 获取验证码
        requestCode: function(phone, callback) {
            API.get("user/captcha", {
                "user_name": phone
            }, function(msg, data) {
                if (msg !== null) {
                    callback(msg, null);
                    return;
                }
                callback(msg, null);
            });
        },
        
        // 成就排行
        pointsRank: function(page, callback) {
            API.get("user/points_ranking", {
                "START": page,
                "PAGESIZE": 50
            }, callback);
        },
        
        // 绿币排行
        coinsRank: function(page, callback) {
            API.get("user/coins_ranking", {
                "START": page,
                "PAGESIZE": 50
            }, callback);
        },
        
        // 所有用户
        allUsers: function(callback) {
            API.get("user/group_list", {
                "START": 0,
                "PAGESIZE": 50
            }, callback);
        },
        
        // 鞭策
        spur: function(user_id, reason_id, callback) {
            API.post("user/spur", {
                user_id: user_id,
                reason_id: reason_id
            }, callback);
        },
        
        // 鼓舞
        inspire: function(user_id, reason_id, callback) {
            API.post("user/inspire", {
                user_id: user_id,
                reason_id: reason_id
            }, callback);
        },
        
    },
    
    // 退出登录
    logout: function() {
        cc.sys.localStorage.setItem("user_id", "");
        cc.sys.localStorage.setItem("user_name", "");
        cc.sys.localStorage.setItem("fullname", "");
        cc.sys.localStorage.setItem("nickname", "");
        cc.sys.localStorage.setItem("gender", "");
        cc.sys.localStorage.setItem("role_id", "");
        cc.sys.localStorage.setItem("department_id", "");
        cc.sys.localStorage.setItem("department_name", "");
        cc.sys.localStorage.setItem("position_id", "");
        cc.sys.localStorage.setItem("position_name", "");
        cc.sys.localStorage.setItem("golds", "");
        cc.sys.localStorage.setItem("coins", "");
        cc.sys.localStorage.setItem("points", "");
        cc.sys.localStorage.setItem("title_name", "");
    },
    
    // 保存信息
    save: function() {
        cc.sys.localStorage.setItem("user_id", this.user_id);
        cc.sys.localStorage.setItem("user_name", this.user_name);
        cc.sys.localStorage.setItem("fullname", this.fullname);
        cc.sys.localStorage.setItem("nickname", this.nickname);
        cc.sys.localStorage.setItem("gender", this.gender);
        cc.sys.localStorage.setItem("role_id", this.role_id);
        cc.sys.localStorage.setItem("department_id", this.department_id);
        cc.sys.localStorage.setItem("department_name", this.department_name);
        cc.sys.localStorage.setItem("position_id", this.position_id);
        cc.sys.localStorage.setItem("position_name", this.position_name);
        cc.sys.localStorage.setItem("golds", this.golds);
        cc.sys.localStorage.setItem("coins", this.coins);
        cc.sys.localStorage.setItem("points", this.points);
        cc.sys.localStorage.setItem("title_name", this.title_name);
    },
    
    // 更新信息
    update: function(params, callback) {
        API.post("user/complete_profile", params, callback);
    },
    
});
