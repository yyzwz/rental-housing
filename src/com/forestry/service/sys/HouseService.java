package com.forestry.service.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.forestry.model.sys.House;

import core.service.Service;

/**
 * @author 郑为中
 */
public interface HouseService extends Service<House> {

	List<Object[]> queryExportedHouse(Long[] ids);
	public int saveHouseTwoDimensionalCode(HttpServletRequest request, String filePath,Long id);
	public void updateHouse(House house);
	
}
