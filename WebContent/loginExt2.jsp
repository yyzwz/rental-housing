<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String ctxpath = request.getContextPath();
	String theme = request.getParameter("theme");
%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
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
        var winLogin = Ext.create("Ext.window.Window", {
            width: 400,
            height: 170,
            modal: true, // 窗口弹出，其他地方不可操作  
            title: '&nbsp;登陆 ',
            collapsible: true,  // 收缩按钮  
            closable: false, // 是否显示关闭窗口按钮  
            iconCls: 'key', // cog , database_gear  
            resizable: false, // 窗体是否可以拉伸  
            constrain: true,
            items: [{
                xtype: 'form',
                width: '100%',
                id: 'myform',
                height: 140,
                //frame: true,  
                padding: '1px',
                buttonAlign: 'center',
                items: [{
                    xtype: 'textfield',
                    id: 'username',
                    name: 'username',
                    fieldCls: 'login_account',
                    fieldLabel: '账&nbsp;&nbsp;号&nbsp;&nbsp;',
                    width: 300,
                    margin: '10,10,10,10',
                    labelAlign: 'right',
                    allowBlank:false
                }, {
                    xtype: "textfield",
                    id: 'password',
                    name: 'password',
                    fieldCls: 'login_password',
                    width: 300,
                    fieldLabel: '密&nbsp;&nbsp;码&nbsp;&nbsp;',
                    margin: '10,10,10,10',
                    labelAlign: 'right',
                    inputType: 'password',
                    allowBlank: false
                }],
                buttons: [{
                    text: '登陆',
                    layout: 'fit',
                    type: 'submit',
                    handler: function () {
                    	var _username = Ext.getCmp('username').getValue();
                        var _password = Ext.getCmp('password').getValue();

                        if (_username == "") {
                            Ext.Msg.alert("提示", "用户名不能为空，请输入用户名");
                        } else if (_password == "") {
                            Ext.Msg.alert("提示", "密码不能为空，请输入用户名");
                        } else {
                            // 掩饰层 (遮罩效果)  
                            var myMask = new Ext.LoadMask(Ext.getBody(), { msg: "正在登陆，请稍后..." });
                            myMask.show();

                            Ext.Ajax.request({
                                url: ctxpath+'/sys/sysuser/houseOwnerLogin',//请求的服务器地址
                                method: 'POST',
                                params: {
                                	houseOwnerCode: _username,
                                	houseOwnerPassWord: _password
                                },
                                success: function (response, opts) {
                                    var sf = response.responseText.trim();
                                    if (sf=="success") {
                                        myMask.hide();
                                        Ext.Msg.alert("提示", "登陆成功!!!");
                                        //window.location.href = "/forestry/houseOwner/indexExt.jsp";
                                        document.location.href = "sys/sysuser/home";
                                    } else {
                                        myMask.hide();
                                        Ext.Msg.alert("提示", "登陆失败...");
                                    }
                                },
                                failure: function (response, opts) {
                                    myMask.hide();
                                    Ext.Msg.alert("提示", "登陆失败");
                                }
                            })
                        }
                    	 
                        
                    }
                }, {
                    text: '重置',
                    handler: function () {
                        Ext.getCmp('myform').form.reset();
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
