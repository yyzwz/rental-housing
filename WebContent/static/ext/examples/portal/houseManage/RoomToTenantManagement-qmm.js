// 房屋管理
Ext.onReady(function() {
	Ext.tip.QuickTipManager.init();
	
	function getChild(pid) {
        var data = new Array();
        Ext.each(regions, function (item) {
            if (item.pid == pid) {
                data.push(item)
            }
        });
        return data;

    };
	var provinceStore = Ext.create('Ext.data.Store', {
        data: getChild(1),
        autoLoad: true,
		fields : [ 'id', 'pid', 'text' ]
    });
    var cityStore = Ext.create('Ext.data.Store', {
        autoLoad: false,
        data: getChild(1),
        fields : [ 'id', 'pid', 'text' ]
    });
    var countyStore = Ext.create('Ext.data.Store', {
        autoLoad: false,
        data: getChild(1),
        fields : [ 'id', 'pid', 'text' ]
    });
	

    
	var roomNameStore = Ext.create('Ext.data.JsonStore', {
		autoLoad : false,//禁止自动装载数据
		proxy : {
			type : 'ajax',
			url : appBaseUri + '/sys/room/getRoom',
			extraParams:{
                houseId:'-1'
            },
			reader : {
				type : 'json',
				root : 'list',
				idProperty : 'ItemValue'
			}
		},
		fields : [ 'ItemText', 'ItemValue' ]
	});
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
	
	
	
	Ext.define('App.RoomToTenantManagementWindow', {
		extend : 'Ext.window.Window',
		constructor : function(config) {
			config = config || {};
			Ext.apply(config, {
				title : '租房信息',
				width : 350,
				height : 570,
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
					},{
						xtype : 'combobox',
						fieldLabel : '房屋',
						id:'RoomToTenantManagement-houseCombobox',
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
								Ext.getCmp("RoomToTenantManagement-houseId").setValue(combo.getValue());
								Ext.getCmp("RoomToTenantManagement-houseName").setValue(combo.getRawValue());
								var roomSelectCmp=Ext.getCmp("RoomToTenantManagement-roomCombobox");
								roomSelectCmp.clearValue();
								Ext.apply(roomSelectCmp.store.proxy.extraParams, {houseId:this.value});
								roomSelectCmp.store.reload();
								
							}
						}
					} ,{
						xtype : 'hiddenfield',
						id : 'RoomToTenantManagement-houseId',
						name : 'houseId'
					},{
						xtype : 'hiddenfield',
						id : 'RoomToTenantManagement-houseName',
						name : 'houseName'
					},{
						xtype : 'combobox',
						fieldLabel : '房间',
						id:'RoomToTenantManagement-roomCombobox',
						store : roomNameStore,
						selectOnFocus:false,
						forceSelection: false,
						valueField : 'ItemValue',
						displayField : 'ItemText',
						typeAhead : true,
						ManagementMode : 'remote',
						emptyText : '请选择...',
						allowBlank : false,
						editable : false,
						listeners : {
							select : function(combo, record, index) {
								//var houseId=Ext.getCmp("RoomToTenantManagement-houseCombobox").getValue();
				 				//var roomSelectCmp=Ext.getCmp("RoomToTenantManagement-roomCombobox");
							    //Ext.apply(roomSelectCmp.store.proxy.extraParams, {houseId:houseId});
								//roomSelectCmp.store.reload();
								Ext.getCmp("RoomToTenantManagement-roomId").setValue(combo.getValue());
								Ext.getCmp("RoomToTenantManagement-roomName").setValue(combo.getRawValue());
							}
						}
					} ,{
						xtype : 'hiddenfield',
						id:"RoomToTenantManagement-roomId",
						name : 'roomId',
						fieldLabel : '房间ID',
						allowBlank : false,
						maxLength : 200
					},{
						xtype : 'hiddenfield',
						id:"RoomToTenantManagement-roomName",
						name : 'roomName',
						fieldLabel : '房间名称',
						allowBlank : false,
						maxLength : 20
					},{
						xtype : 'textfield',
						name : 'tenantName',
						fieldLabel : '租客姓名',
						allowBlank : false,
						regex:/^[\u4E00-\u9FA5]+$/,
						regexText:'请输入汉字'
					},{
						xtype : 'hiddenfield',
						name : 'tenantId'
					}, {
						xtype : 'textfield',
						name : 'tenantIdentify',
						fieldLabel : '租客身份证号',
						allowBlank : false,
						regex : /^(\d{18,18}|\d{15,15}|\d{17,17}x)$/,
		                regexText : '输入正确的身份号码'
					}, {
						xtype : 'textfield',
						name : 'tenantTel',
						fieldLabel : '租客电话',
						allowBlank : false,
						regex:/^1[\d]{10}$/,
						regexText:'手机号码必须是1开头的,后面跟10位数字结尾'  
					},{
						xtype : 'textfield',
						name : 'tenantFromShen',
						fieldLabel : '来源省',
						allowBlank : false,
						maxLength : 20
					},{
						xtype : 'textfield',
						name : 'tenantFromShi',
						fieldLabel : '来源市',
						allowBlank : false,
						maxLength : 20
					},{
						xtype : 'textfield',
						name : 'tenantFromXian',
						fieldLabel : '来源县',
						allowBlank : false,
						maxLength : 20
					},{
						xtype : 'textfield',
						fieldLabel : '租客工作单位',
						name : 'tenantWorkOrganization',
						allowBlank : false,
						maxLength : 20
					},{
						xtype : 'textarea',
						name : 'tenantDesc',
						fieldLabel : '租客描述',
						allowBlank : true,
						maxLength : 200
					},{
						xtype : 'textarea',
						name : 'descInfo',
						fieldLabel : '租住说明',
						allowBlank : true,
					},{
						xtype : 'datefield',
						fieldLabel : '起始时间',
						name : 'startDate',
						allowBlank : false,
						mformat : 'Y-m-d'
					},{
						xtype : 'datefield',
						name : 'endDate',
						fieldLabel : '结束时间',
						allowBlank : false,
						mformat : 'Y-m-d'
					}
					],
					buttons : [ '->', {
						id : 'RoomToTenantwindow-save',
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
									url : appBaseUri + '/sys/roomToTenant/saveRoomToTenant',
									params : {
										cmd : vals['cmd'],
										id : vals['id'],
										startDate : vals['startDate'],
										endDate : vals['endDate'],
										roomId : vals['roomId'],
										roomName : vals['roomName'],
										houseId : vals['houseId'],
										houseName : vals['houseName'],
										roomName : vals['roomName'],
										houseDesc : vals['houseDesc'],
										tenantId : vals['tenantId'],
										tenantName : vals['tenantName'],
										descInfo : vals['descInfo'],
										tenantIdentify : vals['tenantIdentify'],
										tenantTel : vals['tenantTel'],
										tenantFromShen : vals['tenantFromShen'],
										tenantFromShi : vals['tenantFromShi'],
										tenantFromXian : vals['tenantFromXian'],
										tenantWorkOrganization : vals['tenantWorkOrganization'],
										tenantDesc : vals['tenantDesc']
									},
									method : "POST",
									success : function(response) {
										if (response.responseText != '') {
											var res = Ext.JSON.decode(response.responseText);
											if (res.success) {
												//saveRoomToTenantTwoDimensionalCode
												 globalObject.msgTip('操作成功！');
												 Ext.getCmp('RoomToTenantManagementgrid').getStore().reload();
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
						id : 'RoomToTenantwindow-cancel',
						text : '取消',
						iconCls : 'icon-cancel',
						width : 80,
						handler : function() {
							this.up('window').close();
						}
					}, {
						id : 'RoomToTenantwindow-accept',
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
			App.RoomToTenantManagementWindow.superclass.constructor.call(this, config);
		}
	});

	Ext.define('Forestry.app.houseManage.RoomToTenantManagement', {
		extend : 'Ext.ux.custom.GlobalGridPanel',
		region : 'center',
		initComponent : function() {
			var me = this;
			//alert(me.ttoolbar);.getStore()
			//alert(me.store);
			Ext.define('ModelList', {
				extend : 'Ext.data.Model',
				idProperty : 'id',
				fields : [ {
					name : 'id',
					type : 'long'
				}, 'departmentName','departmentId', 'houseId','houseName','tenantIdentify','tenantTel','tenantFromShen','tenantFromShi','tenantFromXian','tenantWorkOrganization','roomId','roomName','tenantId' ,'tenantName', 'descInfo', 'startDate','endDate','checkOpion'  ]
			});

			var store = me.createStore({
				modelName : 'ModelList',
				// 获取列表
				proxyUrl : appBaseUri + '/sys/roomToTenant/getRoomToTenantList',
				// 删除地址
				proxyDeleteUrl : appBaseUri + '/sys/roomToTenant/deleteRoomToTenant',
				// 导出地址
				proxyExportUrl : appBaseUri + '/sys/roomToTenant/getExportedRoomToTenantList',
				// 审核地址
				proxyCheckUrl : appBaseUri + '/sys/roomToTenant/checkRoomToTenant',
				//退租地址
				proxyQuitTenantUrl : appBaseUri + '/sys/roomToTenant/quitTenant',
				
				extraParams : me.extraParams
			});

			var columns = [ 
			{
				text : "ID",
				xtype : "hidden",
				dataIndex : 'id',
				flex : 0.05
			},{
				text : "ID",
				xtype : "hidden",
				dataIndex : 'tenantId',
				flex : 0.05
			},{
				text : "区域",
				dataIndex : 'departmentName',
				width : '5%',
				sortable : false
			}, {
				text : "房屋",
				dataIndex : 'houseName',
				width : '5%',
				sortable : false
			},{
				text : "房间名称",
				dataIndex : 'roomName',
				width : '5%',
				sortable : false
			},{
				text : "租客姓名",
				dataIndex : 'tenantName',
				width : '5%',
				sortable : false
			},{
				text : "身份证号",
				dataIndex : 'tenantIdentify',
				width : '10%',
				sortable : false
			}, {
				text : "租客电话",
				dataIndex : 'tenantTel',
				width : '6%',
				sortable : false
			},{
				text : "来源省",
				dataIndex : 'tenantFromShen',
				width : '5%',
				sortable : false
			},{
				text : "来源市",
				dataIndex : 'tenantFromShi',
				width : '5%',
				sortable : false
			},{
				text : "来源县",
				dataIndex : 'tenantFromXian',
				width : '5%',
				sortable : false
			},{
				text : "工作单位",
				dataIndex : 'tenantWorkOrganization',
				width : '16%',
				sortable : false
			},{
				text : "照片",
				dataIndex : 'tenantImage',
				xtype : 'actioncolumn',
				width : '4%',
				sortable : false,
				items : [ {
					iconCls : 'icon-pictures',
					tooltip : '房主图片',
					handler : function(grid, rowIndex, colIndex) {
						var entity = grid.getStore().getAt(rowIndex);
						if(entity.get('tenantImage')=="")
							globalObject.msgTip("暂无照片");
						else{
							new Ext.window.Window({
								title : '租客图片',
								width : 340,
								height : 240,
								bodyPadding : '10 5',
								modal : true,
								autoScroll : true,
								items : [ {
									html :  "<img src='/forestry/static/img/tenantImages/"+entity.get('houseImage')+"' width=300 height=200 />"
								} ]
							}).show();
						}
					}
				}]
			},{
				text : "描述信息",
				dataIndex : 'descInfo',
				flex : 0.2,
				sortable : false
			},{
				text : "起始时间",
				dataIndex : 'startDate',
				flex : 0.2,
				sortable : false
			},{
				text : "结束时间",
				dataIndex : 'endDate',
				flex : 0.2,
				sortable : false
			},{
				text : "审核意见",
				dataIndex : 'checkOpion',
				flex : 0.2,
				sortable : false
			},{
				text : "操作",
				xtype : 'actioncolumn',
				flex : 0.2,
				sortable : false,
				items : [ {
					iconCls : 'icon-view',
					tooltip : '查看',
					disabled : !globalObject.haveActionMenu(me.cButtons, 'View'),
					handler : function(grid, rowIndex, colIndex) {
						var gridRecord = grid.getStore().getAt(rowIndex);
						var win = new App.RoomToTenantManagementWindow({
							hidden : true
						});
						var form = win.down('form').getForm();
						form.loadRecord(gridRecord);
			
						Ext.getCmp("RoomToTenantManagement-houseCombobox").setValue(gridRecord.get('houseId'));
						Ext.getCmp("RoomToTenantManagement-houseCombobox").setRawValue(gridRecord.get('houseName'));
						Ext.getCmp("RoomToTenantManagement-houseCombobox").setReadOnly(true);
						Ext.getCmp("RoomToTenantManagement-roomCombobox").setValue(gridRecord.get('roomId'));
						Ext.getCmp("RoomToTenantManagement-roomCombobox").setRawValue(gridRecord.get('roomName'));
						Ext.getCmp("RoomToTenantManagement-roomCombobox").setReadOnly(true)
						//form.findField('houseName').setReadOnly(true);
						//form.findField('houseDesc').setReadOnly(true);
						//form.findField('houseTypeName').setReadOnly(true);
						Ext.getCmp('RoomToTenantwindow-save').hide();
						Ext.getCmp('RoomToTenantwindow-cancel').hide();
						Ext.getCmp('RoomToTenantwindow-accept').show();
						win.show();
					}
				}, {
					iconCls : 'edit',
					tooltip : '修改',
					disabled : !globalObject.haveActionMenu(me.cButtons, 'Edit'),
					handler : function(grid, rowIndex, colIndex) {
						var gridRecord = grid.getStore().getAt(rowIndex);
						var win = new App.RoomToTenantManagementWindow({
							hidden : true
						});
						//win.setHeight(250);
						var form = win.down('form').getForm();
						form.loadRecord(gridRecord);
						form.findField("cmd").setValue("edit");
						Ext.getCmp("RoomToTenantManagement-houseCombobox").setValue(gridRecord.get('houseId'));
						Ext.getCmp("RoomToTenantManagement-houseCombobox").setRawValue(gridRecord.get('houseName'));
						Ext.getCmp("RoomToTenantManagement-roomCombobox").setValue(gridRecord.get('roomId'));
						Ext.getCmp("RoomToTenantManagement-roomCombobox").setRawValue(gridRecord.get('roomName'));
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
				id : 'RoomToTenantManagementgrid',
				store : store,
				columns : columns
			});
			//alert(me.tbarMenus);
			store.loadPage(1);

			this.callParent(arguments);
			//alert(me.tbarMenus);
		},
		onAddClick : function() {
			new App.RoomToTenantManagementWindow().show();
		},
		onViewClick : function() {
			var grid = Ext.getCmp("RoomToTenantManagementgrid");
			//grid.getToolbar();
			var id = grid.getSelectionModel().getSelection()[0].get('id');
			var gridRecord = grid.getStore().findRecord('id', id);
			var win = new App.RoomToTenantManagementWindow({
				hidden : true,
				height : 430
			});
			var form = win.down('form').getForm();
			//form.findField('houseName').setReadOnly(true);
			//form.findField('houseDesc').setReadOnly(true);
			//form.findField('houseTypeName').setReadOnly(true);
			form.loadRecord(gridRecord);
			Ext.getCmp('RoomToTenantwindow-save').hide();
			Ext.getCmp('RoomToTenantwindow-cancel').hide();
			Ext.getCmp('RoomToTenantwindow-accept').show();
			Ext.getCmp("RoomToTenantManagement-houseCombobox").setValue(gridRecord.get('houseId'));
			Ext.getCmp("RoomToTenantManagement-houseCombobox").setRawValue(gridRecord.get('houseName'));
			Ext.getCmp("RoomToTenantManagement-houseCombobox").setReadOnly(true);
			Ext.getCmp("RoomToTenantManagement-roomCombobox").setValue(gridRecord.get('roomId'));
			Ext.getCmp("RoomToTenantManagement-roomCombobox").setRawValue(gridRecord.get('roomName'));
			Ext.getCmp("RoomToTenantManagement-roomCombobox").setReadOnly(true)
			win.show();
		}
	});
});