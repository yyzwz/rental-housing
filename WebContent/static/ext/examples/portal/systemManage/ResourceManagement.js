// 资源管理（菜单管理）
Ext.onReady(function() {
	Ext.tip.QuickTipManager.init();

	var stateStore = new Ext.data.SimpleStore({
		fields : [ 'ItemValue', 'ItemText' ],
		data : [ [ false, '否' ], [ true, '是' ] ]
	});

	Ext.define('App.ResourceManagementWindow', {
		extend : 'Ext.window.Window',
		constructor : function(config) {
			config = config || {};
			Ext.apply(config, {
				title : '资源管理',
				width : 350,
				height : 440,
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
						name : 'menuCode',
						labelAlign: 'right',
						fieldLabel : '菜单代号',
						allowBlank : false,
						maxLength : 50
					}, {
						xtype : 'textfield',
						name : 'menuName',
						labelAlign: 'right',
						fieldLabel : '菜单名称',
						allowBlank : false,
						maxLength : 50
					}, {
						xtype : 'textfield',
						name : 'sortOrder',
						fieldLabel : '排序',
						labelAlign: 'right',
						allowBlank : false,
						vtype : 'alphanum'
					}, {
						xtype : 'textfield',
						name : 'menuConfig',
						fieldLabel : '菜单配置参数',
						labelAlign: 'right',
						maxLength : 200
					}, {
						xtype : 'checkboxgroup',
						name : 'buttons',
						fieldLabel : '按钮显示项',
						labelAlign: 'right',
						maxLength : 200,
						// cls : 'x-check-group-alt',
						items : [ {
							boxLabel : '添加',
							name : 'Add'
						}, {
							boxLabel : '修改',
							name : 'Edit'
						}, {
							boxLabel : '删除',
							name : 'Delete'
						}, {
							boxLabel : '审核',
							name : 'Examine'
						}, {
							boxLabel : '查看',
							name : 'View'
						} ]
					}, {
						xtype : 'combobox',
						name : 'expanded',
						fieldLabel : '是否伸展',
						labelAlign: 'right',
						store : stateStore,
						valueField : 'ItemValue',
						displayField : 'ItemText',
						typeAhead : true,
						queryMode : 'local',
						emptyText : '请选择...',
						allowBlank : false,
						editable : false
					}, {
						xtype : 'combobox',
						name : 'checked',
						fieldLabel : '是否勾选',
						labelAlign: 'right',
						store : stateStore,
						valueField : 'ItemValue',
						displayField : 'ItemText',
						typeAhead : true,
						queryMode : 'local',
						emptyText : '请选择...',
						allowBlank : false,
						editable : false
					}, {
						xtype : 'combobox',
						name : 'leaf',
						fieldLabel : '是否叶子',
						labelAlign: 'right',
						store : stateStore,
						valueField : 'ItemValue',
						displayField : 'ItemText',
						typeAhead : true,
						queryMode : 'local',
						emptyText : '请选择...',
						allowBlank : false,
						editable : false
					}, {
						xtype : 'textfield',
						name : 'url',
						labelAlign: 'right',
						fieldLabel : '创建Tab的路径'
					}, {
						xtype : 'textfield',
						name : 'iconCls',
						labelAlign: 'right',
						fieldLabel : '按钮样式'
					}, new Ext.create('Ext.ux.TreePicker', {
						name : 'parentId',
						fieldLabel : '父节点',
						labelAlign: 'right',
						url : appBaseUri + '/sys/authority/getAuthorityTreePicker?roleId=' + globalRoleId,
						selectParentNode : true,
						displayField : 'text',
						valueField : 'id',
						allowBlank : false,
						anchor : '100%'
					}) ],
					buttons : [ '->', {
						id : 'resourcemanagementwindow-save',
						text : '保存',
						iconCls : 'icon-save',
						width : 80,
						handler : function(btn, eventObj) {
							var window = btn.up('window');
							var form = window.down('form').getForm();
							if (form.isValid()) {
								var vals = form.getValues();
								if (isNaN(vals['sortOrder'])) {
									globalObject.errTip('排序项只能输入数字！');
									return;
								}
								window.getEl().mask('数据保存中，请稍候...');
								var sb = new StringBuffer();
								if (vals['Add'] == 'on') {
									sb.append('Add,');
								}
								if (vals['Edit'] == 'on') {
									sb.append('Edit,');
								}
								if (vals['Delete'] == 'on') {
									sb.append('Delete,');
								}
								if (vals['View'] == 'on') {
									sb.append('View,');
								}
								var buttons = sb.toString();
								Ext.Ajax.request({
									url : appBaseUri + '/sys/authority/saveAuthority',
									params : {
										cmd : vals['cmd'],
										id : vals['id'],
										menuCode : vals['menuCode'],
										menuName : vals['menuName'],
										sortOrder : vals['sortOrder'],
										menuConfig : vals['menuConfig'],
										buttons : buttons,
										expanded : vals['expanded'],
										checked : vals['checked'],
										leaf : vals['leaf'],
										url : vals['url'],
										iconCls : vals['iconCls'],
										parentId : vals['parentId']
									},
									method : "POST",
									success : function(response) {
										if (response.responseText != '') {
											var res = Ext.JSON.decode(response.responseText);
											if (res.success) {
												globalObject.msgTip('操作成功！');
												Ext.getCmp('resourcemanagementgrid').getStore().reload();
											} else {
												globalObject.msgTip('菜单代号不能重复！');
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
						id : 'resourcemanagementwindow-cancel',
						text : '取消',
						iconCls : 'icon-cancel',
						width : 80,
						handler : function() {
							this.up('window').close();
						}
					}, {
						id : 'resourcemanagementwindow-accept',
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
			App.ResourceManagementWindow.superclass.constructor.call(this, config);
		}
	});

	Ext.define('Forestry.app.systemManage.ResourceManagement', {
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
				}, {
					name : 'sortOrder',
					type : 'int'
				}, 'menuCode', 'menuName', 'menuConfig', 'buttons', {
					name : 'expanded',
					type : 'boolean'
				}, {
					name : 'checked',
					type : 'boolean'
				}, {
					name : 'leaf',
					type : 'boolean'
				}, 'url', 'iconCls', {
					name : 'parentId',
					type : 'long'
				} ]
			});

			var store = me.createStore({
				modelName : 'ModelList',
				proxyUrl : appBaseUri + '/sys/authority/getAuthorityPagination',
				proxyDeleteUrl : appBaseUri + '/sys/authority/deleteAuthority',
				extraParams : me.extraParams,
				sortProperty : 'id',
				sortDirection : 'ASC'
			});

			var columns = [ {
				text : "ID",
				dataIndex : 'id',
				width : '3%',
				sortable : false
			}, {
				text : "菜单代号",
				dataIndex : 'menuCode',
				width : '14%',
				sortable : false
			}, {
				text : "菜单名称",
				dataIndex : 'menuName',
				width : '10%',
				sortable : false
			}, {
				text : "创建Tab的路径",
				dataIndex : 'url',
				width : '12%',
				sortable : false
			}, {
				text : "排序",
				dataIndex : 'sortOrder',
				width : '5%',
				sortable : false
			}, {
				text : "菜单配置参数",
				dataIndex : 'menuConfig',
				sortable : false,
				hidden : true
			}, {
				text : "按钮显示项",
				dataIndex : 'buttons',
				width : '12%',
				sortable : false,
				renderer : function(v) {
					var sb = new StringBuffer();
					if (v == null || v == '' || v == undefined) {
						sb.append(' ');
					}
					if (v != null) {
						if (v.contains('Add')) {
							sb.append('添加 ');
						}
						if (v.contains('Edit')) {
							sb.append('修改 ');
						}
						if (v.contains('Delete')) {
							sb.append('删除 ');
						}
						if (v.contains('View')) {
							sb.append('查看 ');
						}
						if (v.contains('Examine')) {
							sb.append('审核 ');
						}
						
					}
					return sb.toString();
				}
			}, {
				text : "是否伸展",
				dataIndex : 'expanded',
				width : '8%',
				sortable : false,
				renderer : function(v) {
					if (v == true) {
						return '是';
					} else {
						return '否';
					}
				}
			}, {
				text : "是否勾选",
				dataIndex : 'checked',
				width : '8%',
				sortable : false,
				renderer : function(v) {
					if (v == true) {
						return '是';
					} else {
						return '否';
					}
				}
			}, {
				text : "是否叶子",
				dataIndex : 'leaf',
				width : '8%',
				sortable : false,
				renderer : function(v) {
					if (v == true) {
						return '是';
					} else {
						return '否';
					}
				}
			}, {
				text : "按钮样式",
				dataIndex : 'iconCls',
				sortable : false,
				hidden : true
			}, {
				text : "父节点ID",
				dataIndex : 'parentId',
				width : '8%',
				sortable : false
			}, {
				xtype : 'actioncolumn',
				width : '8%',
				items : [ {
					iconCls : 'icon-view',
					tooltip : '查看',
					disabled : !globalObject.haveActionMenu(me.cButtons, 'View'),
					handler : function(grid, rowIndex, colIndex) {
						var gridRecord = grid.getStore().getAt(rowIndex);
						var win = new App.ResourceManagementWindow({
							hidden : true
						});
						var form = win.down('form').getForm();
						form.loadRecord(gridRecord);
						form.findField('menuCode').setReadOnly(true);
						form.findField('menuName').setReadOnly(true);
						form.findField('sortOrder').setReadOnly(true);
						form.findField('menuConfig').setReadOnly(true);
						form.findField('buttons').setReadOnly(true);
						var buttons = gridRecord.get('buttons');
						if (buttons != null) {
							if (buttons.contains('Add')) {
								form.findField("Add").setValue('on');
							}
							if (buttons.contains('Edit')) {
								form.findField("Edit").setValue('on');
							}
							if (buttons.contains('Delete')) {
								form.findField("Delete").setValue('on');
							}
							if (buttons.contains('View')) {
								form.findField("View").setValue('on');
							}
						}
						form.findField('expanded').setReadOnly(true);
						form.findField('checked').setReadOnly(true);
						form.findField('leaf').setReadOnly(true);
						form.findField('url').setReadOnly(true);
						form.findField('iconCls').setReadOnly(true);
						form.findField("parentId").getStore().load();
						form.findField('parentId').setReadOnly(true);
						Ext.getCmp('resourcemanagementwindow-save').hide();
						Ext.getCmp('resourcemanagementwindow-cancel').hide();
						Ext.getCmp('resourcemanagementwindow-accept').show();
						win.show();
					}
				}, {
					iconCls : 'edit',
					tooltip : '修改',
					disabled : !globalObject.haveActionMenu(me.cButtons, 'Edit'),
					handler : function(grid, rowIndex, colIndex) {
						var gridRecord = grid.getStore().getAt(rowIndex);
						var win = new App.ResourceManagementWindow({
							hidden : true
						});
						var form = win.down('form').getForm();
						form.loadRecord(gridRecord);
						form.findField("parentId").getStore().load();
						form.findField("cmd").setValue("edit");
						var buttons = gridRecord.get('buttons');
						if (buttons != null) {
							if (buttons.contains('Add')) {
								form.findField("Add").setValue('on');
							}
							if (buttons.contains('Edit')) {
								form.findField("Edit").setValue('on');
							}
							if (buttons.contains('Delete')) {
								form.findField("Delete").setValue('on');
							}
							if (buttons.contains('View')) {
								form.findField("View").setValue('on');
							}
						}
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
				id : 'resourcemanagementgrid',
				store : store,
				columns : columns
			});

			store.loadPage(1);

			this.callParent(arguments);
		},
		onAddClick : function() {
			new App.ResourceManagementWindow().show();
		},
		onViewClick : function() {
			var grid = Ext.getCmp("resourcemanagementgrid");
			var id = grid.getSelectionModel().getSelection()[0].get('id');
			var gridRecord = grid.getStore().findRecord('id', id);
			var win = new App.ResourceManagementWindow({
				hidden : true,
				height : 230
			});
			var form = win.down('form').getForm();
			form.loadRecord(gridRecord);
			form.findField('menuCode').setReadOnly(true);
			form.findField('menuName').setReadOnly(true);
			form.findField('sortOrder').setReadOnly(true);
			form.findField('menuConfig').setReadOnly(true);
			form.findField('buttons').setReadOnly(true);
			var buttons = gridRecord.get('buttons');
			if (buttons != null) {
				if (buttons.contains('Add')) {
					form.findField("Add").setValue('on');
				}
				if (buttons.contains('Edit')) {
					form.findField("Edit").setValue('on');
				}
				if (buttons.contains('Delete')) {
					form.findField("Delete").setValue('on');
				}
				if (buttons.contains('View')) {
					form.findField("View").setValue('on');
				}
			}
			form.findField('expanded').setReadOnly(true);
			form.findField('checked').setReadOnly(true);
			form.findField('leaf').setReadOnly(true);
			form.findField('url').setReadOnly(true);
			form.findField('iconCls').setReadOnly(true);
			form.findField("parentId").getStore().load();
			form.findField('parentId').setReadOnly(true);
			Ext.getCmp('resourcemanagementwindow-save').hide();
			Ext.getCmp('resourcemanagementwindow-cancel').hide();
			Ext.getCmp('resourcemanagementwindow-accept').show();
			win.show();
		}
	});
});