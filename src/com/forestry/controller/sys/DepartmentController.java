package com.forestry.controller.sys;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.forestry.core.ForestryBaseController;
import com.forestry.model.sys.Department;
import com.forestry.model.sys.House;
import com.forestry.model.sys.TenantQuery;
import com.forestry.service.sys.DepartmentService;
import com.forestry.service.sys.HouseService;
import com.forestry.service.sys.TenantService;

import core.extjs.ExtJSBaseParameter;
import core.extjs.ListView;
import core.support.BaseParameter;
import core.support.QueryResult;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Controller
@RequestMapping("/sys/department")
public class DepartmentController extends ForestryBaseController<Department> {

	@Resource
	private DepartmentService departmentService;
	
	@Resource
	private HouseService houseService;
	
	@Resource
	private TenantService tenantService;
	
	@RequestMapping("/getDepartments")
	public void getDepartments(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Department> departmentList = departmentService.doQueryAll();
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < departmentList.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.element("ItemText", departmentList.get(i).getName());
			jsonObject.element("ItemValue", departmentList.get(i).getId());
			jsonArray.add(jsonObject);
		}
		JSONObject resultJSONObject = new JSONObject();
		resultJSONObject.element("list", jsonArray);
		writeJSON(response, resultJSONObject);
	}
	@RequestMapping(value = "/saveDepartment", method = { RequestMethod.POST, RequestMethod.GET })
	public void doSave(Department entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
		ExtJSBaseParameter parameter = ((ExtJSBaseParameter) entity);
		Department department = departmentService.getByProerties("id", entity.getId());
		if (CMD_EDIT.equals(parameter.getCmd())) {
			departmentService.update(entity);
		} else if (CMD_NEW.equals(parameter.getCmd())) {
			departmentService.persist(entity);
		}
		parameter.setCmd(CMD_EDIT);
		parameter.setSuccess(true);
		writeJSON(response, parameter);
	}

	@RequestMapping(value = "/getDepartmentList", method = { RequestMethod.POST, RequestMethod.GET })
	public void getDepartmentList(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		Department department = new Department();
		department.setFirstResult(firstResult);
		department.setMaxResults(maxResults);
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put(sortedObject, sortedValue);
		department.setSortedConditions(sortedCondition);
		QueryResult<Department> queryResult = departmentService.doPaginationQuery(department);
		ListView<Department> forestryListView = new ListView<Department>();
		forestryListView.setData(queryResult.getResultList());
		forestryListView.setTotalRecord(queryResult.getTotalCount());
		writeJSON(response, forestryListView);
	}

	@RequestMapping(value = "/getDepartmentListInMap", method = { RequestMethod.POST, RequestMethod.GET })
	public void getDepartmentListInMap(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		Department department = new Department();
		department.setFirstResult(firstResult);
		department.setMaxResults(maxResults);
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put(sortedObject, sortedValue);
		department.setSortedConditions(sortedCondition);
		QueryResult<Department> queryResult = departmentService.doPaginationQuery(department);
		
		for(int i=0;i<queryResult.getResultList().size();i++) {
			Department d=(Department)queryResult.getResultList().get(i);
			String sql="select t.* ,h.departmentName,h.houseName,h.houseAddress,h.houseMapLocation,r.roomName from tenant t,room_tenant rt ,room r ,house h where t.id=rt.tenantId and rt.roomId=r.id and r.houseID=h.id and h.departmentId="+d.getId();
			TenantQuery tenantQuery = new TenantQuery();
			List<BaseParameter> arr=departmentService.creatNativeSqlQuery(sql, tenantQuery);
			((Department)queryResult.getResultList().get(i)).setCountTenantNumber(arr.size());

		}
		ListView<Department> forestryListView = new ListView<Department>();
		forestryListView.setData(queryResult.getResultList());
		forestryListView.setTotalRecord(queryResult.getTotalCount());
		writeJSON(response, forestryListView);
	}
	@RequestMapping("/deleteDepartment")
	public void deleteSysUser(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Long[] ids) throws IOException {
		boolean flag = departmentService.deleteByPK(ids);
		if (flag) {
			writeJSON(response, "{success:true}");
		} else {
			writeJSON(response, "{success:false}");
		}
	}
	
	@RequestMapping("/getDepartmentsInZZReport")
	public void getDepartmentsInZZReport(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		Random r = new Random(1);
		List<Department> departmentList = departmentService.doQueryAll();
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < departmentList.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.element("departmentName", departmentList.get(i).getName());
			ArrayList<String> arrPropNames=new ArrayList<String>();
			ArrayList<Object> arrPropValues=new ArrayList<Object>();
			arrPropNames.add("departmentId");
			arrPropValues.add(departmentList.get(i).getId());
			
			String[] propNames= new String[arrPropNames.size()];
			Object[] propValues= new Object[arrPropNames.size()];
			for(int j=0;j<arrPropNames.size();j++) {
				propNames[j]=(String)arrPropNames.get(j);
				propValues[j]=arrPropValues.get(j);
			}
			List<House> houseList = houseService.queryByProerties(propNames, propValues);
			jsonObject.element("houseNum", houseList.size());
			//tenantService
			String sql="select distinct t.id from tenant t,room_tenant rt ,room r ,house h where t.id=rt.tenantId and rt.roomId=r.id and r.houseID=h.id ";
			sql=sql+" and h.departmentId="+departmentList.get(i).getId();
			TenantQuery tenantQuery = new TenantQuery();
			List<BaseParameter> arr=tenantService.creatNativeSqlQuery(sql, tenantQuery);
			jsonObject.element("tenantNum", arr.size());
			jsonArray.add(jsonObject);
		}
		JSONObject resultJSONObject = new JSONObject();
		resultJSONObject.element("list", jsonArray);
		writeJSON(response, resultJSONObject);
		
	}
	@RequestMapping("/getTenantInZZReport")
	public void getTenantInZZReport(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		Random r = new Random(1);
		List<Department> departmentList = departmentService.doQueryAll();
		StringBuffer departmentIds=new StringBuffer();
		StringBuffer departmentNames=new StringBuffer();
		StringBuffer modelFields=new StringBuffer();
		JSONObject resultJSONObject = new JSONObject();
		modelFields.append("yearAndMonth");
		for (int i = 0; i <  departmentList.size()/2; i++) {
			if(i!=0) {
				departmentIds.append(",");
				departmentNames.append(",");
			}
			departmentIds.append("department_"+String.valueOf(departmentList.get(i).getId()));
			departmentNames.append(departmentList.get(i).getName());
			modelFields.append(",department_"+String.valueOf(departmentList.get(i).getId()));
			resultJSONObject.element("departmentNames", departmentNames.toString());
			resultJSONObject.element("departmentIds", departmentIds.toString());
			resultJSONObject.element("modelFields", modelFields.toString());
		}
		
		JSONArray jsonDataArray = new JSONArray();
		for (int i = 1; i < 13; i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.element("yearAndMonth", i+"月份");
			for (int j = 0; j < departmentList.size()/2; j++) {
				jsonObject.element("department_"+String.valueOf(departmentList.get(j).getId()),  r.nextInt(100));
			}
			jsonDataArray.add(jsonObject);
		}
		resultJSONObject.element("data", jsonDataArray);
		JSONObject qmmJSONObject = new JSONObject();
		qmmJSONObject.element("departmentPart_1", resultJSONObject);
		
		departmentIds=new StringBuffer();
		departmentNames=new StringBuffer();
		modelFields=new StringBuffer();
		resultJSONObject = new JSONObject();
		modelFields.append("yearAndMonth");
		for (int i = departmentList.size()/2; i <  departmentList.size(); i++) {
			if(i!=0) {
				departmentIds.append(",");
				departmentNames.append(",");
			}
			departmentIds.append("department_"+String.valueOf(departmentList.get(i).getId()));
			departmentNames.append(departmentList.get(i).getName());
			modelFields.append(",department_"+String.valueOf(departmentList.get(i).getId()));
			resultJSONObject.element("departmentNames", departmentNames.toString());
			resultJSONObject.element("departmentIds", departmentIds.toString());
			resultJSONObject.element("modelFields", modelFields.toString());
		}
		
		jsonDataArray = new JSONArray();
		for (int i = 1; i < 13; i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.element("yearAndMonth", i+"月份");
			for (int j = departmentList.size()/2; j < departmentList.size(); j++) {
				jsonObject.element("department_"+String.valueOf(departmentList.get(j).getId()),  r.nextInt(100));
			}
			jsonDataArray.add(jsonObject);
		}
		resultJSONObject.element("data", jsonDataArray);
		qmmJSONObject.element("departmentPart_2", resultJSONObject);
		
		
		writeJSON(response, qmmJSONObject);
		
	}


	public void getTenantInZZReportQmm(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		Random r = new Random(1);
		List<Department> departmentList = departmentService.doQueryAll();
		StringBuffer departmentIds=new StringBuffer();
		StringBuffer departmentNames=new StringBuffer();
		StringBuffer modelFields=new StringBuffer();
		JSONObject resultJSONObject = new JSONObject();
		modelFields.append("yearAndMonth");
		for (int i = 0; i <  departmentList.size()/2; i++) {
			if(i!=0) {
				departmentIds.append(",");
				departmentNames.append(",");
			}
			departmentIds.append("department_"+String.valueOf(departmentList.get(i).getId()));
			departmentNames.append(departmentList.get(i).getName());
			modelFields.append(",department_"+String.valueOf(departmentList.get(i).getId()));
			resultJSONObject.element("departmentNames", departmentNames.toString());
			resultJSONObject.element("departmentIds", departmentIds.toString());
			resultJSONObject.element("modelFields", modelFields.toString());
		}
		
		JSONArray jsonDataArray = new JSONArray();
		for (int i = 1; i < 13; i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.element("yearAndMonth", i+"月份");
			for (int j = 0; j < departmentList.size()/2; j++) {
				jsonObject.element("department_"+String.valueOf(departmentList.get(j).getId()),  r.nextInt(100));
			}
			jsonDataArray.add(jsonObject);
		}
		resultJSONObject.element("data", jsonDataArray);
		writeJSON(response, resultJSONObject);
		
	}
}
