//林区光照度分布
Ext.define('Forestry.app.environmentMonitor.LightfallDistribution', {
    extend: 'Ext.panel.Panel',
    html : '<iframe src= "' + appBaseUri + '/static/leaflet/lightfall.html" width="100%" height="100%" marginwidth="0" framespacing="0" marginheight="0" frameborder="0" ></iframe>',
    initComponent: function () {
        this.callParent(arguments);
    }
});