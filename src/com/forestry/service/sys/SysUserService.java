package com.forestry.service.sys;

import java.util.List;

import com.forestry.model.sys.SysUser;

import core.service.Service;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public interface SysUserService extends Service<SysUser> {

	List<SysUser> getSysUserList(List<SysUser> resultList);
	List<Object[]> queryExportedSysUser(Long[] ids);
}
