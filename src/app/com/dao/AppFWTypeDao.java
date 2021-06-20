package app.com.dao;

import java.util.List;

import org.hibernate.Query;

import app.com.model.AppFWType;

import core.dao.Dao;

/**
 * @author 齐鸣鸣
 */
public interface AppFWTypeDao extends Dao<AppFWType> {

	public List<Object[]> queryExportedFWType(Long[] ids);
}
