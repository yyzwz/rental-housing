package com.forestry.controller.sys;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forestry.core.Constant;
import com.forestry.core.ForestryBaseController;
import com.forestry.model.sys.HouseOwner;
import com.forestry.model.sys.RoleAuthority;
import com.forestry.model.sys.SysUser;
import com.forestry.service.sys.HouseOwnerService;
import com.forestry.service.sys.RoleAuthorityService;
import com.forestry.service.sys.SysUserService;
import core.extjs.ExtJSBaseParameter;
import core.extjs.ListView;
import core.support.Item;
import core.support.QueryResult;
import core.util.BeanUtils;
import core.util.MD5;
import core.web.SystemCache;

/**
 * @author 郑为中
 */
@Controller
@RequestMapping("/sys/sysuser")
public class SysUserController extends ForestryBaseController<SysUser> implements Constant {

	@Resource
	private SysUserService sysUserService;
	@Resource
	private HouseOwnerService houseOwnerService;
	@Resource
	private RoleAuthorityService roleAuthorityService;
	
	@RequestMapping("/login")
	public void login(SysUser sysUserModel, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		SysUser sysUser = sysUserService.getByProerties("userName", sysUserModel.getUserName());
		if (sysUser == null || sysUser.getRole() == 0) { // 用户名有误或已被禁用
			result.put("result", -1);
			writeJSON(response, result);
			return;
		}
		if (!sysUser.getPassword().equals(MD5.crypt(sysUserModel.getPassword()))) { // 密码错误
			result.put("result", -2);
			writeJSON(response, result);
			return;
		}

		sysUser.setLastLoginTime(new Date());
		sysUserService.update(sysUser);
		request.getSession().setAttribute(SESSION_SYS_USER, sysUser);
		result.put("result", 1);
		writeJSON(response, result);
	}

	@RequestMapping("/home")
	public String home(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (request.getSession().getAttribute(SESSION_SYS_USER) == null) {
			return "";
		} else {
			return "back/main";
		}
	}
	@RequestMapping("/loginHouseOwner")
	public void loginHouseOwner(SysUser sysUserModel, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		SysUser sysUser = sysUserService.getByProerties("userName", sysUserModel.getUserName());
		if (sysUser == null || sysUser.getRole() == 0) { // 用户名有误或已被禁用
			result.put("result", -1);
			writeJSON(response, result);
			return;
		}
		if (!sysUser.getPassword().equals(MD5.crypt(sysUserModel.getPassword()))) { // 密码错误
			result.put("result", -2);
			writeJSON(response, result);
			return;
		}

		sysUser.setLastLoginTime(new Date());
		sysUserService.update(sysUser);
		request.getSession().setAttribute(SESSION_SYS_USER, sysUser);
		result.put("result", 1);
		writeJSON(response, result);
	}
	@RequestMapping("/houseOwner")
	public String houseOwnerHome(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (request.getSession().getAttribute(SESSION_SYS_USER) == null) {
			return "";
		} else {
			return "houseOwner/houseOwner";
		}
	
		
	}
	@RequestMapping("/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.getSession().removeAttribute(SESSION_SYS_USER);
		response.sendRedirect("/forestry/login.jsp");
	}

	@RequestMapping("/resetPassword")
	public void resetPassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String userName = request.getParameter("userName");
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		Map<String, Object> result = new HashMap<String, Object>();
		SysUser sysUser = sysUserService.getByProerties("userName", userName);
		if (!sysUser.getPassword().equals(MD5.crypt(oldPassword))) {
			result.put("result", -2);
			writeJSON(response, result);
			return;
		}
		result.put("result", 1);
		sysUser.setPassword(MD5.crypt(newPassword));
		sysUserService.update(sysUser);
		writeJSON(response, result);
	}

	@RequestMapping("/externalVerifySysUser")
	public void externalVerifySysUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		SysUser sysUser = sysUserService.getByProerties(new String[] { "userName", "password" }, new Object[] { username, MD5.crypt(password) });
		if (null == sysUser) {
			writeJSON(response, "{success:false}");
		} else {
			writeJSON(response, "{success:true}");
		}
	}

	@RequestMapping("/getRoleName")
	public void getRoleName(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONArray jsonArray = new JSONArray();
		for (Map.Entry<String, Item> roleMap : SystemCache.DICTIONARY.get("SYSUSER_ROLE").getItems().entrySet()) {
			Item item = roleMap.getValue();
			JSONObject jsonObject = new JSONObject();
			jsonObject.element("ItemText", item.getValue());
			jsonObject.element("ItemValue", item.getKey());
			jsonArray.add(jsonObject);
		}
		JSONObject resultJSONObject = new JSONObject();
		resultJSONObject.element("list", jsonArray);
		writeJSON(response, resultJSONObject);
	}

	@Override
	@RequestMapping(value = "/saveSysUser", method = { RequestMethod.POST, RequestMethod.GET })
	public void doSave(SysUser entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
		ExtJSBaseParameter parameter = ((ExtJSBaseParameter) entity);
		SysUser checkuserName = sysUserService.getByProerties("userName", entity.getUserName());
		if (null != checkuserName && null == entity.getId()) {
			parameter.setSuccess(false);
		} else {
			if (CMD_EDIT.equals(parameter.getCmd())) {
				entity.setLastLoginTime(checkuserName.getLastLoginTime());
				sysUserService.update(entity);
			} else if (CMD_NEW.equals(parameter.getCmd())) {
				entity.setPassword(MD5.crypt(entity.getPassword()));
				sysUserService.persist(entity);
			}
			parameter.setCmd(CMD_EDIT);
			parameter.setSuccess(true);
		}
		writeJSON(response, parameter);
	}

	@RequestMapping(value = "/getSysUserList", method = { RequestMethod.POST, RequestMethod.GET })
	public void getSysUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		SysUser sysUser = new SysUser();
		String realName = request.getParameter("real_name");
		if (StringUtils.isNotBlank(realName)) {
			realName=URLDecoder.decode(realName);
			sysUser.set$like_realName(realName);
		}
		String departmentId = request.getParameter("departmentId");
		if (StringUtils.isNotBlank(departmentId)&&(!departmentId.equals("-1"))) {
			sysUser.set$eq_departmentId(Long.valueOf(departmentId));
		}
		String role = request.getParameter("role");
		if (StringUtils.isNotBlank(role)) {
			sysUser.set$eq_role(Short.valueOf(role));
		}
		sysUser.setFirstResult(firstResult);
		sysUser.setMaxResults(maxResults);
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put(sortedObject, sortedValue);
		sysUser.setSortedConditions(sortedCondition);
		QueryResult<SysUser> queryResult = sysUserService.doPaginationQuery(sysUser);
		List<SysUser> forestryList = sysUserService.getSysUserList(queryResult.getResultList());
		ListView<SysUser> forestryListView = new ListView<SysUser>();
		forestryListView.setData(forestryList);
		forestryListView.setTotalRecord(queryResult.getTotalCount());
		writeJSON(response, forestryListView);
	}

	@RequestMapping("/deleteSysUser")
	public void deleteSysUser(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Long[] ids) throws IOException {
		if (Arrays.asList(ids).contains(Long.valueOf("1"))) {
			writeJSON(response, "{success:false,msg:'删除项包含超级管理员，超级管理员不能删除！'}");
		} else {
			boolean flag = sysUserService.deleteByPK(ids);
			if (flag) {
				writeJSON(response, "{success:true}");
			} else {
				writeJSON(response, "{success:false}");
			}
		}
	}

	@RequestMapping(value = "/getRoleNameList")
	public void getRoleNameList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List roleNameList = new ArrayList();
		for (Map.Entry<String, Item> roleMap : SystemCache.DICTIONARY.get("SYSUSER_ROLE").getItems().entrySet()) {
			Item item = roleMap.getValue();
			SysUser sysUser = new SysUser();
			sysUser.setRole(Short.valueOf(item.getKey()));
			sysUser.setRoleName(item.getValue());
			roleNameList.add(sysUser);
		}
		ListView roleNameListView = new ListView();
		roleNameListView.setData(roleNameList);
		roleNameListView.setTotalRecord(Long.valueOf(roleNameList.size()));
		writeJSON(response, roleNameListView);
	}
	@RequestMapping("/downloadImportedFile")
	public ResponseEntity<byte[]> downloadImportedFile() throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", "template.xlsx");
		File filePath = new File(getClass().getClassLoader().getResource("/").getPath().replace("/WEB-INF/classes/", "/static/download/attachment/" + "template.xlsx"));
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(filePath), headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/exportSysUser", method = { RequestMethod.POST, RequestMethod.GET })
	public void exportForestry(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Long[] ids) throws Exception {
		List<Object[]> forestryList = sysUserService.queryExportedSysUser(ids);
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
		cell0.setCellValue("用户名");
		cell1.setCellValue("姓名");
		cell2.setCellValue("手机号");
		cell3.setCellValue("邮箱");
		cell4.setCellValue("最后登录时间");
		cell5.setCellValue("角色");
		for (int i = 0; i < forestryList.size(); i++) {
			Object[] forestry = forestryList.get(i);
			row = sheet.createRow(i + 1);
			cell0 = row.createCell(0);
			cell1 = row.createCell(1);
			cell2 = row.createCell(2);
			cell3 = row.createCell(3);
			cell4 = row.createCell(4);
			cell5 = row.createCell(5);
			cell0.setCellValue(BeanUtils.getValuleInObject(forestry[0]));
			cell1.setCellValue(BeanUtils.getValuleInObject(forestry[1]));
			cell2.setCellValue(BeanUtils.getValuleInObject(forestry[2]));
			cell3.setCellValue(BeanUtils.getValuleInObject(forestry[3]));
			cell4.setCellValue(BeanUtils.getValuleInObject(forestry[4]));
			cell5.setCellValue(BeanUtils.getValuleInObject(forestry[5]).equals("1")?"超级管理员":"区域管理员");
			sheet.setColumnWidth(0, 6000);
			sheet.setColumnWidth(1, 6000);
			sheet.setColumnWidth(2, 6000);
			sheet.setColumnWidth(3, 6000);
			sheet.setColumnWidth(4, 6000);
			sheet.setColumnWidth(5, 6000);
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

	
	@RequestMapping("/login2")
	public void houseOwnerLogin(SysUser sysUserModel, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		SysUser sysUser = new SysUser();
		String houseOwnerCode=sysUserModel.getUserName();
		String houseOwnerPassWord=sysUserModel.getPassword();
		String[] propNames= {"houseOwnerCode","houseOwnerPassWord"};
		Object[] propValues= {houseOwnerCode,houseOwnerPassWord};
		List<HouseOwner> houseOwners = houseOwnerService.queryByProerties(propNames, propValues);
		if(houseOwners.size()==0) {
			result.put("result", -2);
			writeJSON(response, result);
			return;
		}
		HouseOwner houseOwner=(HouseOwner)houseOwners.get(0);
		sysUser.setId(houseOwner.getId());
		sysUser.setRole(Short.valueOf("3"));
		sysUser.setRealName(houseOwner.getHouseOwnerName());
		sysUser.setUserName(houseOwner.getHouseOwnerName());
		sysUser.setUserGrade("3级");

		request.getSession().setAttribute(SESSION_SYS_USER, sysUser);
		result.put("result", 1);
		writeJSON(response, result);
	}
	
	@RequestMapping("/getRightInRole")
	public void getRightInRole(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String authority_id=request.getParameter("authority_id");
		boolean isEixted=false;
		SysUser sysUser=(SysUser)request.getSession().getAttribute(SESSION_SYS_USER);
		String[] propNames= {"role"};
		Object[] propValues= {sysUser.getRole()};
		List<RoleAuthority> resultList=roleAuthorityService.queryByProerties(propNames, propValues);
		for(int i=0;i<resultList.size();i++) {
			RoleAuthority roleAuthority=(RoleAuthority)resultList.get(i);
			if(roleAuthority.getAuthorityId().equals(authority_id))
				isEixted=true;
		}
		if(isEixted)
			response.getWriter().write("true");
		else
			response.getWriter().write("false");
	}
	
}
