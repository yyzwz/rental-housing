// 房屋管理
Ext.onReady(function() {
	Ext.tip.QuickTipManager.init();

	
	
	Ext.define('App.HouseOwnerManagementWindow', {
		extend : 'Ext.window.Window',
		constructor : function(config) {
			config = config || {};
			Ext.apply(config, {
				title : '房东信息',
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
					items : [ 
					{
						name : "cmd",
						xtype : "hidden",
						value : 'new'
					}, {
						xtype : 'hiddenfield',
						name : 'id'
					}, {
						xtype : 'textfield',
						name : 'houseOwnerCode',
						fieldLabel : '登录账号',
						labelAlign: 'right',
						allowBlank : false,
						minLength :3, //允许输入的最少字符数
	                    minLengthText : "最小长度不能少于3个字符！",//提示文本
	                    maxLength : 10,
	                    maxLengthText : "最大长度不能超过10个字符！",
	                    validateOnChange: false,  //失去焦点时才进行验证
	                    validator: function(value){
	        				//var houseOwnerCode = Ext.get("houseOwnerCode");
	        				if(value != null){
	        					var result = isValidator(appBaseUri+"/com/houseOwner/isValidatorByFieldName","houseOwnerCode",value);
	        					//return result;
	        					if(result)
	        						return result;
	        					else
	        						return "该账户已经存在！";
	        				}
	        			}
					}, {
						xtype : 'textfield',
						name : 'houseOwnerPassWord',
						fieldLabel : '登录密码',
						labelAlign: 'right',
						emptyText : '请输入登录密码',
						allowBlank : false,
						minLength :4, //允许输入的最少字符数
	                    minLengthText : "最小长度不能少于4个字符！",//提示文本
	                    maxLength : 10,
	                    maxLengthText : "最大长度不能超过10个字符！"
					}, {
						xtype : 'textfield',
						name : 'houseOwnerName',
						fieldLabel : '姓名',
						labelAlign: 'right',
						allowBlank : false,
						maxLength : 10,
						regex:/^[\u4E00-\u9FA5]+$/,
						regexText:'请输入汉字'
					},{
						xtype : 'textfield',
						fieldLabel : '身份证号',
						allowBlank : false,
						labelAlign: 'right',
						name : 'houseOwnerIdentify',
						maxLength : 20,
						regex : /^(\d{18,18}|\d{15,15}|\d{17,17}x)$/,
	                    regexText : '输入正确的身份号码',
	                    validator: function(value){
	        				//var houseOwnerCode = Ext.get("houseOwnerCode");
	        				if(value != null){
	        					var result = isValidator(appBaseUri+"/com/houseOwner/isValidatorByFieldName","houseOwnerIdentify",value);
	        					//return result;
	        					if(result)
	        						return result;
	        					else
	        						return "该身份证号码已经存在！";
	        				}
	        			}
					},{
						xtype : 'textfield',
						fieldLabel : '电话',
						labelAlign: 'right',
						name : 'houseOwnerTel',
						maxLength : 20,
						regex:/^1[\d]{10}|[0-9]{4}-[0-9]{8}$/,
						allowBlank : false,
	                    regexText:'手机号码必须是1开头的,后面跟10位数字结尾',
	                    validateOnChange: false,  //失去焦点时才进行验证
	                    validator: function(value){
	        				//var houseOwnerCode = Ext.get("houseOwnerCode");
	        				if(value != null){
	        					var result = isValidator(appBaseUri+"/com/houseOwner/isValidatorByFieldName","houseOwnerTel",value);
	        					//return result;
	        					if(result)
	        						return result;
	        					else
	        						return "该电话已经存在！";
	        				}
	        			}
					},{
						xtype : 'textfield',
						fieldLabel : '住址',
						allowBlank : false,
						labelAlign: 'right',
						name : 'houseOwnerAddress',
						maxLength : 30,
	                    maxLengthText : "地址最大长度不能超过30个字符！"
					}, {
						xtype : 'textarea',
						name : 'houseOwnerDesc',
						fieldLabel : '描述信息',
						labelAlign: 'right',
						allowBlank : true,
						maxLength : 100,
	                    maxLengthText : "最大长度不能超过100个字符！"
					}
					],
					buttons : [ '->', {
						id : 'HouseOwnerwindow-save',
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
									url : appBaseUri + '/sys/HouseOwner/saveHouseOwner',
									params : {
										cmd : vals['cmd'],
										id : vals['id'],
										houseOwnerCode : vals['houseOwnerCode'],
										houseOwnerPassWord : vals['houseOwnerPassWord'],
										houseOwnerName : vals['houseOwnerName'],
										houseOwnerIdentify : vals['houseOwnerIdentify'],
										houseOwnerTel : vals['houseOwnerTel'],
										houseOwnerAddress : vals['houseOwnerAddress'],
										houseOwnerDesc : vals['houseOwnerDesc']
									},
									method : "POST",
									success : function(response) {
										if (response.responseText != '') {
											var res = Ext.JSON.decode(response.responseText);
											if (res.success) {
												//saveHouseTwoDimensionalCode
												 globalObject.msgTip('操作成功！');
												 Ext.getCmp('HouseOwnerManagementgrid').getStore().reload();
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
						id : 'HouseOwnerwindow-cancel',
						text : '取消',
						iconCls : 'icon-cancel',
						width : 80,
						handler : function() {
							this.up('window').close();
						}
					}, {
						id : 'HouseOwnerwindow-accept',
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
			App.HouseOwnerManagementWindow.superclass.constructor.call(this, config);
		}
	});

	Ext.define('Forestry.app.houseManage.HouseOwnerManagement', {
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
				}, 'houseOwnerCode','houseOwnerName','houseOwnerPassWord','houseOwnerIdentify' ,'houseOwnerTel','houseOwnerAddress', 'houseOwnerDesc', 'houseOwnerImage','houseOwnerTwoDimensionalCode','checkOpion'  ]
			});
			// 查询
			Ext.define('App.HouseOwnerQueryWindow', {
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
								name : 'houseOwnerQuery-houseOwnerName',
								id : 'houseOwnerQuery-houseOwnerName',
								labelAlign: 'right',
								fieldLabel : '房东名'
							},{
								xtype : 'textfield',
								name : 'houseOwnerQuery-houseOwnerIdentify',
								id : 'houseOwnerQuery-houseOwnerIdentify',
								labelAlign: 'right',
								fieldLabel : '省份证号'
							},{
								xtype : 'textfield',
								name : 'houseOwnerQuery-houseOwnerTel',
								id : 'houseOwnerQuery-houseOwnerTel',
								labelAlign: 'right',
								fieldLabel : '电话号码'
							} 
							],
							buttons : [ '->', {
								text : '确定',
								iconCls : 'icon-accept',
								width : 80,
								handler : function() {
									var searchParams = {
											houseOwnerName : encodeURI(Ext.getCmp('houseOwnerQuery-houseOwnerName').getValue()),
											houseOwnerIdentify : encodeURI(Ext.getCmp('houseOwnerQuery-houseOwnerIdentify').getValue()),
											houseOwnerTel : encodeURI(Ext.getCmp('houseOwnerQuery-houseOwnerTel').getValue())
										};
									Ext.apply(store.proxy.extraParams, searchParams);
									store.reload();
									this.up('window').close();
								}
							}, '->' ]
						} ]
					});
					App.HouseOwnerQueryWindow.superclass.constructor.call(this, config);
				}
			});
			var store = me.createStore({
				modelName : 'ModelList',
				// 获取列表
				proxyUrl : appBaseUri + '/sys/HouseOwner/getHouseOwnerList',
				// 删除地址
				proxyDeleteUrl : appBaseUri + '/sys/HouseOwner/deleteHouseOwner',
				// 导出地址
				proxyExportUrl : appBaseUri + '/sys/HouseOwner/getExportedHouseOwnerList',
				// 审核地址
				proxyCheckUrl : appBaseUri + '/sys/HouseOwner/checkHouseOwner',
				extraParams : me.extraParams
			});

			var columns = [ 
			{
				text : "ID",
				xtype : "hidden",
				dataIndex : 'id',
				flex : 0.05
			},{
				text : "登录账号",
				dataIndex : 'houseOwnerCode',
				flex : 0.15,
				sortable : false
			},{
				text : "姓名",
				dataIndex : 'houseOwnerName',
				width : '17%',
				sortable : false
			},{
				text : "登录密码",
				dataIndex : 'houseOwnerPassWord',
				width : '17%',
				sortable : false
			},{
				text : "身份证号",
				dataIndex : 'houseOwnerIdentify',
				width : '17%',
				sortable : false
			},{
				text : "审核意见",
				dataIndex : 'checkOpion',
				flex : 0.21
			},{
				text : "电话",
				dataIndex : 'houseOwnerTel',
				flex : 0.15
			},{
				text : "住址",
				dataIndex : 'houseOwnerAddress',
				flex : 0.15,
				sortable : false
			},{
				text : "描述信息",
				dataIndex : 'houseOwnerDesc',
				flex : 0.2,
				sortable : false
			}, {
				text : "房主图片",
				dataIndex : 'houseOwnerImage',
				xtype : 'actioncolumn',
				flex : 0.2,
				sortable : false,
				items : [ {
					iconCls : 'icon-pictures',
					tooltip : '房主图片',
					handler : function(grid, rowIndex, colIndex) {
						
						var entity = grid.getStore().getAt(rowIndex);
						new Ext.window.Window({
							title : '房主图片',
							width : 340,
							height : 240,
							bodyPadding : '10 5',
							modal : true,
							autoScroll : true,
							items : [ {
								html :  "<img src='/forestry/static/img/houseImages/houseOwner_"+entity.get('id')+".jpg' width=300 height=200 />"
							} ]
						}).show();
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
						var win = new App.HouseOwnerManagementWindow({
							hidden : true
						});
						var form = win.down('form').getForm();
						form.loadRecord(gridRecord);
						form.findField('houseName').setReadOnly(true);
						form.findField('houseDesc').setReadOnly(true);
						form.findField('houseTypeName').setReadOnly(true);
						Ext.getCmp('HouseOwnerwindow-save').hide();
						Ext.getCmp('HouseOwnerwindow-cancel').hide();
						Ext.getCmp('HouseOwnerwindow-accept').show();
						win.show();
					}
				}, {
					iconCls : 'edit',
					tooltip : '修改',
					disabled : !globalObject.haveActionMenu(me.cButtons, 'Edit'),
					handler : function(grid, rowIndex, colIndex) {
						var gridRecord = grid.getStore().getAt(rowIndex);
						var win = new App.HouseOwnerManagementWindow({
							hidden : true
						});
						win.setHeight(250);
						var form = win.down('form').getForm();
						form.loadRecord(gridRecord);
						form.findField("cmd").setValue("edit");
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
				id : 'HouseOwnerManagementgrid',
				store : store,
				columns : columns
			});

			store.loadPage(1);

			this.callParent(arguments);
		},
		onAddClick : function() {
			new App.HouseOwnerManagementWindow().show();
		},
		onQueryClick : function() {
			new App.HouseOwnerQueryWindow().show();
		},
		onQueryAllClick : function() {
			var store=Ext.getCmp("HouseOwnerManagementgrid").getStore();
			var searchParams = {
					houseOwnerName : null,
					houseOwnerIdentify:null,
					houseOwnerTel:null
			};
			Ext.apply(store.proxy.extraParams, searchParams);
			store.reload();
		},
		onViewClick : function() {
			var grid = Ext.getCmp("HouseOwnerManagementgrid");
			var id = grid.getSelectionModel().getSelection()[0].get('id');
			var gridRecord = grid.getStore().findRecord('id', id);
			var win = new App.HouseOwnerManagementWindow({
				hidden : true,
				height : 430
			});
			var form = win.down('form').getForm();
			form.findField('houseOwnerCode').setReadOnly(true);
			form.findField('houseOwnerPassWord').setReadOnly(true);
			form.findField('houseOwnerName').setReadOnly(true);
			form.findField('houseOwnerIdentify').setReadOnly(true);
			form.findField('houseOwnerTel').setReadOnly(true);
			form.findField('houseOwnerDesc').setReadOnly(true);
			form.loadRecord(gridRecord);
			Ext.getCmp('HouseOwnerwindow-save').hide();
			Ext.getCmp('HouseOwnerwindow-cancel').hide();
			Ext.getCmp('HouseOwnerwindow-accept').show();
			win.show();
		}
	});
});