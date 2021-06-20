package app.com.dao.impl;

import org.springframework.stereotype.Repository;

import app.com.dao.AppAdminDao;
import app.com.model.AppAdmim;
import core.dao.BaseDao;

/**
 * @author 郑为中
 */
@Repository
public class AppAdminDaoImpl extends BaseDao<AppAdmim> implements AppAdminDao {

	public AppAdminDaoImpl() {
		super(AppAdmim.class);
	}
}
