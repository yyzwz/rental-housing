// 失踪警报设置
Ext.onReady(function() {
	Ext.tip.QuickTipManager.init();

	var getConfigTypeNameStore = Ext.create('Ext.data.JsonStore', {
		proxy : {
			type : 'ajax',
			url : appBaseUri + '/sys/config/getConfigTypeName',
			reader : {
				type : 'json',
				root : 'list',
				idProperty : 'ItemValue'
			}
		},
		fields : [ 'ItemText', 'ItemValue' ]
	});

	Ext.define('App.AlarmConfigurationWindow', {
		extend : 'Ext.window.Window',
		constructor : function(config) {
			config = config || {};
			Ext.apply(config, {
				title : '失踪警报设置信息',
				width : 350,
				height : 170,
				bodyPadding : '10 5',
				modal : true,
				layout : 'fit',
				items : [ {
					xtype : 'form',
					fieldDefaults : {
						labelAlign : 'left',
						labelWidth : 80,
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
						xtype : 'combobox',
						fieldLabel : '配置类型',
						name : 'configTypeName',
						store : getConfigTypeNameStore,
						valueField : 'ItemValue',
						displayField : 'ItemText',
						typeAhead : true,
						queryMode : 'remote',
						emptyText : '请选择...',
						allowBlank : false,
						editable : false,
						listeners : {
							select : function(combo, record, index) {
								Ext.getCmp("alarmConfigurationForm-configType").setValue(combo.getValue());
							}
						}
					}, {
						xtype : 'hiddenfield',
						id : 'alarmConfigurationForm-configType',
						name : 'configType'
					}, {
						xtype : 'textfield',
						name : 'configValue',
						fieldLabel : '配置值'
					} ],
					buttons : [ '->', {
						id : 'alarmconfigurationwindow-save',
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
									url : appBaseUri + '/sys/config/saveConfig',
									params : {
										cmd : vals['cmd'],
										id : vals['id'],
										configType : vals['configType'],
										configValue : vals['configValue']
									},
									method : "POST",
									success : function(response) {
										if (response.responseText != '') {
											var res = Ext.JSON.decode(response.responseText);
											if (res.success) {
												globalObject.msgTip('操作成功！');
												Ext.getCmp('alarmconfigurationgrid').getStore().reload();
											} else {
												globalObject.errTip('配置类型已存在，请重新选择！');
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
						id : 'alarmconfigurationwindow-cancel',
						text : '取消',
						iconCls : 'icon-cancel',
						width : 80,
						handler : function() {
							this.up('window').close();
						}
					}, {
						id : 'alarmconfigurationwindow-accept',
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
			App.AlarmConfigurationWindow.superclass.constructor.call(this, config);
		}
	});

	Ext.define('Forestry.app.systemManage.AlarmConfiguration', {
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
				}, 'configTypeName', {
					name : 'configType',
					type : 'short'
				}, {
					name : 'configValue',
					type : 'int'
				}, ]
			});

			var store = me.createStore({
				modelName : 'ModelList',
				proxyUrl : appBaseUri + '/sys/config/getConfig',
				proxyDeleteUrl : appBaseUri + '/sys/config/deleteConfig',
				extraParams : me.extraParams
			});

			var columns = [ {
				text : "ID",
				dataIndex : 'id',
				width : '10%'
			}, {
				text : "配置类型",
				dataIndex : 'configTypeName',
				width : '35%',
				sortable : false
			}, {
				text : "configType",
				dataIndex : 'configType',
				hidden : true,
				sortable : false
			}, {
				text : "配置值",
				dataIndex : 'configValue',
				width : '35%'
			}, {
				xtype : 'actioncolumn',
				width : '15%',
				items : [ {
					iconCls : 'icon-view',
					tooltip : '查看',
					disabled : !globalObject.haveActionMenu(me.cButtons, 'View'),
					handler : function(grid, rowIndex, colIndex) {
						var gridRecord = grid.getStore().getAt(rowIndex);
						var win = new App.AlarmConfigurationWindow({
							hidden : true
						});
						var form = win.down('form').getForm();
						form.loadRecord(gridRecord);
						form.findField('configValue').setReadOnly(true);
						form.findField('configTypeName').setReadOnly(true);
						Ext.getCmp('alarmconfigurationwindow-save').hide();
						Ext.getCmp('alarmconfigurationwindow-cancel').hide();
						Ext.getCmp('alarmconfigurationwindow-accept').show();
						win.show();
					}
				}, {
					iconCls : 'edit',
					tooltip : '修改',
					disabled : !globalObject.haveActionMenu(me.cButtons, 'Edit'),
					handler : function(grid, rowIndex, colIndex) {
						var gridRecord = grid.getStore().getAt(rowIndex);
						var win = new App.AlarmConfigurationWindow({
							hidden : true
						});
						var form = win.down('form').getForm();
						form.loadRecord(gridRecord);
						form.findField("cmd").setValue("edit");
						form.findField("configTypeName").setReadOnly(true);
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
				id : 'alarmconfigurationgrid',
				store : store,
				columns : columns
			});

			store.loadPage(1);

			this.callParent(arguments);
		},
		onAddClick : function() {
			new App.AlarmConfigurationWindow().show();
		},
		onViewClick : function() {
			var grid = Ext.getCmp("alarmconfigurationgrid");
			var id = grid.getSelectionModel().getSelection()[0].get('id');
			var gridRecord = grid.getStore().findRecord('id', id);
			var win = new App.AlarmConfigurationWindow({
				hidden : true
			});
			var form = win.down('form').getForm();
			form.loadRecord(gridRecord);
			form.findField('configValue').setReadOnly(true);
			form.findField('configTypeName').setReadOnly(true);
			Ext.getCmp('alarmconfigurationwindow-save').hide();
			Ext.getCmp('alarmconfigurationwindow-cancel').hide();
			Ext.getCmp('alarmconfigurationwindow-accept').show();
			win.show();
		}
	});
});