package app.com.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import app.com.dao.AppHouseAndRoomDao;
import app.com.model.AppHouseAndRoom;
import app.com.service.AppHouseAndRoomService;
import core.service.BaseService;

/**
 * @author 郑为中
 */
@Service
public class AppHouseAndRoomServiceImpl extends BaseService<AppHouseAndRoom> implements AppHouseAndRoomService {

	private AppHouseAndRoomDao appHouseAndRoomDao;
	
	@Resource
	public void setAppHouseAndRoomDao(AppHouseAndRoomDao appHouseAndRoomDao) {
		this.appHouseAndRoomDao = appHouseAndRoomDao;
		this.dao = appHouseAndRoomDao;
	}
}