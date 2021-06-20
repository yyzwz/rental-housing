//林区温度分布
Ext.define('Forestry.app.environmentMonitor.TemperatureDistribution', {
    extend: 'Ext.panel.Panel',
    html : '<iframe src= "' + appBaseUri + '/static/leaflet/temperature.html" width="100%" height="100%" marginwidth="0" framespacing="0" marginheight="0" frameborder="0" ></iframe>',
    initComponent: function () {
        this.callParent(arguments);
    }
});