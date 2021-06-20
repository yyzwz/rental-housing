package com.forestry.service.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.forestry.dao.sys.ForestryTypeDao;
import com.forestry.model.sys.ForestryType;
import com.forestry.service.sys.ForestryTypeService;

import core.service.BaseService;
import core.util.HtmlUtils;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Service
public class ForestryTypeServiceImpl extends BaseService<ForestryType> implements ForestryTypeService {

	private ForestryTypeDao forestryTypeDao;

	@Resource
	public void setForestryTypeDao(ForestryTypeDao forestryTypeDao) {
		this.forestryTypeDao = forestryTypeDao;
		this.dao = forestryTypeDao;
	}

	@Override
	public List<ForestryType> getForestryTypeList(List<ForestryType> resultList) {
		List<ForestryType> forestryTypeList = new ArrayList<ForestryType>();
		for (ForestryType entity : resultList) {
			ForestryType forestryType = new ForestryType();
			forestryType.setId(entity.getId());
			forestryType.setName(entity.getName());
			forestryType.setDescription(entity.getDescription());
			forestryType.setDescriptionNoHtml(HtmlUtils.htmltoText(entity.getDescription()));
			forestryTypeList.add(forestryType);
		}
		return forestryTypeList;
	}

}
