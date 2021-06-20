// 树木种类信息录入
Ext.define('Forestry.app.forestryManage.ForestryTypeEntry', {
	extend : 'Ext.panel.Panel',
	style : 'padding:5px;',
	bodyStyle : 'overflow-x:auto; overflow-y:scroll',
	initComponent : function() {
		var me = this;
		me.dataId = me.dataId == undefined ? 0 : me.dataId;

		Ext.apply(this, {
			loadUrl : appBaseUri + '/sys/forestrytype/getForestrytypeById',
			// title : '树木种类信息录入',
			xtype : 'form',
			layout : 'anchor',
			bodyPadding : '10 0',
			ui : 'light',
			items : [ {
				id : 'forestrytype-cmd',
				name : "cmd",
				xtype : "hidden",
				value : 'new'
			}, {
				id : 'forestrytype-id',
				name : 'id',
				xtype : 'hiddenfield'
			}, {
				id : 'forestrytype-name',
				name : 'name',
				xtype : 'textfield',
				fieldLabel : '名称',
				allowBlank : false,
				anchor : '96%',
				maxLength : 200,
				maxLengthText : '长度不能超过200'
			}, {
				xtype : 'htmleditor',
				border : true,
				id : 'forestrytype-description',
				name : 'description',
				fieldLabel : '描述',
				anchor : '96%',
				// width : '96%',
				fontFamilies : [ 'Arial', 'Courier New', 'Tahoma', 'Times New Roman', 'Verdana', '宋体', '黑体', '隶书', '楷体_GB2312' ],
				height : 500,
				plugins : [ Ext.create('Ext.ux.custom.ImageHtmlEditor') ]
			} ],
			buttons : [ '->', {
				text : '保存',
				iconCls : 'icon-save',
				handler : function(btn, eventObj) {
					var name = Ext.getCmp('forestrytype-name').getValue();
					var cmd = Ext.getCmp('forestrytype-cmd').getValue();
					var id = Ext.getCmp('forestrytype-id').getValue();
					var description = Ext.getCmp('forestrytype-description').getValue();
					if ("" == name) {
						globalObject.errTip('名称不能为空！');
						return;
					}
					Ext.Ajax.request({
						url : appBaseUri + '/sys/forestrytype/saveForestrytype',
						params : {
							cmd : cmd,
							id : id,
							name : name,
							description : description
						},
						method : "POST",
						success : function(response) {
							var result = eval("(" + response.responseText + ")");
							Ext.getCmp('forestrytype-id').setValue(result.id);
							Ext.getCmp('forestrytype-cmd').setValue('edit');
							globalObject.msgTip('操作成功！');
						},
						failure : function(response) {
							globalObject.errTip('操作失败！');
						}
					});
				}
			}, {
				text : '保存并新增',
				iconCls : 'icon-add',
				hidden : me.dataId != undefined && me.dataId != '',
				handler : function(btn, eventObj) {
					var name = Ext.getCmp('forestrytype-name').getValue();
					var cmd = Ext.getCmp('forestrytype-cmd').getValue();
					var id = Ext.getCmp('forestrytype-id').getValue();
					var description = Ext.getCmp('forestrytype-description').getValue();
					if ("" == name) {
						globalObject.errTip('名称不能为空！');
						return;
					}
					Ext.Ajax.request({
						url : appBaseUri + '/sys/forestrytype/saveForestrytype',
						params : {
							cmd : cmd,
							id : id,
							name : name,
							description : description
						},
						method : "POST",
						success : function(response) {
							Ext.getCmp('forestrytype-id').setValue(null);
							Ext.getCmp('forestrytype-cmd').setValue('new');
							Ext.getCmp('forestrytype-name').setValue(null);
							Ext.getCmp('forestrytype-description').setValue(null);
							globalObject.msgTip('操作成功！');
						},
						failure : function(response) {
							globalObject.errTip('操作失败！');
						}
					});
				}
			}, {
				text : '取消',
				iconCls : 'icon-cancel',
				handler : function() {
					globalObject.closeTab(this.listTabId);
				}
			}, '->' ]
		});

		this.callParent(arguments);
		this.on('boxready', function() {
			if (me.loadUrl && me.dataId != 0) {
				// me.getForm().waitMsgTarget = me.getEl();
				Ext.Ajax.request({
					url : me.loadUrl,
					params : {
						'id' : me.dataId
					},
					waitMsg : '数据载入中，请稍候...',
					method : "POST",
					success : function(response) {
						var result = eval("(" + response.responseText + ")");
						Ext.getCmp('forestrytype-id').setValue(result.id);
						Ext.getCmp('forestrytype-cmd').setValue('edit');
						Ext.getCmp('forestrytype-name').setValue(result.name);
						Ext.getCmp('forestrytype-description').setValue(result.description);
					},
					failure : function(response) {
						globalObject.errTip('加载数据失败！');
					}
				});
			}
		});
	},
	onDestroy : function() {
		if (Ext.getCmp('ext-comp-1033')) {
			Ext.getCmp('ext-comp-1033').getStore().reload();
		}
	}
});