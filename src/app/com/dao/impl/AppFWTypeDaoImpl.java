package app.com.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import app.com.dao.AppFWTypeDao;
import app.com.model.AppFWType;

import core.dao.BaseDao;

/**
 * @author 郑为中
 */
@Repository
public class AppFWTypeDaoImpl extends BaseDao<AppFWType> implements AppFWTypeDao {

	public AppFWTypeDaoImpl() {
		super(AppFWType.class);
	}
	@Override
	public List<Object[]> queryExportedFWType(Long[] ids) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < ids.length; i++) {
			sb.append(ids[i] + ",");
		}
		Query query = getSession().createSQLQuery(
				"select f.name,f.remark from fw_type f where  f.id in (" + sb.deleteCharAt(sb.toString().length() - 1).toString() + ")");
		return query.list();
	}
}
