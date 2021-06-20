package com.forestry.controller.sys;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.forestry.model.sys.RoomToTenant;
import com.forestry.model.sys.RoomToTenantQuery;
import com.forestry.model.sys.SysUser;
import com.forestry.model.sys.Tenant;
import com.forestry.model.sys.House;
import com.forestry.service.sys.RoomToTenantService;
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
 * @author 齐鸣鸣
 */
@Controller
@RequestMapping("/sys/roomToTenant")
public class RoomToTenantController extends ForestryBaseController<RoomToTenant> {

	@Resource
	private RoomToTenantService roomToTenantService;
	@Resource
	private TenantService tenantService;

	@RequestMapping(value = "/saveRoomToTenant", method = { RequestMethod.POST, RequestMethod.GET })
	public void doSave(RoomToTenant entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
		ExtJSBaseParameter parameter = ((ExtJSBaseParameter) entity);
		String startDateString=request.getParameter("startDate");
		if(startDateString!=null) {
	        java.sql.Date  startDate= core.util.BeanUtils.getDateOfString(startDateString);
			entity.setStartDate(startDate);
		}
		String endDateString=request.getParameter("endDate");
		if(endDateString!=null) {
			java.sql.Date  endDate= core.util.BeanUtils.getDateOfString(endDateString);;
			entity.setEndDate(endDate);
		}
		Tenant tenant=new Tenant();
		tenant.setTenantName(entity.getTenantName());
		tenant.setTenantIdentify(entity.getTenantIdentify());
		tenant.setTenantTel(entity.getTenantTel());
		tenant.setTenantFromShen(entity.getTenantFromShen());
		tenant.setTenantFromShi(entity.getTenantFromShi());
		tenant.setTenantFromXian(entity.getTenantFromXian());
		tenant.setTenantWorkOrganization(entity.getTenantWorkOrganization());
		tenant.setTenantDesc(entity.getTenantDesc());
		if (CMD_EDIT.equals(parameter.getCmd())) {
			//部分修改，会自动修改bean类的属性值未NULL
			tenant.setId(entity.getTenantId());
			tenantService.updateTenant(tenant);
			roomToTenantService.updateRoomToTenant(entity);
		} else if (CMD_NEW.equals(parameter.getCmd())) {
			tenantService.persist(tenant);//插入tenant后，teant.id将自动获得对应的数据库ID
			entity.setTenantId(tenant.getId());//
			entity.setTenantName(tenant.getTenantName());
			roomToTenantService.persist(entity);
		}
		parameter.setCmd(CMD_EDIT);
		parameter.setSuccess(true);
		writeJSON(response, parameter);
	}
	
	@RequestMapping("/checkRoomToTenant")
	public void checkRoomToTenant(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Long[] ids) throws IOException {
		SysUser sysUser=(SysUser)request.getSession().getAttribute(SESSION_SYS_USER);
		String checkOption=request.getParameter("checkOption");
		int flag = roomToTenantService.check(ids,checkOption,sysUser);
		if (flag>0) {
			writeJSON(response, "{success:true}");
		} else {
			writeJSON(response, "{success:false}");
		}
	}
	
	@RequestMapping(value = "/getRoomToTenantList", method = { RequestMethod.POST, RequestMethod.GET })
	public void getRoomToTenantList(HttpServletRequest request, HttpServletResponse response) throws Exception {	
		Integer firstResult = Integer.valueOf(request.getParameter("start"));
		Integer maxResults = Integer.valueOf(request.getParameter("limit"));
		String sortedObject = null;
		String sortedValue = null;
		List<LinkedHashMap<String, Object>> sortedList = mapper.readValue(request.getParameter("sort"), List.class);
		for (int i = 0; i < sortedList.size(); i++) {
			Map<String, Object> map = sortedList.get(i);
			sortedObject = (String) map.get("property");
			sortedValue = (String) map.get("direction");
		}
		SysUser sysUser=(SysUser)request.getSession().getAttribute(SESSION_SYS_USER);
		String sql="select DISTINCT h.departmentName,h.houseName,t.tenantIdentify,t.tenantTel,t.tenantFromShen,t.tenantFromShi,t.tenantFromXian,t.tenantWorkOrganization,rt.* from houseowner hw ,room r ,house h,room_tenant rt ,tenant t where rt.checkOpion='未审核' and t.id=rt.tenantId and rt.roomId=r.id and r.houseId=h.id ";
		if(sysUser.getUserGrade().equals("2级")){
			sql=sql+"and h.departmentId="+sysUser.getDepartmentId();
		}else
			if(sysUser.getUserGrade().equals("3级")){
				sql=sql+"and h.houseOwnerId="+sysUser.getId();
			}
		RoomToTenantQuery roomToTenantQuery = new RoomToTenantQuery();
		List<BaseParameter> arr=roomToTenantService.creatNativeSqlQuery(sql, roomToTenantQuery);
		ListView<BaseParameter> forestryListView = new ListView<BaseParameter>();;
		forestryListView.setTotalRecord(Long.valueOf(arr.size()));
		sql=sql+"  limit "+firstResult+","+(firstResult+maxResults-1)+""; 
		arr=roomToTenantService.creatNativeSqlQuery(sql, roomToTenantQuery);
		forestryListView.setData(arr);
		PageUtils pageUtils=new PageUtils();
		writeJSONQmm(response, forestryListView );
	}

	@RequestMapping("/deleteRoomToTenant")
	public void deleteRoomToTenant(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Long[] ids) throws IOException {
		if (ids != null && ids.length > 0) {
			for (int i = 0; i < ids.length; i++) {
				RoomToTenant roomToTenant=roomToTenantService.get(ids[i]);
				tenantService.deleteByPK(roomToTenant.getTenantId());	
			}
		}
		boolean flag = roomToTenantService.deleteByPK(ids);
		if (flag) {
			writeJSON(response, "{success:true}");
		} else {
			writeJSON(response, "{success:false}");
		}
	}
	//退租
	@RequestMapping("/quitTenant")
	public void quitTenant(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Long[] ids) throws IOException {
		if (ids != null && ids.length > 0) {
			for (int i = 0; i < ids.length; i++) {
				RoomToTenant roomToTenant=roomToTenantService.get(ids[i]);
				roomToTenant.setIsHistory("yes");
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//注意月份是MM
		        java.util.Date  registDate= new Date(System.currentTimeMillis());
		        roomToTenant.setEndDate(new java.sql.Date(registDate.getTime()));
		        roomToTenantService.persist(roomToTenant);	
			}
		}
		writeJSON(response, "{success:true}");
		
	}
	@RequestMapping(value = "/getExportedRoomToTenantList", method = { RequestMethod.POST, RequestMethod.GET })
	public void getExportedRoomToTenantList(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Long[] ids) throws Exception {
		List<Object[]> forestryList = roomToTenantService.queryExportedRoomToTenant(ids);
		//创建一个新的Excel
		HSSFWorkbook workBook = new HSSFWorkbook();
		//创建sheet页
		HSSFSheet sheet = workBook.createSheet("出租房间信息");
		//设置第一行为Header
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
		HSSFCell cell9 = row.createCell(8);
		HSSFCell cell10 = row.createCell(10);
		HSSFCell cell11 = row.createCell(11);
		HSSFCell cell12 = row.createCell(12);
		
		cell0.setCellValue("所在区域");
		cell1.setCellValue("房屋名称");
		cell2.setCellValue("房间名称");
		cell3.setCellValue("租客姓名");
		cell4.setCellValue("租客身份证");
		cell5.setCellValue("租客电话");
		cell6.setCellValue("省");
		cell7.setCellValue("市");
		cell8.setCellValue("县");
		cell9.setCellValue("工作单位");
		cell10.setCellValue("其他信息");
		cell11.setCellValue("起始时间");
		cell12.setCellValue("结束时间");
		
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
