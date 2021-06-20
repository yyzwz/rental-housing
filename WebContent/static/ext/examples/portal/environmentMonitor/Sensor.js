// 传感器位置标识
Ext.define('Forestry.app.environmentMonitor.Sensor', {
	extend : 'Ext.panel.Panel',
	initComponent : function() {
		var me = this;
		Ext.apply(this, {
			layout : 'border',
			items : [ Ext.create('Forestry.app.environmentMonitor.SensorLeaflet'), Ext.create('Forestry.app.environmentMonitor.SensorGrid', {
				cButtons : me.cButtons,
				cName : me.cName
			}) ]
		});
		this.callParent(arguments);
	}
});

// 传感器在Leaflet地图的位置标识
Ext.define('Forestry.app.environmentMonitor.SensorLeaflet', {
	extend : 'Ext.panel.Panel',
	region : 'north',
	height : '50%',
	split : true,
	html : '<iframe src= "' + appBaseUri + '/static/leaflet/sensor.html" width="100%" height="100%" marginwidth="0" framespacing="0" marginheight="0" frameborder="0" ></iframe>',
	initComponent : function() {
		this.callParent(arguments);
	}
});

Ext.define('App.SensorWindow', {
	extend : 'Ext.window.Window',
	constructor : function(config) {
		config = config || {};
		Ext.apply(config, {
			title : '传感器信息',
			width : 350,
			height : 200,
			bodyPadding : '10 5',
			modal : true,
			layout : 'fit',
			items : [ {
				xtype : 'form',
				fieldDefaults : {
					labelAlign : 'left',
					labelWidth : 70,
					anchor : '100%'
				},
				items : [ {
					name : "cmd",
					xtype : "hidden",
					value : 'new'
				}, {
					name : "id",
					xtype : "hiddenfield"
				}, {
					xtype : 'textfield',
					name : 'sensorId',
					fieldLabel : '传感器ID',
					allowBlank : false
				}, {
					xtype : 'textfield',
					name : 'xcoordinate',
					fieldLabel : 'x坐标',
					maxLength : 20,
					allowBlank : false
				}, {
					xtype : 'textfield',
					name : 'ycoordinate',
					fieldLabel : 'y坐标',
					maxLength : 20,
					allowBlank : false
				} ],
				buttons : [ '->', {
					id : 'sensorwindow-save',
					text : '保存',
					iconCls : 'icon-save',
					width : 80,
					handler : function(btn, eventObj) {
						var window = btn.up('window');
						var form = window.down('form').getForm();
						if (form.isValid()) {
							window.getEl().mask('数据保存中，请稍候...');
							var vals = form.getValues();

							if (isNaN(vals['sensorId']) || isNaN(vals['xcoordinate']) || isNaN(vals['ycoordinate'])) {
								globalObject.errTip('传感器ID、x坐标、y坐标只能输入数值！');
								window.getEl().unmask();
								return;
							}

							Ext.Ajax.request({
								url : appBaseUri + '/sys/sensor/saveSensor',
								params : {
									cmd : vals['cmd'],
									id : vals['id'],
									sensorId : vals['sensorId'],
									xcoordinate : vals['xcoordinate'],
									ycoordinate : vals['ycoordinate']
								},
								method : "POST",
								success : function(response) {
									if (response.responseText != '') {
										var res = Ext.JSON.decode(response.responseText);
										if (res.success) {
											globalObject.msgTip('操作成功！');
											Ext.getCmp('sensorgrid').getStore().reload();
										} else {
											globalObject.errTip('传感器ID已重复，请输入唯一值！');
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
					id : 'sensorwindow-cancel',
					text : '取消',
					iconCls : 'icon-cancel',
					width : 80,
					handler : function() {
						this.up('window').close();
					}
				}, {
					id : 'sensorwindow-accept',
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
		App.SensorWindow.superclass.constructor.call(this, config);
	}
});

Ext.define('Forestry.app.environmentMonitor.SensorGrid', {
	extend : 'Ext.grid.Panel',
	plain : true,
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
			}, {
				name : 'sensorId',
				type : 'int'
			}, {
				name : 'type',
				type : 'short'
			}, 'xcoordinate', 'ycoordinate' ]
		});

		var store = Ext.create('Ext.data.Store', {
			model : 'ModelList',
			// autoDestroy: true,
			autoLoad : true,
			remoteSort : true,
			pageSize : globalPageSize,
			proxy : {
				type : 'ajax',
				url : appBaseUri + '/sys/sensor/getSensor',
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
			text : "ID",
			dataIndex : 'id',
			width : '10%'
		}, {
			text : "传感器ID",
			dataIndex : 'sensorId',
			width : '20%'
		}, {
			text : "类型",
			dataIndex : 'type',
			width : '16%',
			renderer : function(v) {
				if (v == 1) {
					return '真实传感器'
				} else {
					return '虚拟传感器'
				}
			}
		}, {
			text : "x坐标",
			dataIndex : 'xcoordinate',
			width : '17%'
		}, {
			text : "y坐标",
			dataIndex : 'ycoordinate',
			width : '17%'
		}, {
			xtype : 'actioncolumn',
			width : '15%',
			items : [ {
				iconCls : 'icon-view',
				tooltip : '查看',
				disabled : !globalObject.haveActionMenu(me.cButtons, 'View'),
				handler : function(grid, rowIndex, colIndex) {
					var gridRecord = grid.getStore().getAt(rowIndex);
					var win = new App.SensorWindow({
						hidden : true
					});
					var form = win.down('form').getForm();
					form.loadRecord(gridRecord);
					form.findField('sensorId').setReadOnly(true);
					form.findField('xcoordinate').setReadOnly(true);
					form.findField('ycoordinate').setReadOnly(true);
					Ext.getCmp('sensorwindow-save').hide();
					Ext.getCmp('sensorwindow-cancel').hide();
					Ext.getCmp('sensorwindow-accept').show();
					win.show();
				}
			}, {
				iconCls : 'edit',
				tooltip : '修改',
				disabled : !globalObject.haveActionMenu(me.cButtons, 'Edit'),
				handler : function(grid, rowIndex, colIndex) {
					var gridRecord = grid.getStore().getAt(rowIndex);
					var win = new App.SensorWindow({
						hidden : true
					});
					var form = win.down('form').getForm();
					form.loadRecord(gridRecord);
					form.findField("cmd").setValue("edit");
					form.findField("sensorId").setReadOnly(true);
					win.show();
				}
			}, {
				iconCls : 'icon-delete',
				tooltip : '删除',
				disabled : !globalObject.haveActionMenu(me.cButtons, 'Delete'),
				handler : function(grid, rowIndex, colIndex) {
					var entity = grid.getStore().getAt(rowIndex);
					me.onDeleteClick();
				}
			} ]
		} ];

		Ext.apply(this, {
			id : 'sensorgrid',
			store : store,
			columns : columns,
			selModel : Ext.create('Ext.selection.CheckboxModel'),
			bbar : Ext.create('Ext.PagingToolbar', {
				store : store,
				displayInfo : true
			}),
			tbar : [ {
				xtype : 'button',
				itemId : 'btnAdd',
				iconCls : 'icon-add',
				text : '添加',
				hidden : !globalObject.haveActionMenu(me.cButtons, 'Add'),
				handler : me.onAddClick
			}, {
				xtype : 'button',
				itemId : 'btnDelete',
				iconCls : 'icon-delete',
				text : '删除',
				hidden : !globalObject.haveActionMenu(me.cButtons, 'Delete'),
				handler : me.onDeleteClick
			} ],
			listeners : {
				itemdblclick : function(dataview, record, item, index, e) {
					var grid = this;
					var id = grid.getSelectionModel().getSelection()[0].get('id');
					var gridRecord = grid.getStore().findRecord('id', id);
					var win = new App.SensorWindow({
						hidden : true
					});
					var form = win.down('form').getForm();
					form.loadRecord(gridRecord);
					form.findField('sensorId').setReadOnly(true);
					form.findField('xcoordinate').setReadOnly(true);
					form.findField('ycoordinate').setReadOnly(true);
					Ext.getCmp('sensorwindow-save').hide();
					Ext.getCmp('sensorwindow-cancel').hide();
					Ext.getCmp('sensorwindow-accept').show();
					win.show();
				}
			}
		});

		store.loadPage(1);

		this.callParent(arguments);
	},
	onAddClick : function() {
		new App.SensorWindow().show();
	},
	onDeleteClick : function() {
		globalObject.confirmTip('删除的记录不可恢复，继续吗？', function(btn) {
			if (btn == 'yes') {
				var s = Ext.getCmp("sensorgrid").getSelectionModel().getSelection();
				if (s == "") {
					globalObject.errTip('请先点击选择删除项！');
				} else {
					var ids = [];
					var idProperty = this.idProperty || 'id';
					for (var i = 0, r; r = s[i]; i++) {
						ids.push(r.get(idProperty));
					}
					Ext.Ajax.request({
						url : appBaseUri + '/sys/sensor/deleteSensor',
						params : {
							ids : ids.join(',')
						},
						success : function(response) {
							if (response.responseText != '') {
								var res = Ext.JSON.decode(response.responseText);
								if (res.success) {
									globalObject.msgTip('操作成功！');
									Ext.getCmp("sensorgrid").getStore().reload();
								} else {
									globalObject.errTip('操作失败！' + res.msg);
								}
							}
						}
					});
				}
			}
		});
	}
});