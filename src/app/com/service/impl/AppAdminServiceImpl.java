package app.com.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import app.com.dao.AppAdminDao;
import app.com.model.AppAdmim;
import app.com.service.AppAdminService;
import core.service.BaseService;

/**
 * @author 郑为中
 */
@Service
public class AppAdminServiceImpl extends BaseService<AppAdmim> implements AppAdminService{

	private AppAdminDao appAdminDao;
	
	@Resource
	public void setAppAdminDao(AppAdminDao appAdminDao) {
		this.appAdminDao = appAdminDao;
		this.dao = appAdminDao;
	}
}
