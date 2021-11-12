package com.forestry.service.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.forestry.dao.sys.RoomDao;
import com.forestry.model.sys.Room;
import com.forestry.service.sys.RoomService;

import core.service.BaseService;

/**
 * @author 郑为中
 */
@Service
public class RoomServiceImpl extends BaseService<Room> implements RoomService {

	private RoomDao roomDao;
	
	@Resource
	private RoomDao houseDao;

	@Resource
	public void setRoomDao(RoomDao houseDao) {
		this.houseDao = houseDao;
		this.dao = houseDao;
	}
	@Override
	public void updateRoom(Room house) {
		houseDao.updateQmm(house);
	}
	@Override
	public int checkRoom(Long[] ids,String checkOption) {
		return roomDao.CheckRoom(ids,checkOption);
	}
	//public int saveRoomTwoDimensionalCode(Long id);
	@Override
	public int saveRoomTwoDimensionalCode(HttpServletRequest request, String filePath,Long id) {
		return houseDao.saveRoomTwoDimensionalCode( request, filePath,id);
	}
	@Override
	public List<Object[]> queryExportedRoom(Long[] ids) {
		return houseDao.queryExportedRoom(ids);
	}

}
