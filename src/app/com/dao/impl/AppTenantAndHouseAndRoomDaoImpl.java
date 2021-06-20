package app.com.dao.impl;

import org.springframework.stereotype.Repository;
import app.com.dao.AppTenantAndHouseAndRoomDao;
import app.com.model.AppTenantAndHouseAndRoom;
import core.dao.BaseDao;

/**
 * @author 郑为中
 */
@Repository
public class AppTenantAndHouseAndRoomDaoImpl extends BaseDao<AppTenantAndHouseAndRoom> implements AppTenantAndHouseAndRoomDao {

	public AppTenantAndHouseAndRoomDaoImpl() {
		super(AppTenantAndHouseAndRoom.class);
	}

}