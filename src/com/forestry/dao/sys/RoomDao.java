package com.forestry.dao.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.forestry.model.sys.Room;

import core.dao.Dao;

/**
 * @author 郑为中
 */
public interface RoomDao extends Dao<Room> {
	
	List<Object[]> queryExportedRoom(Long[] ids);

	int CheckRoom(Long[] ids,String checkOption);
    void updateRoom(Room house);
    int saveRoomTwoDimensionalCode(HttpServletRequest request, String filePath,Long id);//saveRoomTwoDimensionalCode
}
