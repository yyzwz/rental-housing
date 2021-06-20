
Ext.define('Forestry.app.houseManage.TenantQuery', {
	extend : 'Ext.grid.Panel',
	region : 'center',
	initComponent : function() {
		var me = this;
		//alert(globalPageSize);
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
		
		
		
		Ext.define('ModelList', {
			extend : 'Ext.data.Model',
			idProperty : 'id',
			fields : [ {
				name : 'id',
				type : 'long'
			}, 'departmentName', 'houseName', 'roomName','houseAddress' , 'tenantName', 'tenantTel','tenantTel','tenantFromShen','tenantFromShi','tenantFromXian','tenantImage','tenantDesc','tenantWorkOrganization','checkOpion']
		});

		var queryTenantstore = Ext.create('Ext.data.Store', {
			model : 'ModelList',
			// autoDestroy: true,
			autoLoad : true,
			remoteSort : true,
			pageSize : globalPageSize,
			proxy : {
				type : 'ajax',
				url : appBaseUri + '/sys/Tenant/getTenantList',
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


		var columns = [ 
			{
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
			}, {
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
				items : [ {
					iconCls : 'icon-pictures',
					tooltip : '房主图片',
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
				}]
			},{
				text : "其他描述",
				dataIndex : 'tenantDesc',
				flex : 0.2,
				sortable : false
			}
			,{
				text : "审核意见",
				dataIndex : 'checkOpion',
				flex : 0.2,
				sortable : false
			}
		];

		var tenantQueryToolbar = Ext.create('Ext.toolbar.Toolbar', {
			items : [ 
				{
					xtype : 'button',
					text : '导出',
					iconCls : 'icon-excel',
					width : '10%',
					maxWidth : 60,
					handler : function(btn, eventObj) {
						
						
						var me = Ext.getCmp("TentQuerygrid");;
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
						location.href = appBaseUri + "/sys/Tenant/getExportedTenantList?ids=" + ids;
					}
				},{
				xtype : 'textfield',
				id : 'tenantQueryform_tenantName',
				name : 'tenantQueryform_tenantName',
				fieldLabel : '租客姓名',
				labelWidth : 60,
				width : '10%'
			}, {
				xtype : 'textfield',
				id : 'tenantQueryform_tenantIdentify',
				name : 'tenantQueryform_tenantIdentify',
				fieldLabel : '身份证号码',
				labelWidth : 70,
				width : '20%'
			}, {
				xtype : 'textfield',
				id : 'tenantQueryform_tenantWorkOrganization',
				name : 'tenantQueryform_tenantWorkOrganization',
				fieldLabel : '工作单位',
				labelWidth : 70,
				width : '10%'
			}, {
				xtype : 'textfield',
				id : 'tenantQueryform_tenantFromShen',
				name : 'tenantQueryform_tenantFromShen',
				fieldLabel : '来源省',
				labelWidth : 70,
				width : '10%'
			},{
				xtype : 'textfield',
				id : 'tenantQueryform_tenantFromShi',
				name : 'tenantQueryform_tenantFromShi',
				fieldLabel : '来源市',
				labelWidth : 70,
				width : '10%'
			},{
				xtype : 'textfield',
				id : 'tenantQueryform_tenantFromXian',
				name : 'tenantQueryform_tenantFromXian',
				fieldLabel : '来源县',
				labelWidth : 70,
				width : '10%'
			}, {
				xtype : AllDeprtmentQuery_hidden,
				id:'tenantQueryform_departmentSelect',
				fieldLabel : '行政区域',
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
						Ext.getCmp("tenantQueryform-departmentId").setValue(combo.getValue());
						Ext.getCmp("tenantQueryform-departmentName").setValue(combo.getRawValue());
					}
				}
			} ,{
				xtype : 'hiddenfield',
				id : 'tenantQueryform-departmentId',
				name : 'departmentId'
			}, {
				xtype : 'hiddenfield',
				id : 'tenantQueryform-departmentName',
				name : 'departmentName'
			}, {
				xtype : 'button',
				text : '查询',
				iconCls : 'icon-search',
				width : '10%',
				maxWidth : 60,
				handler : function(btn, eventObj) {
					var searchParams = {
							tenantName : encodeURI(Ext.getCmp('tenantQueryform_tenantName').getValue()),
							tenantIdentify : encodeURI(Ext.getCmp('tenantQueryform_tenantIdentify').getValue()),
							tenantWorkOrganization : encodeURI(Ext.getCmp('tenantQueryform_tenantWorkOrganization').getValue()),
							tenantFromShen : encodeURI(Ext.getCmp('tenantQueryform_tenantFromShen').getValue()),
							tenantFromShi : encodeURI(Ext.getCmp('tenantQueryform_tenantFromShi').getValue()),
							tenantFromXian : encodeURI(Ext.getCmp('tenantQueryform_tenantFromXian').getValue()),
							departmentId : encodeURI(Ext.getCmp('tenantQueryform-departmentId').getValue())
					};
					Ext.apply(queryTenantstore.proxy.extraParams, searchParams);
					queryTenantstore.reload();
				}
			}, {
				xtype : 'button',
				text : '重置',
				iconCls : 'icon-reset',
				width : '10%',
				maxWidth : 60,
				handler : function(btn, eventObj) {
					Ext.getCmp('tenantQueryform_tenantName').setValue(null);
					Ext.getCmp('tenantQueryform_tenantIdentify').setValue(null);
					Ext.getCmp('tenantQueryform_tenantWorkOrganization').setValue(null);
					Ext.getCmp('tenantQueryform_tenantFromShen').setValue(null);
					Ext.getCmp('tenantQueryform_tenantFromShi').setValue(null);
					Ext.getCmp('tenantQueryform_tenantFromXian').setValue(null);
					Ext.getCmp('tenantQueryform_departmentSelect').setValue(null);
					Ext.getCmp('tenantQueryform-departmentId').setValue(null);
				}
			} ]
		});

		Ext.apply(this, {
			id:"TentQuerygrid",
			selModel: { selType: 'checkboxmodel' },   //选择框
			store : queryTenantstore,
			columns : columns,
			tbar : tenantQueryToolbar,
			bbar : Ext.create('Ext.PagingToolbar', {
				store : queryTenantstore,
				displayInfo : true
			}),
			listeners : {
				itemdblclick : function(dataview, record, item, index, e) {
					var grid = this;
					var id = grid.getSelectionModel().getSelection()[0].get('id');
					var gridRecord = grid.getStore().findRecord('id', id);
					//var win = new App.TenantQueryWindow({
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