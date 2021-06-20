package com.forestry.dao.sys;

import java.util.List;

import com.forestry.model.sys.SysUser;

import core.dao.Dao;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public interface SysUserDao extends Dao<SysUser> {
	List<Object[]> queryExportedSysUserDao(Long[] ids);
}
