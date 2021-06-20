package com.forestry.model.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.forestry.model.sys.param.MonitorLogParameter;
import com.google.common.base.Objects;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 * @监控日志实体类
 */
@Entity
@Table(name = "monitor_log")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MonitorLog extends MonitorLogParameter {

	private static final long serialVersionUID = 2137862416068961975L;
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id; // ID
	@Column(name = "log_type")
	private Short logType; // 日志类型（1：树木失踪警报）
	@Column(name = "object_id")
	private Integer objectId; // 对象ID（例如：传感器ID）
	@Column(name = "value")
	private Double value; // 失联时间，单位为毫秒
	@Column(name = "message", length = 256)
	private String message; // 日志描述
	@Column(name = "disarm", columnDefinition = "int default 0 not null")
	private Boolean disarm; //是否解除警报

	public MonitorLog() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Short getLogType() {
		return logType;
	}

	public void setLogType(Short logType) {
		this.logType = logType;
	}

	public Integer getObjectId() {
		return objectId;
	}

	public void setObjectId(Integer objectId) {
		this.objectId = objectId;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean getDisarm() {
		return disarm;
	}

	public void setDisarm(Boolean disarm) {
		this.disarm = disarm;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final MonitorLog other = (MonitorLog) obj;
		return Objects.equal(this.id, other.id) && Objects.equal(this.logType, other.logType) && Objects.equal(this.objectId, other.objectId) && Objects.equal(this.value, other.value)
				&& Objects.equal(this.message, other.message) && Objects.equal(this.disarm, other.disarm);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.id, this.logType, this.objectId, this.value, this.message, this.disarm);
	}

}
