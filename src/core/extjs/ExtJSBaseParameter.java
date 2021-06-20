package core.extjs;

import core.support.BaseParameter;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public class ExtJSBaseParameter extends BaseParameter {

	private static final long serialVersionUID = -6478237711699437927L;
	private Boolean success;
	private String message;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
