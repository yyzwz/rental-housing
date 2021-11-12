package com.forestry.controller.sys;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
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
import com.forestry.model.sys.HouseOwner;
import com.forestry.model.sys.HouseOwnerQuery;
import com.forestry.model.sys.HouseType;
import com.forestry.model.sys.QueryRoom;
import com.forestry.model.sys.SysUser;
import com.forestry.service.sys.HouseOwnerService;

import core.extjs.ExtJSBaseParameter;
import core.extjs.ListView;
import core.support.BaseParameter;
import core.support.QueryResult;
import core.util.BeanUtils;
import core.util.MD5;
import core.util.PageUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
/**
 * @author 郑为中
 */
@Controller
@RequestMapping("/sys/HouseOwner")
public class HouseOwnerController extends ForestryBaseController<HouseOwner> {

	@Resource
	private HouseOwnerService houseOwnerService;

	@RequestMapping("/checkHouseOwner")
	public void checkHouseOwner(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Long[] ids) throws IOException {
		String checkOption=request.getParameter("checkOption");
		int flag = houseOwnerService.checkHouseOwner(ids,checkOption);
		if (flag>0) {
			writeJSON(response, "{success:true}");
		} else {
			writeJSON(response, "{success:false}");
		}
	}
	@RequestMapping(value = "/saveHouseOwner", method = { RequestMethod.POST, RequestMethod.GET })
	public void doSave(HouseOwner entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
		ExtJSBaseParameter parameter = ((ExtJSBaseParameter) entity);
		if (CMD_EDIT.equals(parameter.getCmd())) {
			//部分修改，会自动修改bean类的属性值未NULL
			houseOwnerService.updateHouseOwner(entity);
		} else if (CMD_NEW.equals(parameter.getCmd())) {
			houseOwnerService.persist(entity);
		}
		parameter.setCmd(CMD_EDIT);
		parameter.setSuccess(true);
		writeJSON(response, parameter);
	}
	//手机端调用实例：http://localhost:8080/forestry/sys/HouseOwner/getHouseOwnerByTwoDimensionalCode?id=41
	@RequestMapping(value = "/getHouseOwnerByTwoDimensionalCode", method = { RequestMethod.POST, RequestMethod.GET })
	public void getHouseOwnerByTwoDimensionalCode(HouseOwner entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id=(String)request.getParameter("id");
		HouseOwner HouseOwner = houseOwnerService.get(Long.valueOf(id));
		writeJSON(response, HouseOwner);
	}
	@RequestMapping(value = "/saveHouseOwnerTwoDimensionalCode", method = { RequestMethod.POST, RequestMethod.GET })
	public void saveHouseOwnerTwoDimensionalCode(HouseOwner entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id=(String)request.getParameter("id");
		//获取二维码产生的绝对路径
		String filePath=request.getServletContext().getRealPath("/")+"\\static\\img\\ORCodeImages";
		int flag = houseOwnerService.saveHouseOwnerTwoDimensionalCode(request, filePath,Long.valueOf(id));
		if (flag>0) {
			writeJSON(response, "{success:true}");
		} else {
			writeJSON(response, "{success:false}");
		}
	}

	
	@RequestMapping(value = "/getHouseOwnerList", method = { RequestMethod.POST, RequestMethod.GET })
	public void getHouseOwnerList(HttpServletRequest request, HttpServletResponse response) throws Exception {		
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
		if(sysUser.getUserGrade().equals("1级")){
			HouseOwner houseOwner = new HouseOwner();
			String houseOwnerName = request.getParameter("houseOwnerName");
			if (StringUtils.isNotBlank(houseOwnerName)) {
				houseOwnerName=URLDecoder.decode(houseOwnerName, "UTF-8");
				houseOwner.set$like_houseOwnerName(houseOwnerName);
			}
			
			String houseOwnerIdentify = request.getParameter("houseOwnerIdentify");
			if (StringUtils.isNotBlank(houseOwnerIdentify)) {
				houseOwnerIdentify=URLDecoder.decode(houseOwnerIdentify, "UTF-8");
				houseOwner.set$like_houseOwnerIdentify(houseOwnerIdentify);
			}
			String houseOwnerTel = request.getParameter("houseOwnerTel");
			if (StringUtils.isNotBlank(houseOwnerTel)) {
				houseOwnerTel=URLDecoder.decode(houseOwnerTel, "UTF-8");
				houseOwner.set$like_houseOwnerTel(houseOwnerTel);
			}
			
			houseOwner.setFirstResult(firstResult);
			houseOwner.setMaxResults(maxResults);
			Map<String, String> sortedCondition = new HashMap<String, String>();
			sortedCondition.put(sortedObject, sortedValue);
			houseOwner.setSortedConditions(sortedCondition);
			QueryResult<HouseOwner> queryResult = houseOwnerService.doPaginationQuery(houseOwner);
			ListView<HouseOwner> forestryListView = new ListView<HouseOwner>();
			forestryListView.setData(queryResult.getResultList());
			forestryListView.setTotalRecord(queryResult.getTotalCount());
			writeJSON(response, forestryListView);
		}else {//若是普通2级管理人员，可以查询区域数据
			String sql="select distinct hw.* from houseowner hw ,room r ,house h,room_tenant rt ,tenant t where t.id=rt.tenantId and rt.roomId=r.id and r.houseId=h.id  and h.departmentId="+sysUser.getDepartmentId();
			HouseOwnerQuery houseOwner = new HouseOwnerQuery();
			List<BaseParameter> arr=houseOwnerService.creatNativeSqlQuery(sql, houseOwner);
			ListView<BaseParameter> forestryListView = new ListView<BaseParameter>();;
			forestryListView.setTotalRecord(Long.valueOf(arr.size()));
			sql=sql+"  limit "+firstResult+","+(firstResult+maxResults-1)+""; 
			arr=houseOwnerService.creatNativeSqlQuery(sql, houseOwner);
			forestryListView.setData(arr);
			PageUtils pageUtils=new PageUtils();
			writeJSONQmm(response, forestryListView );
		}
		
	}
	
	@RequestMapping("/deleteHouseOwner")
	public void deleteHouseOwner(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Long[] ids) throws IOException {
		boolean flag = houseOwnerService.deleteByPK(ids);
		if (flag) {
			writeJSON(response, "{success:true}");
		} else {
			writeJSON(response, "{success:false}");
		}
	}
	
	@RequestMapping(value = "/getExportedHouseOwnerList", method = { RequestMethod.POST, RequestMethod.GET })
	public void getExportedHouseOwnerList(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Long[] ids) throws Exception {
		List<Object[]> forestryList = houseOwnerService.queryExportedHouseOwner(ids);
		//创建一个新的Excel
		HSSFWorkbook workBook = new HSSFWorkbook();
		//创建sheet页
		HSSFSheet sheet = workBook.createSheet("树木信息");
		//设置第一行为Header
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell0 = row.createCell(0);
		HSSFCell cell1 = row.createCell(1);
		HSSFCell cell2 = row.createCell(2);
		HSSFCell cell3 = row.createCell(3);
		HSSFCell cell4 = row.createCell(4);
		HSSFCell cell5 = row.createCell(5);
		HSSFCell cell6 = row.createCell(6);
		cell0.setCellValue("登录账户");
		cell1.setCellValue("姓名");
		cell2.setCellValue("登录密码");
		cell3.setCellValue("身份证号");
		cell4.setCellValue("电话");
		cell5.setCellValue("住址");
		cell6.setCellValue("描述");
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
			
			cell0.setCellValue(BeanUtils.getValuleInObject(forestry[0]));
			cell1.setCellValue(BeanUtils.getValuleInObject(forestry[1]));
			cell2.setCellValue(BeanUtils.getValuleInObject(forestry[2]));
			cell3.setCellValue(BeanUtils.getValuleInObject(forestry[3]));
			cell4.setCellValue(BeanUtils.getValuleInObject(forestry[4]));
			cell5.setCellValue(BeanUtils.getValuleInObject(forestry[5]));
			cell6.setCellValue(BeanUtils.getValuleInObject(forestry[6]));
			
			sheet.setColumnWidth(0, 6000);
			sheet.setColumnWidth(1, 6000);
			sheet.setColumnWidth(2, 6000);
			sheet.setColumnWidth(3, 6000);
			sheet.setColumnWidth(4, 6000);
			sheet.setColumnWidth(5, 6000);
			sheet.setColumnWidth(6, 6000);
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
	
	@RequestMapping("/login")
	public void login(SysUser sysUserModel, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		SysUser sysUser = new SysUser();
		String houseOwnerCode=request.getParameter("houseOwnerCode");
		String houseOwnerPassWord=request.getParameter("houseOwnerPassWord");
		String[] propNames= {"houseOwnerCode","houseOwnerPassWord"};
		Object[] propValues= {houseOwnerCode,houseOwnerPassWord};
		List<HouseOwner> houseOwners = houseOwnerService.queryByProerties(propNames, propValues);
		if(houseOwners.size()==0) {
			response.getWriter().write("failure");
			return;
		}
		HouseOwner houseOwner=(HouseOwner)houseOwners.get(0);
		sysUser.setId(houseOwner.getId());
		sysUser.setRole(Short.valueOf("3"));
		sysUser.setRealName(houseOwner.getHouseOwnerName());

		request.getSession().setAttribute(SESSION_SYS_USER, sysUser);
		result.put("result", 1);
		writeJSON(response, result);
	}
}
