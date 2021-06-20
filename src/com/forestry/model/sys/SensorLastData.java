package com.forestry.model.sys;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.forestry.model.sys.param.SensorLastDataParameter;
import com.google.common.base.Objects;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 * @传感器的温度、湿度和光照度最新数据实体类
 */
@Entity
@Table(name = "sensor_lastdata")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SensorLastData extends SensorLastDataParameter {

	private static final long serialVersionUID = -1760923157506325485L;
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id; // ID
	@Column(name = "sensor_id")
	private Integer sensorId; // 传感器ID
	@Column(name = "sensor_type")
	private Short sensorType; // 传感器类型（1：温度℃，2：湿度%，3：光照度lx）
	@Column(name = "sensor_lastvalue")
	private Double sensorLastValue; // 传感器值		
	@Column(name = "update_time")
	private Timestamp updateTime; // 更新时间

	public SensorLastData() {

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

	public Short getSensorType() {
		return sensorType;
	}

	public void setSensorType(Short sensorType) {
		this.sensorType = sensorType;
	}

	public Double getSensorLastValue() {
		return sensorLastValue;
	}

	public void setSensorLastValue(Double sensorLastValue) {
		this.sensorLastValue = sensorLastValue;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final SensorLastData other = (SensorLastData) obj;
		return Objects.equal(this.id, other.id) && Objects.equal(this.sensorId, other.sensorId) && Objects.equal(this.sensorType, other.sensorType) && Objects.equal(this.sensorLastValue, other.sensorLastValue)
				&& Objects.equal(this.updateTime, other.updateTime);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.id, this.sensorId, this.sensorType, this.sensorLastValue, this.updateTime);
	}

}
