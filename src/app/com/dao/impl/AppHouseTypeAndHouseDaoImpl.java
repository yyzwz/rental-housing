package app.com.dao.impl;

import org.springframework.stereotype.Repository;

import app.com.dao.AppHouseTypeAndHouseDao;
import app.com.model.AppHouseTypeAndHouse;
import core.dao.BaseDao;

/**
 * @author 郑为中
 */
@Repository
public class AppHouseTypeAndHouseDaoImpl extends BaseDao<AppHouseTypeAndHouse> implements AppHouseTypeAndHouseDao {

	public AppHouseTypeAndHouseDaoImpl() {
		super(AppHouseTypeAndHouse.class);
	}

}