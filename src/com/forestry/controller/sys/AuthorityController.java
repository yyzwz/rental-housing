package com.forestry.controller.sys;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.forestry.core.ForestryBaseController;
import com.forestry.model.sys.Authority;
import com.forestry.model.sys.RoleAuthority;
import com.forestry.service.sys.AuthorityService;
import com.forestry.service.sys.RoleAuthorityService;

import core.extjs.ExtJSBaseParameter;
import core.extjs.ListView;
import core.support.QueryResult;

/**
 * @author 郑为中
 */
@Controller
@RequestMapping("/sys/authority")
public class AuthorityController extends ForestryBaseController<Authority> {

	@Resource
	private AuthorityService authorityService;
	@Resource
	private RoleAuthorityService roleAuthorityService;

	@RequestMapping("/getAuthority")
	public void getAuthority(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Short role = Short.valueOf(request.getParameter("globalRoleId"));
		List<Authority> mainMenuList = authorityService.queryByParentIdAndRole(role);
		List resultList = new ArrayList();
		for (int i = 0; i < mainMenuList.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.element("id", mainMenuList.get(i).getId());
			jsonObject.element("sortOrder", mainMenuList.get(i).getSortOrder());
			jsonObject.element("menuCode", mainMenuList.get(i).getMenuCode());
			jsonObject.element("text", mainMenuList.get(i).getMenuName());
			jsonObject.element("menuConfig", mainMenuList.get(i).getMenuConfig());
			jsonObject.element("buttons", mainMenuList.get(i).getButtons());
			jsonObject.element("expanded", mainMenuList.get(i).getExpanded());
			jsonObject.element("checked", mainMenuList.get(i).getChecked());
			jsonObject.element("leaf", mainMenuList.get(i).getLeaf());
			jsonObject.element("url", mainMenuList.get(i).getUrl());
			jsonObject.element("iconCls", mainMenuList.get(i).getIconCls());
			jsonObject.element("descInfo", mainMenuList.get(i).getDescInfo());
			
			JSONArray jsonArray = new JSONArray();
			List<Authority> childrenMenuList = authorityService.queryChildrenByParentIdAndRole(mainMenuList.get(i).getId(), role);
			for (int j = 0; j < childrenMenuList.size(); j++) {
				JSONObject childrenJsonObject = new JSONObject();

				String buttons = authorityService.querySurfaceAuthorityList(roleAuthorityService.queryByProerties("role", role), childrenMenuList.get(j).getId(), childrenMenuList.get(j).getButtons());

				childrenJsonObject.element("id", childrenMenuList.get(j).getId());
				childrenJsonObject.element("sortOrder", childrenMenuList.get(j).getSortOrder());
				childrenJsonObject.element("menuCode", childrenMenuList.get(j).getMenuCode());
				childrenJsonObject.element("text", childrenMenuList.get(j).getMenuName());
				childrenJsonObject.element("menuConfig", childrenMenuList.get(j).getMenuConfig());
				childrenJsonObject.element("buttons", buttons);
				childrenJsonObject.element("expanded", childrenMenuList.get(j).getExpanded());
				childrenJsonObject.element("checked", childrenMenuList.get(j).getChecked());
				childrenJsonObject.element("leaf", childrenMenuList.get(j).getLeaf());
				childrenJsonObject.element("url", childrenMenuList.get(j).getUrl());
				childrenJsonObject.element("iconCls", childrenMenuList.get(j).getIconCls());
				jsonArray.add(childrenJsonObject);
			}
			jsonObject.element("children", jsonArray);
			resultList.add(jsonObject);
		}
		writeJSON(response, resultList);
	}

	@RequestMapping("/getAuthorizationList")
	public void getAuthorizationList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String roleParam = request.getParameter("role");
		List<String> authorityIdList = new ArrayList<String>();
		if (roleParam != null) {
			List<RoleAuthority> roleAuthorityList = roleAuthorityService.queryByProerties("role", Short.valueOf(roleParam));
			for (RoleAuthority roleAuthority : roleAuthorityList) {
				authorityIdList.add(roleAuthority.getAuthorityId());
			}
		}

		List<Authority> mainMenuList = authorityService.queryByProerties("parentId", null);
		List resultList = new ArrayList();
		for (int i = 0; i < mainMenuList.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.element("id", mainMenuList.get(i).getId());
			jsonObject.element("text", mainMenuList.get(i).getMenuName()+(mainMenuList.get(i).getDescInfo().equals("")?"":"("+ mainMenuList.get(i).getDescInfo()+")"));
			jsonObject.element("expanded", mainMenuList.get(i).getExpanded());

			if (authorityIdList.contains(mainMenuList.get(i).getId().toString())) {
				jsonObject.element("checked", true);
			} else {
				jsonObject.element("checked", false);
			}

			jsonObject.element("leaf", mainMenuList.get(i).getLeaf());

			JSONArray jsonArray = new JSONArray();
			List<Authority> childrenMenuList = authorityService.queryByProerties("parentId", mainMenuList.get(i).getId());
			for (int j = 0; j < childrenMenuList.size(); j++) {
				JSONObject childrenJsonObject = new JSONObject();
				childrenJsonObject.element("id", childrenMenuList.get(j).getId());
				childrenJsonObject.element("text", childrenMenuList.get(j).getMenuName()+(childrenMenuList.get(j).getDescInfo().equals("")?"":"("+ childrenMenuList.get(j).getDescInfo()+")"));
				childrenJsonObject.element("expanded", childrenMenuList.get(j).getExpanded());
				if (authorityIdList.contains(childrenMenuList.get(j).getId().toString())) {
					childrenJsonObject.element("checked", true);
				} else {
					childrenJsonObject.element("checked", false);
				}

				if (childrenMenuList.get(j).getButtons().length() == 0) {
					childrenJsonObject.element("leaf", true);
				} else {
					childrenJsonObject.element("leaf", false);
				}

				JSONArray buttonJSONArray = new JSONArray();
				String[] buttons = childrenMenuList.get(j).getButtons().split(",");
				for (int z = 0; z < buttons.length; z++) {
					if (StringUtils.isBlank(buttons[z])) {
						continue;
					}
					JSONObject buttonChildrenJSONObject = new JSONObject();
					buttonChildrenJSONObject.element("id", childrenMenuList.get(j).getId() + buttons[z]);
					String buttonText = null;
					if (buttons[z].equalsIgnoreCase("Add")) {
						buttonText = "添加";
					} else if (buttons[z].trim().equalsIgnoreCase("Edit")) {
						buttonText = "修改";
					} else if (buttons[z].trim().equalsIgnoreCase("Delete")) {
						buttonText = "删除";
					} else if (buttons[z].trim().equalsIgnoreCase("View")) {
						buttonText = "查看";
					} else if (buttons[z].trim().equalsIgnoreCase("Import")) {
						buttonText = "导入";
					} else if (buttons[z].trim().equalsIgnoreCase("Query")) {
						buttonText = "查询";
					} else if (buttons[z].trim().equalsIgnoreCase("Disarm")) {
						buttonText = "解除";
					} else if (buttons[z].trim().equalsIgnoreCase("Export")) {
						buttonText = "导出";
					}else if (buttons[z].trim().equalsIgnoreCase("Examine")) {
						buttonText = "审核";
					}else if (buttons[z].trim().equalsIgnoreCase("AllDeprtmentQuery")) {
						buttonText = "跨区域查询";
					}else if (buttons[z].trim().equalsIgnoreCase("QuitTenant")) {
						buttonText = "退租";
					}
					
					buttonChildrenJSONObject.element("text", buttonText);
					buttonChildrenJSONObject.element("expanded", true);

					if (authorityIdList.contains(childrenMenuList.get(j).getId() + buttons[z])) {
						buttonChildrenJSONObject.element("checked", true);
					} else {
						buttonChildrenJSONObject.element("checked", false);
					}

					buttonChildrenJSONObject.element("leaf", true);
					buttonJSONArray.add(buttonChildrenJSONObject);
				}
				childrenJsonObject.element("children", buttonJSONArray);

				jsonArray.add(childrenJsonObject);

			}
			jsonObject.element("children", jsonArray);
			resultList.add(jsonObject);
		}
		writeJSON(response, resultList);
	}

	@RequestMapping("/getAuthorityTreePicker")
	public void getAuthorityTreePicker(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Short role = Short.valueOf(request.getParameter("roleId"));
		List<Authority> mainMenuList = authorityService.queryByParentIdAndRole(role);
		List resultList = new ArrayList();
		for (int i = 0; i < mainMenuList.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.element("id", mainMenuList.get(i).getId());
			jsonObject.element("sortOrder", mainMenuList.get(i).getSortOrder());
			jsonObject.element("menuCode", mainMenuList.get(i).getMenuCode());
			jsonObject.element("text", mainMenuList.get(i).getMenuName());
			jsonObject.element("menuConfig", mainMenuList.get(i).getMenuConfig());
			jsonObject.element("buttons", mainMenuList.get(i).getButtons());
			jsonObject.element("expanded", true);
			jsonObject.element("checked", mainMenuList.get(i).getChecked());
			jsonObject.element("leaf", mainMenuList.get(i).getLeaf());
			jsonObject.element("url", mainMenuList.get(i).getUrl());
			jsonObject.element("iconCls", mainMenuList.get(i).getIconCls());

			JSONArray jsonArray = new JSONArray();
			List<Authority> childrenMenuList = authorityService.queryChildrenByParentIdAndRole(mainMenuList.get(i).getId(), role);
			for (int j = 0; j < childrenMenuList.size(); j++) {
				JSONObject childrenJsonObject = new JSONObject();

				String buttons = authorityService.querySurfaceAuthorityList(roleAuthorityService.queryByProerties("role", role), childrenMenuList.get(j).getId(), childrenMenuList.get(j).getButtons());

				childrenJsonObject.element("id", childrenMenuList.get(j).getId());
				childrenJsonObject.element("sortOrder", childrenMenuList.get(j).getSortOrder());
				childrenJsonObject.element("menuCode", childrenMenuList.get(j).getMenuCode());
				childrenJsonObject.element("text", childrenMenuList.get(j).getMenuName());
				childrenJsonObject.element("menuConfig", childrenMenuList.get(j).getMenuConfig());
				childrenJsonObject.element("buttons", buttons);
				childrenJsonObject.element("expanded", true);
				childrenJsonObject.element("checked", childrenMenuList.get(j).getChecked());
				childrenJsonObject.element("leaf", childrenMenuList.get(j).getLeaf());
				childrenJsonObject.element("url", childrenMenuList.get(j).getUrl());
				childrenJsonObject.element("iconCls", childrenMenuList.get(j).getIconCls());
				jsonArray.add(childrenJsonObject);
			}
			jsonObject.element("children", jsonArray);
			resultList.add(jsonObject);
		}
		writeJSON(response, resultList);
	}

	@Override
	@RequestMapping(value = "/saveAuthority", method = { RequestMethod.POST, RequestMethod.GET })
	public void doSave(Authority entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
		ExtJSBaseParameter parameter = ((ExtJSBaseParameter) entity);
		Authority checkMenuCode = authorityService.getByProerties("menuCode", entity.getMenuCode());
		if (null != checkMenuCode && null == entity.getId()) {
			parameter.setSuccess(false);
		} else {
			if (entity.getChecked() == false) {
				entity.setChecked(null);
			}
			if (CMD_EDIT.equals(parameter.getCmd())) {
				authorityService.update(entity);
			} else if (CMD_NEW.equals(parameter.getCmd())) {
				authorityService.persist(entity);
			}
			parameter.setCmd(CMD_EDIT);
			parameter.setSuccess(true);
		}
		writeJSON(response, parameter);
	}

	@RequestMapping(value = "/getAuthorityPagination", method = { RequestMethod.POST, RequestMethod.GET })
	public void getAuthorityPagination(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		Authority authority = new Authority();
		authority.setFirstResult(firstResult);
		authority.setMaxResults(maxResults);
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put(sortedObject, sortedValue);
		authority.setSortedConditions(sortedCondition);
		QueryResult<Authority> queryResult = authorityService.doPaginationQuery(authority);
		ListView<Authority> authorityListView = new ListView<Authority>();
		authorityListView.setData(queryResult.getResultList());
		authorityListView.setTotalRecord(queryResult.getTotalCount());
		writeJSON(response, authorityListView);
	}

	@RequestMapping("/deleteAuthority")
	public void deleteAuthority(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Long[] ids) throws IOException {
		boolean flag = authorityService.deleteByPK(ids);
		if (flag) {
			writeJSON(response, "{success:true}");
		} else {
			writeJSON(response, "{success:false}");
		}
	}

}
