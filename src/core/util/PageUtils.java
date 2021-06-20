package core.util;

public class PageUtils {
	private int maxResults;
	private int firstResult;
	private String sortColumns;
	private String cmd;
	private String[] queryDynamicConditions= {};
	private String[] sortedConditions= {};
	private String[] dynamicProperties= {};
	private String success;
	private String message;
	private String sortColumnsString;
	public int getMaxResults() {
		return maxResults;
	}
	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}
	public int getFirstResult() {
		return firstResult;
	}
	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}
	public String getSortColumns() {
		return sortColumns;
	}
	public void setSortColumns(String sortColumns) {
		this.sortColumns = sortColumns;
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public String[] getQueryDynamicConditions() {
		return queryDynamicConditions;
	}
	public void setQueryDynamicConditions(String[] queryDynamicConditions) {
		this.queryDynamicConditions = queryDynamicConditions;
	}
	public String[] getSortedConditions() {
		return sortedConditions;
	}
	public void setSortedConditions(String[] sortedConditions) {
		this.sortedConditions = sortedConditions;
	}
	public String[] getDynamicProperties() {
		return dynamicProperties;
	}
	public void setDynamicProperties(String[] dynamicProperties) {
		this.dynamicProperties = dynamicProperties;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSortColumnsString() {
		return sortColumnsString;
	}
	public void setSortColumnsString(String sortColumnsString) {
		this.sortColumnsString = sortColumnsString;
	}

	
}
