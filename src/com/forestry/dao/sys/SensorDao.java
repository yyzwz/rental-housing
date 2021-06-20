package com.forestry.dao.sys;

import java.util.List;

import com.forestry.model.sys.Sensor;

import core.dao.Dao;
import core.support.QueryResult;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public interface SensorDao extends Dao<Sensor> {

	List<Sensor> querySensorBySensorType(Short sensorType);

	List<Sensor> querySensorLastData();

	QueryResult<Sensor> querySensorList(Sensor sensor);

	List<Sensor> querySensorLastDataWithEpcId();

	List<Sensor> queryForestrySensorLastData();

}
