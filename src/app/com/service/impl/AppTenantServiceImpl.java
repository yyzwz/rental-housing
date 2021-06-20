package app.com.service.impl;



import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import app.com.dao.AppTenantDao;
import app.com.model.AppHouse;
import app.com.model.AppTenant;
import app.com.service.AppTenantService;
import core.service.BaseService;

/**
 * @author 郑为中
 */
@Service
public class AppTenantServiceImpl extends BaseService<AppTenant> implements AppTenantService {

	private AppTenantDao appTenantDao;
	
	@Resource
	public void setAppUserDao(AppTenantDao appTenantDao) {
		this.appTenantDao = appTenantDao;
		this.dao = appTenantDao;
	}
	
	public void saveTenant(AppTenant appTenant){
		appTenantDao.updateQmm(appTenant);
	}
}
