var Spinner = require("Spinner");
var SpinnerLabel = require("SpinnerLabel");
var UserAPI = require("UserAPI");
const Toast = require("Toast");

cc.Class({
    extends: cc.Component,

    properties: {
        userSpinner: Spinner,
        reasonSpinner: Spinner,
        reasonValue: SpinnerLabel,
        closeButton: cc.Button,
        submitButton: cc.Button,
        
        reasons: [Object],
        users: [Object],
        selectedUser: Object,
        selectedReason: Object
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
        this.submitButton.node.on("click", this.submitButtonClicked, this); 
        
        this.userSpinner.numberOfCellCallback = function() {
            return this.users.length;
        }.bind(this);
        this.userSpinner.textOfCellCallback = function(index) {
            return this.users[index].fullname;
        }.bind(this);
        this.userSpinner.cellClickedCallback = function(index) {
            this.selectedUser = this.users[index];
        }.bind(this);
        
        this.reasonSpinner.numberOfCellCallback = function() {
            return this.reasons.length;
        }.bind(this);
        this.reasonSpinner.textOfCellCallback = function(index) {
            return this.reasons[index].reason;
        }.bind(this);
        this.reasonSpinner.cellClickedCallback = function(index) {
            this.selectedReason = this.reasons[index];
            this.reasonValue.valueLabel.string = this.selectedReason.value;
        }.bind(this);
        
        // 鼓舞理由
        UserAPI.inspireReason(function(msg, data) {
            if (data === null) {
                return Toast.show(msg);
            }
            this.reasons = data;
            if (data.length > 0) {
                this.selectedReason = this.reasons[0];
                this.reasonValue.valueLabel.string = this.selectedReason.value;
                this.reasonSpinner.reloadData();
            }
        }.bind(this));
        // 所有用户
        UserAPI.allUsers(function(msg, data) {
            if (data === null) {
                return Toast.show(msg);
            }
            this.users = data;
            this.userSpinner.reloadData();
            if ((this.selectedUser === null || this.selectedUser === undefined) && data.length > 0) {
                this.selectedUser = this.users[0];
            }
            this.userSpinner.valueLabel.string = this.selectedUser.fullname;
        }.bind(this));
    },
    
    closeButtonClicked: function() {
        this.node.removeFromParent();
    },
    
    submitButtonClicked: function() {
        if (this.selectedUser === null ||
            this.selectedReason === null) {
                return Toast.show("请选择用户和原因");
        }
        UserAPI.inspire(this.selectedUser.user_id, this.selectedReason.reason_id, function(msg, data) {
            if (data === null) {
                return Toast.show(msg);
            }
            this.node.removeFromParent();
            Toast.show("打赏成功");
        }.bind(this));
    }

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
