<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="initial-scale=1,user-scalable=no,maximum-scale=1" />
		<title>房屋租赁管理系统-登入</title>
		<link rel="stylesheet" type="text/css" href="${contextPath}/static/css/login_01.css">
		<link href="https://fonts.googleapis.com/css?family=Permanent+Marker" rel="stylesheet">
		<script type="text/javascript" src="${contextPath}/static/js/jquery-1.11.0.min.js"></script>
		<script type="text/javascript">
			var contextPath = "${contextPath}";
			var url ="${contextPath}/sys/sysuser/login";
			function login(){
				var userClass=$('input:radio[name="userClass"]:checked').val();
				if(userClass=="manager")
					url ="${contextPath}/sys/sysuser/login";
				else
					url ="${contextPath}/sys/sysuser/login2";	
				var $userName = document.getElementById("userName").value;
				var $password = document.getElementById("password").value;
				if($userName == ""){
					$("#tip").html("请输入用户名");
					return;
				}
				if($password == ""){
					$("#tip").html("请输入密码");
					return;
				}
				$.ajax({
					dataType : "json",
					url : url,
					type : "post",
					data : {
						userName : $userName,
						password : $password
					},
					complete : function(xmlRequest) {
					    
						var returninfo = eval("(" + xmlRequest.responseText + ")");
						if (returninfo.result == 1) {
							document.location.href = "${contextPath}/sys/sysuser/home";
						} else if (returninfo.result == -1) {
							$("#tip").html("用户名有误或已被禁用");
						} else if (returninfo.result == -2) {
							$("#tip").html("用户名或者密码错误");
						} else {
							$("#tip").html("服务器错误");
						}
					}
				});
			}
		</script>
		<style>
			#bgcc {
				position: absolute;
				width: 1019px;
				height: 577px;
				left: 40%;
				top: 35%;
				margin-left: -325px;
				margin-top: -149px;
			}
			
			#content {
				position: absolute;
				left: 221px;
				top: 111px;
				width: 582px;
				height: 296px;
				background-image: url('static/img/111.jpg');
			}
			
			.inputcss-tip {
				position: absolute;
				width: 200px;
				height: 30px;
				background: transparent;
				border: 0px solid #ffffff;
				color: red;
				font-size: 12px;
				left: 335px;
				top: 120px;
				top: 115px\9;
			}
			
			.inputcss-userName {
				position: absolute;
				width: 145px;
				height: 18px;
				background: transparent;
				border: 0px solid #ffffff;
				font-size: 15px;
				left: 330px;
				top: 136px;
			}
			
			.inputcss-password {
				position: absolute;
				width: 145px;
				height: 18px;
				background: transparent;
				border: 0px solid #ffffff;
				font-size: 15px;
				left: 330px;
				top: 174px;
			}
			
			.save {
				position: absolute;
				left: 335px;
				top: 213px;
			}
			.qmm {
				position: absolute;
				font-size: 15px;
				left: 300px;
				top: 103px;
			}
			.reset {
				position: absolute;
				left: 410px;
				top: 213px;
			}
			.qmmqmm {
				position: absolute;
				left: 410px;
				top: 240px;
			}
		</style>
	</head>
	<body>
		<div class="signup-form">
            <form class="" name="form1" id="form1" method="post" action="${contextPath}/sys/user/login">
                <h1>房屋租借管理系统</h1>
                <input name="userName" type="text" class="txtb" id="userName" value="admin" />
				<input name="password" type="password" class="txtb" id="password" value="123456" />
				<div style="display:inline">
					 <input name="userClass" type="radio" value="manager" checked="checked" style="height:20px;width:20px;display:inline"/>管理员
					 <input name="userClass" type="radio" value="houseOwner" style="height:20px;width:20px;display:inline"/>房东
				</div>
                	<input type="button" onclick="javascript:login();" value="登入" class="signup-btn">
                <a href="houseOwnerRegist.jsp">房东注册</a>
            </form>
        </div>
		
	</body>
</html>
