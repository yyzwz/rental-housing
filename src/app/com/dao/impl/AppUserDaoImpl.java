package app.com.dao.impl;

import org.springframework.stereotype.Repository;
import app.com.dao.AppUserDao;
import app.com.model.AppUser;
import core.dao.BaseDao;

/**
 * @author 郑为中
 */
@Repository
public class AppUserDaoImpl extends BaseDao<AppUser> implements AppUserDao {

	public AppUserDaoImpl() {
		super(AppUser.class);
	}

}