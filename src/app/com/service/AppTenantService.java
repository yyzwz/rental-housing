package app.com.service;


import app.com.model.AppHouse;
import app.com.model.AppTenant;
import core.service.Service;

/**
 * @author 郑为中
 */

public interface AppTenantService extends Service<AppTenant>{
	public void saveTenant(AppTenant appTenant);
	
}
