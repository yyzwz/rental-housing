// 房屋管理
Ext.onReady(function() {
	Ext.tip.QuickTipManager.init();

	Ext.define('App.TenantManagementWindow', {
		extend : 'Ext.window.Window',
		constructor : function(config) {
			config = config || {};
			Ext.apply(config, {
				title : '租客信息',
				width : 350,
				height : 340,
				bodyPadding : '10 5',
				modal : true,
				layout : 'fit',
				items : [ {
					xtype : 'form',
					fieldDefaults : {
						labelAlign : 'left',
						labelWidth : 90,
						anchor : '100%'
					},
					items : [ {
						name : "cmd",
						xtype : "hidden",
						value : 'new'
					}, {
						xtype : 'hiddenfield',
						name : 'id'
					}, {
						xtype : 'textfield',
						name : 'tenantName',
						fieldLabel : '姓名',
						labelAlign: 'right',
						allowBlank : false,
						maxLength : 20
					}, {
						xtype : 'textfield',
						name : 'tenantIdentify',
						fieldLabel : '身份证号',
						labelAlign: 'right',
						allowBlank : false,
						maxLength : 20
					}, {
						xtype : 'textfield',
						name : 'tenantTel',
						fieldLabel : '电话',
						labelAlign: 'right',
						allowBlank : false,
						maxLength : 20
					},{
						xtype : 'textfield',
						fieldLabel : '工作单位',
						labelAlign: 'right',
						name : 'tenantWorkOrganization',
						maxLength : 20
					},{
						xtype : 'textarea',
						name : 'tenantDesc',
						fieldLabel : '描述信息',
						labelAlign: 'right',
						allowBlank : false,
						maxLength : 200
					}
					],
					buttons : [ '->', {
						id : 'Tenantwindow-save',
						text : '保存',
						iconCls : 'icon-save',
						width : 80,
						handler : function(btn, eventObj) {
							var window = btn.up('window');
							var form = window.down('form').getForm();
							if (form.isValid()) {
								window.getEl().mask('数据保存中，请稍候...');
								var vals = form.getValues();
								Ext.Ajax.request({
									async: false,
									url : appBaseUri + '/sys/Tenant/saveTenant',
									params : {
										cmd : vals['cmd'],
										id : vals['id'],
										tenantName : vals['tenantName'],
										tenantIdentify : vals['tenantIdentify'],
										tenantTel : vals['tenantTel'],
										tenantWorkOrganization : vals['tenantWorkOrganization'],
										tenantDesc : vals['tenantDesc']
									},
									method : "POST",
									success : function(response) {
										if (response.responseText != '') {
											var res = Ext.JSON.decode(response.responseText);
											if (res.success) {
												//saveHouseTwoDimensionalCode
												 globalObject.msgTip('操作成功！');
												 Ext.getCmp('TenantManagementgrid').getStore().reload();
											} else {
												globalObject.errTip('保存失败！');
											}
										}
									},
									failure : function(response) {
										globalObject.errTip('操作失败！');
									}
								});
								window.getEl().unmask();
								window.close();
							}
						}
					}, {
						id : 'Tenantwindow-cancel',
						text : '取消',
						iconCls : 'icon-cancel',
						width : 80,
						handler : function() {
							this.up('window').close();
						}
					}, {
						id : 'Tenantwindow-accept',
						text : '确定',
						hidden : true,
						iconCls : 'icon-accept',
						width : 80,
						handler : function() {
							this.up('window').close();
						}
					}, '->' ]
				} ]
			});
			App.TenantManagementWindow.superclass.constructor.call(this, config);
		}
	});

	Ext.define('Forestry.app.houseManage.TenantManagement', {
		extend : 'Ext.ux.custom.GlobalGridPanel',
		region : 'center',
		initComponent : function() {
			var me = this;
			Ext.define('ModelList', {
				extend : 'Ext.data.Model',
				idProperty : 'id',
				fields : [ {
					name : 'id',
					type : 'long'
				}, 'departmentName', 'tenantIdentify','houseName', 'roomName','houseAddress' , 'tenantName', 'tenantTel','tenantTel','tenantFromShen','tenantFromShi','tenantFromXian','tenantImage','tenantDesc','tenantWorkOrganization']
			});

			var store = me.createStore({
				modelName : 'ModelList',
				// 获取列表
				proxyUrl : appBaseUri + '/sys/Tenant/getTenantList',
				// 删除地址
				proxyDeleteUrl : appBaseUri + '/sys/Tenant/deleteTenant',
				// 导出地址
				proxyExportUrl : appBaseUri + '/sys/Tenant/getExportedTenantList',
				// 审核地址
				proxyCheckUrl : appBaseUri + '/sys/Tenant/checkTenant',
				extraParams : me.extraParams
			});

			var columns = [ 
			{
				text : "ID",
				xtype : "hidden",
				dataIndex : 'id',
				flex : 0.05
			},{
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
			},{
				text : "姓名",
				dataIndex : 'tenantName',
				width : '5%',
				sortable : false
			},{
				text : "身份证号",
				dataIndex : 'tenantIdentify',
				width : '10%',
				sortable : false
			},{
				text : "电话",
				dataIndex : 'tenantTel',
				width : '6%',
				sortable : false
			},{
				text : "来源省",
				dataIndex : 'tenantFromShen',
				width : '4%',
				sortable : false
			},{
				text : "来源市",
				dataIndex : 'tenantFromShi',
				width : '4%',
				sortable : false
			},{
				text : "来源县",
				dataIndex : 'tenantFromXian',
				width : '4%',
				sortable : false
			},{
				text : "工作单位",
				dataIndex : 'tenantWorkOrganization',
				width : '15%',
				sortable : false
			},{
				text : "描述信息",
				dataIndex : 'TenantDesc',
				width : '5%',
				sortable : false
			}, {
				text : "照片",
				dataIndex : 'tenantImage',
				xtype : 'actioncolumn',
				flex : 0.2,
				sortable : false,
				items : [ {
					iconCls : 'icon-pictures',
					tooltip : '房主图片',
					handler : function(grid, rowIndex, colIndex) {
						
						var entity = grid.getStore().getAt(rowIndex);
						new Ext.window.Window({
							title : '房主图片',
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
				}]
			},{
				text : "操作",
				xtype : 'actioncolumn',
				flex : 0.2,
				items : [ {
					iconCls : 'icon-view',
					tooltip : '查看',
					disabled : !globalObject.haveActionMenu(me.cButtons, 'View'),
					handler : function(grid, rowIndex, colIndex) {
						var gridRecord = grid.getStore().getAt(rowIndex);
						var win = new App.TenantManagementWindow({
							hidden : true
						});
						var form = win.down('form').getForm();
						form.loadRecord(gridRecord);

						Ext.getCmp('Tenantwindow-save').hide();
						Ext.getCmp('Tenantwindow-cancel').hide();
						Ext.getCmp('Tenantwindow-accept').show();
						win.show();
					}
				}, {
					iconCls : 'edit',
					tooltip : '修改',
					disabled : !globalObject.haveActionMenu(me.cButtons, 'Edit'),
					handler : function(grid, rowIndex, colIndex) {
						var gridRecord = grid.getStore().getAt(rowIndex);
						var win = new App.TenantManagementWindow({
							hidden : true
						});
						win.setHeight(250);
						var form = win.down('form').getForm();
						form.loadRecord(gridRecord);
						form.findField("cmd").setValue("edit");
						win.show();
					}
				
				},{
					iconCls : 'icon-delete',
					tooltip : '删除',
					disabled : !globalObject.haveActionMenu(me.cButtons, 'Delete'),
					handler : function(grid, rowIndex, colIndex) {
						var entity = grid.getStore().getAt(rowIndex);
						singleId = entity.get('id');
						me.onDeleteClick();
					}
				},{
					iconCls : 'edit',
					tooltip : '审核',
					disabled : !globalObject.haveActionMenu(me.cButtons, 'Examine'),
					handler : function(grid, rowIndex, colIndex) {
				
						var entity = grid.getStore().getAt(rowIndex);
						singleId = entity.get('id');
						me.onCheckClick();
					}
				
				}]
			} ];

			Ext.apply(this, {
				id : 'TenantManagementgrid',
				store : store,
				columns : columns
			});

			store.loadPage(1);

			this.callParent(arguments);
		},
		onAddClick : function() {
			new App.TenantManagementWindow().show();
		},
		onViewClick : function() {
			var grid = Ext.getCmp("TenantManagementgrid");
			var id = grid.getSelectionModel().getSelection()[0].get('id');
			var gridRecord = grid.getStore().findRecord('id', id);
			var win = new App.TenantManagementWindow({
				hidden : true,
				height : 430
			});
			var form = win.down('form').getForm();
			form.loadRecord(gridRecord);
			Ext.getCmp('Tenantwindow-save').hide();
			Ext.getCmp('Tenantwindow-cancel').hide();
			Ext.getCmp('Tenantwindow-accept').show();
			win.show();
		}
	});
});