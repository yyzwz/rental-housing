<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.forestry.model.sys.SysUser"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>基于Java的出租房屋管理系统</title>
		
		<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>

    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.0.3/dist/leaflet.css" />
    <script src="https://unpkg.com/leaflet@1.0.3/dist/leaflet.js"></script> 
    <script src="https://cdn.bootcss.com/proj4js/2.4.3/proj4.js"></script>
    <script src="https://cdn.bootcss.com/proj4leaflet/1.0.1/proj4leaflet.min.js"></script>
	<script type="text/javascript" src="${contextPath}/static/ext/examples/shared/tileLayer.baidu.js"></script>	
		
		<link rel="stylesheet" type="text/css" href="${contextPath}/static/ext/examples/portal/portal.css" />
		<link rel="stylesheet" type="text/css" href="${contextPath}/static/ext/examples/shared/example.css" />
		<link rel="stylesheet" type="text/css" href="${contextPath}/static/css/forestry-style.css"  />
	   <script type="text/javascript" src="http://api.map.baidu.com/api?v=1.5&ak=C09c918bbe0b24d988da51a15644d98a"></script>	 
	    <script type="text/javascript" src="${contextPath}/static/ext/examples/shared/include-ext.js"></script>
	    <script type="text/javascript" src="${contextPath}/static/ext/locale/ext-lang-zh_CN.js"></script>
	    <script type="text/javascript" src="${contextPath}/static/ext/examples/shared/options-toolbar.js"></script>
	    <script type="text/javascript" src="${contextPath}/static/ext/examples/shared/examples.js"></script>
	    <script type="text/javascript">
	  		var userName = '${SESSION_SYS_USER.userName}';
	  		var globalRoleId = '${SESSION_SYS_USER.role}';
	    	var appBaseUri = '${contextPath}';
	    	var appName = '基于Java的出租房屋管理系统';
	        Ext.Loader.setPath('Ext.app', '${contextPath}/static/ext/examples/portal/classes');
	        Ext.Loader.setPath('Ext.ux', '${contextPath}/static/ext/examples/ux');
	        Ext.Loader.setPath('Forestry.app', '${contextPath}/static/ext/examples/portal');
	    </script>
	    <script type="text/javascript" src="${contextPath}/static/ext/examples/portal/portal.js"></script>
	    <script type="text/javascript" src="${contextPath}/static/ext/examples/portal/regions.js"></script>
	    <%
			SysUser sysUser=(SysUser)request.getSession().getAttribute("SESSION_SYS_USER");
		    String userGade=sysUser.getUserGrade();
		    String userDepartmentId=String.valueOf(sysUser.getDepartmentId());
		    String userDepartmentName=sysUser.getDepartmentName();
		    String houseOwnerId=String.valueOf(sysUser.getId());
		    String houseOwnerName=sysUser.getUserName();
		    String selectedDepartmentIdInMap="-1";
		    String selectedHouseOwnerIdInMap="-1";
		    String mapDetailTitle="";
		    if(sysUser.getUserGrade().equals("3级")){
		    	selectedHouseOwnerIdInMap=houseOwnerId;
		    	mapDetailTitle=houseOwnerName+"区域出租房屋情况详情";
		    }else
		    	if(sysUser.getUserGrade().equals("1级")){
		    		mapDetailTitle="开发区域出租房屋情况详情";
		   		}else
		   			if(sysUser.getUserGrade().equals("2级")){
			    		mapDetailTitle=userDepartmentName+"区域出租房屋情况详情";
			   		}
		%>
	    <script type="text/javascript">
		    var selectedDepartmentIdInMap=<%=selectedDepartmentIdInMap %>;//默认区域参数departmentId
		    var selectedHouseOwnerIdInMap=<%=selectedHouseOwnerIdInMap %>;//默认区域参数houseOwnerId
		    //Ext.getCmp("forestryTenantInMapHome").items.items[2].setTitle("<%=mapDetailTitle %>")
		    function showDepartmentTenant(departmentId,departmentName) {
		    	//alert(Ext.getCmp("forestryTenantInMapHome").items.items[2].title);
		    	Ext.getCmp("forestryTenantInMapHome").items.items[2].setTitle(departmentName+"区域出租房屋情况详情")
		    	//Ext.getCmp("forestryTenantInMapHome").items.items[2].title=departmentName+"区域出租房屋情况详情";
		        var store=Ext.getCmp("forestryTenantInMapStoreId").getStore()
		        var params={
						start : '0',
						limit : '100000000',
						departmentId:departmentId
					}
		        Ext.apply(store.proxy.extraParams, params);//重新设置地图图标对应对区域参数departmentId
		        store.reload();
		    };
		    function showMyTenants(houseOwnerId,houseOwnerName) {
		    	//alert(houseOwnerName);
		    	//alert(Ext.getCmp("forestryTenantInMapHome").items.items[2].title);
		    	Ext.getCmp("forestryTenantInMapHome").items.items[2].setTitle(houseOwnerName+"的出租房屋情况详情")
		    	//Ext.getCmp("forestryTenantInMapHome").items.items[2].title=departmentName+"区域出租房屋情况详情";
		        var store=Ext.getCmp("forestryTenantInMapStoreId").getStore()
		        var params={
						start : '0',
						limit : '100000000',
						houseOwnerId:houseOwnerId
					}
		        Ext.apply(store.proxy.extraParams, params);//重新设置地图图标对应对区域参数departmentId
		        store.reload();
		    };
		    function checkTime(i){
		       if (i<10){
		           i="0" + i
		       }
		       return i;
		    }
		    function getDateStringFormat(qmm){
		    	if(qmm==null||qmm=="null")
		    		return "";
		    	var d=new Date(qmm)
		    	result=d.getFullYear() + '-' + checkTime(d.getMonth() + 1) + '-' + checkTime(d.getDate()) ;
		    	return result;
		    }
		   
	        Ext.require([
	            'Ext.layout.container.*',
	            'Ext.resizer.Splitter',
	            'Ext.fx.target.Element',
	            'Ext.fx.target.Component',
	            'Ext.window.Window',
	            'Ext.app.Portlet',
	            'Ext.app.PortalColumn',
	            'Ext.app.PortalPanel',
	            'Ext.app.Portlet',
	            'Ext.app.PortalDropZone'
	        ]);
	     	// alert("<spring:message code='g_operateFailure' />");
	    </script>
	</head>
	<body>
		<span id="app-msg" style="display:none;"></span>
	    <form id="history-form" class="x-hide-display">
	        <input type="hidden" id="x-history-field" />
	        <iframe id="x-history-frame"></iframe>
	    </form>
	</body>
</html>
