package com.forestry.service.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.forestry.model.sys.Room;

import core.service.Service;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public interface RoomService extends Service<Room> {

	List<Object[]> queryExportedRoom(Long[] ids);
	public int checkRoom(Long[] ids,String checkOption);
	public int saveRoomTwoDimensionalCode(HttpServletRequest request, String filePath,Long id);
	public void updateRoom(Room house);
	
}
