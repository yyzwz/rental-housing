Ext.define('Ext.app.PortalColumn', {
    extend: 'Ext.container.Container',
    alias: 'widget.portalcolumn',

    requires: [
        'Ext.layout.container.Anchor',
        'Ext.app.Portlet'
    ],

    layout: 'anchor',
    defaultType: 'portlet',
    cls: 'x-portal-column'
});