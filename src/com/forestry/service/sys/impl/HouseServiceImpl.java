package com.forestry.service.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.forestry.dao.sys.HouseDao;
import com.forestry.dao.sys.HouseTypeDao;
import com.forestry.model.sys.House;
import com.forestry.model.sys.SysUser;
import com.forestry.service.sys.HouseService;

import core.service.BaseService;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Service
public class HouseServiceImpl extends BaseService<House> implements HouseService {

	private HouseDao houseDao;
	
	//@Resource
	//private HouseTypeDao houseTypeDao;

	@Resource
	public void setHouseDao(HouseDao houseDao) {
		this.houseDao = houseDao;
		this.dao = houseDao;
	}
	@Override
	public void updateHouse(House house) {
		houseDao.updateQmm(house);
	}
	
	//public int saveHouseTwoDimensionalCode(Long id);
	@Override
	public int saveHouseTwoDimensionalCode(HttpServletRequest request, String filePath,Long id) {
		return houseDao.saveHouseTwoDimensionalCode( request, filePath,id);
	}
	@Override
	public List<Object[]> queryExportedHouse(Long[] ids) {
		return houseDao.queryExportedHouse(ids);
	}
	
	
	

}
