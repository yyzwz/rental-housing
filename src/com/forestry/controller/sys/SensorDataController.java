package com.forestry.controller.sys;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forestry.core.ForestryBaseController;
import com.forestry.model.sys.SensorData;
import com.forestry.service.sys.SensorDataService;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Controller
@RequestMapping("/sys/sensordata")
public class SensorDataController extends ForestryBaseController<SensorData> {

	@Resource
	private SensorDataService sensorDataService;

	@RequestMapping(value = "/getSensorDataStatistics")
	public void getSensorDataStatistics(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Object[]> list = sensorDataService.doGetSensorDataStatistics(Short.valueOf(request.getParameter("sensorType")));
		List<Object[]> enhanceList = sensorDataService.doGetEnhanceSensorDataStatistics(list);
		writeJSON(response, enhanceList);
	}

}
