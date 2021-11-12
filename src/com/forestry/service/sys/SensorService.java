package com.forestry.service.sys;

import java.util.List;

import com.forestry.model.sys.Sensor;

import core.service.Service;
import core.support.QueryResult;

/**
 * @author 郑为中
 */
public interface SensorService extends Service<Sensor> {

	List<Sensor> querySensorBySensorType(Short sensorType);

	List<Sensor> querySensorLastData();

	QueryResult<Sensor> querySensorList(Sensor sensor);

	List<Sensor> querySensorLastDataWithEpcId();

	List<Sensor> queryForestrySensorLastData();

}
