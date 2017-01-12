cc.Class({
    extends: cc.Component,

    properties: {
        // foo: {
        //    default: null,      // The default value will be used only when the component attaching
        //                           to a node for the first time
        //    url: cc.Texture2D,  // optional, default is typeof default
        //    serializable: true, // optional, default is true
        //    visible: true,      // optional, default is true
        //    displayName: 'Foo', // optional
        //    readonly: false,    // optional, default is false
        // },
        // ...
    },
    
    statics: {
        
        // 月赛
        sportMonthList: function(user_id, msg_content, callback) {
            API.get("sports/sports_list", {
                "user_id": user_id,
                "type": "1"
            }, callback);
        },
        
        // 年赛
        sportYearList: function(user_id, msg_content, callback) {
            API.get("sports/sports_list", {
                "user_id": user_id,
                "type": "2"
            }, callback);
        },
        
        // 参赛选手
        players: function(sport_id, callback) {
            API.get("sports/department_msg", {
                "sport_id": sport_id
            }, callback);
        },
        
        // 排行
        rank: function(sport_id, callback) {
            API.get("sports/sport_rank", {
                "sport_id": sport_id
            }, callback);
        },
        
        // 参赛规则
        rule: function(sport_id, callback) {
            API.get("sports/sport_rule", {
                "sport_id": sport_id
            }, callback);
        },
        
        // 报名
        enroll: function(user_id, sport_id, callback) {
            API.post("sports/enrol_sport", {
                "user_id": user_id,
                "sport_id": sport_id
            }, callback);
        },
        
    },

    // use this for initialization
    onLoad: function () {

    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
