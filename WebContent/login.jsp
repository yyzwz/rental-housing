<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="initial-scale=1,user-scalable=no,maximum-scale=1" />
		<title>基于Java的出租房屋管理系统</title>
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
					$("#tip").html("请输入您的用户名");
					return;
				}
				if($password == ""){
					$("#tip").html("请输入您的密码");
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
				background-image: url('static/img/bg1.png');
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
		<div id="bgcc">
			<div id="content">
				<form name="form1" id="form1" method="post" action="${contextPath}/sys/user/login">
					<div id="tip" class="inputcss-tip"></div>
					<div>
						<input name="userName" type="text" class="inputcss-userName" id="userName" value="admin" /><br>
						<input name="password" type="password" class="inputcss-password" id="password" value="123456" />
						
					</div>
					<div class="qmm">
						 <input name="userClass" type="radio" value="manager" checked="checked"/>管理员  &nbsp;&nbsp;
						 <input name="userClass" type="radio" value="houseOwner"/>房东   &nbsp;&nbsp;&nbsp;
					</div>
					<div class="save">
						<a href="javascript:login();"> 
							<img src="static/img/btnlogin.png"></img>
						</a>
					</div>
					<div class="reset">
						<a href="#"> 
							<img src="static/img/reset.png"></img>
						</a>
					</div>
					<div class="qmmqmm"> 
						<a href="houseOwnerRegist.jsp">房东注册</a> &nbsp;&nbsp;&nbsp;
				
					</div>
				</form>
			</div>
			
		</div>
	</body>
</html>
