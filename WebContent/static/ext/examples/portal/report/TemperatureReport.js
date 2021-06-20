// 林区温度变化表
Ext.require([ 'Ext.chart.*', 'Ext.layout.container.Fit', 'Ext.window.MessageBox', 'Ext.grid.Panel' ]);

Ext.define('Forestry.app.report.TemperatureReport', {
	extend : 'Ext.panel.Panel',
	initComponent : function() {

		var store1 = Ext.create('Ext.data.JsonStore', {
			fields : [ 'name', 'data1', 'mx', 'mn', 'st', 'ed' ],
			autoLoad : true,
			proxy : {
				type : 'ajax',
				url : appBaseUri + '/sys/sensordata/getSensorDataStatistics?sensorType=1',
				reader : {
					type : 'array',
					root : ''
				}
			}
		})
		
		var qq=[
		["06:00", 25.62, 32, 0.3, "32", "31.9"], 
		["07:00", 21.37, 32, 0.3, "31.8", "0.3"],
		 ["06:00", 25.62, 32, 0.3, "32", "31.9"],
		 ["07:00", 21.37, 32, 0.3, "31.8", "0.3"],
		 ["08:00", 21.33, 32, 0.2, "0.2", "32"],
		 ["09:00", 21.37, 32, 0.2, "32", "0.2"],
		 ["10:00", 20.73, 31.4, 0.2, "0.2", "31.4"],
		 ["11:00", 19.37, 29.3, 0.3, "28.5", "0.3"],
		 ["12:00", 19.1, 28.8, 0.3, "0.3", "28.2"],
		 ["13:00", 18.73, 28.3, 0.3, "27.6", "0.3"],
		 ["14:00", 18.7, 28.3, 0.2, "27.4", "0.2"],
		 ["15:00", 18.77, 28, 0.3, "0.3", "28"],
		 ["16:00", 19.03, 28.5, 0.2, "28.4", "0.2"],
		 ["17:00", 19.1, 28.6, 0.2, "0.2", "28.6"],
		 ["18:00", 19.58, 29.7, 0.2, "29.7", "0.2"],
		 ["19:00", 20.1, 30.1, 0.3, "0.3", "30.1"]];
		
		var dataStore = new Ext.data.JsonStore({
			fields : [ 'name', 'data1', 'mx', 'mn', 'st', 'ed' ],
			data: qq
		});

		var chart = Ext.create('Ext.chart.Chart', {
			animate : true,
			shadow : true,
			store : dataStore,
			axes : [ {
				type : 'Numeric',
				position : 'left',
				fields : [ 'data1' ],
				title : '℃',
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
						this.setTitle("时刻：" + storeItem.get('name') + "<br/>平均温度：" + storeItem.get('data1') + "℃" + "<br/>最高温度：" + storeItem.get('mx') + "℃" + "<br/>最低温度：" + storeItem.get('mn') + "℃" + "<br/>开始温度："
								+ storeItem.get('st') + "℃" + "<br/>结束温度：" + storeItem.get('ed') + "℃");
					}
				}
			} ]
		});

		var panel1 = Ext.create('widget.panel', {
			layout : 'fit',
			/**
			 * <code>
			tbar : [ {
				text : '保存报表',
				handler : function() {
					Ext.MessageBox.confirm('系统信息', '确认将报表保存成图片？', function(choice) {
						if (choice == 'yes') {
							chart.save({
								type : 'image/png'
							});
						}
					});
				}
			} ],
			 *</code>
			 */
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
