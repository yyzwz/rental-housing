package com.forestry.service.sys.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.forestry.dao.sys.SensorLastDataDao;
import com.forestry.model.sys.SensorLastData;
import com.forestry.service.sys.SensorLastDataService;

import core.service.BaseService;

/**
 * @author 郑为中
 */
@Service
public class SensorLastDataServiceImpl extends BaseService<SensorLastData> implements SensorLastDataService {

	private SensorLastDataDao sensorLastDataDao;

	@Resource
	public void setSensorLastDataDao(SensorLastDataDao sensorLastDataDao) {
		this.sensorLastDataDao = sensorLastDataDao;
		this.dao = sensorLastDataDao;
	}

}
