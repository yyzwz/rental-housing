



//传感器在Leaflet地图的位置标识
Ext.define('Forestry.app.report.DSJReport', {
	extend : 'Ext.panel.Panel',
	region : 'north',
	header: false,
	height : '70%',
	split : true,
	html : '<iframe id="myMap"  src= "' + appBaseUri + '/static/dsj/index.jsp" width="100%" height="100%" marginwidth="0" framespacing="0" marginheight="0" frameborder="0" ></iframe>',
	initComponent : function() {
		this.callParent(arguments);
	}
});
