package app.com.dao.impl;

import org.springframework.stereotype.Repository;

import app.com.dao.AppHouseTypeDao;
import app.com.model.AppHouseType;
import core.dao.BaseDao;

/**
 * @author 郑为中
 */
@Repository
public class AppHouseTypeDaoImpl extends BaseDao<AppHouseType> implements AppHouseTypeDao {

	public AppHouseTypeDaoImpl() {
		super(AppHouseType.class);
	}
}