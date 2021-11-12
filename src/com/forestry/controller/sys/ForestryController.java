package com.forestry.controller.sys;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.NumberFormat;
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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.RequestContext;

import com.forestry.core.ForestryBaseController;
import com.forestry.model.sys.Forestry;
import com.forestry.model.sys.ForestryType;
import com.forestry.service.sys.ForestryService;
import com.forestry.service.sys.ForestryTypeService;

import core.extjs.ExtJSBaseParameter;
import core.extjs.ListView;
import core.support.QueryResult;
import core.util.ForestryUtils;

/**
 * @author 郑为中
 */
@Controller
@RequestMapping("/sys/forestry")
public class ForestryController extends ForestryBaseController<Forestry> {

	private static final Logger log = Logger.getLogger(ForestryController.class);
	private static SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");

	@Resource
	private ForestryService forestryService;
	@Resource
	private ForestryTypeService forestryTypeService;

	@RequestMapping(value = "/getForestry", method = { RequestMethod.POST, RequestMethod.GET })
	public void getForestry(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		Forestry forestry = new Forestry();
		String epcId = request.getParameter("epcId");
		if (StringUtils.isNotBlank(epcId)) {
			forestry.set$like_epcId(epcId);
		}
		String name = request.getParameter("name");
		if (StringUtils.isNotBlank(name)) {
			forestry.set$like_name(name);
		}
		String forestryTypeId = request.getParameter("forestryTypeId");
		if (StringUtils.isNotBlank(forestryTypeId)) {
			forestry.set$eq_typeId(Long.valueOf(forestryTypeId));
		}
		forestry.setFirstResult(firstResult);
		forestry.setMaxResults(maxResults);
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put(sortedObject, sortedValue);
		forestry.setSortedConditions(sortedCondition);
		QueryResult<Forestry> queryResult = forestryService.doPaginationQuery(forestry);
		List<Forestry> forestryList = forestryService.getForestryList(queryResult.getResultList());
		ListView<Forestry> forestryListView = new ListView<Forestry>();
		forestryListView.setData(forestryList);
		forestryListView.setTotalRecord(queryResult.getTotalCount());
		writeJSON(response, forestryListView);
	}

	@RequestMapping("/deleteForestry")
	public void deleteForestry(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Long[] ids) throws IOException {
		boolean flag = forestryService.deleteByPK(ids);
		if (flag) {
			writeJSON(response, "{success:true}");
		} else {
			writeJSON(response, "{success:false}");
		}
	}

	@RequestMapping("/getForestryTypeName")
	public void getForestryTypeName(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<ForestryType> forestryTypeList = forestryTypeService.doQueryAll();
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < forestryTypeList.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.element("ItemText", forestryTypeList.get(i).getName());
			jsonObject.element("ItemValue", forestryTypeList.get(i).getId());
			jsonArray.add(jsonObject);
		}
		JSONObject resultJSONObject = new JSONObject();
		resultJSONObject.element("list", jsonArray);
		writeJSON(response, resultJSONObject);
	}

	@Override
	@RequestMapping(value = "/saveForestry", method = { RequestMethod.POST, RequestMethod.GET })
	public void doSave(Forestry entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
		ExtJSBaseParameter parameter = ((ExtJSBaseParameter) entity);
		Forestry checkEpcId = forestryService.getByProerties("epcId", entity.getEpcId());
		if (null != checkEpcId && null == entity.getId()) {
			parameter.setSuccess(false);
		} else {
			ForestryType forestryType = forestryTypeService.get(entity.getForestryTypeId());
			entity.setForestryType(forestryType);
			if (CMD_EDIT.equals(parameter.getCmd())) {
				forestryService.update(entity);
			} else if (CMD_NEW.equals(parameter.getCmd())) {
				forestryService.persist(entity);
			}
			parameter.setCmd(CMD_EDIT);
			parameter.setSuccess(true);
		}
		writeJSON(response, parameter);
	}

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

	@RequestMapping(value = "/importForestryFile", method = RequestMethod.POST)
	public void importForestryFile(@RequestParam(value = "importedFile", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
		RequestContext requestContext = new RequestContext(request);
		JSONObject json = new JSONObject();
		if (!file.isEmpty()) {
			if (file.getSize() > 2097152) {
				json.put("msg", requestContext.getMessage("g_fileTooLarge"));
			} else {
				try {
					String originalFilename = file.getOriginalFilename();
					String fileName = sdf.format(new Date()) + ForestryUtils.getRandomString(3) + originalFilename.substring(originalFilename.lastIndexOf("."));
					File filePath = new File(getClass().getClassLoader().getResource("/").getPath().replace("/WEB-INF/classes/", "/static/download/attachment/" + DateFormatUtils.format(new Date(), "yyyyMM")));
					if (!filePath.exists()) {
						filePath.mkdirs();
					}
					String serverFile = filePath.getAbsolutePath() + "\\" + fileName;
					file.transferTo(new File(serverFile));

					String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
					if (!fileType.equalsIgnoreCase("xls") && !fileType.equalsIgnoreCase("xlsx")) {
						json.put("success", false);
						json.put("msg", requestContext.getMessage("g_notValidExcel"));
						writeJSON(response, json.toString());
						return;
					}
					int count = 0;
					StringBuilder stringBuilder = new StringBuilder();
					InputStream xls = new FileInputStream(serverFile);
					Workbook wb = null;
					Sheet sheet = null;
					Row currentRow = null;
					Row headRow = null;
					Cell currentCell = null;
					if (fileType.equals("xls")) {
						wb = new HSSFWorkbook(xls);
					} else if (fileType.equals("xlsx")) {
						wb = new XSSFWorkbook(xls);
					}
					sheet = wb.getSheetAt(0);// excel中至少会存在一个sheet页
					int rowNum = sheet.getPhysicalNumberOfRows();// 物理有效行数
					Object[] rowValues = null;// excel中一行树木信息
					List<Object[]> models = new ArrayList<Object[]>();// excel中全部树木信息
					if (rowNum > 1) {
						headRow = sheet.getRow(0);
						columns: for (int i = 1; i < rowNum; i++) {
							currentRow = sheet.getRow(i);
							if (currentRow != null) {
								rowValues = new Object[5];
								// int cellNum = currentRow.getLastCellNum();// 总单元格数目
								for (short j = 0; j < 5; j++) {
									try {
										currentCell = currentRow.getCell(j);
										Object obj = null;
										if (currentCell == null) {
											obj = "";
										} else {
											switch (currentCell.getCellType()) {
												case Cell.CELL_TYPE_BLANK:
													obj = "";
													break;
												case Cell.CELL_TYPE_STRING:
													obj = currentCell.getRichStringCellValue();
													break;
												case Cell.CELL_TYPE_NUMERIC:
													if (HSSFDateUtil.isCellDateFormatted(currentCell)) {
														double d = currentCell.getNumericCellValue();
														Date date = HSSFDateUtil.getJavaDate(d);
														obj = sdfDate.format(date);
													} else {
														NumberFormat nf = NumberFormat.getInstance();
														nf.setGroupingUsed(false);//true时的格式：1,234,567,890
														obj = nf.format(currentCell.getNumericCellValue());
													}
													break;
												default:
													obj = "";
													break;
											}
										}
										String cellVal = obj.toString();
										rowValues[j] = cellVal;
									} catch (IllegalStateException e) {
										rowValues = null;
										stringBuilder.append("第" + i + "行," + headRow.getCell(j).getRichStringCellValue() + "列输入了非法值，未导入成功！");
										continue columns;
									} catch (NullPointerException e) {
										rowValues = null;
										stringBuilder.append("第" + i + "行," + headRow.getCell(j).getRichStringCellValue() + "列输入了空值，未导入成功!");
										continue columns;
									} catch (Exception e) {
										rowValues = null;
										stringBuilder.append(e.getMessage());
										continue columns;
									}
								}
								if (rowValues != null) {
									models.add(rowValues);
								}
							}
						}
					} else if (rowNum <= 1 && rowNum > 0) {// 表示模版中只存在头部信息
						json.put("success", false);
						json.put("msg", "Excel表格中没有需要导入 的内容！");
						writeJSON(response, json.toString());
						return;
					} else if (rowNum <= 0) {// 表示这是一个空sheet页
						json.put("success", false);
						json.put("msg", "所导入文件格式不正确，请下载模板！");
						writeJSON(response, json.toString());
						return;
					}
					List<Forestry> list = objectToForestry(models);// Object-->Forestry
					for (int i = 0; i < list.size(); i++) {
						if (StringUtils.isBlank(list.get(i).getEpcId()) || StringUtils.isBlank(list.get(i).getName())) {
							stringBuilder.append("第" + (i + 1) + "行记录的必填项有空值，导入失败。");
							continue;
						}
						Forestry checkForestryEpcId = forestryService.getByProerties("epcId", list.get(i).getEpcId());
						if (checkForestryEpcId != null) {
							stringBuilder.append("第" + (i + 1) + "行记录的epc编码已存在，导入失败。");
							continue;
						}
						if (list.get(i).getForestryType() == null) {
							stringBuilder.append("第" + (i + 1) + "行记录的种类为空或不存在，导入失败。");
							continue;
						}
						forestryService.persist(list.get(i));
						count++;
					}

					json.put("success", true);
					json.put("msg", count + "条记录导入完成。" + stringBuilder.toString());
				} catch (Exception e) {
					e.printStackTrace();
					json.put("success", false);
					json.put("msg", requestContext.getMessage("g_operateFailure"));
					writeJSON(response, json.toString());
				}
			}
		} else {
			json.put("success", false);
			json.put("msg", requestContext.getMessage("g_uploadNotExists"));
		}
		writeJSON(response, json.toString());
	}

	private List<Forestry> objectToForestry(List<Object[]> models) {
		List<Forestry> forestryList = new ArrayList<Forestry>();
		Forestry forestry = null;
		for (int i = 0; i < models.size(); i++) {
			try {
				forestry = new Forestry();
				forestry.setEpcId(models.get(i)[0].toString());
				forestry.setName(models.get(i)[1].toString());
				if (StringUtils.isBlank(models.get(i)[2].toString())) {
					forestry.setPlantTime(null);
				} else {
					forestry.setPlantTime(sdfDate.parse(models.get(i)[2].toString()));
				}
				if (StringUtils.isBlank(models.get(i)[3].toString())) {
					forestry.setEntryTime(null);
				} else {
					forestry.setEntryTime(sdfDate.parse(models.get(i)[3].toString()));
				}
				ForestryType forestryType = forestryTypeService.getByProerties("name", models.get(i)[4].toString());
				forestry.setForestryType(forestryType);
				forestryList.add(forestry);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		return forestryList;
	}

	@RequestMapping("/downloadImportedFile")
	public ResponseEntity<byte[]> downloadImportedFile() throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", "template.xlsx");
		File filePath = new File(getClass().getClassLoader().getResource("/").getPath().replace("/WEB-INF/classes/", "/static/download/attachment/" + "template.xlsx"));
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(filePath), headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/exportForestry", method = { RequestMethod.POST, RequestMethod.GET })
	public void exportForestry(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Long[] ids) throws Exception {
		List<Object[]> forestryList = forestryService.queryExportedForestry(ids);
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
		cell0.setCellValue("epc编码");
		cell1.setCellValue("名称");
		cell2.setCellValue("种植时间");
		cell3.setCellValue("入园时间");
		cell4.setCellValue("所属种类名称");
		for (int i = 0; i < forestryList.size(); i++) {
			Object[] forestry = forestryList.get(i);
			row = sheet.createRow(i + 1);
			cell0 = row.createCell(0);
			cell1 = row.createCell(1);
			cell2 = row.createCell(2);
			cell3 = row.createCell(3);
			cell4 = row.createCell(4);
			cell0.setCellValue(forestry[0].toString());
			cell1.setCellValue(forestry[1].toString());
			cell2.setCellValue(forestry[2].toString());
			cell3.setCellValue(forestry[3].toString());
			cell4.setCellValue(forestry[4].toString());
			sheet.setColumnWidth(0, 6000);
			sheet.setColumnWidth(1, 6000);
			sheet.setColumnWidth(2, 6000);
			sheet.setColumnWidth(3, 6000);
			sheet.setColumnWidth(4, 6000);
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
