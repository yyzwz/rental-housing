package com.forestry.dao.sys.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.forestry.dao.sys.SensorDataDao;
import com.forestry.model.sys.SensorData;

import core.dao.BaseDao;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Repository
public class SensorDataDaoImpl extends BaseDao<SensorData> implements SensorDataDao {

	public SensorDataDaoImpl() {
		super(SensorData.class);
	}

	@Override
	public List<Object[]> doGetSensorDataStatistics(Short sensorType) {
		// MySQL
		Query query = getSession().createSQLQuery("select concat(date_format(record_time, '%H'),':00') name,round(avg(sensor_value),2) data1,max(sensor_value) mx,min(sensor_value) mn,cast(group_concat(sensor_value) as char) gc from sensor_data sd where sensor_type = ? group by date_format(record_time, '%Y-%m-%d %H')"); 
		// Oracle
		// Query query = getSession().createSQLQuery("select concat(to_char(record_time, 'yyyy-mm-dd hh24'),':00') name,round(avg(sensor_value),2) data1,max(sensor_value) mx,min(sensor_value) mn,wm_concat(sensor_value) gc from sensor_data sd where sensor_type = ? group by to_char(record_time, 'yyyy-mm-dd hh24')");
		// SQL Server
		// Query query = getSession().createSQLQuery("select CONVERT(varchar(12) ,record_time, 114) name,round(avg(sensor_value),2) data1,max(sensor_value) mx,min(sensor_value) mn,'' gc from sensor_data sd where sensor_type = ? group by CONVERT(varchar(12) ,record_time, 114)");
		query.setParameter(0, sensorType);
		return query.list();
	}

}
