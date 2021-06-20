// 树木识别信息查询
Ext.define('App.ForestryQueryWindow', {
	extend : 'Ext.window.Window',
	constructor : function(config) {
		config = config || {};
		Ext.apply(config, {
			title : '树木识别信息',
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
				items : [ {
					xtype : 'textfield',
					name : 'epcId',
					fieldLabel : 'epc编码'
				}, {
					xtype : 'textfield',
					name : 'name',
					fieldLabel : '名称'
				}, {
					xtype : 'datefield',
					name : 'plantTime',
					fieldLabel : '种植时间'
				}, {
					xtype : 'datefield',
					name : 'entryTime',
					fieldLabel : '入园时间'
				}, {
					xtype : 'textfield',
					fieldLabel : '所属种类名称',
					name : 'forestryTypeName'
				} ],
				buttons : [ '->', {
					text : '确定',
					iconCls : 'icon-accept',
					width : 80,
					handler : function() {
						this.up('window').close();
					}
				}, '->' ]
			} ]
		});
		App.ForestryQueryWindow.superclass.constructor.call(this, config);
	}
});

Ext.define('Forestry.app.forestryManage.ForestryQuery', {
	extend : 'Ext.grid.Panel',
	region : 'center',
	initComponent : function() {
		var me = this;

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
			}, 'forestryTypeName', 'forestryTypeId' ]
		});

		var forestryquerystore = Ext.create('Ext.data.Store', {
			model : 'ModelList',
			// autoDestroy: true,
			autoLoad : true,
			remoteSort : true,
			pageSize : globalPageSize,
			proxy : {
				type : 'ajax',
				url : appBaseUri + '/sys/forestry/getForestry',
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
			width : '5%'
		}, {
			text : "epc编码",
			dataIndex : 'epcId',
			width : '21%'
		}, {
			text : "名称",
			dataIndex : 'name',
			width : '18%'
		}, {
			text : "种植时间",
			dataIndex : 'plantTime',
			width : '17%'
		}, {
			text : "入园时间",
			dataIndex : 'entryTime',
			width : '17%'
		}, {
			text : "所属种类名称",
			dataIndex : 'forestryTypeName',
			width : '17%',
			sortable : false
		}, {
			text : "forestryTypeId",
			dataIndex : 'forestryTypeId',
			hidden : true
		} ];

		var ttoolbar = Ext.create('Ext.toolbar.Toolbar', {
			items : [ {
				xtype : 'textfield',
				id : 'forestryquery-epcId',
				name : 'epcId',
				fieldLabel : 'epc编码',
				labelWidth : 60,
				width : '25%'
			}, {
				xtype : 'textfield',
				id : 'forestryquery-name',
				name : 'name',
				fieldLabel : '名称',
				labelWidth : 30,
				width : '20%'
			}, {
				xtype : 'combobox',
				fieldLabel : '所属种类名称',
				id : 'forestryquery-forestryTypeName',
				name : 'forestryTypeName',
				store : forestryTypeNameStore,
				valueField : 'ItemValue',
				displayField : 'ItemText',
				typeAhead : true,
				queryMode : 'remote',
				emptyText : '请选择...',
				editable : false,
				labelWidth : 90,
				width : '35%',
				maxWidth : 320
			}, {
				xtype : 'button',
				text : '查询',
				iconCls : 'icon-search',
				disabled : !globalObject.haveActionMenu(me.cButtons, 'Query'),
				width : '10%',
				maxWidth : 60,
				handler : function(btn, eventObj) {
					var searchParams = {
						epcId : Ext.getCmp('forestryquery-epcId').getValue(),
						name : Ext.getCmp('forestryquery-name').getValue(),
						forestryTypeId : Ext.getCmp('forestryquery-forestryTypeName').getValue()
					};
					Ext.apply(forestryquerystore.proxy.extraParams, searchParams);
					forestryquerystore.reload();
				}
			}, {
				xtype : 'button',
				text : '重置',
				iconCls : 'icon-reset',
				width : '10%',
				maxWidth : 60,
				handler : function(btn, eventObj) {
					Ext.getCmp('forestryquery-epcId').setValue(null);
					Ext.getCmp('forestryquery-name').setValue(null);
					Ext.getCmp('forestryquery-forestryTypeName').setValue(null);
				}
			} ]
		});

		Ext.apply(this, {
			store : forestryquerystore,
			columns : columns,
			tbar : ttoolbar,
			bbar : Ext.create('Ext.PagingToolbar', {
				store : forestryquerystore,
				displayInfo : true
			}),
			listeners : {
				itemdblclick : function(dataview, record, item, index, e) {
					var grid = this;
					var id = grid.getSelectionModel().getSelection()[0].get('id');
					var gridRecord = grid.getStore().findRecord('id', id);
					var win = new App.ForestryQueryWindow({
						hidden : true
					});
					var form = win.down('form').getForm();
					form.loadRecord(gridRecord);
					form.findField('epcId').setReadOnly(true);
					form.findField('name').setReadOnly(true);
					form.findField('plantTime').setReadOnly(true);
					form.findField('entryTime').setReadOnly(true);
					form.findField('forestryTypeName').setReadOnly(true);
					win.show();
				}
			}
		});

		this.callParent(arguments);
	}
});