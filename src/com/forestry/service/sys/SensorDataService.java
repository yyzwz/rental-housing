package com.forestry.service.sys;

import java.util.List;

import com.forestry.model.sys.SensorData;

import core.service.Service;

/**
 * @author 郑为中
 */
public interface SensorDataService extends Service<SensorData> {

	List<Object[]> doGetSensorDataStatistics(Short sensorType);

	List<Object[]> doGetEnhanceSensorDataStatistics(List<Object[]> list);

}
