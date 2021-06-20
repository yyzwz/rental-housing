package app.com.dao.impl;

import org.springframework.stereotype.Repository;

import app.com.dao.AppPeopleDao;
import app.com.model.AppPeople;
import core.dao.BaseDao;

/**
 * @author 郑为中
 */
@Repository
public class AppPeopleDaoImpl extends BaseDao<AppPeople> implements AppPeopleDao {

	public AppPeopleDaoImpl() {
		super(AppPeople.class);
	}
}
