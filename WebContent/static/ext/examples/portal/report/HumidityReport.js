// 林区湿度变化表
Ext.require([ 'Ext.chart.*', 'Ext.layout.container.Fit', 'Ext.window.MessageBox', 'Ext.grid.Panel' ]);

Ext.define('Forestry.app.report.HumidityReport', {
	extend : 'Ext.panel.Panel',
	initComponent : function() {

		var store1 = Ext.create('Ext.data.JsonStore', {
			fields : [ 'name', 'data1', 'mx', 'mn', 'st', 'ed' ],
			autoLoad : true,
			proxy : {
				type : 'ajax',
				url : appBaseUri + '/sys/sensordata/getSensorDataStatistics?sensorType=2',
				reader : {
					type : 'array',
					root : ''
				}
			}
		})

		var chart = Ext.create('Ext.chart.Chart', {
			animate : true,
			shadow : true,
			store : store1,
			axes : [ {
				type : 'Numeric',
				position : 'left',
				fields : [ 'data1' ],
				title : '%',
				grid : true
			}, {
				type : 'Category',
				position : 'bottom',
				fields : [ 'name' ]
			} ],
			series : [ {
				type : 'line',
				axis : 'left',
				gutter : 80,
				xField : 'name',
				yField : [ 'data1' ],
				tips : {
					trackMouse : true,
					width : 150,
					height : 110,
					layout : 'fit',
					renderer : function(klass, item) {
						var storeItem = item.storeItem, data = [ {
							name : 'data1',
							data : storeItem.get('data1')
						} ], i, l, html;

						this.setTitle("时刻：" + storeItem.get('name') + "<br/>平均湿度：" + storeItem.get('data1') + "%" + "<br/>最高湿度：" + storeItem.get('mx') + "%" + "<br/>最低湿度：" + storeItem.get('mn') + "%" + "<br/>开始湿度："
								+ storeItem.get('st') + "%" + "<br/>结束湿度：" + storeItem.get('ed') + "%");
					}
				}
			} ]
		});

		var panel1 = Ext.create('widget.panel', {
			layout : 'fit',
			items : chart
		});

		Ext.apply(this, {
			layout : 'fit',
			region : "center",
			items : [ panel1 ]
		});

		this.callParent(arguments);
	}
});
