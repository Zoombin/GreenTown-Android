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
        
        //删除左右两端的空格
        isEmpty: function (str){ 
　　        return str.replace(/(^\s*)|(\s*$)/g, "") === "";
　　    },
        
        isPhone: function(phone) {
            if(!(/^1[34578]\d{9}$/.test(phone))){ 
                return false; 
            } 
            return true;
        }  
    },

    // use this for initialization
    onLoad: function () {

    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
