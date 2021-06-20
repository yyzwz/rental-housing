package app.com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import app.com.model.param.AppUserAndHouseParameter;

/**
 * @author 郑为中
 * @房东实体类
 */
@Entity
@Table(name = "houseowner_house")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AppUserAndHouse extends AppUserAndHouseParameter {

private static final long serialVersionUID = 7034294844559971211L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "houseOwnerId", length = 20)
	private Long userID; // 房东ID

	@Column(name = "houseId", length = 200)
	private Long HouseID; // 房屋ID

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public Long getHouseID() {
		return HouseID;
	}

	public void setHouseID(Long houseID) {
		HouseID = houseID;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((HouseID == null) ? 0 : HouseID.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((userID == null) ? 0 : userID.hashCode());
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
		AppUserAndHouse other = (AppUserAndHouse) obj;
		if (HouseID == null) {
			if (other.HouseID != null)
				return false;
		} else if (!HouseID.equals(other.HouseID))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (userID == null) {
			if (other.userID != null)
				return false;
		} else if (!userID.equals(other.userID))
			return false;
		return true;
	}

	
	
	
}
