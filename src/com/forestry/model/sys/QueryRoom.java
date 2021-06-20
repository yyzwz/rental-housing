package com.forestry.model.sys;

import java.math.BigInteger;
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

import core.extjs.ExtJSBaseParameter;


public class QueryRoom extends RoomParameter {

	private static final long serialVersionUID = 6683458492945661067L;

	private BigInteger id;

	private String roomName; // 房间名称

	private String roomArea; // 房间面积

	private String roomDesc; // 房间描述

	private String roomTwoDimensionalCode; // 房间二维码照片

	private String roomImage; // 房间照片

	private BigInteger houseId; // 房屋ID

	private String houseName; //房屋名
	
	private String houseOwnerName;//房东姓名
    private BigInteger houseOwnerId;//房东ID

	public String getHouseOwnerName() {
		return houseOwnerName;
	}



	public void setHouseOwnerName(String houseOwnerName) {
		this.houseOwnerName = houseOwnerName;
	}



	public BigInteger getHouseOwnerId() {
		return houseOwnerId;
	}



	public void setHouseOwnerId(BigInteger houseOwnerId) {
		this.houseOwnerId = houseOwnerId;
	}

	private String checkOpion; //房屋名

	private Date registDate; // 注册日期

	private Date checkDate; // 审核日期

	private BigInteger checkerId; // 审核者ID

	private String checkerName; //审核姓名
	
	private String queryDepartmentName; //房屋名

	
	



	public BigInteger getId() {
		return id;
	}



	public void setId(BigInteger id) {
		this.id = id;
	}



	public String getQueryDepartmentName() {
		return queryDepartmentName;
	}



	public void setQueryDepartmentName(String queryDepartmentName) {
		this.queryDepartmentName = queryDepartmentName;
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



	

	public BigInteger getHouseId() {
		return houseId;
	}



	public void setHouseId(BigInteger houseId) {
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



	public BigInteger getCheckerId() {
		return checkerId;
	}



	public void setCheckerId(BigInteger checkerId) {
		this.checkerId = checkerId;
	}



	public String getCheckerName() {
		return checkerName;
	}



	public void setCheckerName(String checkerName) {
		this.checkerName = checkerName;
	}



	public QueryRoom() {

	}





	

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final QueryRoom other = (QueryRoom) obj;
		return Objects.equal(this.id, other.id) && Objects.equal(this.roomName, other.roomName) && Objects.equal(this.houseId, other.houseId);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.id, this.roomName);
	}

}
