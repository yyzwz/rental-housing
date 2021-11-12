package com.forestry.controller.sys;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.forestry.core.ForestryBaseController;
import com.forestry.model.sys.ForestryType;
import com.forestry.model.sys.QueryRoom;
import com.forestry.model.sys.Room;
import com.forestry.model.sys.SysUser;
import com.forestry.model.sys.Room;
import com.forestry.model.sys.param.RoomParameter;
import com.forestry.service.sys.DepartmentService;
import com.forestry.service.sys.RoomService;

import core.extjs.ExtJSBaseParameter;
import core.extjs.ListView;
import core.support.BaseParameter;
import core.support.QueryResult;
import core.util.BeanUtils;
import core.util.PageUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author 郑为中
 */
@Controller
@RequestMapping("/sys/room")
public class RoomController extends ForestryBaseController<Room> {

	@Resource
	private RoomService roomService;
	//@Resource
	//private DepartmentService departmentService;
	
	@RequestMapping(value = "/saveRoom", method = { RequestMethod.POST, RequestMethod.GET })
	public void doSave(Room entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
		ExtJSBaseParameter parameter = ((ExtJSBaseParameter) entity);
		entity.setHouseID(Long.valueOf((String)request.getParameter("houseId")));
		if (CMD_EDIT.equals(parameter.getCmd())) {
			roomService.updateRoom(entity);
		} else if (CMD_NEW.equals(parameter.getCmd())) {
			roomService.persist(entity);
		}
		parameter.setCmd(CMD_EDIT);
		parameter.setSuccess(true);
		writeJSON(response, parameter);
	}

	@RequestMapping("/getRoom")
	public void getRoom(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<String> arrPropNames=new ArrayList<String>();
		ArrayList<Object> arrPropValues=new ArrayList<Object>();
		String houseId=request.getParameter("houseId");
		if(houseId!=null && !houseId.equals("")){//若是普通1级管理人员，可以查询全部数据
			arrPropNames.add("houseId");
			arrPropValues.add(Long.valueOf(houseId));
		}
		String[] propNames= new String[arrPropNames.size()];
		Object[] propValues= new Object[arrPropNames.size()];
		for(int i=0;i<arrPropNames.size();i++) {
			propNames[i]=(String)arrPropNames.get(i);
			propValues[i]=arrPropValues.get(i);
		}
		List<Room> roomList = roomService.queryByProerties(propNames, propValues);
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < roomList.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.element("ItemText", roomList.get(i).getRoomName());
			jsonObject.element("ItemValue", roomList.get(i).getId());
			jsonArray.add(jsonObject);
		}
		JSONObject resultJSONObject = new JSONObject();
		resultJSONObject.element("list", jsonArray);
		writeJSON(response, resultJSONObject);
	}

	@RequestMapping(value = "/getRoomList", method = { RequestMethod.POST, RequestMethod.GET })
	public void getRoomList(HttpServletRequest request, HttpServletResponse response) throws Exception {		
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
		String sql="select r.* ,h.houseOwnerId,h.houseOwnerName,h.departmentName as queryDepartmentName from house h ,room r where  r.houseId=h.id";
		if(sysUser.getUserGrade().equals("2级")){//若是普通2级管理人员，只能查询本区域数据
			sql=sql+" and h.departmentId="+sysUser.getDepartmentId();
		}else
			if(sysUser.getUserGrade().equals("3级")){//若是普通1级管理人员，可以查询全部数据
				sql=sql+" and h.houseOwnerId="+sysUser.getId();
			}
		String houseName = request.getParameter("houseName");
		if (StringUtils.isNotBlank(houseName)) {
			houseName=URLDecoder.decode(houseName, "UTF-8");
			sql=sql+" and h.houseName like'%"+houseName+"%'";
		}
		
		String departmentId = request.getParameter("departmentId");
		if (StringUtils.isNotBlank(departmentId)) {
			departmentId=URLDecoder.decode(departmentId, "UTF-8");
			sql=sql+" and h.departmentId="+departmentId;
		}
		
		QueryRoom queryRoom = new QueryRoom();
		List<BaseParameter> arr=roomService.creatNativeSqlQuery(sql, queryRoom);
		ListView<BaseParameter> forestryListView = new ListView<BaseParameter>();;
		forestryListView.setTotalRecord(Long.valueOf(arr.size()));
		sql=sql+"  limit "+firstResult+","+(firstResult+maxResults-1)+""; 
		arr=roomService.creatNativeSqlQuery(sql, queryRoom);
		forestryListView.setData(arr);
		PageUtils pageUtils=new PageUtils();
		writeJSONQmm(response, forestryListView );
		
	}

	@RequestMapping("/checkRoom")
	public void checkRoom(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Long[] ids) throws IOException {
		SysUser sysUser=(SysUser)request.getSession().getAttribute(SESSION_SYS_USER);
		String checkOption=request.getParameter("checkOption");
		int flag = roomService.check(ids,checkOption,sysUser);
		if (flag>0) {
			writeJSON(response, "{success:true}");
		} else {
			writeJSON(response, "{success:false}");
		}
	}
	@RequestMapping("/deleteRoom")
	public void deleteRoom(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Long[] ids) throws IOException {
		boolean flag = roomService.deleteByPK(ids);
		if (flag) {
			writeJSON(response, "{success:true}");
		} else {
			writeJSON(response, "{success:false}");
		}
	}
	@RequestMapping(value = "/getExportedRoomList", method = { RequestMethod.POST, RequestMethod.GET })
	public void exportForestry(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Long[] ids) throws Exception {
		List<Object[]> forestryList = roomService.queryExportedRoom(ids);
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

		
		cell0.setCellValue("所在区域");
		cell1.setCellValue("房屋名称");
		cell2.setCellValue("房间名称");
		cell3.setCellValue("房间面积");

		
		for (int i = 0; i < forestryList.size(); i++) {
			Object[] forestry = forestryList.get(i);
			row = sheet.createRow(i + 1);
			cell0 = row.createCell(0);
			cell1 = row.createCell(1);
			cell2 = row.createCell(2);
			cell3 = row.createCell(3);

			cell0.setCellValue(BeanUtils.getValuleInObject(forestry[0]));
			cell1.setCellValue(BeanUtils.getValuleInObject(forestry[1]));
			cell2.setCellValue(BeanUtils.getValuleInObject(forestry[2]));
			cell3.setCellValue(BeanUtils.getValuleInObject(forestry[3]));


			sheet.setColumnWidth(0, 6000);
			sheet.setColumnWidth(1, 6000);
			sheet.setColumnWidth(2, 6000);
			sheet.setColumnWidth(3, 6000);


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
