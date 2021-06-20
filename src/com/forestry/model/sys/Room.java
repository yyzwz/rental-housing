package com.forestry.model.sys;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.forestry.model.sys.param.RoomParameter;
import com.google.common.base.Objects;

/**
 * @author 齐鸣鸣
 * @房间类型实体类
 */
@Entity
@Table(name = "room")
@DynamicInsert(true) 
@DynamicUpdate(true) 
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Room extends RoomParameter {

	private static final long serialVersionUID = 6683458492945661067L;
	@Id
	@GeneratedValue
	private Long id;
	@Column(name = "roomName", length = 50)
	private String roomName; // 房间名称
	@Column(name = "roomArea", length = 200)
	private String roomArea; // 房间面积
	@Column(name = "roomDesc", length = 200)
	private String roomDesc; // 房间描述
	@Column(name = "roomTwoDimensionalCode", length = 200)
	private String roomTwoDimensionalCode; // 房间二维码照片
	@Column(name = "roomImage", length = 200)
	private String roomImage; // 房间照片
	@Column(name = "houseId", length = 200)
	private Long houseId; // 房屋ID
	@Column(name = "houseName", length = 200)
	private String houseName; //房屋名
	
	@Column(name = "checkOpion", length = 200)
	private String checkOpion; //房屋名
	@Column(name = "registDate", length = 200)
	private Date registDate; // 注册日期
	@Column(name = "checkDate", length = 200)
	private Date checkDate; // 审核日期
	@Column(name = "checkerId", length = 200)
	private Long checkerId; // 审核者ID
	@Column(name = "checkerName", length = 200)
	private String checkerName; //审核姓名
	


	


	public void setHouseId(Long houseId) {
		this.houseId = houseId;
	}



	public String getRoomName() {
		return roomName;
	}



	public String getCheckOpion() {
		return checkOpion;
	}



	public void setCheckOpion(String checkOpion) {
		this.checkOpion = checkOpion;
	}



	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}



	public String getRoomArea() {
		return roomArea;
	}



	public void setRoomArea(String roomArea) {
		this.roomArea = roomArea;
	}



	public String getRoomDesc() {
		return roomDesc;
	}



	public void setRoomDesc(String roomDesc) {
		this.roomDesc = roomDesc;
	}



	public String getRoomTwoDimensionalCode() {
		return roomTwoDimensionalCode;
	}



	public void setRoomTwoDimensionalCode(String roomTwoDimensionalCode) {
		this.roomTwoDimensionalCode = roomTwoDimensionalCode;
	}



	public String getRoomImage() {
		return roomImage;
	}



	public void setRoomImage(String roomImage) {
		this.roomImage = roomImage;
	}



	public Long getHouseId() {
		return houseId;
	}



	public void setHouseID(Long houseId) {
		this.houseId = houseId;
	}



	public String getHouseName() {
		return houseName;
	}



	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}



	public Date getRegistDate() {
		return registDate;
	}



	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}



	public Date getCheckDate() {
		return checkDate;
	}



	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}



	public Long getCheckerId() {
		return checkerId;
	}



	public void setCheckerId(Long checkerId) {
		this.checkerId = checkerId;
	}



	public String getCheckerName() {
		return checkerName;
	}



	public void setCheckerName(String checkerName) {
		this.checkerName = checkerName;
	}



	public Room() {

	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Room other = (Room) obj;
		return Objects.equal(this.id, other.id) && Objects.equal(this.roomName, other.roomName) && Objects.equal(this.houseId, other.houseId);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.id, this.roomName);
	}

}
