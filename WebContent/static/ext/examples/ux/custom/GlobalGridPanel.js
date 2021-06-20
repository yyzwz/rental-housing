

var checkOpionAgree="同意";
var checkOpionDisagree="不同意";
var checkOpionStore = Ext.create("Ext.data.Store", {
	fields: ["ItemText", "ItemValue"],
	data: [
		{ ItemText: checkOpionAgree, ItemValue: 1 },
		{ ItemText: checkOpionDisagree, ItemValue: 2 }
	]
});	

Ext.define('Ext.ux.custom.GlobalGridPanel', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.globalgrid',
	// requires : [ 'Ext.ux.grid.FiltersFeature' ],
	xtype : 'cell-editing',
   
	initComponent : function() {
		var me = this;
		
		var singleId;

		var uniqueID = me.cName + (me.cId ? me.cId : '') + (me.myId ? me.myId : '');

		this.cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
			clicksToEdit : 2
		});

		var tbarMenus = new Array();
		if (globalObject.haveActionMenu(me.cButtons, 'Add')) {
			tbarMenus.push({
				xtype : 'button',
				itemId : 'btnAdd',
				iconCls : 'icon-add',
				text : '添加',
				scope : this,
				// disabled : !globalObject.haveAction(me.cName + 'Add'),
				handler : me.onAddClick
			});
		}
		if (globalObject.haveActionMenu(me.cButtons, 'Import')) {
			tbarMenus.push({
				xtype : 'button',
				itemId : 'btnImport',
				iconCls : 'icon-excel',
				text : '导入',
				scope : this,
				// disabled : !globalObject.haveAction(me.cName + 'Add'),
				handler : me.onImportClick
			});
		}
		if (globalObject.haveActionMenu(me.cButtons, 'Export')) {
			tbarMenus.push({
				xtype : 'button',
				itemId : 'btnExport',
				iconCls : 'icon-excel',
				text : '导出',
				disabled : true,
				scope : this,
				// disabled : !globalObject.haveAction(me.cName + 'Export'),
				handler : function() {
					me.onExportClick();
				}
			});
		}

		if (globalObject.haveActionMenu(me.cButtons, 'Delete')) {
			tbarMenus.push({
				xtype : 'button',
				itemId : 'btnDelete',
				iconCls : 'icon-delete',
				text : '删除',
				scope : this,
				disabled : true,
				handler : me.onDeleteClick
			});
		}
		if (globalObject.haveActionMenu(me.cButtons, 'Examine')) {
			tbarMenus.push({
				xtype : 'button',
				itemId : 'btnEdit',
				iconCls : 'edit',
				text : '审核',
				scope : this,
				disabled : true,
				handler : me.onCheckClick
			});
		}
		if (globalObject.haveActionMenu(me.cButtons, 'QuitTenant')) {
			tbarMenus.push({
				xtype : 'button',
				itemId : 'btnEdit',
				iconCls : 'edit',
				text : '退租',
				scope : this,
				disabled : true,
				handler : me.onQuitTenantClick
			});
		}
		if (globalObject.haveActionMenu(me.cButtons, 'Query')) {
			/*
			tbarMenus.push({
				xtype : 'textfield',
				name:'realName',
				itemId : 'realName',
				emptyText:'输入姓名',
				iconCls : 'edit',
				scope : this
			});
			*/
			tbarMenus.push({
				xtype : 'button',
				itemId : 'btnQuery',
				iconCls : 'edit',
				text : '条件查询',
				scope : this,
				handler : me.onQueryClick
			});
			;
			tbarMenus.push({
				xtype : 'button',
				itemId : 'btnQueryAll',
				iconCls : 'edit',
				text : '全部查询',
				scope : this,
				handler : me.onQueryAllClick
			});
			
		}
		for(var i=0;i<arguments.length;i++){
			tbarMenus.push(arguments[i]);
		}
		
		
		if (tbarMenus.length == 0)
			me.hideTBar = true;
		this.ttoolbar = Ext.create('Ext.toolbar.Toolbar', {
			hidden : me.hideTBar || false,
			items : tbarMenus
		});

		Ext.apply(this, {
			stateful : me.cName ? true : false,
			stateId : me.cName ? (uniqueID + 'gird') : null,
			enableColumnMove : me.cName ? true : false,
			plugins : this.plugins,
			selModel : Ext.create('Ext.selection.CheckboxModel'),
			border : false,
			tbar : this.ttoolbar,
			bbar : me.hideBBar ? null : Ext.create('Ext.PagingToolbar', {
				store : me.getStore(),
				displayInfo : true
			}),
			listeners : {
				itemdblclick : function(dataview, record, item, index, e) {
					me.onViewClick();
				}
			}
		});
		this.getSelectionModel().on('selectionchange', function(sm, records) {
			if (me.down('#btnDelete'))
				me.down('#btnDelete').setDisabled(sm.getCount() == 0);
			if (me.down('#btnEdit'))
				me.down('#btnEdit').setDisabled(sm.getCount() == 0);
			if (me.down('#btnExport'))
				me.down('#btnExport').setDisabled(sm.getCount() == 0);
		});

		this.callParent(arguments);
	},
	createStore : function(config) {
		Ext.applyIf(this, config);

		return Ext.create('Ext.data.Store', {
			model : config.modelName,
			// autoDestroy: true,
			// autoLoad: true,
			remoteSort : true,
			pageSize : globalPageSize,
			proxy : {
				type : 'ajax',
				url : config.proxyUrl,
				extraParams : config.extraParams || null,
				reader : {
					type : 'json',
					root : 'data',
					totalProperty : 'totalRecord',
					successProperty : "success"
				}
			},
			sorters : [ {
				property : config.sortProperty || 'id',
				direction : config.sortDirection || 'DESC'
			} ]
		});
	},
	getTabId : function() {
		return this.up('panel').getId();
	},
	onAddClick : function() {
	},
	onEditClick : function() {
	},
	onImportClick : function() {
	},
	onViewClick : function() {
	},
	onDeleteClick : function() {
		var me = this;
		globalObject.confirmTip('删除的记录不可恢复，继续吗？', function(btn) {
			if (btn == 'yes') {
				var s = me.getSelectionModel().getSelection();
				var ids = [];
				var idProperty = me.idProperty || 'id';
				for (var i = 0, r; r = s[i]; i++) {
					ids.push(r.get(idProperty));
				}
				Ext.Ajax.request({
					url : me.proxyDeleteUrl,
					params : {
						ids : ids.join(',') || singleId
					},
					success : function(response) {
						if (response.responseText != '') {
							var res = Ext.JSON.decode(response.responseText);
							if (res.success) {
								globalObject.msgTip('操作成功！');
								// Ext.example.msg('系统信息', '{0}', "操作成功！");
								me.getStore().reload();
							} else {
								globalObject.errTip('操作失败！' + res.msg);
							}
						}
					}
				});
			}
		});
	},
	onQuitTenantClick : function() {
		var me = this;
		globalObject.confirmTip('退租不可恢复，继续吗？', function(btn) {
			if (btn == 'yes') {
				var s = me.getSelectionModel().getSelection();
				var ids = [];
				var idProperty = me.idProperty || 'id';
				for (var i = 0, r; r = s[i]; i++) {
					ids.push(r.get(idProperty));
				}
				Ext.Ajax.request({
					url : me.proxyQuitTenantUrl,
					params : {
						ids : ids.join(',') || singleId
					},
					success : function(response) {
						if (response.responseText != '') {
							var res = Ext.JSON.decode(response.responseText);
							if (res.success) {
								globalObject.msgTip('操作成功！');
								// Ext.example.msg('系统信息', '{0}', "操作成功！");
								me.getStore().reload();
							} else {
								globalObject.errTip('操作失败！' + res.msg);
							}
						}
					}
				});
			}
		});
	},
	onCheckClick : function() {
		var me = this;
		var s = me.getSelectionModel().getSelection();
		var ids = [];
		var idProperty = me.idProperty || 'id';
		for (var i = 0, r; r = s[i]; i++) {
			ids.push(r.get(idProperty));
		}
		Ext.define('App.CheckWindow', {
			extend : 'Ext.window.Window',
			constructor : function(config) {
				config = config || {};
				Ext.apply(config, {
					title : '审核',
					width : 350,
					height : 140,
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
						items : [ 
						{
							xtype : 'combobox',
							fieldLabel : '审核意见',
							valueField : 'ItemValue',
							displayField : 'ItemText',
							typeAhead : true,
							store: checkOpionStore,
							queryMode: "local",
							emptyText : '请选择...',
							allowBlank : false,
							editable : false,
							listeners : {
								select : function(combo, record, index) {
									Ext.getCmp("checkForm-checkOption").setValue(combo.getRawValue());
								}
							}
						},{
							xtype : 'hiddenfield',
							id : 'checkForm-checkOption',
							name : 'checkForm-checkOption'
						}
						],
						buttons : [ '->', {
							id : 'Housewindow-save',
							text : '确定',
							iconCls : 'icon-save',
							width : 80,
							handler : function(btn, eventObj) {
								var window = btn.up('window');
								var form = window.down('form').getForm();
								if (form.isValid()) {
									window.getEl().mask('数据保存中，请稍候...');
									var vals = form.getValues();
									Ext.Ajax.request({
										url :  me.proxyCheckUrl,//appBaseUri + '/sys/house/checkHouse',
										params : {
											ids : ids.join(',') || singleId,
											checkOption:vals['checkForm-checkOption']
										},
										success : function(response) {
											if (response.responseText != '') {
												var res = Ext.JSON.decode(response.responseText);
												if (res.success) {
													globalObject.msgTip('操作成功！');
													// Ext.example.msg('系统信息', '{0}', "操作成功！");
													me.getStore().reload();
												} else {
													globalObject.errTip('操作失败！' + res.msg);
												}
											}
										}
									});
									window.getEl().unmask();
									window.close();
								}
							}
						}, {
							id : 'Housewindow-cancel',
							text : '取消',
							iconCls : 'icon-cancel',
							width : 80,
							handler : function() {
								this.up('window').close();
							}
						},  '->' ]
					} ]
				});
				App.CheckWindow.superclass.constructor.call(this, config);
			}
		});
		new App.CheckWindow().show(); 
		
	
		
	},
	onExportClick : function() {
		var me = this;
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
		location.href = me.proxyExportUrl + "?ids=" + ids;
		/**
		Ext.Ajax.request({
			url : me.proxyExportUrl,
			method : 'POST',
			params : {
				ids : ids.join(',')
			},
			success : function(response) {
			}
		});
		**/
	}
});
