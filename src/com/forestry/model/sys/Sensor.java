package com.forestry.model.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.forestry.model.sys.param.SensorParameter;
import com.google.common.base.Objects;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 * @传感器的坐标实体类
 */
@Entity
@Table(name = "sensor")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Sensor extends SensorParameter {

	private static final long serialVersionUID = 7877260411628783581L;
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id; // ID
	@Column(name = "sensor_id")
	private Integer sensorId; // 传感器ID
	@Column(name = "xcoordinate")
	private Double xcoordinate; // x坐标	
	@Column(name = "ycoordinate")
	private Double ycoordinate; // y坐标
	@Column(name = "epc_id", length = 100)
	private String epcId; // epc编码
	@Column(name = "type")
	private Short type; // 类型（1：真实传感器，2：虚拟传感器）

	public Sensor() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getSensorId() {
		return sensorId;
	}

	public void setSensorId(Integer sensorId) {
		this.sensorId = sensorId;
	}

	public Double getXcoordinate() {
		return xcoordinate;
	}

	public void setXcoordinate(Double xcoordinate) {
		this.xcoordinate = xcoordinate;
	}

	public Double getYcoordinate() {
		return ycoordinate;
	}

	public void setYcoordinate(Double ycoordinate) {
		this.ycoordinate = ycoordinate;
	}

	public String getEpcId() {
		return epcId;
	}

	public void setEpcId(String epcId) {
		this.epcId = epcId;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Sensor other = (Sensor) obj;
		return Objects.equal(this.id, other.id) && Objects.equal(this.sensorId, other.sensorId) && Objects.equal(this.xcoordinate, other.xcoordinate) && Objects.equal(this.ycoordinate, other.ycoordinate)
				&& Objects.equal(this.epcId, other.epcId) && Objects.equal(this.type, other.type);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.id, this.sensorId, this.xcoordinate, this.ycoordinate, this.epcId, this.type);
	}

}
