// 权限管理
Ext.define('Forestry.app.systemManage.AuthorizationManagement', {
	extend : 'Ext.panel.Panel',
	initComponent : function() {
		var me = this;
		Ext.apply(this, {
			layout : 'border',
			items : [ Ext.create('Forestry.app.systemManage.AuthorizationManagement.SysUserGrid', {
				cButtons : me.cButtons,
				cName : me.cName
			}), Ext.create('Forestry.app.systemManage.AuthorizationManagement.MenuTree') ]
		});
		this.callParent(arguments);
	}
});

// 角色列表
Ext.define('Forestry.app.systemManage.AuthorizationManagement.SysUserGrid', {
	extend : 'Ext.grid.Panel',
	id : 'authorizationmanagement-sysusergrid',
	region : 'west',
	width : '18%',
	initComponent : function() {
		var me = this;
		Ext.define('SysUserRoleList', {
			extend : 'Ext.data.Model',
			idProperty : 'role',
			fields : [ {
				name : 'role',
				type : 'short'
			}, 'roleName' ]
		});
		var sysusergridstore = Ext.create('Ext.data.Store', {
			model : 'SysUserRoleList',
			// autoDestroy: true,
			autoLoad : true,
			remoteSort : true,
			pageSize : globalPageSize,
			proxy : {
				type : 'ajax',
				url : appBaseUri + '/sys/sysuser/getRoleNameList',
				extraParams : me.extraParams || null,
				reader : {
					type : 'json',
					root : 'data',
					totalProperty : 'totalRecord',
					successProperty : "success"
				}
			}
		});
		var sysusergridcolumns = [ {
			text : "roleId",
			dataIndex : 'role',
			hidden : true,
			sortable : false,
			editor : {
				allowBlank : false
			}
		}, {
			text : "角色",
			dataIndex : 'roleName',
			sortable : false,
			width : '85%',
			editor : {
				allowBlank : false
			}
		} ];
		Ext.apply(this, {
			store : sysusergridstore,
			selModel : Ext.create('Ext.selection.CheckboxModel'),
			columns : sysusergridcolumns,
			listeners : {
				'itemclick' : function(item, record) {
					me.currentRole = record.get('role');
					Ext.getCmp('authorizationmanagement-rolemenu').getStore().load({
						params : {
							'role' : me.currentRole
						}
					});
				}
			}
		});
		this.callParent(arguments);
	}
});

// 树形菜单
Ext.define('Forestry.app.systemManage.AuthorizationManagement.MenuTree', {
	extend : 'Ext.tree.Panel',
	id : 'authorizationmanagement-rolemenu',
	plain : true,
	border : true,
	region : 'center',
	autoScroll : true,
	initComponent : function() {
		var me = this;
		var menutreestore = Ext.create('Ext.data.TreeStore', {
			autoLoad : true,
			proxy : {
				type : 'ajax',
				url : appBaseUri + '/sys/authority/getAuthorizationList',
				reader : {
					type : 'json',
					root : 'children'
				}
			}
		});
		Ext.apply(this, {
			// title : '菜单权限',
			store : menutreestore,
			rootVisible : false,
			tbar : [ {
				xtype : 'button',
				iconCls : 'icon-save',
				text : '保存菜单权限',
				scope : this,
				handler : me.saveMenuPermission
			} ]
		});
		this.callParent(arguments);
	},
	saveMenuPermission : function() {
		var me = this;
		var roleId = Ext.getCmp('authorizationmanagement-sysusergrid').currentRole;
		if (!roleId) {
			globalObject.infoTip('请先选择角色！');
			return;
		};
		var s = me.getChecked();
		var ids = [];
		for (var i = 0, r; r = s[i]; i++) {
			if (r.get('id') != 'root')
				ids.push(r.get('id'));
		}
		me.setLoading('权限保存中...');
		Ext.Ajax.request({
			url : appBaseUri + '/sys/roleauthority/saveRoleAuthority',
			params : {
				ids : ids.join(','),
				role : roleId
			},
			success : function(response) {
				me.setLoading(false);
				var res = Ext.JSON.decode(response.responseText);
				if (res && !res.success) {
					Ext.Msg.alert('出错信息', res.msg);
				} else {
					globalObject.msgTip('保存成功！');
				}
			},
			failure : function(response, opts) {
				me.setLoading(false);
				Ext.Msg.alert('出错信息', '操作失败！');
			}
		});
	}
});