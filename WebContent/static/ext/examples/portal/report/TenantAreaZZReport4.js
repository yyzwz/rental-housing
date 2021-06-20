


Ext.define('Forestry.app.report.TenantAreaZZReport', {
	extend : 'Ext.panel.Panel',
	region : 'north',
	header: false,
	height : '50%',
	split : true,
	
	initComponent : function() {
		
		var qmmqmm=[
			{yearAndMonth :'1月份', department_5 : 10, department_6 : 35,department_7:56},
			{yearAndMonth :'2月份', department_5 : 40, department_6 : 30,department_7:56},
			{yearAndMonth :'3月份', department_5 : 30,department_6 : 30,department_7:56},
			{yearAndMonth :'4月份', department_5 : 20, department_6 : 30,department_7:56},
			{yearAndMonth :'5月份', department_5 : 10, department_6 : 35,department_7:56},
			{yearAndMonth :'6月份', department_5 : 40, department_6 : 30,department_7:56},
			{yearAndMonth :'7月份', department_5 : 30,department_6 : 30,department_7:56},
			{yearAndMonth :'8月份', department_5 : 20, department_6 : 30,department_7:56},
			{yearAndMonth :'9月份', department_5 : 10, department_6 : 35,department_7:56},
			{yearAndMonth :'10月份', department_5 : 40, department_6 : 30,department_7:56},
			{yearAndMonth :'11月份', department_5 : 30,department_6 : 30,department_7:56},
			{yearAndMonth :'12月份', department_5 : 20, department_6 : 30,department_7:56}
		]
		
		var dataStore_data=[
			{yearAndMonth: '1月份',department_5: 85,department_6: 88,department_7: 47}, 
			{yearAndMonth: '2月份',department_5: 54,department_6: 4,department_7: 34}, 
			{yearAndMonth: '3月份',department_5: 78,department_6: 48,department_7: 69}, 
			{yearAndMonth: '4月份',department_5: 17,department_6: 63,department_7: 62}, 
			{yearAndMonth: '5月份',department_5: 92,department_6: 62,department_7: 96}, 
			{yearAndMonth: '6月份',department_5: 76,department_6: 32,department_7: 10}, 
			{yearAndMonth: '7月份',department_5: 74,department_6: 59,department_7: 9}, 
			{yearAndMonth: '8月份',department_5: 37,department_6: 2,department_7: 5}, 
			{yearAndMonth: '9月份',department_5: 0,department_6: 6,department_7: 63}, 
			{yearAndMonth: '10月份',department_5: 89,department_6: 20,department_7: 75}, 
			{yearAndMonth: '11月份',department_5: 15,department_6: 60,department_7: 77},
			{yearAndMonth: '12月份',department_5: 77,department_6: 92,department_7: 20}
		];
		var modelFields=[];
		var departmentIds=[];
		var departmentNames=[];//['开发区','下洋涂','长街村'];
		
		//qmm_data.departmentNames=JSON.parse("['开发区','下洋涂','长街村']")
		
		Ext.Ajax.request({
			async: false,
		    url: '/forestry/sys/department/getTenantInZZReport',
		    method: 'POST',
		    success: function (response, options) {
		    	//Ext.MessageBox.alert('失败',response.responseText);
		    	var result = JSON.parse(response.responseText);
		    	departmentNames=result.departmentNames.split(",");
		    	departmentIds=result.departmentIds.split(",");
		    	modelFields=result.modelFields.split(",");
		    	dataStore_data=result.data;//result.data;
		    	
		    },
		    failure: function (response, options) {
		        Ext.MessageBox.alert('失败', '请求超时或网络故障,错误编号：' + response.status);
		    }
		});
		

    	for (i = 0; i < dataStore_data.length; i++) {
    		var unit=dataStore_data[i];
    		//alert(unit.department_7);
    	}
        
		var dataStore_fields=modelFields;
		var housechart2_fields=departmentIds;
		var dataStore_series=[];
		for (i = 0; i < departmentNames.length; i++) {
			 var departmentName=departmentNames[i];
			 var departmentId=departmentIds[i];
		     var unit={
						type: 'line',
						highlight: {
							size: 9,
							radius: 7
						},
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
			data: dataStore_data
		});
		
		var housechart2=Ext.create('Ext.panel.Panel', {
			title : '区域出租屋趋势图',
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
					title: '出租屋数量'
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

