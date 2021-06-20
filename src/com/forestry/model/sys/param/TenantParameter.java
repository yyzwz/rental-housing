package com.forestry.model.sys.param;

import java.sql.Date;

import javax.persistence.Column;

import core.extjs.ExtJSBaseParameter;

/**
 * @author 齐鸣鸣
 */
public class TenantParameter extends ExtJSBaseParameter {

	private static final long serialVersionUID = 6683458492945661067L;
	private String $like_tenantName;
	private String $like_tenantIdentify;
	private String $like_tenantWorkOrganization;
	private String $like_tenantFromShen;
	private String $like_tenantFromShi;
	private String $like_tenantFromXian;
	private String houseOwnerName;
	private String roomName;
	private String houseMapLocation;
	private String houseAddress;
	private String houseName;
	private String departmentName;
	
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getHouseName() {
		return houseName;
	}
	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getHouseMapLocation() {
		return houseMapLocation;
	}
	public void setHouseMapLocation(String houseMapLocation) {
		this.houseMapLocation = houseMapLocation;
	}
	public String getHouseAddress() {
		return houseAddress;
	}
	public void setHouseAddress(String houseAddress) {
		this.houseAddress = houseAddress;
	}
	public String getHouseOwnerName() {
		return houseOwnerName;
	}
	public void setHouseOwnerName(String houseOwnerName) {
		this.houseOwnerName = houseOwnerName;
	}
	public String get$like_tenantName() {
		return $like_tenantName;
	}
	public void set$like_tenantName(String $like_tenantName) {
		this.$like_tenantName = $like_tenantName;
	}
	public String get$like_tenantIdentify() {
		return $like_tenantIdentify;
	}
	public void set$like_tenantIdentify(String $like_tenantIdentify) {
		this.$like_tenantIdentify = $like_tenantIdentify;
	}
	public String get$like_tenantWorkOrganization() {
		return $like_tenantWorkOrganization;
	}
	public void set$like_tenantWorkOrganization(String $like_tenantWorkOrganization) {
		this.$like_tenantWorkOrganization = $like_tenantWorkOrganization;
	}
	public String get$like_tenantFromShen() {
		return $like_tenantFromShen;
	}
	public void set$like_tenantFromShen(String $like_tenantFromShen) {
		this.$like_tenantFromShen = $like_tenantFromShen;
	}
	public String get$like_tenantFromShi() {
		return $like_tenantFromShi;
	}
	public void set$like_tenantFromShi(String $like_tenantFromShi) {
		this.$like_tenantFromShi = $like_tenantFromShi;
	}
	public String get$like_tenantFromXian() {
		return $like_tenantFromXian;
	}
	public void set$like_tenantFromXian(String $like_tenantFromXian) {
		this.$like_tenantFromXian = $like_tenantFromXian;
	}
}
