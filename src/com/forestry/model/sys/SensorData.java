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

import com.forestry.model.sys.param.SensorDataParameter;
import com.google.common.base.Objects;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 * @传感器的温度、湿度和光照度历史数据实体类
 */
@Entity
@Table(name = "sensor_data")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SensorData extends SensorDataParameter {

	private static final long serialVersionUID = 5660086974005263060L;
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id; // ID
	@Column(name = "sensor_id")
	private Integer sensorId; // 传感器ID
	@Column(name = "sensor_type")
	private Short sensorType; // 传感器类型（1：温度℃，2：湿度%，3：光照度lx）
	@Column(name = "sensor_value")
	private Double sensorValue; // 传感器值		
	@Column(name = "record_time")
	private Timestamp recordTime; // 录入时间

	public SensorData() {

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

	public Double getSensorValue() {
		return sensorValue;
	}

	public void setSensorValue(Double sensorValue) {
		this.sensorValue = sensorValue;
	}

	public Timestamp getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Timestamp recordTime) {
		this.recordTime = recordTime;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final SensorData other = (SensorData) obj;
		return Objects.equal(this.id, other.id) && Objects.equal(this.sensorId, other.sensorId) && Objects.equal(this.sensorType, other.sensorType) && Objects.equal(this.sensorValue, other.sensorValue)
				&& Objects.equal(this.recordTime, other.recordTime);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.id, this.sensorId, this.sensorType, this.sensorValue, this.recordTime);
	}

}
