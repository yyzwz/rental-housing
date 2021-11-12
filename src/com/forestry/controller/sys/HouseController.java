package com.forestry.controller.sys;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.forestry.model.sys.Forestry;
import com.forestry.model.sys.House;
import com.forestry.model.sys.HouseType;
import com.forestry.model.sys.SysUser;
import com.forestry.service.sys.HouseService;

import core.extjs.ExtJSBaseParameter;
import core.extjs.ListView;
import core.support.QueryResult;
import core.util.BeanUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
/**
 * @author 郑为中
 */
@Controller
@RequestMapping("/sys/house")
public class HouseController extends ForestryBaseController<House> {

	@Resource
	private HouseService houseService;

	@RequestMapping("/getHouse")
	public void getHouse(HttpServletRequest request, HttpServletResponse response) throws Exception {
		House house = new House();
		SysUser sysUser=(SysUser)request.getSession().getAttribute(SESSION_SYS_USER);
		
		ArrayList<String> arrPropNames=new ArrayList<String>();
		ArrayList<Object> arrPropValues=new ArrayList<Object>();
		
		String checkOpion=request.getParameter("checkOpion");	
		if(checkOpion!=null) {
			arrPropNames.add("checkOpion");
			checkOpion=URLDecoder.decode(checkOpion);
			arrPropValues.add(checkOpion);
		}
		
		if(sysUser.getUserGrade().equals("2级")){//若是普通1级管理人员，可以查询全部数据
			Long loginerDepartmentId = sysUser.getDepartmentId();
			arrPropNames.add("departmentId");
			arrPropValues.add(Long.valueOf(loginerDepartmentId));
		}else
			if(sysUser.getUserGrade().equals("3级")) {
				arrPropNames.add("houseOwnerId");
				arrPropValues.add(Long.valueOf(sysUser.getId()));
			}
		String[] propNames= new String[arrPropNames.size()];
		Object[] propValues= new Object[arrPropNames.size()];
		for(int i=0;i<arrPropNames.size();i++) {
			propNames[i]=(String)arrPropNames.get(i);
			propValues[i]=arrPropValues.get(i);
		}
		List<House> houseList = houseService.queryByProerties(propNames, propValues);
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < houseList.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.element("ItemText", houseList.get(i).getHouseName());
			jsonObject.element("ItemValue", houseList.get(i).getId());
			jsonArray.add(jsonObject);
		}
		JSONObject resultJSONObject = new JSONObject();
		resultJSONObject.element("list", jsonArray);
		writeJSON(response, resultJSONObject);
	}
	@RequestMapping("/checkHouse")
	public void checkHouse(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Long[] ids) throws IOException {
		SysUser sysUser=(SysUser)request.getSession().getAttribute(SESSION_SYS_USER);
		String checkOption=request.getParameter("checkOption");
		int flag = houseService.check(ids,checkOption,sysUser);
		if (flag>0) {
			writeJSON(response, "{success:true}");
		} else {
			writeJSON(response, "{success:false}");
		}
	}
	@RequestMapping(value = "/saveHouse", method = { RequestMethod.POST, RequestMethod.GET })
	public void doSave(House entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
		ExtJSBaseParameter parameter = ((ExtJSBaseParameter) entity);
		String houseName=(String)request.getParameter("houseName");	
		if (CMD_EDIT.equals(parameter.getCmd())) {
			//部分修改，会自动修改bean类的属性值未NUL
			houseService.updateHouse(entity);
		} else if (CMD_NEW.equals(parameter.getCmd())) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//注意月份是MM
	        java.util.Date  registDate= new Date(System.currentTimeMillis());
			entity.setRegistDate(new java.sql.Date(registDate.getTime()));
			SysUser sysUser=(SysUser)request.getSession().getAttribute(SESSION_SYS_USER);
			entity.setHouseOwnerId(sysUser.getId());
			entity.setHouseOwnerName(sysUser.getRealName());
			houseService.persist(entity);
		}
		parameter.setCmd(CMD_EDIT);
		parameter.setSuccess(true);
		writeJSON(response, parameter);
	}
	//手机端调用实例：http://localhost:8080/forestry/sys/house/getHouseByTwoDimensionalCode?id=41
	@RequestMapping(value = "/getHouseByTwoDimensionalCode", method = { RequestMethod.POST, RequestMethod.GET })
	public void getHouseByTwoDimensionalCode(House entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id=(String)request.getParameter("id");
		House house = houseService.get(Long.valueOf(id));
		writeJSON(response, house);
	}
	@RequestMapping(value = "/saveHouseTwoDimensionalCode", method = { RequestMethod.POST, RequestMethod.GET })
	public void saveHouseTwoDimensionalCode(House entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id=(String)request.getParameter("id");
		//获取二维码产生的绝对路径
		String filePath=request.getServletContext().getRealPath("/")+"\\static\\img\\ORCodeImages";
		int flag = houseService.saveHouseTwoDimensionalCode(request, filePath,Long.valueOf(id));
		if (flag>0) {
			writeJSON(response, "{success:true}");
		} else {
			writeJSON(response, "{success:false}");
		}
	}
	@RequestMapping(value = "/getHouseList", method = { RequestMethod.POST, RequestMethod.GET })
	public void getHouseList(HttpServletRequest request, HttpServletResponse response) throws Exception {	
		Integer firstResult = Integer.valueOf(request.getParameter("start"));
		Integer maxResults = Integer.valueOf(request.getParameter("limit"));
		String sortedObject = null;
		String sortedValue = null;
		String qmm=request.getParameter("sort");//"[{\"property\":\"registDate\",\"direction\":\"DESC\"}]";
		List<LinkedHashMap<String, Object>> sortedList = mapper.readValue(qmm, List.class);
		for (int i = 0; i < sortedList.size(); i++) {
			Map<String, Object> map = sortedList.get(i);
			sortedObject = (String) map.get("property");
			sortedValue = (String) map.get("direction");
		}
		House house = new House();
		String registDate=request.getParameter("registDate");
		if (StringUtils.isNotBlank(registDate)) {
			house.set$leDate_registDate(registDate);
		}
		String houseName = request.getParameter("houseName");
		if (StringUtils.isNotBlank(houseName)) {
			houseName=URLDecoder.decode(houseName, "UTF-8");
			house.set$like_houseName(houseName);
		}
		String checkOpion = request.getParameter("checkOpion");
		//checkOpion=URLDecoder.decode(request.getParameter("checkOpion"),"utf-8");
		if (StringUtils.isNotBlank(checkOpion)) {
			checkOpion=URLDecoder.decode(checkOpion, "UTF-8");
			house.set$eq_checkOpion(checkOpion);
		}
		String departmentId = request.getParameter("departmentId");
		if (StringUtils.isNotBlank(departmentId)) {
			departmentId=URLDecoder.decode(departmentId, "UTF-8");
			house.set$eq_departmentId(Long.valueOf(departmentId));
		}
		SysUser sysUser=(SysUser)request.getSession().getAttribute(SESSION_SYS_USER);
		if(sysUser.getUserGrade().equals("2级")){//若是普通1级管理人员，可以查询全部数据
			Long loginerDepartmentId = sysUser.getDepartmentId();
			house.set$eq_departmentId(loginerDepartmentId);
		}else
			if(sysUser.getUserGrade().equals("3级")) {
				house.set$eq_houseOwnerId(Long.valueOf(sysUser.getId()));
			}
		house.setFirstResult(firstResult);
		house.setMaxResults(maxResults);
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put(sortedObject, sortedValue);
		house.setSortedConditions(sortedCondition);
		QueryResult<House> queryResult = houseService.doPaginationQuery(house);
		ListView<House> forestryListView = new ListView<House>();
		forestryListView.setData(queryResult.getResultList());
		forestryListView.setTotalRecord(queryResult.getTotalCount());
		writeJSON(response, forestryListView);
	}

	@RequestMapping("/deleteHouse")
	public void deleteHouse(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Long[] ids) throws IOException {
		boolean flag = houseService.deleteByPK(ids);//ids=[1,12,13]
		if (flag) {
			writeJSON(response, "{success:true}");
		} else {
			writeJSON(response, "{success:false}");
		}
	}
	
	@RequestMapping(value = "/queryHouse")
	public void queryHouse(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		House house = new House();
		String houseName = request.getParameter("houseName");
		if (StringUtils.isNotBlank(houseName)) {
			houseName=URLDecoder.decode(houseName, "UTF-8");
			house.set$like_houseName(houseName);
		}
		String houseOwnerName = request.getParameter("houseOwnerName");
		if (StringUtils.isNotBlank(houseOwnerName)) {
			houseOwnerName=URLDecoder.decode(houseOwnerName, "UTF-8");
			house.set$like_houseOwnerName(houseOwnerName);
		}
		String houseTypeId = request.getParameter("houseTypeId");
		if (StringUtils.isNotBlank(houseTypeId)) {
			houseTypeId=URLDecoder.decode(houseTypeId, "UTF-8");
			house.set$eq_houseTypeId(Long.valueOf(houseTypeId));
		}
		String departmentId = request.getParameter("departmentId");
		if (StringUtils.isNotBlank(departmentId)) {
			departmentId=URLDecoder.decode(departmentId, "UTF-8");
			house.set$eq_departmentId(Long.valueOf(departmentId));
		}
		
		String checkOpion = request.getParameter("checkOpion");
		//checkOpion=URLDecoder.decode(request.getParameter("checkOpion"),"utf-8");
		if (StringUtils.isNotBlank(checkOpion)) {
			checkOpion=URLDecoder.decode(checkOpion, "UTF-8");
			house.set$eq_checkOpion(checkOpion);
		}
		
		SysUser sysUser=(SysUser)request.getSession().getAttribute(SESSION_SYS_USER);
		if(sysUser.getUserGrade().equals("2级")){//若是普通1级管理人员，可以查询全部数据
			Long loginerDepartmentId = sysUser.getDepartmentId();
			house.set$eq_departmentId(loginerDepartmentId);
		}
		
		house.setFirstResult(firstResult);
		house.setMaxResults(maxResults);
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put(sortedObject, sortedValue);
		house.setSortedConditions(sortedCondition);
		QueryResult<House> queryResult = houseService.doPaginationQuery(house);
		ListView<House> forestryListView = new ListView<House>();
		forestryListView.setData(queryResult.getResultList());
		forestryListView.setTotalRecord(queryResult.getTotalCount());
		writeJSON(response, forestryListView);
	}
	
	@RequestMapping(value = "/getExportedHouseList", method = { RequestMethod.POST, RequestMethod.GET })
	public void exportForestry(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Long[] ids) throws Exception {
		List<Object[]> forestryList = houseService.queryExportedHouse(ids);
		//创建一个新的Excel
		HSSFWorkbook workBook = new HSSFWorkbook();
		//创建sheet页
		HSSFSheet sheet = workBook.createSheet("房屋信息");
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
		
		cell0.setCellValue("所在区域");
		cell1.setCellValue("房屋类型");
		cell2.setCellValue("房屋名称");
		cell3.setCellValue("房东姓名");
		cell4.setCellValue("房屋地址");
		cell5.setCellValue("登记日期");
		cell6.setCellValue("其他描述");
		cell7.setCellValue("审核意见");
		
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

			cell0.setCellValue(BeanUtils.getValuleInObject(forestry[0]));
			cell1.setCellValue(BeanUtils.getValuleInObject(forestry[1]));
			cell2.setCellValue(BeanUtils.getValuleInObject(forestry[2]));
			cell3.setCellValue(BeanUtils.getValuleInObject(forestry[3]));
			cell4.setCellValue(BeanUtils.getValuleInObject(forestry[4]));
			cell5.setCellValue(BeanUtils.getValuleInObject(forestry[5]));
			cell6.setCellValue(BeanUtils.getValuleInObject(forestry[6]));
			cell7.setCellValue(BeanUtils.getValuleInObject(forestry[7]));
			
			sheet.setColumnWidth(0, 6000);
			sheet.setColumnWidth(1, 6000);
			sheet.setColumnWidth(2, 6000);
			sheet.setColumnWidth(3, 6000);
			sheet.setColumnWidth(4, 6000);
			sheet.setColumnWidth(5, 6000);
			sheet.setColumnWidth(6, 6000);
			sheet.setColumnWidth(7, 6000);
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
