// 用户管理
Ext.onReady(function() {
	Ext.tip.QuickTipManager.init();
	
    var grade1="1级";
    var grade2="2级";
	var gradeStore = Ext.create("Ext.data.Store", {
	    fields: ["ItemText", "ItemValue"],
	    data: [
	        { ItemText: grade1, ItemValue: 1 },
	        { ItemText: grade2, ItemValue: 2 }
	    ]
	});
	
	var roleNameStore = Ext.create('Ext.data.JsonStore', {
		proxy : {
			type : 'ajax',
			url : appBaseUri + '/sys/sysuser/getRoleName',
			reader : {
				type : 'json',
				root : 'list',
				idProperty : 'ItemValue'
			}
		},
		fields : [ 'ItemText', 'ItemValue' ]
	});
	var departmentStore = Ext.create('Ext.data.JsonStore', {
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

	Ext.define('App.UserManagementWindow', {
		extend : 'Ext.window.Window',
		constructor : function(config) {
			config = config || {};
			Ext.apply(config, {
				title : '用户信息',
				width : 350,
				height : 330,
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
						name : 'userName',
						fieldLabel : '用户名',
						labelAlign: 'right',
						allowBlank : false,
						maxLength : 30
					}, {
						xtype : 'textfield',
						name : 'password',
						fieldLabel : '密码',
						labelAlign: 'right',
						allowBlank : false,
						maxLength : 32
					}, {
						xtype : 'textfield',
						name : 'realName',
						fieldLabel : '姓名',
						labelAlign: 'right',
						maxLength : 30
					}, {
						xtype : 'textfield',
						name : 'tel',
						fieldLabel : '手机号',
						labelAlign: 'right',
						maxLength : 15
					}, {
						xtype : 'textfield',
						name : 'email',
						fieldLabel : '邮箱',
						labelAlign: 'right',
						//vtype : 'email',
						maxLength : 30
					}, {
						xtype : 'textfield',
						name : 'lastLoginTime',
						fieldLabel : '最后登录时间',
						labelAlign: 'right',
						hidden : true
					},{
						xtype : 'combobox',
						fieldLabel : '区域',
						id:'usermanagementwindow-departmentIdCombobox',
						labelAlign: 'right',
						store : departmentStore,
						valueField : 'ItemValue',
						displayField : 'ItemText',
						typeAhead : true,
						queryMode : 'remote',
						emptyText : '请选择...',
						allowBlank : false,
						editable : false,
						listeners : {
							select : function(combo, record, index) {
								Ext.getCmp("usermanagementform-departmentId").setValue(combo.getValue());
								Ext.getCmp("usermanagementform-departmentName").setValue(combo.getRawValue());
							}
						}
					}, {
						xtype : 'hiddenfield',
						id : 'usermanagementform-departmentId',
						name : 'departmentId'
					}, {
						xtype : 'hiddenfield',
						id : 'usermanagementform-departmentName',
						name : 'departmentName'
					}, {
						xtype : 'combobox',
						id:'usermanagementwindow-roleIdCombobox',
						fieldLabel : '角色',
						labelAlign: 'right',
						name : 'roleName',
						store : roleNameStore,
						valueField : 'ItemValue',
						displayField : 'ItemText',
						typeAhead : true,
						queryMode : 'remote',
						emptyText : '请选择...',
						allowBlank : false,
						editable : false,
						listeners : {
							select : function(combo, record, index) {
								Ext.getCmp("usermanagementform-role").setValue(combo.getValue());
							}
						}
					}, {
						xtype : 'hiddenfield',
						id : 'usermanagementform-role',
						name : 'role'
					},{
						xtype : 'combobox',
						id:'usermanagementwindow-userGradeCombobox',
						fieldLabel : '级别',
						labelAlign: 'right',
						valueField : 'ItemValue',
						displayField : 'ItemText',
						typeAhead : true,
						store: gradeStore,
						queryMode: "local",
						emptyText : '请选择...',
						allowBlank : false,
						editable : false,
						listeners : {
							select : function(combo, record, index) {
								Ext.getCmp("usermanagementform-grade").setValue(combo.getRawValue());
							}
						}
					},{
						xtype : 'hiddenfield',
						id : 'usermanagementform-grade',
						name : 'grade'
					} ],
					buttons : [ '->', {
						id : 'usermanagementwindow-save',
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
									url : appBaseUri + '/sys/sysuser/saveSysUser',
									params : {
										cmd : vals['cmd'],
										id : vals['id'],
										userName : vals['userName'],
										password : vals['password'],
										realName : vals['realName'],
										tel : vals['tel'],
										email : vals['email'],
										role : vals['role'],
										departmentId : vals['departmentId'],
										departmentName : vals['departmentName'],
										userGrade : Ext.getCmp("usermanagementwindow-userGradeCombobox").getRawValue()
									},
									method : "POST",
									success : function(response) {
										if (response.responseText != '') {
											var res = Ext.JSON.decode(response.responseText);
											if (res.success) {
												globalObject.msgTip('操作成功！');
												Ext.getCmp('usermanagementgrid').getStore().reload();
											} else {
												globalObject.errTip('用户名已存在，请输入唯一值！');
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
						id : 'usermanagementwindow-cancel',
						text : '取消',
						iconCls : 'icon-cancel',
						width : 80,
						handler : function() {
							this.up('window').close();
						}
					}, {
						id : 'usermanagementwindow-accept',
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
			App.UserManagementWindow.superclass.constructor.call(this, config);
		}
	});


	Ext.define('Forestry.app.systemManage.UserManagement', {
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
				}, 'userName', 'password', 'realName', 'tel', 'email','departmentName',
				{
					name : 'departmentId',
					type : 'long'
				},{
					name : 'lastLoginTime',
					type : 'datetime',
					dateFormat : 'Y-m-d H:i:s'
				}, {
					name : 'role',
					type : 'short'
				}, 'roleName','userGrade' ]
			});

			var store = me.createStore({
				modelName : 'ModelList',
				proxyUrl : appBaseUri + '/sys/sysuser/getSysUserList',
				proxyDeleteUrl : appBaseUri + '/sys/sysuser/deleteSysUser',
				proxyExportUrl : appBaseUri + '/sys/sysuser/exportSysUser',
				extraParams : me.extraParams
			});
			// 查询
			Ext.define('App.UserQueryWindow', {
				extend : 'Ext.window.Window',
				constructor : function(config) {
					config = config || {};
					Ext.apply(config, {
						title : '用户信息查询',
						width : 350,
						height : 250,
						bodyPadding : '10 5',
						layout : 'fit',
						modal : true,
						items : [ {
							xtype : 'form',
							fieldDefaults : {
								labelAlign : 'left',
								labelWidth : 90,
								anchor : '100%'
							},
							items : [ 
							{
								xtype : 'textfield',
								name : 'userQuery-real_name',
								id : 'userQuery-real_name',
								fieldLabel : '姓名'
							}, {
								xtype : 'combobox',
								fieldLabel : '部门',
								store : departmentStore,
								valueField : 'ItemValue',
								displayField : 'ItemText',
								typeAhead : true,
								queryMode : 'remote',
								emptyText : '请选择...',
								allowBlank : false,
								editable : false,
								listeners : {
									select : function(combo, record, index) {
										Ext.getCmp("userQueryform-departmentId").setValue(combo.getValue());
									}
								}
							}, {
								xtype : 'hiddenfield',
								id : 'userQueryform-departmentId',
								name : 'userQueryform-departmentId'
							} ],
							buttons : [ '->', {
								text : '确定',
								iconCls : 'icon-accept',
								width : 80,
								handler : function() {
									//
									var searchParams = {
											real_name : encodeURI(Ext.getCmp('userQuery-real_name').getValue()),
											departmentId:Ext.getCmp('userQueryform-departmentId').getValue(),
										};
									Ext.apply(store.proxy.extraParams, searchParams);
									store.reload();
									this.up('window').close();
								}
							}, '->' ]
						} ]
					});
					App.UserQueryWindow.superclass.constructor.call(this, config);
				}
			});
			var columns = [ {
				text : "ID",
				xtype : "hidden",
				dataIndex : 'id',
				width : '2%'
			}, {
				text : "用户名",
				dataIndex : 'userName',
				width : '5%'
			}, {
				text : "所属区域",
				dataIndex : 'departmentName',
				width : '5%'
			}, {
				text : "姓名",
				dataIndex : 'realName',
				width : '10%'
			}, {
				text : "手机号",
				dataIndex : 'tel',
				width : '14%'
			}, {
				text : "邮箱",
				dataIndex : 'email',
				width : '10%'
			}, {
				text : "最后登录时间",
				dataIndex : 'lastLoginTime',
				width : '10%'
			}, {
				text : "级别",
				dataIndex : 'userGrade',
				width : '12%',
				sortable : false
			},{
				text : "角色",
				dataIndex : 'roleName',
				width : '12%',
				sortable : false
			},{
				text : "roleId",
				dataIndex : 'role',
				hidden : true,
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
						var win = new App.UserManagementWindow({
							hidden : true
						});
						var form = win.down('form').getForm();
						form.loadRecord(gridRecord);
						form.findField('userName').setReadOnly(true);
						form.findField('password').hide();
						form.findField('realName').setReadOnly(true);
						form.findField('tel').setReadOnly(true);
						form.findField('email').setReadOnly(true);
						form.findField('lastLoginTime').show().setReadOnly(true);
						form.findField('roleName').setValue(gridRecord.get('roleName')).setReadOnly(true);
						Ext.getCmp("usermanagementwindow-departmentIdCombobox").setRawValue(gridRecord.get('departmentName'));
						Ext.getCmp("usermanagementwindow-roleIdCombobox").setRawValue(gridRecord.get('roleName'));
						Ext.getCmp("usermanagementwindow-userGradeCombobox").setRawValue(gridRecord.get('userGrade'));
						Ext.getCmp('usermanagementwindow-save').hide();
						Ext.getCmp('usermanagementwindow-cancel').hide();
						Ext.getCmp('usermanagementwindow-accept').show();
						win.show();
					}
				}, {
					iconCls : 'edit',
					tooltip : '修改',
					disabled : !globalObject.haveActionMenu(me.cButtons, 'Edit'),
					handler : function(grid, rowIndex, colIndex) {
						var gridRecord = grid.getStore().getAt(rowIndex);
						var win = new App.UserManagementWindow({
							hidden : true
						});
						win.setHeight(300);
						var form = win.down('form').getForm();
						form.loadRecord(gridRecord);
						form.findField("cmd").setValue("edit");
						form.findField("userName").setReadOnly(true);
						form.findField('password').hide();
						Ext.getCmp("usermanagementwindow-departmentIdCombobox").setRawValue(gridRecord.get('departmentName'));
						Ext.getCmp("usermanagementwindow-roleIdCombobox").setRawValue(gridRecord.get('roleName'));
						Ext.getCmp("usermanagementwindow-userGradeCombobox").setRawValue(gridRecord.get('userGrade'));
						// form.findField('roleName').setRawValue(gridRecord.get('roleName'));
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
				id : 'usermanagementgrid',
				store : store,
				columns : columns
			});

			store.loadPage(1);

			this.callParent(arguments);
		},
		onQueryClick : function() {
			new App.UserQueryWindow().show();
		},
		onQueryAllClick : function() {
			var store=Ext.getCmp("usermanagementgrid").getStore();
			var searchParams = {
				real_name : "",
				departmentId:"-1"
			};
			Ext.apply(store.proxy.extraParams, searchParams);
			store.reload();
		},
		onAddClick : function() {
			new App.UserManagementWindow().show();
		},
		onViewClick : function() {
			var grid = Ext.getCmp("usermanagementgrid");
			var id = grid.getSelectionModel().getSelection()[0].get('id');
			var gridRecord = grid.getStore().findRecord('id', id);
			var win = new App.UserManagementWindow({
				hidden : true,
				height : 320
			});
			var form = win.down('form').getForm();
			form.loadRecord(gridRecord);
			form.findField('userName').setReadOnly(true);
			form.findField('password').hide();
			form.findField('realName').setReadOnly(true);
			form.findField('tel').setReadOnly(true);
			form.findField('email').setReadOnly(true);
			form.findField('lastLoginTime').show().setReadOnly(true);
			form.findField('roleName').setValue(grid.getSelectionModel().getSelection()[0].get('roleName')).setReadOnly(true);
			Ext.getCmp("usermanagementwindow-departmentIdCombobox").setRawValue(gridRecord.get('departmentName'));
			Ext.getCmp("usermanagementwindow-roleIdCombobox").setRawValue(gridRecord.get('roleName'));
			Ext.getCmp("usermanagementwindow-userGradeCombobox").setRawValue(gridRecord.get('userGrade'));
			Ext.getCmp('usermanagementwindow-save').hide();
			Ext.getCmp('usermanagementwindow-cancel').hide();
			Ext.getCmp('usermanagementwindow-accept').show();
			win.show();
		}
	});
});