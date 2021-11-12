package com.forestry.service.sys;

import java.util.List;

import com.forestry.model.sys.SysUser;

import core.service.Service;

/**
 * @author 郑为中
 */
public interface SysUserService extends Service<SysUser> {

	List<SysUser> getSysUserList(List<SysUser> resultList);
	List<Object[]> queryExportedSysUser(Long[] ids);
}
