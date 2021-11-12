package com.forestry.dao.sys;

import java.util.List;

import org.hibernate.Query;

import com.forestry.model.sys.HouseType;

import core.dao.Dao;

/**
 * @author 郑为中
 */
public interface HouseTypeDao extends Dao<HouseType> {

	public List<Object[]> queryExportedHouseType(Long[] ids);
}
