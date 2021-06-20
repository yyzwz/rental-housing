// 区域管理
Ext.onReady(function() {
	Ext.tip.QuickTipManager.init();

	Ext.define('App.DepartmentManagementWindow', {
		extend : 'Ext.window.Window',
		constructor : function(config) {
			config = config || {};
			Ext.apply(config, {
				title : '区域信息',
				width : 350,
				height : 240,
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
						name : 'name',
						fieldLabel : '区域名称',
						labelAlign: 'right',
						emptyText : '请输入区域名称',
						allowBlank : false,
						maxLength : 30
					}, {
						xtype : 'textarea',
						name : 'remark',
						labelAlign: 'right',
						fieldLabel : '备注信息'
					} ],
					buttons : [ '->', {
						id : 'deptmanagementwindow-save',
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
									url : appBaseUri + '/sys/department/saveDepartment',
									params : {
										cmd : vals['cmd'],
										id : vals['id'],
										name : vals['name'],
										remark : vals['remark']
									},
									method : "POST",
									success : function(response) {
										if (response.responseText != '') {
											var res = Ext.JSON.decode(response.responseText);
											if (res.success) {
												globalObject.msgTip('操作成功！');
												Ext.getCmp('departmentmanagementgrid').getStore().reload();
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
						id : 'deptmanagementwindow-cancel',
						text : '取消',
						iconCls : 'icon-cancel',
						width : 80,
						handler : function() {
							this.up('window').close();
						}
					}, {
						id : 'deptmanagementwindow-accept',
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
			App.DepartmentManagementWindow.superclass.constructor.call(this, config);
		}
	});

	Ext.define('Forestry.app.systemManage.DepartmentManagement', {
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
				}, 'name', 'remark' ]
			});

			var store = me.createStore({
				modelName : 'ModelList',
				// 获取列表
				proxyUrl : appBaseUri + '/sys/department/getDepartmentList',
				// 删除地址
				proxyDeleteUrl : appBaseUri + '/sys/department/deleteDepartment',
				extraParams : me.extraParams
			});

			var columns = [ {
				text : "ID",
				xtype : "hidden",
				dataIndex : 'id',
				flex : 0.05
			}, {
				text : "区域名称",
				dataIndex : 'name',
				flex : 0.15
			}, {
				text : "备注信息",
				dataIndex : 'remark',
				flex : 0.2,
				sortable : false
			}, {
				xtype : 'actioncolumn',
				flex : 0.2,
				items : [ {
					iconCls : 'icon-view',
					tooltip : '查看',
					disabled : !globalObject.haveActionMenu(me.cButtons, 'View'),
					handler : function(grid, rowIndex, colIndex) {
						var gridRecord = grid.getStore().getAt(rowIndex);
						var win = new App.DepartmentManagementWindow({
							hidden : true
						});
						var form = win.down('form').getForm();
						form.loadRecord(gridRecord);
						form.findField('name').setReadOnly(true);
						form.findField('remark').setReadOnly(true);
						Ext.getCmp('deptmanagementwindow-save').hide();
						Ext.getCmp('deptmanagementwindow-cancel').hide();
						Ext.getCmp('deptmanagementwindow-accept').show();
						win.show();
					}
				}, {
					iconCls : 'edit',
					tooltip : '修改',
					disabled : !globalObject.haveActionMenu(me.cButtons, 'Edit'),
					handler : function(grid, rowIndex, colIndex) {
						var gridRecord = grid.getStore().getAt(rowIndex);
						var win = new App.DepartmentManagementWindow({
							hidden : true
						});
						win.setHeight(250);
						var form = win.down('form').getForm();
						form.loadRecord(gridRecord);
						form.findField("cmd").setValue("edit");
						win.show();
					}
				}, {
					iconCls : 'icon-delete',
					tooltip : '删除',
					disabled : !globalObject.haveActionMenu(me.cButtons, 'Delete'),
					handler : function(grid, rowIndex, colIndex) {
						var entity = grid.getStore().getAt(rowIndex);
						singleId = entity.get('id');
						me.onDeleteClick();
					}
				} ]
			} ];

			Ext.apply(this, {
				id : 'departmentmanagementgrid',
				store : store,
				columns : columns
			});

			store.loadPage(1);

			this.callParent(arguments);
		},
		onAddClick : function() {
			new App.DepartmentManagementWindow().show();
		},
		onViewClick : function() {
			var grid = Ext.getCmp("departmentmanagementgrid");
			var id = grid.getSelectionModel().getSelection()[0].get('id');
			var gridRecord = grid.getStore().findRecord('id', id);
			var win = new App.DepartmentManagementWindow({
				hidden : true,
				height : 230
			});
			var form = win.down('form').getForm();
			form.findField('name').setReadOnly(true);
			form.findField('remark').setReadOnly(true);
			form.loadRecord(gridRecord);
			Ext.getCmp('deptmanagementwindow-save').hide();
			Ext.getCmp('deptmanagementwindow-cancel').hide();
			Ext.getCmp('deptmanagementwindow-accept').show();
			win.show();
		}
	});
});