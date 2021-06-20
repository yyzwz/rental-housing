package app.com.dao.impl;

import org.springframework.stereotype.Repository;
import app.com.dao.AppHouseAndRoomDao;
import app.com.model.AppHouseAndRoom;
import core.dao.BaseDao;

/**
 * @author 郑为中
 */
@Repository
public class AppHouseAndRoomDaoImpl extends BaseDao<AppHouseAndRoom> implements AppHouseAndRoomDao {

	public AppHouseAndRoomDaoImpl() {
		super(AppHouseAndRoom.class);
	}
}