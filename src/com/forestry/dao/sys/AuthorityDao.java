package com.forestry.dao.sys;

import java.util.List;

import com.forestry.model.sys.Authority;

import core.dao.Dao;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public interface AuthorityDao extends Dao<Authority> {

	List<Authority> queryByParentIdAndRole(Short role);

	List<Authority> queryChildrenByParentIdAndRole(Long parentId, Short role);

}
