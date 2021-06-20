package app.com.dao.impl;

import org.springframework.stereotype.Repository;

import app.com.dao.AppTenantDao;
import app.com.model.AppTenant;
import core.dao.BaseDao;

/**
 * @author 郑为中
 */
@Repository
public class AppTenantDaoImpl extends BaseDao<AppTenant> implements AppTenantDao {

	public AppTenantDaoImpl() {
		super(AppTenant.class);
	}

}
