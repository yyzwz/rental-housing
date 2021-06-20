package app.com.service;

import javax.servlet.http.HttpServletRequest;

import app.com.model.AppHouse;
import core.service.Service;

/**
 * @author 郑为中
 */

public interface AppHouseService extends Service<AppHouse> {
	public void saveHouse(AppHouse appHouse);
	
	public int saveHouseTwoDimensionalCode(HttpServletRequest request, String filePath,Long id);

}
