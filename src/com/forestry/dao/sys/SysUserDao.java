package com.forestry.dao.sys;

import java.util.List;

import com.forestry.model.sys.SysUser;

import core.dao.Dao;

/**
 * @author 郑为中
 */
public interface SysUserDao extends Dao<SysUser> {
	List<Object[]> queryExportedSysUserDao(Long[] ids);
}
