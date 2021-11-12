package com.forestry.service.sys;

import java.util.List;

import com.forestry.model.sys.MonitorLog;

import core.service.Service;

/**
 * @author 郑为中
 */
public interface MonitorLogService extends Service<MonitorLog> {

	List<MonitorLog> querySensorMonitorLog();

}
