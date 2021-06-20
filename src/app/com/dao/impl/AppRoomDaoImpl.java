package app.com.dao.impl;

import org.springframework.stereotype.Repository;

import app.com.dao.AppRoomDao;
import app.com.model.AppRoom;
import core.dao.BaseDao;

/**
 * @author 郑为中
 */
@Repository
public class AppRoomDaoImpl extends BaseDao<AppRoom> implements AppRoomDao {

	public AppRoomDaoImpl() {
		super(AppRoom.class);
	}
}