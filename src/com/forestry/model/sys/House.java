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

import com.forestry.model.sys.param.HouseParameter;
import com.google.common.base.Objects;

/**
 * @author 齐鸣鸣
 * @房屋类型实体类
 */
@Entity
@Table(name = "house")
@DynamicInsert(true) 
@DynamicUpdate(true) 
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class House extends HouseParameter {

	private static final long serialVersionUID = 6683458492945661067L;
	@Id
	@GeneratedValue
	private Long id;
	@Column(name = "houseAddress", length = 50)
	private String houseAddress; // 名称
	@Column(name = "houseDesc", length = 200)
	private String houseDesc; // 描述
	@Column(name = "houseMapLocation", length = 200)
	private String houseMapLocation; // 地图定位信息
	@Column(name = "houseName", length = 200)
	private String houseName; // 定位信息
	@Column(name = "houseTypeName", length = 200)
	private String houseTypeName; // 房屋类型名
	@Column(name = "houseTypeId", length = 200)
	private Long houseTypeId; // 房屋类型ID
	@Column(name = "houseTwoDimensionalCode", length = 200)
	private String houseTwoDimensionalCode; // 房屋二维码图片
	@Column(name = "houseImage", length = 200)
	private String houseImage; // 房屋图片
	
	@Column(name = "houseOwnerId", length = 200)
	private Long houseOwnerId; // 房屋主ID
	@Column(name = "houseOwnerName", length = 200)
	private String houseOwnerName; // 房屋主名
	
	@Column(name = "registDate", length = 200)
	private Date registDate; // 注册日期
	
	@Column(name = "departmentName", length = 200)
	private String departmentName; // 房屋类型名
	@Column(name = "departmentId", length = 200)
	private Long departmentId; // 房屋类型ID
	
	
	@Column(name = "checkOpion", length = 200)
	private String checkOpion; // 房屋主名
	@Column(name = "checkDate", length = 200)
	private Date checkDate; // 审核日期
	@Column(name = "checkerId", length = 200)
	private Long checkerId; // 审核者ID
	@Column(name = "checkerName", length = 200)
	private String checkerName; //审核姓名
	

	
	
	public String getDepartmentName() {
		return departmentName;
	}



	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}



	public Long getDepartmentId() {
		return departmentId;
	}



	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}



	public String getCheckOpion() {
		return checkOpion;
	}



	public void setCheckOpion(String checkOpion) {
		this.checkOpion = checkOpion;
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



	public Long getHouseOwnerId() {
		return houseOwnerId;
	}



	public void setHouseOwnerId(Long houseOwnerId) {
		this.houseOwnerId = houseOwnerId;
	}



	public String getHouseOwnerName() {
		return houseOwnerName;
	}



	public void setHouseOwnerName(String houseOwnerName) {
		this.houseOwnerName = houseOwnerName;
	}



	public String getHouseTwoDimensionalCode() {
		return houseTwoDimensionalCode;
	}



	public void setHouseTwoDimensionalCode(String houseTwoDimensionalCode) {
		this.houseTwoDimensionalCode = houseTwoDimensionalCode;
	}



	public String getHouseImage() {
		return houseImage;
	}



	public void setHouseImage(String houseImage) {
		this.houseImage = houseImage;
	}


	
	public String getHouseAddress() {
		return houseAddress;
	}



	public void setHouseAddress(String houseAddress) {
		this.houseAddress = houseAddress;
	}



	public String getHouseDesc() {
		return houseDesc;
	}



	public void setHouseDesc(String houseDesc) {
		this.houseDesc = houseDesc;
	}



	public String getHouseMapLocation() {
		return houseMapLocation;
	}



	public void setHouseMapLocation(String houseMapLocation) {
		this.houseMapLocation = houseMapLocation;
	}



	public String getHouseName() {
		return houseName;
	}



	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}



	public String getHouseTypeName() {
		return houseTypeName;
	}



	public void setHouseTypeName(String houseTypeName) {
		this.houseTypeName = houseTypeName;
	}



	public Long getHouseTypeId() {
		return houseTypeId;
	}



	public void setHouseTypeId(Long houseTypeId) {
		this.houseTypeId = houseTypeId;
	}



	public House() {

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
		final House other = (House) obj;
		return Objects.equal(this.id, other.id) && Objects.equal(this.houseAddress, other.houseAddress) && Objects.equal(this.houseMapLocation, other.houseMapLocation);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.id, this.houseName, this.houseAddress);
	}

}
