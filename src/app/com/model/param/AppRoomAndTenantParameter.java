package app.com.model.param;

import core.extjs.ExtJSBaseParameter;

public class AppRoomAndTenantParameter extends ExtJSBaseParameter {
	private static final long serialVersionUID = 6683452492945661067L;
	
	private String startDateWithString;
	private String endDateWithString;
	public String getStartDateWithString() {
		return startDateWithString;
	}
	public void setStartDateWithString(String startDateWithString) {
		this.startDateWithString = startDateWithString;
	}
	public String getEndDateWithString() {
		return endDateWithString;
	}
	public void setEndDateWithString(String endDateWithString) {
		this.endDateWithString = endDateWithString;
	}
	
	
}
