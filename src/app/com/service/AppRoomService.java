package app.com.service;

import app.com.model.AppHouse;
import app.com.model.AppRoom;
import core.service.Service;

/**
 * @author 郑为中
 */

public interface AppRoomService extends Service<AppRoom>{
	
	public void saveRoom(AppRoom appRoom);
}