// 林区湿度变化表
Ext.require([ 'Ext.chart.*', 'Ext.layout.container.Fit', 'Ext.window.MessageBox', 'Ext.grid.Panel' ]);

Ext.define('Forestry.app.report.HouseAreaZZReport', {
	extend : 'Ext.panel.Panel',
	initComponent : function() {

		Ext.Ajax.request({
            url: '/forestry/sys/department/getDepartmentListInMap',
            params: { start: 0, limit:100000000 },
            method: 'POST',
            success: function (response, options) {
            	var result = JSON.parse(response.responseText);
                var taotalNum=result.totalRecord;
                var departments=result.data;
                for(var i=0;i<departments.length;i++){
              	  var department =departments[i];
                }
                Ext.MessageBox.alert('成功', '从服务端获取结果: ' + response.responseText);
            },
            failure: function (response, options) {
                Ext.MessageBox.alert('失败', '请求超时或网络故障,错误编号：' + response.status);
            }
        });
		var departmentStoreInZZReport = Ext.create('Ext.data.JsonStore', {
			fields:['ageRange', 'proportion', 'growing'],
			proxy : {
				type : 'ajax',
				url : appBaseUri + '/sys/department/getDepartmentsInZZReport',
				reader : {
					type : 'array',
					root : ''
				}
			}
		});
		var qmm=[
			{ageRange :'小于30岁', proportion : 10, growing : 35, sex : 5},
			{ageRange :'30-40岁', proportion : 40, growing : 30, sex : 7},
			{ageRange :'40-50岁', proportion : 30,growing : 30, sex : 10},
			{ageRange :'50岁以上', proportion : 20, growing : 30, sex : 50}
		]
		var dataStore = new Ext.data.JsonStore({
			fields:['ageRange', 'proportion', 'growing'],
			data: qmm
		});

		var chart=Ext.create('Ext.panel.Panel', {
			title : '员工年龄分布图',
			width: 400,
			height: 400,
			layout: 'fit',
			items : [{
				xtype : 'chart',
				store : departmentStoreInZZReport,
				animate: true,//是否启用动画效果
				legend: {
					position: 'bottom' //图例位置
				},
				shadow: true,
				axes: [{
					type: 'Numeric',
					position: 'left',
					minimum : 0,//数轴最小值
					maximum : 60,//数轴最大值
					fields: ['proportion','growing'],
					title: '百分比'
				}, {
					type: 'Category',
					position: 'bottom',
					fields: ['ageRange'],
					title: '区域出租房屋数量'
				}],
				series : [{
	                type: 'column',//柱状图表序列
	                axis: 'left',
	                xField: 'ageRange',
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
