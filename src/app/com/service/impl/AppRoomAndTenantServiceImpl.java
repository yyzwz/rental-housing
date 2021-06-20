package app.com.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import app.com.dao.AppHouseTypeDao;
import app.com.dao.AppRoomAndTenantDao;
import app.com.model.AppHouse;
import app.com.model.AppHouseType;
import app.com.model.AppRoomAndTenant;
import app.com.service.AppRoomAndTenantService;
import core.service.BaseService;

/**
 * @author 郑为中
 */
@Service
public class AppRoomAndTenantServiceImpl extends BaseService<AppRoomAndTenant> implements AppRoomAndTenantService {

	private AppRoomAndTenantDao appRoomAndTenantDao;
	
	@Resource
	public void setAppRoomAndTenantDao(AppRoomAndTenantDao appRoomAndTenantDao) {
		this.appRoomAndTenantDao = appRoomAndTenantDao;
		this.dao = appRoomAndTenantDao;
	}
	
	public void saveRoomAndTenant(AppRoomAndTenant appRoomAndTenant){
		appRoomAndTenantDao.updateQmm(appRoomAndTenant);
	}
}
