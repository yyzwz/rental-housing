package app.com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author 郑为中
 * @村落实体类
 */
@Entity
@Table(name = "department")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AppDepartment {
private static final long serialVersionUID = 9034294844559970211L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "name", length = 200)
	private String Name; // 村名
	
	@Column(name = "remark", length = 200)
	private String Remark; // 村介绍

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Name == null) ? 0 : Name.hashCode());
		result = prime * result + ((Remark == null) ? 0 : Remark.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AppDepartment other = (AppDepartment) obj;
		if (Name == null) {
			if (other.Name != null)
				return false;
		} else if (!Name.equals(other.Name))
			return false;
		if (Remark == null) {
			if (other.Remark != null)
				return false;
		} else if (!Remark.equals(other.Remark))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
}
