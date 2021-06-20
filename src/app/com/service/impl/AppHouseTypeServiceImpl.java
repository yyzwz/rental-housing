package app.com.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import app.com.dao.AppHouseTypeDao;
import app.com.model.AppHouseType;
import app.com.service.AppHouseTypeService;
import core.service.BaseService;

/**
 * @author 郑为中
 */
@Service
public class AppHouseTypeServiceImpl extends BaseService<AppHouseType> implements AppHouseTypeService {

	private AppHouseTypeDao appHouseTypeDao;
	
	@Resource
	public void setAppHouseTypeDao(AppHouseTypeDao appHouseTypeDao) {
		this.appHouseTypeDao = appHouseTypeDao;
		this.dao = appHouseTypeDao;
	}
}