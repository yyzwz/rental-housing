package com.forestry.service.sys;

import java.util.List;

import com.forestry.model.sys.Forestry;

import core.service.Service;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public interface ForestryService extends Service<Forestry> {

	List<Forestry> getForestryList(List<Forestry> resultList);
	
	List<Object[]> queryExportedForestry(Long[] ids);

}
