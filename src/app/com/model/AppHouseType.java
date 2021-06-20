package app.com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import app.com.model.param.AppHouseTypeParameter;

/**
 * @author 郑为中
 * @房屋类型实体类(正版)
 */
@Entity
@Table(name = "housetype")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AppHouseType extends AppHouseTypeParameter {

private static final long serialVersionUID = 7034294844559970211L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "houseTypeName", length = 50)
	private String HouseTypeName; // 房屋类型名
	
	@Column(name = "houseTypeDesc", length = 200)
	private String HouseTypeDesc; // 房屋描述

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHouseTypeName() {
		return HouseTypeName;
	}

	public void setHouseTypeName(String houseTypeName) {
		HouseTypeName = houseTypeName;
	}

	public String getHouseTypeDesc() {
		return HouseTypeDesc;
	}

	public void setHouseTypeDesc(String houseTypeDesc) {
		HouseTypeDesc = houseTypeDesc;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((HouseTypeDesc == null) ? 0 : HouseTypeDesc.hashCode());
		result = prime * result + ((HouseTypeName == null) ? 0 : HouseTypeName.hashCode());
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
		AppHouseType other = (AppHouseType) obj;
		if (HouseTypeDesc == null) {
			if (other.HouseTypeDesc != null)
				return false;
		} else if (!HouseTypeDesc.equals(other.HouseTypeDesc))
			return false;
		if (HouseTypeName == null) {
			if (other.HouseTypeName != null)
				return false;
		} else if (!HouseTypeName.equals(other.HouseTypeName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
