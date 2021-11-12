package com.forestry.dao.sys.impl;

import org.springframework.stereotype.Repository;

import com.forestry.dao.sys.DepartmentDao;
import com.forestry.model.sys.Department;

import core.dao.BaseDao;

/**
 * @author 郑为中
 */
@Repository
public class DepartmentDaoImpl extends BaseDao<Department> implements DepartmentDao {

	public DepartmentDaoImpl() {
		super(Department.class);
	}

}
