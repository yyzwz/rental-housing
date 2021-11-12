package com.forestry.model.sys.param;

import core.extjs.ExtJSBaseParameter;

/**
 * @author 郑为中
 */
public class ConfigParameter extends ExtJSBaseParameter {

	private static final long serialVersionUID = -2197040433315922797L;
	private String configTypeName;

	public String getConfigTypeName() {
		return configTypeName;
	}

	public void setConfigTypeName(String configTypeName) {
		this.configTypeName = configTypeName;
	}

}
