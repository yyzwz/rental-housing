package com.forestry.service.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.forestry.dao.sys.TenantDao;
import com.forestry.model.sys.Tenant;
import com.forestry.service.sys.TenantService;

import core.service.BaseService;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Service
public class TenantServiceImpl extends BaseService<Tenant> implements TenantService {

	private TenantDao TenantDao;
	

	@Resource
	public void setTenantDao(TenantDao TenantDao) {
		this.TenantDao = TenantDao;
		this.dao = TenantDao;
	}
	@Override
	public void updateTenant(Tenant Tenant) {
		TenantDao.updateQmm(Tenant);
	}
	@Override
	public int checkTenant(Long[] ids,String checkOption) {
		return TenantDao.CheckTenant(ids,checkOption);
	}
	//public int saveTenantTwoDimensionalCode(Long id);
	@Override
	public int saveTenantTwoDimensionalCode(HttpServletRequest request, String filePath,Long id) {
		return TenantDao.saveTenantTwoDimensionalCode( request, filePath,id);
	}
	@Override
	public List<Object[]> queryExportedTenant(Long[] ids) {
		return TenantDao.queryExportedTenant(ids);
	}

}
