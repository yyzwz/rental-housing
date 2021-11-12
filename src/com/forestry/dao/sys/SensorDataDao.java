package com.forestry.dao.sys;

import java.util.List;

import com.forestry.model.sys.SensorData;

import core.dao.Dao;

/**
 * @author 郑为中
 */
public interface SensorDataDao extends Dao<SensorData> {

	List<Object[]> doGetSensorDataStatistics(Short sensorType);

}
