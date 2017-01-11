cc.Class({
    extends: cc.Component,
    
    // cell数量
    numberOfRowsCallback: Function,
    // cell高度
    heightOfRowCallback: Function,
    // cell的view
    cellForRowAtIndexCallback: Function,
    // cell点击
    cellForAtIndexClickedCallback: Function,

    properties: {
        
    },

    // use this for initialization
    onLoad: function () {
        this.spawnCount = 20;
        this.items = [];
        this.initialize();
        this.updateTimer = 0;
        this.updateInterval = 0.2;
        this.lastContentPosY = 0;
    },
    
    initialize: function() {
        this.content.height = this.totalCount * (this.itemTemplate.height + this.spacing) + this.spacing; // get total content height
    	for (let i = 0; i < this.spawnCount; ++i) { // spawn items, we only need to do this once
    		let item = cc.instantiate(this.itemTemplate);
    		this.content.addChild(item);
    		item.setPosition(0, -item.height * (0.5 + i) - this.spacing * (i + 1));
    		item.getComponent('Item').updateItem(i, i);
            this.items.push(item);
    	}
    },
    
    update: function(dt) {
        this.updateTimer += dt;
        if (this.updateTimer < this.updateInterval) return; // we don't need to do the math every frame
        this.updateTimer = 0;
    }

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
