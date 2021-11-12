package com.forestry.model.sys;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.forestry.model.sys.param.HouseTypeParameter;
import com.google.common.base.Objects;

/**
 * @author 郑为中
 * @房屋类型实体类
 */
@Entity
@Table(name = "housetype")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class HouseType extends HouseTypeParameter {

	private static final long serialVersionUID = 6683458492945661067L;
	@Id
	@GeneratedValue
	private Long id;
	@Column(name = "houseTypeName", length = 50)
	private String houseTypeName; // 名称
	@Column(name = "houseTypeDesc", length = 200)
	private String houseTypeDesc; // 备注

	
	public HouseType() {

	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHouseTypeName() {
		return houseTypeName;
	}

	public void setHouseTypeName(String houseTypeName) {
		this.houseTypeName = houseTypeName;
	}

	public String getHouseTypeDesc() {
		return houseTypeDesc;
	}

	public void setHouseTypeDesc(String houseTypeDesc) {
		this.houseTypeDesc = houseTypeDesc;
	}


	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final HouseType other = (HouseType) obj;
		return Objects.equal(this.id, other.id) && Objects.equal(this.houseTypeName, other.houseTypeName) && Objects.equal(this.houseTypeDesc, other.houseTypeDesc);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.id, this.houseTypeName, this.houseTypeDesc);
	}

}
