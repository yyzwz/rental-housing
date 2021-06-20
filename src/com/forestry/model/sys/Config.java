package com.forestry.model.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.forestry.model.sys.param.ConfigParameter;
import com.google.common.base.Objects;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 * @系统设置实体类
 */
@Entity
@Table(name = "config")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Config extends ConfigParameter {

	private static final long serialVersionUID = 3318045462222940089L;
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id; // ID
	@Column(name = "config_type", nullable = false)
	private Short configType; // 配置类型
	@Column(name = "config_value")
	private Integer configValue; // 配置值

	public Config() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Short getConfigType() {
		return configType;
	}

	public void setConfigType(Short configType) {
		this.configType = configType;
	}

	public Integer getConfigValue() {
		return configValue;
	}

	public void setConfigValue(Integer configValue) {
		this.configValue = configValue;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Config other = (Config) obj;
		return Objects.equal(this.id, other.id) && Objects.equal(this.configType, other.configType) && Objects.equal(this.configValue, other.configValue);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.id, this.configType, this.configValue);
	}

}
