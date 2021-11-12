package com.forestry.controller.sys;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.forestry.core.ForestryBaseController;
import com.forestry.model.sys.House;
import com.forestry.model.sys.Room;
import com.forestry.model.sys.SysUser;
import com.forestry.model.sys.Tenant;
import com.forestry.model.sys.TenantQuery;
import com.forestry.service.sys.AuthorityService;
import com.forestry.service.sys.HouseService;
import com.forestry.service.sys.RoomService;
import com.forestry.service.sys.TenantService;

import core.extjs.ExtJSBaseParameter;
import core.extjs.ListView;
import core.support.BaseParameter;
import core.support.QueryResult;
import core.util.BeanUtils;
import core.util.PageUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
/**
 * @author 郑为中
 */
@Controller
@RequestMapping("/sys/Tenant")
public class TenantController extends ForestryBaseController<Tenant> {

	@Resource
	private TenantService tenantService;
	@Resource
	private RoomService roomService;
	@Resource
	private HouseService houseService;
	
	
	
	@RequestMapping("/checkTenant")
	public void checkTenant(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Long[] ids) throws IOException {
		String checkOption=request.getParameter("checkOption");
		int flag = tenantService.checkTenant(ids,checkOption);
		if (flag>0) {
			writeJSON(response, "{success:true}");
		} else {
			writeJSON(response, "{success:false}");
		}
	}
	@RequestMapping(value = "/saveTenant", method = { RequestMethod.POST, RequestMethod.GET })
	public void doSave(Tenant entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
		ExtJSBaseParameter parameter = ((ExtJSBaseParameter) entity);
		if (CMD_EDIT.equals(parameter.getCmd())) {
			//部分修改，会自动修改bean类的属性值未NULL
			tenantService.updateTenant(entity);
		} else if (CMD_NEW.equals(parameter.getCmd())) {
			tenantService.persist(entity);
		}
		parameter.setCmd(CMD_EDIT);
		parameter.setSuccess(true);
		writeJSON(response, parameter);
	}
	//手机端调用实例：http://localhost:8080/forestry/sys/Tenant/getTenantByTwoDimensionalCode?id=41
	@RequestMapping(value = "/getTenantByTwoDimensionalCode", method = { RequestMethod.POST, RequestMethod.GET })
	public void getTenantByTwoDimensionalCode(Tenant entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id=(String)request.getParameter("id");
		Tenant Tenant = tenantService.get(Long.valueOf(id));
		writeJSON(response, Tenant);
	}
	
	@RequestMapping(value = "/getTenantList", method = { RequestMethod.POST, RequestMethod.GET })
	public void getTenantList(HttpServletRequest request, HttpServletResponse response) throws Exception {		
		Integer firstResult = Integer.valueOf(request.getParameter("start"));
		Integer maxResults = Integer.valueOf(request.getParameter("limit"));
		String sortedObject = null;
		String sortedValue = null;
		
		String defaultSort="[{\"property\":\"id\",\"direction\":\"DESC\"}]";
		if(request.getParameter("sort")!=null)
			defaultSort = request.getParameter("sort");
		List<LinkedHashMap<String, Object>> sortedList = mapper.readValue(defaultSort, List.class);
		
		for (int i = 0; i < sortedList.size(); i++) {
			Map<String, Object> map = sortedList.get(i);
			sortedObject = (String) map.get("property");
			sortedValue = (String) map.get("direction");
		}
		SysUser sysUser=(SysUser)request.getSession().getAttribute(SESSION_SYS_USER);
		
		StringBuffer condictions=new  StringBuffer();
		String tenantName = request.getParameter("tenantName");
		if (StringUtils.isNotBlank(tenantName)) {
			tenantName=URLDecoder.decode(tenantName, "UTF-8");
			condictions.append(" and t.tenantName like  '%"+tenantName+"%'");
		}
		String tenantIdentify = request.getParameter("tenantIdentify");
		if (StringUtils.isNotBlank(tenantIdentify)) {
			tenantIdentify=URLDecoder.decode(tenantIdentify, "UTF-8");
			condictions.append(" and t.tenantIdentify like  '%"+tenantIdentify+"%'");
		}
		String tenantWorkOrganization = request.getParameter("tenantWorkOrganization");
		if (StringUtils.isNotBlank(tenantWorkOrganization)) {
			tenantWorkOrganization=URLDecoder.decode(tenantWorkOrganization, "UTF-8");
			condictions.append(" and t.tenantWorkOrganization like  '%"+tenantWorkOrganization+"%'");
		}
		String tenantFromShen = request.getParameter("tenantFromShen");
		if (StringUtils.isNotBlank(tenantFromShen)) {
			tenantFromShen=URLDecoder.decode(tenantFromShen, "UTF-8");
			condictions.append(" and t.tenantFromShen like  '%"+tenantFromShen+"%'");
		}
		String tenantFromShi = request.getParameter("tenantFromShi");
		if (StringUtils.isNotBlank(tenantFromShi)) {
			tenantFromShi=URLDecoder.decode(tenantFromShi, "UTF-8");
			condictions.append(" and t.tenantFromShi like  '%"+tenantFromShi+"%'");
		}
		String tenantFromXian = request.getParameter("tenantFromXian");
		if (StringUtils.isNotBlank(tenantFromXian)) {
			tenantFromXian=URLDecoder.decode(tenantFromXian, "UTF-8");
			condictions.append(" and t.tenantFromShi like  '%"+tenantFromXian+"%'");
		}
		String sql="select t.* ,h.departmentName,h.houseName,h.houseAddress,h.houseMapLocation,r.roomName from tenant t,room_tenant rt ,room r ,house h where t.id=rt.tenantId and rt.roomId=r.id and r.houseID=h.id ";
		
		if(sysUser.getUserGrade().equals("1级")){//若是普通1级管理人员，可以查询全部数据
			String departmentId = request.getParameter("departmentId");
			if (StringUtils.isNotBlank(departmentId)) {
				sql=sql+" and h.departmentId="+departmentId;
			}
		}else
			if(sysUser.getUserGrade().equals("2级")){//若是普通2级管理人员，只能可以查询所在行政区域的租客数据
				sql=sql+" and h.departmentId="+sysUser.getDepartmentId();
			}
		if(!condictions.toString().equals(""))
			sql=sql+condictions.toString();
		TenantQuery tenantQuery = new TenantQuery();
		List<BaseParameter> arr=tenantService.creatNativeSqlQuery(sql, tenantQuery);
		ListView<BaseParameter> forestryListView = new ListView<BaseParameter>();;
		forestryListView.setTotalRecord(Long.valueOf(arr.size()));
		sql=sql+"  limit "+firstResult+","+(firstResult+maxResults-1)+""; 
		arr=tenantService.creatNativeSqlQuery(sql, tenantQuery);
		forestryListView.setData(arr);
		PageUtils pageUtils=new PageUtils();
		writeJSONQmm(response, forestryListView );
	}

	@RequestMapping(value = "/getTenantListInMap", method = { RequestMethod.POST, RequestMethod.GET })
	public void getTenantListInMap(HttpServletRequest request, HttpServletResponse response) throws Exception {		
		Integer firstResult = Integer.valueOf(request.getParameter("start"));
		Integer maxResults = Integer.valueOf(request.getParameter("limit"));
		
		
		String sortedObject = null;
		String sortedValue = null;
		
		String defaultSort="[{\"property\":\"id\",\"direction\":\"DESC\"}]";
		if(request.getParameter("sort")!=null)
			defaultSort = request.getParameter("sort");
		List<LinkedHashMap<String, Object>> sortedList = mapper.readValue(defaultSort, List.class);
		
		for (int i = 0; i < sortedList.size(); i++) {
			Map<String, Object> map = sortedList.get(i);
			sortedObject = (String) map.get("property");
			sortedValue = (String) map.get("direction");
		}
		SysUser sysUser=(SysUser)request.getSession().getAttribute(SESSION_SYS_USER);
		String sql="select t.* ,h.id as houseId,h.houseOwnerId,h.houseOwnerName,h.houseImage,h.departmentName,h.houseName,h.houseAddress,h.houseMapLocation,r.roomName from tenant t,room_tenant rt ,room r ,house h where t.id=rt.tenantId and rt.roomId=r.id and r.houseID=h.id ";
		if(request.getParameter("departmentId")!=null) {
			Integer departmentId = Integer.valueOf(request.getParameter("departmentId"));
			if(departmentId!=-1)
				sql=sql+" and h.departmentId="+departmentId;
		}
		if(request.getParameter("houseOwnerId")!=null) {
			Integer houseOwnerId = Integer.valueOf(request.getParameter("houseOwnerId"));
			if(houseOwnerId!=-1)
				sql=sql+" and h.houseOwnerId="+houseOwnerId;
		}
		
		TenantQuery tenantQuery = new TenantQuery();
		List<BaseParameter> arr=tenantService.creatNativeSqlQuery(sql, tenantQuery);
		ListView<BaseParameter> forestryListView = new ListView<BaseParameter>();;
		forestryListView.setTotalRecord(Long.valueOf(arr.size()));
		sql=sql+"  limit "+firstResult+","+(firstResult+maxResults-1)+""; 
		arr=tenantService.creatNativeSqlQuery(sql, tenantQuery);
		forestryListView.setData(arr);
		PageUtils pageUtils=new PageUtils();
		writeJSONQmm(response, forestryListView );
	}
	
	
	@RequestMapping("/deleteTenant")
	public void deleteTenant(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Long[] ids) throws IOException {
		boolean flag = tenantService.deleteByPK(ids);
		if (flag) {
			writeJSON(response, "{success:true}");
		} else {
			writeJSON(response, "{success:false}");
		}
	}
	
	@RequestMapping(value = "/getExportedTenantList", method = { RequestMethod.POST, RequestMethod.GET })
	public void getExportedTenantList(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Long[] ids) throws Exception {
		List<Object[]> forestryList = tenantService.queryExportedTenant(ids);
		//创建一个新的Excel
		HSSFWorkbook workBook = new HSSFWorkbook();
		//创建sheet页
		HSSFSheet sheet = workBook.createSheet("出租房间信息");
		//设置第一行为Header
		// update by 郑为中  ,要求导出租客同时导出房东信息
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell0 = row.createCell(0);
		HSSFCell cell1 = row.createCell(1);
		HSSFCell cell2 = row.createCell(2);
		HSSFCell cell3 = row.createCell(3);
		HSSFCell cell4 = row.createCell(4);
		HSSFCell cell5 = row.createCell(5);
		HSSFCell cell6 = row.createCell(6);
		HSSFCell cell7 = row.createCell(7);
		HSSFCell cell8 = row.createCell(8);
		HSSFCell cell9 = row.createCell(9);
		HSSFCell cell10 = row.createCell(10);
		HSSFCell cell11 = row.createCell(11);
		HSSFCell cell12 = row.createCell(12);
		HSSFCell cell13 = row.createCell(13);
		HSSFCell cell14 = row.createCell(14);
		HSSFCell cell15 = row.createCell(15);
		
		cell0.setCellValue("房东姓名");
		cell1.setCellValue("身份证");
		cell2.setCellValue("电话号码");
		cell3.setCellValue("房屋地址");
		cell4.setCellValue("所在区域");
		cell5.setCellValue("房屋名称");
		cell6.setCellValue("房间名称");
		cell7.setCellValue("地址");
		cell8.setCellValue("租客姓名");
		cell9.setCellValue("身份证号");
		cell10.setCellValue("租客电话");
		cell11.setCellValue("省");
		cell12.setCellValue("市");
		cell13.setCellValue("县");
		cell14.setCellValue("工作单位");
		cell15.setCellValue("其他信息");

		
		for (int i = 0; i < forestryList.size(); i++) {
			Object[] forestry = forestryList.get(i);
			row = sheet.createRow(i + 1);
			cell0 = row.createCell(0);
			cell1 = row.createCell(1);
			cell2 = row.createCell(2);
			cell3 = row.createCell(3);
			cell4 = row.createCell(4);
			cell5 = row.createCell(5);
			cell6 = row.createCell(6);
			cell7 = row.createCell(7);
			cell8 = row.createCell(8);
			cell9 = row.createCell(9);
			cell10 = row.createCell(10);
			cell11 = row.createCell(11);
			cell12 = row.createCell(12);
			cell13 = row.createCell(13);
			cell14 = row.createCell(14);
			cell15 = row.createCell(15);

			cell0.setCellValue(BeanUtils.getValuleInObject(forestry[0]));
			cell1.setCellValue(BeanUtils.getValuleInObject(forestry[1]));
			cell2.setCellValue(BeanUtils.getValuleInObject(forestry[2]));
			cell3.setCellValue(BeanUtils.getValuleInObject(forestry[3]));
			cell4.setCellValue(BeanUtils.getValuleInObject(forestry[4]));
			cell5.setCellValue(BeanUtils.getValuleInObject(forestry[5]));
			cell6.setCellValue(BeanUtils.getValuleInObject(forestry[6]));
			cell7.setCellValue(BeanUtils.getValuleInObject(forestry[7]));
			cell8.setCellValue(BeanUtils.getValuleInObject(forestry[8]));
			cell9.setCellValue(BeanUtils.getValuleInObject(forestry[9]));
			cell10.setCellValue(BeanUtils.getValuleInObject(forestry[10]));
			cell11.setCellValue(BeanUtils.getValuleInObject(forestry[11]));
			cell12.setCellValue(BeanUtils.getValuleInObject(forestry[12]));
			cell13.setCellValue(BeanUtils.getValuleInObject(forestry[13]));
			cell14.setCellValue(BeanUtils.getValuleInObject(forestry[14]));
			cell15.setCellValue(BeanUtils.getValuleInObject(forestry[15]));

			sheet.setColumnWidth(0, 6000);
			sheet.setColumnWidth(1, 6000);
			sheet.setColumnWidth(2, 6000);
			sheet.setColumnWidth(3, 6000);
			sheet.setColumnWidth(4, 6000);
			sheet.setColumnWidth(5, 6000);
			sheet.setColumnWidth(6, 6000);
			sheet.setColumnWidth(7, 6000);
			sheet.setColumnWidth(8, 6000);
			sheet.setColumnWidth(9, 6000);
			sheet.setColumnWidth(10, 6000);
			sheet.setColumnWidth(11, 6000);
			sheet.setColumnWidth(12, 6000);
			sheet.setColumnWidth(13, 6000);
			sheet.setColumnWidth(14, 6000);
			sheet.setColumnWidth(15, 6000);
		}
		response.reset();
		response.setContentType("application/msexcel;charset=UTF-8");
		try {
			response.addHeader("Content-Disposition", "attachment;filename=file.xls");
			OutputStream out = response.getOutputStream();
			workBook.write(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
