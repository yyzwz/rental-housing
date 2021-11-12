package com.forestry.dao.sys.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.forestry.dao.sys.SysUserDao;
import com.forestry.model.sys.SysUser;

import core.dao.BaseDao;

/**
 * @author 郑为中
 */
@Repository
public class SysUserDaoImpl extends BaseDao<SysUser> implements SysUserDao {

	public SysUserDaoImpl() {
		super(SysUser.class);
	}
	@Override
	public List<Object[]> queryExportedSysUserDao(Long[] ids) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < ids.length; i++) {
			sb.append(ids[i] + ",");
		}
		Query query = getSession().createSQLQuery(
				"select f.user_name,f.real_name,f.tel, f.email,f.last_logintime ,f.role from sys_user f where  f.id in (" + sb.deleteCharAt(sb.toString().length() - 1).toString() + ")");
		return query.list();
	}
}
