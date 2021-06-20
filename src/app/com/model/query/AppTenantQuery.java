package app.com.model.query;

import core.support.BaseParameter;

public class AppTenantQuery extends BaseParameter{

	private String departmentName;

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
	
}
