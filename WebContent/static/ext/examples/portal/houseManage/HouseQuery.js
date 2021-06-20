var checkOpionAgree="同意";
var checkOpionDisagree="不同意";
var checkOpionStore = Ext.create("Ext.data.Store", {
	fields: ["ItemText", "ItemValue"],
	data: [
		{ ItemText: checkOpionAgree, ItemValue: 1 },
		{ ItemText: checkOpionDisagree, ItemValue: 2 }
	]
});
Ext.define('Forestry.app.houseManage.HouseQuery', {
	extend : 'Ext.grid.Panel',
	region : 'center',
	initComponent : function() {
		var me = this;
	
		var AllDeprtmentQuery_hidden="hidden"
	        if(globalObject.haveActionMenu(me.cButtons, 'AllDeprtmentQuery'))
	        		AllDeprtmentQuery_hidden="combobox";//判断登录者是否有查询所有部门权限
		var departmentQueryStore = Ext.create('Ext.data.JsonStore', {
			proxy : {
				type : 'ajax',
				url : appBaseUri + '/sys/department/getDepartments',
				reader : {
					type : 'json',
					root : 'list',
					idProperty : 'ItemValue'
				}
			},
			fields : [ 'ItemText', 'ItemValue' ]
		});
		
		var houseTypeNameStore = Ext.create('Ext.data.JsonStore', {
			proxy : {
				type : 'ajax',
				url : appBaseUri + '/sys/houseType/getHouseType',
				reader : {
					type : 'json',
					root : 'list',
					idProperty : 'ItemValue'
				}
			},
			fields : [ 'ItemText', 'ItemValue' ]
		});
		
		Ext.define('ModelList', {
			extend : 'Ext.data.Model',
			idProperty : 'id',
			fields : [ {
				name : 'id',
				type : 'long'
			}, 'departmentName', 'houseTypeName','houseName','houseOwnerName','houseAddress', {
				name : 'registDate',
				type : 'datetime',
				dateFormat : 'Y-m-d'
			},  'houseDesc','houseImage','houseTwoDimensionalCode','checkOpion']
		});

		var queryHousestore = Ext.create('Ext.data.Store', {
			model : 'ModelList',
			// autoDestroy: true,
			autoLoad : true,
			remoteSort : true,
			pageSize : globalPageSize,
			proxy : {
				type : 'ajax',
				url : appBaseUri + '/sys/house/queryHouse',
				extraParams : me.extraParams || null,
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
			text : "编号",
			dataIndex : 'id',
			xtype : "hidden",
			width : '5%'
		}, {
			text : "所属区域",
			dataIndex : 'departmentName',
			width : '8%',
			sortable : false
		},{
			text : "房屋类型",
			dataIndex : 'houseTypeName',
			width : '8%',
			sortable : false
		}, {
			text : "房屋名称",
			dataIndex : 'houseName',
			width : '18%',
			sortable : false
		}, {
			text : "房东姓名",
			dataIndex : 'houseOwnerName',
			width : '9%',
			sortable : false
		}, {
			text : "地址",
			dataIndex : 'houseAddress',
			width : '17%',
			sortable : false
		}, {
			text : "登记日期",
			dataIndex : 'registDate',
			width : '10%',
			sortable : false
		}, {
			text : "描述",
			width : '10%',
			dataIndex : 'houseDesc',
			sortable : false
		}, {
			text : "房屋图片",
			dataIndex : 'houseImage',
			xtype : 'actioncolumn',
			flex : 0.2,
			sortable : false,
			items : [ {
				iconCls : 'icon-pictures',
				tooltip : '房屋图片',
				handler : function(grid, rowIndex, colIndex) {
					
					var entity = grid.getStore().getAt(rowIndex);
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
								html :  "<img src='/forestry/static/img/houseImages/house_"+entity.get('id')+".jpg' width=300 height=200 />"
							} ]
						}).show();
					}
				}
			}]
		} ,{
			text : "审核意见",
			dataIndex : 'checkOpion',
			flex : 0.2,
			sortable : false
		}
		];

		var houseQueryToolbarToolbar = Ext.create('Ext.toolbar.Toolbar', {
			items : [ 
			{
				xtype : 'button',
				text : '导出',
				iconCls : 'icon-excel',
				width : '10%',
				maxWidth : 60,
				handler : function(btn, eventObj) {
					
					
					var me = Ext.getCmp("HouseQuerygrid");;
					var s = me.getSelectionModel().getSelection();
					var ids = [];
					var idProperty = me.idProperty || 'id';
					for (var i = 0, r; r = s[i]; i++) {
						ids.push(r.get(idProperty));
					}
					if (ids.length < 1) {
						globalObject.infoTip('请先选择导出的数据行！');
						return;
					}
					//// 导出地址proxyExportUrl : appBaseUri + '/sys/Tenant/getExportedTenantList',					
					location.href = appBaseUri + "/sys/house/getExportedHouseList?ids=" + ids;
				}
			},
			{
				xtype : 'textfield',
				id : 'houseQueryform_houseName',
				name : 'houseName',
				fieldLabel : '房屋名',
				labelWidth : 60,
				width : '10%'
			}, {
				xtype : 'textfield',
				id : 'houseQueryform_houseOwnerName',
				name : 'houseOwnerName',
				fieldLabel : '房东姓名',
				labelWidth : 60,
				width : '10%'
			},{
				xtype : 'combobox',
				fieldLabel : '房屋类型',
				id:'houseQueryform_houseType',
				labelWidth : 60,
				store : houseTypeNameStore,
				valueField : 'ItemValue',
				displayField : 'ItemText',
				typeAhead : true,
				queryMode : 'remote',
				emptyText : '请选择...',
				editable : false,
				listeners : {
					select : function(combo, record, index) {
						Ext.getCmp("houseQueryform-houseTypeId").setValue(combo.getValue());
						Ext.getCmp("houseQueryform-houseTypeName").setValue(combo.getRawValue());
					}
				}
			} ,{
				xtype : 'hiddenfield',
				id : 'houseQueryform-houseTypeId',
				name : 'houseTypeId'
			}, {
				xtype : 'hiddenfield',
				id : 'houseQueryform-houseTypeName',
				name : 'houseTypeName'
			}, {
				xtype : AllDeprtmentQuery_hidden,
				fieldLabel : '行政区域',
				id:'houseQueryform_departmentSelect',
				labelWidth : 60,
				store : departmentQueryStore,
				valueField : 'ItemValue',
				displayField : 'ItemText',
				typeAhead : true,
				queryMode : 'remote',
				emptyText : '请选择...',
				editable : false,
				listeners : {
					select : function(combo, record, index) {
						Ext.getCmp("houseQueryform-departmentId").setValue(combo.getValue());
						Ext.getCmp("houseQueryform-departmentName").setValue(combo.getRawValue());
					}
				}
			} ,{
				xtype : 'hiddenfield',
				id : 'houseQueryform-departmentId',
				name : 'departmentId'
			}, {
				xtype : 'hiddenfield',
				id : 'houseQueryform-departmentName',
				name : 'departmentName'
			}, {
				xtype : 'combobox',
				fieldLabel : '审核意见',
				labelWidth : 60,
				id:'houseQueryform_checkOpionSelect',
				valueField : 'ItemValue',
				displayField : 'ItemText',
				typeAhead : true,
				store: checkOpionStore,
				queryMode: "local",
				emptyText : '请选择...',
				width : '10%',
				editable : false,
				listeners : {
					select : function(combo, record, index) {
						Ext.getCmp("houseQueryform-checkOption").setValue(combo.getRawValue());
					}
				}
			},{
				xtype : 'hiddenfield',
				id : 'houseQueryform-checkOption'
			},{
				xtype : 'button',
				text : '查询',
				iconCls : 'icon-search',
				width : '10%',
				maxWidth : 60,
				handler : function(btn, eventObj) {
					var searchParams = {
							houseName : encodeURI(Ext.getCmp('houseQueryform_houseName').getValue()),
							houseOwnerName : encodeURI(Ext.getCmp('houseQueryform_houseOwnerName').getValue()),
							houseTypeId : encodeURI(Ext.getCmp('houseQueryform-houseTypeId').getValue()),
							departmentId : encodeURI(Ext.getCmp('houseQueryform-departmentId').getValue()),
							checkOpion : encodeURI(Ext.getCmp('houseQueryform-checkOption').getValue())
					};
					Ext.apply(queryHousestore.proxy.extraParams, searchParams);
					queryHousestore.reload();
				}
			}, {
				xtype : 'button',
				text : '重置',
				iconCls : 'icon-reset',
				width : '10%',
				maxWidth : 60,
				handler : function(btn, eventObj) {
					Ext.getCmp('houseQueryform_houseName').setValue(null);
					Ext.getCmp('houseQueryform_houseOwnerName').setValue(null);
					Ext.getCmp('houseQueryform_houseType').setValue(null);
					Ext.getCmp('houseQueryform_departmentSelect').setValue(null);
					Ext.getCmp('houseQueryform-houseTypeId').setValue(null);
					Ext.getCmp('houseQueryform-departmentId').setValue(null);
					Ext.getCmp('houseQueryform_checkOpionSelect').setValue(null);
					Ext.getCmp('houseQueryform-checkOption').setValue(null);
				}
			} ]
		});

		Ext.apply(this, {
			id : 'HouseQuerygrid',
			store : queryHousestore,
			selModel: { selType: 'checkboxmodel' },   //选择框
			columns : columns,
			tbar : houseQueryToolbarToolbar,
			bbar : Ext.create('Ext.PagingToolbar', {
				store : queryHousestore,
				displayInfo : true
			}),
			listeners : {
				itemdblclick : function(dataview, record, item, index, e) {
					var grid = this;
					var id = grid.getSelectionModel().getSelection()[0].get('id');
					var gridRecord = grid.getStore().findRecord('id', id);
					//var win = new App.HouseQueryWindow({
					//	hidden : true
					//});
					//var form = win.down('form').getForm();
					//form.loadRecord(gridRecord);
					//form.findField('epcId').setReadOnly(true);
					//form.findField('name').setReadOnly(true);
					//form.findField('plantTime').setReadOnly(true);
					//form.findField('entryTime').setReadOnly(true);
					//form.findField('houseName').setReadOnly(true);
					//win.show();
				}
			}
		});

		this.callParent(arguments);
	}
});