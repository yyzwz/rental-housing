package app.com.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import app.com.dao.AppHouseTypeAndHouseDao;
import app.com.model.AppHouseTypeAndHouse;
import app.com.service.AppHouseTypeAndHouseService;
import core.service.BaseService;

/**
 * @author 郑为中
 */
@Service
public class AppHouseTypeAndHouseServiceImpl extends BaseService<AppHouseTypeAndHouse> implements AppHouseTypeAndHouseService {

	private AppHouseTypeAndHouseDao appHouseTypeAndHouseDao;
	
	@Resource
	public void setAppHouseTypeDao(AppHouseTypeAndHouseDao appHouseTypeAndHouseDao) {
		this.appHouseTypeAndHouseDao = appHouseTypeAndHouseDao;
		this.dao = appHouseTypeAndHouseDao;
	}
}