package com.forestry.service.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.forestry.dao.sys.HouseTypeDao;
import com.forestry.model.sys.ForestryType;
import com.forestry.model.sys.HouseType;
import com.forestry.service.sys.HouseTypeService;

import core.service.BaseService;
import core.util.HtmlUtils;
import core.web.SystemCache;

/**
 * @author 齐鸣鸣
 */
@Service
public class HouseTypeServiceImpl extends BaseService<HouseType> implements HouseTypeService {

	private HouseTypeDao houseTypeDao;

	@Resource
	public void setSysUserDao(HouseTypeDao houseTypeDao) {
		this.houseTypeDao = houseTypeDao;
		this.dao = houseTypeDao;
	}


	@Override
	public List<Object[]> queryExportedHouseType(Long[] ids) {
		return houseTypeDao.queryExportedHouseType(ids);
	}

}
