package app.com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import app.com.model.param.AppHouseAndRoomParameter;

/**
 * @author 郑为中
 * @房屋和房间的关系实体类
 */
@Entity
@Table(name = "house_room")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AppHouseAndRoom extends AppHouseAndRoomParameter{

private static final long serialVersionUID = 7034294844559970211L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "roomId", length = 20)
	private int RoomID; // 房间ID
	
	@Column(name = "houseID", length = 20)
	private int HouseID; // 房屋ID

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getRoomID() {
		return RoomID;
	}

	public void setRoomID(int roomID) {
		RoomID = roomID;
	}

	public int getHouseID() {
		return HouseID;
	}

	public void setHouseID(int houseID) {
		HouseID = houseID;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + HouseID;
		result = prime * result + RoomID;
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
		AppHouseAndRoom other = (AppHouseAndRoom) obj;
		if (HouseID != other.HouseID)
			return false;
		if (RoomID != other.RoomID)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
