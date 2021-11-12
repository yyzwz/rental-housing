package com.forestry.service.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.forestry.model.sys.HouseOwner;

import core.service.Service;

/**
 * @author 郑为中
 */
public interface HouseOwnerService extends Service<HouseOwner> {

	List<Object[]> queryExportedHouseOwner(Long[] ids);
	public int checkHouseOwner(Long[] ids,String checkOption);
	public int saveHouseOwnerTwoDimensionalCode(HttpServletRequest request, String filePath,Long id);
	public void updateHouseOwner(HouseOwner HouseOwner);
	
}
