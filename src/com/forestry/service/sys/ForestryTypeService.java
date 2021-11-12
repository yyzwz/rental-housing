package com.forestry.service.sys;

import java.util.List;

import com.forestry.model.sys.ForestryType;

import core.service.Service;

/**
 * @author 郑为中
 */
public interface ForestryTypeService extends Service<ForestryType> {

	List<ForestryType> getForestryTypeList(List<ForestryType> resultList);

}
