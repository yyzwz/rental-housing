package com.forestry.dao.sys.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.forestry.dao.sys.SensorDao;
import com.forestry.model.sys.Sensor;

import core.dao.BaseDao;
import core.support.QueryResult;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Repository
public class SensorDaoImpl extends BaseDao<Sensor> implements SensorDao {

	public SensorDaoImpl() {
		super(Sensor.class);
	}

	@Override
	public List<Sensor> querySensorBySensorType(Short sensorType) {
		SQLQuery query = getSession()
				.createSQLQuery(
						"select xcoordinate,ycoordinate,sensor_lastvalue from sensor ss inner join sensor_lastdata sd on ss.sensor_id = sd.sensor_id where ss.xcoordinate is not null and ss.ycoordinate is not null and ss.type = 1 and sd.sensor_type = ?");
		query.setParameter(0, sensorType);
		return query.list();
	}

	@Override
	public List<Sensor> querySensorLastData() {
		SQLQuery query = getSession()
				.createSQLQuery(
						"select ss.sensor_id,ss.xcoordinate,ss.ycoordinate,(select sd.sensor_lastvalue from sensor_lastdata sd where ss.sensor_id = sd.sensor_id and sd.sensor_type = 1) temperature,(select sd.sensor_lastvalue from sensor_lastdata sd where ss.sensor_id = sd.sensor_id and sd.sensor_type = 2) humidity,(select sd.sensor_lastvalue from sensor_lastdata sd where ss.sensor_id = sd.sensor_id and sd.sensor_type = 3) lightfall from sensor ss where ss.xcoordinate is not null and ss.ycoordinate is not null and ss.type = 1 group by ss.sensor_id,xcoordinate,ycoordinate");
		return query.list();
	}

	@Override
	public List<Sensor> queryForestrySensorLastData() {
		SQLQuery query = getSession()
				.createSQLQuery(
						"select ss.sensor_id,ss.xcoordinate,ss.ycoordinate,(select sd.sensor_lastvalue from sensor_lastdata sd where ss.sensor_id = sd.sensor_id and sd.sensor_type = 1) temperature,(select sd.sensor_lastvalue from sensor_lastdata sd where ss.sensor_id = sd.sensor_id and sd.sensor_type = 2) humidity,(select sd.sensor_lastvalue from sensor_lastdata sd where ss.sensor_id = sd.sensor_id and sd.sensor_type = 3) lightfall from sensor ss where ss.xcoordinate is not null and ss.ycoordinate is not null group by ss.sensor_id,xcoordinate,ycoordinate");
		return query.list();
	}

	@Override
	public QueryResult<Sensor> querySensorList(Sensor sensor) {
		String cntSQL = "select count(*) from Sensor where xcoordinate is not null and ycoordinate is not null and epcId is not null";
		String querySQL = "from Sensor where xcoordinate is not null and ycoordinate is not null and epcId is not null order by id desc";
		QueryResult<Sensor> qr = new QueryResult<Sensor>();
		Query cntQuery = getSession().createQuery(cntSQL);
		Query queryQuery = getSession().createQuery(querySQL);
		queryQuery.setFirstResult(sensor.getFirstResult());
		queryQuery.setMaxResults(sensor.getMaxResults());
		qr.setTotalCount(((Number) cntQuery.uniqueResult()).longValue());
		qr.setResultList(queryQuery.list());
		return qr;
	}

	@Override
	public List<Sensor> querySensorLastDataWithEpcId() {
		SQLQuery query = getSession()
				.createSQLQuery(
						"select ss.sensor_id,ss.xcoordinate,ss.ycoordinate,(select sd.sensor_lastvalue from sensor_lastdata sd where ss.sensor_id = sd.sensor_id and sd.sensor_type = 1) temperature,(select sd.sensor_lastvalue from sensor_lastdata sd where ss.sensor_id = sd.sensor_id and sd.sensor_type = 2) humidity,(select sd.sensor_lastvalue from sensor_lastdata sd where ss.sensor_id = sd.sensor_id and sd.sensor_type = 3) lightfall,ss.epc_id from sensor ss where ss.xcoordinate is not null and ss.ycoordinate is not null and ss.epc_id is not null");
		return query.list();
	}

}
