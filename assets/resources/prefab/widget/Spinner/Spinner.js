cc.Class({
    extends: cc.Component,

    properties: {
        nameLabel: cc.Label,
        valueLabel: cc.Label,
        touchButton: cc.Button,
        textOfCellCallback: Function,
        numberOfCellCallback: Function,
        cellClickedCallback: Function,
        spinnerCell: cc.Prefab,
        selectedIndex: 0,
        cells: [cc.Node]
    },

    // use this for initialization
    onLoad: function () {
        this.touchButton.node.on("click", this.spinnerClicked, this);
        cc.loader.loadRes("prefab/widget/Spinner/SpinnerCell", cc.Prefab, function(err, prefab) {
            this.spinnerCell = prefab;
        }.bind(this));
    },
    
    spinnerClicked: function() {
        this.cells = [];
        
        if (cc.director.getScene().getChildByName("SpinnerExpand") === null) {
            cc.loader.loadRes("prefab/widget/Spinner/SpinnerExpand", cc.Prefab, function(err, prefab) {
                if (err !== null) {
                    return cc.error(err.message || err);
                }
                var winSize = cc.director.getWinSize();
                var node = cc.instantiate(prefab);
                node.name = "SpinnerExpand";
                node.setPosition(cc.v2(winSize.width / 2, winSize.height / 2));
                cc.director.getScene().addChild(node, 10000);
                
                var spinnerExpand = node.getComponent("SpinnerExpand");
                var expandLayout = spinnerExpand.expandLayout;
                expandLayout.node.scale = this.node.scale * 0.7;
                var rect = this.touchButton.node.getBoundingBoxToWorld();
                var position = cc.v2(rect.x, rect.y);
                position = cc.v2(position.x - winSize.width / 2 + rect.width / 2, 
                                 position.y - winSize.height / 2 - rect.height / 2 - rect.height);
                expandLayout.node.setPosition(position);
                
                let itemWidth = spinnerExpand.scrollview.node.width;
                let itemHeight = spinnerExpand.scrollview.node.height / 3;
                if (this.numberOfCellCallback !== null && 
                    this.textOfCellCallback !== null) {
                    let length = this.numberOfCellCallback();
                    spinnerExpand.scrollview.content.height = itemHeight * length;
                    for (let i = 0; i < length; i++) {
                        var item = cc.instantiate(this.spinnerCell);
                        item.setPosition(cc.v2(0, -itemHeight / 2 - i * itemHeight));
                        item.width = itemWidth;
                        item.height = itemHeight;
                        spinnerExpand.scrollview.content.addChild(item);
                        
                        var cell = item.getComponent("SpinnerCell");
                        cell.label.string = this.textOfCellCallback(i);
                        cell.line.node.active = i < length - 1;
                        cell.button.node.tag = i;
                        cell.button.node.on("click", this.cellClicked, this);
                        this.cells.push(cell);
                    }
                }
                // 加载
            }.bind(this));
        } else {
            cc.director.getScene().getChildByName("SpinnerExpand").removeFromParent(true);
        }
    },
    
    reloadData: function() {
        this.selectedIndex = 0;
        if (this.numberOfCellCallback !== null && 
            this.textOfCellCallback !== null &&
            this.selectedIndex < this.numberOfCellCallback()) {
            var text = this.textOfCellCallback(this.selectedIndex);
            this.valueLabel.string = text;
        } else {
            this.valueLabel.string = "";
        }
    },
    
    setSelectedIndex: function(index) {
        this.selectedIndex = index;
        if (this.numberOfCellCallback !== null && 
            this.textOfCellCallback !== null &&
            this.selectedIndex < this.numberOfCellCallback()) {
            var text = this.textOfCellCallback(index);
            if (index < this.cells.length) {
                var cell = this.cells[index];
                cell.label.string = text;
                this.valueLabel.string = text;
                this.spinnerClicked();
            }
        }
    },
    
    cellClicked: function(event) {
        this.setSelectedIndex(event.detail.node.tag);
        if (this.cellClickedCallback) {
            this.cellClickedCallback(event.detail.node.tag);
        }
    }

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
