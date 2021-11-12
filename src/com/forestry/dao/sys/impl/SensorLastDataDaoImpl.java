package com.forestry.dao.sys.impl;

import org.springframework.stereotype.Repository;

import com.forestry.dao.sys.SensorLastDataDao;
import com.forestry.model.sys.SensorLastData;

import core.dao.BaseDao;

/**
 * @author 郑为中
 */
@Repository
public class SensorLastDataDaoImpl extends BaseDao<SensorLastData> implements SensorLastDataDao {

	public SensorLastDataDaoImpl() {
		super(SensorLastData.class);
	}

}
