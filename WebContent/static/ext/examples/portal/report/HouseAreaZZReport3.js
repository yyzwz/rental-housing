// 林区湿度变化表
Ext.require([ 'Ext.chart.*', 'Ext.layout.container.Fit', 'Ext.window.MessageBox', 'Ext.grid.Panel' ]);

Ext.define('Forestry.app.report.HouseAreaZZReport', {
	extend : 'Ext.panel.Panel',
	initComponent : function() {

		
		
		
		var qmm=[
			{departmentName :'小于30岁', proportion : 10, growing : 35, sex : 5},
			{departmentName :'30-40岁', proportion : 40, growing : 30, sex : 7},
			{departmentName :'40-50岁', proportion : 30,growing : 30, sex : 10},
			{departmentName :'50岁以上', proportion : 20, growing : 30, sex : 50}
		]
		
		Ext.Ajax.request({
			async: false,
            url: '/forestry/sys/department/getDepartmentsInZZReport',
            method: 'POST',
            success: function (response, options) {
            	//alert(response.responseText);
            	qmm = JSON.parse(response.responseText).list;
                
            },
            failure: function (response, options) {
                Ext.MessageBox.alert('失败', '请求超时或网络故障,错误编号：' + response.status);
            }
        });
		var dataStore = new Ext.data.JsonStore({
			fields:['departmentName', 'proportion'],
			data: qmm
		});

		var chart=Ext.create('Ext.panel.Panel', {
			title : '区域房屋出租数量图',
			width: 400,
			height: 400,
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
					maximum : 100,//数轴最大值
					fields: ['proportion'],
					title: '数量'
				}, {
					type: 'Category',
					position: 'bottom',
					fields: ['departmentName'],
					title: '区域出租房屋数量'
				}],
				series : [{
	                type: 'column',//柱状图表序列
	                axis: 'left',
	                xField: 'departmentName',
	                yField: 'proportion',
					title : '数量比例'
	            }]
			}]
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
