var API = cc.Class({
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
        
        baseURL: "http://112.124.98.9:3004/api/",
        
        post: function(url, params, callback) {
            var xhr = cc.loader.getXMLHttpRequest();
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4) {
                    if (xhr.status == 200) {
                        var json = JSON.parse(xhr.responseText);
                        if (json.error === 0) {
                            callback(json.msg, json.data);
                        } else {
                            callback(json.msg, null);
                        }
                    } else {
                        callback(xhr.statusText, null);
                    }
                }
            };
            
            var paramsString = "";
            for (var key in params) { 
                paramsString += key + "=" + encodeURI(params[key]) + "&";
            }
            if (paramsString.length > 0) {
                paramsString = "?" + paramsString.substring(0, paramsString.length - 1);
            }
            var requestURL = this.baseURL + url + paramsString;
            cc.log("POST  " + requestURL);
            xhr.open("POST", requestURL, true);
            xhr.send();
        },
        
        get: function(url, params, callback) {
            var xhr = cc.loader.getXMLHttpRequest();
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4) {
                    if (xhr.status == 200) {
                        var json = JSON.parse(xhr.responseText);
                        if (json.error === 0) {
                            callback(json.msg, json.data);
                        } else {
                            callback(json.msg, null);
                        }
                    } else {
                        callback(xhr.statusText, null);
                    }
                }
            };
            
            var paramsString = "";
            for (var key in params) { 
                paramsString += key + "=" + encodeURI(params[key]) + "&";
            }
            if (paramsString.length > 0) {
                paramsString = paramsString.substring(0, paramsString.length - 1);
            }
            var requestURL = this.baseURL + url + "?" + paramsString;
            cc.log("GET  " + requestURL);
            xhr.open("GET", requestURL, true);
            xhr.send();
        }
        
    },

    // use this for initialization
    onLoad: function () {

    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
