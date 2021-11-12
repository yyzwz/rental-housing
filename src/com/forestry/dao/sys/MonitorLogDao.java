package com.forestry.dao.sys;

import java.util.List;

import com.forestry.model.sys.MonitorLog;

import core.dao.Dao;

/**
 * @author 郑为中
 */
public interface MonitorLogDao extends Dao<MonitorLog> {

	List<MonitorLog> querySensorMonitorLog();

}
