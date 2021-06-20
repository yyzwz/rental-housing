// 树木识别信息录入
Ext.onReady(function() {
	Ext.tip.QuickTipManager.init();

	var forestryTypeNameStore = Ext.create('Ext.data.JsonStore', {
		proxy : {
			type : 'ajax',
			url : appBaseUri + '/sys/forestry/getForestryTypeName',
			reader : {
				type : 'json',
				root : 'list',
				idProperty : 'ItemValue'
			}
		},
		fields : [ 'ItemText', 'ItemValue' ]
	});

	Ext.define('App.ForestryEntryWindow', {
		extend : 'Ext.window.Window',
		constructor : function(config) {
			config = config || {};
			Ext.apply(config, {
				title : '树木识别信息',
				width : 350,
				height : 250,
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
						name : 'epcId',
						fieldLabel : 'epc编码',
						maxLength : 100,
						allowBlank : false
					}, {
						xtype : 'textfield',
						name : 'name',
						fieldLabel : '名称',
						maxLength : 200,
						allowBlank : false
					}, {
						xtype : 'datefield',
						name : 'plantTime',
						fieldLabel : '种植时间',
						format : 'Y-m-d H:i:s',
						// maxValue : new Date(),
						// value : new Date(),
						allowBlank : true
					}, {
						xtype : 'datefield',
						name : 'entryTime',
						fieldLabel : '入园时间',
						format : 'Y-m-d H:i:s',
						allowBlank : true
					}, {
						xtype : 'combobox',
						fieldLabel : '所属种类名称',
						name : 'forestryTypeName',
						store : forestryTypeNameStore,
						valueField : 'ItemValue',
						displayField : 'ItemText',
						typeAhead : true,
						queryMode : 'remote',
						emptyText : '请选择...',
						allowBlank : false,
						editable : false,
						listeners : {
							select : function(combo, record, index) {
								Ext.getCmp("forestryEntryForm-forestryTypeId").setValue(combo.getValue());
							}
						}
					}, {
						xtype : 'hiddenfield',
						id : 'forestryEntryForm-forestryTypeId',
						name : 'forestryTypeId'
					} ],
					buttons : [ '->', {
						id : 'forestryentrywindow-save',
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
									url : appBaseUri + '/sys/forestry/saveForestry',
									params : {
										cmd : vals['cmd'],
										id : vals['id'],
										epcId : vals['epcId'],
										name : vals['name'],
										plantTime : vals['plantTime'],
										entryTime : vals['entryTime'],
										forestryTypeId : vals['forestryTypeId']
									},
									method : "POST",
									success : function(response) {
										if (response.responseText != '') {
											var res = Ext.JSON.decode(response.responseText);
											if (res.success) {
												globalObject.msgTip('操作成功！');
												Ext.getCmp('forestryentrygrid').getStore().reload();
											} else {
												globalObject.errTip('epc编码已存在，请输入唯一值！');
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
						id : 'forestryentrywindow-cancel',
						text : '取消',
						iconCls : 'icon-cancel',
						width : 80,
						handler : function() {
							this.up('window').close();
						}
					}, {
						id : 'forestryentrywindow-accept',
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
			App.ForestryEntryWindow.superclass.constructor.call(this, config);
		}
	});

	Ext.define('Forestry.app.forestryManage.ForestryEntry', {
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
				}, 'epcId', 'name', {
					name : 'plantTime',
					type : 'datetime',
					dateFormat : 'Y-m-d H:i:s'
				}, {
					name : 'entryTime',
					type : 'datetime',
					dateFormat : 'Y-m-d H:i:s'
				}, 'forestryTypeName', 'forestryTypeId', 'forestryTypeImagePath' ]
			});

			var store = me.createStore({
				modelName : 'ModelList',
				proxyUrl : appBaseUri + '/sys/forestry/getForestry',
				proxyDeleteUrl : appBaseUri + '/sys/forestry/deleteForestry',
				extraParams : me.extraParams
			});

			var columns = [ {
				text : "ID",
				dataIndex : 'id',
				width : '6%'
			}, {
				text : "epc编码",
				dataIndex : 'epcId',
				width : '18%'
			}, {
				text : "名称",
				dataIndex : 'name',
				width : '16%'
			}, {
				text : "种植时间",
				dataIndex : 'plantTime',
				width : '15%'
			}, {
				text : "入园时间",
				dataIndex : 'entryTime',
				width : '15%'
			}, {
				text : "所属种类名称",
				dataIndex : 'forestryTypeName',
				width : '16%',
				sortable : false
			}, {
				text : "forestryTypeId",
				dataIndex : 'forestryTypeId',
				hidden : true,
				sortable : false
			}, {
				text : "图片路径",
				dataIndex : 'forestryTypeImagePath',
				hidden : true,
				sortable : false
			}, {
				xtype : 'actioncolumn',
				width : '10%',
				items : [ {
					iconCls : 'icon-pictures',
					tooltip : '图片',
					handler : function(grid, rowIndex, colIndex) {
						var entity = grid.getStore().getAt(rowIndex);
						alert(entity.get('forestryTypeImagePath'));
						new Ext.window.Window({
							title : '图片',
							width : 340,
							height : 240,
							bodyPadding : '10 5',
							modal : true,
							autoScroll : true,
							items : [ {
								html : entity.get('forestryTypeImagePath')
							} ]
						}).show();
					}
				}, {
					iconCls : 'icon-view',
					tooltip : '查看',
					disabled : !globalObject.haveActionMenu(me.cButtons, 'View'),
					handler : function(grid, rowIndex, colIndex) {
						var gridRecord = grid.getStore().getAt(rowIndex);
						var win = new App.ForestryEntryWindow({
							hidden : true
						});
						var form = win.down('form').getForm();
						form.loadRecord(gridRecord);
						form.findField('epcId').setReadOnly(true);
						form.findField('name').setReadOnly(true);
						form.findField('plantTime').setReadOnly(true);
						form.findField('entryTime').setReadOnly(true);
						form.findField('forestryTypeName').setReadOnly(true);
						Ext.getCmp('forestryentrywindow-save').hide();
						Ext.getCmp('forestryentrywindow-cancel').hide();
						Ext.getCmp('forestryentrywindow-accept').show();
						win.show();
					}
				}, {
					iconCls : 'edit',
					tooltip : '修改',
					disabled : !globalObject.haveActionMenu(me.cButtons, 'Edit'),
					handler : function(grid, rowIndex, colIndex) {
						var gridRecord = grid.getStore().getAt(rowIndex);
						var win = new App.ForestryEntryWindow({
							hidden : true
						});
						var form = win.down('form').getForm();
						form.loadRecord(gridRecord);
						form.findField("cmd").setValue("edit");
						form.findField("epcId").setReadOnly(true);
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
				id : 'forestryentrygrid',
				store : store,
				columns : columns
			});

			store.loadPage(1);

			this.callParent(arguments);
		},
		onAddClick : function() {
			new App.ForestryEntryWindow().show();
		},
		onViewClick : function() {
			var grid = Ext.getCmp("forestryentrygrid");
			var id = grid.getSelectionModel().getSelection()[0].get('id');
			var gridRecord = grid.getStore().findRecord('id', id);
			var win = new App.ForestryEntryWindow({
				hidden : true
			});
			var form = win.down('form').getForm();
			form.loadRecord(gridRecord);
			form.findField('epcId').setReadOnly(true);
			form.findField('name').setReadOnly(true);
			form.findField('plantTime').setReadOnly(true);
			form.findField('entryTime').setReadOnly(true);
			form.findField('forestryTypeName').setReadOnly(true);
			Ext.getCmp('forestryentrywindow-save').hide();
			Ext.getCmp('forestryentrywindow-cancel').hide();
			Ext.getCmp('forestryentrywindow-accept').show();
			win.show();
		}
	});
});