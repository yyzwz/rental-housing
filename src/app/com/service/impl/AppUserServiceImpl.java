package app.com.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import app.com.dao.AppUserDao;
import app.com.model.AppUser;
import app.com.service.AppUserService;
import core.service.BaseService;

/**
 * @author 郑为中
 */
@Service
public class AppUserServiceImpl extends BaseService<AppUser> implements AppUserService {

	private AppUserDao appUserDao;
	
	@Resource
	public void setAppUserDao(AppUserDao appUserDao) {
		this.appUserDao = appUserDao;
		this.dao = appUserDao;
	}
}
