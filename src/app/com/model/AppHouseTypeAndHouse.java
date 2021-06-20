package app.com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import app.com.model.param.AppHouseTypeAndHouseParameter;

/**
 * @author 郑为中
 * @房屋类型和房屋的实体关系类
 */
@Entity
@Table(name = "housetype_house")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AppHouseTypeAndHouse extends AppHouseTypeAndHouseParameter {

private static final long serialVersionUID = 7034204844559970201L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "houseTypeId", length = 20)
	private Long HouseTypeId; // 房屋类型ID
	
	@Column(name = "houseId", length = 20)
	private Long HouseId; // 房屋ID

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getHouseTypeId() {
		return HouseTypeId;
	}

	public void setHouseTypeId(Long houseTypeId) {
		HouseTypeId = houseTypeId;
	}

	public Long getHouseId() {
		return HouseId;
	}

	public void setHouseId(Long houseId) {
		HouseId = houseId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((HouseId == null) ? 0 : HouseId.hashCode());
		result = prime * result + ((HouseTypeId == null) ? 0 : HouseTypeId.hashCode());
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
		AppHouseTypeAndHouse other = (AppHouseTypeAndHouse) obj;
		if (HouseId == null) {
			if (other.HouseId != null)
				return false;
		} else if (!HouseId.equals(other.HouseId))
			return false;
		if (HouseTypeId == null) {
			if (other.HouseTypeId != null)
				return false;
		} else if (!HouseTypeId.equals(other.HouseTypeId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
}
