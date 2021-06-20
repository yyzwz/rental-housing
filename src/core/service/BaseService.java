package core.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.forestry.model.sys.SysUser;

import core.dao.Dao;
import core.support.BaseParameter;
import core.support.QueryResult;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Transactional
public class BaseService<E> implements Service<E> {

	protected Dao<E> dao;

	public void persist(E entity) {
		dao.persist(entity);
	}

	public boolean deleteByPK(Serializable... id) {
		return dao.deleteByPK(id);
	}

	public void delete(E entity) {
		dao.delete(entity);
	}

	public void deleteByProperties(String[] propName, Object[] propValue) {
		dao.deleteByProperties(propName, propValue);
	}

	public void deleteByProperties(String propName, Object propValue) {
		dao.deleteByProperties(propName, propValue);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public E get(Serializable id) {
		return dao.get(id);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public E getByProerties(String[] propName, Object[] propValue) {
		return dao.getByProerties(propName, propValue);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public E getByProerties(String propName, Object propValue) {
		return dao.getByProerties(propName, propValue);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public E getByProerties(String[] propName, Object[] propValue, Map<String, String> sortedCondition) {
		return dao.getByProerties(propName, propValue, sortedCondition);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public E getByProerties(String propName, Object propValue, Map<String, String> sortedCondition) {
		return dao.getByProerties(propName, propValue, sortedCondition);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public E load(Serializable id) {
		return dao.load(id);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<E> queryByProerties(String[] propName, Object[] propValue) {
		return dao.queryByProerties(propName, propValue);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<E> queryByProerties(String propName, Object propValue) {
		return dao.queryByProerties(propName, propValue);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<E> queryByProerties(String[] propName, Object[] propValue, Map<String, String> sortedCondition) {
		return dao.queryByProerties(propName, propValue, sortedCondition);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<E> queryByProerties(String propName, Object propValue, Map<String, String> sortedCondition) {
		return dao.queryByProerties(propName, propValue, sortedCondition);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<E> queryByProerties(String[] propName, Object[] propValue, Map<String, String> sortedCondition, Integer top) {
		return dao.queryByProerties(propName, propValue, sortedCondition, top);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<E> queryByProerties(String[] propName, Object[] propValue, Integer top) {
		return dao.queryByProerties(propName, propValue, top);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<E> queryByProerties(String propName, Object propValue, Map<String, String> sortedCondition, Integer top) {
		return dao.queryByProerties(propName, propValue, sortedCondition, top);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<E> queryByProerties(String propName, Object propValue, Integer top) {
		return dao.queryByProerties(propName, propValue, top);
	}

	public E merge(E entity) {
		return dao.merge(entity);
	}

	public void update(E entity) {
		dao.update(entity);
	}

	public void updateByProperties(String[] conditionName, Object[] conditionValue, String[] propertyName, Object[] propertyValue) {
		dao.updateByProperties(conditionName, conditionValue, propertyName, propertyValue);
	}

	public void updateByProperties(String conditionName, Object conditionValue, String[] propertyName, Object[] propertyValue) {
		dao.updateByProperties(conditionName, conditionValue, propertyName, propertyValue);
	}

	public void updateByProperties(String[] conditionName, Object[] conditionValue, String propertyName, Object propertyValue) {
		dao.updateByProperties(conditionName, conditionValue, propertyName, propertyValue);
	}

	public void updateByProperties(String conditionName, Object conditionValue, String propertyName, Object propertyValue) {
		dao.updateByProperties(conditionName, conditionValue, propertyName, propertyValue);
	}

	public void update(E entity, Serializable oldId) {
		dao.update(entity, oldId);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<E> doQueryAll() {
		return dao.doQueryAll();
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<E> doQueryAll(Map<String, String> sortedCondition, Integer top) {
		return dao.doQueryAll(sortedCondition, top);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<E> doQueryAll(Integer top) {
		return dao.doQueryAll(top);
	}

	public void evict(E entity) {
		dao.evict(entity);
	}

	public void clear() {
		dao.clear();
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public Long countAll() {
		return dao.countAll();
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public Long doCount(BaseParameter parameter) {
		return dao.doCount(parameter);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<E> doQuery(BaseParameter parameter) {
		return dao.doQuery(parameter);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public QueryResult<E> doPaginationQuery(BaseParameter parameter) {
		return dao.doPaginationQuery(parameter);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public QueryResult<E> doPaginationQuery(BaseParameter parameter, boolean bool) {
		return dao.doPaginationQuery(parameter, bool);
	}


	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<BaseParameter> creatNativeSqlQuery(String sql, BaseParameter param) {
		// TODO Auto-generated method stub
		return  (List<BaseParameter>) dao.creatNativeSqlQuery(sql, param);
	}
	

	@Transactional
	public int check(Long[] ids, String checkOption, SysUser sysUser) {
		return dao.Check(ids, checkOption, sysUser);
	}
}