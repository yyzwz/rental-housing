

//树木位置标识
Ext.define('Forestry.app.report.TenantAreaZZReport', {
	extend : 'Ext.panel.Panel',
	initComponent : function() {
		var me = this;
		Ext.apply(this, {
			layout : 'border',
			items : 
				[ 
					Ext.create('Forestry.app.report.HouseZZReprot'),
					Ext.create('Forestry.app.TenantZZReprot'),
				]
		});
		this.callParent(arguments);
	}
});

var dataStore = new Ext.data.JsonStore({
	fields:['car', 'food', 'house', 'culture', 'year'],
	data: [
		{car : 10, food : 10, house : 30, culture : 5, year : 2007},
		{car : 11, food : 12, house : 50, culture : 8, year : 2008},
		{car : 12, food : 13, house : 100, culture : 10, year : 2009},
		{car : 12, food : 13, house : 200, culture : 8, year : 2010},
		{car : 20, food : 20, house : 100, culture : 10, year : 2011}
	]
});

// 传感器在Leaflet地图的位置标识
Ext.define('Forestry.app.report.HouseZZReprot', {
	extend : 'Ext.panel.Panel',
	initComponent : function() {
		var housechart=Ext.create('Ext.panel.Panel', {
			title : '产品销售利润分布图',
			width: 400,
			height: 400,
			renderTo: Ext.getBody(),
			layout: 'fit',
			items : [{
				xtype : 'chart',
				store : dataStore,
				animate: true,//是否启用动画效果
				legend: {
					position: 'bottom' //图例位置
				},
				axes: [{
					type: 'Numeric',
					minimum: 0,
					maximum : 60,
					position: 'left',
					fields: ['car', 'food', 'house', 'culture'],
					title: '利润（单位万元）'
				}, {
					type: 'Category',
					position: 'bottom',
					fields: ['year'],
					title: '年份'
				}],
				series : [{
				    type: 'area',
					highlight: true,
					axis: 'left',
					xField: 'year',//x轴字段
					yField: ['car', 'food', 'house', 'culture'],//y轴字段
					title : ['汽车','食品', '住房', '文化'],//配置图例字段说明
					showInLegend: true//是否显示在图里当中
				}]
			}]
		});
	
		
		var panel1 = Ext.create('widget.panel', {
			layout : 'fit',
			items : housechart
		});

		Ext.apply(this, {
			layout : 'fit',
			region : "center",
			items : [ panel1 ]
		});

		this.callParent(arguments);
	}
});

Ext.define('Forestry.app.TenantZZReprot', {
	extend : 'Ext.panel.Panel',
	region : 'north',
	header: false,
	height : '50%',
	split : true,
	
	initComponent : function() {
		var housechart2=Ext.create('Ext.panel.Panel', {
			title : '产品销售利润分布图',
			width: 400,
			height: 400,
			renderTo: Ext.getBody(),
			layout: 'fit',
			items : [{
				xtype : 'chart',
				store : dataStore,
				animate: true,//是否启用动画效果
				legend: {
					position: 'bottom' //图例位置
				},
				axes: [{
					type: 'Numeric',
					minimum: 0,
					maximum : 60,
					position: 'left',
					fields: ['car', 'food', 'house', 'culture'],
					title: '利润（单位万元）'
				}, {
					type: 'Category',
					position: 'bottom',
					fields: ['year'],
					title: '年份'
				}],
				series : [{
				    type: 'area',
					highlight: true,
					axis: 'left',
					xField: 'year',//x轴字段
					yField: ['car', 'food', 'house', 'culture'],//y轴字段
					title : ['汽车','食品', '住房', '文化'],//配置图例字段说明
					showInLegend: true//是否显示在图里当中
				}]
			}]
		});
	
		
		var panel2 = Ext.create('widget.panel', {
			layout : 'fit',
			items : housechart2
		});

		Ext.apply(this, {
			layout : 'fit',
			items : [ panel2 ]
		});

		this.callParent(arguments);
	}
});

