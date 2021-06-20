Ext.define('Ext.app.Home',{ // 起始页
	extend : 'Ext.form.Panel',
	initComponent : function() {
		Ext.apply(this,{
			autoScroll : true,
			defaults : {
				defaults : {
					ui : 'light',
					closable : false
				}
			},
			items : [ {
				id : 'c1',
				items : [ {
					id : 'p1',
					title : '欢迎使用宁波市房租后台管理系统',
					style : 'width:400px;height:400px;padding:0px; line-height:22px;width:800;',
					html : '<center><img  style=\'width:100%;height:100%;\' src = "' + appBaseUri + '/static/leaflet/images/zhen.png" width = "501" height = "650"/></center>'
				} ]
			} ],
			isReLayout : false
		});
		this.callParent(arguments);
	}
});
