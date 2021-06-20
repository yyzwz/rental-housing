package app.com.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import app.com.dao.AppUserAndHouseDao;
import app.com.model.AppUserAndHouse;
import app.com.service.AppUserAndHouseService;
import core.service.BaseService;

/**
 * @author 郑为中
 */
@Service
public class AppUserAndHouseServiceImpl extends BaseService<AppUserAndHouse> implements AppUserAndHouseService {

	private AppUserAndHouseDao appUserAndHouseDao;
	
	@Resource
	public void setAppUserAndHouseDao(AppUserAndHouseDao appUserAndHouseDao) {
		this.appUserAndHouseDao = appUserAndHouseDao;
		this.dao = appUserAndHouseDao;
	}
}