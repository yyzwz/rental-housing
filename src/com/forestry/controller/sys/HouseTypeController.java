package com.forestry.controller.sys;

import java.io.IOException;
import java.io.OutputStream;
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
import com.forestry.model.sys.ForestryType;
import com.forestry.model.sys.HouseType;
import com.forestry.model.sys.param.HouseTypeParameter;
import com.forestry.service.sys.HouseTypeService;

import core.extjs.ExtJSBaseParameter;
import core.extjs.ListView;
import core.support.QueryResult;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author 郑为中
 */
@Controller
@RequestMapping("/sys/houseType")
public class HouseTypeController extends ForestryBaseController<HouseType> {

	@Resource
	private HouseTypeService houseTypeService;

	@RequestMapping(value = "/saveHouseType", method = { RequestMethod.POST, RequestMethod.GET })
	public void doSave(HouseType entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
		ExtJSBaseParameter parameter = ((ExtJSBaseParameter) entity);
		HouseType HouseType = houseTypeService.getByProerties("id", entity.getId());
		if (CMD_EDIT.equals(parameter.getCmd())) {
			houseTypeService.update(entity);
		} else if (CMD_NEW.equals(parameter.getCmd())) {
			houseTypeService.persist(entity);
		}
		parameter.setCmd(CMD_EDIT);
		parameter.setSuccess(true);
		writeJSON(response, parameter);
	}

	@RequestMapping("/getHouseType")
	public void getHouseType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<HouseType> houseTypeList = houseTypeService.doQueryAll();
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < houseTypeList.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.element("ItemText", houseTypeList.get(i).getHouseTypeName());
			jsonObject.element("ItemValue", houseTypeList.get(i).getId());
			jsonArray.add(jsonObject);
		}
		JSONObject resultJSONObject = new JSONObject();
		resultJSONObject.element("list", jsonArray);
		writeJSON(response, resultJSONObject);
	}

	@RequestMapping(value = "/getHouseTypeList", method = { RequestMethod.POST, RequestMethod.GET })
	public void getHouseTypeList(HttpServletRequest request, HttpServletResponse response) throws Exception {		
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
		HouseType HouseType = new HouseType();
		HouseType.setFirstResult(firstResult);
		HouseType.setMaxResults(maxResults);
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put(sortedObject, sortedValue);
		HouseType.setSortedConditions(sortedCondition);
		QueryResult<HouseType> queryResult = houseTypeService.doPaginationQuery(HouseType);
		ListView<HouseType> forestryListView = new ListView<HouseType>();
		forestryListView.setData(queryResult.getResultList());
		forestryListView.setTotalRecord(queryResult.getTotalCount());
		writeJSON(response, forestryListView);
	}

	@RequestMapping("/deleteHouseType")
	public void deleteHouseType(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Long[] ids) throws IOException {
		boolean flag = houseTypeService.deleteByPK(ids);
		if (flag) {
			writeJSON(response, "{success:true}");
		} else {
			writeJSON(response, "{success:false}");
		}
	}
	@RequestMapping(value = "/getExportedHouseTypeList", method = { RequestMethod.POST, RequestMethod.GET })
	public void exportForestry(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Long[] ids) throws Exception {
		List<Object[]> forestryList = houseTypeService.queryExportedHouseType(ids);
		//创建一个新的Excel
		HSSFWorkbook workBook = new HSSFWorkbook();
		//创建sheet页
		HSSFSheet sheet = workBook.createSheet("房屋类型信息");
		//设置第一行为Header
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell0 = row.createCell(0);
		HSSFCell cell1 = row.createCell(1);

		cell0.setCellValue("房屋类型名称");
		cell1.setCellValue("备注信息");

		for (int i = 0; i < forestryList.size(); i++) {
			Object[] forestry = forestryList.get(i);
			row = sheet.createRow(i + 1);
			cell0 = row.createCell(0);
			cell1 = row.createCell(1);

			cell0.setCellValue(forestry[0].toString());
			cell1.setCellValue(forestry[1].toString());

			sheet.setColumnWidth(0, 6000);
			sheet.setColumnWidth(1, 6000);

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
