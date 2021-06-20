package com.forestry.dao.sys;

import java.util.List;

import com.forestry.model.sys.Attachment;

import core.dao.Dao;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public interface AttachmentDao extends Dao<Attachment> {

	List<Object[]> queryFlowerList(String epcId);

	void deleteAttachmentByForestryTypeId(Long forestryTypeId);

}
