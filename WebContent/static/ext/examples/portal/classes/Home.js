

//树木位置标识
Ext.define('Ext.app.Home', {
	extend : 'Ext.panel.Panel',
	initComponent : function() {
		var me = this;
		Ext.apply(this, {
			id : 'forestryTenantInMapHome',
			layout : 'border',
			items : 
				[ 
					Ext.create('Forestry.app.Map'), 
					Ext.create('Forestry.app.TenantInMap', {
						cButtons : me.cButtons,
						cName : me.cName
					}) 
				]
		});
		this.callParent(arguments);
	}
});


// 传感器在Leaflet地图的位置标识
Ext.define('Forestry.app.Map', {
	extend : 'Ext.panel.Panel',
	region : 'north',
	title:'宁波市出租房屋GIS全貌',
	// collapsible:true, 
	height : '70%',
	split : true,
	html : '<iframe id="myMap"  src= "' + appBaseUri + '/static/leaflet/showTenantInfoInMap.jsp" width="100%" height="100%" marginwidth="0" framespacing="0" marginheight="0" frameborder="0" ></iframe>',
	initComponent : function() {
		this.callParent(arguments);
	}
});



 Ext.define('Forestry.app.TenantInMap', {
	id:'Forestry.app.TenantInMap',
	extend : 'Ext.grid.Panel',
	plain : true,
	title:'区域出租房屋情况详情',
	border : true,
	region : 'center',
	autoScroll : true,
	split : true,
	initComponent : function() {
		var me = this;

		Ext.define('ModelList', {
			extend : 'Ext.data.Model',
			idProperty : 'id',
			fields : [ {
				name : 'id',
				type : 'long'
			}, 'departmentName','houseId' ,'houseImage','houseOwnerName','houseName', 'roomName','houseAddress' , 'tenantName', 'tenantTel','tenantTel','tenantFromShen','tenantFromShi','tenantFromXian','tenantImage','tenantDesc','tenantWorkOrganization']
		});

		var store = Ext.create('Ext.data.Store', {
			model : 'ModelList',
			// autoDestroy: true,
			autoLoad : false,//禁止自动装载数据
			remoteSort : true,
			pageSize : globalPageSize,
			proxy : {
				type : 'ajax',
				url : appBaseUri + '/sys/Tenant/getTenantListInMap',
				extraParams : {
					start : '0',
					limit : '100000000',
					departmentId:selectedDepartmentIdInMap,
					houseOwnerId:selectedHouseOwnerIdInMap
				},
				reader : {
					type : 'json',
					root : 'data',
					totalProperty : 'totalRecord',
					successProperty : "success"
				}
			},
			sorters : [ {
				property : 'id',
				direction : 'DESC'
			} ]
		});

		var columns = [ {
			text : "ID",
			dataIndex : 'id',
			xtype : "hidden",
			width : '6%',
			sortable : false
		}, {
			text : "区域",
			dataIndex : 'departmentName',
			width : '5%',
			sortable : false
		},{
			text : "房东姓名",
			dataIndex : 'houseOwnerName',
			width : '5%',
			sortable : false
		},{
			text : "房屋",
			dataIndex : 'houseName',
			width : '5%',
			sortable : false
		}, {
			text : "房间",
			dataIndex : 'roomName',
			width : '5%',
			sortable : false
		}, {
			text : "地址",
			dataIndex : 'houseAddress',
			width : '15%',
			sortable : false
		}, {
			text : "租客姓名",
			dataIndex : 'tenantName',
			width : '5%',
			sortable : false
		}, {
			text : "租客电话",
			dataIndex : 'tenantTel',
			width : '10%',
			sortable : false
		},{
			text : "来源省",
			dataIndex : 'tenantFromShen',
			width : '5%',
			sortable : false
		},{
			text : "来源市",
			dataIndex : 'tenantFromShi',
			width : '5%',
			sortable : false
		},{
			text : "来源县",
			dataIndex : 'tenantFromXian',
			width : '5%',
			sortable : false
		},{
			text : "工作单位",
			dataIndex : 'tenantWorkOrganization',
			width : '16%',
			sortable : false
		},{
			text : "照片",
			dataIndex : 'tenantImage',
			xtype : 'actioncolumn',
			width : '4%',
			sortable : false,
			items : [ 
				{
					iconCls : 'icon-pictures',
					tooltip : '房屋图片',
					handler : function(grid, rowIndex, colIndex) {
						var entity = grid.getStore().getAt(rowIndex);
						if(entity.get('houseImage')=="")
							globalObject.msgTip("暂无照片");
						else{
							new Ext.window.Window({
								title : '房屋图片',
								width : 340,
								height : 240,
								bodyPadding : '10 5',
								modal : true,
								autoScroll : true,
								items : [ {
									html :  "<img src='/forestry/static/img/houseImages/house_"+entity.get('houseId')+".jpg' width=300 height=200 />"
								} ]
							}).show();
						}
					}
				},
				{
					iconCls : 'icon-pictures',
					tooltip : '租客图片',
					handler : function(grid, rowIndex, colIndex) {
						var entity = grid.getStore().getAt(rowIndex);
						if(entity.get('tenantImage')=="")
							globalObject.msgTip("暂无照片");
						else{
							new Ext.window.Window({
								title : '租客图片',
								width : 340,
								height : 240,
								bodyPadding : '10 5',
								modal : true,
								autoScroll : true,
								items : [ {
									html :  "<img src='/forestry/static/img/houseImages/tenant_"+entity.get('id')+".jpg' width=300 height=200 />"
								} ]
							}).show();
						}
					}
				}
			]
		},{
			text : "其他描述",
			dataIndex : 'tenantDesc',
			flex : 0.15,
			sortable : false
		}
		];

		Ext.apply(this, {
			id : 'forestryTenantInMapStoreId',
			store : store,
			columns : columns,
			//selModel : Ext.create('Ext.selection.CheckboxModel'),
			bbar : Ext.create('Ext.PagingToolbar', {
				store : store,
				displayInfo : true
			})
		});

		//store.loadPage(1);

		this.callParent(arguments);
	}
	
});
