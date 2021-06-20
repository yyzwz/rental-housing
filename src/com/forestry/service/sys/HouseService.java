package com.forestry.service.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.forestry.model.sys.House;

import core.service.Service;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public interface HouseService extends Service<House> {

	List<Object[]> queryExportedHouse(Long[] ids);
	public int saveHouseTwoDimensionalCode(HttpServletRequest request, String filePath,Long id);
	public void updateHouse(House house);
	
}
