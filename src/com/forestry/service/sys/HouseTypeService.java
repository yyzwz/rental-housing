package com.forestry.service.sys;

import java.util.ArrayList;
import java.util.List;

import com.forestry.model.sys.HouseType;

import core.service.Service;
import core.util.HtmlUtils;

/**
 * @author 郑为中
 */
public interface HouseTypeService extends Service<HouseType> {
	List<Object[]> queryExportedHouseType(Long[] ids);
}
