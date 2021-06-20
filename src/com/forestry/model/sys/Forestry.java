package com.forestry.model.sys;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.forestry.model.sys.param.ForestryParameter;
import com.google.common.base.Objects;

import core.extjs.DateTimeSerializer;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 * @树木信息实体类
 */
@Entity
@Table(name = "forestry")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Forestry extends ForestryParameter {

	private static final long serialVersionUID = -1659957832585685551L;
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id; // ID
	@Column(name = "epc_id", length = 100, nullable = false)
	private String epcId; // epc编码
	@Column(name = "name", length = 200, nullable = false)
	private String name; // 名称
	@Column(name = "plant_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date plantTime; // 种植时间
	@Column(name = "entry_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date entryTime; // 入园时间
	@ManyToOne
	@JoinColumn(name = "type_id", nullable = false)
	private ForestryType forestryType; // 种类
	@Column(name = "type_id", insertable = false, updatable = false)
	private Long typeId; // 拼凑SQL，不会持久化到数据库

	public Forestry() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEpcId() {
		return epcId;
	}

	public void setEpcId(String epcId) {
		this.epcId = epcId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getPlantTime() {
		return plantTime;
	}

	public void setPlantTime(Date plantTime) {
		this.plantTime = plantTime;
	}

	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}

	public ForestryType getForestryType() {
		return forestryType;
	}

	public void setForestryType(ForestryType forestryType) {
		this.forestryType = forestryType;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Forestry other = (Forestry) obj;
		return Objects.equal(this.id, other.id) && Objects.equal(this.epcId, other.epcId) && Objects.equal(this.name, other.name) && Objects.equal(this.plantTime, other.plantTime)
				&& Objects.equal(this.entryTime, other.entryTime) && Objects.equal(this.forestryType, other.forestryType);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.id, this.epcId, this.name, this.plantTime, this.entryTime, this.forestryType);
	}

}
