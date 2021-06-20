/** 待完成* */
Ext.define('Ext.ux.custom.AttachmentHtmlEditor', {
	extend : 'Ext.util.Observable',
	alias : 'widget.attachmenthtmleditor',
	langTitle : '插入附件',
	langIconCls : 'icon-attach',
	init : function(view) {
		var scope = this;
		view.on('render', function() {
			scope.onRender(view);
		});
	},

	/**
	 * 添加"插入附件"按钮
	 */
	onRender : function(view) {
		var scope = this;
		view.getToolbar().add({
			iconCls : scope.langIconCls,
			tooltip : {
				title : scope.langTitle,
				width : 60
			},
			handler : function() {
				scope.showAttachmentWindow(view);
			}
		});
	},

	/**
	 * 显示"插入附件"窗体
	 */
	showAttachmentWindow : function(view) {
		var scope = this;
		Ext.create('Ext.window.Window', {
			width : 400,
			height : 315,
			title : scope.langTitle,
			layout : 'fit',
			autoShow : true,
			modal : true,
			resizable : false,
			maximizable : false,
			constrain : true,
			plain : true,
			enableTabScroll : true,
			bodyPadding : '1 1 1 1',
			border : false,
			items : [ {
				xtype : 'panel',
				bodyPadding : '1 1 1 1',
				items : [ {
					xtype : 'form',
					layout : 'column',
					autoScroll : true,
					border : false,
					defaults : {
						columnWidth : 1,
						labelWidth : 60,
						labelAlign : 'right',
						padding : '5 5 5 5',
						allowBlank : false
					},
					items : [ {
						xtype : 'fileuploadfield',
						fieldLabel : '选择文件',
						beforeLabelTextTpl : zc.getStar(),
						buttonText : '请选择...',
						name : 'upload',
						emptyText : '请选择文件',
						blankText : '文件不能为空',
						listeners : {
							change : function(view, value, eOpts) {
								scope.uploadAttachmentCheck(view, value);
							}
						}
					}, {
						xtype : 'textfield',
						fieldLabel : '附件名称',
						name : 'fileName',
						id : 'zc_form_HtmlEditorAttachment_form_fileName',
						maxLength : 50,
						emptyText : '请输入附件名称',
						blankText : '附件名称不能为空'
					}, {
						xtype : 'textfield',
						fieldLabel : '附件说明',
						name : 'content',
						id : 'zc_form_HtmlEditorAttachment_form_content',
						allowBlank : true,
						maxLength : 100,
						emptyText : '简短的附件说明'
					}, {
						columnWidth : 1,
						xtype : 'fieldset',
						title : '上传须知',
						layout : {
							type : 'table',
							columns : 3
						},
						collapsible : false,// 是否可折叠
						defaultType : 'label',// 默认的Form表单组件
						items : [ {
							html : '1.上传文档不超过100KB',
							colspan : 3
						}, {
							html : '2.为了保证文档能正常使用，我们支持以下格式的文档上传',
							colspan : 3
						}, {
							html : '&nbsp;&nbsp;&nbsp;',
							rowspan : 5
						}, {
							html : 'MS Office文档：',
							colspan : 1,
							tdAttrs : {
								align : 'right'
							}
						}, {
							html : 'doc,docx,ppt,pptx,xls,xlsx,vsd,pot,pps,rtf',
							colspan : 1
						}, {
							html : 'WPS office系列：',
							colspan : 1,
							tdAttrs : {
								align : 'right'
							}
						}, {
							html : 'wps,et,dps',
							colspan : 1
						}, {
							html : 'PDF：',
							colspan : 1,
							tdAttrs : {
								align : 'right'
							}
						}, {
							html : 'pdf',
							colspan : 1
						}, {
							html : '纯文本：',
							colspan : 1,
							tdAttrs : {
								align : 'right'
							}
						}, {
							html : 'txt',
							colspan : 1
						}, {
							html : 'EPUB：',
							colspan : 1,
							tdAttrs : {
								align : 'right'
							}
						}, {
							html : 'epub',
							colspan : 1
						} ]
					} ],
					buttons : [ {
						text : '保存',
						action : 'btn_save',
						iconCls : 'saveIcon',
						handler : function(btn) {
							scope.saveUploadAttachment(btn, view);
						}
					}, {
						text : '取消',
						iconCls : 'cancelIcon',
						handler : function(btn) {
							btn.up('window').close();
						}
					} ]
				} ]
			} ]
		});
	},

	/**
	 * 插入附件验证
	 */
	uploadAttachmentCheck : function(fileObj, fileName) {
		var scope = this;
		var fileNameObj = Ext.getCmp('zc_form_HtmlEditorAttachment_form_fileName');
		var contentObj = Ext.getCmp('zc_form_HtmlEditorAttachment_form_content');
		// 附件类型验证
		if (!(scope.getAttachmentTypeCheck(scope.getAttachmentHZ(fileName)))) {
			Ext.MessageBox.alert('温馨提示', '上传附件类型有误');
			// 清空插入内容
			fileObj.reset();
			fileNameObj.setValue('');
			contentObj.setValue('');
			return;
		}
		// 设置默认的文件名称
		var defaultFileName = fileName.substr(0, fileName.lastIndexOf('.'));
		fileNameObj.setValue(defaultFileName);
		contentObj.setValue(defaultFileName);
	},

	/**
	 * 获取附件后缀(小写) 例如：txt
	 */
	getAttachmentHZ : function(AttachmentName) {
		// 后缀
		var hz = '';
		// 附件名称中最后一个.的位置
		var index = AttachmentName.lastIndexOf('.');
		if (index != -1) {
			// 后缀转成小写
			hz = AttachmentName.substr(index + 1).toLowerCase();
		}
		return hz;
	},

	/**
	 * 附件类型验证
	 */
	getAttachmentTypeCheck : function(hz) {
		var typestr = 'doc,docx,ppt,pptx,xls,xlsx,vsd,pot,pps,rtf,wps,et,dps,pdf,txt,epub';
		var types = typestr.split(',');// 附件类型
		for (var i = 0; i < types.length; i++) {
			if (hz == types[i]) {
				return true;
			}
		}
		return false;
	},

	/**
	 * 插入附件
	 */
	saveUploadAttachment : function(btn, view) {
		var scope = this;
		var windowObj = btn.up('window');// 获取Window对象
		var formObj = btn.up('form');// 获取Form对象
		if (formObj.isValid()) { // 验证Form表单
			formObj.form.doAction('submit', {
				url : zc.bp() + '/bdata_Thtmleditor_getUploadAttachment.action',
				method : 'POST',
				submitEmptyText : false,
				waitMsg : '正在上传附件,请稍候...',
				timeout : 60000, // 60s
				success : function(response, options) {
					var result = options.result;
					if (!result.success) {
						Ext.MessageBox.alert('温馨提示', result.msg);
						return;
					}
					scope.insertAttachment(view, result.data);
					windowObj.close();// 关闭窗体
				},
				failure : function(response, options) {
					Ext.MessageBox.alert('温馨提示', options.result.msg);
				}
			});
		}
	},

	/**
	 * 插入附件
	 */
	insertAttachment : function(view, data) {
		var url = data.url;
		var fileName = data.fileName;
		var content = data.content;
		var str = '&nbsp;<a  target="_blank" href="' + url + '" ';
		if (content != undefined && content != null && content != '') {
			str += ' title="' + content + '" ';
		}
		str += ' >' + fileName + '</a>&nbsp;';
		view.insertAtCursor(str);
	}
});
