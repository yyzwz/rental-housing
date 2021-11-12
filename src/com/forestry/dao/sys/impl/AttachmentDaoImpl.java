package com.forestry.dao.sys.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.forestry.dao.sys.AttachmentDao;
import com.forestry.model.sys.Attachment;

import core.dao.BaseDao;

/**
 * @author 郑为中
 */
@Repository
public class AttachmentDaoImpl extends BaseDao<Attachment> implements AttachmentDao {

	public AttachmentDaoImpl() {
		super(Attachment.class);
	}

	@Override
	public List<Object[]> queryFlowerList(String epcId) {
		Query query = this.getSession().createSQLQuery("select ft.name,group_concat(a.file_path),ft.description,f.epc_id from forestry_type ft inner join forestry f on ft.id=f.type_id left join attachment a on a.forestrytype_id=ft.id where f.epc_id=? group by a.file_name");
		query.setParameter(0, epcId);
		return query.list();
	}

	@Override
	public void deleteAttachmentByForestryTypeId(Long forestryTypeId) {
		Query query = this.getSession().createSQLQuery("delete from attachment where forestrytype_id = :forestryTypeId");
		query.setParameter("forestryTypeId", forestryTypeId);
		query.executeUpdate();
	}

}
