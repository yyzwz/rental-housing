package com.forestry.model.sys.param;

import core.extjs.ExtJSBaseParameter;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public class SensorDataParameter extends ExtJSBaseParameter {

	private static final long serialVersionUID = 2484290856206136745L;
	private String temperature;
	private String humidity;
	private String lightfall;

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getLightfall() {
		return lightfall;
	}

	public void setLightfall(String lightfall) {
		this.lightfall = lightfall;
	}

}
