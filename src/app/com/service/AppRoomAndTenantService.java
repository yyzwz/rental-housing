package app.com.service;

import app.com.model.AppRoomAndTenant;
import core.service.Service;

/**
 * @author 郑为中
 */

public interface AppRoomAndTenantService extends Service<AppRoomAndTenant> {
	public void saveRoomAndTenant(AppRoomAndTenant appRoomAndTenant);
	
	
}
