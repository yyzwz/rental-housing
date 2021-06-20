package com.forestry.dao.sys.impl;

import org.springframework.stereotype.Repository;

import com.forestry.dao.sys.RoleAuthorityDao;
import com.forestry.model.sys.RoleAuthority;

import core.dao.BaseDao;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Repository
public class RoleAuthorityDaoImpl extends BaseDao<RoleAuthority> implements RoleAuthorityDao {

	public RoleAuthorityDaoImpl() {
		super(RoleAuthority.class);
	}
}
