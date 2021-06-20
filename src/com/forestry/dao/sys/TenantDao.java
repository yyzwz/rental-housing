package com.forestry.dao.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.forestry.model.sys.Tenant;

import core.dao.Dao;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public interface TenantDao extends Dao<Tenant> {
	
	List<Object[]> queryExportedTenant(Long[] ids);

	int CheckTenant(Long[] ids,String checkOption);
    void updateTenant(Tenant Tenant);
    int saveTenantTwoDimensionalCode(HttpServletRequest request, String filePath,Long id);//saveTenantTwoDimensionalCode
}
