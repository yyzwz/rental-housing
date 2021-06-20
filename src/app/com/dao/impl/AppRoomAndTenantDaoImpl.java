package app.com.dao.impl;

import org.springframework.stereotype.Repository;

import app.com.dao.AppRoomAndTenantDao;
import app.com.model.AppRoomAndTenant;
import core.dao.BaseDao;

/**
 * @author 郑为中
 */
@Repository
public class AppRoomAndTenantDaoImpl extends BaseDao<AppRoomAndTenant> implements AppRoomAndTenantDao {
	
	public AppRoomAndTenantDaoImpl() {
		super(AppRoomAndTenant.class);
	}
}
