package com.forestry.service.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.forestry.model.sys.RoomToTenant;

import core.service.Service;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public interface RoomToTenantService extends Service<RoomToTenant> {

	List<Object[]> queryExportedRoomToTenant(Long[] ids);
	public void updateRoomToTenant(RoomToTenant house);
	
}
