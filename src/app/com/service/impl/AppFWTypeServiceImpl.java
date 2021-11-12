package app.com.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import app.com.dao.AppFWTypeDao;
import app.com.model.AppFWType;
import app.com.service.AppFWTypeService;

import core.service.BaseService;
import core.web.SystemCache;

/**
 * @author 郑为中
 */
@Service
public class AppFWTypeServiceImpl extends BaseService<AppFWType> implements AppFWTypeService {

	private AppFWTypeDao fWTypeDao;

	@Resource
	public void setSysUserDao(AppFWTypeDao fWTypeDao) {
		this.fWTypeDao = fWTypeDao;
		this.dao = fWTypeDao;
	}


	@Override
	public List<Object[]> queryExportedFWType(Long[] ids) {
		return fWTypeDao.queryExportedFWType(ids);
	}

}
