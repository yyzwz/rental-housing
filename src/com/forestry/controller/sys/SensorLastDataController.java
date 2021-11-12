package com.forestry.controller.sys;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forestry.core.ForestryBaseController;
import com.forestry.model.sys.SensorLastData;
import com.forestry.service.sys.SensorLastDataService;

/**
 * @author 郑为中
 */
@Controller
@RequestMapping("/sys/sensorlastdata")
public class SensorLastDataController extends ForestryBaseController<SensorLastData> {

	@Resource
	private SensorLastDataService sensorLastDataService;

}
