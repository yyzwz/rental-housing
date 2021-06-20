package app.com.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import app.com.dao.AppPeopleDao;
import app.com.model.AppPeople;
import app.com.service.AppPeopleService;
import core.service.BaseService;

/**
 * @author 郑为中
 */
@Service
public class AppPeopleServiceImpl  extends BaseService<AppPeople> implements AppPeopleService {

	private AppPeopleDao appPeopleDao;
	
	@Resource
	public void setAppPeopleDao(AppPeopleDao appPeopleDao) {
		this.appPeopleDao = appPeopleDao;
		this.dao = appPeopleDao;
	}
	
}