package app.com.service.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import app.com.dao.AppHouseDao;
import app.com.model.AppHouse;
import app.com.service.AppHouseService;
import core.service.BaseService;

/**
 * @author 郑为中
 */
@Service
public class AppHouseServiceImpl extends BaseService<AppHouse> implements AppHouseService {
	
	private AppHouseDao appHouseDao;
	
	@Resource
	public void setAppHouseDao(AppHouseDao appHouseDao) {
		this.appHouseDao = appHouseDao;
		this.dao = appHouseDao;
	}
	
	public void saveHouse(AppHouse appHouse){
		appHouseDao.updateQmm(appHouse);
	}
	
	@Override
	public int saveHouseTwoDimensionalCode(HttpServletRequest request, String filePath,Long id) {
		return appHouseDao.saveHouseTwoDimensionalCode( request, filePath,id);
	}
}
