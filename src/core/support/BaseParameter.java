package core.support;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public class BaseParameter implements Serializable {

	private static final long serialVersionUID = -2050801753454734869L;

	public static final String SORTED_ASC = "ASC";
	public static final String SORTED_DESC = "DESC";
	/**
	 * count per page
	 */
	private Integer maxResults = 40;

	private Integer firstResult = 0;
	/**
	 * when needn't pagination,the top count of list
	 */
	private Integer topCount;
	/**
	 * 
	 */
	private String[] sortColumns;

	private String cmd;

	/**
	 * dynamic query conditions for example: queryConditions.put("_ne_id",value); when static and dynamic exists at the same time,static priority
	 */
	private Map<String, Object> queryDynamicConditions = new HashMap<String, Object>(4);

	/**
	 * sorted confiditions example : sortedConditions.put("id","ASC") means "order by id asc"
	 * 
	 * @return
	 */
	private Map<String, String> sortedConditions = new LinkedHashMap<String, String>(2);

	/**
	 * dynamic properties
	 */
	private Map<String, Object> dynamicProperties = new HashMap<String, Object>(4);

	public Integer getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
	}

	public Map<String, Object> getQueryDynamicConditions() {
		return queryDynamicConditions;
	}

	public void setQueryDynamicConditions(Map<String, Object> queryDynamicConditions) {
		this.queryDynamicConditions = queryDynamicConditions;
	}

	public Map<String, String> getSortedConditions() {
		return sortedConditions;
	}

	public void setSortedConditions(Map<String, String> sortedConditions) {
		this.sortedConditions = sortedConditions;
	}

	public Integer getTopCount() {
		return topCount;
	}

	public void setTopCount(Integer topCount) {
		this.topCount = topCount;
	}

	public String[] getSortColumns() {
		return sortColumns;
	}

	public Map<String, Object> getDynamicProperties() {
		return dynamicProperties;
	}

	public void setDynamicProperties(Map<String, Object> dynamicProperties) {
		this.dynamicProperties = dynamicProperties;
	}

	public String getSortColumnsString() {
		StringBuffer sb = new StringBuffer();
		if (sortColumns != null) {
			for (String s : sortColumns) {
				sb.append("&sortColumns=" + s);
			}
		}
		return sb.toString();
	}

	public void setSortColumns(String[] sortColumns) {
		this.sortColumns = sortColumns;
		if (sortColumns != null) {
			for (String s : sortColumns) {
				String[] sa = s.split("\\|");
				if (sa.length == 2)
					sortedConditions.put(sa[0], sa[1]);
			}
		}
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public Integer getFirstResult() {
		return firstResult;
	}

	public void setFirstResult(Integer firstResult) {
		this.firstResult = firstResult;
	}

}