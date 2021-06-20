package app.com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import app.com.model.param.AppUserParameter;

/**
 * @author 郑为中
 * @房间实体类
 */
@Entity
@Table(name = "room")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AppRoom extends AppUserParameter {
	
private static final long serialVersionUID = 7034294144559970211L;
	
	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "roomName", length = 255)
	private String RoomName; // 房间名称
	

	@Column(name = "roomArea", length = 200)
	private String RoomArea; // 房间面积
	

	@Column(name = "roomDesc", length = 255)
	private String RoomDesc; // 房间简介
	

	@Column(name = "roomTwoDimensionalCode", length = 200)
	private String RoomTwoDimensionalCode; // 房间二维码
	

	@Column(name = "roomImage", length = 200)
	private String RoomImage; // 房间照片
	
	
	@Column(name = "houseID", length = 20)
	private Long HouseID; // 房间二维码
	

	@Column(name = "houseName", length = 255)
	private String HouseName; // 房间照片


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getRoomName() {
		return RoomName;
	}


	public void setRoomName(String roomName) {
		RoomName = roomName;
	}


	public String getRoomArea() {
		return RoomArea;
	}


	public void setRoomArea(String roomArea) {
		RoomArea = roomArea;
	}


	public String getRoomDesc() {
		return RoomDesc;
	}


	public void setRoomDesc(String roomDesc) {
		RoomDesc = roomDesc;
	}


	public String getRoomTwoDimensionalCode() {
		return RoomTwoDimensionalCode;
	}


	public void setRoomTwoDimensionalCode(String roomTwoDimensionalCode) {
		RoomTwoDimensionalCode = roomTwoDimensionalCode;
	}


	public String getRoomImage() {
		return RoomImage;
	}


	public void setRoomImage(String roomImage) {
		RoomImage = roomImage;
	}


	public Long getHouseID() {
		return HouseID;
	}


	public void setHouseID(Long houseID) {
		HouseID = houseID;
	}


	public String getHouseName() {
		return HouseName;
	}


	public void setHouseName(String houseName) {
		HouseName = houseName;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((HouseID == null) ? 0 : HouseID.hashCode());
		result = prime * result + ((HouseName == null) ? 0 : HouseName.hashCode());
		result = prime * result + ((RoomArea == null) ? 0 : RoomArea.hashCode());
		result = prime * result + ((RoomDesc == null) ? 0 : RoomDesc.hashCode());
		result = prime * result + ((RoomImage == null) ? 0 : RoomImage.hashCode());
		result = prime * result + ((RoomName == null) ? 0 : RoomName.hashCode());
		result = prime * result + ((RoomTwoDimensionalCode == null) ? 0 : RoomTwoDimensionalCode.hashCode());
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
		AppRoom other = (AppRoom) obj;
		if (HouseID == null) {
			if (other.HouseID != null)
				return false;
		} else if (!HouseID.equals(other.HouseID))
			return false;
		if (HouseName == null) {
			if (other.HouseName != null)
				return false;
		} else if (!HouseName.equals(other.HouseName))
			return false;
		if (RoomArea == null) {
			if (other.RoomArea != null)
				return false;
		} else if (!RoomArea.equals(other.RoomArea))
			return false;
		if (RoomDesc == null) {
			if (other.RoomDesc != null)
				return false;
		} else if (!RoomDesc.equals(other.RoomDesc))
			return false;
		if (RoomImage == null) {
			if (other.RoomImage != null)
				return false;
		} else if (!RoomImage.equals(other.RoomImage))
			return false;
		if (RoomName == null) {
			if (other.RoomName != null)
				return false;
		} else if (!RoomName.equals(other.RoomName))
			return false;
		if (RoomTwoDimensionalCode == null) {
			if (other.RoomTwoDimensionalCode != null)
				return false;
		} else if (!RoomTwoDimensionalCode.equals(other.RoomTwoDimensionalCode))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
}
