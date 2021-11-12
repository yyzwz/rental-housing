package com.forestry.service.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.forestry.dao.sys.RoomToTenantDao;
import com.forestry.model.sys.RoomToTenant;
import com.forestry.service.sys.RoomToTenantService;

import core.service.BaseService;

/**
 * @author 郑为中
 */
@Service
public class RoomToTenantServiceImpl extends BaseService<RoomToTenant> implements RoomToTenantService {

	private RoomToTenantDao roomToTenantDao;
	
	@Resource
	public void setRoomToTenantDao(RoomToTenantDao roomToTenantDao) {
		this.roomToTenantDao = roomToTenantDao;
		this.dao = roomToTenantDao;
	}
	@Override
	public void updateRoomToTenant(RoomToTenant roomToTenant) {
		roomToTenantDao.updateQmm(roomToTenant);
	}
	
	
	@Override
	public List<Object[]> queryExportedRoomToTenant(Long[] ids) {
		return roomToTenantDao.queryExportedRoomToTenant(ids);
	}
	

}
