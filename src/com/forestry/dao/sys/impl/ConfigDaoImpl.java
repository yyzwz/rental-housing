package com.forestry.dao.sys.impl;

import org.springframework.stereotype.Repository;

import com.forestry.dao.sys.ConfigDao;
import com.forestry.model.sys.Config;

import core.dao.BaseDao;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Repository
public class ConfigDaoImpl extends BaseDao<Config> implements ConfigDao {

	public ConfigDaoImpl() {
		super(Config.class);
	}

}
