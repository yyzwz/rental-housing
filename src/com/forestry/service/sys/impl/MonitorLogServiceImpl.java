package com.forestry.service.sys.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.forestry.dao.sys.MonitorLogDao;
import com.forestry.model.sys.MonitorLog;
import com.forestry.service.sys.MonitorLogService;

import core.service.BaseService;

/**
 * @author 郑为中
 */
@Service
public class MonitorLogServiceImpl extends BaseService<MonitorLog> implements MonitorLogService {

	private MonitorLogDao monitorLogDao;

	@Resource
	public void setMonitorLogDao(MonitorLogDao monitorLogDao) {
		this.monitorLogDao = monitorLogDao;
		this.dao = monitorLogDao;
	}

	@Override
	public List<MonitorLog> querySensorMonitorLog() {
		return monitorLogDao.querySensorMonitorLog();
	}

}
