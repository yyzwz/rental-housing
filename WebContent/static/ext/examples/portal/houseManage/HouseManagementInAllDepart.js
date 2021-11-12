// 房屋管理
Ext.onReady(function() {
	Ext.tip.QuickTipManager.init();

	var houseTypeNameStore = Ext.create('Ext.data.JsonStore', {
		proxy : {
			type : 'ajax',
			url : appBaseUri + '/sys/houseType/getHouseType',
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
	var checkOpionAgree="同意";
	var checkOpionDisagree="不同意";
	var checkOpionStore = Ext.create("Ext.data.Store", {
		fields: ["ItemText", "ItemValue"],
		data: [
			{ ItemText: checkOpionAgree, ItemValue: 1 },
			{ ItemText: checkOpionDisagree, ItemValue: 2 }
		]
	});
	
	
	Ext.define('App.HouseManagementInAllDepartWindow', {
		extend : 'Ext.window.Window',
		constructor : function(config) {
			config = config || {};
			Ext.apply(config, {
				title : '房屋信息',
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
						id:'houseForm-houseTypeNameCombobox',
						fieldLabel : '房屋类型',
						store : houseTypeNameStore,
						valueField : 'ItemValue',
						displayField : 'ItemText',
						typeAhead : true,
						ManagementMode : 'remote',
						emptyText : '请选择...',
						allowBlank : false,
						editable : false,
						listeners : {
							select : function(combo, record, index) {
								Ext.getCmp("houseForm-houseTypeId").setValue(combo.getValue());
								Ext.getCmp("houseForm-houseTypeName").setValue(combo.getRawValue());
							}
						}
					} ,{
						xtype : 'combobox',
						id:'houseForm-departmentNameCombobox',
						fieldLabel : '区域',
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
								Ext.getCmp("houseForm-departmentId").setValue(combo.getValue());
								Ext.getCmp("houseForm-departmentName").setValue(combo.getRawValue());
							}
						}
					}, {
						xtype : 'hiddenfield',
						id : 'houseForm-departmentId',
						name : 'departmentId'
					}, {
						xtype : 'hiddenfield',
						id : 'houseForm-departmentName',
						name : 'departmentName'
					}, {
						xtype : 'hiddenfield',
						name : 'id'
					}, {
						xtype : 'textfield',
						name : 'houseName',
						fieldLabel : '房屋名称',
						emptyText : '请输入房屋名称',
						allowBlank : false,
						maxLength : 200
					},{
						xtype : 'textfield',
						fieldLabel : '房主姓名',
						id : 'houseOwnerName',
						name : 'houseOwnerName',
						maxLength : 200
					}, {
						xtype : 'textarea',
						name : 'houseAddress',
						fieldLabel : '房屋地址',
						allowBlank : false,
						maxLength : 200
					},{
						xtype : 'textarea',
						name : 'houseDesc',
						fieldLabel : '描述信息'
					}, {
						xtype : 'hiddenfield',
						id : 'houseForm-houseTypeId',
						name : 'houseTypeId'
					},{
						xtype : 'hiddenfield',
						id : 'houseForm-houseTypeName',
						name : 'houseTypeName'
					},{
						xtype : 'hiddenfield',
						id : 'houseMapLocation',
						name : 'houseMapLocation'
					},{
						xtype : 'hiddenfield',
						id : 'houseTwoDimensionalCode',
						name : 'houseTwoDimensionalCode'
					},{
						xtype : 'hiddenfield',
						id : 'houseImage',
						name : 'houseImage'
					},{
						xtype : 'hiddenfield',
						id : 'houseOwnerId',
						name : 'houseOwnerId'
					}
					],
					buttons : [ '->', {
						id : 'Housewindow-save',
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
									url : appBaseUri + '/sys/house/saveHouse',
									params : {
										cmd : vals['cmd'],
										id : vals['id'],
										houseName : vals['houseName'],
										houseDesc : vals['houseDesc'],
										houseTypeId : vals['houseTypeId'],
										houseTypeName : vals['houseTypeName'],
										houseAddress : vals['houseAddress'],
										houseMapLocation : vals['houseMapLocation'],
										houseTwoDimensionalCode : vals['houseTwoDimensionalCode'],
										houseImage : vals['houseImage'],
										houseOwnerId : vals['houseOwnerId'],
										houseOwnerName : vals['houseOwnerName'],
										departmentId : vals['departmentId'],
										departmentName : vals['departmentName']
									},
									method : "POST",
									success : function(response) {
										if (response.responseText != '') {
											var res = Ext.JSON.decode(response.responseText);
											if (res.success) {
												
												//判断前端是否选择有图片，否则就不调用上传图片Ajax 
												//调用Ajax插入图片
												//saveHouseTwoDimensionalCode
												globalObject.msgTip('操作成功！');
												Ext.getCmp('HouseManagementInAllDepartgrid').getStore().reload();
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
						id : 'Housewindow-cancel',
						text : '取消',
						iconCls : 'icon-cancel',
						width : 80,
						handler : function() {
							this.up('window').close();
						}
					}, {
						id : 'Housewindow-accept',
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
			App.HouseManagementInAllDepartWindow.superclass.constructor.call(this, config);
		}
	});

	Ext.define('Forestry.app.houseManage.HouseManagementInAllDepart', {
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
				},'departmentName', 'houseTypeId','houseTypeName','houseAddress' ,'houseName', 'houseDesc', 'houseMapLocation','houseTwoDimensionalCode' ,'houseImage', 'houseOwnerId', 'checkOpion','houseOwnerName' ]
			});

			var store = me.createStore({
				modelName : 'ModelList',
				// 获取列表
				proxyUrl : appBaseUri + '/sys/house/getHouseList',
				// 删除地址
				proxyDeleteUrl : appBaseUri + '/sys/house/deleteHouse',
				// 导出地址
				proxyExportUrl : appBaseUri + '/sys/house/getExportedHouseList',
				// 审核地址
				proxyCheckUrl : appBaseUri + '/sys/house/checkHouse',
				extraParams : me.extraParams
			});

			// 查询
			Ext.define('App.HouseQueryWindow', {
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
								name : 'houseQuery-houseName',
								id : 'houseQuery-houseName',
								fieldLabel : '房屋名'
							}, {
								xtype : 'combobox',
								fieldLabel : '区域',
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
										Ext.getCmp("houseQueryform-departmentId").setValue(combo.getValue());
									}
								}
							},{
								xtype : 'hiddenfield',
								id : 'houseQueryform-departmentId',
								name : 'houseQueryform-departmentId'
							},{
								xtype : 'combobox',
								fieldLabel : '审核意见',
								id:'houseQueryform_checkOpionSelect',
								valueField : 'ItemValue',
								displayField : 'ItemText',
								typeAhead : true,
								store: checkOpionStore,
								queryMode: "local",
								emptyText : '请选择...',
								editable : false,
								listeners : {
									select : function(combo, record, index) {
										Ext.getCmp("houseQueryform-checkOption").setValue(combo.getRawValue());
									}
								}
							},{
								xtype : 'hiddenfield',
								id : 'houseQueryform-checkOption'
							} ],
							buttons : [ '->', {
								text : '确定',
								iconCls : 'icon-accept',
								width : 80,
								handler : function() {
									var searchParams = {
											houseName : encodeURI(Ext.getCmp('houseQuery-houseName').getValue()),
											departmentId:encodeURI(Ext.getCmp('houseQueryform-departmentId').getValue()),
											checkOpion:encodeURI(Ext.getCmp('houseQueryform-checkOption').getValue())
										};
									Ext.apply(store.proxy.extraParams, searchParams);
									store.reload();
									this.up('window').close();
								}
							}, '->' ]
						} ]
					});
					App.HouseQueryWindow.superclass.constructor.call(this, config);
				}
			});
			var columns = [ 
			{
				text : "ID",
				xtype : "hidden",
				dataIndex : 'id',
				flex : 0.05,
				sortable : false
			},{
				text : "所在区域",
				dataIndex : 'departmentName',
				flex : 0.15,
				sortable : false
			},{
				text : "房屋类型",
				dataIndex : 'houseTypeName',
				flex : 0.15,
				sortable : false
			},{
				text : "房屋名称",
				dataIndex : 'houseName',
				flex : 0.15,
				sortable : false
			},{
				text : "房屋主人",
				dataIndex : 'houseOwnerName',
				width : '17%',
				sortable : false
			},{
				text : "房屋地址",
				dataIndex : 'houseAddress',
				flex : 0.15,
				sortable : false
			},{
				text : "注册日期",
				dataIndex : 'registDate',
				flex : 0.2,
				sortable : false
			},{
				text : "描述信息",
				dataIndex : 'houseDesc',
				flex : 0.2,
				sortable : false
			}, {
				text : "房屋图片",
				dataIndex : 'houseImage',
				xtype : 'actioncolumn',
				flex : 0.2,
				sortable : false,
				items : [ {
					iconCls : 'icon-pictures',
					tooltip : '房屋图片',
					handler : function(grid, rowIndex, colIndex) {
						
						var entity = grid.getStore().getAt(rowIndex);
						var entity = grid.getStore().getAt(rowIndex);
						if(entity.get('houseImage')=="")
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
									html :  "<img src='/forestry/static/img/houseImages/house_"+entity.get('id')+".jpg' width=300 height=200 />"
								} ]
							}).show();
						}
					}
				}]
			},{
				text : "二维码",
				dataIndex : 'houseTwoDimensionalCode',
				xtype : 'actioncolumn',
				flex : 0.2,
				sortable : false,
				items : [ {
					iconCls : 'icon-pictures',
					tooltip : '二维码',
					handler : function(grid, rowIndex, colIndex) {
						
						var entity = grid.getStore().getAt(rowIndex);
						var entity = grid.getStore().getAt(rowIndex);
						if(entity.get('houseImage')=="1")
							globalObject.msgTip("暂无照片");
						else{
							new Ext.window.Window({
								title : '二维码',
								width : 340,
								height : 440,
								bodyPadding : '10 5',
								modal : true,
								autoScroll : true,
								items : [ {
									html :  "<img src='/forestry/static/img/houseerweima/"+entity.get('houseTwoDimensionalCode')+"' width=300 height=300 /><center><div>" + entity.get('houseAddress')+"</div></center>"
								
								} ]
							}).show();
						}
					}
				}]
			},{
				text : "审核意见",
				dataIndex : 'checkOpion',
				flex : 0.2,
				sortable : false
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
						var win = new App.HouseManagementInAllDepartWindow({
							hidden : true
						});
						var form = win.down('form').getForm();
						form.loadRecord(gridRecord);
						form.findField('houseName').setReadOnly(true);
						form.findField('houseDesc').setReadOnly(true);
						form.findField('houseTypeName').setReadOnly(true);
						Ext.getCmp('Housewindow-save').hide();
						Ext.getCmp('Housewindow-cancel').hide();
						Ext.getCmp('Housewindow-accept').show();
						Ext.getCmp("houseForm-houseTypeNameCombobox").setValue(gridRecord.get('houseTypeId'));
						Ext.getCmp("houseForm-houseTypeNameCombobox").setRawValue(gridRecord.get('houseTypeName'));
						Ext.getCmp("houseForm-houseTypeNameCombobox").setReadOnly(true);
						Ext.getCmp("houseForm-departmentNameCombobox").setValue(gridRecord.get('departmentId'));
						Ext.getCmp("houseForm-departmentNameCombobox").setRawValue(gridRecord.get('departmentName'));
						Ext.getCmp("houseForm-departmentNameCombobox").setReadOnly(true);
						win.show();
					}
				}, {
					iconCls : 'edit',
					tooltip : '修改',
					disabled : !globalObject.haveActionMenu(me.cButtons, 'Edit'),
					handler : function(grid, rowIndex, colIndex) {
						var gridRecord = grid.getStore().getAt(rowIndex);
						var win = new App.HouseManagementInAllDepartWindow({
							hidden : true
						});
						win.setHeight(250);
						var form = win.down('form').getForm();
						form.loadRecord(gridRecord);
						form.findField("cmd").setValue("edit");
						Ext.getCmp("houseForm-houseTypeNameCombobox").setValue(gridRecord.get('houseTypeId'));
						Ext.getCmp("houseForm-houseTypeNameCombobox").setRawValue(gridRecord.get('houseTypeName'));
						Ext.getCmp("houseForm-departmentNameCombobox").setValue(gridRecord.get('departmentId'));
						Ext.getCmp("houseForm-departmentNameCombobox").setRawValue(gridRecord.get('departmentName'));
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
				id : 'HouseManagementInAllDepartgrid',
				store : store,
				columns : columns
			});

			store.loadPage(1);

			this.callParent(arguments);
		},
		onAddClick : function() {
			new App.HouseManagementInAllDepartWindow().show();
		},
		onQueryClick : function() {
			new App.HouseQueryWindow().show();
		},
		onQueryAllClick : function() {
			var store=Ext.getCmp("HouseManagementInAllDepartgrid").getStore();
			var searchParams = {
					houseName : null,
					departmentId:null,
					checkOpion:null
			};
			Ext.apply(store.proxy.extraParams, searchParams);
			store.reload();
		},
		onViewClick : function() {
			var grid = Ext.getCmp("HouseManagementInAllDepartgrid");
			var id = grid.getSelectionModel().getSelection()[0].get('id');
			var gridRecord = grid.getStore().findRecord('id', id);
			var win = new App.HouseManagementInAllDepartWindow({
				hidden : true,
				height : 430
			});
			var form = win.down('form').getForm();
			form.findField('houseName').setReadOnly(true);
			form.findField('houseDesc').setReadOnly(true);
			form.findField('houseTypeName').setReadOnly(true);
			form.loadRecord(gridRecord);
			Ext.getCmp('Housewindow-save').hide();
			Ext.getCmp('Housewindow-cancel').hide();
			Ext.getCmp('Housewindow-accept').show();
			Ext.getCmp("houseForm-houseTypeNameCombobox").setValue(gridRecord.get('houseTypeId'));
			Ext.getCmp("houseForm-houseTypeNameCombobox").setRawValue(gridRecord.get('houseTypeName'));
			Ext.getCmp("houseForm-houseTypeNameCombobox").setReadOnly(true);
			Ext.getCmp("houseForm-departmentNameCombobox").setValue(gridRecord.get('departmentId'));
			Ext.getCmp("houseForm-departmentNameCombobox").setRawValue(gridRecord.get('departmentName'));
			Ext.getCmp("houseForm-departmentNameCombobox").setReadOnly(true);
			win.show();
		}
	});
});