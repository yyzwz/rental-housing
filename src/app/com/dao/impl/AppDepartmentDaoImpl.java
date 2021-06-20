package app.com.dao.impl;

import org.springframework.stereotype.Repository;

import app.com.dao.AppDepartmentDao;
import app.com.model.AppDepartment;
import core.dao.BaseDao;

/**
 * @author 郑为中
 */
@Repository
public class AppDepartmentDaoImpl extends BaseDao<AppDepartment> implements AppDepartmentDao {

	public AppDepartmentDaoImpl() {
		super(AppDepartment.class);
	}
}
