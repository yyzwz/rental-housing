package app.com.dao.impl;

import org.springframework.stereotype.Repository;
import app.com.dao.AppUserAndHouseDao;
import app.com.model.AppUserAndHouse;
import core.dao.BaseDao;

/**
 * @author 郑为中
 */
@Repository
public class AppUserAndHouseDaoImpl extends BaseDao<AppUserAndHouse> implements AppUserAndHouseDao {

	public AppUserAndHouseDaoImpl() {
		super(AppUserAndHouse.class);
	}
}