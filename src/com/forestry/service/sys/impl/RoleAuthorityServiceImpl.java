package com.forestry.service.sys.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.forestry.dao.sys.RoleAuthorityDao;
import com.forestry.model.sys.RoleAuthority;
import com.forestry.service.sys.RoleAuthorityService;

import core.service.BaseService;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Service
public class RoleAuthorityServiceImpl extends BaseService<RoleAuthority> implements RoleAuthorityService {

	private RoleAuthorityDao roleAuthorityDao;

	@Resource
	public void setRoleAuthorityDao(RoleAuthorityDao roleAuthorityDao) {
		this.roleAuthorityDao = roleAuthorityDao;
		this.dao = roleAuthorityDao;
	}

}
