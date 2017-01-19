cc.Class({
    extends: cc.Component,

    properties: {
        messageTypeLabel: cc.Label,
        messageLabel: cc.Label,
        height: 120
    },

    // use this for initialization
    onLoad: function () {
        
    },
    
    updateMessage: function(msg_type, msg_content) {
        this.messageTypeLabel.string = msg_type;
        this.messageLabel.string = msg_content;
        let height = this.messageLabel.node.height + 40;
        if (height >= this.height) {
            this.height = height;
        }
    }

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
