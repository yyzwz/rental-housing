package app.com.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import app.com.dao.AppRoomDao;
import app.com.model.AppHouse;
import app.com.model.AppRoom;
import app.com.service.AppRoomService;
import core.service.BaseService;

/**
 * @author 郑为中
 */
@Service
public class AppRoomServiceImpl extends BaseService<AppRoom> implements AppRoomService {

	private AppRoomDao appRoomDao;
	
	@Resource
	public void setAppRoomDao(AppRoomDao appRoomDao) {
		this.appRoomDao = appRoomDao;
		this.dao = appRoomDao;
	}
	
	public void saveRoom(AppRoom appRoom){
		appRoomDao.updateQmm(appRoom);
	}
}