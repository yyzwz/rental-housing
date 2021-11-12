

//树木位置标识
Ext.define('Forestry.app.report.TenantAreaZZReport', {
	extend : 'Ext.panel.Panel',
	initComponent : function() {
		var me = this;
		Ext.apply(this, {
			layout : 'border',
			items : 
				[ 
					Ext.create('Forestry.app.report.TenantAreaZZReport_1'),
					Ext.create('Forestry.app.TenantAreaZZReport_2'),
				]
		});
		this.callParent(arguments);
	}
});



var dataStore_data=[];
var modelFields_1=[];
var departmentIds_1=[];
var departmentNames_1=[];

Ext.Ajax.request({
	async: false,
    url: '/forestry/sys/department/getTenantInZZReport',
    method: 'POST',
    success: function (response, options) {
    	//Ext.MessageBox.alert('失败',response.responseText);
    	var result = JSON.parse(response.responseText);
    	departmentNames_1=result.departmentPart_1.departmentNames.split(",");
    	departmentIds_1=result.departmentPart_1.departmentIds.split(",");
    	modelFields_1=result.departmentPart_1.modelFields.split(",");
    	dataStore_data_1=result.departmentPart_1.data;
    	
    	departmentNames_2=result.departmentPart_2.departmentNames.split(",");
    	departmentIds_2=result.departmentPart_2.departmentIds.split(",");
    	modelFields_2=result.departmentPart_2.modelFields.split(",");
    	dataStore_data_2=result.departmentPart_2.data;
    },
    failure: function (response, options) {
        Ext.MessageBox.alert('失败', '请求超时或网络故障,错误编号：' + response.status);
    }
});



// 传感器在Leaflet地图的位置标识
Ext.define('Forestry.app.report.TenantAreaZZReport_1', {
	extend : 'Ext.panel.Panel',
	initComponent : function() {
		var dataStore_fields=modelFields_1;
		var housechart2_fields=departmentIds_1;
		var dataStore_series=[];
		for (i = 0; i < departmentNames_1.length; i++) {
			 var departmentName=departmentNames_1[i];
			 var departmentId=departmentIds_1[i];
		     var unit={
						type: 'line',
						highlight: {
							size: 9,
							radius: 7
						},
						axis: 'left',
						smooth: true,//是否平滑曲线
						xField: 'yearAndMonth',//横轴字段
						yField: 'department_5',//纵轴字段
						title : '开发区',//配置图例字段说明
						showInLegend: true//是否显示在图例当中
					};
		     unit.title=departmentName;
		     unit.yField=departmentId;
		     dataStore_series.push(unit);
		}
		
		var dataStore = new Ext.data.JsonStore({
			fields:dataStore_fields,
			data: dataStore_data_1
		});
		
		var housechart=Ext.create('Ext.panel.Panel', {
			title : '区域租客数量趋势图2',
			width: 400,
			height: 400,
			bodyStyle : 'overflow-x:hidden; overflow-y:scroll',
			renderTo: Ext.getBody(),
			layout: 'fit',
			items : [{
				xtype : 'chart',
				store : dataStore,
				animate: true,//是否启用动画效果
				legend: {
					position: 'bottom' //图例位置
				},
				shadow: true,
				axes: [{
					type: 'Numeric',
					position: 'left',
					minimum : 0,//数轴最小值
					maximum : 110,//数轴最大值
					fields: housechart2_fields,
					title: '租客数量'
				}, {
					type: 'Category',
					position: 'bottom',
					fields: ['yearAndMonth'],
					label: { rotate: { degrees: 315} },
					title: '2020年'
				}],
				series : dataStore_series
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

Ext.define('Forestry.app.TenantAreaZZReport_2', {
	extend : 'Ext.panel.Panel',
	region : 'north',
	header: false,
	height : '50%',
	split : true,
	
	initComponent : function() {
		var dataStore_fields=modelFields_2;
		var housechart2_fields=departmentIds_2;
		var dataStore_series=[];
		for (i = 0; i < departmentNames_2.length; i++) {
			 var departmentName=departmentNames_2[i];
			 var departmentId=departmentIds_2[i];
		     var unit={
						type: 'line',
						highlight: {
							size: 9,
							radius: 7
						},
						smooth: true,//是否平滑曲线
						axis: 'left',
						xField: 'yearAndMonth',//横轴字段
						yField: 'department_5',//纵轴字段
						title : '开发区',//配置图例字段说明
						showInLegend: true//是否显示在图例当中
					};
		     unit.title=departmentName;
		     unit.yField=departmentId;
		     dataStore_series.push(unit);
		}
		var dataStore = new Ext.data.JsonStore({
			fields:dataStore_fields,
			data: dataStore_data_2
		});
		var housechart2=Ext.create('Ext.panel.Panel', {
			title : '区域租客数量趋势图1',
			width: 400,
			height: 400,
			bodyStyle : 'overflow-x:hidden; overflow-y:scroll',
			renderTo: Ext.getBody(),
			layout: 'fit',
			items : [{
				xtype : 'chart',
				store : dataStore,
				animate: true,//是否启用动画效果
				legend: {
					position: 'bottom' //图例位置
				},
				shadow: true,
				axes: [{
					type: 'Numeric',
					position: 'left',
					minimum : 0,//数轴最小值
					maximum : 110,//数轴最大值
					fields: housechart2_fields,
					title: '租客数量'
				}, {
					type: 'Category',
					position: 'bottom',
					fields: ['yearAndMonth'],
					label: { rotate: { degrees: 315} },
					title: '2020年'
				}],
				series : dataStore_series
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

