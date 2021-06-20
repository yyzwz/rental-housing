package app.com.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import app.com.dao.AppDepartmentDao;
import app.com.model.AppDepartment;
import app.com.service.AppDepartmentService;
import core.service.BaseService;

/**
 * @author 郑为中
 */
@Service
public class AppDepartmentServiceImpl extends BaseService<AppDepartment> implements AppDepartmentService{

	private AppDepartmentDao appDepartmentDao;
	
	@Resource
	public void setAppDepartmentDao(AppDepartmentDao appDepartmentDao) {
		this.appDepartmentDao = appDepartmentDao;
		this.dao = appDepartmentDao;
	}
}
