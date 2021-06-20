package com.forestry.model.sys.param;

import core.extjs.ExtJSBaseParameter;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public class ForestryTypeParameter extends ExtJSBaseParameter {

	private static final long serialVersionUID = -6335587468796360403L;
	private String property;
	private String direction;
	private String descriptionNoHtml;

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getDescriptionNoHtml() {
		return descriptionNoHtml;
	}

	public void setDescriptionNoHtml(String descriptionNoHtml) {
		this.descriptionNoHtml = descriptionNoHtml;
	}

}
