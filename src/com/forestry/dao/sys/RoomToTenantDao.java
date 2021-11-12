package com.forestry.dao.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.forestry.model.sys.RoomToTenant;

import core.dao.Dao;

/**
 * @author 郑为中
 */
public interface RoomToTenantDao extends Dao<RoomToTenant> {
	
	List<Object[]> queryExportedRoomToTenant(Long[] ids);

	int CheckRoomToTenant(Long[] ids,String checkOption);
    void updateRoomToTenant(RoomToTenant house);
    
}
