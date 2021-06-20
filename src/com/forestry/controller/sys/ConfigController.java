package com.forestry.controller.sys;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.forestry.core.ForestryBaseController;
import com.forestry.model.sys.Config;
import com.forestry.service.sys.ConfigService;

import core.extjs.ExtJSBaseParameter;
import core.extjs.ListView;
import core.support.Item;
import core.support.QueryResult;
import core.web.SystemCache;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Controller
@RequestMapping("/sys/config")
public class ConfigController extends ForestryBaseController<Config> {

	private static final Logger log = Logger.getLogger(ConfigController.class);

	@Resource
	private ConfigService configService;

	@RequestMapping("/getConfigTypeName")
	public void getConfigTypeName(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONArray jsonArray = new JSONArray();
		for (Map.Entry<String, Item> configTypeMap : SystemCache.DICTIONARY.get("CONFIG_TYPE").getItems().entrySet()) {
			Item item = configTypeMap.getValue();
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
	@RequestMapping(value = "/saveConfig", method = { RequestMethod.POST, RequestMethod.GET })
	public void doSave(Config entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
		ExtJSBaseParameter parameter = ((ExtJSBaseParameter) entity);
		Config checkConfigType = configService.getByProerties("configType", entity.getConfigType());
		if (null != checkConfigType && null == entity.getId()) {
			parameter.setSuccess(false);
		} else {
			if (CMD_EDIT.equals(parameter.getCmd())) {
				configService.update(entity);
			} else if (CMD_NEW.equals(parameter.getCmd())) {
				configService.persist(entity);
			}
			commonConfig();
			parameter.setCmd(CMD_EDIT);
			parameter.setSuccess(true);
		}
		writeJSON(response, parameter);
	}

	private void commonConfig() {
		try {
			HttpClient client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);//设置连接超时时间为5秒（连接初始化时间）
			GetMethod method = new GetMethod("http://192.168.1.86:27730/sensor/cfg?missingthreshold=90");
			int statusCode = client.executeMethod(method);//状态，一般200为OK状态，其他情况会抛出如404,500,403等错误
			if (statusCode != HttpStatus.SC_OK) {
				log.error("失踪警报设置的HTTP GET访问失败！");
			}
			log.info("失踪警报设置的HTTP GET访问结果：" + method.getResponseBodyAsString());
			client.getHttpConnectionManager().closeIdleConnections(1);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@RequestMapping(value = "/getConfig")
	public void getConfig(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		Config config = new Config();
		config.setFirstResult(firstResult);
		config.setMaxResults(maxResults);
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put(sortedObject, sortedValue);
		config.setSortedConditions(sortedCondition);
		QueryResult<Config> queryResult = configService.doPaginationQuery(config);
		List<Config> configList = configService.getConfigList(queryResult.getResultList());
		ListView<Config> configListView = new ListView<Config>();
		configListView.setData(configList);
		configListView.setTotalRecord(queryResult.getTotalCount());
		writeJSON(response, configListView);
	}

	@RequestMapping("/deleteConfig")
	public void deleteConfig(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Long[] ids) throws IOException {
		boolean flag = configService.deleteByPK(ids);
		if (flag) {
			writeJSON(response, "{success:true}");
		} else {
			writeJSON(response, "{success:false}");
		}
	}

}
