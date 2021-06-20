package app.com.service;

import app.com.model.AppHouse;
import app.com.model.AppTenantAndHouseAndRoom;
import core.service.Service;

/**
 * @author 郑为中
 */

public interface AppTenantAndHouseAndRoomService extends Service<AppTenantAndHouseAndRoom>{
	public void saveTenantAndHouseAndRoom(AppTenantAndHouseAndRoom appTenantAndHouseAndRoom);
}