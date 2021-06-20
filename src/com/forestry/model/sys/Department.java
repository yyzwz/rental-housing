package com.forestry.model.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.forestry.model.sys.param.DepartmentParameter;
import com.google.common.base.Objects;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 * @部门实体类
 */
@Entity
@Table(name = "department")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Department extends DepartmentParameter {

	private static final long serialVersionUID = 6034294844559970211L;
	@Id
	@GeneratedValue
	private Long id;
	@Column(name = "name", length = 50)
	private String name; // 名称
	@Column(name = "remark", length = 100)
	private String remark; // 备注
	@Column(name = "jwd", length = 100)
	private String jwd; // 备注
	
	
	
	public String getJwd() {
		return jwd;
	}

	public void setJwd(String jwd) {
		this.jwd = jwd;
	}

	public Department() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Department other = (Department) obj;
		return Objects.equal(this.id, other.id) && Objects.equal(this.name, other.name) && Objects.equal(this.remark, other.remark);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.id, this.name, this.remark);
	}

}
