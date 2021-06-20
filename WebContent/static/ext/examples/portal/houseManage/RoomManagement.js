// 房屋管理
Ext.onReady(function() {
	Ext.tip.QuickTipManager.init();

	var houseNameStore = Ext.create('Ext.data.JsonStore', {
		proxy : {
			type : 'ajax',
			url : appBaseUri + '/sys/house/getHouse',
			extraParams:{
				checkOpion:encodeURI('同意')
            },
			reader : {
				type : 'json',
				root : 'list',
				idProperty : 'ItemValue'
			}
		},
		fields : [ 'ItemText', 'ItemValue' ]
	});
	var checkOpionAgree="同意";
	var checkOpionDisagree="不同意";
	var checkOpionStore = Ext.create("Ext.data.Store", {
		fields: ["ItemText", "ItemValue"],
		data: [
			{ ItemText: checkOpionAgree, ItemValue: 1 },
			{ ItemText: checkOpionDisagree, ItemValue: 2 }
		]
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
	Ext.define('App.RoomManagementWindow', {
		extend : 'Ext.window.Window',
		constructor : function(config) {
			config = config || {};
			Ext.apply(config, {
				title : '房间信息',
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
						xtype : 'combobox',
						id:'RoomManagement-houseNameCombobox',
						fieldLabel : '房屋',
						labelAlign: 'right',
						store : houseNameStore,
						valueField : 'ItemValue',
						displayField : 'ItemText',
						typeAhead : true,
						ManagementMode : 'remote',
						emptyText : '请选择...',
						allowBlank : false,
						editable : false,
						listeners : {
							select : function(combo, record, index) {
								Ext.getCmp("RoomManagement-houseId").setValue(combo.getValue());
								Ext.getCmp("RoomManagement-houseName").setValue(combo.getRawValue());
							}
						}
					} , {
						xtype : 'hiddenfield',
						name : 'id'
					},{
						xtype : 'textfield',
						fieldLabel : '房间名',
						labelAlign: 'right',
						name : 'roomName',
						allowBlank : false,
						maxLength : 20
					},{
						xtype : 'numberfield',
						fieldLabel : '房间面积',
						allowBlank : false,
						labelAlign: 'right',
						name : 'roomArea',
						maxLength : 10
					},{
						xtype : 'textarea',
						name : 'roomDesc',
						fieldLabel : '描述信息',
						labelAlign: 'right',
						maxLength : 100
					}, {
						xtype : 'hiddenfield',
						id : 'RoomManagement-houseId',
						name : 'houseId'
					},{
						xtype : 'hiddenfield',
						id : 'RoomManagement-houseName',
						name : 'houseName'
					}
					],
					buttons : [ '->', {
						id : 'Roomwindow-save',
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
									url : appBaseUri + '/sys/room/saveRoom',
									params : {
										cmd : vals['cmd'],
										id : vals['id'],
										roomName : vals['roomName'],
										roomArea : vals['roomArea'],
										roomDesc : vals['roomDesc'],
										houseName : vals['houseName'],
										houseId : vals['houseId']
									},
									method : "POST",
									success : function(response) {
										if (response.responseText != '') {
											var res = Ext.JSON.decode(response.responseText);
											if (res.success) {
												globalObject.msgTip('操作成功！');
												Ext.getCmp('RoomManagementgrid').getStore().reload();
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
						id : 'Roomwindow-cancel',
						text : '取消',
						iconCls : 'icon-cancel',
						width : 80,
						handler : function() {
							this.up('window').close();
						}
					}, {
						id : 'Roomwindow-accept',
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
			App.RoomManagementWindow.superclass.constructor.call(this, config);
		}
	});

	Ext.define('Forestry.app.houseManage.RoomManagement', {
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
				}, 'queryDepartmentName','houseOwnerName','roomName','roomArea','roomDesc' ,'roomTwoDimensionalCode', 'roomImage', 'houseId','houseTwoDimensionalCode' ,'houseImage', 'houseName', 'checkOpion' ]
			});

			var store = me.createStore({
				modelName : 'ModelList',
				// 获取列表
				proxyUrl : appBaseUri + '/sys/room/getRoomList',
				// 删除地址
				proxyDeleteUrl : appBaseUri + '/sys/room/deleteRoom',
				// 导出地址
				proxyExportUrl : appBaseUri + '/sys/room/getExportedRoomList',
				// 审核地址
				proxyCheckUrl : appBaseUri + '/sys/room/checkRoom',
				extraParams : me.extraParams
			});

			// 查询
			Ext.define('App.RoomQueryWindow', {
				extend : 'Ext.window.Window',
				constructor : function(config) {
					config = config || {};
					Ext.apply(config, {
						title : '查询',
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
								name : 'roomQuery-houseName',
								id : 'roomQuery-houseName',
								labelAlign: 'right',
								fieldLabel : '房屋名'
							}, {
								xtype : 'combobox',
								fieldLabel : '区域',
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
										Ext.getCmp("roomQueryform-departmentId").setValue(combo.getValue());
									}
								}
							},{
								xtype : 'hiddenfield',
								id : 'roomQueryform-departmentId',
								name : 'roomQueryform-departmentId'
							} ],
							buttons : [ '->', {
								text : '确定',
								iconCls : 'icon-accept',
								width : 80,
								handler : function() {
									var searchParams = {
											houseName : encodeURI(Ext.getCmp('roomQuery-houseName').getValue()),
											departmentId:encodeURI(Ext.getCmp('roomQueryform-departmentId').getValue())
											
										};
									Ext.apply(store.proxy.extraParams, searchParams);
									store.reload();
									this.up('window').close();
								}
							}, '->' ]
						} ]
					});
					App.RoomQueryWindow.superclass.constructor.call(this, config);
				}
			});
			var columns = [ 
			{
				text : "ID",
				xtype : "hidden",
				dataIndex : 'id',
				flex : 0.05
			},{
				text : "区域",
				dataIndex : 'queryDepartmentName',
				width : '17%',
				sortable : false
			},{
				text : "区域",
				dataIndex : 'houseOwnerName',
				width : '17%',
				sortable : false
			},{
				text : "房屋名",
				dataIndex : 'houseName',
				width : '17%',
				sortable : false
			},{
				text : "房间名称",
				dataIndex : 'roomName',
				flex : 0.15,
				sortable : false
			},{
				text : "房间面积",
				dataIndex : 'roomArea',
				flex : 0.15,
				sortable : false
			},{
				text : "房间图片",
				dataIndex : 'roomImage',
				xtype : 'actioncolumn',
				flex : 0.2,
				sortable : false,
				items : [ {
					iconCls : 'icon-pictures',
					tooltip : '房间图片',
					handler : function(grid, rowIndex, colIndex) {
						var entity = grid.getStore().getAt(rowIndex);
						if(entity.get('roomImage')=="")
							globalObject.msgTip("暂无照片");
						else{
							new Ext.window.Window({
							title : '房屋图片',
							width : 340,
							height : 240,
							bodyPadding : '10 5',
							modal : true,
							autoScroll : true,
							items : [ {
								html :  "<img src='/forestry/static/img/houseImages/room_"+entity.get('id')+".jpg' width=300 height=200 />"
							} ]
						}).show();
					  }
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
						var win = new App.RoomManagementWindow({
							hidden : true
						});
						var form = win.down('form').getForm();
						form.loadRecord(gridRecord);
						//form.findField('houseName').setReadOnly(true);
						//form.findField('houseDesc').setReadOnly(true);
						//form.findField('houseName').setReadOnly(true);
						Ext.getCmp('Roomwindow-save').hide();
						Ext.getCmp('Roomwindow-cancel').hide();
						Ext.getCmp('Roomwindow-accept').show();
						Ext.getCmp("RoomManagement-houseNameCombobox").setValue(gridRecord.get('houseId'));
						Ext.getCmp("RoomManagement-houseNameCombobox").setRawValue(gridRecord.get('houseName'));
						Ext.getCmp("RoomManagement-houseNameCombobox").setReadOnly(true);
						win.show();
					}
				}, {
					iconCls : 'edit',
					tooltip : '修改',
					disabled : !globalObject.haveActionMenu(me.cButtons, 'Edit'),
					handler : function(grid, rowIndex, colIndex) {
						var gridRecord = grid.getStore().getAt(rowIndex);
						//alert(gridRecord.get('houseName'));
						//alert(gridRecord.get('houseId'));
						var win = new App.RoomManagementWindow({
							hidden : true
						});
						win.setHeight(250);
						var form = win.down('form').getForm();
						form.loadRecord(gridRecord);
						form.findField("cmd").setValue("edit");
						Ext.getCmp("RoomManagement-houseNameCombobox").setValue(gridRecord.get('houseId'));
						Ext.getCmp("RoomManagement-houseNameCombobox").setRawValue(gridRecord.get('houseName'));
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
				id : 'RoomManagementgrid',
				store : store,
				columns : columns
			});

			store.loadPage(1);

			this.callParent(arguments);
		},
		onAddClick : function() {
			new App.RoomManagementWindow().show();
		},
		onQueryClick : function() {
			new App.RoomQueryWindow().show();
		},
		onQueryAllClick : function() {
			var store=Ext.getCmp("RoomManagementgrid").getStore();
			var searchParams = {
					houseName : null,
					departmentId:null
			};
			Ext.apply(store.proxy.extraParams, searchParams);
			store.reload();
		},
		onViewClick : function() {
			var grid = Ext.getCmp("RoomManagementgrid");
			var id = grid.getSelectionModel().getSelection()[0].get('id');
			var gridRecord = grid.getStore().findRecord('id', id);
			var win = new App.RoomManagementWindow({
				hidden : true,
				height : 430
			});
			var form = win.down('form').getForm();
			//form.findField('houseName').setReadOnly(true);
			//form.findField('houseDesc').setReadOnly(true);
			//form.findField('houseName').setReadOnly(true);
			form.loadRecord(gridRecord);
			Ext.getCmp('Roomwindow-save').hide();
			Ext.getCmp('Roomwindow-cancel').hide();
			Ext.getCmp('Roomwindow-accept').show();
			Ext.getCmp("RoomManagement-houseNameCombobox").setValue(gridRecord.get('houseId'));
			Ext.getCmp("RoomManagement-houseNameCombobox").setRawValue(gridRecord.get('houseName'));
			Ext.getCmp("RoomManagement-houseNameCombobox").setReadOnly(true);
			win.show();
		}
	});
});