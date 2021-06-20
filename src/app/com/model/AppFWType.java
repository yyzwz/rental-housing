package app.com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.google.common.base.Objects;

import app.com.model.param.AppFWTypeParameter;

/**
 * @author 齐鸣鸣
 * @房屋类型实体类
 */
@Entity
@Table(name = "department")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AppFWType extends AppFWTypeParameter {

	private static final long serialVersionUID = 6034294844559970211L;
	@Id
	@GeneratedValue
	private Long id;
	@Column(name = "name", length = 50)
	private String name; // 名称
	@Column(name = "remark", length = 200)
	private String remark; // 备注

	public AppFWType() {

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
		final AppFWType other = (AppFWType) obj;
		return Objects.equal(this.id, other.id) && Objects.equal(this.name, other.name) && Objects.equal(this.remark, other.remark);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.id, this.name, this.remark);
	}

}
