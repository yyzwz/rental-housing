package app.com.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import app.com.dao.AppTenantAndHouseAndRoomDao;
import app.com.model.AppHouse;
import app.com.model.AppTenantAndHouseAndRoom;
import app.com.service.AppTenantAndHouseAndRoomService;
import core.service.BaseService;

/**
 * @author 郑为中
 */
@Service
public class AppTenantAndHouseAndRoomServiceImpl extends BaseService<AppTenantAndHouseAndRoom> implements AppTenantAndHouseAndRoomService {

	private AppTenantAndHouseAndRoomDao appTenantAndHouseAndRoomDao;
	
	@Resource
	public void setAppTenantAndHouseAndRoomDao(AppTenantAndHouseAndRoomDao appTenantAndHouseAndRoomDao) {
		this.appTenantAndHouseAndRoomDao = appTenantAndHouseAndRoomDao;
		this.dao = appTenantAndHouseAndRoomDao;
	}
	
	public void saveTenantAndHouseAndRoom(AppTenantAndHouseAndRoom appTenantAndHouseAndRoom){
		appTenantAndHouseAndRoomDao.updateQmm(appTenantAndHouseAndRoom);
	}
}