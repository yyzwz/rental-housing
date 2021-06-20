package com.forestry.dao.sys;

import java.util.List;

import com.forestry.model.sys.SensorData;

import core.dao.Dao;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public interface SensorDataDao extends Dao<SensorData> {

	List<Object[]> doGetSensorDataStatistics(Short sensorType);

}
