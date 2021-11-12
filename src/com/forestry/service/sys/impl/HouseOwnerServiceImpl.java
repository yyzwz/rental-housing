package com.forestry.service.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.forestry.dao.sys.HouseOwnerDao;
import com.forestry.dao.sys.HouseTypeDao;
import com.forestry.model.sys.HouseOwner;
import com.forestry.service.sys.HouseOwnerService;

import core.service.BaseService;

/**
 * @author 郑为中
 */
@Service
public class HouseOwnerServiceImpl extends BaseService<HouseOwner> implements HouseOwnerService {

	private HouseOwnerDao houseOwnerDao;
	
	

	@Resource
	public void setHouseOwnerDao(HouseOwnerDao houseOwnerDao) {
		this.houseOwnerDao = houseOwnerDao;
		this.dao = houseOwnerDao;
	}
	@Override
	public void updateHouseOwner(HouseOwner HouseOwner) {
		houseOwnerDao.updateQmm(HouseOwner);
	}
	@Override
	public int checkHouseOwner(Long[] ids,String checkOption) {
		return houseOwnerDao.CheckHouseOwner(ids,checkOption);
	}
	//public int saveHouseOwnerTwoDimensionalCode(Long id);
	@Override
	public int saveHouseOwnerTwoDimensionalCode(HttpServletRequest request, String filePath,Long id) {
		return houseOwnerDao.saveHouseOwnerTwoDimensionalCode( request, filePath,id);
	}
	@Override
	public List<Object[]> queryExportedHouseOwner(Long[] ids) {
		return houseOwnerDao.queryExportedHouseOwner(ids);
	}

}
