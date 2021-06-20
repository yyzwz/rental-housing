package com.forestry.dao.sys.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.forestry.dao.sys.HouseTypeDao;
import com.forestry.model.sys.HouseType;

import core.dao.BaseDao;

/**
 * @author 齐鸣鸣
 */
@Repository
public class HouseTypeDaoImpl extends BaseDao<HouseType> implements HouseTypeDao {

	public HouseTypeDaoImpl() {
		super(HouseType.class);
	}
	@Override
	public List<Object[]> queryExportedHouseType(Long[] ids) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < ids.length; i++) {
			sb.append(ids[i] + ",");
		}
		Query query = getSession().createSQLQuery(
				"select f.HouseTypeName,f.HouseTypeDesc from housetype f where  f.id in (" + sb.deleteCharAt(sb.toString().length() - 1).toString() + ")");
		return query.list();
	}
}
