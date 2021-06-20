<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String ctxpath = request.getContextPath();
	String theme = request.getParameter("theme");
%>
<HTML>
 <HEAD>
  <TITLE>登录</TITLE>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <link rel="stylesheet" type="text/css" href="static/js/ext-4.0/resources/css/ext-all.css" />
  <script type="text/javascript" src="static/js/ext-4.0/bootstrap.js"></script>
  <script type="text/javascript" src="static/js/ext-4.0/locale/ext-lang-zh_CN.js"></script>
 </HEAD>
 <script type="text/javascript">
    var ctxpath = '<%=ctxpath%>';
    
    Ext.onReady(function () {
    	function isValidator(filedName, filedValue){
    		/********** 登录名唯一验证 **********/
    		var flag = true;

    		var parameters = {
    			"filedName": filedName,
    			"filedValue": filedValue
    		}
    		Ext.Ajax.request({
    			url: ctxpath+"/com/houseOwner/isValidatorByFieldName",
    			method: "post",
    			params: parameters,
    			async: false,//必须为同步
    			success: function(response, config){//message:true 验证成功，message:false 验证失败
    				flag = Ext.JSON.decode(response.responseText);
    			}
    		});
    		return flag;
    	};
        var winLogin = Ext.create("Ext.window.Window", {
            width: 400,
            height: 340,
            modal: true, // 窗口弹出，其他地方不可操作  
            title: '房东注册 ',
            collapsible: true,  // 收缩按钮  
            closable: false, // 是否显示关闭窗口按钮  
            iconCls: 'key', // cog , database_gear  
            resizable: false, // 窗体是否可以拉伸  
            constrain: true,
            items: [
            	{
            	id:"myform",
                xtype: 'form',
                width: '100%',
                height: 300,
                //frame: true,  
                padding: '1px',
                buttonAlign: 'center',
                items: [
                {
                    xtype: 'textfield',
                    id: 'houseOwnerCode',
                    name: 'houseOwnerCode',
                    fieldCls: 'login_account',
                    fieldLabel: '账&nbsp;&nbsp;号&nbsp;&nbsp;',
                    width: 300,
                    margin: '10,10,10,10',
                    labelAlign: 'right',
                    blankText : "登录名不能为空",
                    allowBlank:false,
                    minLength :3, //允许输入的最少字符数
                    minLengthText : "最小长度不能少于3个字符！",//提示文本
                    maxLength : 10,
                    maxLengthText : "最大长度不能超过10个字符！",
                    validateOnChange: false,  //失去焦点时才进行验证
                    validator: function(value){
        				//var houseOwnerCode = Ext.get("houseOwnerCode");
        				if(value != null){
        					var result = isValidator("houseOwnerCode",value);
        					//return result;
        					if(result)
        						return result;
        					else
        						return "该账户已经存在！";
        				}
        			}
                }, {
                    xtype: "textfield",
                    id: 'houseOwnerPassWord',
                    name: 'houseOwnerPassWord',
                    fieldCls: 'login_account',
                    width: 300,
                    fieldLabel: '密&nbsp;&nbsp;码&nbsp;&nbsp;',
                    margin: '10,10,10,10',
                    labelAlign: 'right',
                    inputType: 'password',
                    allowBlank: false,
                    minLength :4, //允许输入的最少字符数
                    minLengthText : "最小长度不能少于4个字符！",//提示文本
                    maxLength : 10,
                    maxLengthText : "最大长度不能超过10个字符！",
                },{
                    xtype: 'textfield',
                    id: 'houseOwnerName',
                    name: 'houseOwnerName',
                    fieldCls: 'login_account',
                    fieldLabel: '姓名&nbsp;&nbsp;',
                    width: 300,
                    margin: '10,10,10,10',
                    labelAlign: 'right',
                    allowBlank:false,
                    regex:/^[\u4E00-\u9FA5]+$/,
                    regexText:'请输入汉字'
                }, {
                    xtype: "textfield",
                    id: 'houseOwnerIdentify',
                    name: 'houseOwnerIdentify',
                    fieldCls: 'login_account',
                    width: 300,
                    fieldLabel: '身&nbsp;&nbsp;份&nbsp;&nbsp;证',
                    margin: '10,10,10,10',
                    labelAlign: 'right',
                    allowBlank: false,
                    regex : /^(\d{18,18}|\d{15,15}|\d{17,17}x)$/,
                    regexText : '输入正确的身份号码',
                    validator: function(value){
        				//var houseOwnerCode = Ext.get("houseOwnerCode");
        				if(value != null){
        					var result = isValidator("houseOwnerIdentify",value);
        					//return result;
        					if(result)
        						return result;
        					else
        						return "该身份证号码已经存在！";
        				}
        			}  
                },{
                    xtype: 'textfield',
                    id: 'houseOwnerTel',
                    name: 'houseOwnerTel',
                    fieldCls: 'login_account',
                    fieldLabel: '电&nbsp;&nbsp;话&nbsp;&nbsp;',
                    width: 300,
                    margin: '10,10,10,10',
                    labelAlign: 'right',
                    allowBlank:false,
                    regex:/^1[\d]{10}$/,
                    regexText:'手机号码必须是1开头的,后面跟10位数字结尾',
                    validateOnChange: false,  //失去焦点时才进行验证
                    validator: function(value){
        				//var houseOwnerCode = Ext.get("houseOwnerCode");
        				if(value != null){
        					var result = isValidator("houseOwnerTel",value);
        					//return result;
        					if(result)
        						return result;
        					else
        						return "该电话已经存在！";
        				}
        			}
                }, {
                    xtype: "textfield",
                    id: 'houseOwnerAddress',
                    name: 'houseOwnerAddress',
                    fieldCls: 'login_account',
                    width: 300,
                    fieldLabel: '住&nbsp;&nbsp;址&nbsp;&nbsp;',
                    margin: '10,10,10,10',
                    labelAlign: 'right',
                    allowBlank: false,
                    maxLength : 30,
                    maxLengthText : "地址最大长度不能超过30个字符！"
                }, {
                    xtype: "textarea",
                    id: 'houseOwnerDesc',
                    name: 'houseOwnerDesc',
                    fieldCls: 'login_account',
                    width: 300,
                    fieldLabel: '描&nbsp;&nbsp;述&nbsp;&nbsp;',
                    margin: '10,10,10,10',
                    labelAlign: 'right',
                    allowBlank: true
                }
                
                ],
                buttons: [{
                    text: '注册',
                    layout: 'fit',
                    type: 'submit',
                    handler : function(btn, eventObj){
                    	var window = btn.up('window');
                    	var form = window.down('form').getForm();
						if (form.isValid()) {
	                          // 掩饰层 (遮罩效果)  
	                          var myMask = new Ext.LoadMask(Ext.getBody(), { msg: "正在注册，请稍后..." });
	                          myMask.show();
							  
	                          Ext.Ajax.request({
	                       	      async: false,
								  url : ctxpath+'/com/houseOwner/registHouseOwner',
								  params : {
									houseOwnerCode : Ext.getCmp('houseOwnerCode').getValue(),
									houseOwnerPassWord : Ext.getCmp('houseOwnerPassWord').getValue(),
									houseOwnerName : Ext.getCmp('houseOwnerName').getValue(),
									houseOwnerIdentify : Ext.getCmp('houseOwnerIdentify').getValue(),
									houseOwnerTel : Ext.getCmp('houseOwnerTel').getValue(),
									houseOwnerAddress : Ext.getCmp('houseOwnerAddress').getValue(),
									houseOwnerDesc : Ext.getCmp('houseOwnerDesc').getValue()
								  },
								  method : "POST",
	                              success: function (response, opts) {
	                                  var sf = response.responseText.trim();
	                                  if (sf=="success") {
	                                      myMask.hide();
	                                      Ext.MessageBox.alert("提示", "注册成功",callBack);
	                                      function callBack(id){
	                                    	  window.location.href = "login.jsp";
	                              		  }
	                                  } else {
	                                      myMask.hide();
	                                      Ext.MessageBox.alert("提示", "注册失败...");
	                                  }
	                              },
	                              failure: function (response, opts) {
	                                  myMask.hide();
	                                  Ext.MessageBox.alert("提示", "注册失败");
	                              }
	                          })
						}
                      }
                  
                }, {
                    text: '登录',
                    handler: function () {
                    	window.location.href = "login.jsp";
                        //Ext.getCmp('myform').form.reset();
                    }
                }]
            }],
            renderTo: Ext.getBody()
        });
        winLogin.show();
    })  
    		
  </script>
 <BODY>
 <br>
 
 </BODY>
</HTML>
