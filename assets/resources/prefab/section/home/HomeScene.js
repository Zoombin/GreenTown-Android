var Config = require("Config");

cc.Class({
    extends: cc.Component,

    properties: {
        closeButton: cc.Button,
        
        departmentButton: cc.Button,
        giftButton: cc.Button,
        inspireButton: cc.Button,
        spurButton: cc.Button,
        taskButton: cc.Button,
        messageButton: cc.Button,
    },

    onLoad: function() {
        cc.eventManager.addListener({
            event: cc.EventListener.KEYBOARD,
            onKeyReleased: function(keyCode, event) {
                if (keyCode == cc.KEY.back) {
                    this.node.removeFromParent();
                }
            }}, this.node);
        this.closeButton.node.on("click", this.closeButtonClicked, this); 
        
        this.departmentButton.node.on("click", this.departmentButtonClicked, this);  
        this.giftButton.node.on("click", this.giftButtonClicked, this);  
        this.inspireButton.node.on("click", this.inspireButtonClicked, this);  
        this.spurButton.node.on("click", this.spurButtonClicked, this);  
        this.taskButton.node.on("click", this.taskButtonClicked, this);   
        this.messageButton.node.on("click", this.messageButtonClicked, this);   
    },
    
    closeButtonClicked: function() {
        this.node.removeFromParent();
    },
    
    departmentButtonClicked: function() {
        Config.show("prefab/section/user/CompleteDepartmentScene");
    },
    
    giftButtonClicked: function() {
        
    },
    
    inspireButtonClicked: function() {
        
    },
    
    spurButtonClicked: function() {
        
    },
    
    taskButtonClicked: function() {
        
    },
    
    messageButtonClicked: function() {
        
    },
});
