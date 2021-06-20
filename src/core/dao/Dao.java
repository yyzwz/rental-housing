package core.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.forestry.model.sys.SysUser;

import core.support.BaseParameter;
import core.support.QueryResult;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public interface Dao<E> {

	/**
	 * Persist object
	 * 
	 * @param entity
	 */
	public void persist(E entity);

	/**
	 * @param id
	 * @return
	 */
	public boolean deleteByPK(Serializable... id);

	/**
	 * Remove a persistent instance
	 * 
	 * @param entity
	 */
	public void delete(E entity);

	/**
	 * delete entity by property though hql
	 * 
	 * @param propName
	 * @param propValue
	 */
	public void deleteByProperties(String propName, Object propValue);

	/**
	 * delete entity by properties though hql
	 * 
	 * @param propName
	 * @param propValue
	 */
	public void deleteByProperties(String[] propName, Object[] propValue);

	/**
	 * Update the persistent instance with the identifier of the given detached instance.
	 * 
	 * @param entity
	 */
	public void update(E entity);
	
	/**
	 * Update the persistent instance with the identifier of the given detached instance.
	 * 
	 * @param entity
	 */
	public void updateQmm(E entity);

	/**
	 * update property batch
	 * 
	 * @param conditionName where clause condiction property name
	 * @param conditionValue where clause condiction property value
	 * @param propertyName update clause property name array
	 * @param propertyValue update clase property value array
	 */
	public void updateByProperties(String[] conditionName, Object[] conditionValue, String[] propertyName, Object[] propertyValue);

	public void updateByProperties(String[] conditionName, Object[] conditionValue, String propertyName, Object propertyValue);

	public void updateByProperties(String conditionName, Object conditionValue, String[] propertyName, Object[] propertyValue);

	public void updateByProperties(String conditionName, Object conditionValue, String propertyName, Object propertyValue);

	/**
	 * cautiously use this method, through delete then insert to update an entity when need to update primary key value (unsupported) use this method
	 * 
	 * @param entity updated entity
	 * @param oldId already existed primary key
	 */
	public void update(E entity, Serializable oldId);

	/**
	 * Merge the state of the given entity into the current persistence context.
	 * 
	 * @param entity
	 */
	public E merge(E entity);

	/**
	 * Get persistent object
	 * 
	 * @param id
	 * @return
	 */
	public E get(Serializable id);

	/**
	 * load persistent object
	 * 
	 * @param id
	 * @return
	 */
	public E load(Serializable id);

	/**
	 * get an entity by properties
	 * 
	 * @param propName
	 * @param propValue
	 * @return
	 */
	public E getByProerties(String[] propName, Object[] propValue);

	public E getByProerties(String[] propName, Object[] propValue, Map<String, String> sortedCondition);

	/**
	 * get an entity by property
	 * 
	 * @param propName
	 * @param propValue
	 * @return
	 */
	public E getByProerties(String propName, Object propValue);

	public E getByProerties(String propName, Object propValue, Map<String, String> sortedCondition);

	/**
	 * query by property
	 * 
	 * @param propName
	 * @param propValue
	 * @return
	 */
	public List<E> queryByProerties(String[] propName, Object[] propValue, Map<String, String> sortedCondition, Integer top);

	public List<E> queryByProerties(String[] propName, Object[] propValue, Map<String, String> sortedCondition);

	public List<E> queryByProerties(String[] propName, Object[] propValue, Integer top);

	public List<E> queryByProerties(String[] propName, Object[] propValue);

	public List<E> queryByProerties(String propName, Object propValue, Map<String, String> sortedCondition, Integer top);

	public List<E> queryByProerties(String propName, Object propValue, Map<String, String> sortedCondition);

	public List<E> queryByProerties(String propName, Object propValue, Integer top);

	public List<E> queryByProerties(String propName, Object propValue);

	/**
	 * Completely clear the session.
	 */
	public void clear();

	/**
	 * Remove this instance from the session cache.
	 */
	public void evict(E entity);

	/**
	 * count all
	 * 
	 * @return
	 */
	public Long countAll();

	/**
	 * Query all
	 * 
	 * @return
	 */
	public List<E> doQueryAll();

	public List<E> doQueryAll(Map<String, String> sortedCondition, Integer top);

	public List<E> doQueryAll(Integer top);

	public Long doCount(BaseParameter parameter);

	public List<E> doQuery(BaseParameter parameter);

	public QueryResult<E> doPaginationQuery(BaseParameter parameter);

	public QueryResult<E> doPaginationQuery(BaseParameter parameter, boolean bool);
	
	public List<E> creatNativeSqlQuery(String sql,BaseParameter param);


	public int Check(Long[] ids, String checkOption, SysUser sysUser);
}
