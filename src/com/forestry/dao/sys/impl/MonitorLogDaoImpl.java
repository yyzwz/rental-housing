package com.forestry.dao.sys.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.forestry.dao.sys.MonitorLogDao;
import com.forestry.model.sys.MonitorLog;

import core.dao.BaseDao;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Repository
public class MonitorLogDaoImpl extends BaseDao<MonitorLog> implements MonitorLogDao {

	public MonitorLogDaoImpl() {
		super(MonitorLog.class);
	}

	@Override
	public List<MonitorLog> querySensorMonitorLog() {
		SQLQuery query = getSession()
				.createSQLQuery(
						"select ss.xcoordinate,ss.ycoordinate,ss.epc_id,ml.object_id,ml.message,ml.value from sensor ss,monitor_log ml where ss.sensor_id = ml.object_id and ss.xcoordinate is not null and ss.ycoordinate is not null and ss.type = 1 and ml.value = (select min(value) from monitor_log mlt where ml.object_id = mlt.object_id)");
		return query.list();
	}

}
