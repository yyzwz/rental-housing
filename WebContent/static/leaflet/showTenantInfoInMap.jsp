<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.forestry.model.sys.SysUser"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<style type="text/css">
		body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
	</style>
	<script type="text/javascript" src="//api.map.baidu.com/api?v=2.0&ak=859d16285fd000feec89e9032513f8bb"></script>
	<title>根据城市名设置地图中心点</title>
</head>
<body>
	<div id="allmap"></div>
</body>
</html>
<%
	SysUser sysUser=(SysUser)request.getSession().getAttribute("SESSION_SYS_USER");
    String userGade=sysUser.getUserGrade();
    String userDepartmentId=String.valueOf(sysUser.getDepartmentId());
    String userDepartmentName=sysUser.getDepartmentName();
    String houseOwnerId=String.valueOf(sysUser.getId());
    String houseOwnerName=sysUser.getUserName();
%>
<script type="text/javascript">
	if(<%=userDepartmentId %>==null){		  
		//showMyTenants
		// map.centerAndZoom(new BMap.Point(121.719823,29.253901), 15);//定位镇政府
	    // 百度地图API功能
		var map = new BMap.Map("allmap");    // 创建Map实例
		map.centerAndZoom("镇政府",15);    // 初始化地图,设置中心点坐标和地图级别
		//添加地图类型控件
		map.addControl(new BMap.MapTypeControl({
			mapTypes:[
	            BMAP_NORMAL_MAP,
	            BMAP_HYBRID_MAP
	        ]}));	  
		map.setCurrentCity("北京");          // 设置地图显示的城市 此项是必须设置的
		map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
		parent.showMyTenants("<%=houseOwnerId %>","<%=houseOwnerName %>");//调用父页面的函数main.jsp
		
	}
	else {
		// 百度地图API功能
		var map = new BMap.Map("allmap");  // 创建Map实例
		
		map.enableScrollWheelZoom();   //启用滚轮放大缩小，默认禁用
		map.enableContinuousZoom();    //启用地图惯性拖拽，默认禁用
		xmlHttp=new XMLHttpRequest();
	    xmlHttp.open("POST","/forestry/sys/department/getDepartmentListInMap",true);
	    xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	    xmlHttp.send("start=0&limit=100000000");
    
	    xmlHttp.onreadystatechange=function () {
	      if (xmlHttp.readyState ==4 && xmlHttp.status ==200){
	    	  var result=eval('(' + xmlHttp.responseText + ')')
	    	  var taotalNum=result.totalRecord;
	          var departments=result.data;
	          for(var i=0;i<departments.length;i++){
	        	  var department =departments[i];
	        	  //console.log(department.id);
	        	  //var point1 = new BMap.Point(121.719823,29.253901);
	        	  if("<%=userGade %>"=="1级"){
	        		  map.centerAndZoom(new BMap.Point(121.719823,29.253901), 15);//定位镇政府
		        	  jingAndWei=department.jwd.split(",");
		        	  var point1 = new BMap.Point(jingAndWei[0],jingAndWei[1]);//121.738668,29.260192
			      	  var marker = new BMap.Marker(point1); // 创建点
			      	  var label = new BMap.Label(department.name+"：租客人数"+department.countTenantNumber,{offset:new BMap.Size(20,-10)});
			      	  marker.setLabel(label);
			      	  marker.addEventListener("click",attribute);
			      	  map.addOverlay(marker);    //增加点
			      	  marker.customData={departmentId:department.id,departmentName:department.name};//自定义参数id
			    
			      	  function attribute(e){
			      		var departmentId= e.target.customData.departmentId;
			      		var departmentName= e.target.customData.departmentName;
			      		
			    		var p = marker.getPosition();  //获取marker的位置
			    		parent.showDepartmentTenant(departmentId,departmentName);//调用父页面的函数main.jsp
		
			    	 }
			      	 
	        	  }else{//若是区域管理员，地图直线本区域的
			      		if("<%=userDepartmentId %>"==department.id){
				        	  jingAndWei=department.jwd.split(",");
				        	  var point1 = new BMap.Point(jingAndWei[0],jingAndWei[1]);//121.738668,29.260192
				        	  map.centerAndZoom(point1,100);
				        	  var marker = new BMap.Marker(point1); // 创建点
					      	  var label = new BMap.Label(department.name+"：租客人数"+department.countTenantNumber,{offset:new BMap.Size(20,-10)});
					      	  marker.setLabel(label);
					      	  marker.addEventListener("click",attribute);
					      	  map.addOverlay(marker);    //增加点
					      	  marker.customData={departmentId:department.id,departmentName:department.name};//自定义参数id
					    	  parent.showDepartmentTenant(department.id,department.name);//调用父页面的函数main.jsp
				
					    	 
				      	}
	        	  }
	        	  
	          }
	      }
	    }
    }

	
	
	
	
	
	
	
	
</script>