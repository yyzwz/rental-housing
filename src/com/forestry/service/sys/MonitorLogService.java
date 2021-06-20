package com.forestry.service.sys;

import java.util.List;

import com.forestry.model.sys.MonitorLog;

import core.service.Service;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public interface MonitorLogService extends Service<MonitorLog> {

	List<MonitorLog> querySensorMonitorLog();

}
