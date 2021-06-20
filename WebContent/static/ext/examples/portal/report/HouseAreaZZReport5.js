// 林区湿度变化表
Ext.require([ 'Ext.chart.*', 'Ext.layout.container.Fit', 'Ext.window.MessageBox', 'Ext.grid.Panel' ]);

Ext.define('Forestry.app.report.HouseAreaZZReport', {
	extend : 'Ext.panel.Panel',
	initComponent : function() {

		
		
		
		var qmm=[
			{departmentName :'小于30岁', proportion : 10},
			{departmentName :'30-40岁', proportion : 40},
			{departmentName :'40-50岁', proportion : 30},
			{departmentName :'50岁以上', proportion : 20}
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
			fields:['departmentName', 'proportion','growing'],
			data: qmm
		});

		var housechart=Ext.create('Ext.panel.Panel', {
			title : '区域房屋出租数量图',
			width: 1400,
			height: 400,
			bodyStyle :'overflow-x:visible;overflow-y:scroll',
			layout: 'fit',
			items : [{
				xtype : 'chart',
				store : dataStore,
				//theme: 'Base',//基本主题
				//theme: 'Blue',//蓝色主题
				theme: 'Category1',//彩色主题1
				axes: [{
					type: 'Numeric',
					position: 'left',
					minimum : 0,//数轴最小值
					maximum : 60,//数轴最大值
					fields: ['proportion','growing'],//同时展示2个数据
					title: '数量'
				}, {
					type: 'Category',
					position: 'bottom',
					style: { width: 20 },//这里是宽度
					fields: ['departmentName'],
					label: { rotate: { degrees: 305} },
					title: '区域出租房屋数量'
				}],
				legend : {
					position : 'bottom' //图例位置
				},
				series : [{
				    type: 'column',
					axis: 'left',
					//style: { width: 20 },//这里是宽度
					xField: 'ageRange',//x轴字段
					yField: ['proportion','growing'],//y轴字段
					title : ['房屋数量','租客数量'],//配置图例字段说明
					
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
