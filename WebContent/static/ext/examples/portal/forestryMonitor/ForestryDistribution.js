// 树木位置分布
Ext.define('Forestry.app.forestryMonitor.ForestryDistribution', {
	extend : 'Ext.panel.Panel',
	// style : 'padding:10px;',
	html : '<iframe src= "' + appBaseUri + '/static/leaflet/forestry.html" width="100%" height="100%" marginwidth="0" framespacing="0" marginheight="0" frameborder="0" ></iframe>',
	initComponent : function() {
		this.callParent(arguments);
	}
});