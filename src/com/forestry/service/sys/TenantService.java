package com.forestry.service.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.forestry.model.sys.Tenant;

import core.service.Service;

/**
 * @author 郑为中
 */
public interface TenantService extends Service<Tenant> {

	List<Object[]> queryExportedTenant(Long[] ids);
	public int checkTenant(Long[] ids,String checkOption);
	public int saveTenantTwoDimensionalCode(HttpServletRequest request, String filePath,Long id);
	public void updateTenant(Tenant Tenant);
	
}
