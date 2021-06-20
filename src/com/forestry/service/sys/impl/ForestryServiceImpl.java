package com.forestry.service.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.forestry.dao.sys.AttachmentDao;
import com.forestry.dao.sys.ForestryDao;
import com.forestry.dao.sys.ForestryTypeDao;
import com.forestry.model.sys.Attachment;
import com.forestry.model.sys.Forestry;
import com.forestry.service.sys.ForestryService;

import core.service.BaseService;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Service
public class ForestryServiceImpl extends BaseService<Forestry> implements ForestryService {

	private ForestryDao forestryDao;
	@Resource
	private AttachmentDao attachmentDao;

	@Resource
	private ForestryTypeDao forestryTypeDao;

	@Resource
	public void setForestryDao(ForestryDao forestryDao) {
		this.forestryDao = forestryDao;
		this.dao = forestryDao;
	}

	@Override
	public List<Forestry> getForestryList(List<Forestry> resultList) {
		List<Forestry> forestryList = new ArrayList<Forestry>();
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String imagePath = request.getContextPath();
		for (Forestry entity : resultList) {
			Forestry forestry = new Forestry();
			forestry.setId(entity.getId());
			forestry.setEpcId(entity.getEpcId());
			forestry.setName(entity.getName());
			forestry.setPlantTime(entity.getPlantTime());
			forestry.setEntryTime(entity.getEntryTime());
			forestry.setForestryTypeName(forestryTypeDao.get(entity.getForestryType().getId()).getName());
			forestry.setForestryTypeId(entity.getForestryType().getId());

			List<Attachment> attachmentList = attachmentDao.queryByProerties("forestrytypeId", entity.getTypeId());
			StringBuilder sb = new StringBuilder("");
			for (int i = 0; i < attachmentList.size(); i++) {
				sb.append("<img src='" + imagePath + "/static/img/upload/" + attachmentList.get(i).getFilePath() + "' width = 300 height = 200 />");
			}

			forestry.setForestryTypeImagePath(sb.toString());
			forestryList.add(forestry);
		}
		return forestryList;
	}
	
	@Override
	public List<Object[]> queryExportedForestry(Long[] ids) {
		return forestryDao.queryExportedForestry(ids);
	}

}
