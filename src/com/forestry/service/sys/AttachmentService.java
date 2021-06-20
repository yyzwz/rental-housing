package com.forestry.service.sys;

import java.util.List;

import com.forestry.model.sys.Attachment;

import core.service.Service;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public interface AttachmentService extends Service<Attachment> {

	List<Object[]> queryFlowerList(String epcId);

	void deleteAttachmentByForestryTypeId(Long forestryTypeId);

}
