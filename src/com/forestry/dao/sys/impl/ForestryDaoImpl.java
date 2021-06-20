package com.forestry.dao.sys.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.forestry.dao.sys.ForestryDao;
import com.forestry.model.sys.Forestry;

import core.dao.BaseDao;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Repository
public class ForestryDaoImpl extends BaseDao<Forestry> implements ForestryDao {

	public ForestryDaoImpl() {
		super(Forestry.class);
	}
	
	@Override
	public List<Object[]> queryExportedForestry(Long[] ids) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < ids.length; i++) {
			sb.append(ids[i] + ",");
		}
		Query query = getSession().createSQLQuery(
				"select f.epc_id,f.name fn,f.plant_time,f.entry_time,ft.name ftn from forestry_type ft,forestry f where ft.id = f.type_id and f.id in (" + sb.deleteCharAt(sb.toString().length() - 1).toString() + ")");
		return query.list();
	}

}
